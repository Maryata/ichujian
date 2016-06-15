/**
 * Created by vpc on 2016/3/11.
 */
$(function(){
    info();
});
function info(){
    $.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityIndexAction!selectActivityIndexList0.action",
        function(data){
            var json = $.parseJSON(data);
            $(json).each(function(){
                var order = this.C_ORDER;
                $('#uploadPreview_'+order).attr("src",this.C_IMG);
                $('#cid_'+order).attr("value",this.C_ID);
                $('#eid_'+order).attr("value",this.C_EID);
                $('#type_'+order).attr("value",this.C_TYPE);
                $('#name_'+order).attr("value",this.C_NAME);
                $('#logo_'+order).attr("value",this.C_IMG);
            });
        });
    $.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityIndexAction!selectActivityIndexList1.action",
        function(data){
            var json = $.parseJSON(data);
            $(json).each(function(){
                var order = this.C_ORDER+4;
                $('#uploadPreview_'+order).attr("src",this.C_IMG);
                $('#cid_'+order).attr("value",this.C_ID);
                $('#eid_'+order).attr("value",this.C_CID);
                $('#title_'+order).text(this.C_NAME);
            });
        });
    $.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityIndexAction!selectActivityIndexList2.action",
        function(data){
            var json = $.parseJSON(data);
            $(json).each(function(){
                var order = this.C_ORDER+8;
                $("#uploadPreview_"+order).attr("src",this.C_IMG);//图片
                $("#number_"+order).text(this.C_VIEW);//数量
                $("#jiaobiao_"+order).attr("src",this.C_LOGO);//角标
                $(".title_"+order).html(this.C_TITLE);//名称
                $("#aid_"+order).attr("value",this.C_AID);//活动id
                $("#cid_"+order).attr("value",this.C_ID);//id
                $("#tagid_"+order).attr("value",this.C_TAGID);//标签id
            });
        });
}
