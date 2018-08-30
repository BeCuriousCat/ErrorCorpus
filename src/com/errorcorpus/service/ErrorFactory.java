package com.errorcorpus.service;
import errors.*;
import errors.Error;

public class ErrorFactory {
	public Error genError(String str,double rate){
		Error err = null;
		switch (str) {
		case "Similiar":
			err = new SimiliarError(Error.SimiliarError, rate);
			break;
		case "soundSimiliar":
			err = new PinYinError(Error.PinYinError, rate);
			break;
		case "punctuation":
			err = new PunctuationError(Error.PunctuationError, rate);
			break;
		case "number":
			err = new NumbersError(Error.NumbersError, rate);
			break;
		default:
			break;
			//TODO ³£¼û´Ê´íÎó
//			case "commom":
//				err = new E 
//				break;
		}
		return err;
	}
}
