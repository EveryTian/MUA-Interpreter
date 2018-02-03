package mua.operation.operator;

import mua.Data;
import mua.operation.Operation;

public abstract class Operator extends Operation {
	
	Operator(String opName, Data arg0, Data arg1) {
		arguments = new Data[arg1 == null ? 1 : 2];
		arguments[0] = arg0;
		if (arg1 != null) {
			arguments[1] = arg1;
		}
		hasReturnValue = true;
		super.opName = opName;
	}
	
}
