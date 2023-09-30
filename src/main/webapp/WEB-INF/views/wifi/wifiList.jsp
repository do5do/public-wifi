<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
</head>
<script>
    async function loadWifiList() {
        let lnt = document.getElementById("lnt");
        let lat = document.getElementById("lat");

        if (lnt.value === "0.0" && lat.value === "0.0") {
            alert("위치 정보를 입력한 후에 조회해 주세요.")
            return;
        }

        // top 20 from db
        let url = "${pageContext.request.contextPath}/wifi/v2?lnt=" + lnt.value + "&lat=" + lat.value;
        if (${type.equals("open")}) {
            // api pagination
            url = "${pageContext.request.contextPath}/wifi?page=1&lnt=" + lnt.value + "&lat=" + lat.value;
        }

        await getWifiList(url)
            .then(text => {
                let parser = new DOMParser();
                let doc = parser.parseFromString(text, "text/html")
                document.body.innerHTML = doc.body.innerHTML
            })
            .catch(err => {
                alert(err)
            })
    }

    function getWifiList(url) {
        return new Promise(resolve => {
            fetch(url)
                .then((response) => {
                    let text = response.text()
                    resolve(text)
                })
        })
    }

    function getMyLocation() {
        let status = document.getElementById("status");
        let lnt = document.getElementById("lnt"); // x
        let lat = document.getElementById("lat"); // y

        let options = {
            enableHighAccuracy : true,
            timeout : 5000,
            maximumAge : 0
        };

        async function success(position) {
            let x = position.coords.longitude;
            let y = position.coords.latitude;
            lnt.value = x;
            lat.value = y;
            status.textContent = "";

            // 위치 정보 저장
            const url = "${pageContext.request.contextPath}/locHistory/save?lnt=" + x + "&lat=" + y;
            await fetch(url)
                .catch(err => console.log(err));
        }

        function error() {
            status.textContent = "현재 위치를 가져올 수 없습니다.";
        }

        if (!navigator.geolocation) {
            status.textContent = "브라우저의 위치 정보 권한 설정이 필요합니다.";
        } else {
            status.textContent = "위치 찾는 중...";
            navigator.geolocation.getCurrentPosition(success, error, options);
        }
    }
</script>

<body>
    <h1>와이파이 정보 구하기</h1>
    <nav>
        <ul>
            <li><a href="${pageContext.request.contextPath}/wifi/v2" class="a-btn">홈</a></li>
            <li>|</li>
            <li><a href="${pageContext.request.contextPath}/locHistory" class="a-btn">위치 히스토리 목록</a></li>
            <li>|</li>
            <li><a href="${pageContext.request.contextPath}/wifi/load" class="a-btn">Open API 와이파이 정보 가져오기</a></li>
            <li>|</li>
            <li><a href="${pageContext.request.contextPath}/bookmark" class="a-btn">북마크 보기</a></li>
            <li>|</li>
            <li><a href="${pageContext.request.contextPath}/bookmarkGroup" class="a-btn">북마크 그룹 관리</a></li>
        </ul>
    </nav>
    <ul class="sub-nav">
        <li>
            <label for="lnt">LNT : </label>
            <c:if test="${lnt == null}">
                <input type="number" id="lnt" value="0.0">
            </c:if>
            <c:if test="${lnt != null}">
                <input type="number" id="lnt" value="${lnt}">
            </c:if>
        </li>
        <li>
            <label for="lat">LAT : </label>
            <c:if test="${lat == null}">
                <input type="number" id="lat" value="0.0">
            </c:if>
            <c:if test="${lat != null}">
                <input type="number" id="lat" value="${lat}">
            </c:if>
        </li>
        <li>
            <button type="button" onclick="getMyLocation()">내 위치 가져오기</button>
            <span id="status"></span>
        </li>
        <li>
            <button type="submit" onclick="loadWifiList()">근처 WIFI 정보 보기</button>
        </li>
    </ul>
    <table>
        <thead>
            <tr>
                <td>거리(km)</td>
                <td>관리번호</td>
                <td>자치구</td>
                <td>와이파이명</td>
                <td>도로명주소</td>
                <td>상세주소</td>
                <td>설치위치(층)</td>
                <td>설치유형</td>
                <td>설치기관</td>
                <td>서비스구분</td>
                <td>망종류</td>
                <td>설치년도</td>
                <td>실내외구분</td>
                <td>WIFI접속환경</td>
                <td>X좌표</td>
                <td>Y좌표</td>
                <td>작업일자</td>
            </tr>
        </thead>
        <tbody>
        <c:if test="${wifiList == null}">
            <tr>
                <td colspan="17">위치 정보를 입력한 후에 조회해 주세요.</td>
            </tr>
        </c:if>
        <c:if test="${wifiList != null}">
            <c:if test="${wifiList.isEmpty()}">
                <tr>
                    <td colspan="17">해당 위치에 대한 데이터가 없습니다.</td>
                </tr>
            </c:if>
            <c:forEach var="item" items="${wifiList}">
                <tr>
                    <td>${item.distance}</td>
                    <td>${item.mgmtNum}</td>
                    <td>${item.borough}</td>
                    <td><a href="${pageContext.request.contextPath}/wifi/detail?mgmtNum=${item.mgmtNum}">${item.wifiName}</a></td>
                    <td>${item.address}</td>
                    <td>${item.addrDetail}</td>
                    <td>${item.installLoc}</td>
                    <td>${item.installType}</td>
                    <td>${item.installAgency}</td>
                    <td>${item.serviceType}</td>
                    <td>${item.netType}</td>
                    <td>${item.installYear}</td>
                    <td>${item.inoutDoor}</td>
                    <td>${item.wifiConnEnv}</td>
                    <td>${item.x}</td>
                    <td>${item.y}</td>
                    <td>${item.workDate}</td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </table>

    <c:if test="${pageable != null}">
        <ul class="pagination">
            <c:if test="${pageable.startPage > pageable.pagePerBlock}">
                <li><a href="wifi?page=1&lnt=${lnt}&lat=${lat}" class="a-btn">처음</a></li>
                <li><a href="wifi?page=${pageable.startPage - 1}&lnt=${lnt}&lat=${lat}" class="a-btn">이전</a></li>
            </c:if>
            <c:forEach var="i" begin="${pageable.startPage}" end="${pageable.endPage}">
                <c:if test="${pageable.currentPage == i}">
                    <li><a href="wifi?page=${i}&lnt=${lnt}&lat=${lat}" class="active">${i}</a></li>
                </c:if>
                <c:if test="${pageable.currentPage != i}">
                    <li><a href="wifi?page=${i}&lnt=${lnt}&lat=${lat}">${i}</a></li>
                </c:if>
            </c:forEach>
            <c:if test="${pageable.endPage < pageable.totalPages}">
                <li><a href="wifi?page=${pageable.endPage + 1}&lnt=${lnt}&lat=${lat}" class="a-btn">다음</a></li>
                <li><a href="wifi?page=${pageable.totalPages}&lnt=${lnt}&lat=${lat}" class="a-btn">마지막</a></li>
            </c:if>
        </ul>
    </c:if>
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

    thead {
        background-color: #04AA6D;
        font-weight: bold;
        color: #fff;
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

    .pagination {
        display: flex;
        justify-content: center;
        margin: 1em 0;
    }

    .pagination li a {
        padding: .3em 1em;
    }

    .active {
        color: white;
        background-color: #04AA6D;
    }
</style>
</html>
