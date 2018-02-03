package mua.operation.operator;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;
import mua.error.ValueError;

public final class DivOp extends Operator {

	public DivOp(Data arg0, Data arg1) {
		super("div", arg0, arg1);
	}

	@Override
	public void execute(NameSpace space) throws ValueError, TypeError {
		double divisor = arguments[1].getNumberValue();
		if (divisor == 0) {
			throw new ValueError(arguments[1], "division by zero (in " + space.getFunctionName() + ")");
		}
		returnValue = new Data(arguments[0].getNumberValue() / divisor);		
	}

}
