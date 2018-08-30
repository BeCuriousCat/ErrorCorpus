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
		//��ȡrequest
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		//��ȡ������������
		String[] errType = request.getParameterValues("inputErrorType");
		//��ȡ�����������÷�ʽ
		String[] numType = getRadioNum(request,errType);
		
		//��ȡ���Ͽ��ĵ�·��
		String path = (String) session.getAttribute("fileName");
		//ʵ�������Ͽ⣬���Ͽ�����Ϊ�ı���С
		Corpus corpus = new Corpus(path, -1);
		
		//��ȡ�������,��Ҫ�����õĴ���
		ArrayList<Error> errors;
		try {
			errors = getErrors(errType,numType, corpus.getCorpus_size(),request);
		} catch (Exception e) {
			System.out.println("���ô���ʱ����");
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
			System.out.println("д�ļ�����");
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
				//���������������Ҫת��double��ʽ
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
