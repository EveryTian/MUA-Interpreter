package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;
import mua.error.ValueError;

public final class SqrtOp extends Operation{

	public SqrtOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = true;
		opName = "sqrt";
	}

	@Override
	public void execute(NameSpace space) throws TypeError, ValueError {
		double argument = arguments[0].getNumberValue();
		if (argument < 0) {
			throw new ValueError(arguments[0], "sqrt argment < 0 (in " + space.getFunctionName() + ")");
		}
		returnValue = new Data(Math.sqrt(argument));
	}
	
}
