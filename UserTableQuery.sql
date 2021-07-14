--users table 삭제
drop table users;

--users sequence 삭제
drop sequence seq_user_no;

-- 테이블 생성
create table users (
    no number,
    id varchar2(20) UNIQUE not null,
    passward varchar2(20) not null,
    name varchar2(20),
    gender varchar2(10),
    primary key(no)
);

--시퀀스 생성
create sequence seq_user_no
increment by 1
start with 1
nocache;

-- 데이터 추가
insert into users
values (
    seq_user_no.nextval,'junzzang','1234','오준식','male');

commit;

-- 출력
select *
from users;
