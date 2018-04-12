import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import errors.Error;
import errors.Sign;
import errors.SimiliarError;


public class tk {
	public static void main(String[] args) {
		
	
	String str = "[{'errorRate':0.004999999888241291,'errorSize':9,'name':'similiar','signs':[{'correct_word':'��','index':91,'paragraph':10,'wrong_word':'��'},{'correct_word':'��','index':75,'paragraph':17,'wrong_word':'��'},{'correct_word':'��','index':15,'paragraph':5,'wrong_word':'��'},{'correct_word':'մ','index':18,'paragraph':4,'wrong_word':'ս'},{'correct_word':'��','index':60,'paragraph':6,'wrong_word':'��'},{'correct_word':'��','index':7,'paragraph':9,'wrong_word':'��'},{'correct_word':'��','index':12,'paragraph':5,'wrong_word':'��'},{'correct_word':'��','index':63,'paragraph':6,'wrong_word':'ϯ'},{'correct_word':'ע','index':64,'paragraph':11,'wrong_word':'��'}]},{'errorRate':0.004999999888241291,'errorSize':9,'name':'pinyin','signs':[{'correct_word':'��','index':12,'paragraph':8,'wrong_word':'��'},{'correct_word':'�W','index':1,'paragraph':8,'wrong_word':'��'},{'correct_word':'�J','index':12,'paragraph':7,'wrong_word':'ƽ'},{'correct_word':'��','index':6,'paragraph':8,'wrong_word':'��'},{'correct_word':'Ђ','index':25,'paragraph':7,'wrong_word':'��'},{'correct_word':'�j','index':19,'paragraph':0,'wrong_word':'��'},{'correct_word':'�I','index':3,'paragraph':4,'wrong_word':'ɭ'},{'correct_word':'�I','index':130,'paragraph':14,'wrong_word':'��'},{'correct_word':'��','index':38,'paragraph':17,'wrong_word':'��'}]}]";
	String str2 = "{'errorRate':0.004999999888241291,'errorSize':9,'name':'similiar','signs':[{'correct_word':'��','index':91,'paragraph':10,'wrong_word':'��'},{'correct_word':'��','index':75,'paragraph':17,'wrong_word':'��'},{'correct_word':'��','index':15,'paragraph':5,'wrong_word':'��'},{'correct_word':'մ','index':18,'paragraph':4,'wrong_word':'ս'},{'correct_word':'��','index':60,'paragraph':6,'wrong_word':'��'},{'correct_word':'��','index':7,'paragraph':9,'wrong_word':'��'},{'correct_word':'��','index':12,'paragraph':5,'wrong_word':'��'},{'correct_word':'��','index':63,'paragraph':6,'wrong_word':'ϯ'},{'correct_word':'ע','index':64,'paragraph':11,'wrong_word':'��'}]}";
	String st = "{'correct_word':'��','index':91,'paragraph':10,'wrong_word':'��'}";
	Sign s = JSON.parseObject(st,Sign.class);
	
	System.out.println(s.toString());
	List<Error> error = JSON.parseArray(str,Error.class);
	System.out.println(error.toString());
	

	
	}
}
