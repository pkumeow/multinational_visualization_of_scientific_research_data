<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>添加一个二级指标的值</title>
<script src = "./js/jquery-3.6.3.min.js"></script>
<style>
    #b{
        width: 500px;
        text-align: right;
    }
</style>
<script>
	function callservlet() {
		$.ajax({
			type: "POST",
			url: "TestAddLevel2Value",
			data: $('#fm').serialize(),
			dataType: "text",
			success: function(result) {
				document.getElementById("resDiv").innerHTML = result;
			},
			error: function(result) {
				document.getElementById("resDiv").innerHTML = result;
			}
		})
	}
</script>
</head>
<body>
	<h1> 添加一个二级指标的值 </h1>
	<div id = "b">
		<form action="" method="POST" id="fm">
			二级指标名: <input type="text" id="name" name = "name" />
			<br/>
			年份: <input type="text" id="year" name = "year"/>
			<br/>
			国家: <input type="text" id="country" name = "country"/>
			<br/>
			值: <input type="text" id="value" name = "value"/>
			<br/>
			<button type="button" onclick = "callservlet()"> 提交</button>
		</form>
		    <br/>
	</div>
	<div id="resDiv"></div>
	<br/><a href="hub.jsp">返回</a><br/>
</body>
</html>