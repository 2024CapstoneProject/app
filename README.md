# app



## 1. Commit 

- 타입

|타입 |설명|
|-|-|
|FEAT |새로운 기능 구현 |
|REFACTOR | 리팩토링|
|FIX |버그 및 오류 해결 |
|DOCS | README나 문서 업로드|

- 커밋 메시지 구조 

  - 제목
   	 - 작업 제목 
    - 마침표 작성 X, 간단하게 작성할 것  
  
  - 본문(선택사항)
    - 작업 내용
  
  - 꼬리말
    - 이슈 번호 
 
   - 예시
  ```
    feat : 화면 스크롤 개발

     - 스크롤 생성

     Closes #1 
   ```
## 2. Issue  
- Issue 구조
  - 제목 
    - 작업 제목
    - 현재 개발하고 있는 기능/수정하고 있는 오류를 한줄로 작성 
  - 본문
    - 작업할 내용 간단 소개
- 예시
```
Title : 회원가입 기능
Description : 작업 내용 
``` 
- 설정
  - 담당자(Assigness) 명시
  - Labels 설정
  - Project 연결
    - Issue 생성 뒤 Status를 In Progress로 설정 
- 주의
  - 꼭 Merge 후 Issue Close 할 것 

## 3. PR
- PR 구조
  - 제목
    - 작업 제목
    - [#Issue번호] 작업하는 기능
  - 본문
    - 작업 내용 설명
   
 - 설정
   - 담당자(Assigness) 명시
   - Labels 설정 
   - Development에서 Issue 연결
     - PR 생성 후 진행
 - 주의
   - Merge 후 브랜치는 삭제 

## 4. 브랜치명
- 구조
  - type/#이슈번호-작업내용
  - 예시
    - feat/#1-create-cafe
- 주의
  - Merge나 새로운 브랜치 clone는 master 브랜치 이용
    
