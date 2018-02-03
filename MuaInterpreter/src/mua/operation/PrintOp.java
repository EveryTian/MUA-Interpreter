package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class PrintOp extends Operation {
	
	public PrintOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = false;
		opName = "print";
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		if (arguments[0].getType() == Data.Type.WORD) {
			System.out.println(arguments[0].getWordValue());
		} else {
			System.out.println(arguments[0]);
		}
	}
	
}
