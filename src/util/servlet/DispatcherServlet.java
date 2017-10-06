package util.servlet;


import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import util.AttributeType;
import util.BeanOperate;
import util.ValidateParameter;
import vo.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.text.MessageFormat;
import java.util.*;

import static dao.AbstractDAOImpl.initCap;

public abstract class DispatcherServlet extends HttpServlet {

    protected SmartUpload smart = null;
    protected String forwardPath = "forward.page";
    protected String referer = "";
    protected String title = "";
    private static final String PAGES_BASENAME = "Pages";
    private static final String MESSAGES_BASENAME = "Messages";

    private ResourceBundle pagesResource;
    private ResourceBundle messagesResource;
    protected HttpServletRequest request;
    protected HttpServletResponse response;

    protected Integer currentPage = 1;
    protected Integer lineSize = 10;
    protected String keyWord = "";
    protected String column = "eid";
    protected String selectItems = null;

    public void init() throws ServletException {
        this.pagesResource = ResourceBundle.getBundle(PAGES_BASENAME,
                Locale.getDefault());
        this.messagesResource = ResourceBundle.getBundle(MESSAGES_BASENAME,
                Locale.getDefault());
    }

    ;

    public void handleSplit(String url) {
        try {
            this.currentPage = Integer.parseInt(this.request.getParameter("currentPage"));
        } catch (Exception e) {
        }
        try {
            this.lineSize = Integer.parseInt(this.request.getParameter("lineSize"));
        } catch (Exception e) {
        }
        this.column = request.getParameter("column") == null ? column : request.getParameter("column");
        this.keyWord = request.getParameter("keyWord") == null ? keyWord : request.getParameter("keyWord");
        this.request.setAttribute("currentPage", this.currentPage);
        this.request.setAttribute("lineSize", this.lineSize);
        this.request.setAttribute("column", this.column);
        this.request.setAttribute("keyWord", this.keyWord);
        this.request.setAttribute("url", getPath(url));
        this.request.setAttribute("selectItems", selectItems);

    }

    public boolean checkCode() {
        String code = this.request.getParameter("code");
        String rand = (String) this.request.getSession().getAttribute("rand");
        if (code == null || rand == null) {
            return false;
        }
        return rand.equalsIgnoreCase(code);
    }

    /**
     * 取得在Pages.properties文件里面定义的访问路径
     *
     * @param key 访问路径的key
     * @return 配置文件中的路径内容，如果没有返回null
     */
    public String getPath(String key) {
        return this.pagesResource.getString(key);
    }

    /**
     * 取得Messages.properties文件中的配置文字信息
     *
     * @param key  访问文字信息的key
     * @param args 所有占位符的内容
     * @return 配置文件中的内容，并且是组合后的结果，如果没有返回null
     */
    public String getMsg(String key, String... args) {
        String note = this.messagesResource.getString(key);
        if (args.length > 0 && title != null) { // 传递了参数内容
            return MessageFormat.format(note, args);
        } else {
            return MessageFormat.format(note, "");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        this.request = request;
        this.response = response;

        referer = request.getHeader("referer");
        if (referer != null) {
            referer = referer.substring(17);
        }
        String path = this.getPath("errors.page");
        String status = request.getRequestURI().substring(
                request.getRequestURI().lastIndexOf("/") + 1);
        if (request.getContentType() != null) {
            if (request.getContentType().contains("multipart/form-data")) {
                this.smart = new SmartUpload();
                try {
                    this.smart.initialize(super.getServletConfig(), request,
                            response);
                    this.smart.upload();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        // 现在可以找到当前类对象this，以及要调用的方法名称status，那么可以利用反射进行调用
        if (status != null && status.length() > 0) {
            // 在进行参数的处理之前，需要对提交数据进行验证
            if (this.validateRequest(status)) {
                this.handleRequest();// 处理参数与简单Java类之间的自动赋值
                try { // 只有将对应的数据都准备完毕了，才可以执行以下方法
                    Method method = this.getClass().getDeclaredMethod(status);
                    method.setAccessible(true);
                    path = this.getPath((String) method.invoke(this));// 反射调用方法
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        request.getRequestDispatcher(path).forward(request, response);
    }

    /**
     * 首先一定要取得操作状态
     *
     * @param status
     * @return
     */
    public boolean validateRequest(String status) {
        boolean flag = false;
        String rule = null;
        try {
            Field field = this.getClass().getDeclaredField(status + "Vlidate");
            field.setAccessible(true);
            rule = (String) field.get(this); // 取出验证规则的数据
        } catch (Exception e) { // 表示数据现在不存在
            flag = true;
        }
        // 需要针对于给定的内容进行指定格式的验证
        if (rule == null || "".equals(rule)) { // 现在没有规则
            flag = true;
        } else { // 现在有规则
            ValidateParameter vp = new ValidateParameter(this, request,
                    this.smart, rule, this.messagesResource);
            flag = vp.validate(); // 进行验证操作
            this.request.setAttribute("errors", vp.getErrors());
        }
        return flag;
    }

    /**
     * 此方法主要是判断是否有文件上传
     *
     * @return 没有文件上传返回false
     */
    public boolean isUpload() {
        try {
            return this.smart.getFiles().getSize() > 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 取得上传文件的个数
     *
     * @return
     */
    public int getUploadCount() {
        return this.smart.getFiles().getCount();
    }

    /**
     * 专门负责文件的保存操作
     *
     * @param index    SmartUpload操作索引
     * @param fileName 文件名称
     * @throws SmartUploadException
     * @throws IOException
     */
    private void saveFile(int index, String fileName) throws IOException,
            SmartUploadException {
        String filePath = super.getServletContext().getRealPath(
                this.getUploadDirectory())
                + fileName;
        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (this.smart.getFiles().getFile(index).getContentType()
                .contains("image")) {
            this.smart.getFiles().getFile(index).saveAs(filePath);
        }
    }

    /**
     * 删除文件
     *
     * @param fileName
     */
    public void deleteFile(String fileName) {
        String filePath = super.getServletContext().getRealPath(
                this.getUploadDirectory())
                + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 批量删除上传文件
     *
     * @param all
     */
    public void deleteFile(Set<String> all) {
        Iterator<String> iter = all.iterator();
        while (iter.hasNext()) {
            this.deleteFile(iter.next());
        }
    }

    /**
     * 存放一个文件信息
     *
     * @param fileName
     */
    public void save(String fileName) {
        try {
            this.saveFile(0, fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SmartUploadException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void save(Map<Integer, String> fileName) {
        Iterator<Map.Entry<Integer, String>> iter = fileName.entrySet()
                .iterator();
        while (iter.hasNext()) {
            Map.Entry<Integer, String> me = iter.next();
            try {
                this.saveFile(me.getKey(), me.getValue());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (SmartUploadException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建一个新的文件名称
     *
     * @return
     */
    public String createSingleFileName() {
        String fileName = "nophoto.jpg";
        if (this.isUpload()) {
            if (this.smart.getFiles().getFile(0).getContentType()
                    .contains("image")) {
                fileName = UUID.randomUUID() + "."
                        + this.smart.getFiles().getFile(0).getFileExt();
            }
        }
        return fileName;
    }

    public Map<Integer, String> createMultiFileName() {
        Map<Integer, String> all = new HashMap<Integer, String>();
        if (this.isUpload()) {
            for (int x = 0; x < this.smart.getFiles().getCount(); x++) {
                if (this.smart.getFiles().getFile(x).getContentType()
                        .contains("image")) {
                    String fileName = UUID.randomUUID() + "."
                            + this.smart.getFiles().getFile(x).getFileExt();
                    all.put(x, fileName);
                }
            }
        }
        return all;
    }

    public SmartUpload getSmart() {
        return this.smart;
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.doGet(request, response);
    }

    public void handleRequest() {
        if (request.getContentType() != null) {
            String s = request.getContentType();
            System.out.println(s);
            // 当前使用了表单封装，意味着是有文件上传，应该使用SmartUpload接收数据
            if (request.getContentType().contains("multipart/form-data")) {
                // 取得全部的请求参数名称，之所以需要名称，主要是确定自动赋值的操作
                Enumeration<String> enu = this.smart.getRequest()
                        .getParameterNames();
                while (enu.hasMoreElements()) { // 循环所有的参数名称
                    String paramName = enu.nextElement();
                    if (paramName.contains(".")) { // 按照简单Java类处理
                        AttributeType at = new AttributeType(this, paramName);
                        if (at.getFiledType().contains("[]")) { // 按照数组的方式进行处理
                            BeanOperate bo = new BeanOperate(this, paramName,
                                    this.smart.getRequest().getParameterValues(
                                            paramName));
                        } else { // 按照单个字符串的方式进行处理
                            BeanOperate bo = new BeanOperate(this, paramName,
                                    this.smart.getRequest().getParameter(
                                            paramName));
                        }
                    }
                }
            } else { // 应该使用普通的request对象进行数据的接收
                // 取得全部的请求参数名称，之所以需要名称，主要是确定自动赋值的操作
                autoSetVo();
            }
        } else { // 应该使用普通的request对象进行数据的接收
            // 取得全部的请求参数名称，之所以需要名称，主要是确定自动赋值的操作
            autoSetVo();
        }
    }

    protected void autoSetVo() {
        Enumeration<String> enu = request.getParameterNames();
        while (enu.hasMoreElements()) { // 循环所有的参数名称
            String paramName = enu.nextElement();
            if (paramName.contains(".")) { // 按照简单Java类处理
                AttributeType at = new AttributeType(this, paramName);
                if (at.getFiledType().contains("[]")) { // 按照数组的方式进行处理
                    BeanOperate bo = new BeanOperate(this, paramName,
                            request.getParameterValues(paramName));
                } else { // 按照单个字符串的方式进行处理
                    BeanOperate bo = new BeanOperate(this, paramName,
                            request.getParameter(paramName));
                }
            }
        }
    }

    public void setMsgAndUrl(String msgKey, String urlKey) {
        this.request.setAttribute("msg", this.getMsg(msgKey, title));
        this.request.setAttribute("url", this.getPath(urlKey));
    }

    public void setMsgAndBack(String msgKey) {
        this.request.setAttribute("msg", this.getMsg(msgKey, title));
        this.request.setAttribute("url", referer);
    }

    /**
     * 交由不同的子类来实现，可以由子类设置统一的占位符提示信息名称标记
     *
     * @return 返回不同子类的描述信息
     */

    /**
     * 取得要进行文件上传保存的目录
     *
     * @return
     */
    public abstract String getUploadDirectory();

    public Map<Integer, Object> autoSetSameVo() throws Exception {
        Enumeration<String> enumeration = smart == null ? request.getParameterNames() : smart.getRequest().getParameterNames();
        //得到当前类的Class对象
        Map<Integer, Object> allVo = new HashMap<>();
        Map<Integer, Object> allVo2 = new HashMap<>();
        Map<String, Object> isExistVo = new HashMap<>();
        Class currentClass = this.getClass();
        Object currentObject = this;
        boolean flag = true;
        while (enumeration.hasMoreElements()) {
            String parameterName = enumeration.nextElement();
            if (parameterName.contains(".")) {
                String[] nameValue = parameterName.split("\\.");
                if (nameValue.length == 2) {
                    for (int x = 0; x < nameValue.length - 1; x++) {
                        //利用当前类的Class对象取得getVo方法
                        Method getVoMethod = currentClass.getDeclaredMethod("get" + initCap(nameValue[x]));
                        //用get方法获取已经实例化了的成员对象（vo类）
                        Object o = getVoMethod.invoke(currentObject);
                        currentObject = o;
                        //获得vo类对象的Class对象
                        currentClass = o.getClass();
                    }
                    //利用当前Class对象来获取成员
                    Field field = currentClass.getDeclaredField(nameValue[1]);
                    //利用vo类对象的Class对象来获取vo中的set方法,参数名，和参数类型
                    Method setMethod = currentClass.getDeclaredMethod("set" + initCap(nameValue[1]), field.getType());
                    //利用vo中的set方法给vo设置值
                    String values[] = smart == null ? request.getParameterValues(parameterName) : smart.getRequest().getParameterValues(parameterName);

                    for (int x = 0; x < values.length; x++) {
                        //如果是第一次设置vo的值，那就实例化新的一个Vo类，如果是第二次，直接从set集合里边取出vo
                        Object vo;
                        if (flag) {
                            vo = currentClass.newInstance();
                        } else {
                            vo = allVo.get(x);
                        }
                        switch (field.getType().getSimpleName()) {//根据成员类型对VO的成员设置值
                            case "String":
                                setMethod.invoke(vo, values[x]);
                                break;
                            case "Integer":
                                setMethod.invoke(vo, "".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                break;
                            case "Date":
                                setMethod.invoke(vo, "".equals(values[x]) || values[x] == null ? null : Date.valueOf(values[x]));
                                break;
                            case "Double":
                                setMethod.invoke(vo, "".equals(values[x]) || values[x] == null ? null : Double.valueOf(values[x]));
                                break;
                            case "Admin":
                                Admin admin = new Admin();
                                admin.setAid(values[x]);
                                setMethod.invoke(vo, admin);
                                break;
                            case "Groups":
                                Groups groups = new Groups();
                                groups.setGid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethod.invoke(vo, groups);
                                break;
                            case "Role":
                                Role role = new Role();
                                role.setRid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethod.invoke(vo, role);
                                break;
                            case "Dept":
                                Dept dept = new Dept();
                                dept.setDid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethod.invoke(vo, dept);
                                break;
                            case "Jobs":
                                Jobs jobs = new Jobs();
                                jobs.setJid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethod.invoke(vo, jobs);
                                break;
                            case "Level":
                                Level level = new Level();
                                level.setLevid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethod.invoke(vo, level);
                                break;
                        }

                        //将设置好值的VO类对象加入到集合中
                        allVo.put(x, vo);

                    }
                    flag = false;
                } else {
                    Map<Integer, Object> objectMap = new HashMap<>();
                    Map<Integer, Method> setMethodMap = new HashMap<>();
                    Map<Integer, Method> getMethodMap = new HashMap<>();
                    for (int x = 0; x < nameValue.length - 1; x++) {
                        //利用当前类的Class对象取得getVo方法
//                        Method getVo1 = currentClass.getDeclaredMethod("get" + initCap(nameValue[x]));
//                        Object vo1 = getVo1.invoke(currentObject);
//                        Method setVo1 = vo1.getClass().getDeclaredMethod("set" + initCap(nameValue[x + 1]), vo1.getClass().getDeclaredField(nameValue[x + 1]).getType());
//
//                        currentClass = vo1.getClass();
//                        currentObject = vo1;
//
//                        Method getVo2 = vo1.getClass().getDeclaredMethod("get" + initCap(nameValue[x + 1]));
//                        Object vo2 = getVo2.invoke(vo1);
//                        Method setVo2 = vo2.getClass().getDeclaredMethod("set" + initCap(nameValue[x + 2]), vo2.getClass().getDeclaredField(nameValue[x + 2]).getType());
//
//                        setVo1.invoke(vo1, vo2);
//                        setVo2.invoke(vo2, 2);

                        Method getVo = currentClass.getDeclaredMethod("get" + initCap(nameValue[x]));
                        Object vo = getVo.invoke(currentObject);
                        Method setVo = vo.getClass().getDeclaredMethod("set" + initCap(nameValue[x + 1]), vo.getClass().getDeclaredField(nameValue[x + 1]).getType());
                        objectMap.put(x, vo);
                        setMethodMap.put(x, setVo);
                        getMethodMap.put(x, getVo);
                        currentClass = vo.getClass();
                        currentObject = vo;
                    }
                    currentClass = objectMap.get(0).getClass();
                    for (int x = 0; x < nameValue.length - 2; x++) {
                        setMethodMap.get(x).invoke(objectMap.get(x), objectMap.get(x + 1));
                    }

                    //利用vo中的set方法给vo设置值
                    String values[] = smart == null ? request.getParameterValues(parameterName) : smart.getRequest().getParameterValues(parameterName);
                    for (int x = 0; x < values.length; x++) {
                        //如果是第一次设置vo的值，那就实例化新的一个Vo类，如果是第二次，直接从vo1map集合里边取出vo
                        Object vo;
                        Object vo2;
                        if (flag) {
                            vo = currentClass.newInstance();
                        } else {
                            vo = allVo.get(x);
                        }
                        if (!isExistVo.containsKey(getMethodMap.get(1).getName())) {
                            vo2 = getMethodMap.get(1).invoke(vo);
                        } else {
                            vo2 = allVo2.get(x);
                        }
                        switch (objectMap.get(nameValue.length - 2).getClass().getDeclaredField(nameValue[nameValue.length - 1]).getType().getSimpleName()) {//根据成员类型对VO的成员设置值
                            case "String":
                                System.out.println(setMethodMap.get(nameValue.length - 2).getName() + "方法名字+操作的vo对象" + vo2 + "对象值：" + values[x]);
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, values[x]);
                                break;
                            case "Integer":
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, "".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                break;
                            case "Date":
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, "".equals(values[x]) || values[x] == null ? null : Date.valueOf(values[x]));
                                break;
                            case "Double":
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, "".equals(values[x]) || values[x] == null ? null : Double.valueOf(values[x]));
                                break;
                            case "Admin":
                                Admin admin = new Admin();
                                admin.setAid(values[x]);
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, admin);
                                break;
                            case "Groups":
                                Groups groups = new Groups();
                                groups.setGid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, groups);
                                break;
                            case "Role":
                                Role role = new Role();
                                role.setRid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, role);

                                break;
                            case "Dept":
                                Dept dept = new Dept();
                                dept.setDid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, dept);
                                break;
                            case "Jobs":
                                Jobs jobs = new Jobs();
                                jobs.setJid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, jobs);
                                break;
                            case "Level":
                                Level level = new Level();
                                level.setLevid("".equals(values[x]) || values[x] == null ? null : Integer.valueOf(values[x]));
                                setMethodMap.get(nameValue.length - 2).invoke(vo2, level);
                                break;
                        }

                        //将设置好值的VO类对象加入到集合中
                        allVo.put(x, vo);
                        allVo2.put(x, vo2);

                    }
                    flag = false;
                    isExistVo.put(getMethodMap.get(1).getName(), 1);

                }
                currentClass = this.getClass();
                currentObject = this;
            }

        }
        return allVo;
    }
}
