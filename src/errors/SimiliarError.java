package errors;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class SimiliarError extends Error {
	// 形近词表
	private static String sim_corpus_path = System.getProperty("user.dir")
			+ "\\corpusLib\\形近字表.txt";
	// 因为需要多次迭代形近词表，所以将其长期放入内存中
	// 构建一个hashmap作为查找表，K：字，V：存储的数组的索引
	private static HashMap<String, String> sim_found_table = null;
	// 存储表，用来保存每一个形似词
	private static ArrayList<ArrayList<String>> sim_store_table = null;

	public SimiliarError(String name, double rate) {
		super(name, rate);
		ArrayList<Sign> sign = new ArrayList<Sign>();
		setSigns(sign);
	}

	@Override
	public boolean process(ArrayList<StringBuffer> corpus_bfs,ArrayList<Error> errors) {
		// 若不存在，则初始化形近词库
		System.out.println("run the process function");
		try {
			initSimiliarlCorpus();
		} catch (IOException e) {
			System.out.println("初始化形似词库出错！");
			e.printStackTrace();
			return false;
		}
		// 添加形近词错误
		ArrayList<Sign> signs = new ArrayList<Sign>();
		for (int i = 0; i < this.getError_size(); i++) {
			char charAtText;
			String StringAtText;//文本中的将被替代的一个字
			int index;
			int paragraph_index;
			// 获得随机的段落数和索引
			paragraph_index = getRondParagraphIndex(corpus_bfs);
			index = getRondIndex(corpus_bfs, paragraph_index);
			
			StringBuffer paragraph = null;
			while (true) {
				paragraph = corpus_bfs.get(paragraph_index);
				boolean repeat = false;
				charAtText = paragraph.charAt(index);
				// 必须转化为string才能找到
				StringAtText = "" + charAtText;
				if (sim_found_table.containsKey(StringAtText)) {
					//迭代Errors看是否有重复
					repeat = isRepeated(errors, signs, index, paragraph_index,repeat);
					//若重复则跳出重来
					if(repeat == true){
						paragraph_index = getRondParagraphIndex(corpus_bfs);
						index = getRondIndex(corpus_bfs,paragraph_index);
						continue;
					}
					//System.out.println("find a err position! :" + index);
					break;
				}
				index += 1;
				if (index >= paragraph.length()){
					paragraph_index = getRondParagraphIndex(corpus_bfs);
					index = getRondIndex(corpus_bfs, paragraph_index);
				}
			}
			// 否则进行替换 st_index:储存表的索引
			// 因为有组合的可能，所以需要先解析这个value
			// System.out.println(sim_found_table.get(StringAtText));
			String[] indexs = sim_found_table.get(StringAtText).split("\\|");
			// 等可能性的划分这个values
			int i_random = (int) (Math.random() * (indexs.length - 1));
			int st_index = Integer.parseInt(indexs[i_random]);
			// 随机选择一个不相同的形近字进行替换
			while (true) {
				// 相似表中位置
				int wordsLength = sim_store_table.get(st_index).size();
				int wrong_index = (int) (Math.random() * wordsLength);
				//System.out.println(wrong_index);
				char wrong_word = sim_store_table.get(st_index)
						.get(wrong_index).charAt(0);
				if (charAtText != wrong_word) {
					String correct_word = StringAtText;
					paragraph.replace(index, index + 1, "" + wrong_word);
					Sign sign = new Sign(paragraph_index, index, Character.toString(wrong_word), correct_word);
					this.getSigns().add(sign);
					break;
				}
			}
		}
		System.out.println("形似字替换完成！");
		return true;
	}


	/**
	 * 初始化相似词库，读入内存，加快迭代时效率
	 * 
	 * @throws IOException
	 */
	private static void initSimiliarlCorpus() throws IOException {
		System.out.println("init Similiar Corpus!");
		if (sim_found_table == null) {
			// 构建一个hashmap作为查找表，K：字，V：存储的数组的索引
			sim_found_table = new HashMap<String, String>();
			// 存储表，用来保存每一个形似词，一行对应一组形似词组
			sim_store_table = new ArrayList<ArrayList<String>>();
			BufferedReader fin = null;
			try {
				File file = new File(sim_corpus_path);
				fin = new BufferedReader(new InputStreamReader(
						new FileInputStream(file), "UTF-8"));
				String line = null;
				int index = 0;
				while ((line = fin.readLine()) != null) {
					String str[] = line.split(" ");
					ArrayList<String> similar = new ArrayList<String>();
					for (String s : str) {
						// 查看是否含有这个字，如果没有添加，如果有就将index组合：组合形式 index1|index2
						if (!sim_found_table.containsKey(s)) {
							similar.add(s);
							sim_found_table.put(s, String.valueOf(index));
						} else {
							String value = sim_found_table.get(s);
							value = value + "|" + index;
							similar.add(s);
							sim_found_table.put(s, String.valueOf(value));
						}
					}
					if (similar.size() != 0)
						sim_store_table.add(similar);
					index += 1;
				}
				//System.out.println(sim_store_table.size());
				System.out.println("similar corpus completed!共"+sim_store_table.size()+"行！");
			} catch (Exception e) {
				System.out.println("Error: init similiar words corpus error!");
				e.printStackTrace();
			} finally {
				if(fin != null)
					fin.close();
			}
		}
	}
}