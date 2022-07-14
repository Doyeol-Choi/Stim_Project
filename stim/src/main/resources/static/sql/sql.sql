-- 4줘해줘 프로젝트 ^ㅗ^
 
-- 회원 테이블
CREATE TABLE user_tbl(
    user_code number PRIMARY KEY,
    user_id varchar2(20) UNIQUE NOT NULL,
    user_password varchar2(20) NOT NULL,
    user_email varchar2(40) NOT NULL,
    user_phone char(11) NOT NULL,
    user_admin char(1) NOT NULL         -- Y/N
    );
    
-- 친구테이블
CREATE TABLE friend_tbl(
    friend_code number PRIMARY KEY,
    friend_user1 number,
    friend_user2 number
);

--찜목록 테이블
CREATE TABLE wish_tbl(
    wish_code number PRIMARY KEY,
    user_code number,
    game_code number
);

--장바구니 테이블
CREATE TABLE cart_tbl(
    cart_code number PRIMARY KEY,
    user_code number,
    game_code number
);

-- 프로필-댓글 테이블
CREATE TABLE profile_comment_tbl(
    comment_code number PRIMARY KEY,
    user_code number ,
    comment_writer varchar2(20) NOT NULL,
    comment_context crob NOT NULL,
    profile_regdate date NOT NULL               --sysdate
);

-- 보유게임 테이블
CREATE TABLE mygame_tbl(
    mygame_code number PRIMARY KEY,
    user_code number ,
    game_code number ,
    mygame_playtime number NOT NULL         --default=0
);

--게임테이블
CREATE TABLE game_tbl(
    game_code number PRIMARY KEY,
    game_name varchar2(100) UNIQUE,
    game_price number NOT NULL,
    game_releaseDate date NOT NULL,
    game_developer varchar(50) NOT NULL,
    game_distribution varchar(50) NOT NULL,
    game_context crob NOT NULL,
    game_salesRate number NOT NULL
    );

--장르 테이블
CREATE TABLE genre_tbl(
    genre_code number PRIMARY KEY,
    game_code number,
    genre_1 varchar2(20),
    genre_2 varchar2(20),
    genre_3 varchar2(20)
);

-- 게임평점 테이블
CREATE TABLE grade_tbl(
    grade_code number PRIMARY KEY,
    user_code number,
    game_code number,
    grade_context crob NOT NULL,
    grade_regDate date NOT NULL,            -- sysdate
    grade_rate char(1)                      -- G/B
);

-- 세일 테이블
CREATE TABLE sale_tbl(
    sale_code number PRIMARY KEY,
    game_code number,
    sale_discount number NOT NULL
);

-- 친구 테이블 외래키
ALTER TABLE friend_tbl ADD CONSTRAINT fk_friend_user1 FOREIGN KEY(friend_user1) references user_tbl (user_code);
ALTER TABLE friend_tbl ADD CONSTRAINT fk_friend_user2 FOREIGN KEY(friend_user2) references user_tbl (user_code);

-- 찜목록 테이블 외래키
ALTER TABLE wish_tbl ADD CONSTRAINT fk_user_code FOREIGN KEY(user_code) references user_tbl (user_code);
ALTER TABLE wish_tbl ADD CONSTRAINT fk_game_code FOREIGN KEY(game_code) references game_tbl (game_code);

-- 장바구니 테이블 외래키
ALTER TABLE cart_tbl ADD CONSTRAINT fk_user_code FOREIGN KEY(user_code) references user_tbl (user_code);
ALTER TABLE cart_tbl ADD CONSTRAINT fk_game_code FOREIGN KEY(game_code) references game_tbl (game_code);

-- 프로필 댓글 테이블 외래키
ALTER TABLE profile_comment_tbl ADD CONSTRAINT fk_user_code FOREIGN KEY(user_code) references user_tbl (user_code);

-- 보유게임 테이블 외래키
ALTER TABLE mygame_tbl ADD CONSTRAINT fk_user_code FOREIGN KEY(user_code) references user_tbl (user_code);
ALTER TABLE mygame_tbl ADD CONSTRAINT fk_game_code FOREIGN KEY(game_code) references game_tbl (game_code);

-- 장르 테이블 외래키
ALTER TABLE genre_tbl ADD CONSTRAINT fk_game_code FOREIGN KEY(game_code) references game_tbl (game_code);

-- 게임평점 테이블 외래키
ALTER TABLE grade_tbl ADD CONSTRAINT fk_user_code FOREIGN KEY(user_code) references user_tbl (user_code);
ALTER TABLE grade_tbl ADD CONSTRAINT fk_game_code FOREIGN KEY(game_code) references game_tbl (game_code);

-- 세일 테이블 외래키
ALTER TABLE sale_tbl ADD CONSTRAINT fk_game_code FOREIGN KEY(game_code) references game_tbl (game_code);

 