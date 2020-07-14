#데이터베이스 생성
create database team12;

#테이블 생성 (각자 테이블 2개씩 맡아 코드 작성)

use team12;

create table DBCOURSE_Customer_Category (
	Customer_Category int primary key,
	Customer_Age_Range int,
	Customer_Gender varchar(10) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE DBCOURSE_Restaurants (
  Restaurant_ID INT PRIMARY KEY,
  Restaurant_Name VARCHAR(45) NOT NULL,
  Restaurant_Type VARCHAR(45),
  Restaurant_Info VARCHAR(45),
  Opening_hours TIME NOT NULL,
  Closing_hours TIME NOT NULL
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table DBCOURSE_Access_Route (
	Restaurant_ID INT primary key,
 	Phone_Number varchar(45),
 	Address varchar(45) NOT NULL,
 	Website_Address varchar(45),
    FOREIGN KEY (Restaurant_ID) REFERENCES DBCOURSE_Restaurants(Restaurant_ID) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE DBCOURSE_Holiday (
  Restaurant_ID INT ,
  Closed_Day VARCHAR(45) ,
  Holiday_Closed Char(1) ,
  PRIMARY KEY(Restaurant_ID, Closed_Day) ,
  constraint R_ID FOREIGN KEY (Restaurant_ID) REFERENCES DBCOURSE_Restaurants(Restaurant_ID) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE DBCOURSE_Ingredients(
  Menu_ID INT PRIMARY KEY,
  Food_Name VARCHAR(45),
  main_ingredient VARCHAR(45),
  sub_ingredient VARCHAR(45),
  Vegetarian_YN Char(1)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE DBCOURSE_Menu(
  Restaurant_ID INT,
  Menu_ID INT ,
  Representative_Food VARCHAR(45) ,
  Price INT,
  PRIMARY KEY (Restaurant_ID, Menu_ID),
  FOREIGN KEY (Restaurant_ID) REFERENCES DBCOURSE_Restaurants (Restaurant_ID) ON DELETE CASCADE,
  FOREIGN KEY (Menu_ID) REFERENCES DBCOURSE_Ingredients(Menu_ID) ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
create table DBCOURSE_Booked_Customers(
	Customer_ID int primary key,
	Customer_Category int,
	Phone_Number varchar(20) not null
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table DBCOURSE_Bookings(
	Booking_ID int primary key,
	Restaurant_ID int not null,
	Customer_ID int not null,
	Time_Of_Event time not null,
	Date_Of_Event date not null,
	foreign key(Restaurant_ID) references DBCOURSE_Restaurants(Restaurant_ID)ON DELETE CASCADE,
	foreign key(Customer_ID) references DBCOURSE_Booked_Customers(Customer_ID)ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

create table DBCOURSE_Reviews(
	Review_ID varchar(45),
	Restaurant_ID INT,
    Customer_Category INT,
    Star DOUBLE NOT NULL,
    Review VARCHAR(100),
    PRIMARY KEY (Review_ID),
    FOREIGN KEY(Restaurant_ID)REFERENCES DBCOURSE_Restaurants(Restaurant_ID)ON DELETE CASCADE,
    FOREIGN KEY(Customer_Category)REFERENCES DBCOURSE_Customer_Category(Customer_Category)ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;
    
create table DBCOURSE_Evaluation(
	Restaurant_ID INT PRIMARY KEY,
    Star_avg DOUBLE,
    Number_Of_Reviews INT,
    FOREIGN KEY(Restaurant_ID)REFERENCES DBCOURSE_Restaurants(Restaurant_ID)ON DELETE CASCADE
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


# index 설정

alter table DBCOURSE_Bookings add index bookings_index(Booking_ID);
alter table DBCOURSE_Evaluation add index star_index(Star_avg);
alter table DBCOURSE_Restaurants add index restaurant_id_index(Restaurant_ID);
alter table DBCOURSE_Menu add index price_index(Price);
alter table DBCOURSE_Ingredients add index menu_id_index(Menu_ID);


# 초기 데이터 삽입

/* Insert Customer_Category */
insert into DBCOURSE_Customer_Category values(11, 10, '여자');
insert into DBCOURSE_Customer_Category values(12, 10, '남자');
insert into DBCOURSE_Customer_Category values(21, 20, '여자');
insert into DBCOURSE_Customer_Category values(22, 20, '남자');
insert into DBCOURSE_Customer_Category values(31, 30, '여자');
insert into DBCOURSE_Customer_Category values(32, 30, '남자');
insert into DBCOURSE_Customer_Category values(41, 40, '여자');
insert into DBCOURSE_Customer_Category values(42, 40, '남자');
insert into DBCOURSE_Customer_Category values(51, 50, '여자');
insert into DBCOURSE_Customer_Category values(52, 50, '남자');
insert into DBCOURSE_Customer_Category values(61, 60, '여자');
insert into DBCOURSE_Customer_Category values(62, 60, '남자');
insert into DBCOURSE_Customer_Category values(71, NULL, '여자');
insert into DBCOURSE_Customer_Category values(72, NULL, '남자');

/*Insert Restaurants*/
insert into DBCOURSE_Restaurants values(1,'파파노다이닝','일식','이대 뒷골목에 숨어있는 일본 가정식','11:00:00','21:00:00');
insert into DBCOURSE_Restaurants values(2,'까이식당','아시아음식','싱가폴 치킨라이스가 맛있는 집','11:00:00','20:00:00');
insert into DBCOURSE_Restaurants values(3,'샐러디','이탈리아','건강하고 가벼운 한끼','08:00:00','20:00:00');
insert into DBCOURSE_Restaurants values(4,'란쥬탕슉','중식','새콤달콤매콤 자꾸 땡기는 사천탕수육 맛집','12:00:00','21:00:00');
insert into DBCOURSE_Restaurants values(5,'에그앤드스푼레이스','이탈리아','브런치가 맛있는 모임장소','11:00:00','21:00:00');
insert into DBCOURSE_Restaurants values(6,'아키','일식','맛있는 술과 안주가 있는 일식당','11:00:00','01:00:00');
insert into DBCOURSE_Restaurants values(7,'유라꾸키친','일식','모든 재료를 수제로 만드는 곳','11:30:00','20:00:00');
insert into DBCOURSE_Restaurants values(8,'가야가야','일식','국물 맛이 깊고 맛있는 이대 라멘 맛집','11:00:00','21:00:00');
insert into DBCOURSE_Restaurants values(9,'방콕익스프레스','태국음식','팟타이가 맛있는 태국 요리점','17:30:00','22:00:00');
insert into DBCOURSE_Restaurants values(10,'미분당','베트남음식','혼밥하기 좋은 고기듬뿍 쌀국수','11:00:00','21:00:00');

/* Insert Access_Route */
insert into DBCOURSE_Access_Route values(1, '02-364-0604', '서울 서대문구 이화여대길 88-14', NULL);
insert into DBCOURSE_Access_Route values(2, '070-7570-0871', '서울 서대문구 이화여대2가길 24', 'https://instagram.com/bistrogai');
insert into DBCOURSE_Access_Route values(3, '02-313-0577', '서울 서대문구 이화여대길 52', 'http://saladykorea.com/');
insert into DBCOURSE_Access_Route values(4, '02-362-9370', '서울 서대문구 이화여대2가길 18', NULL);
insert into DBCOURSE_Access_Route values(5, '02-312-5234', '서울 서대문구 이화여대길 26', 'https://instagram.com/spoon_race');
insert into DBCOURSE_Access_Route values(6, '02-313-1363', '서울 서대문구 성산로 531', NULL);
insert into DBCOURSE_Access_Route values(7, '02-324-7779', '서울 서대문구 이화여대3길 12-4', NULL);
insert into DBCOURSE_Access_Route values(8, '02-363-7877', '서울 서대문구 이화여대5길 7-3', 'http://www.gayagaya.co.kr');
insert into DBCOURSE_Access_Route values(9, '02-6401-7793', '서울 서대문구 연세로2길 93-7', 'http://www.bangkokexp.com/');
insert into DBCOURSE_Access_Route values(10, '02-3141-0807', '서울 서대문구 연세로5다길 35', NULL);

/*Insert Holiday*/
insert into DBCOURSE_Holiday values(1,'None','N');
insert into DBCOURSE_Holiday values(2,'Sun','Y');
insert into DBCOURSE_Holiday values(3,'None','Y');
insert into DBCOURSE_Holiday values(4,'None','N');
insert into DBCOURSE_Holiday values(5,'Mon','Y');
insert into DBCOURSE_Holiday values(6,'Sun','N');
insert into DBCOURSE_Holiday values(7,'Sat','Y');
insert into DBCOURSE_Holiday values(7,'Sun','Y');
insert into DBCOURSE_Holiday values(8,'None','N');
insert into DBCOURSE_Holiday values(9,'None','N');
insert into DBCOURSE_Holiday values(10,'None','N');

/*Insert Booked_Customer*/
insert into DBCOURSE_Booked_Customers values(11793, 21, '010-4268-1793');
insert into DBCOURSE_Booked_Customers values(25306, 32, '010-2584-5306');
insert into DBCOURSE_Booked_Customers values(32108, 31, '010-4489-2108');
insert into DBCOURSE_Booked_Customers values(49908, 41, '010-3376-9908');
insert into DBCOURSE_Booked_Customers values(54533, 11, '010-4723-4533');
insert into DBCOURSE_Booked_Customers values(63456, 21, '010-3423-3456');
insert into DBCOURSE_Booked_Customers values(72311, 31, '010-3255-2311');
insert into DBCOURSE_Booked_Customers values(82222, 21, '010-1111-2222');
insert into DBCOURSE_Booked_Customers values(91234, 42, '010-1234-1234');
insert into DBCOURSE_Booked_Customers values(109999, 22, '010-3424-9999');
insert into DBCOURSE_Booked_Customers values(112222, 21, '010-1111-2222');

/*Insert Booking*/
insert into DBCOURSE_Bookings values(105211, 1, 11793,'11:00:00',str_to_date('2018.05.21', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(106012, 2, 25306,'11:30:00',str_to_date('2018.06.01', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(104134, 4, 32108,'12:00:00',str_to_date('2018.04.13', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(205074, 4, 63456,'12:30:00',str_to_date('2018.05.07', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(105235, 5, 109999,'13:00:00',str_to_date('2018.05.23', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(205255, 5, 72311,'13:30:00',str_to_date('2018.05.25', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(105258, 8, 49908,'14:00:00',str_to_date('2018.05.25', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(205228, 8, 91234,'14:30:00',str_to_date('2018.05.22', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(106019, 9, 54533,'12:00:00',str_to_date('2018.06.01', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(1052210, 10,82222,'12:30:00',str_to_date('2018.05.22', '%Y.%m.%d'));
insert into DBCOURSE_Bookings values(206021, 1, 112222,'16:00:00',str_to_date('2018.06.02', '%Y.%m.%d'));


/*Insert Evalution*/
insert into DBCOURSE_Evaluation values(1,5,1);
insert into DBCOURSE_Evaluation values(2,4,1);
insert into DBCOURSE_Evaluation values(3,3.75,4);
insert into DBCOURSE_Evaluation values(4,4.3,3);
insert into DBCOURSE_Evaluation values(5,3.5,2);
insert into DBCOURSE_Evaluation values(6,4,3);
insert into DBCOURSE_Evaluation values(7,4,4);
insert into DBCOURSE_Evaluation values(8,4.5,2);
insert into DBCOURSE_Evaluation values(9,4,2);
insert into DBCOURSE_Evaluation values(10,4.6,3);

/*Insert Review*/
insert into DBCOURSE_Reviews values('abc001',1,21,5,'맛있고 깔끔한 일본식음식');
insert into DBCOURSE_Reviews values('today15',2,22,4, NULL);
insert into DBCOURSE_Reviews values('noname',3,71,3,'야채만 한 가득');
insert into DBCOURSE_Reviews values('booking77',3,52,3,'맛있지만 계란이 더 있었으면 ');
insert into DBCOURSE_Reviews values('0lucky0',3,21,4,'채식하시는 분들에게 적극추천');
insert into DBCOURSE_Reviews values('wowbb',3,21,5,'재료가 신선하고 맛있다');
insert into DBCOURSE_Reviews values('가나다라',4,72,5,'안가보신 분들 꼭 가세요');
insert into DBCOURSE_Reviews values('예약하기',4,32,5,'너무 맛있습니다.');
insert into DBCOURSE_Reviews values('아이디없음',4,11,3,'맛은있으나 직원이 불친절');
insert into DBCOURSE_Reviews values('중복된아이디',5,71,4,'그럭저럭 무난한 오믈렛');
insert into DBCOURSE_Reviews values('xyz7584',5,52,3,'분위기는 좋은데 맛은..ㅋ..');
insert into DBCOURSE_Reviews values('dbdb',6,32,4,'덮밥, 돈까스 양 많고 좋다	');
insert into DBCOURSE_Reviews values('apple7',6,12,5,'맛나는 이자카야! 사장님 인심 좋음');
insert into DBCOURSE_Reviews values('popup',6,22,3,'재방문은 굳이 안할거 같아요');
insert into DBCOURSE_Reviews values('noerror',7,21,5,'점심식사 한끼로 딱!');
insert into DBCOURSE_Reviews values('isee',7,51,5,'혼밥하기 좋아요.');
insert into DBCOURSE_Reviews values('오늘은공휴일',7,52,4,'깔끔한데 여는 날이 너무 랜덤..');
insert into DBCOURSE_Reviews values('저녁뭐먹지',7,42,2,'맛있는데 갈때마다 문 닫혀 있어서 짜증..');
insert into DBCOURSE_Reviews values('asdjfoaiwe',8,21,5,'정통 일본 라멘을 맛보고 싶다면 이곳으로!');
insert into DBCOURSE_Reviews values('없음',8,32,4, NULL);
insert into DBCOURSE_Reviews values('happy19',9,21,5,'가격도 싸고 맛도 기본에 충실하다 할 수 있다. 맛있다!');
insert into DBCOURSE_Reviews values('숫자9999',9,22,3,'직원이 너무 불친절하고, 음식이 짜다');
insert into DBCOURSE_Reviews values('popo',10,32,4,'맛도 있고 인테리어 굿인데 여자 직원분이 무서워요..');
insert into DBCOURSE_Reviews values('기차소리',10,41,5,'웨이팅 20분이었지만 맛있음');
insert into DBCOURSE_Reviews values('아무거나',10,42,5,'한국화된 느낌?! 해장으로 좋을 것 같다.');

/*Insert INGREDIENT*/
insert into  DBCOURSE_ingredients values(0101,'샤케동정식','연어','쌀','N');
insert into  DBCOURSE_ingredients values(0102,'부타노쇼가야키정식','돼지고기','밀가루','N');

insert into  DBCOURSE_ingredients values(0201,'치킨라이스','닭고기','쌀','N');
insert into  DBCOURSE_ingredients values(0202,'치킨누들','닭고기','밀가루','N');

insert into  DBCOURSE_ingredients values(0301,'두부 샐러디','두부','채소','Y');
insert into  DBCOURSE_ingredients values(0302,'에그 베이컨 랩','계란','채소','N');

insert into  DBCOURSE_ingredients values(0401,'사천 꿔바로우','돼지고기','밀가루','N');
insert into  DBCOURSE_ingredients values(0402,'해선도삭면','해산물','밀가루','N');

insert into  DBCOURSE_ingredients values(0501,'크림치즈 오믈렛','계란','크림치즈','Y');
insert into  DBCOURSE_ingredients values(0502,'와플','밀가루','메이플 시럽','Y');

insert into  DBCOURSE_ingredients values(0601,'사케동','연어','쌀','N');
insert into  DBCOURSE_ingredients values(0602,'일본식 돈까스','돼지고기','튀김가루','N');

insert into  DBCOURSE_ingredients values(0701,'돈까스김치나베','돼지고기','김치','N');
insert into  DBCOURSE_ingredients values(0702,'치킨김치나베','닭고기','김치','N');

insert into  DBCOURSE_ingredients values(0801,'돈코츠라멘','돼지 뼈','밀가루','N');
insert into  DBCOURSE_ingredients values(0802,'돈코츠 차슈멘','돼지 뼈','밀가루','N');

insert into  DBCOURSE_ingredients values(0901,'뿌팟퐁커리','게','카레가루','N');
insert into  DBCOURSE_ingredients values(0902,'새우팟타이','새우','쌀국수 면','N');

insert into  DBCOURSE_ingredients values(1001,'차돌박이 쌀국수','차돌박이','쌀국수 면','N');
insert into  DBCOURSE_ingredients values(1002,'양지 쌀국수','양지','쌀국수 면','N');

/*Insert MENU*/
insert into  DBCOURSE_menu values(1,0101,'샤케동정식',9000);
insert into  DBCOURSE_menu values(1,0102,'부타노쇼가야키정식',9000);

insert into  DBCOURSE_menu values(2,0201,'치킨라이스',7000);
insert into  DBCOURSE_menu values(2,0202,'치킨누들',7000);

insert into  DBCOURSE_menu values(3,0301,'두부 샐러디',4400);
insert into  DBCOURSE_menu values(3,0302,'에그 베이컨 랩',5000);
            
insert into  DBCOURSE_menu values(4,0401,'사천 꿔바로우',15000);
insert into  DBCOURSE_menu values(4,0402,'해선도삭면',7000);
            
insert into  DBCOURSE_menu values(5,0501,'크림치즈 오믈렛',10000);
insert into  DBCOURSE_menu values(5,0502,'와플',13000);

insert into  DBCOURSE_menu values(6,0601,'사케동',13000);
insert into  DBCOURSE_menu values(6,0602,'일본식돈까스',9000);

insert into  DBCOURSE_menu values(7,0701,'돈까스김치나베',8000);
insert into  DBCOURSE_menu values(7,0702,'치킨김치나베',8000);

insert into  DBCOURSE_menu values(8,0801,'돈코츠라멘',7000);
insert into  DBCOURSE_menu values(8,0802,'돈코츠 차슈멘',9000);

insert into  DBCOURSE_menu values(9,0901,'뿌팟퐁커리',13500);
insert into  DBCOURSE_menu values(9,0902,'새우팟타이',7500);

insert into  DBCOURSE_menu values(10,1001,'차돌박이 쌀국수',8000);
insert into  DBCOURSE_menu values(10,1002,'양지 쌀국수',8500);

#view 만들기

use team12;

/*이한나*/
create view DBCOURSE_confirm_reservation_view
as select Booking_ID, Restaurant_Name, Date_Of_Event, Time_Of_Event, Phone_Number
from DBCOURSE_Restaurants natural join (DBCOURSE_Bookings natural join DBCOURSE_BOOKED_Customers);

/*이해빈*/
create view  DBCOURSE_MENU_INFO_VIEW
as select Menu_Id,Restaurant_ID,Representative_Food,Price,main_ingredient,sub_ingredient,Vegetarian_YN
from DBCOURSE_Menu natural join DBCOURSE_Ingredients;

/*이해빈*/
create view DBCOURSE_RESTAURANT_INFO_VIEW
as select* from dbcourse_restaurants natural join dbcourse_access_route;

#권한 부여 (이한나)

create user 'team12'@'localhost' identified by 'team12';
grant all privileges on team12.* to 'team12'@'localhost';
flush privileges;

    
