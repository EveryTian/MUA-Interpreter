package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class IslistOp extends Operation {

	public IslistOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "islist";
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		returnValue = new Data(arguments[0].getType() == Data.Type.LIST);
	}

}
