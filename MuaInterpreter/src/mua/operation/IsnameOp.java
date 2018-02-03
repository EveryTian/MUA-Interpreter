package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class IsnameOp extends Operation {
	
	public IsnameOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "isname";
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		String word = arguments[0].getWordValue();
		returnValue = new Data(space.wholeSpaceContainsBinding(word));
	}
	
}
