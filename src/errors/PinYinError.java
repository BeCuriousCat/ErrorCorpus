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
		
		
		//初始化pinyin4j
		HanyuPinyinOutputFormat deFormat = new HanyuPinyinOutputFormat();
		deFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		deFormat.setVCharType(HanyuPinyinVCharType.WITH_V);
		//生成错误的段落索引和序号索引
		int paragraph_index = 0; 
		int index = 0;
		
		//要改的字
		char charAtText;
		//是否重复
		boolean repeat = false; 
		StringBuffer paragraph = null;
		//拼音
		String[] pinyin ;
		// 添加形近词错误
		ArrayList<Sign> signs = new ArrayList<Sign>();
		System.out.println("开始添加音近字错误！");
		int count = 0; //计数器，用来选择set中的随机一个字符
		char correct_word;
		char wrong_word = 0;
		for (int i = 0; i < getError_size(); i++) {
			int loop_cnt = 0;
			int repeat_cnt = 0;
			while(true){
				repeat = false;
				//随机索引,选择一个字
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
					//多音字会长度会大于1，选择其他任意一个，赋值给pinyin[0]
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
						//若改拼音下只有一个汉字，则重新选择
						if(max <= 1){
							continue;
						}
						int rand = (int) (Math.random() * max);
						Iterator<Character> iter = set.iterator();
						count = 0;
						//从同一个拼音中选择随机同音字
						while(iter.hasNext()){
							if(rand == count){
								wrong_word = iter.next();
								break;
							}
							count++;
						}
						
						correct_word = charAtText;
						paragraph.replace(index, index+1, String.valueOf(wrong_word));
						//为输出显示：
						/*paragraph_index += 1;
						index +=1;
						System.out.println("第"+paragraph_index+"段第"+index+"处："+correct_word+"替换成"+wrong_word);
						*/
						Sign sign = new Sign(paragraph_index, index, Character.toString(wrong_word), Character.toString(correct_word));
						signs.add(sign);
						
						break;
					}
				} catch (Exception e) {
					//System.out.println(charAtText+":转换成拼音出错！已跳过重新选择！");
					//e.printStackTrace();
					paragraph_index = getRondParagraphIndex(corpus_bfs);
					index = getRondIndex(corpus_bfs, paragraph_index);
				}
				
			}
		}
		//把生成的位置放入类属性中
		this.setSigns(signs);
		return false;
	}
	/**
	 * 测试！
	 * @param args
	 */
	public static void main(String[] args) {
		StringBuffer sb = new StringBuffer();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		
		sb.append("拼音字型更新測試版，拼音字型及下載");
		sb1.append("这几天正在教小孩学汉语拼音，本来自认为这是个简单的工作，就是重复劳动罢了");
		sb2.append("例 tony 示例, lo拼音的咯估计很多人不知道，lo和luo 是有差别的");
		ArrayList<StringBuffer> bfs = new ArrayList<StringBuffer>();
		
		bfs.add(sb);
		bfs.add(sb1);
		bfs.add(sb2);
		
		PinYinError er = new PinYinError("err", 0.01);
		er.process(bfs, new ArrayList<Error>());
 	}
	//初始化拼音查找表
	private void initPinYinLib() {
		GeneratePY2HanZiLib PYLib = new GeneratePY2HanZiLib();
		try {
			System.out.println("初始化拼音查找库");
			map = PYLib.readText();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("init PinYinLib defeat!");
			e.printStackTrace();
		}
	}

}