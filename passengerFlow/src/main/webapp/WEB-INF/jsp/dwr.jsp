<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'dwrReverse.jsp' starting page</title>
<script type='text/javascript' src='<%=basePath%>/dwr/engine.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/util.js'></script>
<script type='text/javascript' src='<%=basePath%>/dwr/interface/RealTimeReverse.js'></script>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<script type="text/javascript">
	function InitReverseAjax()
	{
		dwr.engine.setActiveReverseAjax(true);
		dwr.engine.setNotifyServerOnPageUnload(true);
		RealTimeReverse.onPageLoad(<%=request.getParameter("eqptId")%>);
	}
	
	function showMessages(message)
	{
		document.getElementById('msgShowText').value=message;
	}
</script>
</head>
<!-- 注：这个是要在使用reverse-ajax的页面必须的 -->
<body onload="InitReverseAjax()">
	点击查看服务器时间
	<input type="button" value="Start/Stop" onclick="RealTimeReverse.toggle();" />
	<br><br>
	<h2 id="clockDisplay"></h2>
	<input type="text" name="PointValue">
	<br><br>
	<input type="text" style="width: 496px" id="msgShowText">
</body>

</html>