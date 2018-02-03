package mua.operation;

import java.util.Vector;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;
import mua.error.ValueError;

public final class FirstOp extends Operation {
	
	public FirstOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "first";
	}

	@Override
	public void execute(NameSpace space) throws TypeError, ValueError {
		switch (arguments[0].getType()) {
		case LIST:
			Vector<Data> listValue = arguments[0].getListValue();
			if (listValue.size() == 0) {
				throw new ValueError(arguments[0], "empty list (in " + space.getFunctionName() + ")");
			}
			returnValue = new Data(listValue.firstElement());
			break;
		case WORD:
			String wordValue = arguments[0].getWordValue();
			if (wordValue.length() == 0) {
				throw new ValueError(arguments[0], "empty word (in " + space.getFunctionName() + ")");
			}
			returnValue = new Data(arguments[0].getWordValue().substring(0, 1));
			break;
		default:
			throw new TypeError(arguments[0], "not a word or a list (in " + space.getFunctionName() + ")");
		}
	}
	
}
