package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneratePY2HanZiLib {
	String path = System.getProperty("user.dir") + "/corpusLib/pinyin.txt";

	public HashMap<String, HashSet<Character>> readText() throws IOException {
		File file = new File(path);
		if (!file.exists()) {
			System.err.println("Can't Find " + file);
		}

		BufferedReader bfr = null;
		int count = 0;
		HashMap<String, HashSet<Character>> map = new HashMap<String, HashSet<Character>>();
		// ƥ��ƴ��������
		String pattern = "([a-z]+)";
		// ����Pattern
		Pattern r = Pattern.compile(pattern);
		// ����matcher����
		Matcher m = null;
		try {
			System.out.println("����Ϊ��λ��ȡpinyin.txt���ݣ�һ�ζ�һ���У�");
			bfr = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "UTF-8"));
			String line = "";
			while ((line = bfr.readLine()) != null) {
				// ���line�ĳ���С��3�����ʾ�䲻�߱����ġ�=��ƴ������������
				if (line.length() < 2)
					continue;
				// ����ƴ�����ж�����
				count += 1;
				// str: china = pinyin
				String[] str = line.split("=");
				if (str.length != 2)
					continue;
				m = r.matcher(line);
				int i = 0;
				while (m.find()) {
					// ������ֵ�����С��ƴ������������
					if (i >= str[0].length()) {
						break;
					}

					if (map.containsKey(m.group(1))) {
						map.get(m.group(1)).add(str[0].charAt(i));
					} else {
						HashSet<Character> set = new HashSet<Character>();
						set.add(str[0].charAt(i));
						map.put(m.group(1), set);
						//System.out.println(m.group(1) + " " + str[0].charAt(i));
					}
					i += 1;
				}
			}
			System.out.println("pinyin.txt������" + count + "�У�");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("��ȡ����pinyin.txt�ļ�����");
			e.printStackTrace();
		} finally {
			bfr.close();
		}

		return map;

	}

	public void writeLib(String fileName, HashMap<String, HashSet<Character>> map)
			throws IOException {
		File file = new File(fileName);
		if (file.exists()) {
			System.out.println("�ļ��Ѵ��ڣ�������������");
			//System.exit(0);
		}
		FileWriter write = null;
		String key = "";
		Set<Character> set = null;
		String space = " ";
		StringBuffer bf = null;
		System.out.println("���������"+map.size());
		try {
			write = new FileWriter(file);
			// ����ÿһ��ƴ��
			Iterator<Entry<String, HashSet<Character>>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				bf = new StringBuffer();
				Entry entry = (Entry) iter.next();
				key = (String) entry.getKey();
				System.out.println(key);
				bf.append(key);
				bf.append(space);
				set = (HashSet) entry.getValue();
				// ����ÿһ��ƴ���µĺ���
				Iterator<Character> it = set.iterator();
				while (it.hasNext()) {
					Character ch = it.next();
					System.out.println(ch);
					bf.append(ch);
					bf.append(space);
				}
				write.write(bf.append("\n").toString());
			}

			
			write.flush();

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("дPY�ļ�����");
			e.printStackTrace();
		} finally {
			write.close();
		}

	}

	public static void main(String[] args) throws IOException {
		GeneratePY2HanZiLib genLib = new GeneratePY2HanZiLib();
		HashMap<String, HashSet<Character>> map = genLib.readText();
		String fileName = System.getProperty("user.dir")
				+ "/corpusLib/pinyin2hanzi.txt";
		genLib.writeLib(fileName, map);
	}
}