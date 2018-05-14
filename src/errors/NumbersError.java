package errors;

import java.util.ArrayList;
import java.util.Random;

public class NumbersError extends Error {

	private static char[] numberList = { '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '零', '一', '二', '三', '四', '五', '六', '七', '八', '九',
			'〇', '①', '②', '③', '④', '⑤', '⑥', '⑦', '⑧', '⑨', '壹', '贰', '叁',
			'肆', '伍', '陆', '柒', '捌', '玖' };

	public NumbersError(String name, double rate) {
		super(name, rate);
		ArrayList<Sign> sign = new ArrayList<Sign>();
		setSigns(sign);
	}

	@Override
	public boolean process(ArrayList<StringBuffer> corpus_bf,
			ArrayList<Error> errors) {
		int errNum = this.getErrorSize();
		ArrayList<ArrayList<Result>> result = new NumbersAndPunctuation()
				.getNumbers(corpus_bf, "\\p{N}");

		Random random = new Random();
		int totalNums = 0;
		for (int i = 0; i < result.size(); i++) {
			// System.out.println(result.get(i).size());
			totalNums += result.get(i).size();
		}
		while (totalNums > 0 && errNum > 0) {
			int num = random.nextInt(totalNums);

			for (int para = 0; para < result.size(); para++) {
				if (num >= result.get(para).size()) {
					num -= result.get(para).size();
				} else {
					int n = random.nextInt(numberList.length);
					Result r = result.get(para).get(num);
					if (String.valueOf(numberList[n]).equals(r.getValue())
							&& n < numberList.length - 1) {
						corpus_bf.get(para).replace(r.getLocation(),
								r.getLocation() + 1,
								String.valueOf(numberList[n + 1]));
						// System.out.println("ori:"+
						// r.getValue()+"Rep"+String.valueOf(numberList[n+1]));
						Sign sign = new Sign(para, r.getLocation(),
								r.getValue(), String.valueOf(numberList[n + 1]));
						this.getSigns().add(sign);
					} else if (n == numberList.length - 1) {
						corpus_bf.get(para).replace(r.getLocation(),
								r.getLocation() + 1,
								String.valueOf(numberList[n - 1]));
						// System.out.println("ori:"+
						// r.getValue()+"Rep"+String.valueOf(numberList[n-1]));
						Sign sign = new Sign(para, r.getLocation(),
								r.getValue(), String.valueOf(numberList[n - 1]));
						this.getSigns().add(sign);
					} else {
						corpus_bf.get(para).replace(r.getLocation(),
								r.getLocation() + 1,
								String.valueOf(numberList[n]));
						// System.out.println("ori:"+
						// r.getValue()+"Rep"+String.valueOf(numberList[n]));
						Sign sign = new Sign(para, r.getLocation(),
								r.getValue(), String.valueOf(numberList[n]));
						this.getSigns().add(sign);
					}
					errNum--;
					result.get(para).remove(num);
					break;
				}
			}
			totalNums = 0;
			for (int i = 0; i < result.size(); i++) {
				// System.out.println(result.get(i).size());
				totalNums += result.get(i).size();
			}
		}

		/*
		 * System.out.println("++++++++++++++++++++"); for(ArrayList<Result>
		 * res: result){ System.out.println(res.size()); }
		 * System.out.println("remain"+errNum+"words didnot be generated");
		 */
		return true;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<StringBuffer> corpus_bf = new ArrayList<StringBuffer>();
		StringBuffer s1 = new StringBuffer("神烦狗,父爱.搜236发个]图分公\\司发给下");
		StringBuffer s2 = new StringBuffer("是广发、四姑;父分7噩耗");
		StringBuffer s3 = new StringBuffer(
				"送饭翻45译？等VB都我t挨个-=+我GV阿福迪哦我0的=华盛7顿和时间段和司法  ");
		StringBuffer s4 = new StringBuffer("发顺丰9仲（裁同一】人乌）干0达");
		corpus_bf.add(s1);
		corpus_bf.add(s2);
		corpus_bf.add(s3);
		corpus_bf.add(s4);

		for (StringBuffer sb : corpus_bf) {
			System.out.println(sb);
		}
		new NumbersError("test", 1).process(corpus_bf, null);
		for (StringBuffer sb : corpus_bf) {
			System.out.println(sb);
		}
	}

}
