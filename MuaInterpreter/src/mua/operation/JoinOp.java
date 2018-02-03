package mua.operation;

import java.util.Vector;

import mua.Data;
import mua.NameSpace;
import mua.error.MuaError;

public final class JoinOp extends Operation {
	
	public JoinOp(Data var0, Data var1) {
		arguments = new Data[] { var0, var1 }; 
		hasReturnValue = true;
		opName = "join";
	}
	
	@Override
	public void execute(NameSpace space) throws MuaError {
		Vector<Data> oriListValue = new Vector<Data>(arguments[0].getListValue());
		oriListValue.add(arguments[1]);
		returnValue = new Data(oriListValue);
	}

}
