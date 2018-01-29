package util;

import java.io.File;
import java.util.ArrayList;

public class CheckOut {
	ArrayList<Error> errors = null; 
	/**
	 * 解析Error_conf文件成json
	 * @param path conf文件路径
	 */
	public void readJson(String path){
		//TODO 解析json函数
		File file = new File(path);
		if(!file.exists()){
			System.out.println("文件不存在！");
			System.exit(0);
		}
		System.out.println("函数没有写完");
	}
	/**
	 * 查询第paragraph_index段第index位置的predict，是否正确
	 * @param paragraph_index
	 * @param index
	 * @param predict
	 * @return 0 表示 该位置不存在错误  1表示该位置存在错误，但是predict预测错误 2表示该位置存在错误且预测正确
	 */
	public int check(int paragraph_index,int index,String predict){
		//TODO
		if( errors == null ){
			System.out.println("请先输入读取一个conf文件！");
		}
		System.out.println("函数没有写完");
		return 0;
	}

}

