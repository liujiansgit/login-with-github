<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
hello world!!!
name=${name } welcome!


<a id="login">Login with GitHub</a>

<script>
    // （第三方网站注册的）客户端id
    client_id = '284077303de65f843231';
    // github 授权地址
    authorize_uri = 'https://github.com/login/oauth/authorize';
    // 回调网址 即本地地址
    redirect_uri = 'http://localhost:8080/oauth/redirect';
    link = document.getElementById('login');
    link.href = authorize_uri + '?' + 'client_id=' + client_id + '&redirect_uri=' + redirect_uri;
</script>

</body>

</html>