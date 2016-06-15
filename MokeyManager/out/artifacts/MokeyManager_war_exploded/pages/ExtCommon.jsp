<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<script type="text/javascript">
<%
String path = request.getContextPath();
%>
jv="20141101111"; //jsVersion
rootPath="<%=path %>/";
</script>
<!-- BEGINLIBS -->
<%
out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+path+"/js/ext-3.4.0/resources/css/ext-all.css\"/>");
out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+path+"/css/ux-all.css\"/>");
//out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+path+"/js/ext-3.4.0/ux/GroupHeaderPlugin.css\"/>");
out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\""+path+"/css/css.css\"/>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/adapter/ext/ext-base.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/ext-all.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/ext-lang-zh_CN.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/hotFixForExtjs.js\"></script>");

out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/ux/Ext.ux.util.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/ux/FileUploadField.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/ux/Ext.ux.form.LovCombo.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/ux/TreeCombo.js\"></script>");
//out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/ux/GroupHeaderPlugin.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/ext-3.4.0/formValidation.js\"></script>");
out.println("<script type=\"text/javascript\" src=\""+path+"/js/common_ext.js\"></script>");

%>
<!-- ENDLIBS -->
<script type="text/javascript">
Ext.BLANK_IMAGE_URL = rootPath+'js/ext-3.4.0/resources/images/default/s.gif';
Ext.onReady(function(){
  Ext.QuickTips.init();
});
</script>
<style type="text/css">
body {
	font: normal 12px verdana;
	margin: 0;
	padding: 2px;
	border: 0 none;
	overflow: hidden;
	height: 100%;
}
</style>