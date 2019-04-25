create database service2db default character set utf8;
use service2db;

create table t_user{
	user_id varchar(48) primary key comment '用户id',
	user_name varchar(30) not null comment '用户名',
	password varchar(64) not null comment '密码',
	user_phone varchar(20) comment '手机号',
	user_type tinyint(4) not null default '1' comment '用户类型 1:普通用户 2:会员 3:管理员',
	locked tinyint(4) not null default '0' comment '用户状态 0:正常 1:锁定',
	credit int(11) default '0' comment '积分',
	last_visit datetime default null comment '上次登录时间'
	last_ip varchar(30) default null comment '上次登录ip'
}engine = InooDB default charset = utf8;

create table t_login_log{
	login_log_id varchar(48) primary key comment '登录记录id',
	user_id varchar(48) not null comment '用户id',
	ip varchar(30) not null comment 'ip',
	login_datetime varchar(30) not null comment '登录时间'
}engine = InooDB default charset = utf8;

create table t_board{
	board_id varchar(48) primary key comment '板块id',
	board_name varchar(20) not null default '默认' comment '板块名称',
	board_desc varchar(150) comment '板块描述',
	letter_num int(11) not null default '0' comment '信件数量'
}engine = InooDB default charset = utf8;

create table t_letter{
	letter_id varchar(48) primary key comment '信件id',
	board_id varchar(48) comment '所属板块',
	letter_title varchar(50) comment '信件题目',
	user_id varchar(48) not null comment '用户id',
	create_time datetime not null comment '上传时间',
	letter_views int(11) default '0' not null comment '浏览次数',
	letter_replies int(11) default '0' not null comment '回复数',
	letter_shares int(11) default '0' not null comment '分享数',
	post_id varchar(48) not null comment '内容对应的post_id',
	letter_digests int(11) default '0' not null comment '点赞数',
	letter_collects int(11) default '0' not null comment '收藏数'
}engine = InooDB default charset = utf8;

create table t_post{
	post_id varchar(48) primary key comment '内容id',
	post_text text not null comment '内容文本',
	last_post_id varchar(48) comment '上一条post_id，可选项',
	from_user_id varchar(48) comment '发送者id,可选项',
	to_user_id varchar(48) comment '接收者id,可选项',
	create_time datetime not null comment '发送时间',
	digest int (11) default '0' not null comment '点赞数'
}engine = InooDB default charset = utf8;

create table t_dynamicses{
	dynamics_id varchar(48) primary key comment '动态id',
	dynamics_title varchar(50) comment '动态题目',
	create_time datetime not null comment '动态时间',
	views int(11) default '0' not null comment '浏览数',
	replies int(11) default '0' not null comment '回复数',
	digests int(11) default '0' not null comment '点赞数',
	user_id varchar(48) not null comment '用户id',
	post_id varchar(48) not null comment '内容id',
	collects int(11) default '0' not null comment '收藏数'
}















