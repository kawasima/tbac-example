<html>
<head>
  <title></title>
</head>
<body>
  <img src="/images/secret.png?token=<%= java.net.URLEncoder.encode((String)request.getAttribute("token"), "UTF-8")%>"/>
</body>
</html>
