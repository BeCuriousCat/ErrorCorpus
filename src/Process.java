import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Process {
	//形近词表
	private static String sim_corpus_path = "D:\\QQfile\\1454787565\\FileRecv\\形近字表.txt";
	//因为需要多次迭代形近词表，所以将其长期放入内存中
	//构建一个hashmap作为查找表，K：字，V：存储的数组的索引
	private static HashMap<String, String> sim_found_table = null;
	//存储表，用来保存每一个形似词
	private static ArrayList<ArrayList<String>> sim_store_table = null;
	
	public static void main(String[] args) throws IOException {
		String path = "D:\\QQfile\\1454787565\\FileRecv\\201602.txt";
		int textSize = 20000;
		double err_rate = 0.001;
		int err_size = (int) Math.ceil(textSize*err_rate);
		//System.out.println(err_size);
		String text = GetText(path,textSize);
		HashMap<String, Integer> errs_size = new HashMap<String, Integer>();
		errs_size.put("Similar", 2);
		
		//检查各种错误的变量是否大于错误百分比值
		int count = 0;
		for (int i: errs_size.values()) {
			count += i;
		}
		if(count > err_size)
		{
			System.out.println("Warning: errors is more than errors rate!");
		}
		//System.out.println(text);
		//构建语料库
		Corpus similar_corpus = null;
		//替换错误
		StringBuffer corpus_bf = new StringBuffer(text);
		similar_corpus = addSimilarErr(corpus_bf,errs_size.get("Similar"),err_rate);
		System.out.println("形似错误添加完成！");
		
		
		//写文件
		String filename = "similar_corpus";
		similar_corpus.write(filename);
	}

	/**
	 * @param corpus_bf
	 * @param err_size
	 * @param err_rate 
	 * @return
	 * @throws IOException
	 */
	private static Corpus addSimilarErr(StringBuffer corpus_bf, Integer err_size, double err_rate) throws IOException {
		//若不存在，则初始化形近词库
		initSimiliarlCorpus();
		//添加形近词错误函数
		Error sim_err = new Error("similiar", err_size);
		HashMap<Integer,String> check = new HashMap<Integer,String>();
		//多次更改corpus,所以先保存为StringBuff
		for (int i = 0; i < err_size; i++) {
			char charAtText;
			String StringAtText;
			int index;
			//如果不在错词库中，则更换一个词
			index = (int) (Math.random() * (corpus_bf.length() - 1));
			while(true){
				charAtText = corpus_bf.charAt(index);
				//System.out.println(charAtText);
				//System.out.println(sim_found_table.containsKey(""+charAtText));
				//必须转化为string才能找到
				StringAtText = ""+charAtText;
				if(sim_found_table.containsKey(StringAtText)){ 
					for (int idx : check.keySet()) {
						if(idx == index) {
							index = (int) (Math.random() * (corpus_bf.length() - 1));
							break;
						} 
					}
					System.out.println("find a err position! :"+index);
					break;
				}
				index += 1;
				if(index >= corpus_bf.length()) 
					index = (int) (Math.random() * (corpus_bf.length() - 1));
			}
			//否则进行替换 st_index:储存表的索引
			//因为有组合的可能，所以需要先解析这个value
			//System.out.println(sim_found_table.get(StringAtText));
			String[] indexs = sim_found_table.get(StringAtText).split("\\|");
			//等可能性的划分这个values
			int i_random = (int) (Math.random()*(indexs.length-1)); 
			int st_index = Integer.parseInt(indexs[i_random]);
			//随机选择一个不相同的形近字进行替换
			while (true){
				//相似表中位置
				int correct_index = (int) Math.random()*sim_store_table.get(st_index).size();
				char correct_word = sim_store_table.get(st_index).get(correct_index).charAt(0);
				if(charAtText != correct_word){
					corpus_bf.replace(index	,index+1, ""+correct_word);
					check.put(index,""+StringAtText);
					break;
				}
			}
			sim_err.setCheck_map(check);
		}
		System.out.println("替换完成！");
		Corpus sim_corpus = new Corpus(corpus_bf.toString(), corpus_bf.length());
		sim_corpus.addErrors(sim_err);
		return sim_corpus;
	}

	private static void initSimiliarlCorpus() throws IOException {
		
		System.out.println("init Similiar Corpus!");
		if(sim_found_table == null){
			//构建一个hashmap作为查找表，K：字，V：存储的数组的索引
			sim_found_table = new HashMap<String, String>();
			//存储表，用来保存每一个形似词
			sim_store_table = new ArrayList<ArrayList<String>>();
			
			BufferedReader fin = null;
			try {
				File file = new File(sim_corpus_path);
				fin = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8") );
				String line = null;
				int index = 0; 
				while((line = fin.readLine()) != null){
					String str[] = line.split(" ");
					ArrayList<String> similar = new ArrayList<String>();
					for (String s : str) {
						//查看是否含有这个字，如果没有添加，如果有就将index组合：组合形式 index1|index2
						if(!sim_found_table.containsKey(s)){
							similar.add(s);
							sim_found_table.put(s, String.valueOf(index));
						}else{
							String value = sim_found_table.get(s);
							value = value+"|"+index;
							similar.add(s);
							sim_found_table.put(s, String.valueOf(value));
						}
					}
					if(similar.size() != 0) sim_store_table.add(similar);
					index +=1;
				}
				//System.out.println(sim_store_table.size());
				System.out.println("similar corpus completed!");
			} catch (Exception e) {
				System.out.println("Error: init similiar words corpus error!");
				e.printStackTrace();
			}finally{
				fin.close();
			}
		}
	}

	private static String GetText(String path, int textSize) throws IOException {
		 File file = new File(path);
		 String bf = null;
		 if(!file.exists())
	        { 
	            System.err.println("Can't Find " + file);
	        }
		BufferedReader  in = null;
		try {
			System.out.println("以字节为单位读取文件内容，一次读固定多个字节：");
            char[] bs = new char[textSize];
 			// 一次读多个字节
			in = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8")); 
            String str;
            in.read(bs);
            System.out.println(bs.length);
            bf = String.valueOf(bs);
            in.close();
            
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}finally{
			in.close();
		}
		return bf;
	}
}
