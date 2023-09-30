<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<script>
    alert("북마크 정보를 ${msg}하였습니다.")
    location.href = "${pageContext.request.contextPath}/bookmark";
</script>
<body>

</body>
</html>
