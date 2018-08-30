import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import util.SuggestResult;

import com.alibaba.fastjson.JSON;

import errors.Error;
import errors.Sign;

public class CheckCharacter {
	public static void main(String[] args) {
		//String txt_path = "C:/Users/chenzewei/Desktop/测试文件结果/";
		String txt_path = "C:/Users/chenzewei/Desktop/abc/";
		//String conf_path = "G:/study/研一/文本测试报告/Data/text/";
		String conf_path = "G:/study/研一/文本测试报告/Data/新建文件夹/";
		String reslut_home = txt_path;

		try (FileWriter fw = new FileWriter(
				new File(reslut_home + "reslut.log"))) {

			ArrayList<File> files = getAllFile(reslut_home);

			for (File file : files) {
				String Fname = file.getName();
				String FType = Fname
						.substring(file.getName().lastIndexOf(".") + 1);
				String name = Fname.substring(0, file.getName()
						.lastIndexOf("."));
				
				// 如果找到的不是txt，就跳过。
				if (!FType.equals("txt")) {
					//System.out.println("没有找到文件"+Fname+".txt");
					continue;
				}
				String txt = txt_path + file.getName();
				String conf = conf_path + name + "_conf.txt";
				float rate = 0.01f;

				long sTime = System.currentTimeMillis();

				String result = readFileDelDup(txt);
				String jsonStr = readFile(conf);
				
				ArrayList<SuggestResult> resluts = getResults(result);
				ArrayList<Error> errors = (ArrayList<Error>) JSON.parseArray(
						jsonStr, Error.class);
				if (errors == null) {
					System.out.println("建议为空！");
					continue;
				}

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
							switch (isCharacterSame(sign, reslut)) {
							case 1:
								suggestCorrect++;
								break;
							case 0:
								correctPosition++;
								break;
							}
						}
					}

					// 查错的
					int wrong = resluts.size() - suggestCorrect;

				}
				System.out.println("共有错误：" + errNumber + " 查出错误："
						+ resluts.size() + " 查对错误：" + suggestCorrect
						+ " 查对位置,但没有审校对：" + correctPosition);
				System.out.println("精确率(查对/所有建议)：" + suggestCorrect * 1.0
						/ resluts.size() + " 召回率(查对/所有错误)：" + suggestCorrect
						* 1.0 / errNumber);
				fw.write("##"+Fname+"##\n");
				fw.write("共有错误：" + errNumber + " 查出错误：" + resluts.size()
						+ " 查对错误：" + suggestCorrect + " 查对位置,但没有审校对："
						+ correctPosition);
				fw.write("\n");
				fw.write("精确率(查对/所有建议)：" + suggestCorrect * 1.0
						/ resluts.size() + " 召回率(查对/所有错误)：" + suggestCorrect
						* 1.0 / errNumber);
				fw.write("\n\r");

				long eTime = System.currentTimeMillis();

				System.out.println("run time: " + (eTime - sTime) / 1000.0);

			}

		} catch (Exception e) {
			System.out.println("检测过程中出现错误！");
			e.printStackTrace();
		}
	}

	// }
	/**
	 * 判断错误字是否是在标记当中
	 * 
	 * @param sign
	 * @param reslut
	 * @return 1 正确识别位置并正确建议 0正确识别但没有给出正确讲义 -1 没有正确识别
	 */
	private static int isCharacterSame(Sign sign, SuggestResult reslut) {
		// 能找对位置,sign段标识从0开始，result从1开始
		if ((sign.getParagraph() + 1) == reslut.getLineNo()
				&& (sign.getIndex() >= reslut.getBegin_index() && sign
						.getIndex() <= reslut.getEnd_index())) {

			// 能找出对的词
			if (reslut.getWrongWord().contains(sign.getWrong_word())
					&& isContainsChar(reslut, sign)) {
				System.out.println(reslut.getWrongWord() + "->"
						+ reslut.toString());
				System.out.println(sign.getCorrect_word() + " "
						+ sign.getIndex());
				return 1;
			} else {
				System.out.println("****************************************");
				System.out.println(reslut.getWrongWord() + "->"
						+ reslut.toString());
				System.out.println(sign.getCorrect_word() + " "
						+ sign.getIndex());
				System.out.println("****************************************");
				return 0;
			}
		}
		return -1;
	}

	public static boolean isContainsChar(SuggestResult reslut, Sign sign) {

		for (String key : reslut.getSuggest()) {
			if (key.contains(sign.getCorrect_word())) {
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
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(txt_path), "utf-8"));) {
			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	private static String readFileDelDup(String txt_path) {
		StringBuffer sb = new StringBuffer();
		HashMap<String, String> dict = new HashMap<String, String>();
		String[] tmp={};
		try (BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(txt_path), "utf-8"));) {
			String line = "";

			while ((line = br.readLine()) != null) {
				tmp = line.split(" ");
				if(dict.size() !=0  && dict.containsKey(tmp[1])){
					if( dict.get(tmp[1]).equals(tmp[0])){
						continue;
					}else{
						dict.put(tmp[1], tmp[0]);
					}
				}else{
					dict.put(tmp[1], tmp[0]);
				}
				sb.append(line);
				sb.append("\n");
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static ArrayList<File> getAllFile(String path) {
		ArrayList<File> flist = new ArrayList<File>();

		File file = new File(path);

		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				flist.add(f);
			}
		}
		return flist;
	}
}