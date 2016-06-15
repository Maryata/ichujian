// 全局数组，用来存放移除出当前的分类id
var removeGid = new Array();
// 定义Map，存放移除的分类的排序
var orderMap = {};
//var tagIdMap={};
$(function(){
    infoList();
    currList();
});
// 查询 当前所有的活动分类
function infoList(){
    var title = $("#title1").val();
    // 查询非当前的其他所有分类
    $.post("${pageContext.request.contextPath}/basedata/ekey/eKActivityIndexCategoryAction!getAllInfo.action",{title:title},
        function(data){
            var json = eval("("+data+")");
            var count = json[0].count;// 数据总数
            $("#page11").empty();
            if(json != 0){
                setTotal(count,"giftTotal");
                // 所有分类
                getinfoListPage("${pageContext.request.contextPath}/basedata/ekey/eKActivityIndexCategoryAction!getAllInfo.action",title);
            }
        });
    $.post("${pageContext.request.contextPath}/basedata/ekey/eKActivityIndexCategoryAction!getAllInfo.action",{title:title},
        function(data){
            json = eval("("+data+")");
            var list = json[0].list;
            $("#table1").empty();
            $(list).each(function(){
                var _str = "<tr class='biaodan-q'><input type='hidden' name='SID' value="+this.C_ID+">";
                _str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
                _str += "<td align='center' name='STITLE'>"+this.C_NAME+"</td></tr>";
                $("#table1").append(_str);
            });
        });
}
// 中间表所有活动分类
function currList(){
    var title = $("#title2").val();
    // 查询当前的分类总数
    $.post("${pageContext.request.contextPath}/basedata/ekey/eKActivityIndexCategoryAction!getCurrInfo.action",{title:title},
        function(data){
            var json = eval("("+data+")");
            var count = json[0].count;// 数据总数
            $("#page22").empty();
            if(json != 0){
                setTotal(count,"cateTotal");
                // 当前中的分类
                getCurrInfoPage("${pageContext.request.contextPath}/basedata/ekey/eKActivityIndexCategoryAction!getCurrInfo.action",title);
            }
        });
    $.post("${pageContext.request.contextPath}/basedata/ekey/eKActivityIndexCategoryAction!getCurrInfo.action",{title:title},
        function(data){
            json = eval("("+data+")");
            var list = json[0].list;
            $("#table2").empty();
            $(list).each(function(){
                var _str = "<tr class='biaodan-q'><input type='hidden' name='SID' value="+this.C_CID+">";
                _str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
                _str += "<td align='center' name='STITLE'>"+this.C_NAME+"</td>";
                _str += "<td align='center'><input type='text' name='SORDER' value='"+(this.C_ORDER||'')+"'/></td></tr>";
                $("#table2").append(_str);
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
// 分页查询所有分类
function getinfoListPage(url,title) {
    laypage({
        cont: 'page11',
        pages: document.getElementById("giftTotal").value,
        curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
        }(),
        jump: function(e, first){ //触发分页后的回调
            if(!first){ //一定要加此判断，否则初始时会无限刷新
                $.post(url,{page:e.curr,title:title},
                    function(data){
                        json = eval("("+data+")");
                        var list = json[0].list;
                        $("#table1").empty();
                        $(list).each(function(){
                            var _str = "<tr class='biaodan-q'><input type='hidden' name='SID' value="+this.C_ID+">";
                            _str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
                            _str += "<td align='center' name='STITLE'>"+this.C_NAME+"</td></tr>";
                            $("#table1").append(_str);
                        });
                    });
            }
        }
    });
}
// 分页查询当前中的分类
function getCurrInfoPage(url,title) {
    laypage({
        cont: 'page22',
        pages: document.getElementById("cateTotal").value,
        curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
        }(),
        jump: function(e, first){ //触发分页后的回调
            if(!first){ //一定要加此判断，否则初始时会无限刷新
                $.post(url,{page:e.curr,title:title},
                    function(data){
                        json = eval("("+data+")");
                        $("#table2").empty();
                        $(json[0].list).each(function(){
                            var _str = "<tr class='biaodan-q'><input type='hidden' name='SID' value="+this.C_CID+" >";
                            _str += "<td align='center'><input type='checkbox' name='ckbox1'></td>";
                            _str += "<td align='center' name='STITLE'>"+d.C_NAME+"</td>";
                            _str += "<td align='center'><input type='text' name='SORDER' value='"+(this.C_ORDER||'')+"'></td></tr>";
                            $("#table2").append(_str);
                        });
                    });
            }
        }
    });
}
// 上  ==》 下
function down(){
// 获取上面table中选中，并遍历
    $("#table1 input:checked").each(function(){
        // 获取当前checkbox所在的行
        var tablerow = $(this).parent("td").parent("tr");
        // 获取id、name、size
        var id = tablerow.find("input[name='SID']").val();
        var title = tablerow.find("[name='STITLE']").html();
        var order = orderMap[id];
        // 填入下面的table
            if(order){
                $("#table2").append("<tr class='biaodan-q'><input type='hidden' name='SID' value="+id+"><td align='center'><input type='checkbox' name='ckbox2'></td><td align='center' name='STITLE'>"+title+"</td>" +
                    "<td align='center'><input name='SORDER' value="+order+" ></td>" +
                    "</tr>");
            }else{
                $("#table2").append("<tr class='biaodan-q'><input type='hidden' name='SID' value="+id+"><td align='center'><input type='checkbox' name='ckbox2'></td><td align='center' name='STITLE'>"+title+"</td>" +
                    "<td align='center'><input name='SORDER' ></td>" +
                    "</tr>");
            }
        // 移除
        tablerow.remove();
    }) ;
}
// 下  ==》 上
function up(){
    $("#table2 input:checked").each(function(){
        var tablerow = $(this).parent("td").parent("tr");
        var id = tablerow.find("input[name='SID']").val();
        // 将要移除出当前的分类id存入全局数组
        removeGid.push(id);
        // 将要移除出当前的分类的order存入全局Map
        orderMap[id] = tablerow.find("[name='SORDER']").val();
        var title = tablerow.find("[name='STITLE']").html();
        $("#table1").append("<tr class='biaodan-q'><input type='hidden' name='SID' value="+id+"><td align='center'><input type='checkbox' name='ckbox1'></td><td align='center' name='STITLE'>"+title+"</td></tr>");
        tablerow.remove();
    }) ;
}
function allDown(){
    $("#table1 input").prop("checked",true);
    down();
}
function allUp(){
    $("#table2 input").prop("checked",true);
    up();
}
// 保存
function submitt(){
    // 获取id
    var sid = new Array();
    var order = new Array();
    // 获取下面表的所有分类id
    $("#form1 [name='SID']").each(function(){
        // 存入数组
        sid.push($(this).val());
    });
    $("#form1 [name='SORDER']").each(function(){
        var _order = $(this).val();
        // 如果排序值为空，设置其值为""
        if(_order=="" || _order==null){
            _order = "n";
        }
        // 存入数组
        order.push(_order);
    });
    // 转成字符串
    sid = sid.toString();
    order = order.toString();
    // 移除的分类id转成字符串
    removeGid = removeGid.toString();
    $.post("${pageContext.request.contextPath}/basedata/ekey/eKActivityIndexCategoryAction!handleIndexCategory.action",{sid:sid,removeGid:removeGid,order:order},function(data){
        window.location.reload();
    });
}