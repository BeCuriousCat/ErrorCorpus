import java.util.ArrayList;
import java.util.HashMap;


public class SimiliarError extends Error{
	//�ν��ʱ�
	private static String sim_corpus_path = System.getProperty("user.dir")+"corpusLib\\�ν��ֱ�.txt";
	//��Ϊ��Ҫ��ε����ν��ʱ����Խ��䳤�ڷ����ڴ���
	//����һ��hashmap��Ϊ���ұ�K���֣�V���洢�����������
	private static HashMap<String, String> sim_found_table = null;
	//�洢����������ÿһ�����ƴ�
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
