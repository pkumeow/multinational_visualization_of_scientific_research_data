<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 可视化界面 </title>
<script src = "./js/echarts.min.js"></script>
<script src = "./js/jquery-3.6.3.min.js"></script>
<script src = "./js/echartsoption.js?rand=55"></script>
<script src = "./js/visualizeajax.js?rand=11225"></script>
<link rel="stylesheet" href="./css/vis.css?rand=11">
</head>
<body>
<div id = "visual" style = "width:1350px; height:600px"> </div>

<form id = "thisForm" method = "POST" action="" class = "container">

<select name="year" id="year" class="select_style">
<option value=null></option>
<option value="2019">2019</option>
<option value="2020">2020</option>
<option value="2021">2021</option>
</select>
<br/>

<select name="country" id="country" class="select_style">
<option value=null></option>
<option value="CN">中国</option>
<option value="US">美国</option>
<option value="GB">英国</option>
<option value="FR">法国</option>
<option value="RU">俄罗斯</option>
</select>
<br/>

<select name="name" id="name" class="select_style">
<option value=null></option>
</select>
<br/>
<button type="button" id="thissubmit" data-title="提交"></button>
<br/>
<button type="button" id="recreate" data-title="切换风格"></button>
<br/>
</form>

<a href="main.jsp">返回</a>
	

<script>
	window.onload = function() {
		$.ajax({
			async: false,
			type: "GET",
			url: "FeedSelect",
			data: "",
			dataType: "json",
			success: function(result) {
				var map = eval(result);
				$.each(map, function(key, value) {
					$("<option value="+value+">"+key+"</option>").appendTo("#name");
				});
			}
		})
		//指标名是从数据库里查出来的, 并不是写死在 html 里, 所以进入页面就要调用一个 ajax 渲染下拉框
	}
	$("#thissubmit").css("visibility", "hidden");
	$("#recreate").css("visibility", "hidden");
	var clicked_submit = false;
	$("#name").change(function() {
		Test();
	})
	$("#country").change(function() {
		Test();
	})
	$("#year").change(function() {
		Test();
	})
	//用户选了可视化方式时才让用户点击按钮生成图表
	
	var preference_bar = true;
    $("#recreate").click(function() {
    	preference_bar = !preference_bar;
		vissetting();
    });
    //在国家名和年份都是 NULL 的时候, 支持用户切换条图和线图
    
	$("#thissubmit").click(function() {
		clicked_submit = true;
		Test();
		vissetting();
	});
    //submit 用了很多 if 渲染图表, 难以调试, 扔到别的地方
</script>
</body>
</html>