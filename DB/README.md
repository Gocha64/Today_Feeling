리눅스 도커세팅

1. 도커 설치  
yum update -y
yum install docker -y

2. 설치 확인  
docker -v

3. 도커 시작  
sudo service docker start

리눅스 DB세팅

1. mysql pull  
docker pull mysql

2. mysql 실행  
docker run --name <컨테이너명> -e MYSQL_ROOT_PASSWORD=<비밀번호> -d -p <포트번호>:<포트번호> -v <볼륨명>:<볼륨경로> <이미지명>

3. mysql 접속  
docker exec -it  
mysql -u root -p

DB세팅
1. TIMEZONE 설정  
SET GLOBAL time_zone='Asia/Seoul';  
SET time_zone='Asia/Seoul';

2. TIME확인  
SELECT NOW();

DB 보안이슈

비밀번호 어려운거 쓰기(1234같은거 쓰지말기)  
well-known포트 지양하기(3306말고 다른거 쓰기)
