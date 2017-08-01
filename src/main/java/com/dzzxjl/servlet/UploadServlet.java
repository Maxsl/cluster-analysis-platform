
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
    
    // �ϴ�����
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB
    
    String algorithm = "";
    String filePath; //�����ϴ��ļ�����·��
    String graphPath; //����ͼƬ�洢·��
    int knum;
    
    /**
     * �ϴ����ݼ������ļ�
     */
    protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		// ����Ƿ�Ϊ��ý���ϴ�
		if (!ServletFileUpload.isMultipartContent(request)) {
		    // ���������ֹͣ
		    PrintWriter writer = response.getWriter();
		    writer.println("Error: ��������� enctype=multipart/form-data");
		    writer.flush();
		    return;
		}
 
        // �����ϴ�����
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // �����ڴ��ٽ�ֵ - �����󽫲�����ʱ�ļ����洢����ʱĿ¼��
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        // ������ʱ�洢Ŀ¼
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
 
        ServletFileUpload upload = new ServletFileUpload(factory);
         
        // ��������ļ��ϴ�ֵ
        upload.setFileSizeMax(MAX_FILE_SIZE);
         
        // �����������ֵ (�����ļ��ͱ�����)
        upload.setSizeMax(MAX_REQUEST_SIZE);
        
        // ���Ĵ���
        upload.setHeaderEncoding("UTF-8"); 

        // ������ʱ·�����洢�ϴ����ļ�
        // ���·����Ե�ǰӦ�õ�Ŀ¼
//        System.out.println("��ǰĿ¼" + this.getServletContext().getRealPath(""));
//        System.out.println(request.getRealPath(""));
//        String uploadPath = this.getServletContext().getRealPath("/WEB-INF/upload") ;
//        String uploadPath = this.getServletContext().getResource("/upload").toString();
       
//         List items;
//         items = upload.parseRequest(request);
        // ���Ŀ¼�������򴴽�
//        File uploadDir = new File(uploadPath);
//        if (!uploadDir.exists()) {
//            uploadDir.mkdir();
//        }
 
        try {
            // ���������������ȡ�ļ�����
            @SuppressWarnings("unchecked")
//            List<FileItem> formItems = upload.parseRequest((RequestContext) request);
            List<FileItem> formItems = upload.parseRequest(new ServletRequestContext(request));
            if (formItems != null && formItems.size() > 0) {
                // ����������
                for (FileItem item : formItems) {
                    // �����ڱ��е��ֶ�
//                    if ("algorithm".equals(item.getFieldName())) {
//                        System.out.println(new String(item.getString("UTF-8")));
//                    } 
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
//                        String filePath = uploadPath + File.separator + fileName;
                        filePath = "/Users/dzzxjl/Documents/data/txt/" + fileName ;
                        File storeFile = new File(filePath);
                        // �ڿ���̨����ļ����ϴ�·��
                        System.out.println(filePath);
                        // �����ļ���Ӳ��
                        item.write(storeFile);
                        request.setAttribute("filePath", filePath);
                        request.setAttribute("message",
                            "�ļ��ϴ��ɹ�!");
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
                    "������Ϣ: " + ex.getMessage());
        }
        
        
        /*
         * ͨ���㷨���д���
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
        
        // ��ת�� message.jsp
        getServletContext().getRequestDispatcher("/manage.jsp").forward(
                request, response);
    }
}