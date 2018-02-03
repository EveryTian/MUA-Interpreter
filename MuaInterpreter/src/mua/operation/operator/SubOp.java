package mua.operation.operator;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class SubOp extends Operator {

	public SubOp(Data arg0, Data arg1) {
		super("sub", arg0, arg1);
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		returnValue = new Data(arguments[0].getNumberValue() - arguments[1].getNumberValue());
	}

}
