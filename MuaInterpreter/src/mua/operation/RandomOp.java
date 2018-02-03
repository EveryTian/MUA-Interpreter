package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class RandomOp extends Operation {
	
	public RandomOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "random";
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		double argument = arguments[0].getNumberValue();
		if (argument < 0) {
			returnValue = new Data(0);
		} else {
			returnValue = new Data(Math.floor(argument * Math.random()));
		}	
	}
	
}
