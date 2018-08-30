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
		// 备感 P_62_0_0_0_10_12 倍感 备感 焙干 #&#&#&#我们备感自豪 
		String[] res = env[0].split(" "); 
		//解析错误的词
		this.wrongWord = res[0];
		//System.out.println(content);
		//抛出时间检查
		Pattern pattern = Pattern.compile("[0-9]+[年月日]");
		Matcher matcher = pattern.matcher(wrongWord);
		if(matcher.find()){
			throw new Exception("抛出时间检查！");
		}
		//抛出量词检查
		Pattern patternNum = Pattern.compile("[0-9一二三四五六七八九十]+[个]");
		Matcher matcherNum = patternNum.matcher(wrongWord);
		if(matcherNum.find()){
			throw new Exception("抛出量词检查！");
		}
				
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
