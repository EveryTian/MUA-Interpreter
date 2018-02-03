package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class IntOp extends Operation{

	public IntOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "int";
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		returnValue = new Data(Math.floor(arguments[0].getNumberValue()));
	}

}
