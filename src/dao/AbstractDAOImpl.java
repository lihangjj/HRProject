package dao;

import util.dao.IDAO;
import vo.*;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.*;
import java.util.Date;

public abstract class AbstractDAOImpl<K, V> implements IDAO<K, V> {
    protected Connection conn;
    protected PreparedStatement pre;
    protected ResultSet res;
    protected String sql;
    protected StringBuffer buf = new StringBuffer();
    protected String tableName;
    protected Class cls;
    protected List<Field> fields = new ArrayList<>();

    public AbstractDAOImpl(Connection conn, V vo) throws Exception {
        this.conn = conn;
        cls = vo.getClass();
        vo = null;
        tableName = cls.getSimpleName().toLowerCase();
        sql = "desc " + tableName;
        pre = conn.prepareStatement(sql);
        res = pre.executeQuery();
        while (res.next()) {
            fields.add(cls.getDeclaredField(res.getString(1)));
        }
    }

    /**
     * 实现数据的批量删除，这个时候的批量删除属于彻底删除功能
     *
     * @param table  表名称
     * @param column 删除表的列名称
     * @param ids    所有的id数据，使用Set集合可以避免重复
     * @return 如果删除成功返回true，否则返回false
     * @throws SQLException
     */
    public boolean removeHandle(String table, String column, Set<?> ids)
            throws SQLException {
        if (ids.size() == 0) { // 表示现在没有任何的数据
            return false;
        }
        buf.setLength(0);
        buf.append("DELETE FROM ").append(table).append(" WHERE ")
                .append(column).append(" IN (");
        Iterator<?> iter = ids.iterator();
        while (iter.hasNext()) {
            buf.append(iter.next()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length()).append(")");
        this.pre = this.conn.prepareStatement(buf.toString());
        return this.pre.executeUpdate() == ids.size();
    }

    /**
     * 负责统计出数据量
     *
     * @param table   要统计数据的表名称
     * @param column  模糊查询的数据列
     * @param keyWord 模糊查询关键字
     * @return 返回指定表的数据量，如果表没有数据，返回0
     * @throws SQLException
     */
    public Integer countHandle(String table, String column, String keyWord)
            throws SQLException {
        buf.setLength(0);
        buf.append("SELECT COUNT(*) FROM ").append(table).append(" WHERE ")
                .append(column).append(" LIKE ?");
        this.pre = this.conn.prepareStatement(buf.toString());
        this.pre.setString(1, "%" + keyWord + "%");
        ResultSet rs = this.pre.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        return 0;
    }

    public Set<String> photoHandle(String table, String photoColumn,
                                   String column, Set<?> ids) throws SQLException {
        Set<String> all = new HashSet<String>();
        buf.setLength(0);
        buf.append("SELECT ").append(photoColumn).append(" FROM ")
                .append(table).append(" WHERE ").append(column).append(" IN (");
        Iterator<?> iter = ids.iterator();
        while (iter.hasNext()) {
            buf.append(iter.next()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length()).append(")");
        buf.append(" AND ").append(photoColumn).append("<>'nophoto.jpg'");
        this.pre = this.conn.prepareStatement(buf.toString());
        ResultSet rs = this.pre.executeQuery();
        while (rs.next()) {
            all.add(rs.getString(1));
        }
        return all;
    }

    public boolean doCreate(V vo) throws Exception {
        buf.setLength(0);
        buf.append("insert into " + tableName + "(");
        for (Field x : fields) {
            buf.append(x.getName()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length()).append(")values(");
        //有几个字段需要设置，就有y个问号
        for (int x = 0; x < fields.size(); x++) {
            buf.append("?,");
        }
        buf.delete(buf.length() - 1, buf.length()).append(")");
        pre = conn.prepareStatement(buf.toString());
        voGet(vo);
        return pre.executeUpdate() > 0;
    }

    private void voGet(V vo) throws Exception {
        for (int x = 0; x < fields.size(); x++) {
            Method getMethod = cls.getMethod("get" + initCap(fields.get(x).getName()));
            switch (fields.get(x).getType().getSimpleName()) {
                case "String":
                    pre.setString(x + 1, (String) getMethod.invoke(vo));
                    break;
                case "Integer":
                    if (getMethod.invoke(vo) == null) {
                        pre.setInt(x + 1, Types.NULL);
                    } else {
                        pre.setInt(x + 1, (int) getMethod.invoke(vo));
                    }
                    break;
                case "Double":
                    if (getMethod.invoke(vo) == null) {
                        pre.setDouble(x + 1, Types.NULL);
                    } else {
                        pre.setDouble(x + 1, (Double) getMethod.invoke(vo));
                    }
                    break;
                case "Date":
                    pre.setTimestamp(x + 1, getMethod.invoke(vo) == null ? null : new Timestamp(((Date) getMethod.invoke(vo)).getTime()));
                    break;
                case "Admin":
                    pre.setString(x + 1, ((Admin) getMethod.invoke(vo)).getAid());
                    break;
                case "Role":
                    pre.setInt(x + 1, ((Role) getMethod.invoke(vo)).getRid());
                    break;
                case "Groups":
                    pre.setInt(x + 1, ((Groups) getMethod.invoke(vo)).getGid());
                    break;
                case "Dept":
                    pre.setInt(x + 1, ((Dept) getMethod.invoke(vo)).getDid());
                    break;
                case "Jobs":
                    pre.setInt(x + 1, ((Jobs) getMethod.invoke(vo)).getJid());
                    break;
                case "Level":
                    pre.setInt(x + 1, ((Level) getMethod.invoke(vo)).getLevid());
                    break;
            }
        }
    }

    public static String initCap(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    @Override
    public List<V> findAll() throws Exception {
        List<V> list = new ArrayList<>();
        bufSelectAll();
        pre = conn.prepareStatement(buf.toString());
        res = pre.executeQuery();
        while (res.next()) {
            V vo = (V) cls.newInstance();
            voSet(vo);
            list.add(vo);
        }
        return list;
    }

    protected void voSet(V vo) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, SQLException {
        for (int x = 0; x < fields.size(); x++) {
            Method setMethod = cls.getMethod("set" + initCap(fields.get(x).getName()), fields.get(x).getType());
            switch (fields.get(x).getType().getSimpleName()) {
                case "String":
                    setMethod.invoke(vo, res.getString(x + 1));
                    break;
                case "Integer":
                    setMethod.invoke(vo, res.getInt(x + 1));
                    break;
                case "Date":
                    setMethod.invoke(vo, res.getDate(x + 1));
                    break;
                case "Double":
                    setMethod.invoke(vo, res.getDouble(x + 1));
                    break;
                case "Admin":
                    Admin admin = new Admin();
                    admin.setAid(res.getString(x + 1));
                    setMethod.invoke(vo, admin);
                    break;
                case "Groups":
                    Groups groups = new Groups();
                    groups.setGid(res.getInt(x + 1));
                    setMethod.invoke(vo, groups);
                    break;
                case "Role":
                    Role role = new Role();
                    role.setRid(res.getInt(x + 1));
                    setMethod.invoke(vo, role);
                    break;
                case "Dept":
                    Dept dept = new Dept();
                    dept.setDid(res.getInt(x + 1));
                    setMethod.invoke(vo, dept);
                    break;
                case "Jobs":
                    Jobs jobs = new Jobs();
                    jobs.setJid(res.getInt(x + 1));
                    setMethod.invoke(vo, jobs);
                    break;
                case "Level":
                    Level level = new Level();
                    level.setLevid(res.getInt(x + 1));
                    setMethod.invoke(vo, level);
                    break;
            }
        }
    }

    @Override
    public boolean doUpdate(V vo) throws Exception {
        buf.setLength(0);
        buf.append("update " + tableName + " set ");
        for (Field x : fields) {
            buf.append(x.getName() + "=?,");
        }
        buf.delete(buf.length() - 1, buf.length()).append(" where " + fields.get(0).getName() + "=?");
        pre = conn.prepareStatement(buf.toString());
        voGet(vo);
        Method getMethod = cls.getMethod("get" + initCap(fields.get(0).getName()));
        switch (fields.get(0).getType().getSimpleName()) {
            case "String":
                pre.setString(fields.size() + 1, (String) getMethod.invoke(vo));
                break;
            case "Integer":
                if (getMethod.invoke(vo) == null) {
                    pre.setInt(fields.size() + 1, Types.NULL);
                } else {
                    pre.setInt(fields.size() + 1, (int) getMethod.invoke(vo));
                }
                break;
        }
        return pre.executeUpdate() > 0;
    }

    /**
     * @param ids 包含了所有要删除的数据ID，不包含有重复内容
     * @return
     * @throws Exception
     */
    @Override
    public boolean doRemoveBatch(Set<K> ids) throws Exception {
        buf.setLength(0);
        buf.append("delete from " + tableName + " where " + fields.get(0).getName() + " in(");
        Iterator<K> iterator = ids.iterator();
        while (iterator.hasNext()) {
            K key = iterator.next();
            buf.append(key + ",");
        }
        buf.delete(buf.length() - 1, buf.length()).append(")");
        pre = conn.prepareStatement(buf.toString());
        return pre.executeUpdate() == ids.size();
    }

    @Override
    public V findById(K id) throws Exception {
        bufSelectAll();
        buf.append(" where " + fields.get(0).getName() + "=?");
        pre = conn.prepareStatement(buf.toString());
        switch (id.getClass().getSimpleName()) {
            case "String":
                pre.setString(1, (String) id);
                break;
            case "Integer":
                pre.setInt(1, (Integer) id);
                break;
        }
        res = pre.executeQuery();
        V vo = null;
        while (res.next()) {
            vo = (V) cls.newInstance();
            voSet(vo);
        }
        return vo;
    }

    protected void bufSelectAll() {
        buf.setLength(0);
        buf.append("select ");
        for (Field x : fields) {
            buf.append(x.getName()).append(",");
        }
        buf.delete(buf.length() - 1, buf.length()).append(" from " + tableName);
    }

    @Override
    public List<V> findAllSplit(Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        List<V> list = new ArrayList<>();
        buf.setLength(0);
        bufSelectAll();
        buf.append(" where " + column + " like ? limit " + (currentPage - 1) * lineSize + "," + lineSize);
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, "%" + keyWord + "%");
        res = pre.executeQuery();
        while (res.next()) {
            V vo = (V) cls.newInstance();
            voSet(vo);
            list.add(vo);
        }
        return list;
    }

    @Override
    public Integer getAllCount(String column, String keyWord) throws Exception {
        buf.setLength(0);
        buf.append("select count(*) from " + tableName + " where " + column + " like ?");
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, "%" + keyWord + "%");
        res = pre.executeQuery();
        if (res.next()) {
            return res.getInt(1);
        }
        return 0;
    }

    public List<V> findAllSplitByStatus(Integer status, Integer currentPage, Integer lineSize, String column, String keyWord) throws Exception {
        List<V> list = new ArrayList<>();
        buf.setLength(0);
        bufSelectAll();
        buf.append(" where status=" + status + " and " + column + " like ? limit " + (currentPage - 1) * lineSize + "," + lineSize);
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, "%" + keyWord + "%");
        res = pre.executeQuery();
        V vo;
        while (res.next()) {
            vo = (V) cls.newInstance();
            voSet(vo);
            list.add(vo);
        }
        return list;
    }

    public Integer getAllCountByStatus(Integer status, String column, String keyWord) throws Exception {
        buf.setLength(0);
        buf.append("select count(*) from " + tableName + " where status=" + status + " and " + column + " like ?");
        pre = conn.prepareStatement(buf.toString());
        pre.setString(1, "%" + keyWord + "%");
        res = pre.executeQuery();
        while (res.next()) {
            return res.getInt(1);
        }
        return 0;
    }
}
