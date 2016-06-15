<%@ page language="java" import="java.util.*,java.net.*" pageEncoding="UTF-8"%>
<%--<%
//String path = request.getContextPath();
//String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//获取jdk信息
//System.out.println(System.getProperty("java.version")); System.out.println(System.getProperty("java.vendor")); System.out.println(System.getProperty("java.vendor.url")); System.out.println(System.getProperty("java.home")); System.out.println(System.getProperty("java.vm.specification.version")); System.out.println(System.getProperty("java.vm.specification.vendor")); System.out.println(System.getProperty("java.vm.specification.name")); System.out.println(System.getProperty("java.vm.version")); System.out.println(System.getProperty("java.vm.vendor")); System.out.println(System.getProperty("java.vm.name")); System.out.println(System.getProperty("java.specification.version")); System.out.println(System.getProperty("java.specification.vendor")); System.out.println(System.getProperty("java.specification.name")); System.out.println(System.getProperty("java.class.version")); System.out.println(System.getProperty("java.class.path")); System.out.println(System.getProperty("java.library.path")); System.out.println(System.getProperty("java.io.tmpdir")); System.out.println(System.getProperty("java.compiler")); System.out.println(System.getProperty("java.ext.dirs"));
//获取系统信息
//System.out.println(System.getProperty("os.name")); System.out.println(System.getProperty("os.arch")); System.out.println(System.getProperty("os.version")); System.out.println(System.getProperty("file.separator")); System.out.println(System.getProperty("path.separator")); System.out.println(System.getProperty("line.separator")); System.out.println(System.getProperty("user.name")); System.out.println(System.getProperty("user.home")); System.out.println(System.getProperty("user.dir"));
%>--%>
<%
Properties props = System.getProperties();
Runtime runtime = Runtime.getRuntime();
long freeMemoery = runtime.freeMemory();
long totalMemory = runtime.totalMemory();
long usedMemory = totalMemory - freeMemoery;
long maxMemory = runtime.maxMemory();
long useableMemory = maxMemory - totalMemory + freeMemoery;
%>
<!doctype html>
<html>
<head>
<title>Server Info</title>
<style>
H1 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:22px;} H2 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:16px;} H3 {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;font-size:14px;} BODY {font-family:Tahoma,Arial,sans-serif;color:black;background-color:white;} B {font-family:Tahoma,Arial,sans-serif;color:white;background-color:#525D76;} P {font-family:Tahoma,Arial,sans-serif;background:white;color:black;font-size:12px;}A {color : black;}A.name {color : black;}HR {color : #525D76;}
  table {
    width: 100%;
  }
  td.page-title {
    text-align: center;
    vertical-align: top;
    font-family:sans-serif,Tahoma,Arial;
    font-weight: bold;
    background: white;
    color: black;
  }
  td.title {
    text-align: left;
    vertical-align: top;
    font-family:sans-serif,Tahoma,Arial;
    font-style:italic;
    font-weight: bold;
    background: #D2A41C;
  }
  td.header-left {
    text-align: left;
    vertical-align: top;
    font-family:sans-serif,Tahoma,Arial;
    font-weight: bold;
    background: #FFDC75;
  }
  td.header-center {
    text-align: center;
    vertical-align: top;
    font-family:sans-serif,Tahoma,Arial;
    font-weight: bold;
    background: #FFDC75;
  }
  td.row-left {
    text-align: left;
    vertical-align: middle;
    font-family:sans-serif,Tahoma,Arial;
    color: black;
  }
  td.row-center {
    text-align: center;
    vertical-align: middle;
    font-family:sans-serif,Tahoma,Arial;
    color: black;
  }
  td.row-right {
    text-align: right;
    vertical-align: middle;
    font-family:sans-serif,Tahoma,Arial;
    color: black;
  }
  TH {
    text-align: center;
    vertical-align: top;
    font-family:sans-serif,Tahoma,Arial;
    font-weight: bold;
    background: #FFDC75;
  }
  TD {
    text-align: center;
    vertical-align: middle;
    font-family:sans-serif,Tahoma,Arial;
    color: black;
  }
  form {
    margin: 1;
  }
  form.inline {
    display: inline;
  }
</style>
</head>

<body bgcolor="#FFFFFF">


<table border="1" cellspacing="0" cellpadding="3">
<tr>
 <td colspan="8" class="title">Request Information</td>
</tr>
<tr>
 <td class="header-center"><small>ServerName</small></td>
 <td class="header-center"><small>ContextPath</small></td>
 <td class="header-center"><small>SessionId</small></td>
</tr>
<tr>
 <td class="row-center"><small><%=request.getServerName()+":"+request.getServerPort() %></small></td>
 <td class="row-center"><small><%=request.getContextPath() %></small></td>
 <td class="row-center"><small><%=request.getSession().getId() %></small></td>
</tr>
</table>

<br>
<br>

<table border="1" cellspacing="0" cellpadding="3">
<tr>
 <td colspan="8" class="title">Memoery Information (MB) </td>
</tr>
<tr>
 <td class="header-center"><small>usedMemory</small></td>
 <td class="header-center"><small>useableMemory</small></td>
 <td class="header-center"><small>maxMemory</small></td>
</tr>
<tr>
 <td class="row-center"><small><%=usedMemory/1024/1024 %></small></td>
 <td class="row-center"><small><%=useableMemory/1024/1024 %></small></td>
 <td class="row-center"><small><%=maxMemory/1024/1024 %></small></td>
</tr>
</table>
<br>
<br>

<table border="1" cellspacing="0" cellpadding="3">
<tr>
 <td colspan="8" class="title">Server Information</td>
</tr>
<tr>
 <td class="header-center"><small>Tomcat Version</small></td>
 <td class="header-center"><small>JVM Version</small></td>
 <td class="header-center"><small>JVM Vendor</small></td>
 <td class="header-center"><small>OS Name</small></td>
 <td class="header-center"><small>OS Version</small></td>
 <td class="header-center"><small>OS Architecture</small></td>
 <td class="header-center"><small>Hostname</small></td>
 <td class="header-center"><small>IP Address</small></td>
</tr>
<tr>
 <td class="row-center"><small><%= application.getServerInfo() %></small></td>
 <td class="row-center"><small><%=System.getProperty("java.version")%> <br> <%=System.getProperty("java.vendor") %></small></td>
 <td class="row-center"><small><%=System.getProperty("java.vm.version") %></small></td>
 <td class="row-center"><small><%=System.getProperty("os.name")%></small></td>
 <td class="row-center"><small><%=System.getProperty("os.version")%></small></td>
 <td class="row-center"><small><%=System.getProperty("os.arch")%></small></td>
 <td class="row-center"><small><%=(InetAddress.getLocalHost()).getHostName()%></small></td>
 <td class="row-center"><small><%=InetAddress.getLocalHost().getHostAddress() %></small></td>
</tr>
</table>
<br>

<table border="1" cellspacing="0" cellpadding="3">
<tr>
 <td colspan="1" class="title">sun Information</td>
</tr>
<tr>
 <td class="header-center"><small>sun.jnu.encoding</small></td>
</tr>
<tr>
 <td class="row-center"><small><%=System.getProperty("sun.jnu.encoding")%></small></td>
</tr>
</table>
<br>

<script language="javascript"> 
//从服务器上获取初始时间 
var currentDate = new Date(<%=new java.util.Date().getTime()%>); 
function run() 
{ 
currentDate.setSeconds(currentDate.getSeconds()+1); 
var time = ""; 
var year = currentDate.getFullYear(); 
var month = currentDate.getMonth() + 1; 
var day = currentDate.getDate(); 
var hour = currentDate.getHours(); 
var minute = currentDate.getMinutes(); 
var second = currentDate.getSeconds(); 
if(hour < 10){ 
time += "0" + hour; 
}else{ 
time += hour; 
} 
time += ":"; 
if(minute < 10){ 
time += "0" + minute; 
}else{ 
time += minute; 
} 
time += ":"; 
if(second < 10){ 
time += "0" + second; 
}else{ 
time += second; 
} 
document.getElementById("dt").innerHTML = "Server Time: "+year+"年"+month+"月"+day+"日" + time; 
}
window.setInterval("run();", 1000); 
</script>

<div id="dt"></div> 
</body>
</html>
