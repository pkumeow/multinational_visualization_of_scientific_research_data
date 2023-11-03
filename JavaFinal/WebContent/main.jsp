<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./css/mainpage.css?rand=31151115">
<script src = "./js/jquery-3.6.3.min.js"></script>
<meta charset="utf-8">
<title>国家科研能力展示系统</title>

<script> 
	window.onload = function() {
		main();
	}
	            
	function main() {
		const aButton = document.querySelector("button[id='chooseA']");
		const bButton = document.querySelector("button[id='chooseB']");
	    const aFile = document.querySelector("input[id='Afile']");
	    const bFile = document.querySelector("input[id='Bfile']");
	    aButton.onclick = function() { aFile.click(); }
	    bButton.onclick = function() { bFile.click(); }
	}
	
	$(function() {
		$("#submit").on("click", function() {
			var fd = new FormData();
			fd.append('Afile', $("#Afile")[0].files[0]);
			fd.append('Bfile', $("#Bfile")[0].files[0]);
			fd.append('path', $("#path").val());
			$.ajax({
				type:"POST",
				url:"Upload",
				data: fd,
				contentType: false,
				processData: false,
				success: function (data) {
					alert("上传成功!");
				},
				error: function() {
					alert("上传失败!");
				}
			})
		})
	})
		
</script>

</head>
<body>
<div class="header">
  <div class="item"><h1>国家科研能力展示系统——王昭睿期末作业</h1></div>
  <div class="header-container">
    <div class = "smallitem"> <input type="text" id="path"> 输入上传文件夹路径 </div>
    <div class = "smallitem"> <button type="button" data-title="选择论文数据" id="chooseA"></button> </div>
    <div class = "smallitem"> <button type="button" data-title="选择专利数据" id="chooseB"></button> </div>
    <div class = "smallitem"> <button type="button" data-title="上传" id="submit"></button> </div>
  </div>
</div>
<input type="file" id="Afile" style="display: none;"/>
<input type="file" id="Bfile" style="display: none;"/>

<div class="parent-container"> 
  <div class="child-container"> 
    <div class="item"> <img src="./imageSrc/testA.jpg?rand=10" width="560" height="270"></img></div>
    <div class="item"><a href="hub.jsp" data-title="点击进入数据管理"></a> </div> 
  </div> 
  <div class="child-container"> 
    <div class="item"> <img src="./imageSrc/testB.jpg" width="560" height="270"></img></div>
  	<div class="item"> <a href="autovis.jsp" data-title="点击进入数据可视化">  </a> <br/> </div>
  </div>
</div>
</body>
</html>