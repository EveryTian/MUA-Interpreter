package mua.operation.operator;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class NotOp extends Operator {

	public NotOp(Data arg0) {
		super("not", arg0, null);
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		returnValue = new Data(!arguments[0].getBoolValue());
	}

}
