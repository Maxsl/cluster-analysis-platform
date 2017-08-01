<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>上传数据</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<div class="container">
      <div class="header clearfix">
      <hr>
        <nav>
          <ul class="nav nav-pills pull-right">
            <li role="presentation"><a href="./index.html">Home</a></li>
            <li role="presentation" class="active"><a href="./upload.jsp">Upload</a></li>
            <li role="presentation"><a href="./about.html">About</a></li>
          </ul>
        </nav>
        <h3 class="text-muted">时空数据聚类平台</h3>
      </div>
	
		<hr>
      <div class="jumbotron">
        <h2>输入数据文件格式：</h2>
		<h3>注意：****替代为tab键</h3>
        <p class="lead">
        0****40.757257****-73.983874****09b0e551693b7f074cb77da4f4da8809<br>
		0****40.755981****-73.984650****0a47083ab012e3af999b431a38318ff6<br>
		0****40.755981****-73.984650****0a47083ab012e3af999b431a38318ff6<br>
        </p>
        <br>
        <form method="post" action="/website/UploadServlet" enctype="multipart/form-data">
        <p>文件上传</p><input type="file" id="fileID" name="uploadFile" />
      </div>
      
      <p>选择你想要使用的算法并输入算法参数</p>
	<input type="radio" name="algorithm" value="kmeans" checked>使用kmeans进行聚类---输入聚类簇中心参数<input type="text" name="knum">
	<br><br>
	<input type="radio" name="algorithm" value="dbscan">使用DBSCAN聚类，输入阈值:扫描半径 (eps)<input type="text" name="eps"> 最小包含点数(minPts)<input type="text" name="minPts">
	<br><br>
	<input type="radio" name="algorithm" value="cfsdp">使用Clustering by fast search and density peaks
	<br><br>
	<input class="btn btn-lg btn-success" id="btn" type="submit" value="上传并提交" />
</form>
      <hr>
      <footer class="footer">
        <p>&copy; 2017 dzzxjl@NJUPT.</p>
      </footer>

    </div> <!-- /container -->
    <script type="text/javascript">
		document.getElementById("btn").onclick=function(e){
			if(document.getElementById("fileID").value==""){
				e.preventDefault();
				alert("请上传附件");
				document.getElementById("fileID").focus();
				return;
			}
		}
	</script>
</body>
</html>