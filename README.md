# 토이 프로젝트

# 프로젝트 주제: ‘토스’ 앱의 주요 기능을 구현해보기

## 메인 홈페이지

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/38a9a14b-cec9-4410-b6ae-2bf01ef178fa/Untitled.png)

## 송금 페이지 1-1 (추천 계좌 ver)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1e2a98e5-5b35-437a-8df1-e2544b650036/Untitled.png)

## 송금 페이지 1-2 (직접 은행 & 계좌 입력 ver)

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/1dc47127-3afb-4a5e-bd58-0817e300dea4/Untitled.png)

## 송금 페이지 2

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/af74b0f1-bd16-4394-aa6b-a925dd649cf6/Untitled.png)

## 계좌 관리 페이지

![Untitled](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/f0713df6-4549-47d1-b3c5-9f68269e8c11/Untitled.png)

## 요구사항 정리

- 회원 기능 (Member)
    - 회원 등록
    - 회원 조회
    - 회원 수정
    - 송금 횟수가 10회가 넘으면 다음 송금부터는 수수료 생김
- 계좌 기능 (Account)
    - 계좌 등록
    - 계좌 조회
    - 다른 계좌와 송금 거래
    - 계좌의 은행에서 시행하는 이벤트에 참여 가능
- 은행 기능 (Bank)
    - 은행 등록 (애플리케이션 최초 실행 시점에만 사용하고 사용하지 않는 기능)
    - 은행 조회
    - 달마다 은행 별 금리에 맞춰 계좌에 금리 지급
- 행사 기능 (Event)
    - 행사 등록
    - 행사 조회
    - 행사 취소
- 거래 기능 (Transaction)
    - 거래 등록
    - 거래 조회
    - 거래 수정 (거래에 대한 메모와 카테고리만 수정이 가능하다)
- 기타 요구사항 & 요구사항 구체화
    - 계좌 관리(조회)를 하면 최신순으로 거래한 내역들이 모두 보여야함
    - 추가로 그 달의 내역을 달력에 표시하고 그 달에 가장 많이 소비한 카테고리 알려주기
    - 계좌의 role(VIP, NORMAL)에 따라 적용되는 금리가 달라야함
    - 프로젝트를 진행하면서 더 추가할 예정…

## 데이터베이스 다이어그램