package mua.operation.operator;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class AndOp extends Operator {

	public AndOp(Data arg0, Data arg1) {
		super("and", arg0, arg1);
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		returnValue = new Data(arguments[0].getBoolValue() && arguments[1].getBoolValue());
	}

}
