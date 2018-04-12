package util;

import java.util.HashSet;

public class SuggestResult {
	private String content;
	private int lineNo;
	private String wrongWord;
	private int begin_index;
	private int end_index;
	
	private HashSet<String> suggest;
	
	public SuggestResult(String txt) throws Exception {
		setContent(txt);
	}
	public String getContent() {
		return content;
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
				+ ", end_index=" + end_index + ", suggest=" + suggest + "]";
	}
	private void praseContent() throws Exception {
		// TODO Auto-generated method stub
		// 哲人 P_160_0_0_0_0_2 责任 哲人 责人 
		String[] res = this.content.split(" "); 
		//解析错误的词
		this.wrongWord = res[0];
		System.out.println(wrongWord);
		
		this.suggest = new HashSet<String>();
		//解析给出的建议
		for (int i = 2; i < res.length; i++) {
			if( res[i].equals("敏感词")){
				throw new Exception("抛出敏感词！");
			}
			
			this.suggest.add(res[i]);
		}
		
		// 解析位置
		String[] position = res[1].split("[_ ,]");
		this.lineNo = Integer.valueOf(position[1]);
		this.begin_index = Integer.valueOf(position[5]);
		this.end_index = Integer.valueOf(position[6]);
		
		
		
	}
}
