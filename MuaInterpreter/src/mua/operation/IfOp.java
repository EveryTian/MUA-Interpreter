package mua.operation;

import java.util.Vector;

import mua.Data;
import mua.error.TypeError;

public class IfOp extends Operation{
	
	Vector<Data> runList;
	int curAppendedDataPos = 0;
	
	public IfOp(Data var0, Data var1, Data var2) throws TypeError {
		arguments = new Data[] { var0, var1, var2 }; 
		hasReturnValue = false;
		opName = "if";
		hasAppendData = true;
		boolean boolValue = arguments[0].getBoolValue();
		Vector<Data> trueList = arguments[1].getListValue();
		Vector<Data> falseList = arguments[2].getListValue();
		runList = boolValue ? trueList : falseList;
	}

	@Override
	public Data getAppendedData() {
		if (runList.isEmpty() || curAppendedDataPos >= runList.size()) {
			return null;
		}
		return runList.get(curAppendedDataPos++);
	}
	
}
