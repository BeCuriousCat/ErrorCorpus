package wordsegment;

import com.sun.jna.Library;
import com.sun.jna.Native;



public class NLPIR {
	// 定义接口CLibrary，继承自com.sun.jna.Library
	public interface CLibrary extends Library {
	// 定义并初始化接口的静态变量
	CLibrary Instance = (CLibrary) Native.loadLibrary("F:\\ICTCLAS2016\\NLPIR\\lib\\win64\\NLPIR", CLibrary.class);
				
	public int NLPIR_Init(String sDataPath, int encoding,String sLicenceCode);
		
	public String NLPIR_GetLastErrorMsg();
	public String NLPIR_ParagraphProcess(String sSrc, int bPOSTagged);
	//对TXT文件内容进行分词
    public double NLPIR_FileProcess(String sSourceFilename,String sResultFilename, int bPOStagged);
	//从TXT文件中提取关键词 
	  
	public void NLPIR_Exit();
	}
	
	public String wordSegment(String strInput){
		String argu = "F:\\ICTCLAS2016\\NLPIR";
		int charset_type = 1;
		
		int init_flag = CLibrary.Instance.NLPIR_Init(argu, charset_type, "0");
		String nativeBytes = null;
		
		if (0 == init_flag) {
			nativeBytes = CLibrary.Instance.NLPIR_GetLastErrorMsg();
			System.err.println("初始化失败！fail reason is "+nativeBytes);
			return "";
		}
		
		

		//String nativeBytes = null;
		try {
			nativeBytes = CLibrary.Instance.NLPIR_ParagraphProcess(strInput, 0);
			//System.out.println("分词结果为： " + nativeBytes);
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		
		CLibrary.Instance.NLPIR_Exit();
		return nativeBytes;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sInput = "据悉，质检总局已将最新有关情况再次通报美方，要求美方加强对输华玉米的产地来源、运输及仓储等环节的管控措施，有效避免输华玉米被未经我国农业部安全评估并批准的转基因品系污染。";
		new NLPIR().wordSegment(sInput);
	}

}
