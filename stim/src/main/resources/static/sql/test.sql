DROP TABLE user_tbl;
DROP SEQUENCE user_seq;
DROP TABLE profile_comment_tbl;
------------------------------------------\
--테스트용

-- 회원 테이블
CREATE TABLE user_tbl(
    user_code number PRIMARY KEY,
    user_id varchar2(60) UNIQUE NOT NULL,
    user_password varchar2(200) NOT NULL,
    user_email varchar2(40) NOT NULL,
    user_phone char(11) NOT NULL,
    user_picture varchar2(100) NOT NULL,
    user_admin char(1) NOT NULL,        -- Y/N
    user_nickname varchar2(40) NOT NULL
    );
ALTER TABLE user_tbl ADD user_nickname varchar2(20) DEFAULT '테스트' NOT NULL;
ALTER TABLE user_tbl MODIFY user_id varchar2(60);
ALTER TABLE user_tbl MODIFY user_nickname varchar2(40);
CREATE SEQUENCE user_seq;

INSERT INTO user_tbl
VALUES (user_seq.nextval,'Sungmin','4860','synclair@naver.com',2197,'testprofile.jpg',0,'Synclair');
INSERT INTO user_tbl
VALUES (user_seq.nextval,'Minji','0711','test@naver.com',2222,'minji.jpg',0,'mammamoo');
INSERT INTO user_tbl
VALUES (user_seq.nextval,'Minjung','1111','nast@naver.com',1234,'sergent.jpg',0,'kmj');
INSERT INTO user_tbl
VALUES (user_seq.nextval,'Doyeol','1234','dopa@gmail.com',12333,'oyaji.jpg',1,'dopa');
COMMIT;

--update user_tbl SET user_nickname = 'dopa', user_email = 'dopa@gmail.com' WHERE user_id = 'Doyeol';
SELECT * FROM user_tbl where user_id = 'Sungmin';  
SELECT * FROM user_tbl; 

DROP TABLE profile_comment_tbl;
DROP SEQUENCE comment_code_seq;

CREATE TABLE profile_comment_tbl(
    comment_code number PRIMARY KEY,
    user_code number,
    comment_context clob NOT NULL,
    profile_regdate date NOT NULL,
    writer_code number,
    CONSTRAINT FK_uCode FOREIGN KEY(user_code) REFERENCES user_tbl(user_code) 
);
CREATE SEQUENCE comment_code_seq NOCACHE;

INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 1, '테스트123', sysdate, 1);
INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 1, '담배 마렵네', TO_DATE(20220624), 2);
INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 1, '테스트321', TO_DATE(20220705), 3);
COMMIT;


INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 2, '테스트321', sysdate, 1);
INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 2, '민지민지', TO_DATE(20220624), 2);
INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 2, '테스트312321', TO_DATE(20220705), 3);
COMMIT;

SELECT comment_code, c.user_code AS user_code, comment_nickname AS writer, comment_context AS content, profile_regdate AS regDate 
FROM profile_comment_tbl c, user_tbl u
WHERE c.user_code = u.user_code;

SELECT * FROM profile_comment_tbl;


SELECT comment_code, 
       c.user_code AS user_code,  
       comment_nickname, 
       comment_context, 
       profile_regdate,
       user_picture,
       user_nickname
FROM user_tbl u, profile_comment_tbl c
WHERE u.user_code = c.writer_code;

-----------------------------------------