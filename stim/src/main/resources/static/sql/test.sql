------------------------------------------\
--테스트용
CREATE TABLE user_tbl(
    user_code number PRIMARY KEY,
    user_id varchar2(20) UNIQUE NOT NULL,
    user_nickname varchar2(20) NOT NULL,
    user_password varchar2(20) NOT NULL
); 


CREATE SEQUENCE user_seq;

INSERT INTO user_tbl
VALUES (user_seq.nextval,'Sungmin','Synclair','4860');
INSERT INTO user_tbl
VALUES (user_seq.nextval,'Minji','mammamoo','0711');
INSERT INTO user_tbl
VALUES (user_seq.nextval,'Minjung','kmj','1111');
COMMIT;

SELECT * FROM user_tbl where user_id = 'Sungmin';  
-----------------------------------------

-- 프로필-댓글 테이블
CREATE TABLE profile_comment_tbl(
    comment_code number PRIMARY KEY,
    user_code number ,
    comment_writer varchar2(20) NOT NULL,
    comment_context clob NOT NULL,
    profile_regdate date NOT NULL --sysdate
    profile_introduce ("프로필에서 할말")
);

CREATE SEQUENCE comment_code_seq NOCACHE;

-- 친구테이블
CREATE TABLE friend_tbl(
    friend_code number PRIMARY KEY,
    friend_user1 number,
    friend_user2 number
);

CREATE SEQUENCE friend_code_seq NOCACHE;