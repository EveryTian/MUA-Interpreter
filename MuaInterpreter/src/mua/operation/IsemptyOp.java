package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class IsemptyOp extends Operation {

	public IsemptyOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "isempty";
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		if (arguments[0].getType() == Data.Type.WORD) {
			returnValue = new Data(arguments[0].getWordValue().equals(""));
		} else if (arguments[0].getType() == Data.Type.LIST) {
			returnValue = new Data(arguments[0].getListValue().size() == 0);
		} else {
			throw new TypeError(arguments[0], "not a word or a list (in " + space.getFunctionName() + ")");
		}
	}

}
