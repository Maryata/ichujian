<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
String path = request.getContextPath();
out.println("<script type=\"text/javascript\" src=\""+path+"/js/jquery/jquery-1.8.x.min.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/Highcharts-4.0.3/js/highcharts.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/Highcharts-4.0.3/js/highcharts-3d.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/Highcharts-4.0.3/js/modules/exporting.js\"></script>");

%>
