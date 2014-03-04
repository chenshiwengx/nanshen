<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<jsp:include page="common/bootstrap.jsp"/>
</head>
<body>
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">男神养成记</a>
        </div>
        <div class="navbar-collapse collapse">
          <form class="navbar-form navbar-right" role="form">
            <div class="form-group">
              <input type="text" placeholder="Email" class="form-control">
            </div>
            <div class="form-group">
              <input type="password" placeholder="Password" class="form-control">
            </div>
            <button type="submit" class="btn btn-success">登陆</button>
            <button class="btn btn-primary " data-toggle="modal" data-target="#myModal">
  				注册
			</button>
          </form>
        </div><!--/.navbar-collapse -->
      </div>
   </div>
   <div class="jumbotron">
      <div class="container">
        <h1></h1>
        <p>人生虽有终点，生命却是无涯，生活，是一种经历，也是一种体验。种下一个善念，收获一种良知；种下一种良知，收获一种道德；种下一种道德，收获一种习惯；种下一种习惯，收获一种性格；种下一种性格，收获一个人生。 </p>
      </div>
    </div>
<div class="container marketing">
      <div class="row featurette">
        <div class="col-md-7">
          <h2 class="featurette-heading">And lastly, this one. <span class="text-muted">Checkmate.</span></h2>
          <p class="lead">Donec ullamcorper nulla non metus auctor fringilla. Vestibulum id ligula porta felis euismod semper. Praesent commodo cursus magna, vel scelerisque nisl consectetur. Fusce dapibus, tellus ac cursus commodo.</p>
        </div>
        <!-- <div class="col-md-5">
          <img class="featurette-image img-responsive" data-src="holder.js/500x500/auto" alt="500x500" src="img/camera_美化000.jpg">
        </div> -->
      </div>

      <hr class="featurette-divider">

      <!-- /END THE FEATURETTES -->
      <!-- FOOTER -->
      <footer>
        <p class="pull-right"><a href="#">Back to top</a></p>
        <p>© 2014 Company, xiao2.  <a href="#">350746707@qq.com</a> ·<a href="#">kiddwen</a></p>
      </footer>

    </div>
    <!-- Modal -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	        <h4 class="modal-title" id="myModalLabel">欢迎你哦，亲~</h4>
	      </div>
	      <div class="modal-body">	        
	      <form role="form">
			  <div class="form-group">
			    <label for="exampleInputEmail1">登陆邮箱</label>
			    <input type="email" class="form-control" id="exampleInputEmail1" placeholder="Enter email">
			  </div>
			  <div class="form-group">
			    <label for="exampleInputPassword1">登录密码</label>
			    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
			  </div>
			   <div class="form-group">
			    <label for="exampleInputPassword1">确认密码</label>
			    <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
			  </div>
			   <div class="form-group">
			    <label for="exampleInputEmail1">登陆昵称</label>
			    <input  class="form-control" id="exampleInputEmail1" placeholder="Enter email">
			  </div>
			   <div class="form-group">
			    <label for="exampleInputEmail1">个人简介</label>
				 <textarea class="form-control" id="exampleInputEmail1"  rows="3"></textarea>
			  </div>
		 </form>	       	        
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary " data-dismiss="modal">确定</button>
	        <button type="button" class="btn btn-default"  data-dismiss="modal">取消</button>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</body>
</html>
