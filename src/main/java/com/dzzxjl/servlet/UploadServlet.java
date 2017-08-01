
package com.dzzxjl.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import com.dzzxjl.data.DataReader;
import com.dzzxjl.main.DensityPeakClusterTest;
import com.dzzxjl.main.KmeansTest;

@WebServlet("/UploadServlet")
public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    String algorithm = "";
    String filePath; //定义上传文件保存路径
    String graphPath; //定义图片存储路径
    int knum;
    
    /**
     * 上传数据及保存文件
     */
    protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		// 检测是否为多媒体上传
		if (!ServletFileUpload.isMultipartContent(request)) {
		    // 如果不是则停止
		    PrintWriter writer = response.getWriter();
		    writer.println("Error: 表单必须包含 enctype=multipart/form-data");
		    writer.flush();
		    return;
		}
 
        // 配置上传参数
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置内存临界值 - 超过后将产生临时文件并存储于临时目录中
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // 设置临时存储目录
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        // 中文处理
        upload.setHeaderEncoding("UTF-8"); 

        // 构造临时路径来存储上传的文件
        // 这个路径相对当前应用的目录
//        System.out.println("当前目录" + this.getServletContext().getRealPath(""));
//        System.out.println(request.getRealPath(""));
//        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload") ;
//        String uploadPath = this.getServletContext().getResource("/upload").toString();
       
//         List items;
//         items = upload.parseRequest(request);
        // 如果目录不存在则创建
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
 
        try {
            // 解析请求的内容提取文件数据
            @SuppressWarnings("unchecked")
//            List<FileItem> formItems = upload.parseRequest((RequestContext) request);
            List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(request));
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    // 处理不在表单中的字段
//                    if ("algorithm".equals(item.getFieldName())) {
//                        System.out.println(new String(item.getString("UTF-8")));
//                    } 
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
//                        String filePath = uploadPath + File.separator + fileName;
                        filePath = "/Users/dzzxjl/Documents/data/txt/" + fileName ;
                        File storeFile = new File(filePath);
                        // 在控制台输出文件的上传路径
                        System.out.println(filePath);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        request.setAttribute("filePath", filePath);
                        request.setAttribute("message",
                            "文件上传成功!");
                    }
                    else {
//                    	 System.out.print(new String(item.getString("UTF-8")));
                         if ("algorithm".equals(item.getFieldName())) {
                        	 algorithm = item.getString("UTF-8");
                        	 System.out.println(algorithm);
                             request.setAttribute("algorithm", algorithm);
                         } 
                         if("knum".equals(item.getFieldName())){
                        	 System.out.println("mark");
                        	 String temp = item.getString();
                        	 if(!temp.equals("")) {
                        		 knum = Integer.parseInt(item.getString("UTF-8"));
                            	 request.setAttribute("knum", knum);
                        	 }
                         }
					}
                }
            }
        } catch (Exception ex) {
            request.setAttribute("message",
                    "错误信息: " + ex.getMessage());
        }
        
        
        /*
         * 通过算法进行处理
         */
        switch (algorithm) {
		case "kmeans":
			KmeansTest kmeansTest = new KmeansTest();
	        graphPath = kmeansTest.execute(filePath,knum);
			break;
		case "dbscan":
			kmeansTest = new KmeansTest();
	        graphPath = kmeansTest.execute(filePath,knum);
			break;
		case "cfsdp":
			DensityPeakClusterTest cfsdpTest = new DensityPeakClusterTest();
	        graphPath = cfsdpTest.execute(filePath);
			break;
		default:
			break;
		}
        
        request.setAttribute("graphPath", graphPath + ".jpeg");
        
        // 跳转到 message.jsp
        getServletContext().getRequestDispatcher("/manage.jsp").forward(
                request, response);
    }
}