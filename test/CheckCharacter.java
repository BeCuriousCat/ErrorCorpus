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
		float rate = 0.0005f;
		for (; rate <= 0.01; rate = rate + 0.0005f) {
			
			long sTime = System.currentTimeMillis();
			
			String result = readFile(txt_path);
			String jsonStr = readFile(conf_path);
			
			ArrayList<SuggestResult> resluts = getResults(result);
			String j = "{'error_rate':2.0000000949949026E-4,'error_size':4,'name':'pinyin','signs':[{'correct_word':'ҫ','index':41,'paragraph':10,'wrong_word':'Ҫ'},{'correct_word':'�o','index':118,'paragraph':109,'wrong_word':'��'},{'correct_word':'�J','index':4,'paragraph':39,'wrong_word':'��'},{'correct_word':'�y','index':125,'paragraph':104,'wrong_word':'��'}]}";
			Object x = JSON.parseObject(j,Error.class);
			ArrayList<Error> errors = (ArrayList<Error>) JSON.parseArray(jsonStr, Error.class);
			
			System.out.println(x.toString());
			for (Error error : errors) {
				System.out.println(error.toString());
			}
			int errNumber = 0;
			int correctPosition = 0;
			int suggestCorrect = 0;
			int suggestWrong = 0;
			for (Error error : errors) {
				errNumber += error.getError_size();
				for (Sign sign : error.getSign()) {
					for (SuggestResult reslut : resluts) {
						//���Ҷ�λ��
						if(sign.getParagraph() == reslut.getLineNo() &&  sign.getIndex() == reslut.getBegin_index() ){
							correctPosition++;
							//���ҳ��ԵĴ�
							if( sign.getWrong_word().equals(reslut.getWrongWord()) && reslut.getSuggest().contains(sign.getCorrect_word())){
								suggestCorrect+=1;
							}else{
								suggestWrong+=1;
							}
						}
					}
				}
				
				//����
				int wrong = resluts.size() - suggestCorrect;
				
				
				System.out.println("���д���"+errNumber+" �������"+resluts.size()+" ���λ�ã�"+correctPosition+" ��Դ���"+suggestCorrect+" û����ԣ�"+suggestWrong);
			}
			
			
			long eTime = System.currentTimeMillis();

			System.out.println("run time: " + (eTime - sTime) / 1000.0);
			// ���ɵ��ļ�������
			DecimalFormat df = new DecimalFormat("######0.0000");   
			String filename = String.valueOf(df.format(rate));


		}
	}

	private static ArrayList<SuggestResult> getResults(String result) {
		String[] split = result.split("\r\n");
		ArrayList<SuggestResult> results = new ArrayList<SuggestResult>();
		for (String string : split) {
			results.add(new SuggestResult(string));
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
