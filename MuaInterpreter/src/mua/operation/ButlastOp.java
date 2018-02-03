package mua.operation;

import java.util.Vector;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;
import mua.error.ValueError;

public final class ButlastOp extends Operation {
	
	public ButlastOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "butlast";
	}

	@Override
	public void execute(NameSpace space) throws TypeError, ValueError {
		switch (arguments[0].getType()) {
		case LIST:
			Vector<Data> listValue = new Vector<Data>(arguments[0].getListValue());
			int listValueSize = listValue.size();
			if (listValueSize == 0) {
				throw new ValueError(arguments[0], "empty list (in " + space.getFunctionName() + ")");
			}
			listValue.remove(listValueSize - 1);
			returnValue = new Data(listValue);
			break;
		case WORD:
			String wordValue = arguments[0].getWordValue();
			int wordValueLength = wordValue.length();
			if (wordValueLength == 0) {
				throw new ValueError(arguments[0], "empty word (in " + space.getFunctionName() + ")");
			}
			returnValue = new Data(wordValue.substring(0, wordValueLength - 1));
			break;
		default:
			throw new TypeError(arguments[0], "not a word or a list (in " + space.getFunctionName() + ")");
		}
	}

}
