<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>editor</title>
<meta charset="utf-8" />
<link rel="stylesheet" href="../../../js/kindeditor/themes/default/default.css" />
<script charset="utf-8" src="../../../js/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="../../../js/kindeditor/lang/zh_CN.js"></script>
<script>
	KindEditor.ready(function(K) {
		window.editor_content = K.create('#content', {
			urlType : 'domain',
			fullscreenMode:true
		});
	});
</script>

</head>
<body>
<textarea id="content" name="content" style="width:700px;height:300px;visibility:hidden;">
</textarea>
<script>
function setContent(c){
	window.editor_content.html(c);
}
function getContent(){
	return window.editor_content.html();
}
</script>

</body>
</html>
