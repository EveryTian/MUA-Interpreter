package mua.operation;

import java.util.Vector;

import mua.Data;
import mua.NameSpace;
import mua.error.MuaError;

public final class ListOp extends Operation {
	
	public ListOp(Data var0, Data var1) {
		arguments = new Data[] { var0, var1 }; 
		hasReturnValue = true;
		opName = "list";
	}
	
	@Override
	public void execute(NameSpace space) throws MuaError {
		Vector<Data> emptyListValue = new Vector<Data>();
		returnValue = new Data(emptyListValue);
		returnValue.addListItem(arguments[0]);
		returnValue.addListItem(arguments[1]);
	}

}
