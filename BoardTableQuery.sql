DROP TABLE board;

DROP SEQUENCE seq_board_no;

CREATE TABLE board(
    no number,
    title varchar2(500) not null,
    content varchar2(4000),
    hit number,
    reg_date date not null,
    user_no number not null,
    primary key(no),
    constraint board_fk foreign key (user_no) references users(no)
);

ALTER TABLE board MODIFY hit num DEFAULT 0;

CREATE SEQUENCE seq_board_no
INCREMENT BY 1
START WITH 1
NOCACHE ;

INSERT INTO board
values(seq_board_no.nextval,
       '치킨쿠폰 뿌려요 !',
       '뻥인데 ㅋㅋ',
       0,
       sysdate,
       1
);

select no,
       title,
       content,
       hit,
       reg_date,
       user_no
from board;

DELETE board
where no = 1;