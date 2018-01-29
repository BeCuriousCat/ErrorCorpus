package errors;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import util.GeneratePY2HanZiLib;
//pinyin4j entry point 
import net.sourceforge.pinyin4j.PinyinHelper;

//only when you need formating the output 
import net.sourceforge.pinyin4j.format.*; 
import net.sourceforge.pinyin4j.format.exception.*;

public class PinYinError extends Error {

	private String root = System.getProperty("user.dir");
	private HashMap<String, HashSet<Character>> map = null;

	public PinYinError(String name, double rate) {
		super(name, rate);
		ArrayList<Sign> sign = new ArrayList<Sign>();
		setSigns(sign);
	}

	@Override
	public boolean process(ArrayList<StringBuffer> corpus_bfs,
			ArrayList<Error> errors) {
		// TODO Auto-generated method stub
		initPinYinLib();
		
		
		//��ʼ��pinyin4j
		HanyuPinyinOutputFormat deFormat = new HanyuPinyinOutputFormat();
		deFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		deFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		//���ɴ���Ķ����������������
		int paragraph_index = 0; 
		int index = 0;
		
		//Ҫ�ĵ���
		char charAtText;
		//�Ƿ��ظ�
		boolean repeat = false; 
		StringBuffer paragraph = null;
		//ƴ��
		String[] pinyin ;
		// ����ν��ʴ���
		ArrayList<Sign> signs = new ArrayList<Sign>();
		System.out.println("��ʼ��������ִ���");
		int count = 0; //������������ѡ��set�е����һ���ַ�
		char correct_word;
		char wrong_word = 0;
		for (int i = 0; i < getError_size(); i++) {
			int loop_cnt = 0;
			int repeat_cnt = 0;
			while(true){
				repeat = false;
				//�������,ѡ��һ����
				paragraph_index = getRondParagraphIndex(corpus_bfs);
				if( corpus_bfs.get(paragraph_index).length() <= 0){
					paragraph_index = getRondParagraphIndex(corpus_bfs);
				}
				index = getRondIndex(corpus_bfs, paragraph_index);
				paragraph = corpus_bfs.get(paragraph_index);
				charAtText = paragraph.charAt(index);
				//System.out.println("======"+charAtText);
				loop_cnt +=1;
				if( loop_cnt >= 5000) {
					//TODO
					System.out.println("Warning: loop times over"+loop_cnt);
					//System.exit(1);
				}
				try {
					pinyin = PinyinHelper.toHanyuPinyinStringArray(charAtText,deFormat);
					//�����᳤ֻ�Ȼ����1��ѡ����������һ������ֵ��pinyin[0]
					if(pinyin.length > 1){
						int py_len = pinyin.length;
						int rand = (int)(Math.random() * py_len);
						//System.out.println(pinyin[rand]);
						pinyin[0] = pinyin[rand];
					}
					
					if(!map.containsKey(pinyin[0])){
						continue;
					}else{
						repeat = isRepeated(errors, index, paragraph_index, repeat);	
						if(repeat){
							repeat_cnt+=1;
							if( repeat_cnt > 500)
								System.out.println("Wraning:repeated"+repeat_cnt);
							continue;
						}
						//System.out.println(map.get(pinyin[0]));
						HashSet<Character> set = map.get(pinyin[0]);
						int max = set.size();
						//����ƴ����ֻ��һ�����֣�������ѡ��
						if(max <= 1){
							continue;
						}
						int rand = (int) (Math.random() * max);
						Iterator<Character> iter = set.iterator();
						count = 0;
						//��ͬһ��ƴ����ѡ�����ͬ����
						while(iter.hasNext()){
							if(rand == count){
								wrong_word = iter.next();
								break;
							}
							count++;
						}
						
						correct_word = charAtText;
						paragraph.replace(index, index+1, String.valueOf(wrong_word));
						//Ϊ�����ʾ��
						/*paragraph_index += 1;
						index +=1;
						System.out.println("��"+paragraph_index+"�ε�"+index+"����"+correct_word+"�滻��"+wrong_word);
						*/
						Sign sign = new Sign(paragraph_index, index, Character.toString(wrong_word), Character.toString(correct_word));
						signs.add(sign);
						
						break;
					}
				} catch (Exception e) {
					//System.out.println(charAtText+":ת����ƴ����������������ѡ��");
					//e.printStackTrace();
					paragraph_index = getRondParagraphIndex(corpus_bfs);
					index = getRondIndex(corpus_bfs, paragraph_index);
				}
				
			}
		}
		//�����ɵ�λ�÷�����������
		this.setSigns(signs);
		return false;
	}
	/**
	 * ���ԣ�
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		
		sb.append("ƴ�����͸��yԇ�棬ƴ�����ͼ����d");
		sb1.append("�⼸�����ڽ�С��ѧ����ƴ������������Ϊ���Ǹ��򵥵Ĺ����������ظ��Ͷ�����");
		sb2.append("�� tony ʾ��, loƴ���Ŀ����ƺܶ��˲�֪����lo��luo ���в���");
		ArrayList<StringBuffer> bfs = new ArrayList<StringBuffer>();
		
		bfs.add(sb);
		bfs.add(sb1);
		bfs.add(sb2);
		
		PinYinError er = new PinYinError("err", 0.01);
		er.process(bfs, new ArrayList<Error>());
 	}
	//��ʼ��ƴ�����ұ�
	private void initPinYinLib() {
		GeneratePY2HanZiLib PYLib = new GeneratePY2HanZiLib();
		try {
			System.out.println("��ʼ��ƴ�����ҿ�");
			map = PYLib.readText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("init PinYinLib defeat!");
			e.printStackTrace();
		}
	}

}