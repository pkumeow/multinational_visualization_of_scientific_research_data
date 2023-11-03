<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*" %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type:"text/css" href="./css/hubpage.css?rand=4">
<meta charset="utf-8">
<title>数据管理系统</title>
</head>
<body>

<div class="header">
  <h1>王昭睿 Java 大作业</h1>
</div>

<div class="container"> 
  <div class="item"> </div>
  <div class="item"> <a href="test_addlevel1index.jsp"> 添加一个一级指标 </a> <br/> </div>
  <div class="item"> <a href="test_addlevel2index.jsp"> 为已存在的一级指标添加一个二级指标 </a> <br/> </div>
  <div class="item"> <a href="test_deleteindex.jsp"> 删除一个指标 </a> <br/> </div>
  <div class="item"> <a href="test_changeindexname.jsp"> 修改一个指标的名字 </a> <br/> </div>
  <div class="item"> <a href="test_addlevel2value.jsp"> 添加一个二级指标的值 </a> <br/> </div>
  <div class="item"> <a href="test_deletelevel2value.jsp"> 删除一个二级指标的值 </a> <br/> </div>
  <div class="item"> <a href="test_modifylevel2value.jsp"> 修改一个二级指标的值 </a> <br/> </div>
  <div class="item"> <a href="main.jsp"> 返回 </a> <br/> </div>
</div>

</body>
</html>