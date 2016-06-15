/**
 * Created by vpc on 2016/3/7.
 */
//初始化加载
$(function(){
    info();//活动分类
});
//------------------------------活动分类-----------------------------------------------
    function info() {
        var headLine = $("#headLines").val();
        // 查询应用总数
        $.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!ekActivityCategoryList.action",{headLine:headLine},
            function (data) {
                var json = $.parseJSON(data);
                var count = json[0].count;// 数据总数
                $("#page11").empty();
                if (count != 0) {
                    setTotal(count, "cateTotal");
                    // 分页显示活动分类
                    getCatePage("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!ekActivityCategoryList.action",headLine);
                }
            });
        // 查询分类(第一页)
        $.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityCategoryInfoAction!ekActivityCategoryList.action",{headLine:headLine},
            function (data) {
                var json = $.parseJSON(data);
                var list = json[0].list;
                $("#mytable").empty();
                $(list).each(function () {
                    var _s = "<tr><td><input class=' w_20 div_h20 pointer'  type='radio' name='radio' data-cid='"+this.C_ID+"' data-cname='"+this.C_NAME+"' data-img='"+this.C_IMG+"' data-type='0'></td>";
                    _s += "<td>" + (this.C_ID || '') + "</td>";
                    _s += "<td>" + (this.C_NAME || '') + "</td>";
                    _s += "<td><a class='c_yellow m_l10' title='预览' href='javascript:void(0);' data-val='"+this.C_IMG+"' onclick='selectPhoto(\""+this.C_IMG+"\");'><i class='icon iconfont icon-yulan f_18'></i></a>";
                    _s += "</td></tr>";
                    $("#mytable").append(_s);
                });
            });
    }

// 设置总页数
function setTotal(count,pageTotal){
    var totalPage = 0;
    var rows = 5;// 每页显示条数
    totalPage = 1;
    // 计算总页数
    totalPage = parseInt((count - 1) / rows) + 1;
    $("#"+pageTotal).val(totalPage);
}
// 分页显示活动分类
function getCatePage(url,headLine) {
    laypage({
        cont: 'page11',
        pages: document.getElementById("cateTotal").value,
        curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
        }(),
        jump: function(e, first){ //触发分页后的回调
            if(!first){ //一定要加此判断，否则初始时会无限刷新
                $.post(url,{page:e.curr,headLine:headLine},
                    function(data){
                        var json = $.parseJSON(data);
                        var list = json[0].list;
                        $("#mytable").empty();
                        $(list).each(function(){
                            var _s = "<tr><td><input class=' w_20 div_h20 pointer'  type='radio' name='radio' data-cid='"+this.C_ID+"' data-cname='"+this.C_NAME+"' data-img='"+this.C_IMG+"' data-type='0'></td>";
                            _s += "<td>" + (this.C_ID || '') + "</td>";
                            _s += "<td>" + (this.C_NAME || '') + "</td>";
                            _s += "<td><a class='c_yellow m_l10' title='预览' data-val='"+this.C_IMG+"' onclick='selectPhoto(\""+this.C_IMG+"\");'><i class='icon iconfont icon-yulan f_18'></i></a>";
                            _s += "</td></tr>";
                            $("#mytable").append(_s);
                        });
                    });
            }
        }
    });
}

//---------------------活动详情------------------------------

function infoList(){
    // 查询应用总数
    var headLine = $("#headLines").val();
    var title = $("#title").val();
    $.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!ekActivityInfoList.action",{title:title,headLine:headLine},
        function(data){
            var json = $.parseJSON(data);
            var count = json[0].count;// 数据总数
            $("#page11").empty();
            if(count != 0){
                setTotal(count,"cateTotal");
                // 分页显示活动详情
                getActivityPage("${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!ekActivityInfoList.action",title,headLine);
            }
        });
    // 查询活动详情(第一页)
    $.post("${pageContext.request.contextPath}/basedata/ekey/EKActivityAction!ekActivityInfoList.action",{title:title,headLine:headLine},
        function(data){
            var json = $.parseJSON(data);
            var list = json[0].list;
            $("#mytable").empty();
            $(list).each(function(){
                var _s = "<tr><td><input class=' w_20 div_h20 pointer'  type='radio' name='radio' data-cid='"+this.C_ID+"' data-cname='"+this.C_TITLE+"' data-img='"+this.C_IMG+"' data-type='1'></td>";
                _s += "<td>" + (this.C_ID || '') + "</td>";
                _s += "<td>" + (this.C_TITLE || '') + "</td>";
                _s += "<td><a class='c_yellow m_l10' title='预览' data-val='"+this.C_IMG+"' onclick='selectPhoto(\""+this.C_IMG+"\");'><i class='icon iconfont icon-yulan f_18'></i></a>";
                _s += "</td></tr>";
                $("#mytable").append(_s);
            });
        });
}
// 分页显示活动详情
function getActivityPage(url,title,headLine) {
    laypage({
        cont: 'page11',
        pages: document.getElementById("cateTotal").value,
        curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
        }(),
        jump: function(e, first){ //触发分页后的回调
            if(!first){ //一定要加此判断，否则初始时会无限刷新
                $.post(url,{page:e.curr,title:title,headLine:headLine},
                    function(data){
                        var json = $.parseJSON(data);
                        var list = json[0].list;
                        $("#mytable").empty();
                        $(list).each(function(){
                            var _s = "<tr><td><input class=' w_20 div_h20 pointer'  type='radio' name='radio' data-cid='"+this.C_ID+"' data-cname='"+this.C_TITLE+"' data-img='"+this.C_IMG+"' data-type='1'></td>";
                            _s += "<td>" + (this.C_ID || '') + "</td>";
                            _s += "<td>" + (this.C_TITLE || '') + "</td>";
                            _s += "<td><a class='c_yellow m_l10' title='预览' data-val='"+this.C_IMG+"' onclick='selectPhoto(\""+this.C_IMG+"\");'><i class='icon iconfont icon-yulan f_18'></i></a>";
                            _s += "</td></tr>";
                            $("#mytable").append(_s);
                        });
                    });
            }
        }
    });
}
//复选框双击事件
$(document).on('click',"input[name='radio']",function(){
    var _obj = $(this);
    parent.setMData({eid: _obj.attr('data-cid') ,logo : _obj.attr('data-img') ,title: _obj.attr("data-cname"),type: _obj.attr("data-type")});
});




