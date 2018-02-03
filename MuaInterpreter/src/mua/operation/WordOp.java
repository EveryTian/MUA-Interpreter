package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.MuaError;
import mua.error.TypeError;

public final class WordOp extends Operation {
	
	public WordOp(Data var0, Data var1) {
		arguments = new Data[] { var0, var1 }; 
		hasReturnValue = true;
		opName = "word";
	}
	
	@Override
	public void execute(NameSpace space) throws MuaError {
		String fstWordValue = arguments[0].getWordValue();
		String sndWordValue;
		switch (arguments[1].getType()) {
		case BOOL:
			sndWordValue = arguments[1].getBoolValue() ? "true" : "false";
			break;
		case NUMBER:
			sndWordValue = Double.toString(arguments[1].getNumberValue());
			break;
		case WORD:
			sndWordValue = arguments[1].getWordValue();
			break;
		default:
			throw new TypeError(arguments[1], "not a word or a number or a bool (in " + space.getFunctionName() + ")");
		}
		returnValue = new Data(fstWordValue + sndWordValue);
	}

}
