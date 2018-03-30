<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
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
<link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->

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
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
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
		<div class="row col-sm-12 padding">
			<h3 class="col-sm-6 col-lg-4">选择需要上传的原始语料文件：</h3>
			
			
			<button type="button" class="btn btn-primary col-sm-4 col-lg-3 " style="margin: 15px auto; " data-toggle="modal" data-target="#exampleModal">选择文件</button>
			<div class="col-sm-2 col-lg-2">${message}</div>
			<!-- Modal -->
			<div class="modal fade" id="exampleModal" tabindex="-1" role="dialog"
				aria-labelledby="exampleModalLabel" aria-hidden="true">
				<div class="modal-dialog modal-lg" role="document">
					<div class="modal-content">
						<div class="modal-header">
							<h5 class="modal-title" id="exampleModalLabel">文件上传</h5>
							<button type="button" class="close" data-dismiss="modal"
								aria-label="Close">
								<span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="modal-body">
							<div class="file-loading">
								<input id="input-b9" name="upload" multiple type="file">
							</div>
							
						</div>
						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">关闭</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-sm-12 padding">
			<h3>属性选择</h3>
			<p>按照需求进行选择添加错误种类和错误数量</p>
			
		</div>

		<form class="form-signin col-mx-12 col-sm-12" action="${basePath }generate!generate" method="post">
			<hr>
			<ul class="list-group">
				<li class="list-group-item form-inline" id="err">
					<div class="row" >
						<div  class="col-lg-2 col-sm-6 Etype " style="height: 44px">
							<label for="inputErrorType" class="sr-only ">请选择错误类型</label> 
							<select  name="inputErrorType" class="form-control left-padding disabled" title="错误类型">
								<option value = "">错误类型</option>
								<option value = "commom">易错词错误</option>
								<option value = "Similiar">形似词错误</option>
								<option value = "soundSimiliar">音近字错误</option>
								<option value = "punctuation">标点错误</option>
								<option value = "number">数字错误</option>
							</select>
						</div>
						<div class="col-lg-2 col-sm-6 Enumber" ><b>请输入错误数量:</b></div>
						<div class="col-lg-3 col-sm-6" id="inputErrorNumber">
							<div class="input-group" >
								<span class="input-group-addon"> 
								<input type="radio" onclick="disTxt(this)" name = "errorNumber0" value="number"></span>
								<input type="text" name="errTxt_number"  class="form-control" disabled>
								<div class="input-group-addon">个</div>
							</div>
							<!-- /input-group -->
						</div>
						<div class="col-lg-1 or">
							<b>or</b>
							<!-- /input-group -->
						</div>
						<!-- /.col-lg-6 -->
						<div class="col-lg-3 col-sm-6">
							<div class="input-group">
								<span class="input-group-addon"> 
								<input type="radio" onclick="disTxt(this)" name ="errorNumber0" value="percent"></span>
								<input type="text" name="errTxt_percent"  class="form-control" disabled>
								<div class="input-group-addon">%</div>
							</div>
							<!-- /input-group -->
						</div>
						<!-- /.col-lg-6 -->
						
						<div class="col-lg-1 add" >
							<img src="<%=basePath %>/resources/img/mult.svg" class="img-responsive">
						</div>
					</div>
				</li>
			</ul>



			<hr>
			<button class="btn btn-lg btn-primary" type="submit">提交</button>
		</form>



	</div>
	<!-- /.container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	
	<!--  bootstrap fileinput-->
	<link href="<%=basePath %>resources/bootrap-fileinput/css/fileinput.css" rel="stylesheet">
	<script type="text/javascript" src="<%=basePath %>resources/bootrap-fileinput/js/fileinput.js"></script>
	<script type="text/javascript" src="<%=basePath %>resources/bootrap-fileinput/js/locales/zh.js"></script>
	
	
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script>
		$(document).on('ready', function() {
			$("#input-b9").fileinput({
				showPreview : false,
				language: 'zh', //设置语言
				showUpload : true,
				uploadsync: true,
				allowedFileExtensions : ["txt"],
				uploadUrl: 'uploadAction',
				enctype: 'multipart/form-data'
			})
		});
	</script>
	<script type="text/javascript">
		function disTxt(object){
				var InputName = $(object).attr("name");
				$("input[name="+InputName+"]").parents().next("input").attr("disabled","disabled");
				console.log(selectValue(object));
				if( selectValue(object) == 1 ){
					$("input[name="+InputName+"]:checked").parents().next("input").removeAttr("disabled")
				}
			};
			
		function checkNum(){
			var num = $(this).val();
			var ival = parseInt(num);
			if(isNaN(ival)){
				alert("请输入数字，请勿输入其他符号!")
				$(this).val("");
			}else{
				if(ival < 0){
					alert("请输入大于0的数字！")
					$(this).val("");
				}				
			}
		};
			
		function checkPercent(){
			var num = $(this).val();
			var ival = parseFloat(num);
			if(isNaN(ival)){
				alert("请输入数字，请勿输入其他符号!")
				$(this).val("");
			}else{
				if(ival<=0 || ival >=100){
					alert("请输入0到100之间的数字！")
					$(this).val("");
				}				
			}
		}
		function checkSelect(){
			console.log(selectValue(this));
			if( selectValue(this) == -1 ){
				alert("请先选择错误类型！");
				$(this).attr("checked",false);
			}
		}
		
		function selectValue(obj){
			var val = $(obj).parents("div.row").find("div.Etype > select").val();
			if( val == "")
				return -1;
			else
				return 1;
		}
		function delli(){
			var id = $(this).attr("class");
			$("li[id="+id+"]").remove();
		}
		
		$().ready(function(){
			var clickCount = 0;
			
			$("div.add").click(function(){
				var htmladd="";
				clickCount +=1;
		        htmladd +='<li class="list-group-item form-inline" id="err'+clickCount+'">';
		        htmladd +='<div class="row" >';
				htmladd +='<div class="col-lg-2 col-sm-6 Etype" style="height: 44px">';		
				htmladd +='<label for="inputErrorType" class="sr-only ">请选择错误类型</label>';
				htmladd +='<select name="inputErrorType" class="form-control left-padding" title="错误类型">';			
				htmladd +='<option value = "">错误类型</option>';				
				htmladd +='<option value = "commom">易错词错误</option>';					
				htmladd +='<option value = "Similiar">形似词错误</option>';						
				htmladd +='<option value = "soundSimiliar">音近字错误</option>';				
				htmladd +='<option value = "punctuation">标点错误</option>';					
				htmladd +='<option value = "number">数字错误</option>';
				htmladd +='</select>';	
				htmladd +='</div>';						
				htmladd +='<div class="col-lg-2 col-sm-6 Enumber" ><b>请输入错误数量:</b></div>';											
				htmladd +='<div class="col-lg-3 col-sm-6" id="inputErrorNumber">';					
				htmladd +='<div class="input-group">';						
				htmladd +='<span class="input-group-addon">';						
				htmladd +='<input type="radio" onclick="disTxt(this)" name = "errorNumber'+clickCount+'"  value="number" ></span>';	
				htmladd +='<input type="text" name="errTxt_number" class="form-control" disabled>';						
				htmladd +='<div class="input-group-addon">个</div>';					
				htmladd +='</div>';	
				htmladd +='<!-- /input-group -->';				
				htmladd +='</div>';				
				htmladd +='<div class="col-lg-1 or">';				
				htmladd +='<b>or</b>';					
				htmladd +='<!-- /input-group -->';						 
				htmladd +='</div>';						
				htmladd +='<!-- /.col-lg-6 -->';						
				htmladd +='<div class="col-lg-3 col-sm-6">';						
				htmladd +='<div class="input-group">';				
				htmladd +='<span class="input-group-addon"> ';				
				htmladd +='<input type="radio" onclick="disTxt(this)" name ="errorNumber'+clickCount+'"  value="percent" ></span> ';			
				htmladd +='<input type="text" name = "errTxt_percent" class="form-control" disabled>';			
				htmladd +='<div class="input-group-addon">%</div>';				
				htmladd +='</div>';				
				htmladd +='<!-- /input-group -->';				
				htmladd +='</div>';				
				htmladd +='<!-- /.col-lg-6 -->';				
			
				//添加删除按钮
				htmladd +='<div class="col-lg-1 del" >';
				htmladd +='<img style="width:80%;margin: 0 auto;"  class = "err'+clickCount+'" src="<%=basePath %>/resources/img/del2.svg" class="img-responsive">';
				htmladd +='</div>';	
				htmladd +='</div>';					
				htmladd +='</li>';
				
				
				
				$("ul.list-group").append(htmladd);
				$("input[name='errTxt_number']").bind("blur",checkNum);
				$("input[name='errTxt_percent']").bind("blur",checkPercent);
				$("img[class^='err']").bind("click",delli);
				$("input[type='radio']").bind("click",checkSelect);
			})
			
			$("input[name='errTxt_number']").blur(checkNum);
			$("input[name='errTxt_percent']").blur(checkPercent);
			$("input[type='radio']").bind("click",checkSelect);
		})
	</script>
</body>
</html>


