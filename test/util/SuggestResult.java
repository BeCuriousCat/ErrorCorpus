package util;

import java.util.HashSet;

public class SuggestResult {
	private String content;
	private int lineNo;
	private String wrongWord;
	private int begin_index;
	private int end_index;
	
	private HashSet<String> suggest;
	
	public SuggestResult(String txt) {
		setContent(txt);
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
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

	private void praseContent() {
		// TODO Auto-generated method stub
		
		String[] res = this.content.split(" \\| "); 
		//解析错误的词
		this.wrongWord = res[0];
		// 解析位置
		String[] position = res[1].split("_");
		this.lineNo = Integer.valueOf(position[0]);
		this.begin_index = Integer.valueOf(position[1]);
		this.end_index = Integer.valueOf(position[2]);
		
		//解析给出的建议
		String[] sugg = res[2].split("\\[,");
		this.suggest = new HashSet<String>();
		for (String string : sugg) {
			this.suggest.add(string);
		}
		
	}
}
