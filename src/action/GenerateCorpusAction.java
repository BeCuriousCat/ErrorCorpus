package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import org.apache.struts2.ServletActionContext;

public class GenerateCorpusAction {
	public String generate() {
		//��ȡrequest
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		//��ȡ������������
		String[] errType = request.getParameterValues("inputErrorType");
		//��ȡ�����������÷�ʽ
		String[] errNum = getRadioNum(request,errType.length);
		//��ȡ��������
		int[] num = getErrorNumber(errNum, request);

		for (int i = 0; i < num.length; i++) {
			System.out.println(errType[i]);
			System.out.println(errNum[i]);
		}

		return "success";
	}

	private String[] getRadioNum(HttpServletRequest request, int len) {
		String[] errNum = new String[len];
		for (int i = 0; i < errNum.length; i++) {
			errNum[i] = request.getParameter("errorNumber"+i);
		}
		return errNum;
	}

	private int[] getErrorNumber(String[] errNum, HttpServletRequest request) {
		int[] num = new int[errNum.length];
		
		return num;
	}
}
