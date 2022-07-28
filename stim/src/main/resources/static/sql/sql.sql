-- 4줘해줘 프로젝트 ^ㅗ^
SELECT * FROM game_tbl WHERE rownum <= 10 ORDER BY game_releaseDate DESC;
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
--ALTER TABLE user_tbl ADD user_nickname varchar2(20) DEFAULT '테스트' NOT NULL;
--ALTER TABLE user_tbl MODIFY user_password varchar2(200);
--ALTER TABLE user_tbl MODIFY user_nickname varchar2(40);
CREATE SEQUENCE user_code_seq NOCACHE;
SELECT * FROM user_tbl;
UPDATE user_tbl SET user_admin = 'Y' WHERE user_id = 'admin';
commit;
-- 친구테이블
CREATE TABLE friend_tbl(
    friend_code number PRIMARY KEY,
    friend_user1 number,
    friend_user2 number,
    friend_accepted char(1)
);

CREATE SEQUENCE friend_code_seq NOCACHE;

--찜목록 테이블
CREATE TABLE wish_tbl(
    wish_code number PRIMARY KEY,
    user_code number,
    game_code number
);

CREATE SEQUENCE wish_code_seq NOCACHE;

--장바구니 테이블
CREATE TABLE cart_tbl(
    cart_code number PRIMARY KEY,
    user_code number,
    game_code number
);

CREATE SEQUENCE cart_code_seq NOCACHE;

-- 프로필-댓글 테이블
CREATE TABLE profile_comment_tbl(
    comment_code number PRIMARY KEY,
    user_code number ,
    writer_code number NOT NULL,
    comment_context clob NOT NULL,
    profile_regdate date NOT NULL               --sysdate
);

CREATE SEQUENCE comment_code_seq NOCACHE;

-- 보유게임 테이블
CREATE TABLE mygame_tbl(
    mygame_code number PRIMARY KEY,
    user_code number,
    game_code number
);

CREATE SEQUENCE mygame_code_seq NOCACHE;

--게임테이블
CREATE TABLE game_tbl(
    game_code number PRIMARY KEY,
    game_name varchar2(100) UNIQUE,
    game_price number NOT NULL,
    game_releaseDate date NOT NULL,
    game_developer varchar(50) NOT NULL,
    game_distribution varchar(50) NOT NULL,
    game_context clob NOT NULL,
    game_salesRate number NOT NULL,
    game_picture varchar2(100) NOT NULL,
    game_discount number DEFAULT '' NOT NULL     --default=""
    );
ALTER TABLE game_tbl ADD game_discount number;
SELECT * FROM game_tbl;
CREATE SEQUENCE game_code_seq NOCACHE;

--장르 테이블
CREATE TABLE genre_tbl(
    genre_code number PRIMARY KEY,
    game_code number,
    genre_1 varchar2(30),
    genre_2 varchar2(30),
    genre_3 varchar2(30)
);

CREATE SEQUENCE genre_code_seq NOCACHE;

-- 게임평점 테이블
CREATE TABLE grade_tbl(
    grade_code number PRIMARY KEY,
    user_code number,
    game_code number,
    grade_context clob NOT NULL,
    grade_regDate date NOT NULL,            -- sysdate
    grade_rate char(1)                      -- G/B
);

CREATE SEQUENCE grade_code_seq NOCACHE;

-- 로그인 유지를 위한 테이블
CREATE TABLE persistent_logins (
    username varchar(64),
    series varchar(64) PRIMARY KEY,
    token varchar(64),
    last_used timestamp
);

-- 친구 테이블 외래키
ALTER TABLE friend_tbl ADD CONSTRAINT fk_friend_user1 FOREIGN KEY(friend_user1) references user_tbl (user_code);
ALTER TABLE friend_tbl ADD CONSTRAINT fk_friend_user2 FOREIGN KEY(friend_user2) references user_tbl (user_code);

-- 찜목록 테이블 외래키
ALTER TABLE wish_tbl ADD CONSTRAINT fk_user_code_wish FOREIGN KEY(user_code) references user_tbl (user_code);
ALTER TABLE wish_tbl ADD CONSTRAINT fk_game_code_wish FOREIGN KEY(game_code) references game_tbl (game_code);

-- 장바구니 테이블 외래키
ALTER TABLE cart_tbl ADD CONSTRAINT fk_user_code_cart FOREIGN KEY(user_code) references user_tbl (user_code);
ALTER TABLE cart_tbl ADD CONSTRAINT fk_game_code_cart FOREIGN KEY(game_code) references game_tbl (game_code);

-- 프로필 댓글 테이블 외래키
ALTER TABLE profile_comment_tbl ADD CONSTRAINT fk_user_code_profile FOREIGN KEY(user_code) references user_tbl (user_code);

-- 보유게임 테이블 외래키
ALTER TABLE mygame_tbl ADD CONSTRAINT fk_user_code_mygame FOREIGN KEY(user_code) references user_tbl (user_code);
ALTER TABLE mygame_tbl ADD CONSTRAINT fk_game_code_mygame FOREIGN KEY(game_code) references game_tbl (game_code);

-- 장르 테이블 외래키
ALTER TABLE genre_tbl ADD CONSTRAINT fk_game_code_genre FOREIGN KEY(game_code) references game_tbl (game_code);

-- 게임평점 테이블 외래키
ALTER TABLE grade_tbl ADD CONSTRAINT fk_user_code_grade FOREIGN KEY(user_code) references user_tbl (user_code);
ALTER TABLE grade_tbl ADD CONSTRAINT fk_game_code_grade FOREIGN KEY(game_code) references game_tbl (game_code);

-- 게임테이블 샘플데이터
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'GTA5',44000,'2015-04-14','Rockstar North','Rockstar Games','동네 건달과 은퇴한 은행 강도 미치광이 사이코패스는 아무도 믿을 수 없는 서로조차 믿을 수 없는 이 무자비한 도시에서 살아남기 위해 그들은 여러 번의 위험한 습격에 몸을 던져야 합니다.',31,'gta5.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Don`t Starve Together',15500,'2016-04-22','Klei Entertainment','Klei Entertainment','이상한 생물, 위험, 신비가 가득한 기묘한 신세계를 탐험합니다. 자원을 수집하여 자신의 생존 스타일에 적합한 아이템과 구조물을 만들어야 합니다. 이상한 땅의 미스터리를 풀며 게임을 진행합니다.',10,'dontstarve.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Raft',21000,'2022-06-21','Redbeet Interactive','Axolot Games','혼자 또는 친구와 함께 당신의 임무는 전 세계의 장대한 해양 모험에서 살아남는 것입니다. 위험한 바다! 생존을 위해 잔해를 모으고 뗏목을 확장하고 잊혀지고 위험한 섬을 향해 항해하십시오!',5,'Raft.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'ELDEN RING',64800,'2022-02-25','FromSoftware Inc','FromSoftware Inc','다채로운 시추에이션을 지닌 탁 트인 필드와 복잡하면서 입체적으로 짜인 거대한 던전이 경계선 없이 이어지는 드넓은 세계. 탐색 끝에는 미지의 것들을 발견했다는 기쁨과 높은 성취감으로 이어지는 압도적인 위협이 플레이어를 기다립니다.',7,'eldenring.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Stardew Valley',16000,'2016-02-27','ConcernedApe','ConcernedApe','당신은 스타듀 밸리에 있는 할아버지의 오래된 농장을 물려받았습니다. 물려받은 도구와 동전 몇 개로 무장한 당신은 새로운 삶을 시작하기 시작했습니다.',25,'StardewValley.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Rust',46000,'2018-02-09','Facepunch Studios','Facepunch Studios','Rust의 단 하나의 목적은 생존입니다. 이를 위해 당신은 굶주림, 갈증, 추위와 같은 어려움들을 이겨내야 합니다. 피신처를 건설하세요',15,'Rust.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'FIFA 22',66000,'2021-10-01','EA Canada and EA Romania','Electronic Arts','전 세계 17,000명 이상의 선수들, 700개 이상의 팀, 90개 이상의 경기장, 30개 이상의 리그를 만나볼 수 있는 세계적인 게임을 플레이하세요.',25,'FIFA22.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'SD건담 배틀 얼라이언스',69800,'2022-08-25','ARTDINK','Bandai Namco Entertainment','『SD건담 배틀 얼라이언스』는 「기동전사 건담」 시리즈의 다양한 모빌슈트와 캐릭터가 각 작품의 세계관을 초월하여 함께 출연하는 새로운 SD건담 액션 RPG입니다.',15,'SDGUNDAM.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'하우스 플리퍼',26000,'2018-05-17','Empyrean','Frozen District, PlayWay S.A.',' 집을 청소하고,벽에 페인트를 칠하고, 난방 및 샤워 시설, 에어컨을 설치하고, 심지어 가구 전체를 세팅할 수도 있습니다.',8,'HouseFlipper.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'아이돌마스터 스탈릿 시즌',64800,'2021-10-14','ILCA,Inc.','BANDAI NAMCO Entertainment','플레이어는 아이돌의 프로듀서가 되어 아이돌을 육성하고 개성 풍부한 아이돌들과 다양한 고락을 함께하며 공통의 목표인 톱 아이돌을 목표로 하게 됩니다.',60,'idolmaster.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'God of War',45800,'2022-01-15','Santa Monica Studio','PlayStation PC LLC','올림푸스 신들을 향한 복수심을 뒤로하고 크레토스는 이제 북유럽 신과 괴물의 땅에 살고 있습니다. 항상 생존을 위해 싸워야 하는 이 혹독하고 가차 없는 세상에서, 그는 생존을 위해 싸우고… 아들에게도 그 방식을 가르쳐야 합니다.',17,'GodofWar.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Super Bunny Man',16500,'2017-09-01','Catobyte','Catobyte Ltd','호기심을 자극할 무언가를 찾아 웜홀을 뛰어넘는 토끼, 시공간을 가로지르는 도전적인 여행을 떠나보세요',25,'SuperBunnyMan.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'TEKKEN 7',43800,'2017-06-02','BANDAI NAMCO Studios Inc.','BANDAI NAMCO Entertainment','미시마 일가의 길고 긴 전설적인 싸움의 끝과, 이 잔혹한 싸움에 감춰져있는 비밀이 밝혀집니다. Unreal Engine 4로 개발되어, 유명 격투 게임 철권이 더욱 충실한 스토리와 영화같은 연출, 그리고 긴장감 넘치는 배틀을 즐겨보세요',80,'tekken.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Arma 3',36000,'2013-09-12','Bohemia Interactive','Bohemia Interactive','거대한 군사 샌드박스 속에서 진정한 전투를 경험하세요. Arma 3는 다양한 싱글플레이어와 멀티플레이어 콘텐츠, 20여 개의 차량, 40여 개의 무기, 무한한 콘텐츠 창작의 기회를 제공하는 PC 최고의 군사 게임입니다. ',37,'arma3.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'DayZ',50000,'2018-12-14','Bohemia Interactive','Bohemia Interactive','DayZ는 수단과 방법을 가리지 않고 생존하는 것이 유일한 목표인 하드코어 오픈월드 서바이벌 게임입니다. 하지만 곳곳에 숨어있는 수많은 위협으로 인해 생존이란 그리 녹녹치 않습니다.',22,'dayz.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Dying Light 2 Stay Human',66000,'2022-02-04','Techland','Techland','20년이 넘는 세월 전 하란에서 우리는 바이러스와 치열한 싸움을 벌였으나 패배하고 말았습니다. 그리고 지금도 계속 패배하고 있습니다.인류의 마지막 거주지가 있는 도시 역시 갈등 속에서 분열의 조짐을 보이고 있습니다. ',44,'dyinglight.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'One Hand Clapping',20000,'2021-12-14','Bad Dream Games','HandyGames','One Hand Clapping 은 보컬 2D 플랫포머입니다. 마이크에 대고 노래하거나 허밍하여 퍼즐을 풀고 주변 세상을 변화시키는 목소리의 힘에 대한 자신감을 찾으세요.',8,'onehandclapping.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Fallout 4',22000,'2015-11-10','Bethesda Game Studios','Bethesda Softworks','볼트 111의 유일한 생존자로서 당신은 핵전쟁으로 파괴된 세계로 들어갑니다. 매 순간이 생존을 위한 싸움이며 모든 선택은 여러분의 몫입니다. ',66,'fallout4.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'It Takes Two',44000,'2021-03-26','Hazelight','Electronic Arts','마법에 걸려 인형으로 변해버린 서로 너무나도 다른 부부, 코디와 메이로 플레이하세요. 예측할 수 없는 것들이 구석구석 숨어 있는 환상적인 세계에 함께 갇힌 두 사람은, 내키지 않더라도 깨진 관계를 회복하고 힘을 합쳐야 합니다.',77,'ittakestwo.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Tricky Towers',16000,'2016-08-02','WeirdBeard','WeirdBeard','마법 주문은 사방에서 날아오고 벽돌은 쉴새없이 쏟아질 겁니다. Tricky Towers로 광란의 즐거움을 느껴보세요!',17,'trickytowers.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'썸썸 편의점',21000,'2020-01-15','TALESSHOP','TALESSHOP','집안 사정으로 편의점에서 알바를 하게 된 주인공.심심해서 스마트폰을 만지작 거리다 연애 어플 [SOME SOME]을 다운로드 받는다.SOME SOME을 켜자 세 명의 아가씨와 새로운 인연이 시작되는데.',88,'somesome.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'BIOHAZARD 6',28200,'2013-03-22','Capcom','Capcom','액션과 서바이벌 호러가 섞인 Biohazard 6는 2013년의 극적인 공포 게임이 될 것을 약속합니다. 치명적인 C 바이러스와 마주하며 이야기는 북미, 전쟁으로 황폐해진 유럽 국가 에도니아, 그리고 중국의 도시 란샹으로 이어집니다.',25,'biohazard.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'문명6',65000,'2016-10-21','Firaxis Games','2K, Aspyr','석기 시대부터 정보화 시대에 걸쳐 세계를 주름잡는 문명의 지배자가 되십시오. 전쟁을 일으키고, 외교관계를 조율하며, 문명을 꽃피우고 역사적인 지도자들과 겨루며 세계사에 길이 남을 위대한 문명을 건설하십시오.',90,'civilization6.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Left 4 Dead 2',10500,'2009-11-17','Valve','Valve','이 공포 협동 액션 게임은 5개의 추가된 캠페인을 통해 사바나에서 뉴올리언스까지 이어지는 미국 최남단의 도시, 늪지대, 공동묘지 등으로 여러분과 친구들을 안내할 것입니다.',76,'left4dead.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'A Dance of Fire and Ice',6500,'2019-01-25','7th Beat Games','7th Beat Games','A Dance of Fire and Ice는 하나의 키로 간단하게 즐길 수 있는 엄격한 판정의 리듬 게임입니다.',24,'ADanceofFireandIce.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'COUNTER-STRIKE',10500,'2000-11-01','Valve','Valve','세계 최고의 온라인 액션 게임을 즐기십시오. 전 세계적으로 유명한 팀 기반의 게임에서 테러리스트가 벌이는 생생한 전투의 현장에 흠뻑 빠지십시오. 팀 동료들과 협력하여 전략적인 미션을 완수하고, 적군 기지를 점령하고 인질을 구출해보십시오.',97,'COUNTER-STRIKE.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Among Us',5500,'2018-11-17','Innersloth','Innersloth','새로운 Among Us 맵, Airship에 승선하세요! 이 비행선에서 서로 협력하며 위대한 계획을 완수해보세요... 크루원일지, 임포스터 편일지는 또 다른 문제죠.',25,'AmongUs.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Assassin`s Creed Odyssey',65000,'2018-10-06','Ubisoft','Ubisoft','낙오자에서 살아있는 전설이 되기까지, 긴 여정을 따라가며 당신의 과거에 숨겨진 비밀을 밝혀내고 고대 그리스의 운명을 바꾸어야 합니다.',66,'Assassin`sCreedOdyssey.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Assassin`s Creed Origins',65000,'2017-10-27','Ubisoft','Ubisoft','위엄과 음모가 공존하던 시대, 고대 이집트가 무자비한 권력 투쟁 속에 사라져 갈 위기에 처해 있습니다. 암살단이 창립된 그때로 돌아가 잔혹한 비밀과 잊혀진 신화의 베일을 벗기십시오.',60,'Assassin`sCreedOrigins.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Assassin`s Creed Syndicate',44000,'2015-11-19','Ubisoft','Ubisoft','산업 혁명이 한창이던 1868년 런던에서 지하 조직을 이끌고 영향력을 높여 진보의 이름 아래 약자를 착취하는 악당과 싸우십시오:',33,'Assassin`sCreedSyndicate.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Assassin`s Creed Rogue',22000,'2015-03-24','Ubisoft','Ubisoft','Shay는 자신에게 등을 돌리는 모든 사람들을 제거하고 궁극적으로 역사상 가장 두려운 암살자가 되는 임무를 시작합니다.',23,'Assassin`sCreedRogue.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'맛집 스토리',12000,'2022-07-04','Kairosoft','Kairosoft','레스토랑 경영 시뮬레이션 게임! 레시피를 개발하고 식재료를 탐색해 지역 최고의 맛집을 향해봐요!',15,'CafeteriaNipponica.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'백화점 스토리',12000,'2022-07-04','Kairosoft','Kairosoft','하늘을 뚫을 만큼 거대한 백화점을 만들자! 여러 가게를 배치하여 멋진 백화점을 만드는 경영 게임이에요.',15,'MegaMallStory.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'워터파크 스토리',12500,'2022-07-04','Kairosoft','Kairosoft','다양한 모습의 풀장을 만들어 쾌적한 레저 공간을 만들어보세요.대중의 인기를 얻으면 워터파크를 찾아오는 손님과 SNS 친구가 될 수 있어요!',25,'PoolSlideStory.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'아카데미 스토리2 ',12000,'2022-06-13','Kairosoft','Kairosoft','교실과 복도를 자유롭게 배치하며 인기 있는 명문학교를 만들자!연애 시스템과 동아리 시스템도 있는 파워업 버전이에요.',25,'PocketAcademy.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Sniper Elite 5',51000,'2022-05-26','Rebellion','Rebellion','사실적이고 섬세한 현실 세계의 장소를 배경으로 한 몰입도 높은 맵에서 전투를 시작하고, 개선된 이동 시스템을 통해 더 많은 곳을 탐험하고 경험하세요.',41,'SniperElite5.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'귀멸의 칼날 히노카미 혈풍담',64800,'2021-10-16','CyberConnect2','SEGA','솔로 플레이 모드에서는 가족을 살해당하고 혈귀로 변해버린 여동생 네즈코를 사람으로 되돌리기 위해카마도 탄지로가 되어 혈귀와 맞서 싸우는 애니메이션 「귀멸의 칼날」에서 그려진 스토리를 경험할 수 있다!',41,'DemonSlayer.jpg', '');
INSERT INTO game_tbl VALUES 
(game_code_seq.nextval,'Project Zomboid',20500,'2013-11-08','The Indie Stone','The Indie Stone','Project Zomboid는 개방형 좀비 감염 샌드박스입니다.',41,'ProjectZomboid.jpg', '');


-- 장르테이블 샘플데이터
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,1,'오픈월드','액션','범죄');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,2,'탐험','생존','샌드박스');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,3,'생존','협동','건설');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,4,'RPG','판타지','오픈월드');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,5,'RPG','농업','픽셀그래픽');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,6,'생존','건설','액션');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,7,'축구','경쟁','시뮬레이션');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,8,'3인칭','액션','로봇');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,9,'샌드박스','캐주얼','건설');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,10,'애니메이션','경영','음악');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,11,'RPG','액션','신화');
INSERT INTO genre_tbl VALUES 
(genre_code_seq.nextval,12,'유머','협동','액션');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,13,'격투','액션','멀티플레이어');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,14,'군사','1인칭','오픈 월드');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,15,'생존','좀비','슈팅');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,16,'1인칭 슈팅','온라인 협동','생존');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,17,'어드벤처','인디','음악');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,18,'탐험','포스트아포칼립스','오픈월드');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,19,'분할 화면','퍼즐','어드벤처');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,20,'파티 게임','퍼즐','건설');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,21,'연애 시뮬레이션','선정적인 내용','성인');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,22,'공포','생존','고어');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,23,'전략','턴제 전략','역사');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval,24,'고어','좀비','협동');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 25, '리듬', '음악', '인디');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 26, '멀티플레이어', '액션', '슈팅');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 27, '멀티플레이어', '온라인 협동', '우주');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 28, '오픈 월드', 'RPG', '암살');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 29, '오픈 월드', '암살', '액션');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 30, '오픈 월드', '암살', '잠입');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 31, '오픈 월드', '암살', '액션');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 32, '요리', 'RPG', '시뮬레이션');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 33, '픽셀그래픽', '던전 크롤러', '건설');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 34, '시뮬레이션', '캐주얼', '건설');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 35, '시뮬레이션', '픽셀그래픽', '연애시뮬레이션');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 36, '액션', '어드벤처', '잠입');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 37, '애니메이션', '액션', '어드벤처');
INSERT INTO genre_tbl VALUES
(genre_code_seq.nextval, 38, '생존', '좀비', '오픈 월드');

commit;
--친구목록 코드26 기준
SELECT user_picture, user_nickname FROM user_tbl 
WHERE user_code IN (SELECT friend_user2 FROM friend_tbl WHERE friend_user1 = 26 AND friend_accepted='Y')
or user_code IN (SELECT friend_user1 FROM friend_tbl WHERE friend_user2 = 26 AND friend_accepted='Y');
-- 친구요청 코드 26 기준
SELECT user_picture, user_nickname FROM user_tbl 
WHERE user_code IN (SELECT friend_user1 FROM friend_tbl WHERE friend_user2 = 26 AND friend_accepted='N');