package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

//����.doc��׺��word  
public class WordWriterUtil {
	public static void createWord(String path, String fileName) {
		// �ж�Ŀ¼�Ƿ����
		File file = new File(path);
		XWPFDocument doc = new XWPFDocument();
		// exists()���Դ˳���·������ʾ���ļ���Ŀ¼�Ƿ���ڡ�
		// mkdir()�����˳���·����ָ����Ŀ¼��
		// mkdirs()�����˳���·����ָ����Ŀ¼���������б��赫�����ڵĸ�Ŀ¼��
		if (!file.exists())
			file.mkdirs();
		// ��ΪHWPFDocument��û���ṩ�����Ĺ��췽�� ����û�а취����word
		// ����ʹ��word2007�����ϵ�XWPFDocument�����й���word
		@SuppressWarnings("resource")
		XWPFDocument document = new XWPFDocument();
		OutputStream stream = null;
		try {
			stream = new FileOutputStream(new File(file, fileName));
			document.write(stream);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (stream != null)
				;
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// ��word��д������
	/**
	 * ��Щ������Ҫ���������͵Ĳ�����ʱ��һ������áﾲ̬�Ľӿ�.������������
	 * 
	 * @param path
	 * @param data
	 */
	public static void writeDataDocx(String path, String data) {
		InputStream istream = null;
		OutputStream ostream = null;
		try {
			istream = new FileInputStream(path);
			ostream = new FileOutputStream(path);
			@SuppressWarnings("resource")
			XWPFDocument document = new XWPFDocument();
			// ���һ������
			XWPFParagraph p1 = document.createParagraph();
			// setAlignment()ָ��Ӧ�����ڴ˶����е��ı��Ķ�����뷽ʽ��CENTER LEFT...
			// p1.setAlignment(ParagraphAlignment.LEFT);
			// p1.setBorderBetween(Borders.APPLES);
			// p1.setBorderBottom(Borders.APPLES);
			// p1.setBorderLeft(Borders.APPLES);ָ��Ӧ��ʾ�����ҳ��ָ������Χ�ı߽硣
			// p1.setBorderRight(Borders.ARCHED_SCALLOPS);ָ��Ӧ��ʾ���Ҳ��ҳ��ָ������Χ�ı߽硣
			// p1.setBorderTop(Borders.ARCHED_SCALLOPS);ָ��Ӧ��ʾ�Ϸ�һ������ͬ��һ��α߽����õĶ���ı߽硣�⼸���ǶԶ���֮��ĸ�ʽ��ͳһ���൱�ڸ�ʽˢ
			// p1.setFirstLineIndent(99);//---���Ŀ�Ȼ���΢��խ
			// p1.setFontAlignment(1);//---����Ķ��뷽ʽ 1�� 2�� 3�� 4���� �� ����д0�͸���
			// p1.setIndentationFirstLine(400);//---��������,ָ�������������Ӧ�����ڸ��εĵ�һ�С�
			// p1.setIndentationHanging(400);//---����ǰ��,ָ������������Ӧͨ����һ�лص���ʼ���ı����ķ������ƶ������Ӹ��εĵ�һ����ɾ����
			// p1.setIndentationLeft(400);//---�������������ƣ�ָ��ӦΪ�����ҶΣ��öε����ݵ���ߵ�Ե����һ��������ߵľ���ұ��ı��߾�����Ȩ�е��Ƕ��ı����ұ�Ե֮�������,���ʡ�Դ����ԣ���Ӧ�ٶ���ֵΪ�㡣
			// p1.setIndentationRight(400);//---ָ��Ӧ������һ�Σ��öε����ݴ����Ҷε��ұ�Ե����ȷ�ı��߾���ұ��ı��߾�����Ȩ�е��Ƕ��ı����ұ�Ե֮�������,���ʡ�Դ����ԣ���Ӧ�ٶ���ֵΪ�㡣
			// p1.setIndentFromLeft(400);//---��������
			// p1.setIndentFromRight(400);
			// p1.setNumID(BigInteger.TEN);
			// p1.setPageBreak(true);//--ָ������Ⱦ�˷�ҳ��ͼ�е��ĵ�����һ�ε����ݶ��������ĵ��е���ҳ�Ŀ�ʼ��
			// p1.setSpacingAfter(6);//--ָ��Ӧ������ĵ��о��Ե�λ��һ�ε����һ��֮��ļ�ࡣ
			// p1.setSpacingAfterLines(6);//--ָ��Ӧ����ڴ��ߵ�λ���ĵ��еĶ�������һ��֮��ļ�ࡣ
			// p1.setSpacingBefore(6);//--ָ��Ӧ���������һ���ĵ��о��Ե�λ�еĵ�һ�еļ�ࡣ
			// p1.setSpacingBeforeLines(6);//--ָ��Ӧ����ڴ��ߵ�λ���ĵ��еĶ���ĵ�һ��֮ǰ�ļ�ࡣ
			// p1.setSpacingLineRule(LineSpacingRule.AT_LEAST);//--ָ����֮��ļ����μ���洢���������С�
			// p1.setStyle("");//--�˷����ṩ����ʽ�Ķ��䣬��ǳ�����.
			// p1.setVerticalAlignment(TextAlignment.CENTER);//---ָ�����ı��Ĵ�ֱ���뷽ʽ��Ӧ���ڴ˶����е��ı�
			// p1.setWordWrapped(true);//--��Ԫ��ָ���Ƿ�������Ӧ�жϳ���һ�е��ı���Χ��ͨ�����������
			// ����������ȼ��� �����л�ͨ���ƶ�����һ�� ���ڴʻ�����ϴ��ƣ� ����ʵ��������֡�
			XWPFRun r1 = p1.createRun();// p1.createRun()��һ��������׷�ӵ���һ��
			// setText(String value)��
			// setText(String value,int pos)
			// value - the literal text which shall be displayed in the document
			// pos - - position in the text array (NB: 0 based)
			r1.setText(data);
			// r1.setTextPosition(20);//����൱�������м��ģ��������20����ô��ģ������,��Ԫ��ָ���ı�ӦΪ�������ڹ�ϵ����Χ�Ƕ�λ�ı���Ĭ�ϻ��������������������������ϵ��м��
			// ---This element specifies the amount by which text shall be
			// ��raised or lowered�� for this run in relation to the default
			// baseline of the surrounding non-positioned text.
			// r1.setStrike(true);//---����ɾ���ߵ�,����!!!
			// r1.setStrikeThrough(true);---Ҳ������ɾ���ߣ�������ϸ΢�������
			// r1.setEmbossed(true);---�������Ӱ�������һ�㣩
			// r1.setDoubleStrikethrough(true);---����˫ɾ����
			// r1.setColor("33CC00");//---����������ɫ ��
			// r1.setFontFamily("fantasy");
			// r1.setFontFamily("cursive");//---����ASCII(0 - 127)������ʽ
			// r1.setBold(jiacu);//---"�ӺڼӴ�"
			//r1.setFontSize(size);// ---�����С
			// r1.setImprinted(true);//�о���setEmbossed(true)���ƣ�����Ӱ
			// r1.setItalic(true);//---�ı�������б����һ�����壿
			// r1.setShadow(true);//---�ı���������Ӱ����ǰ����������ӰЧ���ķ����о�ûʲô����
			// r1.setSmallCaps(true);//---�ı��� Ӣ����ĸ �ĸ�ʽ
			// r1.setSubscript(VerticalAlign.BASELINE);//---valign��ֱ�����
			// r1.setUnderline(UnderlinePatterns.DASH);//--��underline type�����»���
			// document.createTable(2, 2);//--����һ���ƶ����еı�
			// document.enforceReadonlyProtection();//--ǿ��ִ���ƶȱ���

			/**
			 * r1.setDocumentbackground(doc, "FDE9D9");//����ҳ�汳��ɫ
			 * r1.testSetUnderLineStyle(doc);//�����»�����ʽ�Լ�ͻ����ʾ�ı�
			 * r1.addNewPage(doc, BreakType.PAGE);
			 * r1.testSetShdStyle(doc);//�������ֵ���
			 */
			document.write(ostream);
			System.out.println("����word�ɹ�");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (istream != null) {
				try {
					istream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (ostream != null) {
				try {
					ostream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	// ��word��д������
	// public static void writeDataDoc(String path,String data){
	// OutputStream ostream=null;
	// try {
	// ostream = new FileOutputStream(path);
	// ostream.write(data.getBytes());
	// } catch (FileNotFoundException e) {
	// e.printStackTrace();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }finally{
	// if(ostream != null){
	// try {
	// ostream.close();
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
	// }
	// }

	// ��ȡ���� docx
	public static String readDataDocx(String filePath) {
		String content = "";
		InputStream istream = null;
		try {
			istream = new FileInputStream(filePath);
			@SuppressWarnings("resource")
			XWPFDocument document = new XWPFDocument(istream);
			// getLastParagraph()���ذ���ҳü��ҳ�ŵ��ı��Ķ���
			// getText()�����ĵ������ı�
			content = document.getLastParagraph().getText();// ������
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (istream != null) {

			}
		}
		return content;
	}
}