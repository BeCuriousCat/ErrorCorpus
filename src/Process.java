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
	//�ν��ʱ�
	private static String sim_corpus_path = "D:\\QQfile\\1454787565\\FileRecv\\�ν��ֱ�.txt";
	//��Ϊ��Ҫ��ε����ν��ʱ����Խ��䳤�ڷ����ڴ���
	//����һ��hashmap��Ϊ���ұ�K���֣�V���洢�����������
	private static HashMap<String, String> sim_found_table = null;
	//�洢����������ÿһ�����ƴ�
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
		
		//�����ִ���ı����Ƿ���ڴ���ٷֱ�ֵ
		int count = 0;
		for (int i: errs_size.values()) {
			count += i;
		}
		if(count > err_size)
		{
			System.out.println("Warning: errors is more than errors rate!");
		}
		//System.out.println(text);
		//�������Ͽ�
		Corpus similar_corpus = null;
		//�滻����
		StringBuffer corpus_bf = new StringBuffer(text);
		similar_corpus = addSimilarErr(corpus_bf,errs_size.get("Similar"),err_rate);
		System.out.println("���ƴ��������ɣ�");
		
		
		//д�ļ�
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
		//�������ڣ����ʼ���ν��ʿ�
		initSimiliarlCorpus();
		//����ν��ʴ�����
		Error sim_err = new Error("similiar", err_size);
		HashMap<Integer,String> check = new HashMap<Integer,String>();
		//��θ���corpus,�����ȱ���ΪStringBuff
		for (int i = 0; i < err_size; i++) {
			char charAtText;
			String StringAtText;
			int index;
			//������ڴ�ʿ��У������һ����
			index = (int) (Math.random() * (corpus_bf.length() - 1));
			while(true){
				charAtText = corpus_bf.charAt(index);
				//System.out.println(charAtText);
				//System.out.println(sim_found_table.containsKey(""+charAtText));
				//����ת��Ϊstring�����ҵ�
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
			//��������滻 st_index:����������
			//��Ϊ����ϵĿ��ܣ�������Ҫ�Ƚ������value
			//System.out.println(sim_found_table.get(StringAtText));
			String[] indexs = sim_found_table.get(StringAtText).split("\\|");
			//�ȿ����ԵĻ������values
			int i_random = (int) (Math.random()*(indexs.length-1)); 
			int st_index = Integer.parseInt(indexs[i_random]);
			//���ѡ��һ������ͬ���ν��ֽ����滻
			while (true){
				//���Ʊ���λ��
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
		System.out.println("�滻��ɣ�");
		Corpus sim_corpus = new Corpus(corpus_bf.toString(), corpus_bf.length());
		sim_corpus.addErrors(sim_err);
		return sim_corpus;
	}

	private static void initSimiliarlCorpus() throws IOException {
		
		System.out.println("init Similiar Corpus!");
		if(sim_found_table == null){
			//����һ��hashmap��Ϊ���ұ�K���֣�V���洢�����������
			sim_found_table = new HashMap<String, String>();
			//�洢����������ÿһ�����ƴ�
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
						//�鿴�Ƿ�������֣����û����ӣ�����оͽ�index��ϣ������ʽ index1|index2
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
			System.out.println("���ֽ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ��̶�����ֽڣ�");
            char[] bs = new char[textSize];
 			// һ�ζ�����ֽ�
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
