package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;
import mua.error.ValueError;

public final class EraseOp extends Operation {
	
	public EraseOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = false;
		opName = "erase";
	}
	
	@Override
	public void execute(NameSpace space) throws ValueError, TypeError {
		if (space.eraseBinding(arguments[0].getWordValue()) == null) {
			throw new ValueError(arguments[0], "not in namespace (in " + space.getFunctionName() + ")");
		}
	}

}
