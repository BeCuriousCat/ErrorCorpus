package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Request;

import org.apache.struts2.ServletActionContext;

public class GenerateCorpusAction {
	public String generate(){

		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		
		String errType = request.getParameter("inputErrorType");
		String errNum = request.getParameter("errorNumber");
		Integer num = getErrorNumber(errNum,request); 
		
		
		
		return "success";
	}

	private Integer getErrorNumber(String errNum, HttpServletRequest request) {
		if( errNum.equals("number")){
			return Integer.parseInt(request.getParameter("errTxt_number"));
		}else if(errNum.equals("precent")){
			return Integer.parseInt(request.getParameter("errTxt_precent"));
		}
		
		return -1;
	}
}
