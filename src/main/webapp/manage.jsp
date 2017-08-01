<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">	
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
	<title>聚类结果</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">

</head>
<body>

<div class="container">
      <div class="header clearfix">
      <hr>
        <nav>
          <ul class="nav nav-pills pull-right">
            <li role="presentation"><a href="./index.html">Home</a></li>
            <li role="presentation"><a href="./upload.jsp">Upload</a></li>
            <li role="presentation"><a href="./about.html">About</a></li>
          </ul>
        </nav>
        <h3 class="text-muted">时空数据聚类平台</h3>
      </div>
	
		<hr>
      <div class="jumbotron">
        <h2>聚类结果：</h2>
        <p class="lead">
        <p>文件上传状态${message}---文件路径信息${filePath}</p>
        <p>所使用算法${algorithm}</p>
        <p>聚类中心个数${knum}</p>
        <p>图片存储路径${graphPath}</p>
        </div>
        <img alt="聚类图片" src="/graph/${graphPath}">
        </p>
        <br>
        
      
      
      <hr>
      <footer class="footer">
        <p>&copy; 2017 dzzxjl@NJUPT.</p>
      </footer>

    </div> <!-- /container -->
</body>
</html>
