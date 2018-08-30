package com.errorcorpus.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;
import javax.xml.crypto.Data;

import org.apache.struts2.ServletActionContext;

import com.errorcorpus.service.ErrorFactory;
import com.opensymphony.xwork2.ActionSupport;

import corpus.Corpus;
import errors.Error;

public class GenerateCorpusAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String generate() {
		//获取request
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		//获取错误类型数组
		String[] errType = request.getParameterValues("inputErrorType");
		//获取错误数量设置方式
		String[] numType = getRadioNum(request,errType);
		
		//获取语料库文档路径
		String path = (String) session.getAttribute("fileName");
		//实例化语料库，语料库设置为文本大小
		Corpus corpus = new Corpus(path, -1);
		
		//获取错误对象,需要将设置的错误
		ArrayList<Error> errors;
		try {
			errors = getErrors(errType,numType, corpus.getCorpus_size(),request);
		} catch (Exception e) {
			System.out.println("设置错误时出错！");
			e.printStackTrace();
			return "error";
		}
		
		corpus.setErrors(errors);
		corpus.run();
		Date date = new  Date();
		String filename = "/output/"+date.getTime();
		try {
			corpus.write(filename, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("写文件出错");
			e.printStackTrace();
		};
		

		return "success";
	}

	private ArrayList<Error> getErrors(String[] errType, String[] numType, int corpus_size,
			HttpServletRequest request) {
			ArrayList<Error> errs = new ArrayList<Error>();
			ErrorFactory ef = new ErrorFactory();
			int num = 0;
			
			double rate = 0;
			String tmp;
			for (int i = 0; i < errType.length; i++) {
				//如果是数量，则需要转成double形式
				tmp = request.getParameter(numType[i]);
				System.out.println(tmp);
				System.out.println(errType[i]+numType[i]);
				
				if( tmp.split("_")[0].equals("number")){
					num = Integer.parseInt(tmp);
					rate = num*1.0/corpus_size;
				}else{
					num = Integer.parseInt(request.getParameter(numType[i]));
					rate = num*1.0/100;
				}
				errs.add(ef.genError(errType[i], rate));
			}
		
		
		return errs;
	}

	private String[] getRadioNum(HttpServletRequest request, String[] errType) {
		String[]  errNum = new String[errType.length];
		for (int i = 0; i < errType.length; i++) {
			errNum[i] = request.getParameter(errType[i]);
		}
		return errNum;
	}

	
}
