# BookWave 프로젝트

**Spring Boot**, **JSP**, **MySQL**을 이용한 웹 기반의 **온라인 도서관 관리 시스템**

## 프로젝트 개요

BookWave 프로젝트는 온라인 도서관 시스템으로, 도서 검색, 대출, 예약 등의 기능을 제공하는 웹 기반 서비스입니다. 실시간 예약 및 대출 시스템을 구현하였으며, eBook 및 포인트 시스템을 통해 다양한 기능을 지원합니다.

- **프로젝트 URL**: [BookWave GitHub](https://github.com/kyeonghooon/bookwave)
- **노션 URL**: [BookWave Notion](https://bookwave.notion.site)
---

## 목차

1. [프로젝트 개요](#프로젝트-개요)
2. [팀 구성 및 역할](#팀-구성-및-역할)
3. [서비스 환경](#서비스-환경)
4. [사용 라이브러리 및 외부 API](#사용-라이브러리-및-외부-api)
5. [사이트맵](#사이트맵)
6. [ERD 다이어그램](#ERD-다이어그램)
7. [주요 기능 소개](#주요-기능-소개)

---

## 팀 구성 및 역할

| 이름   | 역할     | 담당 기능                                           |
|--------|----------|---------------------------------------------------|
| **강경훈** | 팀장     | 리더, eBook 기능, 이메일 인증 서비스 구축, 홈 화면 및 레이아웃, 시설 관련 기능, 포인트 소비 기능 |
| **김민경** | 팀원     | 소셜 로그인 API 연동, 일반 로그인 및 회원가입, 아이디/패스워드 찾기 기능        |
| **방민석** | 팀원     | 예약 및 대출 기능, 리뷰 CRUD, 읽은 내역 조회                        |
| **석지웅** | 팀원     | 관리자 페이지 기능 구현, 포인트 결제 API 연동                      |
| **서치원** | 팀원     | 도서 목록 API 파싱, 도서 조회 기능, 회원 정보 수정, 포인트 내역 조회           |

---

## 서비스 환경

- **OS**: Windows 10
- **브라우저**: Chrome 121.0, Edge 129.0
- **백엔드**: Spring Boot, MySQL, H2, MyBatis
- **프론트엔드**: JSP, JSTL, Chart.js, Epub.js
- **커뮤니케이션 도구**: Discord, Notion
- **버전 관리**: GitHub, GitBash

---

## 사용 라이브러리 및 외부 API

### 사용 라이브러리

- **Lombok**: 간편한 생성자 및 메서드 사용
- **MySQL Connector**: Java와 MySQL을 연결한 효율적인 DB 사용
- **Jakarta Servlet JSP JSTL API**: JSP 페이지에서 간편한 코드 작성
- **Spring Security**: 간편한 인증 및 보안 처리
- **Spring WebSocket**: 전이중 및 양방향 통신 제공
- **Chart.js**: 데이터 시각화 도구
- **Epub.js**: ePub 파일 렌더링

### 외부 API

| 기능         | API 명          | 설명                      |
|--------------|----------------|---------------------------|
| 로그인         | 카카오/네이버/구글 로그인 | 소셜 로그인 API 연동          |
| 이메일 인증      | Gmail STMP     | 사용자 이메일 인증             |
| 결제          | 토스 페이먼츠       | 사용자 결제 승인 및 취소 처리     |

---

## 사이트맵

### 유저용 사이트맵 
![image](https://github.com/user-attachments/assets/18017028-db44-454f-94bb-b6642d21f42f)

### 관리자용 사이트맵
![image](https://github.com/user-attachments/assets/a11165b7-3531-4370-9806-0cfe991e6f8e)

---

## ERD 다이어그램
![image](https://github.com/user-attachments/assets/86e1a508-b1bf-438c-873d-36ff83c62e44)
![image](https://github.com/user-attachments/assets/2201c137-08e5-4452-a771-fa8e3dd0247b)

---

## 주요 기능 소개
[파이널 2조 BookWave 프로젝트 보고서.pdf](https://github.com/user-attachments/files/17275008/2.BookWave.pdf)

---
