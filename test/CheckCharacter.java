import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;

import util.SuggestResult;

import com.alibaba.fastjson.JSON;

import errors.Error;
import errors.Sign;


public class CheckCharacter {
	public static void main(String[] args) {
		String txt_path = "C:/Users/chenzewei/Desktop/all/";
		String conf_path = System.getProperty("user.dir") + "/data/";

		txt_path += "T2_4_2Word0.0010.txt";
		conf_path += "T2_4_Word0.0010_conf.txt";
		float rate = 0.01f;
		//for (; rate <= 0.01; rate = rate + 0.0005f) {
			
			long sTime = System.currentTimeMillis();
			
			String result = readFile(txt_path);
			String jsonStr = readFile(conf_path);
			
			ArrayList<SuggestResult> resluts = getResults(result);
			ArrayList<Error> errors = (ArrayList<Error>) JSON.parseArray(jsonStr, Error.class);

			for (Error error : errors) {
				System.out.println(error.toString());
			}
			int errNumber = 0;
			int correctPosition = 0;
			int suggestCorrect = 0;
			int suggestWrong = 0;
			for (Error error : errors) {
				errNumber += error.getSigns().size();
				for (Sign sign : error.getSigns()) {
					for (SuggestResult reslut : resluts) {
						switch(isCharacterSame(sign, reslut)){
						case 1: 
							suggestCorrect++;
							break;
						case 0:
							correctPosition++;
							break;
						}
					}
				}
				
				//查错的
				int wrong = resluts.size() - suggestCorrect;
				
				
			}
			System.out.println("共有错误："+errNumber+" 查出错误："+resluts.size()+" 查对错误："+suggestCorrect+" 查对位置,但没有审校对："+correctPosition);
			System.out.println("精确率(查对/所有建议)："+suggestCorrect*1.0/resluts.size()+" 召回率(查对/所有错误)："+suggestCorrect*1.0/errNumber);
			
			
			long eTime = System.currentTimeMillis();

			System.out.println("run time: " + (eTime - sTime) / 1000.0);
			// 生成的文件的名称
			DecimalFormat df = new DecimalFormat("######0.0000");   
			String filename = String.valueOf(df.format(rate));


		}
	//}
	/**
	 * 判断错误字是否是在标记当中
	 * @param sign
	 * @param reslut
	 * @return 1 正确识别位置并正确建议     0正确识别但没有给出正确讲义   -1 没有正确识别
	 */
	private static int isCharacterSame(Sign sign, SuggestResult reslut) {
		//能找对位置,sign段标识从0开始，result从1开始
		if( (sign.getParagraph()+1) == reslut.getLineNo() &&  (sign.getIndex() >= reslut.getBegin_index() && sign.getIndex() <= reslut.getEnd_index()) ){
			
			//能找出对的词
			if( reslut.getWrongWord().contains(sign.getWrong_word()) && isContainsChar(reslut, sign)){
				System.out.println(reslut.getWrongWord()+"->"+reslut.toString());
				System.out.println(sign.getCorrect_word()+" "+sign.getIndex());
				return 1;
			}else{
				System.out.println("****************************************");
				System.out.println(reslut.getWrongWord()+"->"+reslut.toString());
				System.out.println(sign.getCorrect_word()+" "+sign.getIndex());
				System.out.println("****************************************");
				return 0;
			}
		}
		return -1;
	}
	
	public static boolean isContainsChar(SuggestResult reslut,Sign sign){
		
		for (String key : reslut.getSuggest()) {
			if(key.contains(sign.getCorrect_word())){
				return true;
			}
		}
		
		return false;
	}

	private static ArrayList<SuggestResult> getResults(String result) {
		String[] split = result.split("\n");
		ArrayList<SuggestResult> results = new ArrayList<SuggestResult>();
		for (String string : split) {
			try {
				results.add(new SuggestResult(string));
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				continue;
			}
		}
		return results;
	}

	private static String readFile(String txt_path) {
		StringBuffer sb = new StringBuffer();
		try( 
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(txt_path),"utf-8"));
			){
			String line = "";
			while( (line = br.readLine()) != null){
				sb.append(line);
				sb.append("\n");
			}
			
		}catch(IOException e){
			e.printStackTrace();
		}
		return sb.toString();
	}
}