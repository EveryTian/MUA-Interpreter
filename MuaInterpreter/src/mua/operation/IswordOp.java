package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class IswordOp extends Operation {

	public IswordOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "isnumber";
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		returnValue = new Data(arguments[0].getType() == Data.Type.WORD);
	}

}
