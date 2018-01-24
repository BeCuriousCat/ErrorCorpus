package errors;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class SimiliarError extends Error {
	// �ν��ʱ�
	private static String sim_corpus_path = System.getProperty("user.dir")
			+ "\\corpusLib\\�ν��ֱ�.txt";
	// ��Ϊ��Ҫ��ε����ν��ʱ������Խ��䳤�ڷ����ڴ���
	// ����һ��hashmap��Ϊ���ұ���K���֣�V���洢�����������
	private static HashMap<String, String> sim_found_table = null;
	// �洢������������ÿһ�����ƴ�
	private static ArrayList<ArrayList<String>> sim_store_table = null;

	public SimiliarError(String name, double rate) {
		super(name, rate);
		ArrayList<Sign> sign = new ArrayList<Sign>();
		setSigns(sign);
	}

	@Override
	public boolean process(ArrayList<StringBuffer> corpus_bfs,ArrayList<Error> errors) {
		// �������ڣ����ʼ���ν��ʿ�
		System.out.println("run the process function");
		try {
			initSimiliarlCorpus();
		} catch (IOException e) {
			System.out.println("��ʼ�����ƴʿ������");
			e.printStackTrace();
			return false;
		}
		// �����ν��ʴ���
		ArrayList<Sign> signs = new ArrayList<Sign>();
		for (int i = 0; i < this.getError_size(); i++) {
			char charAtText;
			String StringAtText;//�ı��еĽ��������һ����
			int index;
			int paragraph_index;
			// �������Ķ�����������
			paragraph_index = getRondParagraphIndex(corpus_bfs);
			index = getRondIndex(corpus_bfs, paragraph_index);
			
			StringBuffer paragraph = null;
			while (true) {
				paragraph = corpus_bfs.get(paragraph_index);
				boolean repeat = false;
				charAtText = paragraph.charAt(index);
				// ����ת��Ϊstring�����ҵ�
				StringAtText = "" + charAtText;
				if (sim_found_table.containsKey(StringAtText)) {
					//����Errors���Ƿ����ظ�
					repeat = isRepeated(errors, signs, index, paragraph_index,repeat);
					//���ظ�����������
					if(repeat == true){
						paragraph_index = getRondParagraphIndex(corpus_bfs);
						index = getRondIndex(corpus_bfs,paragraph_index);
						break;
					}
					System.out.println("find a err position! :" + index);
					break;
				}
				index += 1;
				if (index >= paragraph.length()){
					paragraph_index = getRondParagraphIndex(corpus_bfs);
					index = getRondIndex(corpus_bfs, paragraph_index);
				}
			}
			// ��������滻 st_index:�����������
			// ��Ϊ����ϵĿ��ܣ�������Ҫ�Ƚ������value
			// System.out.println(sim_found_table.get(StringAtText));
			String[] indexs = sim_found_table.get(StringAtText).split("\\|");
			// �ȿ����ԵĻ������values
			int i_random = (int) (Math.random() * (indexs.length - 1));
			int st_index = Integer.parseInt(indexs[i_random]);
			// ���ѡ��һ������ͬ���ν��ֽ����滻
			while (true) {
				// ���Ʊ���λ��
				int correct_index = (int) Math.random()
						* sim_store_table.get(st_index).size();
				char correct_word = sim_store_table.get(st_index)
						.get(correct_index).charAt(0);
				if (charAtText != correct_word) {
					String wrong_word = paragraph.substring(index, index+1);
					paragraph.replace(index, index + 1, "" + correct_word);
					Sign sign = new Sign(paragraph_index, index, Character.toString(correct_word), wrong_word);
					this.getSigns().add(sign);
					break;
				}
			}
		}
		System.out.println("�滻��ɣ�");
		return true;
	}

	
	/**
	 * ����״������������Ͽ����ɵĴ������Ƿ��ظ�
	 * @param errors
	 * @param signs
	 * @param index
	 * @param paragraph_index
	 * @param repeat
	 * @return
	 */
	private boolean isRepeated(ArrayList<Error> errors, ArrayList<Sign> signs,
			int index, int paragraph_index, boolean repeat) {
		for (Error err : errors) {
			for (Sign sign : signs) {
				if (sign.getIndex() == index && sign.getParagraph() == paragraph_index) {
					repeat = true;
				}
			}
		}
		return repeat;
	}
	/**
	 * �ڶ��ڻ��һ���������
	 * @param corpus_bfs
	 * @param paragraph_index
	 * @return
	 */
	private int getRondIndex(ArrayList<StringBuffer> corpus_bfs,
			int paragraph_index) {
		int index;
		index = (int) (Math.random() * (corpus_bfs.get(paragraph_index).length() - 1));
		return index;
	}
	/**
	 * �������Ķ�������
	 * @param corpus_bfs
	 * @return
	 */
	private int getRondParagraphIndex(ArrayList<StringBuffer> corpus_bfs) {
		int paragraph_index;
		paragraph_index = (int) ( Math.random() * (corpus_bfs.size() - 1));
		return paragraph_index;
	}

	/**
	 * ��ʼ�����ƴʿ⣬�����ڴ棬�ӿ����ʱЧ��
	 * 
	 * @throws IOException
	 */
	private static void initSimiliarlCorpus() throws IOException {
		System.out.println("init Similiar Corpus!");
		if (sim_found_table == null) {
			// ����һ��hashmap��Ϊ���ұ���K���֣�V���洢�����������
			sim_found_table = new HashMap<String, String>();
			// �洢������������ÿһ�����ƴʣ�һ�ж�Ӧһ�����ƴ���
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
						// �鿴�Ƿ�������֣����û�����ӣ�����оͽ�index��ϣ������ʽ index1|index2
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
				System.out.println(sim_store_table.size());
				System.out.println("similar corpus completed!");
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