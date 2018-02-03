package mua.operation;

import java.util.Vector;

import mua.Data;
import mua.error.TypeError;

public final class RepeatOp extends Operation {
	
	private int curTimes = 1;
	private int curAppendedDataPos = 0;
	private double times;
	private Vector<Data> dataList;
	
	public RepeatOp(Data var0, Data var1) throws TypeError {
		arguments = new Data[] { var0, var1 }; 
		hasReturnValue = false;
		opName = "repeat";
		hasAppendData = true;
		times = arguments[0].getNumberValue();
		dataList = arguments[1].getListValue();
	}

	@Override
	public Data getAppendedData() {
		if (dataList.isEmpty() || curTimes > times) {
			return null;
		}
		Data retValue = dataList.get(curAppendedDataPos);
		if (++curAppendedDataPos == dataList.size())
		{
			++curTimes;
			curAppendedDataPos = 0;
		}
		return retValue;
	}
	
}
