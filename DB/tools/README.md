데이터 수집,관리 도구

crawler.py
유튜브 뮤직의 플레이리스트 감지 시 플레이리스트 구성요소의 url과 제목을 받아오고 사용자 입력을 받아 장르를 부여하는 프로그램

1. requirement  
크롬 브라우저  
유튜브 뮤직이 사용 가능한 유튜브 프리미엄 계정  

2. 사전 작업
win+r 후 'chrome.exe --remote-debugging-port=65489 --user-data-dir="C:/ChromeTEMP" 실행  
그 후 실행된 크롬 창에서 유튜브 뮤직이 사용 가능한 계정으로 로그인

3. 프로그램 사용
step1. 크롬 창에서 플레이리스트 접근  
step2. 프로그램이 값을 읽을 때까지 대기
step3. 장르 입력관련 출력시 장르 입력
step1-3의 반복

4. 프로그램 종료
step1. 아무 플레이리스트 접근
step2. 장르 입력 시 -1입력

5. 데이터 저장
폴더 내부의 data.xlsx에 저장됨