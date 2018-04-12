package util;

import com.sun.jna.Library;
import com.sun.jna.Native;



public class NLPIR {
	// ����ӿ�CLibrary���̳���com.sun.jna.Library
	public interface CLibrary extends Library {
	// ���岢��ʼ���ӿڵľ�̬����
	CLibrary Instance = (CLibrary) Native.loadLibrary("G:\\Myeclipse10.0\\ICTCLAS2016\\NLPIR\\lib\\win64\\NLPIR", CLibrary.class);
				
	public int NLPIR_Init(String sDataPath, int encoding,String sLicenceCode);
		
	public String NLPIR_GetLastErrorMsg();
	public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);
	//��TXT�ļ����ݽ��зִ�
    public double NLPIR_FileProcess(String sSourceFilename,String sResultFilename, int bPOStagged);
	//��TXT�ļ�����ȡ�ؼ��� 
	  
	public void NLPIR_Exit();
	}
	
	public String wordSegment(String strInput){
		//String argu = System.getProperty("User.dir")+"\\ICTCLAS2016\\NLPIR";
		String argu = "G:\\Myeclipse10.0\\ICTCLAS2016\\NLPIR";
		int charset_type = 1;
		
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;

		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("��ʼ��ʧ�ܣ�fail reason is "+nativeBytes);
			return "";
		}
		

		//String nativeBytes = null;
		try {
			nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(strInput, 0);

			//System.out.println("�ִʽ��Ϊ�� " + nativeBytes);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		CLibrary.Instance.NLPIR_Exit();
		return nativeBytes;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sInput = "��Ϥ���ʼ��ܾ��ѽ������й�����ٴ�ͨ��������Ҫ��������ǿ���仪���׵Ĳ�����Դ�����估�ִ��Ȼ��ڵĹܿش�ʩ����Ч�����仪���ױ�δ���ҹ�ũҵ����ȫ��������׼��ת����Ʒϵ��Ⱦ��";
		System.out.println(new NLPIR().wordSegment(sInput));
	}

}
