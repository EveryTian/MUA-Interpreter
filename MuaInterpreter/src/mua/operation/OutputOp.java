package mua.operation;

import mua.Data;
import mua.NameSpace;

public final class OutputOp extends Operation {

	public OutputOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = false;
		opName = "output";
	}
	
	@Override
	public void execute(NameSpace space) {
		space.setReturnValue(arguments[0]);
	}
	
}
