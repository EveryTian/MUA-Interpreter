package mua.operation;

import mua.Data;
import mua.NameSpace;

public final class PoallOp extends Operation {
	
	public PoallOp() {
		arguments = new Data[] {}; 
		hasReturnValue = false;
		opName = "poall";
	}
	
	@Override
	public void execute(NameSpace space) {
		for (String key : space.getBindingsKeySet()) {
			System.out.println(key);
//			System.out.print(key);
//			System.out.print(" : ");
//			System.out.println(space.getlocalSpaceBindingData(key));
		}
	}
	
}
