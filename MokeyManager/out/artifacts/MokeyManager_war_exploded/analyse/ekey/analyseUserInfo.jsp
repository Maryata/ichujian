<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>数据分析</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <script type="text/javascript" charset="utf-8"
            src="${pageContext.request.contextPath}/js/jquery/jquery-1.11.1.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/table.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <script src="${pageContext.request.contextPath}/ekpages/layer/layer.js"></script>

</head>
<style>
        .table_top{
            font-size:12px;
            white-space: nowrap;
        }
        .table_top tbody {
            display: block;
        }
    .table_top .ling td{
        height: 0px;
        overflow: hidden;
        line-height: 0px;
        padding: 0 2px;
        border-left: 1px #b3b3b3 solid;
        border-top: 1px #b3b3b3 solid;
        color: #b3b3b3;
        background-color: #b3b3b3;}
    .table_top thead {
        width: 100%;
        display: block;
    }
    .table_top tbody {
        display: block;

        margin-top: -2px
    }
    .tgao_thead{overflow-y: scroll;}
    .tgao_tbody{overflow-y: scroll;}

</style>
<body>
<div class="box_w">
    <!--面包屑-->
    <table width="100%" height="35" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr>
            <td class="c_gray">数据分析 <img class="m_0_10" src="${pageContext.request.contextPath}/image/dian2.jpg"
                                         width="9" height="9"/></td>
        </tr>
    </table>
    <!--表格1-->
    <form id="form0" method="post">
        <div class="w_100b bc_gray2  p_10 f_14"> 品牌
            <select id="brand" name="brand" class="input_bor2 m_l_10" onchange="getSuppliers()"></select>
            <span class="m_l_20">渠道</span>
            <select id="sup" name="sup" class="input_bor2 m_l_10"></select>
            <span class="m_l_20"></span>
            <span class="m_l_20">开始时间</span>
            <input class="input_bor2 m_l_10" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'eDate\')}'})" id="sDate"
                   name="sDate" value="${sDate}"/>
            <span class="m_l_20">结束时间</span>
            <input class="input_bor2 m_l_10" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sDate\')}',maxDate:'%y-%M-%d'})" id="eDate"
                   name="eDate" value="${eDate}"/>
            <input type="hidden" name="type" id="type" value="1">
            </br>    </br>
            <table   cellspacing="0" cellpadding="0">
               <tr>
                    <input type="checkbox"  class="input_bor2 m_l_10" id="All" >全选&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="checkbox"  class="input_bor2 m_l_10" value="1" id="register" name="parameter">注册&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="checkbox"  class="input_bor2 m_l_10" value="2" id="active" name="parameter">激活&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                    <input type="checkbox" class="input_bor2 m_l_10"  value="3" id="use" name="parameter">免费通话&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                </tr>
                </br></br>
                <tr>
                    <input type="checkbox"  class="input_bor2 m_l_10" value="4" name="parameter">次日
                    <input type="checkbox"  class="input_bor2 m_l_10" value="5" name="parameter">第7日
                    <input type="checkbox" class="input_bor2 m_l_10"  value="6" name="parameter">7日内
                    <input type="checkbox"  class="input_bor2 m_l_10" value="7" name="parameter">第30日
                    <input type="checkbox"  class="input_bor2 m_l_10" value="8" name="parameter">30日内
                </tr>
                <button type="button" class="btn_orange f_r m_r_20" onclick="_query(2);">导出报表</button>
                <button type="button" class="btn_green f_r m_r_10" onclick="_query(1);">查　询</button>
                <div style="display:none;">
                    <button type="button" id="selectData" class="btn_green f_r m_r_10" ></button>
                </div>

            </table>


        </div>
    </form>


    <div class="w_100b t_c f_18 f_b m_t_20" id="dataTitle" style="overflow-x: auto;">
    <!--表格3-->
    <table class=" border_b t_c p_td_10 table_top" cellspacing="0" cellpadding="0" id="tgao">
        <thead id="header" class=" border_b t_c p_td_10 pinned" cellspacing="0" cellpadding="0"></thead>
        <tbody id="data_body" class=" border_b t_c p_td_10" cellspacing="0" cellpadding="0"></tbody>
    </table>
    </div>
</div>
<%--<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src="${pageContext.request.contextPath}/ekpages/js/jquery.pin.js"></script>--%>
<script type="text/javascript">
    var _winHeight= window.screen.availHeight
            //$(window).height();

    $(document).ready(function(){
        $("#selectData").click(function(){
            var winHeight=$(window).height();
            var boxHeight=$("#tgao").outerHeight();
            var chaz=winHeight - 360;
            var tbodyNub=chaz - 100;
            if (boxHeight > chaz) {

                $("#header").addClass("tgao_thead");
                $("#data_body").addClass("tgao_tbody");
                $("#data_body").css("height", tbodyNub)
            }
        });
    });
    $("#All").click(function() {
        $("input[name='parameter']").prop("checked", this.checked);
    });

    $("input[name='parameter']").click(function() {
        var $subs = $("input[name='parameter']");
        $("#All").prop("checked" , $subs.length == $subs.filter(":checked").length ? true :false);
    });

    $(function () {
        getBrands();
    });
    // 查询可见的品牌
    function getBrands() {
        $.post("${pageContext.request.contextPath}/analyse/userAnalyseAction!getBrands.action",
                function (data) {
                    // 品牌
                    var brands = data[0].brands;
                    var brandStr = "";
                    if (brands.length > 1) {
                        brandStr += "<option value='0'>全部</option>"
                    }
                    for (i = 0; i < brands.length; i++) {
                        brandStr += "<option value='" + brands[i].C_ID + "'>" + brands[i].C_NAME + "</option>";
                    }
                    $("#brand").append(brandStr);

                    // 渠道
                    var suppliers = data[0].suppliers;
                    var supStr = "";
                    if (suppliers.length > 1) {
                        supStr += "<option value='0'>全部</option>"
                    }
                    for (i = 0; i < suppliers.length; i++) {
                        supStr += "<option value='" + suppliers[i].C_SUPPLIER_CODE + "'>" + suppliers[i].C_SUPPLIER_NAME + "</option>";
                    }
                    $("#sup").append(supStr);
                }, "json");
    }
    function getSuppliers(brand) {
        var $brand = $("#brand").val();
        $.post("${pageContext.request.contextPath}/analyse/userAnalyseAction!getSuppliersByBrand.action",{brand:$brand},
                function (data) {
                    var suppliers = data[0].suppliers;
                    var supStr = "";
                    if (suppliers.length > 1) {
                        supStr += "<option value='0'>全部</option>"
                    }
                    for (i = 0; i < suppliers.length; i++) {
                        supStr += "<option value='" + suppliers[i].C_SUPPLIER_CODE + "'>" + suppliers[i].C_SUPPLIER_NAME + "</option>";
                    }
                    $("#sup").empty();
                    $("#sup").append(supStr);
                }, "json");
    }

    function _query(arg) {
        var $brand=$("#brand").val();
        var sup=$("#sup").val();
        var sDate = $("#sDate").val();// 起始时间 2016-01-01
        var eDate = $("#eDate").val();// 结束时间
        var type=$("#type").val();
        if (sDate == null || sDate == "" || sDate == "undefined") {
            alert("起始时间不能为空");
            return;
        }
        if (eDate == null || eDate == "" || eDate == "undefined") {
            alert("结束时间不能为空");
            return;
        }
        else {
            var sTime = Date.parse(sDate);
            var eTime = Date.parse(eDate);
            if (sTime > eTime) {
                alert("起始时间不能大于结束时间");
                return;
            }
        }
        if($("#sup option").size()<1){
          layer.msg("暂无权限");
          return false;
        }

       if (arg==1) {
           layer.load();
           var obj=document.getElementsByName('parameter');  //选择所有name="'Germany'"的对象，返回数组
           //取到对象数组后，我们来循环检测它是不是被选中
           var s='';
           var d=[];
           var s1='';
           var lasttr="";
          /* var _winHeight=$(window).height();
           alert(_winHeight);*/
           var countAlldownloadUser=0;
           var countAllactivationUser=0;
           var  countAllregisterUser =0;
           var countAllUse =0;
           var countAllUseKeys =0;
           var countUseAndKeysAlls=0;
           //注册日留存总数
           var DayAllCount=0;
           var WeekAllCount=0;
           var WeekAllRate=0;
           var N_WeekAllCount=0;
           var N_WeekAllRate=0;
           var  MonthAllCount=0;
           var  MonthAllRate=0;
           var  N_MonthAllCount=0;
           var  N_MonthAllRate=0;
           //激活日留存总数
           var  DayAllActivityCount=0;
           var WeekAllActivityCount=0;
           var WeekAllActivityRate=0;
           var N_WeekAllActivityCount=0;
           var N_WeekAllActivityRate=0;
           var MonthAllActivityCount=0;
           var MonthAllActivityRate=0;
           var N_MonthAllActivityCount=0;
           var N_MonthAllActivityRate=0;
           //免费通话的总时长
           var  DayAllFreeCallCount=0
           var  DayAlFreeCallTime=0;
           var  WeekAllFreeCallCount=0;
           var  WeekAlFreeCallTime=0;
           var  WeekAllFreeCallRate=0;
           var  N_WeekAllFreeCallCount=0;
           var  N_WeekAlFreeCallTime=0;
           var  N_WeekAllFreeCallRate=0;
           var  MonthAllFreeCallCount=0;
           var  MonthAlFreeCallTime=0;
           var  MonthAllFreeCallRate=0;
           var  N_MonthAllFreeCallCount=0;
           var  N_MonthAlFreeCallTime=0;
           var  N_MonthAllFreeCallRate=0;

           for(var i=0; i<obj.length; i++){
               if(obj[i].checked) {
                   s += obj[i].value + ',';  //如果选中，将value添加到变量s中
                   d.push(obj[i].value);
               }
               if(obj[i].checked)
                   s1+=obj[i].value+',';  //如果选中，将value添加到变量s中
           }
           //如果没有选择

           if(s1==""){
               $.post("${pageContext.request.contextPath}/analyse/userAnalyseAction!selectAnalyseDate.action",{brand:$brand,sup:sup,sDate:sDate,eDate:eDate,parameter:s1,type:2},
                       function(data){
                           //$(".pinned").pin({containerSelector: ".table_top"});//
                           layer.closeAll();
                           var json = $.parseJSON(data);
                           $("#header").empty();
                           $("#data_body").empty();
                           var list = json[0].list;
                           var head ="";
                           head+="<tr  class='bc_gray2 f_14'>";
                           head+="<td>时间</td><td >下载数</td><td >激活数</td><td >激活率</td><td >注册数</td><td >注册率</td>";
                           head+="<td >启动数</td><td >按键使用数</td><td >合　计</td>";// "<td >免费通话人数</td>" +
                           head+="</tr>";
                           $(list).each(function(index,value){
                               if(index==0){
                                   head+="<tr class='ling'>";
                                   head+="<td>"+(this.C_DATE)+"</td>" +
                                           "<td >"+this.C_DOWNLOAD+"</td>" +
                                           "<td >"+this.C_ACTIVE+"</td>" +
                                           "<td >"+this.C_ACTIVE_RATE+"</td>" +
                                           "<td >"+this.C_REGISTER+"</td>" +
                                           "<td >"+this.C_REGISTER_RATE+"</td>" +
                                               // "<td >"+this.freeCallUserCount+"</td>" +
                                           "<td >"+this.C_START+"</td>" +
                                           "<td >"+this.C_USEKEY+"</td>" +
                                           "<td >"+this.C_USE+"</td>" +
                                           "</tr>"
                               }
                           });
                           $("#header").append(head);
                           $(list).each(function(index,value){
                               var _head="";
                               var str="";
                               if(index==0){
                                   _head=   "<tr  class='bc_gray2 f_14 ling'>" +
                                           "<td>时间</td>" +
                                           "<td >下载数</td>" +
                                           "<td >激活数</td>" +
                                           "<td >激活率</td>" +
                                           "<td >注册数</td>" +
                                           "<td >注册率</td>" +
                                               // "<td >免费通话人数</td>" +
                                           "<td >启动数</td>" +
                                           "<td >按键使用数</td>" +
                                           "<td >合计</td>" +
                                           "</tr>";
                                   $("#data_body").append(_head);
                               }

                               str = "<tr>" +
                               "<td>"+(this.C_DATE)+"</td>" +
                               "<td >"+this.C_DOWNLOAD+"</td>" +
                               "<td >"+this.C_ACTIVE+"</td>" +
                               "<td >"+this.C_ACTIVE_RATE+"</td>" +
                               "<td >"+this.C_REGISTER+"</td>" +
                               "<td >"+this.C_REGISTER_RATE+"</td>" +
                              // "<td >"+this.freeCallUserCount+"</td>" +
                               "<td >"+this.C_START+"</td>" +
                               "<td >"+this.C_USEKEY+"</td>" +
                               "<td >"+this.C_USE+"</td>" +
                               "</tr>"
                               $("#data_body").append(str);
                               countAlldownloadUser += parseInt(this.C_DOWNLOAD);
                               countAllactivationUser += parseInt(this.C_ACTIVE);
                               countAllregisterUser += parseInt(this.C_REGISTER);
                               countAllUse  += parseInt(this.C_START);
                               countAllUseKeys  +=parseInt(this.C_USEKEY);
                               countUseAndKeysAlls  +=parseInt(this.C_USE);
                             //  freeAllCallUserCount +=parseInt(this.freeCallUserCount);



                           });
                           /*var rate1=Math.round(countAllactivationUser/ countAlldownloadUser * 10000) / 100.00 + "%";
                           var rate2=Math.round(countAllregisterUser / countAllactivationUser * 10000) / 100.00 + "%";*/
                           var rate1="";
                           var rate2="";
                           if(countAlldownloadUser==0){
                               rate1="0.00%";
                           }else{
                               rate1=Math.round(countAllactivationUser/ countAlldownloadUser * 10000) / 100.00 + "%";
                           }

                           if(countAllactivationUser==0){
                               rate2="0.00%";
                           }else{
                               rate2=Math.round(countAllregisterUser / countAllactivationUser * 10000) / 100.00 + "%";
                           }

                           var lasttr1="";
                           lasttr1  = "<tr>" +
                                   "<td >合计</td>" +
                                   "<td >" +countAlldownloadUser+ "</td>" +
                                   "<td>" +countAllactivationUser + "</td>" +
                                   "<td>"+rate1+"</td>" +
                                   "<td >"+countAllregisterUser+"</td>" +
                                   "<td >"+rate2+"</td>" +
                                //   "<td >"+freeAllCallUserCount+"</td>" +
                                   "<td >"+countAllUse+"</td>" +
                                   "<td >"+countAllUseKeys+"</td>" +
                                   "<td >"+countUseAndKeysAlls+"</td>" +
                                   "</tr>"
                           $("#data_body").append(lasttr1);
                           //var _winHeight=$(window).height();
                           //alert(_winHeight);
                           var winHeight=_winHeight;
                           var boxHeight=$("#tgao").outerHeight();
                           var chaz=winHeight - 360;
                           var tbodyNub=chaz - 100;
                           if (boxHeight > chaz) {
                               $("#header").addClass("tgao_thead");
                               $("#data_body").addClass("tgao_tbody");
                               $("#data_body").css("height", tbodyNub)
                           }
                       });


           }else{
               if($.inArray("1", d) ==-1&& $.inArray("2", d)==-1 && $.inArray("3", d)==-1){
                   alert("请选择任意一个父类");
                   layer.closeAll();
                   return false;
               }
               if($.inArray("4", d)==-1 && $.inArray("5", d)==-1 && $.inArray("6", d)==-1&& $.inArray("7", d)==-1&& $.inArray("8", d)==-1){
                   alert("请选择任意一个子类");
                   layer.closeAll();
                   return false;
               }

               var num=0;
               for(var i=4;i<9;i++){
                   if($.inArray(i+"", d)!=-1){
                       num++;
                   }
               }
               var count=0;
               $.post("${pageContext.request.contextPath}/analyse/userAnalyseAction!selectAnalyseDate.action",{brand:$brand,sup:sup,sDate:sDate,eDate:eDate,parameter:s,type:2},
                       function(data){
                           //$(".pinned").pin({containerSelector: ".table_top"});//
                           layer.closeAll();
                           var json = $.parseJSON(data);
                           $("#header").empty();
                           $("#data_body").empty();
                           var list = json[0].list;
                           var _str = "";
                               _str +="<tr class='bc_gray2 f_14'>";
                               _str +="<td rowspan='2'>时间</td>" +
                                       "<td rowspan='2'>下载数</td>" +
                                       "<td rowspan='2'>激活数</td>" +
                                       "<td rowspan='2'>激活率</td>" +
                                       "<td rowspan='2'>注册数</td>";
                                _str +="<td rowspan='2'>注册率</td>" +
                                     //  "<td rowspan='2'>免费通话人数</td>" +
                                       "<td rowspan='2'>启动数</td>" +
                                       "<td rowspan='2'>按键使用数</td>" +
                                       "<td rowspan='2'>合&nbsp;&nbsp;计</td>";
                               if($.inArray("1", d)!=-1) {
                                   if(num==1) {
                                       _str += "<td colspan='2'>注册</td>";
                                   }else if(num==2){
                                       _str += "<td colspan='4'>注册</td>";
                                   }else if(num==3){
                                       _str += "<td colspan='6'>注册</td>";
                                   }else if(num==4){
                                       _str += "<td colspan='8'>注册</td>";
                                   }else if(num==5){
                                       _str += "<td colspan='10'>注册</td>";
                                   }
                               }
                               if($.inArray("2", d)!=-1) {
                                   if(num==1) {
                                       _str += "<td colspan='2'>激活</td>";
                                   }else if(num==2){
                                       _str += "<td colspan='4'>激活</td>";
                                   }else if(num==3){
                                       _str += "<td colspan='6'>激活</td>";
                                   }else if(num==4){
                                       _str += "<td colspan='8'>激活</td>";
                                   }else if(num==5){
                                       _str += "<td colspan='10'>激活</td>";
                                   }
                               }
                               if($.inArray("3", d)!=-1) {
                                   if(num==1) {
                                       _str += "<td colspan='3'>免费通话</td>";
                                   }else if(num==2){
                                       _str += "<td colspan='6'>免费通话</td>";
                                   }else if(num==3){
                                       _str += "<td colspan='9'>免费通话</td>";
                                   }else if(num==4){
                                       _str += "<td colspan='12'>免费通话</td>";
                                   }else if(num==5){
                                       _str += "<td colspan='15'>免费通话</td>";
                                   }
                               }
                           _str +="</tr>" ;
                           _str +="<tr class='bc_gray2 f_14'>";
                               if( $.inArray("1", d)!=-1 && $.inArray("4", d)!=-1){
                                   _str +="<td >次日留存</td><td >次日留存率</td>";
                               }
                               if( $.inArray("1", d)!=-1 && $.inArray("5", d)!=-1){
                                   _str +="<td >周留存</td><td >周留存率</td>";
                               }
                               if( $.inArray("1", d)!=-1 && $.inArray("6", d)!=-1){
                                   _str +="<td >周内留存</td><td >周内留存率</td>";
                               }
                               if( $.inArray("1", d)!=-1 && $.inArray("7", d)!=-1){
                                   _str +="<td >月留存</td><td >月留存率</td>";
                               }
                               if( $.inArray("1", d)!=-1 && $.inArray("8", d)!=-1){
                                   _str +="<td >月内留存</td><td >月内留存率</td>";
                               }

                           if($.inArray("2", d)!=-1){
                               if( $.inArray("2", d)!=-1 && $.inArray("4", d)!=-1){
                                   _str +="<td >次日留存</td><td >次日留存率</td>";
                               }
                               if( $.inArray("2", d)!=-1 && $.inArray("5", d)!=-1){
                                   _str +="<td >周留存</td><td >周留存率</td>";
                               }
                               if( $.inArray("2", d)!=-1 && $.inArray("6", d)!=-1){
                                   _str +="<td >周内留存</td><td >周内留存率</td>";
                               }
                               if( $.inArray("2", d)!=-1 && $.inArray("7", d)!=-1){
                                   _str +="<td >月留存</td><td >月留存率</td>";
                               }
                               if( $.inArray("2", d)!=-1 && $.inArray("8", d)!=-1){
                                   _str +="<td >月内留存</td><td >月内留存率</td>";
                               }
                           }

                           if($.inArray("3", d)!=-1){
                               if( $.inArray("3", d)!=-1 && $.inArray("4", d)!=-1){
                                   _str +="<td >次日留存</td><td >时长(分)</td><td >次日留存率</td>";
                               }
                               if( $.inArray("3", d)!=-1 && $.inArray("5", d)!=-1){
                                   _str +="<td >周留存</td><td >时长(分)</td><td >周留存率</td>";
                               }
                               if( $.inArray("3", d)!=-1 && $.inArray("6", d)!=-1){
                                   _str +="<td >周内留存</td><td >时长(分)</td><td >周内留存率</td>";
                               }
                               if( $.inArray("3", d)!=-1 && $.inArray("7", d)!=-1){
                                   _str +="<td >月留存</td><td >时长(分)</td><td >月留存率</td>";
                               }
                               if( $.inArray("3", d)!=-1 && $.inArray("8", d)!=-1){
                                   _str +="<td >月内留存</td><td >时长(分)</td><td >月内留存率</td>";
                               }
                           }
                           _str +="</tr>";
                           $(list).each(function(index,value) {
                               if(index==0){
                                   _str += "<tr class='ling'>";
                                   _str += "<td rowspan='2'>" + ( this.C_DATE )+ "</td>" +
                                           "<td rowspan='2'>" + ( this.C_DOWNLOAD ) +"</td>" +
                                           "<td rowspan='2'>" + ( this.C_ACTIVE ) + "</td>" +
                                           "<td rowspan='2'>" + ( this.C_ACTIVE_RATE )+ "</td>" +
                                           "<td rowspan='2'>" + this.C_REGISTER + "</td>";
                                   _str += "<td rowspan='2'>" + this.C_REGISTER_RATE + "</td>" +
                                               //"<td rowspan='2'>"+this.freeCallUserCount+"</td>" +
                                           "<td rowspan='2'>" + this.C_START + "</td>" +
                                           "<td rowspan='2'>" + this.C_USEKEY + "</td><td rowspan='2'>" + this.C_USE + "</td>";
                                   _str += "</tr>";
                                   _str += "<tr class='ling'>";
                                   if ($.inArray("1", d) != -1) {
                                       if ($.inArray("1", d) != -1 && $.inArray("4", d) != -1) {
                                           _str += "<td >" + (this.C_REG_D) + "</td><td >" + (this.C_REG_D_RATE) + "</td>";
                                       }

                                       if ($.inArray("1", d) != -1 && $.inArray("5", d) != -1) {
                                           _str += "<td >" + (this.C_REG_W_END) + "</td><td >" + (this.C_REG_W_END_RATE) + "</td>";
                                       }
                                       if ($.inArray("1", d) != -1 && $.inArray("6", d) != -1) {
                                           _str += "<td >" + (this.C_REG_W_ALL) + "</td><td >" + (this.C_REG_W_ALL_RATE) + "</td>";
                                       }
                                       if ($.inArray("1", d) != -1 && $.inArray("7", d) != -1) {
                                           _str += "<td >" + (this.C_REG_M_END) + "</td><td >" + (this.C_REG_M_END_RATE) + "</td>";
                                       }
                                       if ($.inArray("1", d) != -1 && $.inArray("8", d) != -1) {
                                           _str += "<td >" + (this.C_REG_M_ALL) + "</td><td >" + (this.C_REG_M_ALL_RATE) + "</td>";
                                       }
                                   }

                                   if ($.inArray("2", d) != -1) {
                                       if ($.inArray("2", d) != -1 && $.inArray("4", d) != -1) {
                                           _str += "<td >" + (this.C_ACT_D) + "</td><td >" + (this.C_ACT_D_RATE) + "</td>";
                                       }
                                       if ($.inArray("2", d) != -1 && $.inArray("5", d) != -1) {
                                           _str += "<td >" + (this.C_ACT_W_END) + "</td><td >" + (this.C_ACT_W_END_RATE) + "</td>";
                                       }
                                       if ($.inArray("2", d) != -1 && $.inArray("6", d) != -1) {
                                           _str += "<td >" + (this.C_ACT_W_ALL) + "</td><td >" + (this.C_ACT_W_ALL_RATE) + "</td>";
                                       }
                                       if ($.inArray("2", d) != -1 && $.inArray("7", d) != -1) {
                                           _str += "<td >" + (this.C_ACT_M_END) + "</td><td >" + (this.C_ACT_M_END_RATE) + "</td>";
                                       }
                                       if ($.inArray("2", d) != -1 && $.inArray("8", d) != -1) {
                                           _str += "<td >" + (this.C_ACT_M_ALL) + "</td><td >" + (this.C_ACT_M_ALL_RATE) + "</td>";
                                       }
                                   }

                                   if ($.inArray("3", d) != -1) {
                                       if ($.inArray("3", d) != -1 && $.inArray("4", d) != -1) {

                                           _str += "<td >" + (this.C_FY_D) + "</td><td >" + (this.C_FY_D_TIME) + "</td><td >" + (this.C_FY_D_RATE) + "</td>";
                                       }
                                       if ($.inArray("3", d) != -1 && $.inArray("5", d) != -1) {
                                           _str += "<td >" + (this.C_FY_W_END) + "</td><td >" + (this.C_FY_W_END_TIME) + "</td><td >" + (this.C_FY_W_END_RATE) + "</td>";
                                       }
                                       if ($.inArray("3", d) != -1 && $.inArray("6", d) != -1) {
                                           _str += "<td >" + (this.C_FY_W_ALL) + "</td><td >" + (this.C_FY_W_ALL_TIME) + "</td><td >" + (this.C_FY_W_ALL_RATE) + "</td>";
                                       }
                                       if ($.inArray("3", d) != -1 && $.inArray("7", d) != -1) {
                                           _str += "<td >" + (this.C_FY_M_END) + "</td><td >" + (this.C_FY_M_END_TIME) + "</td><td >" + (this.C_FY_M_END_RATE) + "</td>";
                                       }
                                       if ($.inArray("3", d) != -1 && $.inArray("8", d) != -1) {
                                           _str += "<td >" + (this.C_FY_M_ALL) + "</td><td >" + (this.C_FY_M_ALL_TIME) + "</td><td >" + (this.C_FY_M_ALL_RATE) + "</td>";
                                       }
                                   }
                                   _str += "</tr>";
                               }
                           });
                           $("#header").append(_str);
                           count = $(list).length;
                           $(list).each(function(index,value) {
                               var _tr="";
                               var _br="";
                               if(index==0){
                                   _br +="<tr class='bc_gray2 f_14  ling'>";
                                   _br +="<td rowspan='2'>时间</td>" +
                                           "<td rowspan='2'>下载数</td>" +
                                           "<td rowspan='2'>激活数</td>" +
                                           "<td rowspan='2'>激活率</td>" +
                                           "<td rowspan='2'>注册数</td>";
                                   _br +="<td rowspan='2'>注册率</td>" +
                                               //  "<td rowspan='2'>免费通话人数</td>" +
                                           "<td rowspan='2'>启动数</td>" +
                                           "<td rowspan='2'>按键使用数</td>" +
                                           "<td rowspan='2'>合计</td>";
                                   if($.inArray("1", d)!=-1) {
                                       if(num==1) {
                                           _br += "<td colspan='2'>注册</td>";
                                       }else if(num==2){
                                           _br += "<td colspan='4'>注册</td>";
                                       }else if(num==3){
                                           _br += "<td colspan='6'>注册</td>";
                                       }else if(num==4){
                                           _br += "<td colspan='8'>注册</td>";
                                       }else if(num==5){
                                           _br += "<td colspan='10'>注册</td>";
                                       }
                                   }
                                   if($.inArray("2", d)!=-1) {
                                       if(num==1) {
                                           _br += "<td colspan='2'>激活</td>";
                                       }else if(num==2){
                                           _br += "<td colspan='4'>激活</td>";
                                       }else if(num==3){
                                           _br += "<td colspan='6'>激活</td>";
                                       }else if(num==4){
                                           _br += "<td colspan='8'>激活</td>";
                                       }else if(num==5){
                                           _br += "<td colspan='10'>激活</td>";
                                       }
                                   }
                                   if($.inArray("3", d)!=-1) {
                                       if(num==1) {
                                           _br += "<td colspan='3'>免费通话</td>";
                                       }else if(num==2){
                                           _br += "<td colspan='6'>免费通话</td>";
                                       }else if(num==3){
                                           _br += "<td colspan='9'>免费通话</td>";
                                       }else if(num==4){
                                           _br += "<td colspan='12'>免费通话</td>";
                                       }else if(num==5){
                                           _br += "<td colspan='15'>免费通话</td>";
                                       }
                                   }
                                   _br +="</tr>" ;
                                   _br +="<tr class='bc_gray2 f_14  ling'>";
                                   if( $.inArray("1", d)!=-1 && $.inArray("4", d)!=-1){
                                       _br +="<td >次日留存</td><td >次日留存率</td>";
                                   }
                                   if( $.inArray("1", d)!=-1 && $.inArray("5", d)!=-1){
                                       _br +="<td >周留存</td><td >周留存率</td>";
                                   }
                                   if( $.inArray("1", d)!=-1 && $.inArray("6", d)!=-1){
                                       _br +="<td >周内留存</td><td >周内留存率</td>";
                                   }
                                   if( $.inArray("1", d)!=-1 && $.inArray("7", d)!=-1){
                                       _br +="<td >月留存</td><td >月留存率</td>";
                                   }
                                   if( $.inArray("1", d)!=-1 && $.inArray("8", d)!=-1){
                                       _br +="<td >月内留存</td><td >月内留存率</td>";
                                   }

                                   if($.inArray("2", d)!=-1){
                                       if( $.inArray("2", d)!=-1 && $.inArray("4", d)!=-1){
                                           _br +="<td >次日留存</td><td >次日留存率</td>";
                                       }
                                       if( $.inArray("2", d)!=-1 && $.inArray("5", d)!=-1){
                                           _br +="<td >周留存</td><td >周留存率</td>";
                                       }
                                       if( $.inArray("2", d)!=-1 && $.inArray("6", d)!=-1){
                                           _br +="<td >周内留存</td><td >周内留存率</td>";
                                       }
                                       if( $.inArray("2", d)!=-1 && $.inArray("7", d)!=-1){
                                           _br +="<td >月留存</td><td >月留存率</td>";
                                       }
                                       if( $.inArray("2", d)!=-1 && $.inArray("8", d)!=-1){
                                           _br +="<td >月内留存</td><td >月内留存率</td>";
                                       }
                                   }

                                   if($.inArray("3", d)!=-1){
                                       if( $.inArray("3", d)!=-1 && $.inArray("4", d)!=-1){
                                           _br +="<td >次日留存</td><td >时长(分)</td><td >次日留存率</td>";
                                       }
                                       if( $.inArray("3", d)!=-1 && $.inArray("5", d)!=-1){
                                           _br +="<td >周留存</td><td >时长(分)</td><td >周留存率</td>";
                                       }
                                       if( $.inArray("3", d)!=-1 && $.inArray("6", d)!=-1){
                                           _br +="<td >周内留存</td><td >时长(分)</td><td >周内留存率</td>";
                                       }
                                       if( $.inArray("3", d)!=-1 && $.inArray("7", d)!=-1){
                                           _br +="<td >月留存</td><td >时长(分)</td><td >月留存率</td>";
                                       }
                                       if( $.inArray("3", d)!=-1 && $.inArray("8", d)!=-1){
                                           _br +="<td >月内留存</td><td >时长(分)</td><td >月内留存率</td>";
                                       }
                                   }
                                   _br+="</tr>";
                                   $("#data_body").append(_br);
                               }
                                _tr += "<tr >";
                                _tr += "<td rowspan='2'>" + ( this.C_DATE )+ "</td>" +
                                        "<td rowspan='2'>" + ( this.C_DOWNLOAD ) +"</td>" +
                                        "<td rowspan='2'>" + ( this.C_ACTIVE ) + "</td>" +
                                        "<td rowspan='2'>" + ( this.C_ACTIVE_RATE )+ "</td>" +
                                        "<td rowspan='2'>" + this.C_REGISTER + "</td>";
                                _tr += "<td rowspan='2'>" + this.C_REGISTER_RATE + "</td>" +
                                        //"<td rowspan='2'>"+this.freeCallUserCount+"</td>" +
                                        "<td rowspan='2'>" + this.C_START + "</td>" +
                                        "<td rowspan='2'>" + this.C_USEKEY + "</td><td rowspan='2'>" + this.C_USE + "</td>";
                                _tr += "</tr>";
                                _tr += "<tr>";
                               if ($.inArray("1", d) != -1) {
                                   if ($.inArray("1", d) != -1 && $.inArray("4", d) != -1) {
                                       _tr += "<td >" + (this.C_REG_D) + "</td><td >" + (this.C_REG_D_RATE) + "</td>";
                                   }

                                   if ($.inArray("1", d) != -1 && $.inArray("5", d) != -1) {
                                       _tr += "<td >" + (this.C_REG_W_END) + "</td><td >" + (this.C_REG_W_END_RATE) + "</td>";
                                   }
                                   if ($.inArray("1", d) != -1 && $.inArray("6", d) != -1) {
                                       _tr += "<td >" + (this.C_REG_W_ALL) + "</td><td >" + (this.C_REG_W_ALL_RATE) + "</td>";
                                   }
                                   if ($.inArray("1", d) != -1 && $.inArray("7", d) != -1) {
                                       _tr += "<td >" + (this.C_REG_M_END) + "</td><td >" + (this.C_REG_M_END_RATE) + "</td>";
                                   }
                                   if ($.inArray("1", d) != -1 && $.inArray("8", d) != -1) {
                                       _tr += "<td >" + (this.C_REG_M_ALL) + "</td><td >" + (this.C_REG_M_ALL_RATE) + "</td>";
                                   }
                               }

                                if ($.inArray("2", d) != -1) {
                                    if ($.inArray("2", d) != -1 && $.inArray("4", d) != -1) {
                                        _tr += "<td >" + (this.C_ACT_D) + "</td><td >" + (this.C_ACT_D_RATE) + "</td>";
                                    }
                                    if ($.inArray("2", d) != -1 && $.inArray("5", d) != -1) {
                                        _tr += "<td >" + (this.C_ACT_W_END) + "</td><td >" + (this.C_ACT_W_END_RATE) + "</td>";
                                    }
                                    if ($.inArray("2", d) != -1 && $.inArray("6", d) != -1) {
                                        _tr += "<td >" + (this.C_ACT_W_ALL) + "</td><td >" + (this.C_ACT_W_ALL_RATE) + "</td>";
                                    }
                                    if ($.inArray("2", d) != -1 && $.inArray("7", d) != -1) {
                                        _tr += "<td >" + (this.C_ACT_M_END) + "</td><td >" + (this.C_ACT_M_END_RATE) + "</td>";
                                    }
                                    if ($.inArray("2", d) != -1 && $.inArray("8", d) != -1) {
                                        _tr += "<td >" + (this.C_ACT_M_ALL) + "</td><td >" + (this.C_ACT_M_ALL_RATE) + "</td>";
                                    }
                                }

                                if ($.inArray("3", d) != -1) {
                                    if ($.inArray("3", d) != -1 && $.inArray("4", d) != -1) {

                                        _tr += "<td >" + (this.C_FY_D) + "</td><td >" + (this.C_FY_D_TIME) + "</td><td >" + (this.C_FY_D_RATE) + "</td>";
                                    }
                                    if ($.inArray("3", d) != -1 && $.inArray("5", d) != -1) {
                                        _tr += "<td >" + (this.C_FY_W_END) + "</td><td >" + (this.C_FY_W_END_TIME) + "</td><td >" + (this.C_FY_W_END_RATE) + "</td>";
                                    }
                                    if ($.inArray("3", d) != -1 && $.inArray("6", d) != -1) {
                                        _tr += "<td >" + (this.C_FY_W_ALL) + "</td><td >" + (this.C_FY_W_ALL_TIME) + "</td><td >" + (this.C_FY_W_ALL_RATE) + "</td>";
                                    }
                                    if ($.inArray("3", d) != -1 && $.inArray("7", d) != -1) {
                                        _tr += "<td >" + (this.C_FY_M_END) + "</td><td >" + (this.C_FY_M_END_TIME) + "</td><td >" + (this.C_FY_M_END_RATE) + "</td>";
                                    }
                                    if ($.inArray("3", d) != -1 && $.inArray("8", d) != -1) {
                                        _tr += "<td >" + (this.C_FY_M_ALL) + "</td><td >" + (this.C_FY_M_ALL_TIME) + "</td><td >" + (this.C_FY_M_ALL_RATE) + "</td>";
                                    }
                                }
                                _tr += "</tr>";
                                 countAlldownloadUser +=parseInt(this.C_DOWNLOAD);
                                 countAllactivationUser += parseInt(this.C_ACTIVE);
                                 countAllregisterUser += parseInt(this.C_REGISTER);
                                 countAllUse  += parseInt(this.C_START);
                                 countAllUseKeys  +=parseInt(this.C_USEKEY);
                                 countUseAndKeysAlls  +=parseInt(this.C_USE);



                                 //注册日留存总数
                               if ($.inArray("1", d) != -1) {
                                   DayAllCount += parseInt(this.C_REG_D);
                                   WeekAllCount += parseInt(this.C_REG_W_END);
                                   WeekAllRate +=parseFloat(this.C_REG_W_END_RATE.replace(/%/, ""));
                                   N_WeekAllCount += parseInt(this.C_REG_W_ALL);
                                   N_WeekAllRate +=parseFloat(this.C_REG_W_ALL_RATE.replace(/%/, ""));
                                   MonthAllCount += parseInt(this.C_REG_M_END);
                                   MonthAllRate +=parseFloat(this.C_REG_M_END_RATE.replace(/%/, ""));
                                   N_MonthAllCount += parseInt(this.C_REG_M_ALL);
                                   N_MonthAllRate +=parseFloat(this.C_REG_M_ALL_RATE.replace(/%/, ""));

                               }
                               if ($.inArray("2", d) != -1) {
                                   DayAllActivityCount += parseInt(this.C_ACT_D);
                                   WeekAllActivityCount += parseInt(this.C_ACT_W_END);
                                   WeekAllActivityRate +=parseFloat(this.C_ACT_W_END_RATE.replace(/%/, ""));
                                   N_WeekAllActivityCount += parseInt(this.C_ACT_W_ALL);
                                   N_WeekAllActivityRate += parseFloat(this.C_ACT_W_ALL_RATE.replace(/%/, ""));
                                   MonthAllActivityCount += parseInt(this.C_ACT_M_END);
                                   MonthAllActivityRate += parseFloat(this.C_ACT_M_END_RATE.replace(/%/, ""));
                                   N_MonthAllActivityCount += parseInt(this.C_ACT_M_ALL);
                                   N_MonthAllActivityRate += parseFloat(this.C_ACT_M_ALL_RATE.replace(/%/, ""));

                               }
                               //免费通话的总时长
                               if ($.inArray("3", d) != -1) {
                                   DayAllFreeCallCount +=parseInt(this.C_FY_D);
                                   DayAlFreeCallTime +=parseInt(this.C_FY_D_TIME);
                                   WeekAlFreeCallTime +=parseInt(this.C_FY_W_END_TIME);
                                   WeekAllFreeCallCount += parseInt(this.C_FY_W_END);
                                   WeekAllFreeCallRate +=parseFloat(this.C_FY_W_END_RATE.replace(/%/, ""));
                                   N_WeekAllFreeCallCount += parseInt(this.C_FY_W_ALL);
                                   N_WeekAlFreeCallTime +=parseInt(this.C_FY_W_ALL_TIME);
                                   N_WeekAllFreeCallRate +=parseFloat(this.C_FY_W_ALL_RATE.replace(/%/, ""));
                                   MonthAllFreeCallCount += parseInt(this.C_FY_M_END);
                                   MonthAlFreeCallTime +=parseInt(this.C_FY_M_END_TIME);
                                   MonthAllFreeCallRate +=parseFloat(this.C_FY_M_END_RATE.replace(/%/, ""));
                                   N_MonthAllFreeCallCount += parseInt(this.C_FY_M_ALL);
                                   N_MonthAlFreeCallTime +=parseInt(this.C_FY_M_ALL_TIME);
                                   N_MonthAllFreeCallRate +=parseFloat(this.C_FY_M_ALL_RATE.replace(/%/, ""));
                               }
                                $("#data_body").append(_tr);
                          });
                           var rate1="";
                           var rate2="";
                           if(countAlldownloadUser==0){
                               rate1="0.00%";
                           }else{
                               rate1=Math.round(countAllactivationUser/ countAlldownloadUser * 10000) / 100.00 + "%";
                           }
                           if(countAlldownloadUser==0){
                               rate2="0.00%";
                           }else{
                               rate2=Math.round(countAllregisterUser / countAllactivationUser * 10000) / 100.00 + "%";
                           }
                           lasttr  = "<tr>" +
                                   "<td rowspan='2'>合计</td>" +
                                   "<td rowspan='2'>" +countAlldownloadUser+ "</td>" +
                                   "<td rowspan='2'>" +countAllactivationUser + "</td>" +
                                   "<td rowspan='2'>"+rate1+"</td>" +
                                   "<td rowspan='2'>"+countAllregisterUser+"</td>" +
                                   "<td rowspan='2'>"+rate2+"</td>" +
                                 //  "<td rowspan='2'>"+freeAllCallUserCount+"</td>" +
                                   "<td rowspan='2'>"+countAllUse+"</td>" +
                                   "<td rowspan='2'>"+countAllUseKeys+"</td>" +
                                   "<td rowspan='2'>"+countUseAndKeysAlls+"</td>" +
                                  "</tr><tr>"
                           if ($.inArray("1", d) != -1) {
                               if ($.inArray("1", d) != -1 && $.inArray("4", d) != -1) {
                                   var rate= Math.round(DayAllCount / countAllregisterUser * 10000) / 100.00 + "%";
                                   if(DayAllCount==0&&countAllregisterUser==0){
                                       lasttr += "<td >" + DayAllCount + "</td><td >" +0+"%" + "</td>";
                                   }else{
                                       lasttr += "<td >" + DayAllCount + "</td><td >" +rate + "</td>";
                                   }

                               }
                               var  _WeekAllRate = Math.round(WeekAllRate/count*100)/100.00+ "%";
                               var  _N_WeekAllRate = Math.round(N_WeekAllRate/count*100)/100.00+ "%";
                               var  _MonthAllRate = Math.round(MonthAllRate/count*100)/100.00+ "%";
                               var  _N_MonthAllRate = Math.round(N_MonthAllRate/count*100)/100.00+ "%";
                               if ($.inArray("1", d) != -1 && $.inArray("5", d) != -1) {
                                   lasttr += "<td >"+WeekAllCount+"</td><td >"+_WeekAllRate+"</td>";
                               }
                               if ($.inArray("1", d) != -1 && $.inArray("6", d) != -1) {
                                   lasttr += "<td >"+N_WeekAllCount+"</td><td >"+_N_WeekAllRate+"</td>";
                               }
                               if ($.inArray("1", d) != -1 && $.inArray("7", d) != -1) {
                                   lasttr += "<td >"+MonthAllCount+"</td><td >"+_MonthAllRate+"</td>";
                               }
                               if ($.inArray("1", d) != -1 && $.inArray("8", d) != -1) {
                                   lasttr += "<td >"+N_MonthAllCount+"</td><td >"+_N_MonthAllRate+"</td>";
                               }
                           }

                          if ($.inArray("2", d) != -1) {
                              if ($.inArray("2", d) != -1 && $.inArray("4", d) != -1) {
                                  var rate= Math.round(DayAllActivityCount / countAllactivationUser * 10000) / 100.00 + "%";
                                  if(DayAllActivityCount==0&&countAllactivationUser==0){
                                      lasttr += "<td >" + DayAllActivityCount + "</td><td >" +0+"%" + "</td>";
                                  }else{
                                      lasttr += "<td >" + DayAllActivityCount + "</td><td >" +rate + "</td>";
                                  }
                              }
                              var  _WeekAllActivityRate = Math.round(WeekAllActivityRate/count*100)/100.00+ "%";
                              var  _N_WeekAllActivityRate = Math.round(N_WeekAllActivityRate/count*100)/100.00+ "%";
                              var  _MonthAllActivityRate = Math.round(MonthAllActivityRate/count*100)/100.00+ "%";
                              var  _N_MonthAllActivityRate = Math.round(N_MonthAllActivityRate/count*100)/100.00+ "%";
                              if ($.inArray("2", d) != -1 && $.inArray("5", d) != -1) {
                                  lasttr += "<td >"+WeekAllActivityCount+"</td><td >"+_WeekAllActivityRate+"</td>";
                              }
                              if ($.inArray("2", d) != -1 && $.inArray("6", d) != -1) {
                                  lasttr += "<td >"+N_WeekAllActivityCount+"</td><td >"+_N_WeekAllActivityRate+"</td>";
                              }
                              if ($.inArray("2", d) != -1 && $.inArray("7", d) != -1) {
                                  lasttr += "<td >"+MonthAllActivityCount+"</td><td >"+_MonthAllActivityRate+"</td>";
                              }
                              if ($.inArray("2", d) != -1 && $.inArray("8", d) != -1) {
                                  lasttr += "<td >"+N_MonthAllActivityCount+"</td><td >"+_N_MonthAllActivityRate+"</td>";
                              }
                           }
                           if ($.inArray("3", d) != -1) {
                               if ($.inArray("3", d) != -1 && $.inArray("4", d) != -1) {
                                   var rate= Math.round(DayAllFreeCallCount / countAllactivationUser * 10000) / 100.00 + "%";
                                   if(DayAllFreeCallCount==0&&countAllactivationUser==0){
                                       lasttr += "<td >" + DayAllFreeCallCount + "</td><td >"+DayAlFreeCallTime+"</td><td >" +0.00+"%" + "</td>";
                                   }else{
                                       lasttr += "<td >" + DayAllFreeCallCount + "</td><td >"+DayAlFreeCallTime+"</td><td >" +rate + "</td>";
                                   }

                               }

                               var  _WeekAllFreeCallRate = Math.round(WeekAllFreeCallRate/count*100)/100.00+ "%";
                               var  _N_WeekAllFreeCallRate = Math.round(N_WeekAllFreeCallRate/count*100)/100.00+ "%";
                               var  _MonthAllFreeCallRate = Math.round(MonthAllFreeCallRate/count*100)/100.00+ "%";
                               var  _N_MonthAllFreeCallRate = Math.round(N_MonthAllFreeCallRate/count*100)/100.00+ "%";
                               if ($.inArray("3", d) != -1 && $.inArray("5", d) != -1) {
                                   lasttr += "<td >"+WeekAllFreeCallCount+"</td><td >"+WeekAlFreeCallTime+"</td><td >"+_WeekAllFreeCallRate+"</td>";
                               }
                               if ($.inArray("3", d) != -1 && $.inArray("6", d) != -1) {
                                   lasttr += "<td >"+N_WeekAllFreeCallCount+"</td><td >"+N_WeekAlFreeCallTime+"</td><td >"+_N_WeekAllFreeCallRate+"</td>";
                               }
                               if ($.inArray("3", d) != -1 && $.inArray("7", d) != -1) {
                                   lasttr += "<td >"+MonthAllFreeCallCount+"</td><td >"+MonthAlFreeCallTime+"</td><td >"+_MonthAllFreeCallRate+"</td>";
                               }
                               if ($.inArray("3", d) != -1 && $.inArray("8", d) != -1) {
                                   lasttr += "<td >"+N_MonthAllFreeCallCount+"</td><td >"+N_MonthAlFreeCallTime+"</td><td >"+_N_MonthAllFreeCallRate+"</td>";
                               }
                           }
                           $("#data_body").append(lasttr);
                               //var winHeight=$(window).height();
                               var winHeight=_winHeight;
                               var boxHeight=$("#tgao").outerHeight();
                               var chaz=winHeight - 360;
                               var tbodyNub=chaz - 100;
                               if (boxHeight > chaz) {
                                   $("#header").addClass("tgao_thead");
                                   $("#data_body").addClass("tgao_tbody");
                                   $("#data_body").css("height", tbodyNub)
                               }
                      });
           }
    }else {
           if($("#data_body tr").length ==0){
               layer.msg("请查询你要导出的数据！");
               return false;
           }else{
               $('#form0').attr("action", "${pageContext.request.contextPath}/analyse/userAnalyseAction!exportAllComplexStatistic.action");
               $('#form0').submit();
           }

        }
    }

</script>
</body>
</html>
