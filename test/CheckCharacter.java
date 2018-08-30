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
		//String txt_path = "C:/Users/chenzewei/Desktop/�����ļ����/";
		String txt_path = "C:/Users/chenzewei/Desktop/abc/";
		//String conf_path = "G:/study/��һ/�ı����Ա���/Data/text/";
		String conf_path = "G:/study/��һ/�ı����Ա���/Data/�½��ļ���/";
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
				
				// ����ҵ��Ĳ���txt����������
				if (!FType.equals("txt")) {
					//System.out.println("û���ҵ��ļ�"+Fname+".txt");
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
					System.out.println("����Ϊ�գ�");
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

					// ����
					int wrong = resluts.size() - suggestCorrect;

				}
				System.out.println("���д���" + errNumber + " �������"
						+ resluts.size() + " ��Դ���" + suggestCorrect
						+ " ���λ��,��û����У�ԣ�" + correctPosition);
				System.out.println("��ȷ��(���/���н���)��" + suggestCorrect * 1.0
						/ resluts.size() + " �ٻ���(���/���д���)��" + suggestCorrect
						* 1.0 / errNumber);
				fw.write("##"+Fname+"##\n");
				fw.write("���д���" + errNumber + " �������" + resluts.size()
						+ " ��Դ���" + suggestCorrect + " ���λ��,��û����У�ԣ�"
						+ correctPosition);
				fw.write("\n");
				fw.write("��ȷ��(���/���н���)��" + suggestCorrect * 1.0
						/ resluts.size() + " �ٻ���(���/���д���)��" + suggestCorrect
						* 1.0 / errNumber);
				fw.write("\n\r");

				long eTime = System.currentTimeMillis();

				System.out.println("run time: " + (eTime - sTime) / 1000.0);

			}

		} catch (Exception e) {
			System.out.println("�������г��ִ���");
			e.printStackTrace();
		}
	}

	// }
	/**
	 * �жϴ������Ƿ����ڱ�ǵ���
	 * 
	 * @param sign
	 * @param reslut
	 * @return 1 ��ȷʶ��λ�ò���ȷ���� 0��ȷʶ��û�и�����ȷ���� -1 û����ȷʶ��
	 */
	private static int isCharacterSame(Sign sign, SuggestResult reslut) {
		// ���Ҷ�λ��,sign�α�ʶ��0��ʼ��result��1��ʼ
		if ((sign.getParagraph() + 1) == reslut.getLineNo()
				&& (sign.getIndex() >= reslut.getBegin_index() && sign
						.getIndex() <= reslut.getEnd_index())) {

			// ���ҳ��ԵĴ�
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