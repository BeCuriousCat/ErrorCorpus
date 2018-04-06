package com.errorcorpus.action;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FileUploadAction extends ActionSupport{

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		private File upload; //上传的文件
	    private String uploadFileName; //文件名称
	    private String uploadContentType; 
	    

		public String upload()  {
	        String realpath = ServletActionContext.getServletContext().getRealPath("/txtCorpus");
	        //D:\apache-tomcat-6.0.18\webapps\struts2_upload\images
	        System.out.println("realpath: "+realpath);
	        if (upload != null) {
	            File savefile = new File(new File(realpath),uploadFileName);
	            System.out.println(savefile);
	            if (!savefile.getParentFile().exists())
	                savefile.getParentFile().mkdirs();
	            try {
					FileUtils.copyFile(upload, savefile);
				} catch (IOException e) {
					System.out.println("上传文件出错！");
					e.printStackTrace();
				}

	            HttpServletRequest request = ServletActionContext.getRequest();
	    		request.setAttribute("message", "success");
	            
	    		HttpSession session = request.getSession();
	    		
	    		session.setAttribute("fileName", savefile.getPath());
	    		
	            return "success" ;
	        }
	        
	        return INPUT;
	       
	    }


	    public File getUpload() {
			return upload;
		}


		public void setUpload(File upload) {
			this.upload = upload;
		}


		public String getUploadFileName() {
			return uploadFileName;
		}


		public void setUploadFileName(String uploadFileName) {
			this.uploadFileName = uploadFileName;
		}


		public String getUploadContentType() {
			return uploadContentType;
		}


		public void setUploadContentType(String uploadContentType) {
			this.uploadContentType = uploadContentType;
		}
}
