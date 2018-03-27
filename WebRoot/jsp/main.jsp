<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html lang="zh-CN">
<head>
<base href="<%=basePath%>">
<title>ErrorCurps</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<meta name="description" content="">
<meta name="author" content="ChenZeWei">
<link rel="icon" href="<%=basePath%>resources/img/cba6j-zs53m-001.ico">
<link href="<%=basePath%>/resources/myCss/index.css" rel="stylesheet">
<link href="<%=basePath%>/resources/myCss/main.css" rel="stylesheet">
<!-- Bootstrap core CSS -->
<link
	href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">


<!-- Custom styles for this template -->
<link href="starter-template.css" rel="stylesheet">

<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>

	<nav class="navbar navbar-inverse navbar-fixed-top">
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">ErrorCorpus</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
				<li class="active"><a href="#">Home</a></li>
				<li><a href="#about">About</a></li>
				<li><a href="#contact">Contact</a></li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
	</nav>

	<div class="container">
		<div class="col-sm-12 padding">
			<h1>属性选择</h1>
			<p>按照需求进行选择添加错误种类和错误数量</p>
			<hr>
		</div>

		<form class="form-signin col-mx-12 col-sm-10">
			<h2 class="form-signin-heading">请选择</h2>
			<ul class="list-group ">
				<li class="list-group-item form-inline">
					<div class="row">
						<label for="inputErrorType" class="sr-only">请选择错误类型</label> <select
							id="inputErrorType" class="form-control left-padding"
							title="错误类型">
							<option>错误类型</option>
							<option>易错词错误</option>
							<option>形似词错误</option>
							<option>音近字错误</option>
							<option>标点错误</option>
							<option>数字错误</option>
						</select>
						<div class="col-lg-3">
							<div class="input-group">
								<span class="input-group-addon"> <input type="radio"></span>
								<input type="text" class="form-control">
							</div>
							<!-- /input-group -->
						</div>
						<!-- /.col-lg-6 -->
						<div class="col-lg-3">
							<div class="input-group">
								<span class="input-group-addon"> <input type="radio"></span>
								<input type="text" class="form-control">
							</div>
							<!-- /input-group -->
						</div>
						<!-- /.col-lg-6 -->
					</div>
				</li>
			</ul>


			<hr>
			<button class="btn btn-lg btn-primary btn-block" type="submit">Sign
				in</button>
		</form>



	</div>
	<!-- /.container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script>
		window.jQuery
				|| document
						.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')
	</script>
	<script
		src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->

</body>
</html>


