package mua.operation.operator;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class GtOp extends Operator {

	public GtOp(Data arg0, Data arg1) {
		super("gt", arg0, arg1);
	}

	@Override
	public void execute(NameSpace space) throws TypeError {
		if (arguments[0].getType() != arguments[1].getType()) {
			StringBuffer errorDataStringBuffer = new StringBuffer("gt");
			errorDataStringBuffer.append(' ')
								 .append(arguments[0])
								 .append(' ')
								 .append(arguments[1])
								 .append(" - ")
								 .append(arguments[0].getTypeString())
								 .append(" and ")
								 .append(arguments[1].getTypeString());
			throw new TypeError(errorDataStringBuffer.toString(), "type not match (in " + space.getFunctionName() + ")");
		}
		switch (arguments[0].getType()) {
		case BOOL:
			returnValue = new Data(arguments[0].getBoolValue() && !arguments[1].getBoolValue());
			break;
		case NUMBER:
			returnValue = new Data(arguments[0].getNumberValue() > arguments[1].getNumberValue());
			break;
		case WORD:
			returnValue = new Data(arguments[0].getWordValue().compareTo(arguments[1].getWordValue()) > 0);
			break;
		default:
			throw new TypeError(arguments[0], "unordered type (in " + space.getFunctionName() + ")");
		}
	}

}
