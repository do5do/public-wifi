## 서울시 공공 와이파이 검색 서비스

### 기능 요구 사항
1. 위치 기반 서울시 공공 와이파이 검색
   - 내 위치를 기준으로 공공 와이파이 정보를 가까운 순으로 Top 20개의 목록을 조회할 수 있습니다.
2. 와이파이 북마크
   - 검색한 와이파이를 북마크 할 수 있습니다.

### 개발 환경
- Java 17
- Servlet
- Jsp / Jstl
- Tomcat 10.1.13
- Sqlite Database

### 프로젝트 구조
~~~
src
├── main
│   ├── generated
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── publicwifi
│   │               ├── bookmark
│   │               │   ├── Bookmark.java
│   │               │   ├── BookmarkDao.java
│   │               │   ├── BookmarkService.java
│   │               │   └── servlet
│   │               │       ├── BookmarkDeleteServlet.java
│   │               │       ├── BookmarkListServlet.java
│   │               │       └── BookmarkSaveServlet.java
│   │               ├── bookmarkGroup
│   │               │   ├── BookmarkGroup.java
│   │               │   ├── BookmarkGroupDao.java
│   │               │   ├── BookmarkGroupService.java
│   │               │   └── servlet
│   │               │       ├── BookmarkGroupDeleteServlet.java
│   │               │       ├── BookmarkGroupEditServlet.java
│   │               │       ├── BookmarkGroupFormServlet.java
│   │               │       ├── BookmarkGroupListServlet.java
│   │               │       ├── BookmarkGroupSaveServlet.java
│   │               │       └── BookmarkGroupUpdateServlet.java
│   │               ├── locHistory
│   │               │   ├── LocHistory.java
│   │               │   ├── LocHistoryDao.java
│   │               │   ├── LocHistoryService.java
│   │               │   └── servlet
│   │               │       ├── LocHistoryDeleteServlet.java
│   │               │       ├── LocHistoryListServlet.java
│   │               │       └── LocHistorySaveServlet.java
│   │               ├── util
│   │               │   ├── ConnectionManager.java
│   │               │   ├── Page.java
│   │               │   └── Pageable.java
│   │               └── wifi
│   │                   ├── Wifi.java
│   │                   ├── WifiDao.java
│   │                   ├── WifiService.java
│   │                   ├── dto
│   │                   │   ├── AddressDto.java
│   │                   │   ├── KakaoResponseDto.java
│   │                   │   └── WifiResponseDto.java
│   │                   └── servlet
│   │                       ├── WifiDbListServlet.java
│   │                       ├── WifiDetailServlet.java
│   │                       ├── WifiListServlet.java
│   │                       └── WifiSaveServlet.java
│   ├── resources
│   │   ├── META-INF
│   │   │   └── beans.xml
│   │   └── publicWifi.sql
│   └── webapp
│       ├── WEB-INF
│       │   ├── views
│       │   │   ├── bookmark
│       │   │   │   ├── bookmarkList.jsp
│       │   │   │   └── bookmarkResult.jsp
│       │   │   ├── bookmarkGroup
│       │   │   │   ├── bookmarkGroupEditForm.jsp
│       │   │   │   ├── bookmarkGroupForm.jsp
│       │   │   │   ├── bookmarkGroupList.jsp
│       │   │   │   └── bookmarkGroupResult.jsp
│       │   │   ├── locHistory
│       │   │   │   ├── locHistoryList.jsp
│       │   │   │   └── locHistoryResult.jsp
│       │   │   └── wifi
│       │   │       ├── wifiDetail.jsp
│       │   │       ├── wifiList.jsp
│       │   │       └── wifiLoad.jsp
│       │   └── web.xml
│       └── index.jsp
└── test
    ├── java
    │   └── com
    │       └── example
    │           └── publicwifi
    │               └── wifi
    │                   └── WifiServiceTest.java
    └── resources
~~~

### 구현 설명
1. 와이파이 정보 가져오기
   - /wifi
     - 처음에 과제에 대한 이해가 부족하여 와이파이 정보 목록을 데이터베이스에 저장하지 않고 불러오는 방식으로 구현하였습니다.
     - 그 과정에서 데이터의 양이 많으니 페이지네이션 처리를 하였고, 페이지 별로 내 위치와 가까운 순으로 정렬됩니다.
   - /wifi/v2
     - 미리 DB에 저장해둔 데이터를 기반으로 불러오며, 내 위치와 가까운 순으로 최대 20개의 목록으로 정렬됩니다.

### 제출 파일
1. ERD
   - ERD Cloud(무료 웹 프로그램)를 사용하여 ERD를 생성하였습니다.
     - SQL 파일 외에 내려받을 수 있는 파일은 엑셀 파일 뿐이어서 ERD 파일은 링크로 첨부합니다. 
       - https://www.erdcloud.com/d/JWeDx6Qeke8hnFZdy
     - SQL 파일 (publicWifi.sql)
     - 캡쳐 이미지 (ERD_image.PNG)
     - <img width="890" alt="ERD_image" src="https://github.com/do5do/public-wifi/assets/56918540/9f1883ab-02a7-4dba-83d5-f387608ff3d3">
2. 데모 영상 (공공와이파이_데모영상)
   - 참고) 현재 지역이 서울이 아니라서 특정 좌표 정보를 미리 넣어두고 실행하였습니다.
3. README.md
   - github repository의 README와 동일합니다.
