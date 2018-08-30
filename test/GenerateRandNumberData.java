import java.util.List;

import com.mifmif.common.regex.Generex;
import com.mifmif.common.regex.util.Iterator;


public class GenerateRandNumberData {
	public static void main(String[] args) {
		Generex generex = new Generex("[0-3]([a-c]|[e-g]{1,2})");
		Generex date = new Generex("([0-9]+[ÄêÔÂÈÕ]){1,3}");

		// Generate random String
		String randomStr = date.getFirstMatch();
		System.out.println(randomStr);// a random value from the previous String list


		// Generate all String that matches the given Regex.
		List<String> matchedStrs = date.getAllMatchedStrings();

		// Using Generex iterator
		Iterator iterator = date.iterator();
		while (iterator.hasNext()) {
			System.out.print(iterator.next() + " ");
		}
	}
}
