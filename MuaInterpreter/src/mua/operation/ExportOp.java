package mua.operation;

import mua.Data;
import mua.Main;
import mua.NameSpace;
import mua.error.NameError;
import mua.error.TypeError;

public final class ExportOp extends Operation {
	
	public ExportOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = false;
		opName = "export";
	}
	
	@Override
	public void execute(NameSpace space) throws NameError, TypeError {
		String word = arguments[0].getWordValue();
		Data data = space.getWholeSpaceBindingData(word);
		if (data == null) {
			throw new NameError(arguments[0], "undefined name (in " + space.getFunctionName() + ")");
		}
		Main.getMainSpace().addBinding(word, data);
	}
	
}
