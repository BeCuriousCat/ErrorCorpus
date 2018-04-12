import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.asm.Type;

import util.SuggestResult;
import corpus.Corpus;
import errors.Error;
import errors.PinYinError;
import errors.Sign;
import errors.SimiliarError;


public class CheckCharacter {
	public static void main(String[] args) {
		String txt_path = "C:/Users/chenzewei/Desktop/output/";
		String conf_path = System.getProperty("user.dir") + "/data/";

		txt_path += "0.0005_result.txt";
		conf_path += "0.0005_conf.txt";
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
				errNumber += error.getErrorSize();
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
				//没建议对
				suggestWrong = correctPosition - suggestCorrect;
				
			}
			System.out.println("共有错误："+errNumber+" 查出错误："+resluts.size()+" 查出位置："+correctPosition+" 查对错误："+suggestCorrect+" 没建议对："+suggestWrong);
			
			
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
			System.out.println(reslut.getWrongWord()+"->"+reslut.toString());
			System.out.println(sign.toString());
			//能找出对的词
			if( reslut.getWrongWord().contains(sign.getWrong_word()) && isContainsChar(reslut, sign)){
				return 1;
			}else{
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
				System.out.println("跳过一个敏感词");
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
