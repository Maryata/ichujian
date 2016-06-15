<#macro accountMenu currentMenu="account">
<div class="user-topnav">
  <a class="user-topnav-1${(currentMenu=="account")?string('b', '')}" href="${basepath}/account/account">个人资料</a>
  <a class="user-topnav-2${(currentMenu=="topwd")?string('b', '')}" href="${basepath}/account/topwd">修改密码</a>
  <a class="user-topnav-3${(currentMenu=="orders")?string('b', '')}" href="${basepath}/account/orders">我的订单</a>
  <a class="user-topnav-4${(currentMenu=="address")?string('b', '')}" href="${basepath}/account/address">配送地址</a>
</div>
</#macro>