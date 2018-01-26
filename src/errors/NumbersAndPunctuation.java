package errors;

import java.util.ArrayList;

public class NumbersAndPunctuation {

	public ArrayList<ArrayList<Result>> getNumbers(ArrayList<StringBuffer> corpus_bf,String matche){		
		ArrayList<ArrayList<Result>> result = new ArrayList<ArrayList<Result>>();
		for(StringBuffer cor_bf: corpus_bf){
			ArrayList<Result> res = new ArrayList<Result>();
			for(int i = 0; i < cor_bf.length(); i++){
				if(String.valueOf(cor_bf.charAt(i)).matches(matche)){
					Result r = new Result(i,String.valueOf(cor_bf.charAt(i)));
					res.add(r);
				}
			}
			result.add(res);
		}
		return result;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<StringBuffer> corpus_bf = new ArrayList<StringBuffer>();
		StringBuffer s1= new StringBuffer("�񷳹�,����.��236����]ͼ�ֹ�\\˾������");
		StringBuffer s2= new StringBuffer("�ǹ㷢���Ĺ�;����7ج��");
		StringBuffer s3= new StringBuffer("�ͷ���45�룿��VB����t����-=+��GV������Ŷ��0��=��ʢ7�ٺ�ʱ��κ�˾��  ");
		StringBuffer s4= new StringBuffer("��˳��9�٣���ͬһ�����ڣ���0��");
		corpus_bf.add(s1);
		corpus_bf.add(s2);
		corpus_bf.add(s3);
		corpus_bf.add(s4);
		ArrayList<ArrayList<Result>> result = new NumbersAndPunctuation().getNumbers(corpus_bf, "\\p{P}");
		for(ArrayList<Result> res: result){
			System.out.println("��ji�Σ�");
			for(Result r: res){
				System.out.print("loc"+r.getLocation()+"Value"+r.getValue());
			}
		}
		
		ArrayList<ArrayList<Result>> result1 = new NumbersAndPunctuation().getNumbers(corpus_bf, "\\p{N}");
		for(ArrayList<Result> res: result1){
			System.out.println("��ji�Σ�");
			for(Result r: res){
				System.out.print("loc"+r.getLocation()+"Value"+r.getValue());
			}
		}

	}

}
