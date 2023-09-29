<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <h1>와이파이 정보</h1>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/wifi/v2" class="a-btn">홈</a></li>
            <li>|</li>
            <li><a href="#" class="a-btn">위치 히스토리 목록</a></li>
            <li>|</li>
            <li><a href="${pageContext.request.contextPath}/wifi/load" class="a-btn">Open API 와이파이 정보 가져오기</a></li>
            <li>|</li>
            <li><a href="#" class="a-btn">북마크 보기</a></li>
            <li>|</li>
            <li><a href="${pageContext.request.contextPath}/bookmarkGroup" class="a-btn">북마크 그룹 관리</a></li>
        </ul>
    </nav>
    <ul class="sub-nav">
        <li>
            <select name="bookmarkGroup">
                <option>북마크 그룹 선택</option>
                <c:if test="${!bookmarkGroups.isEmpty()}">
                    <c:forEach var="item" items="${bookmarkGroups}">
                        <option value="${item.name}">${item.name}</option>
                    </c:forEach>
                </c:if>
            </select>
        </li>
        <li>
            <button>북마크 추가하기</button>
        </li>
    </ul>
    <table>
        <tbody>
            <c:if test="${wifi != null}">
                <tr>
                    <td>거리(Km)</td>
                    <td>0.000</td>
                </tr>
                <tr>
                    <td>관리 번호</td>
                    <td>${wifi.mgmtNum}</td>
                </tr>
                <tr>
                    <td>자치구</td>
                    <td>${wifi.borough}</td>
                </tr>
                <tr>
                    <td>와이파이명</td>
                    <td>${wifi.wifiName}</td>
                </tr>
                <tr>
                    <td>도로명 주소</td>
                    <td>${wifi.address}</td>
                </tr>
                <tr>
                    <td>상세 주소</td>
                    <td>${wifi.addrDetail}</td>
                </tr>
                <tr>
                    <td>설치 위치</td>
                    <td>${wifi.installLoc}</td>
                </tr>
                <tr>
                    <td>설치 유형</td>
                    <td>${wifi.installType}</td>
                </tr>
                <tr>
                    <td>설치 기관</td>
                    <td>${wifi.installAgency}</td>
                </tr>
                <tr>
                    <td>서비스 구분</td>
                    <td>${wifi.serviceType}</td>
                </tr>
                <tr>
                    <td>망 종류</td>
                    <td>${wifi.netType}</td>
                </tr>
                <tr>
                    <td>설치 년도</td>
                    <td>${wifi.installYear}</td>
                </tr>
                <tr>
                    <td>실외 구분</td>
                    <td>${wifi.inoutDoor}</td>
                </tr>
                <tr>
                    <td>와이파이 접속 환경</td>
                    <td>${wifi.wifiConnEnv}</td>
                </tr>
                <tr>
                    <td>X좌표</td>
                    <td>${wifi.x}</td>
                </tr>
                <tr>
                    <td>Y좌표</td>
                    <td>${wifi.y}</td>
                </tr>
                <tr>
                    <td>작업일자</td>
                    <td>${wifi.workDate}</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>

<style>
    ul, li {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    nav {
        padding-bottom: 1em;
    }

    nav ul {
        display: flex;
    }

    nav ul li {
        padding: 0 .5em;
    }

    .a-btn {
        text-decoration: underline;
    }

    .sub-nav {
        display: flex;
        padding-bottom: 1em;
    }

    .sub-nav li {
        padding: 0 .5em;
    }

    table {
        border: 1px solid #fff;
        border-collapse: collapse;
        width: 100%;
    }

    tbody tr td:first-child {
        background-color: #04AA6D;
        font-weight: bold;
        color: #fff;
        width: 35%;
    }

    tbody tr td:last-child {
        text-align: left;
    }

    tbody tr:nth-child(2n) {
        background-color: #E7E9EB;
    }

    thead td {
        border-color: #fff;
    }

    td {
        padding: 1em;
        text-align: center;
        border: 1px solid #ddd;
    }
</style>
</html>
