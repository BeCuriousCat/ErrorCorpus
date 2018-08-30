package util;

import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SuggestResult {
	private String content;
	private int lineNo;
	private String wrongWord;
	private int begin_index;
	private int end_index;
	private String env;
	
	private HashSet<String> suggest;
	
	public SuggestResult(String txt) throws Exception {
		setContent(txt);
	}
	public String getContent() {
		return content;
	}
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public void setContent(String content) throws Exception {
		this.content = content;
		
		praseContent();
	}
	

	public String getWrongWord() {
		return wrongWord;
	}

	public int getLineNo() {
		return lineNo;
	}

	public int getBegin_index() {
		return begin_index;
	}

	public int getEnd_index() {
		return end_index;
	}

	public HashSet<String> getSuggest() {
		return suggest;
	}

	
	@Override
	public String toString() {
		return "SuggestResult [content=" + content + ", lineNo=" + lineNo
				+ ", wrongWord=" + wrongWord + ", begin_index=" + begin_index
				+ ", end_index=" + end_index + ", env=" + env + ", suggest="
				+ suggest + "]";
	}
	private void praseContent() throws Exception {
		// TODO Auto-generated method stub
		String env[] = this.content.split("#&#&#&#");
		setEnv(env[1]);
		// ���� P_62_0_0_0_10_12 ���� ���� ���� #&#&#&#���Ǳ����Ժ� 
		String[] res = env[0].split(" "); 
		//��������Ĵ�
		this.wrongWord = res[0];
		//System.out.println(content);
		//�׳�ʱ����
		Pattern pattern = Pattern.compile("[0-9]+[������]");
		Matcher matcher = pattern.matcher(wrongWord);
		if(matcher.find()){
			throw new Exception("�׳�ʱ���飡");
		}
		//�׳����ʼ��
		Pattern patternNum = Pattern.compile("[0-9һ�����������߰˾�ʮ]+[��]");
		Matcher matcherNum = patternNum.matcher(wrongWord);
		if(matcherNum.find()){
			throw new Exception("�׳����ʼ�飡");
		}
				
		this.suggest = new HashSet<String>();
		//���������Ľ���
		for (int i = 2; i < res.length; i++) {
			if( res[i].equals("���д�")){
				throw new Exception("�׳����дʣ�");
			}
			
			this.suggest.add(res[i]);
		}
		
		// ����λ��
		String[] position = res[1].split("[_ ,]");
		this.lineNo = Integer.valueOf(position[1]);
		this.begin_index = Integer.valueOf(position[5]);
		this.end_index = Integer.valueOf(position[6]);
		
		
		
	}
}
