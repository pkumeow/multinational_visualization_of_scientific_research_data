<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>删除一个二级指标的值</title>
<script src = "./js/jquery-3.6.3.min.js"></script>
	<style>
        #t{
            width: 500px;
            text-align: right;
        }
    </style>
	<script>
		function callservlet() {
			$.ajax({
				type: "POST",
				url: "TestDeleteLevel2Value",
				data: $('#fm').serialize(),
				dataType: "text",
				success: function(result) {
					document.getElementById("resDiv").innerHTML = result;
				},
				error: function() {
					document.getElementById("resDiv").innerHTML = "failed";
				}
			})
		}
	</script>
</head>
<body>
	<h1> 删除一个二级指标的值 </h1>
	<div id = "t">
		<form action="TestDeleteLevel2Value" method="POST" id="fm">
			二级指标名: <input type="text" id="name" name="name" />
			<br/>
			年份: <input type="text" id="year" name="year" />
			<br/>
			国家: <input type="text" id="country" name="country" />
			<br/>
			<button type="button" onclick = "callservlet()"> 提交</button>
		</form>
	</div>
	<div id="resDiv"></div>
	<br/><a href="hub.jsp">返回</a><br/>
</body>
</html>