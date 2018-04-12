package errors;

import java.util.ArrayList;
import java.util.Random;

public class PunctuationError extends Error{
	private static char[] punctuationList = {',','.','?','\'','\"',';',':','\\','>','<','=','+','-','{','}','[',']','|','(',')','*','&','^','%','$','#','@','!','`','~',
            '��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��','��'};
	
	public PunctuationError(String name, double rate) {
		super(name, rate);
		ArrayList<Sign> sign = new ArrayList<Sign>();
		setSigns(sign);
	}
	
	@Override
	public boolean process(ArrayList<StringBuffer> corpus_bf, ArrayList<Error> errors) {
		int errNum = this.getErrorSize();
		ArrayList<ArrayList<Result>> result = new NumbersAndPunctuation().getNumbers(corpus_bf, "\\p{P}");
		
		
		Random random = new Random();
		int totalNums = 0;
		for(int i = 0; i<result.size();i++){
			//System.out.println(result.get(i).size());
			totalNums += result.get(i).size();
		}
		while(totalNums>0&&errNum>0){
			int num = random.nextInt(totalNums);
			
			for(int para=0; para<result.size(); para++){
				if(num >= result.get(para).size()){
					num -= result.get(para).size(); 
				}else{
					int n = random.nextInt(punctuationList.length);
					Result r = result.get(para).get(num);
					if(String.valueOf(punctuationList[n]).equals(r.getValue())&& n<punctuationList.length-1){
						corpus_bf.get(para).replace(r.getLocation(), r.getLocation()+1, String.valueOf(punctuationList[n+1]));
						//System.out.println("ori:"+ r.getValue()+"Rep"+String.valueOf(punctuationList[n+1]));
						Sign sign = new Sign(para, r.getLocation(), r.getValue(), String.valueOf(punctuationList[n+1]));
						this.getSigns().add(sign);
					}else if(n==punctuationList.length-1){
						corpus_bf.get(para).replace(r.getLocation(), r.getLocation()+1, String.valueOf(punctuationList[n-1]));
						//System.out.println("ori:"+ r.getValue()+"Rep"+String.valueOf(punctuationList[n-1]));
						Sign sign = new Sign(para, r.getLocation(), r.getValue(), String.valueOf(punctuationList[n-1]));
						this.getSigns().add(sign);
					}else{
						corpus_bf.get(para).replace(r.getLocation(), r.getLocation()+1, String.valueOf(punctuationList[n]));
						//System.out.println("ori:"+ r.getValue()+"Rep"+String.valueOf(punctuationList[n]));
						Sign sign = new Sign(para, r.getLocation(), r.getValue(), String.valueOf(punctuationList[n]));
						this.getSigns().add(sign);
					}
					errNum--;
					result.get(para).remove(num);
					break;
				}
			}
			totalNums = 0;
			for(int i = 0; i<result.size();i++){
				//System.out.println(result.get(i).size());
				totalNums += result.get(i).size();
			}
		}
			
		
		/*
		System.out.println("++++++++++++++++++++");
		for(ArrayList<Result> res: result){
			System.out.println(res.size());
		}
		System.out.println("remain"+errNum+"words didnot be generated");
		*/
		return true;
	}
	
	public static void main(String[] args) {
		ArrayList<StringBuffer> corpus_bf = new ArrayList<StringBuffer>();
		StringBuffer s1= new StringBuffer("�񷳹�,����.��236����]ͼ�ֹ�\\˾������");
		StringBuffer s2= new StringBuffer("�ǹ㷢���Ĺ�;����7ج��");
		StringBuffer s3= new StringBuffer("�ͷ���45�룿��VB����t����-=+��GV������Ŷ��0��=��ʢ7�ٺ�ʱ��κ�˾��  ");
		StringBuffer s4= new StringBuffer("��˳��9�٣���ͬһ�����ڣ���0��");
		corpus_bf.add(s1);
		corpus_bf.add(s2);
		corpus_bf.add(s3);
		corpus_bf.add(s4);
		
		for(StringBuffer sb: corpus_bf){
			System.out.println(sb);
		}
		new PunctuationError("test",1).process(corpus_bf, null);
		for(StringBuffer sb: corpus_bf){
			System.out.println(sb);
		}

	}



	

}
