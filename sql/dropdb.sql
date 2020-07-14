#사용자 제거 (이한나)

drop user if exists 'team12'@'localhost';
flush privileges;

#데이터베이스 제거 및 확인 (이한나)

drop database team12;
show databases;
