import java.util.ArrayList;
import java.util.List;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import errors.Error;
import errors.Sign;
import errors.SimiliarError;


public class tk {
	public static void main(String[] args) {
		
	
	String str = "[{'errorRate':0.004999999888241291,'errorSize':9,'name':'similiar','signs':[{'correct_word':'侩','index':91,'paragraph':10,'wrong_word':'会'},{'correct_word':'谎','index':75,'paragraph':17,'wrong_word':'流'},{'correct_word':'景','index':15,'paragraph':5,'wrong_word':'见'},{'correct_word':'沾','index':18,'paragraph':4,'wrong_word':'战'},{'correct_word':'桥','index':60,'paragraph':6,'wrong_word':'请'},{'correct_word':'催','index':7,'paragraph':9,'wrong_word':'崔'},{'correct_word':'渗','index':12,'paragraph':5,'wrong_word':'参'},{'correct_word':'杏','index':63,'paragraph':6,'wrong_word':'席'},{'correct_word':'注','index':64,'paragraph':11,'wrong_word':'主'}]},{'errorRate':0.004999999888241291,'errorSize':9,'name':'pinyin','signs':[{'correct_word':'伂','index':12,'paragraph':8,'wrong_word':'陪'},{'correct_word':'焀','index':1,'paragraph':8,'wrong_word':'沪'},{'correct_word':'甁','index':12,'paragraph':7,'wrong_word':'平'},{'correct_word':'蜀','index':6,'paragraph':8,'wrong_word':'书'},{'correct_word':'袀','index':25,'paragraph':7,'wrong_word':'军'},{'correct_word':'簀','index':19,'paragraph':0,'wrong_word':'泽'},{'correct_word':'襂','index':3,'paragraph':4,'wrong_word':'森'},{'correct_word':'躀','index':130,'paragraph':14,'wrong_word':'关'},{'correct_word':'沁','index':38,'paragraph':17,'wrong_word':'钦'}]}]";
	String str2 = "{'errorRate':0.004999999888241291,'errorSize':9,'name':'similiar','signs':[{'correct_word':'侩','index':91,'paragraph':10,'wrong_word':'会'},{'correct_word':'谎','index':75,'paragraph':17,'wrong_word':'流'},{'correct_word':'景','index':15,'paragraph':5,'wrong_word':'见'},{'correct_word':'沾','index':18,'paragraph':4,'wrong_word':'战'},{'correct_word':'桥','index':60,'paragraph':6,'wrong_word':'请'},{'correct_word':'催','index':7,'paragraph':9,'wrong_word':'崔'},{'correct_word':'渗','index':12,'paragraph':5,'wrong_word':'参'},{'correct_word':'杏','index':63,'paragraph':6,'wrong_word':'席'},{'correct_word':'注','index':64,'paragraph':11,'wrong_word':'主'}]}";
	String st = "{'correct_word':'侩','index':91,'paragraph':10,'wrong_word':'会'}";
	Sign s = JSON.parseObject(st,Sign.class);
	
	System.out.println(s.toString());
	List<Error> error = JSON.parseArray(str,Error.class);
	System.out.println(error.toString());
	

	
	}
}
