package corpusgeneration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import textpreprocessing.EasyWrongTableProcessing;
import wordsegment.NLPIR;

class Result{
	int location;
	String value;
	
	public Result(int location, String value){
		this.location = location;
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getLocation() {
		return location;
	}

	public void setLocation(int location) {
		this.location = location;
	}

	
	
}

public class EasyWrongWords extends Error{
	
	//构造函数
	public EasyWrongWords(String name, double rate) {
		super(name, rate);
		// TODO Auto-generated constructor stub
		ArrayList<Sign> sign = new ArrayList<Sign>();
		setSigns(sign);
	}

	
	//继承自Error类，用于1. 判断现在生成的错误与之前生成的ArrayList<Error> errors（传入的参数）中的错误没有冲突
	//2. 将生成的错误写入ArrayList<StringBuffer> corpus_bf中
	public  boolean process(ArrayList<StringBuffer> corpus_bf,ArrayList<Error> errors){
		
		HashMap<String,ArrayList> map = new EasyWrongTableProcessing().easyWrongTableProcessing();
		//int errorNum = this.getError_size();
		int errorNum = 2;
	    ArrayList< ArrayList<Result>> result = new ArrayList< ArrayList<Result>>();
	    
	    int paraNum = corpus_bf.size();
		for(int i=0;i<paraNum;i++){
			String wordSeg = new NLPIR().wordSegment(corpus_bf.get(i).toString()); //进行句子分词
			//System.out.println(wordSeg);
			String[] wordList = wordSeg.split(" ");
			
			ArrayList<Result> res = new ArrayList<Result>();
			
			int loc = 0;	
			for(String words:wordList){
				Result r = new Result(loc, words);
				res.add(r);
				loc += words.length();
			}
			result.add(res);
		}
		
		/*
		for(ArrayList<Result> res: result){
			for(Result r: res){
				System.out.println("location:"+r.getLocation()+"value:"+r.getValue());
			}
		}*/
		Random random = new Random();
		int totalWords = 0;
		for(int i = 0; i<result.size();i++){
			System.out.println(result.get(i).size());
			totalWords += result.get(i).size();
		}
		while(totalWords>0&&errorNum>0){
			int num = random.nextInt(totalWords);
			
			for(int para=0; para<result.size(); para++){
				if(num >= result.get(para).size()){
					num -= result.get(para).size(); 
				}
				else{
					if(map.containsKey(result.get(para).get(num).getValue())){
						String rep = null;
						Result r = result.get(para).get(num);
						int len = map.get(r.getValue()).size();
						if(len==1){
							rep = (String) map.get(r.getValue()).get(0);
						}else{
							int index = random.nextInt(len);
							rep = (String) map.get(r.getValue()).get(index);
						}
						
						corpus_bf.get(para).replace(r.getLocation(), r.getLocation()+r.getValue().length(), rep);
						System.out.println("ori:"+ r.getValue()+"Rep"+rep);
						errorNum--;
					}
					result.get(para).remove(num);
					break;
				}
			}
			
			totalWords = 0;
			for(int i = 0; i<result.size();i++){
				System.out.println(result.get(i).size());
				totalWords += result.get(i).size();
			}
		}

		
		System.out.println("++++++++++++++++++++");
		for(ArrayList<Result> res: result){
			System.out.println(res.size());
		}
		System.out.println("remain"+errorNum+"words didnot be generated");
		
		return true;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<StringBuffer> corpus_bf = new ArrayList<StringBuffer>();
		StringBuffer s1= new StringBuffer("神烦狗父爱搜发个图分公司发给下");
		StringBuffer s2= new StringBuffer("是广发四姑父分噩耗");
		StringBuffer s3= new StringBuffer("送饭翻译等VB都我挨个我GV阿福迪哦我的华盛顿和时间段和司法  ");
		StringBuffer s4= new StringBuffer("发顺丰同一人乌干达");
		corpus_bf.add(s1);
		corpus_bf.add(s2);
		corpus_bf.add(s3);
		corpus_bf.add(s4);
		for(StringBuffer sb: corpus_bf){
			System.out.println(sb);
		}
		new EasyWrongWords("test",1).process(corpus_bf, null);
		for(StringBuffer sb: corpus_bf){
			System.out.println(sb);
		}
	}

}
