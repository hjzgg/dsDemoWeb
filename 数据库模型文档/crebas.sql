/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2016/4/29 20:38:00                           */
/*==============================================================*/


drop table if exists comment;

drop table if exists problem;

drop table if exists user;

/*==============================================================*/
/* Table: comment                                               */
/*==============================================================*/
create table comment
(
   commentid            varchar(32) not null,
   problemid            varchar(32),
   userid               varchar(32),
   ccontent             longtext,
   ctime                timestamp,
   primary key (commentid)
);

alter table comment comment '评论的表';

/*==============================================================*/
/* Table: problem                                               */
/*==============================================================*/
create table problem
(
   problemid            varchar(32) not null,
   problemname          varchar(50),
   primary key (problemid)
);

alter table problem comment '题目的表';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   userid               varchar(32) not null,
   username             varchar(50),
   password             varchar(30),
   primary key (userid)
);

alter table user comment '用户表';

alter table comment add constraint FK_problemToComment foreign key (problemid)
      references problem (problemid) on delete restrict on update restrict;

alter table comment add constraint FK_userToComent foreign key (userid)
      references user (userid) on delete restrict on update restrict;

