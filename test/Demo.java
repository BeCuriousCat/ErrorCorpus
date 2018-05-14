import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

import corpus.Corpus;
import errors.EasyWrongWords;
import errors.Error;
import errors.NumbersError;
import errors.PinYinError;
import errors.PunctuationError;
import errors.SimiliarError;

public class Demo {
	public static void main(String[] args) {
		String path = System.getProperty("user.dir") + "/data/201710.txt";
		int corpus_size = 10000;
		float rate = 0.001f;
		
		for (; rate <= (0.001 * 32) ; rate = rate * 2 ) {
			// 设置错误的名称
			Error easyWrongWords = new EasyWrongWords(Error.EasyWrongWords, rate * 0.2);
			//Error simError = new SimiliarError(Error.SimiliarError, rate * 0.5);
			//Error pinyinError = new PinYinError(Error.PinYinError, rate * 0.5);
			//Error numbersError = new NumbersError(Error.NumbersError, rate * 0.2);
			//Error punctuationError = new PunctuationError(Error.PunctuationError, rate * 0.2);
			// 生成一个语料库，设置原始语料库的路径和生成语料库的大小
			Corpus cor = new Corpus(path, corpus_size);
			System.out.println(cor.getCorpus_size());
			// 将错误添加到语料库中
			//cor.addErrors(easyWrongWords);

			cor.addErrors(easyWrongWords);
			//System.out.println(easyWrongWords.getErrorSize());
			//cor.addErrors(simError);
			//cor.addErrors(pinyinError);
			//cor.addErrors(numbersError);
			//cor.addErrors(punctuationError);
			// 运行各错误添加的逻辑，添加错误

			long sTime = System.currentTimeMillis();
			cor.run();
			long eTime = System.currentTimeMillis();

			System.out.println("run time: " + (eTime - sTime) / 1000.0);

			// 生成的文件的名称
			DecimalFormat df = new DecimalFormat("######0.0000");   
			String filename = String.valueOf(df.format(rate));
			// conf文件是否需要输出非json部分
			boolean withConfHeader = false;
			try {
				cor.write("T2_1_Word"+filename, withConfHeader);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}
}