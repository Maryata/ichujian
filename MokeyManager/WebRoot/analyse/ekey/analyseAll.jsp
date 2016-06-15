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
        <div class="w_100b bc_gray2  p_10 f_14"> 供应商
            <select id="sup" name="sup" class="input_bor2 m_l_10"></select>
            <span class="m_l_20"></span>
            <span class="m_l_20">开始时间</span>
            <input class="input_bor2 m_l_10" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'eDate\')}'})" id="sDate"
                   name="sDate" value="${sDate}"/>
            <span class="m_l_20">结束时间</span>
            <input class="input_bor2 m_l_10" type="text" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'sDate\')}',maxDate:'%y-%M-%d'})" id="eDate"
                   name="eDate" value="${eDate}"/>
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
            </table>


        </div>
    </form>


    <div class="w_100b t_c f_18 f_b m_t_20" id="dataTitle" style="overflow-x: auto;">
    <!--表格3-->
    <table class=" border_b t_c p_td_10 m_t_20" cellspacing="0" cellpadding="0" id="query">
      <thead id="header" class=" border_b t_c p_td_10 m_t_20" cellspacing="0" cellpadding="0"></thead>
      <tbody id="data_body" class=" border_b t_c p_td_10 m_t_20" cellspacing="0" cellpadding="0"></tbody>
    </table>
    </div>
</div>






<script type="text/javascript">

    $("#All").click(function() {
        $("input[name='parameter']").prop("checked", this.checked);
    });

    $("input[name='parameter']").click(function() {
        var $subs = $("input[name='parameter']");
        $("#All").prop("checked" , $subs.length == $subs.filter(":checked").length ? true :false);
    });



    function _query(arg) {
        var sup=$("#sup").val();
        var sDate = $("#sDate").val();// 起始时间 2016-01-01
        var eDate = $("#eDate").val();// 结束时间
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

       if (arg==1) {
           layer.load();
           var obj=document.getElementsByName('parameter');  //选择所有name="'Germany'"的对象，返回数组
           //取到对象数组后，我们来循环检测它是不是被选中
           var s='';
           var d=[];
           var s1='';
           var lasttr;
           var countAlldownloadUser=0;
           var countAllactivationUser=0;
           var  countAllregisterUser =0;
           var countAllUse =0;
           var countAllUseKeys =0;
           var countUseAndKeysAlls=0;
           //注册日留存总数
           var  DayAllCount=0;
//           //注册周留存总数
//           var  WeekAllCount;
//           //注册周内留存总数
//           var  WeekNAllCount;
//           //注册月留存总数
//           var  WeekAllCount;
//           //注册月内留存总数
//           var  WeekNAllCount;

           //激活日留存总数
           var  DayAllActivityCount=0;
//           //激活周留存总数
//           var  WeekAllActivityCount;
//           //激活周内留存总数
//           var  WeekAllActivityNCount;
//           //激活月留存总数
//           var  MonthAllActivityCount;
//           //激活月内留存总数
//           var  MonthAllActivityNCount;

           //免费通话日留存总数
       //    var  freeAllCallUserCount=0;
           //免费通话的次日留存总数
           var  DayAllFreeCallCount=0
           //免费通话的总时长
           var  DayAlFreeCallTime=0;

//           //免费通话周留存总数
//           var  WeekAllFreeCallCount;
//           //免费通话周内留存总数
//           var  WeekAllNFreeCallCount;
//           //免费通话月留存总数
//           var  MonthAllFreeCallCount;
//           //免费通话月内留存总数
//           var  MonthAllNFreeCallCount;


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
               $.post("${pageContext.request.contextPath}/analyse/userAnalyseAction!selectAllComplexStatistics.action",{sup:sup,sDate:sDate,eDate:eDate,parameter:s1},
                       function(data){
                           layer.closeAll();
                           var json = $.parseJSON(data);
                           $("#header").empty();
                           $("#data_body").empty();
                           var list = json[0].baseDate;
                           var str = "";
                           var list = json[0].list;
                           var lasttr1;
                           var   head =
                                   "<tr  class='bc_gray2 f_14'>" +
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
                                   "</tr>"
                           $("#header").append(head);
                           $(list).each(function(){
                               str = "<tr>" +
                               "<td>"+(this.time)+"</td>" +
                               "<td >"+this.countdownloadUser+"</td>" +
                               "<td >"+this.countactivationUser+"</td>" +
                               "<td >"+this.countactivationUserRate+"</td>" +
                               "<td >"+this.countregisterUser+"</td>" +
                               "<td >"+this.countregisterUserRate+"</td>" +
                              // "<td >"+this.freeCallUserCount+"</td>" +
                               "<td >"+this.countUse+"</td>" +
                               "<td >"+this.countUseKeys+"</td>" +
                               "<td >"+this.countUseAndKeysAll+"</td>" +
                               "</tr>"
                               $("#data_body").append(str);
                               countAlldownloadUser += parseInt(this.countdownloadUser);
                               countAllactivationUser += parseInt(this.countactivationUser);
                               countAllregisterUser += parseInt(this.countregisterUser);
                             //  countAllUse  += parseInt(this.countUse);
                             //  countAllUseKeys  +=parseInt(this.countUseKeys);
                             //  countUseAndKeysAlls  +=parseInt(this.countUseAndKeysAll);
                             //  freeAllCallUserCount +=parseInt(this.freeCallUserCount);


                           });
                           var rate1=Math.round(countAllactivationUser/ countAlldownloadUser * 10000) / 100.00 + "%";
                           var rate2=Math.round(countAllregisterUser / countAllactivationUser * 10000) / 100.00 + "%";
                           lasttr1  = "<tr>" +
                                   "<td >合计</td>" +
                                   "<td >" +countAlldownloadUser+ "</td>" +
                                   "<td>" +countAllactivationUser + "</td>" +
                                   "<td>"+rate1+"</td>" +
                                   "<td >"+countAllregisterUser+"</td>" +
                                   "<td >"+rate2+"</td>" +
                                //   "<td >"+freeAllCallUserCount+"</td>" +
                                   "<td ></td>" +
                                   "<td ></td>" +
                                   "<td ></td>" +
                                   "</tr>"
                           $("#data_body").append(lasttr1);
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
               $.post("${pageContext.request.contextPath}/analyse/userAnalyseAction!selectAllComplexStatistics.action",{sup:sup,sDate:sDate,eDate:eDate,parameter:s},
                       function(data){
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
                                       "<td rowspan='2'>合计</td>";
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
                           $("#header").append(_str);
                           $(list).each(function(index,value) {
                               var _tr="";
                                _tr += "<tr >";
                                _tr += "<td rowspan='2'>" + this.time + "</td>" +
                                        "<td rowspan='2'>" + this.countdownloadUser +"</td>" +
                                        "<td rowspan='2'>" + this.countactivationUser + "</td>" +
                                        "<td rowspan='2'>" + this.countactivationUserRate + "</td>" +
                                        "<td rowspan='2'>" + this.countregisterUser + "</td>";
                                _tr += "<td rowspan='2'>" + this.countregisterUserRate + "</td>" +
                                        //"<td rowspan='2'>"+this.freeCallUserCount+"</td>" +
                                        "<td rowspan='2'>" + this.countUse + "</td>" +
                                        "<td rowspan='2'>" + this.countUseKeys + "</td><td rowspan='2'>" + this.countUseAndKeysAll + "</td>";
                                _tr += "</tr>";
                                _tr += "<tr>";
                               if ($.inArray("1", d) != -1) {
                                   if ($.inArray("1", d) != -1 && $.inArray("4", d) != -1) {
                                       _tr += "<td >" + (this.DayCount) + "</td><td >" + (this.dayRate) + "</td>";
                                   }

                                   if ($.inArray("1", d) != -1 && $.inArray("5", d) != -1) {
                                       _tr += "<td >" + (this.WeekCount) + "</td><td >" + (this.WeekRate) + "</td>";
                                   }
                                   if ($.inArray("1", d) != -1 && $.inArray("6", d) != -1) {
                                       _tr += "<td >" + (this.WeekNCount) + "</td><td >" + (this.WeekNRate) + "</td>";
                                   }
                                   if ($.inArray("1", d) != -1 && $.inArray("7", d) != -1) {
                                       _tr += "<td >" + (this.MonthCount) + "</td><td >" + (this.MonthRate) + "</td>";
                                   }
                                   if ($.inArray("1", d) != -1 && $.inArray("8", d) != -1) {
                                       _tr += "<td >" + (this.MonthNCount) + "</td><td >" + (this.MonthNRate) + "</td>";
                                   }
                               }

                                if ($.inArray("2", d) != -1) {
                                    if ($.inArray("2", d) != -1 && $.inArray("4", d) != -1) {
                                        _tr += "<td >" + (this.DayActivityCount) + "</td><td >" + (this.DayActivityRate) + "</td>";
                                    }
                                    if ($.inArray("2", d) != -1 && $.inArray("5", d) != -1) {
                                        _tr += "<td >" + (this.WeekActivityCount) + "</td><td >" + (this.WeekActivityRate) + "</td>";
                                    }
                                    if ($.inArray("2", d) != -1 && $.inArray("6", d) != -1) {
                                        _tr += "<td >" + (this.WeekActivityNCount) + "</td><td >" + (this.WeekActivityNRate) + "</td>";
                                    }
                                    if ($.inArray("2", d) != -1 && $.inArray("7", d) != -1) {
                                        _tr += "<td >" + (this.MonthActivityCount) + "</td><td >" + (this.MonthActivityRate) + "</td>";
                                    }
                                    if ($.inArray("2", d) != -1 && $.inArray("8", d) != -1) {
                                        _tr += "<td >" + (this.MonthActivityNCount) + "</td><td >" + (this.MonthActivityNRate) + "</td>";
                                    }
                                }

                                if ($.inArray("3", d) != -1) {
                                    if ($.inArray("3", d) != -1 && $.inArray("4", d) != -1) {

                                        _tr += "<td >" + (this.DayFreeCallCount) + "</td><td >" + (this.DayFreeCallTime) + "</td><td >" + (this.DayFreeCallRate) + "</td>";
                                    }
                                    if ($.inArray("3", d) != -1 && $.inArray("5", d) != -1) {
                                        _tr += "<td >" + (this.WeekFreeCallCount) + "</td><td >" + (this.WeekFreeCallTime) + "</td><td >" + (this.WeekFreeCallRate) + "</td>";
                                    }
                                    if ($.inArray("3", d) != -1 && $.inArray("6", d) != -1) {
                                        _tr += "<td >" + (this.WeekNFreeCallCount) + "</td><td >" + (this.weekNFreeCallTime) + "</td><td >" + (this.WeekNFreeCallRate) + "</td>";
                                    }
                                    if ($.inArray("3", d) != -1 && $.inArray("7", d) != -1) {
                                        _tr += "<td >" + (this.MonthFreeCallCount) + "</td><td >" + (this.MonthFreeCalltime) + "</td><td >" + (this.MonthFreeCallRate) + "</td>";
                                    }
                                    if ($.inArray("3", d) != -1 && $.inArray("8", d) != -1) {
                                        _tr += "<td >" + (this.MonthNFreeCallCount) + "</td><td >" + (this.MonthNFreeCallTime) + "</td><td >" + (this.MonthNFreeCallRate) + "</td>";
                                    }
                                }
                                _tr += "</tr>";
                                 countAlldownloadUser +=parseInt(this.countdownloadUser);
                                 countAllactivationUser += parseInt(this.countactivationUser);
                                 countAllregisterUser += parseInt(this.countregisterUser);
                                 countAllUse  += parseInt(this.countUse);
                                 countAllUseKeys +=parseInt(this.countUseKeys);
                                 countUseAndKeysAlls  +=parseInt( this.countUseAndKeysAll);
                              // freeAllCallUserCount +=parseInt(this.freeCallUserCount);
                               DayAllFreeCallCount +=parseInt(this.DayFreeCallCount);

                                 //注册日留存总数
                               if ($.inArray("1", d) != -1) {
                                   DayAllCount += parseInt(this.DayCount);
                               }
//                               //注册周留存总数
//                               WeekAllCount+=this.WeekCount;
//                               //注册周内留存总数
//                                 WeekNAllCount+=this.WeekNCount;
//                               //注册月留存总数
//                               MonthAllCount+=this.MonthCount;
//                               //注册月内留存总数
//                               MonthNAllCount=+this.MonthNCount;
                                 //激活日留存总数
                               if ($.inArray("1", d) != -1) {
                                   DayAllActivityCount += parseInt(this.DayActivityCount);
                               }
//                               //激活周留存总数
//                               WeekAllActivityCount=+this.WeekActivityCount;
//                               //激活周内留存总数
//                               WeekAllActivityNCount=+this.WeekActivityNCount;
//                               //激活月留存总数
//                                 MonthAllActivityCount=+this.MonthActivityCount;
//                               //激活月内留存总数
//                               MonthAllActivityNCount=+this.MonthActivityNCount;
                                 //免费通话日留存总数
                           //    alert(this.DayFreeCallCount)
                              if ($.inArray("3", d) != -1) {
                                  DayAlFreeCallTime +=parseInt(this.DayFreeCallTime);
                             }
//                               //免费通话周留存总数
//                               WeekAllFreeCallCount=+this.WeekFreeCallCount;
//                               //免费通话周内留存总数
//                               WeekAllNFreeCallCount=+this.WeekNFreeCallCount;
//                               //免费通话月留存总数
//                                 MonthAllFreeCallCount=+this.MonthFreeCallCount;
//                               //免费通话月内留存总数
//                                MonthAllNFreeCallCount=+this.MonthNFreeCallCount;
                                $("#data_body").append(_tr);
                          });
                           var rate1=Math.round(countAllactivationUser/ countAlldownloadUser * 10000) / 100.00 + "%";
                           var rate2=Math.round(countAllregisterUser / countAllactivationUser * 10000) / 100.00 + "%";
                           lasttr  = "<tr>" +
                                   "<td rowspan='2'>合计</td>" +
                                   "<td rowspan='2'>" +countAlldownloadUser+ "</td>" +
                                   "<td rowspan='2'>" +countAllactivationUser + "</td>" +
                                   "<td rowspan='2'>"+rate1+"</td>" +
                                   "<td rowspan='2'>"+countAllregisterUser+"</td>" +
                                   "<td rowspan='2'>"+rate2+"</td>" +
                                 //  "<td rowspan='2'>"+freeAllCallUserCount+"</td>" +
                                   "<td rowspan='2'></td>" +
                                   "<td rowspan='2'></td>" +
                                   "<td rowspan='2'></td>" +
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
                               if ($.inArray("1", d) != -1 && $.inArray("5", d) != -1) {
                                   lasttr += "<td ></td><td ></td>";
                               }
                               if ($.inArray("1", d) != -1 && $.inArray("6", d) != -1) {
                                   lasttr += "<td ></td><td ></td>";
                               }
                               if ($.inArray("1", d) != -1 && $.inArray("7", d) != -1) {
                                   lasttr += "<td ></td><td ></td>";
                               }
                               if ($.inArray("1", d) != -1 && $.inArray("8", d) != -1) {
                                   lasttr += "<td ></td><td ></td>";
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
                              if ($.inArray("2", d) != -1 && $.inArray("5", d) != -1) {
                                  lasttr += "<td ></td><td ></td>";
                              }
                              if ($.inArray("2", d) != -1 && $.inArray("6", d) != -1) {
                                  lasttr += "<td ></td><td ></td>";
                              }
                              if ($.inArray("2", d) != -1 && $.inArray("7", d) != -1) {
                                  lasttr += "<td ></td><td ></td>";
                              }
                              if ($.inArray("2", d) != -1 && $.inArray("8", d) != -1) {
                                  lasttr += "<td ></td><td ></td>";
                              }
                           }
                           if ($.inArray("3", d) != -1) {
                               if ($.inArray("3", d) != -1 && $.inArray("4", d) != -1) {
                                   var rate= Math.round(DayAllFreeCallCount / countAllactivationUser * 10000) / 100.00 + "%";
                                   if(DayAllFreeCallCount==0&&countAllactivationUser==0){
                                       lasttr += "<td >" + DayAllFreeCallCount + "</td><td >"+DayAlFreeCallTime+"</td><td >" +0+"%" + "</td>";
                                   }else{
                                       lasttr += "<td >" + DayAllFreeCallCount + "</td><td >"+DayAlFreeCallTime+"</td><td >" +rate + "</td>";
                                   }

                               }
                               if ($.inArray("3", d) != -1 && $.inArray("5", d) != -1) {
                                   lasttr += "<td ></td><td ></td><td ></td>";
                               }
                               if ($.inArray("3", d) != -1 && $.inArray("6", d) != -1) {
                                   lasttr += "<td ></td><td ></td><td ></td>";
                               }
                               if ($.inArray("3", d) != -1 && $.inArray("7", d) != -1) {
                                   lasttr += "<td ></td><td ></td><td ></td>";
                               }
                               if ($.inArray("3", d) != -1 && $.inArray("8", d) != -1) {
                                   lasttr += "<td ></td><td ></td><td ></td>";
                               }
                           }
                           $("#data_body").append(lasttr);
                      });
           }
    }else {
           var obj=document.getElementsByName('parameter');  //选择所有name="'Germany'"的对象，返回数组
           //取到对象数组后，我们来循环检测它是不是被选中
           var s='';
           var d=[];
           var s1='';
           for(var i=0; i<obj.length; i++){
               if(obj[i].checked) {
                   s += obj[i].value + ',';  //如果选中，将value添加到变量s中
                   d.push(obj[i].value);
               }
               if(obj[i].checked)
                   s1+=obj[i].value+',';  //如果选中，将value添加到变量s中
           }
           $('#form0').attr("action", "${pageContext.request.contextPath}/analyse/userAnalyseAction!exportAllComplexStatistics.action",{sup:sup,sDate:sDate,eDate:eDate,parameter:s1});
           layer.load();
           $('#form0').submit();
        }
    }
    $(function () {
        getSuppliers();
    });
        function getSuppliers() {
            $.post("${pageContext.request.contextPath}/analyse/userAnalyseAction!getSuppliers.action",
                    function (data) {
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


</script>
</body>
</html>
