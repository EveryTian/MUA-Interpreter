package mua.operation.operator;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class EqOp extends Operator {

	public EqOp(Data arg0, Data arg1) {
		super("eq", arg0, arg1);
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		returnValue = new Data(arguments[0].equals(arguments[1]));
	}

}
