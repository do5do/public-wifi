<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script>
    async function saveBookmarkGroup() {
        let form = document.forms.saveBookmarkGroup;
        form.submit();
    }
</script>
<body>
    <h1>북마크 그룹 수정</h1>
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
    <form method="post" action="${pageContext.request.contextPath}/bookmarkGroup/update">
        <table>
            <tbody>
            <tr>
                <td>북마크 이름</td>
                <td><input type="text" name="name" value="${bookmarkGroup.name}"></td>
            </tr>
            <tr>
                <td>순서</td>
                <td><input type="number" name="seq" value="${bookmarkGroup.seq}"></td>
            </tr>
            <tr>
                <td colspan="2" style="background-color: #fff; text-align: center">
                    <input type="hidden" name="bgno" value="${bookmarkGroup.bgno}">
                    <a href="${pageContext.request.contextPath}/bookmarkGroup">돌아가기</a>
                    <button type="submit">수정</button>
                </td>
            </tr>
            </tbody>
        </table>
    </form>
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
