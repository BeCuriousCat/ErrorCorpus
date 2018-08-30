import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import corpus.Corpus;
import errors.EasyWrongWords;
import errors.Error;
import errors.PinYinError;
import errors.SimiliarError;


public class GenAndOutPutNonProofWord {
	public static void main(String[] args) {
		//String path = System.getProperty("user.dir") + "/data/201710.txt";
		int corpus_size = 20000;
		float rate = 0.002f;
		
		String root = System.getProperty("user.dir") + "/data/target";
		ArrayList<File> files = getAllFile(root);
		for (File file : files) {
			System.out.println(file.getName());
			String Fname = file.getName();
			String FType = Fname.substring(file.getName().lastIndexOf(".") + 1);
			String name = Fname.substring(0, file.getName().lastIndexOf("."));
			System.out.println(Fname);
			// ����ҵ��Ĳ���txt����������
			if (!FType.equals("txt")) {
				System.out.println("û���ҵ��ļ�"+Fname+".txt");
				continue;
			}
			
			//genWordError(file.getAbsolutePath(), corpus_size, rate);
			//genCharError(file.getAbsolutePath(), corpus_size, rate);
		}

	}

	private static void genWordError(String path, int corpus_size, float rate) {
		// ���ô��������
		Error easyWrongWords = new EasyWrongWords(Error.EasyWrongWords, rate);
		// Error simError = new SimiliarError(Error.SimiliarError, rate * 0.5);
		// Error pinyinError = new PinYinError(Error.PinYinError, rate * 0.5);
		// Error numbersError = new NumbersError(Error.NumbersError, rate *
		// 0.2);
		// Error punctuationError = new PunctuationError(Error.PunctuationError,
		// rate * 0.2);
		// ����һ�����Ͽ⣬����ԭʼ���Ͽ��·�����������Ͽ�Ĵ�С
		Corpus cor = new Corpus(path, corpus_size);
		System.out.println(cor.getCorpus_size());
		// ��������ӵ����Ͽ���
		// cor.addErrors(easyWrongWords);

		cor.addErrors(easyWrongWords);
		// System.out.println(easyWrongWords.getErrorSize());
		// cor.addErrors(simError);
		// cor.addErrors(pinyinError);
		// cor.addErrors(numbersError);
		// cor.addErrors(punctuationError);
		// ���и�������ӵ��߼�����Ӵ���

		long sTime = System.currentTimeMillis();
		cor.run();
		long eTime = System.currentTimeMillis();

		System.out.println("run time: " + (eTime - sTime) / 1000.0);

		// ���ɵ��ļ�������
		DecimalFormat df = new DecimalFormat("######0.0000");
		String filename = String.valueOf(df.format(rate));
		// conf�ļ��Ƿ���Ҫ�����json����
		boolean withConfHeader = false;
		try {
			String source = path.substring(path.lastIndexOf("\\") + 1,
					path.lastIndexOf("."));
			cor.write4Word("E(Word)" + "T" + source + "R" + filename + "W"
					+ corpus_size, withConfHeader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void genCharError(String path, int corpus_size, float rate) {
		// ���ô��������
		//Error easyWrongWords = new EasyWrongWords(Error.EasyWrongWords, rate);
		Error simError = new SimiliarError(Error.SimiliarError, rate * 0.5);
		Error pinyinError = new PinYinError(Error.PinYinError, rate * 0.5);
		// Error numbersError = new NumbersError(Error.NumbersError, rate *
		// 0.2);
		// Error punctuationError = new PunctuationError(Error.PunctuationError,
		// rate * 0.2);
		// ����һ�����Ͽ⣬����ԭʼ���Ͽ��·�����������Ͽ�Ĵ�С
		Corpus cor = new Corpus(path, corpus_size);
		System.out.println(cor.getCorpus_size());
		// ��������ӵ����Ͽ���
		// cor.addErrors(easyWrongWords);

		//cor.addErrors(easyWrongWords);
		// System.out.println(easyWrongWords.getErrorSize());
		 cor.addErrors(simError);
		 cor.addErrors(pinyinError);
		// cor.addErrors(numbersError);
		// cor.addErrors(punctuationError);
		// ���и�������ӵ��߼�����Ӵ���

		long sTime = System.currentTimeMillis();
		cor.run();
		long eTime = System.currentTimeMillis();

		System.out.println("run time: " + (eTime - sTime) / 1000.0);

		// ���ɵ��ļ�������
		DecimalFormat df = new DecimalFormat("######0.0000");
		String filename = String.valueOf(df.format(rate));
		// conf�ļ��Ƿ���Ҫ�����json����
		boolean withConfHeader = false;
		try {
			String source = path.substring(path.lastIndexOf("\\") + 1,
					path.lastIndexOf("."));
			cor.write4Word("E(Character)" + "T" + source + "R" + filename + "W"
					+ corpus_size, withConfHeader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static ArrayList<File> getAllFile(String path) {
		ArrayList<File> flist = new ArrayList<File>();

		File file = new File(path);

		if (file.isDirectory()) {
			for (File f : file.listFiles()) {
				flist.add(f);
			}
		}
		return flist;
	}
}
