-- 一、删除数据库
DROP DATABASE IF EXISTS hrdb ;
-- 二、创建数据库
CREATE DATABASE hrdb CHARACTER SET UTF8 ;
-- 三、使用hr数据库
USE hrdb ;
-- 四、删除数据表
-- 1、删除管理员登录日志表
DROP TABLE IF EXISTS adminlogs;
-- 2、删除工资变更信息表
DROP TABLE IF EXISTS salary;
-- 3、删除职位变更信息
DROP TABLE IF EXISTS work;
-- 4、删除课程考勤记录表
DROP TABLE IF EXISTS logs;
-- 5、删除课程详情信息表
DROP TABLE IF EXISTS details;
-- 6、删除课程信息表
DROP TABLE IF EXISTS course;
-- 7、删除雇员信息表
DROP TABLE IF EXISTS employee;
-- 8、删除职位信息表
DROP TABLE IF EXISTS jobs;
-- 9、删除雇员级别信息表
DROP TABLE IF EXISTS level;
-- 10、删除管理员信息表
DROP TABLE IF EXISTS admin;
-- 11、删除角色-权限组信息表
DROP TABLE IF EXISTS role_groups;
-- 12、删除角色信息表
DROP TABLE IF EXISTS role;
-- 13、删除部门信息表
DROP TABLE IF EXISTS dept;
-- 14、删除权限信息表
DROP TABLE IF EXISTS action;
-- 15、删除权限组信息表
DROP TABLE IF EXISTS groups;


-- 五、创建数据表
-- 1、创建权限组信息表
CREATE TABLE groups (
	gid		INT	AUTO_INCREMENT,
	title		VARCHAR(50),
	note		TEXT ,
	CONSTRAINT pk_gid PRIMARY KEY(gid)
);

-- 2、权限信息表
CREATE TABLE action (
	actid		INT	AUTO_INCREMENT ,
	gid		INT,
	title		VARCHAR(50),
	url		VARCHAR(100) ,
	CONSTRAINT pk_actid PRIMARY KEY(actid) ,
	CONSTRAINT fk_gid1 FOREIGN KEY(gid) REFERENCES groups(gid) ON DELETE CASCADE
);

-- 3、角色信息表
CREATE TABLE role (
	rid		INT	AUTO_INCREMENT ,
	title		VARCHAR(50),
	note		TEXT,
	CONSTRAINT pk_rid PRIMARY KEY(rid)
);

-- 4、角色-权限组关系表
CREATE TABLE role_groups (
	rid		INT ,
	gid		INT ,
	CONSTRAINT fk_rid2 FOREIGN KEY(rid) REFERENCES role(rid) ON DELETE CASCADE ,
	CONSTRAINT fk_gid2 FOREIGN KEY(gid) REFERENCES groups(gid) ON DELETE CASCADE 
);

-- 5、管理员信息表
CREATE TABLE admin (
	aid		VARCHAR(50) ,
	rid		INT,
	password	VARCHAR(32),
	type		INT,
	lastdate	DATETIME,
	flag		INT ,
	CONSTRAINT pk_aid PRIMARY KEY(aid) ,
	CONSTRAINT fk_rid3 FOREIGN KEY(rid) REFERENCES role(rid) ON DELETE SET NULL
);

-- 6、管理员登录日志表
CREATE TABLE adminlogs (
	alid		INT	AUTO_INCREMENT ,
	aid		VARCHAR(50),
	logindate	DATETIME,
	ip		VARCHAR(50),
	CONSTRAINT pk_alid PRIMARY KEY (alid) ,
	CONSTRAINT fk_aid FOREIGN KEY(aid) REFERENCES admin(aid) ON DELETE CASCADE
);

-- 7、职位信息
CREATE TABLE jobs (
	jid		INT	AUTO_INCREMENT,
	title		VARCHAR(50),
	note		TEXT,
	CONSTRAINT pk_jid PRIMARY KEY(jid) 
);

-- 8、雇员级别信息
CREATE TABLE level (
	levid		INT	AUTO_INCREMENT ,
	title		VARCHAR(50),
	losal		FLOAT,
	hisal		FLOAT,
	CONSTRAINT pk_levid PRIMARY KEY(levid)
);


-- 9、部门信息表
CREATE TABLE dept (
	did		INT	AUTO_INCREMENT ,
	dname		VARCHAR(50),
	current		INT,
	CONSTRAINT pk_did PRIMARY KEY (did)
);

-- 10、雇员信息表
CREATE TABLE employee (
	eid		INT	,
	aid		VARCHAR(50) ,
	did		INT ,
	levid		INT ,
	jid		INT ,
	ename		VARCHAR(50),
	birthday	DATETIME,
	sex		VARCHAR(10),
	idcard		VARCHAR(18),
	dname		VARCHAR(50),
	job		VARCHAR(50),
	school		VARCHAR(50),
	profession	VARCHAR(50),
	grad		DATETIME,
	photo		VARCHAR(50),
	indate		DATETIME ,
	outdate		DATETIME ,
	status		INT ,
	sal		FLOAT ,
	note		TEXT,
	edu		VARCHAR(50) ,
	CONSTRAINT pk_eid PRIMARY KEY(eid) ,
	CONSTRAINT fk_aid4 FOREIGN KEY(aid) REFERENCES admin(aid) ON DELETE SET NULL ,
	CONSTRAINT fk_did4 FOREIGN KEY(did) REFERENCES dept(did) ON DELETE SET NULL ,
	CONSTRAINT fk_levid4 FOREIGN KEY(levid) REFERENCES level(levid) ON DELETE SET NULL ,
	CONSTRAINT fk_jid4 FOREIGN KEY(jid) REFERENCES jobs(jid) ON DELETE SET NULL 
) ;

-- 11、创建课程信息表
CREATE TABLE course (
	cid		INT	AUTO_INCREMENT,
	aid		VARCHAR(50),
	cname		VARCHAR(50),
	total		INT,
	begin		DATETIME,
	end		DATETIME,
	status		INT,
	photo		VARCHAR(50),
	note		TEXT,
	CONSTRAINT pk_cid PRIMARY KEY(cid) ,
	CONSTRAINT fk_aid6 FOREIGN KEY(aid) REFERENCES admin(aid) ON DELETE SET NULL 
);

-- 12、创建课程详情表
CREATE TABLE details (
	dtid		INT	AUTO_INCREMENT,
	cid		INT,
	eid		INT,
	ename		VARCHAR(50) ,
	score		FLOAT ,
	CONSTRAINT pk_dtid PRIMARY KEY(dtid) ,
	CONSTRAINT fk_cid7 FOREIGN KEY(cid) REFERENCES course(cid) ON DELETE CASCADE ,
	CONSTRAINT fk_eid7 FOREIGN KEY(eid) REFERENCES employee(eid) ON DELETE CASCADE 
);

-- 13、课程考勤表
CREATE TABLE logs (
	lid		INT	AUTO_INCREMENT,
	dtid		INT,
	recdate		DATETIME,
	num		INT,
	status		INT,
	CONSTRAINT pk_lid PRIMARY KEY(lid) ,
	CONSTRAINT fk_did8 FOREIGN KEY(dtid) REFERENCES details(dtid) ON DELETE CASCADE
);


-- 14、工资信息变更
CREATE TABLE salary (
	sid		INT	AUTO_INCREMENT,
	oldlevid	INT,
	newlevid	INT,
	eid		INT,
	aid		VARCHAR(50),
	cdate		DATETIME,
	oldsal		FLOAT,
	newsal		FLOAT,
	reason		TEXT,
	note		TEXT,
	CONSTRAINT pk_sid PRIMARY KEY (sid) ,
	CONSTRAINT fk_oldlevid9 FOREIGN KEY(oldlevid) REFERENCES level(levid) ,
	CONSTRAINT fk_newlevid9 FOREIGN KEY(newlevid) REFERENCES level(levid) ,
	CONSTRAINT fk_eid9 FOREIGN KEY(eid) REFERENCES employee(eid) ,
	CONSTRAINT fk_aid9 FOREIGN KEY(aid) REFERENCES admin(aid) 
);

-- 15、职位信息变更
CREATE TABLE work (
	wid		INT	AUTO_INCREMENT,
	eid		INT,
	aid		VARCHAR(50),
	olddid		INT,
	newdid		INT,
	oldjid		INT,
	newjid		INT,
	cdate		DATETIME,
	olddname	VARCHAR(50),
	oldjob		VARCHAR(50),
	newdname	VARCHAR(50),
	newjob		VARCHAR(50),
	reason		TEXT,
	note		TEXT,
	CONSTRAINT pk_wid PRIMARY KEY (wid) ,
	CONSTRAINT fk_eid10 FOREIGN KEY(eid) REFERENCES employee(eid) ,
	CONSTRAINT fk_aid10 FOREIGN KEY(aid) REFERENCES admin(aid)  ON DELETE SET NULL,
	CONSTRAINT fk_olddid10 FOREIGN KEY(olddid) REFERENCES dept(did) ON DELETE SET NULL ,
	CONSTRAINT fk_newdid10 FOREIGN KEY(newdid) REFERENCES dept(did) ON DELETE SET NULL ,
	CONSTRAINT fk_oldjid10 FOREIGN KEY(oldjid) REFERENCES jobs(jid) ON DELETE SET NULL,
	CONSTRAINT fk_newjid10 FOREIGN KEY(newjid) REFERENCES jobs(jid) ON DELETE SET NULL 
);

-- 六、测试数据
-- 1、部门信息
INSERT INTO dept(dname,current) VALUES ('开发部',0) ;
INSERT INTO dept(dname,current) VALUES ('销售部',0) ;
INSERT INTO dept(dname,current) VALUES ('财务部',0) ;
INSERT INTO dept(dname,current) VALUES ('人事部',0) ;
INSERT INTO dept(dname,current) VALUES ('后勤部',0) ;

-- 2、职位级别信息
INSERT INTO level(title,losal,hisal) VALUES ('LV01',800,2000) ;
INSERT INTO level(title,losal,hisal) VALUES ('LV02',2001,4000) ;
INSERT INTO level(title,losal,hisal) VALUES ('LV03',4001,6000) ;
INSERT INTO level(title,losal,hisal) VALUES ('LV04',6001,8000) ;
INSERT INTO level(title,losal,hisal) VALUES ('LV05',8001,10000);
INSERT INTO level(title,losal,hisal) VALUES ('LV06',10001,12000) ;
INSERT INTO level(title,losal,hisal) VALUES ('LV07',12001,14000) ;
INSERT INTO level(title,losal,hisal) VALUES ('LV08',14001,20000) ;
INSERT INTO level(title,losal,hisal) VALUES ('LV09',20001,30000) ;
INSERT INTO level(title,losal,hisal) VALUES ('LV10',30001,90000) ;

-- 3、职位信息
INSERT INTO jobs(title,note) VALUES ('实习生','-') ;
INSERT INTO jobs(title,note) VALUES ('初级工程师','-') ;
INSERT INTO jobs(title,note) VALUES ('中级工程师','-') ;
INSERT INTO jobs(title,note) VALUES ('高级工程师','-') ;
INSERT INTO jobs(title,note) VALUES ('经理','-') ;
INSERT INTO jobs(title,note) VALUES ('总监','-') ;
INSERT INTO jobs(title,note) VALUES ('副总裁','-') ;
INSERT INTO jobs(title,note) VALUES ('总裁','-') ;

-- 4、权限组信息
INSERT INTO groups (title,note) VALUES ('权限管理','-') ;
INSERT INTO groups (title,note) VALUES ('信息管理','-') ;
INSERT INTO groups (title,note) VALUES ('课程管理','-') ;
INSERT INTO groups (title,note) VALUES ('人事管理','-') ;

-- 5、权限信息
INSERT INTO action(gid,title,url) VALUES (1,'增加管理员','/pages/back/admin/admin/AdminBackServlet/insertPre') ;
INSERT INTO action(gid,title,url) VALUES (1,'管理员列表','/pages/back/admin/admin/AdminBackServlet/list') ;
INSERT INTO action(gid,title,url) VALUES (1,'增加角色','/pages/back/admin/role/RoleBackServlet/insertPre') ;
INSERT INTO action(gid,title,url) VALUES (1,'角色列表','/pages/back/admin/role/RoleBackServlet/list') ;
INSERT INTO action(gid,title,url) VALUES (1,'增加权限组','/pages/back/admin/groups/GroupsBackServlet/insertPre') ;
INSERT INTO action(gid,title,url) VALUES (1,'权限组列表','/pages/back/admin/groups/GroupsBackServlet/list') ;
INSERT INTO action(gid,title,url) VALUES (1,'增加权限','/pages/back/admin/action/GroupsBackServlet/insertPre') ;
INSERT INTO action(gid,title,url) VALUES (1,'权限列表','/pages/back/admin/action/GroupsBackServlet/list') ;

INSERT INTO action(gid,title,url) VALUES (2,'增加部门','/pages/back/admin/dept/DeptBackServlet/insertPre') ;
INSERT INTO action(gid,title,url) VALUES (2,'部门列表','/pages/back/admin/dept/DeptBackServlet/list') ;
INSERT INTO action(gid,title,url) VALUES (2,'增加职位','/pages/back/admin/jobs/JobsBackServlet/insertPre') ;
INSERT INTO action(gid,title,url) VALUES (2,'职位列表','/pages/back/admin/jobs/JobsBackServlet/list') ;
INSERT INTO action(gid,title,url) VALUES (2,'增加级别','/pages/back/admin/level/LevelBackServlet/insertPre') ;
INSERT INTO action(gid,title,url) VALUES (2,'级别列表','/pages/back/admin/level/LevelBackServlet/list') ;

INSERT INTO action(gid,title,url) VALUES (3,'课程列表','/pages/back/admin/course/CourseBackServlet/list') ;

INSERT INTO action(gid,title,url) VALUES (4,'在职人员列表','/pages/back/admin/employee/EmployeeBackServlet/listIn') ;
INSERT INTO action(gid,title,url) VALUES (4,'离职人员列表','/pages/back/admin/employee/EmployeeBackServlet/listOut') ;
INSERT INTO action(gid,title,url) VALUES (4,'薪金变更列表','/pages/back/admin/employee/SalaryBackServlet/list') ;
INSERT INTO action(gid,title,url) VALUES (4,'职位变更列表','/pages/back/admin/employee/WorkBackServlet/list') ;

-- 6、角色信息
INSERT INTO role(title,note) VALUES ('管理员角色','-') ;
INSERT INTO role(title,note) VALUES ('信息管理','-') ;

-- 7、角色-权限组信息
INSERT INTO role_groups(rid,gid) VALUES (1,1) ;
INSERT INTO role_groups(rid,gid) VALUES (1,2) ;
INSERT INTO role_groups(rid,gid) VALUES (1,3) ;
INSERT INTO role_groups(rid,gid) VALUES (1,4) ;
INSERT INTO role_groups(rid,gid) VALUES (2,2) ;
INSERT INTO role_groups(rid,gid) VALUES (2,3) ;
INSERT INTO role_groups(rid,gid) VALUES (2,4) ;

-- 8、管理员信息
-- 增加管理员信息：admin / hello
INSERT INTO admin(aid,password,rid,type,flag) VALUES ('admin','5D41402ABC4B2A76B9719D911017C592',1,1,1) ;
-- 增加人事管理员信息：mldn / java
INSERT INTO admin(aid,password,type,flag) VALUES ('mldn','93F725A07423FE1C889F448B33D21F46',0,0) ;
COMMIT ;
