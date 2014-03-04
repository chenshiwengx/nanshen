<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="common/bootstrap.jsp"/>
	<title>用户注册</title>
  </head>
  <body>
  <div class="container" style="margin-top:30px;">
      <div class="masthead">
        <ul class="nav nav-pills pull-right">
          <li class="active"><a href="#">主页</a></li>
          <li><a href="#">关于</a></li>
          <li><a href="#">联系</a></li>
        </ul>
        <h3 class="muted">SSH2</h3>
      </div>
      <hr>
      <div class='row'>
      <div class='span7'>
  		<form class="form-horizontal" action='<%=basePath%>wenwen/newuser.action'>
		  <div class="control-group">
		    <label class="control-label" for="EMAIL">登录账号</label>
		    <div class="controls">
		      <input type="text" id="EMAIL" name="user.email"  placeholder="inputEmail" style='height:30px;'>
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="PSW">登录密码</label>
		    <div class="controls">
		      <input type="password" id="PSW" name="user.psw" placeholder="登录密码" style='height:30px;'>
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="PSW">再次输入登录密码</label>
		    <div class="controls">
		      <input type="password" id="PSW" name="PSW" placeholder="登录密码" style='height:30px;'>
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="LOGINNAME">昵称</label>
		    <div class="controls">
		      <input type="text" id="LOGINNAME" name="user.loginname"  placeholder="昵称" style='height:30px;'>
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="SEX">性别</label>
		    <div class="controls">
		      <input type="text" id="SEX" name="user.sex" placeholder="性别" style='height:30px;'>
		    </div>
		  </div>
		  <div class="control-group">
		    <label class="control-label" for="TEL">联系电话</label>
		    <div class="controls">
		      <input type="text" id="TEL" name="user.tel" placeholder="联系电话" style='height:30px;'>
		    </div>
		  </div>
		  <div class="control-group">
		    <div class="controls">
		      <label class="checkbox">
		        <input type="checkbox">同意条款
		      </label>
		      <button type="submit" class="btn btn-primary">注册</button>
		    </div>
		  </div>
		</form>
		 </div>
		 <form id="uploadform" enctype="multipart/form-data" action="<%=basePath%>upload/onefile.action" method="post">  
 			 <input  id="uploadFile"type="file" name="uploadFile">  
 			 <input type="text" name='dd'/>同意条款
     	 <div class='span5'>
     	 	<img id="pic"src="..." class="img-polaroid" style='width:150;height:150px;'>
     	 </div>
     	 </form> 
     	 <button  id='J_submit' class="btn btn-info" onclick="fileUpLoad()">上传</button>
     	 </div>
		<hr>
     	 <div class="footer">
       		 <p>© Company 2013</p>
     	 </div>
     	 </div>
  </body>
 
</html>
