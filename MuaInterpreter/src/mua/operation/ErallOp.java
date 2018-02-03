package mua.operation;

import mua.Data;
import mua.NameSpace;

public final class ErallOp extends Operation {
	
	public ErallOp() {
		arguments = new Data[] {}; 
		hasReturnValue = false;
		opName = "erall";
	}
	
	@Override
	public void execute(NameSpace space) {
		space.clearBindings();
	}

}
