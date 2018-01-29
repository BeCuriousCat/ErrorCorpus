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
		// 匹配拼音的正则
		String pattern = "([a-z]+)";
		// 创建Pattern
		Pattern r = Pattern.compile(pattern);
		// 创建matcher对象
		Matcher m = null;
		try {
			System.out.println("以行为单位读取pinyin.txt内容，一次读一整行：");
			bfr = new BufferedReader(new InputStreamReader(new FileInputStream(
					file), "UTF-8"));
			String line = "";
			while ((line = bfr.readLine()) != null) {
				// 如果line的长度小于3，则表示其不具备中文、=和拼音这三个属性
				if (line.length() < 2)
					continue;
				// 计算拼音中有多少行
				count += 1;
				// str: china = pinyin
				String[] str = line.split("=");
				if (str.length != 2)
					continue;
				m = r.matcher(line);
				int i = 0;
				while (m.find()) {
					// 如果汉字的数量小于拼音的数量则丢弃
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
			System.out.println("pinyin.txt共处理" + count + "行！");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("读取或处理pinyin.txt文件出错！");
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
			System.out.println("文件已存在！请重新命名！");
			//System.exit(0);
		}
		FileWriter write = null;
		String key = "";
		Set<Character> set = null;
		String space = " ";
		StringBuffer bf = null;
		System.out.println("输出数量："+map.size());
		try {
			write = new FileWriter(file);
			// 遍历每一个拼音
			Iterator<Entry<String, HashSet<Character>>> iter = map.entrySet().iterator();
			while (iter.hasNext()) {
				bf = new StringBuffer();
				Entry entry = (Entry) iter.next();
				key = (String) entry.getKey();
				System.out.println(key);
				bf.append(key);
				bf.append(space);
				set = (HashSet) entry.getValue();
				// 遍历每一个拼音下的汉字
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
			System.out.println("写PY文件出错！");
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