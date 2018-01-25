import java.io.IOException;

import errors.Error;
import errors.PinYinError;
import errors.SimiliarError;

public class Demo {
	public static void main(String[] args) {
		
		String path = System.getProperty("user.dir")+"/data/201602.txt";
		int corpus_size = 200000;
		//���ô��������
		Error simError = new SimiliarError(Error.SimiliarError, 0.001);
		Error pinyinError = new PinYinError(Error.PinYinError,0.001);
		
		//����һ�����Ͽ⣬����ԭʼ���Ͽ��·�����������Ͽ�Ĵ�С
		Corpus cor = new Corpus(path, corpus_size);
		System.out.println(cor.getCorpus_size());
		//��������ӵ����Ͽ���
		cor.addErrors(simError);
		cor.addErrors(pinyinError);
		//���и�������ӵ��߼�����Ӵ���
		cor.run();
		//���ɵ��ļ�������
		String filename = "similar_corpus";
		//conf�ļ��Ƿ���Ҫ�����json����
		boolean withConfHeader = false;
		try {
			cor.write(filename,withConfHeader);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
