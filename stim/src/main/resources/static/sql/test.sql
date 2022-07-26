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
    comment_nickname varchar2(20) NOT NULL,
    comment_context clob NOT NULL,
    profile_regdate date NOT NULL,
    --write_code number,
    CONSTRAINT FK_uCode FOREIGN KEY(user_code) REFERENCES user_tbl(user_code) 
);
CREATE SEQUENCE comment_code_seq NOCACHE;

INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 1, 'Sungmin', '테스트123', sysdate);
INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 1, 'Minji', '담배 마렵네', TO_DATE(20220624));
INSERT INTO profile_comment_tbl
VALUES(comment_code_seq.nextval, 1, 'Minjung', '테스트321', TO_DATE(20220705));
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
       (SELECT user_picture FROM user_tbl u, profile_comment_tbl c WHERE u.user_code IN 1 ) AS user_picture
FROM profile_comment_tbl c, user_tbl u
WHERE u.user_id = 'Sungmin';
-----------------------------------------