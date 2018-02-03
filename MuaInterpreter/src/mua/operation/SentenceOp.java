package mua.operation;

import java.util.Vector;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class SentenceOp extends Operation {
	
	public SentenceOp(Data var0, Data var1) {
		arguments = new Data[] { var0, var1 }; 
		hasReturnValue = true;
		opName = "sentence";
	}
	
	@Override
	public void execute(NameSpace space) throws TypeError {
		Vector<Data> newListValue = new Vector<Data>(arguments[0].getListValue());
		newListValue.addAll(arguments[1].getListValue());
		returnValue = new Data(newListValue);
	}
	
}
