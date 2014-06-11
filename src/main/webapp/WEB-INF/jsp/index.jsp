<%@ page import="java.net.URLEncoder" %>
<%@ page import="tbac.util.TokenUtil" %>
<html>
<head>
  <title></title>
</head>
<body>
  <img src="/images/secret.png?token=<%= URLEncoder.encode(TokenUtil.createToken(request, "/images/secret.png"), "UTF-8")%>"/>
</body>
</html>
