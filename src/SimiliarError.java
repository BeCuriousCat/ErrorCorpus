import java.util.ArrayList;
import java.util.HashMap;


public class SimiliarError extends Error{
	//形近词表
	private static String sim_corpus_path = System.getProperty("user.dir")+"corpusLib\\形近字表.txt";
	//因为需要多次迭代形近词表，所以将其长期放入内存中
	//构建一个hashmap作为查找表，K：字，V：存储的数组的索引
	private static HashMap<String, String> sim_found_table = null;
	//存储表，用来保存每一个形似词
	private static ArrayList<ArrayList<String>> sim_store_table = null;
	
	public SimiliarError(String name, double rate) {
		super(name, rate);
		// TODO Auto-generated constructor stub
		
		String path = "D:\\QQfile\\1454787565\\FileRecv\\201602.txt";
		int textSize = 20000;
		double err_rate = 0.001;
		int err_size = (int) Math.ceil(textSize*err_rate);
		//System.out.println(err_size);
		String text = GetText(path,textSize);
		HashMap<String, Integer> errs_size = new HashMap<String, Integer>();
		errs_size.put("Similar", 2);
	}

	@Override
	public boolean process() {
		// TODO Auto-generated method stub
		
		
		
		return false;
	}
	
	

}
