﻿<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="org.springframework.context.ApplicationContext"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String id=request.getParameter("id");                     //分类浏览

String online=request.getParameter("online");                 //在线用户

String type=request.getParameter("type");                 //根据类型搜索
String serachvalue=request.getParameter("serachvalue");   //搜索值

String checkboxs=request.getParameter("checkboxs");       //修改的ID
String edit_group=request.getParameter("edit_group");     //修改的类型

String deleteuser=request.getParameter("deleteuser");     //删除用户

String offuser=request.getParameter("offuser");           //踢掉用户


String checkbox=request.getParameter("foo");
System.out.println(checkbox);

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>管理系统</title>
<link href="../css/style.css" type=text/css rel=stylesheet>

<script type="text/javascript">
	
function nTabs(thisObj,Num){ 
if(thisObj.className == "active")return; 
var tabObj = thisObj.parentNode.id; 
var tabList = document.getElementById(tabObj).getElementsByTagName("li"); 
for(i=0; i <tabList.length; i++){ 
if (i == Num){ 
thisObj.className = "active"; 
document.getElementById(tabObj+"_Content"+i).style.display = "block"; 
}else{ 
tabList[i].className = "normal"; 
document.getElementById(tabObj+"_Content"+i).style.display = "none"; 
} 
} 
} 

function showuser(id){       //显示用户
if(id==0){
document.forms[0].action="../pages/user1.jsp?id=0";
document.forms[0].submit();
}else if(id==1){ 
document.forms[0].action="../pages/user1.jsp?id=1";
document.forms[0].submit();
}else if(id==2){
document.forms[0].action="../pages/user1.jsp?id=2";
document.forms[0].submit();
}else if(id==3){
document.forms[0].action="../pages/user1.jsp?id=3";
document.forms[0].submit();
}else if(id==4){         //显示在线用户
document.forms[0].action="../pages/user1.jsp?online=online";
document.forms[0].submit();
}
}


function offuser(){      //踢掉用户
var checkboxs=document.getElementsByName("checkbox");        //选择的用户
var users="";
for(i=0;i<checkboxs.length;i++){
    if(checkboxs[i].checked==true){
    users+=checkboxs[i].value+",";
    }
}
document.forms[0].action="../pages/user1.jsp?offuser="+users;
document.forms[0].submit();
}


function serach(){     //搜索用户
var type=document.getElementById("serachuser").value;      //动作
var serachvalue=document.getElementById("serachvalue").value;  //值
if(serachvalue==""||serachvalue==null){
return;
}else{
document.forms[0].action="../pages/user1.jsp?type="+type+"&serachvalue="+serachvalue;
document.forms[0].submit();
}
}

function updatetype(){
var checkboxs=document.getElementsByName("checkbox");        //选择的用户
var edit_group=document.getElementById("edit_group").value;  //修改的类型
var users="";

if(edit_group==0){ 
return;
}else{
for(i=0;i<checkboxs.length;i++){
    if(checkboxs[i].checked==true){
    users+=checkboxs[i].value+",";
    }
}
document.forms[0].action="../pages/user1.jsp?edit_group="+edit_group+"&checkboxs="+users;
document.forms[0].submit();
}
}

function deleteuser(){
var checkboxs=document.getElementsByName("checkbox");        //选择的用户
var users="";
for(i=0;i<checkboxs.length;i++){
    if(checkboxs[i].checked==true){
    users+=checkboxs[i].value+",";
    }
}
//document.forms[0].method="POST";
document.forms[0].action="/IchujianManager/user1.jsp;
alert(users);
document.getElementById('foo').value=users;
alert(document.getElementById('foo').value);
//document.forms[0].submit();
}

function allcheckbox(){   //全选
var checkboxs=document.getElementsByName("checkbox");        //选择的用户
for(i=0;i<checkboxs.length;i++){
    checkboxs[i].checked=true;
}
}

function allcheckboxno(){   //全选
var checkboxs=document.getElementsByName("checkbox");        //选择的用户
for(i=0;i<checkboxs.length;i++){
    checkboxs[i].checked=false;
}
}
</script> 
</head>

<body>
<div style=" padding:15px;">

    <form action="" id="ff"  name="ff" method="post"> 
      
    <input type="hidden" id="foo" name="foo">
    </form>
    <div class="content" style="padding:10px 0px;">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
             <td width="65" align="right"><input name="" onClick="deleteuser()" type="button" class="btn_sc" value="删除"></td>
          </tr>
        </table>
    </div>

    <div class="yhdx">
    	<table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          <td rowspan="2" align="left"><img src="../image/icon_admin1.png" style="border:1px solid #cfcfcf;" width="40"></td>
          <td rowspan="2" align="left"><img src="../image/icon_admin2.png" style="border:1px solid #cfcfcf;" width="40"></td>
          <td rowspan="2" align="left"><img src="../image/icon_admin3.png" style="border:1px solid #cfcfcf;" width="40"></td>
          <td align="left">
          </td>
          </tr>
          <tr>
          <td align="left"><img src="../image/icon_admin1.png" width="12">111</td>
          </tr>
          <tr>
          <td align="left"><img src="../image/icon_admin2.png" width="12">222</td>
          </tr>
          <tr>
          <td align="left"><img src="../image/icon_admin3.png" width="12">333</td>
          </tr>  
          <tr>
            <td colspan="2" height="30" align="center"></td>
          </tr>
          <tr>
            <td align="left"><input name="checkbox" id="checkbox" type="checkbox" value="444"></td>
            <td align="right"></td>
          </tr>
        </table>
    </div>
  
    
</div>
</body>
</html>
