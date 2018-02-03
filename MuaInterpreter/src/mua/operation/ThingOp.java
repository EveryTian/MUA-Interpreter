package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.NameError;
import mua.error.TypeError;

public final class ThingOp extends Operation {
	
	public ThingOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "thing";
	}
	
	@Override
	public void execute(NameSpace space) throws TypeError, NameError {
		if ((returnValue = space.getWholeSpaceBindingData(arguments[0].getWordValue())) == null) {		
			throw new NameError(arguments[0], "undefined name (in " + space.getFunctionName() + ")");
		}
	}
	
}
