package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.MuaError;

public abstract class Operation implements IOperation {

	protected String opName;
	protected boolean hasReturnValue;
	protected Data[] arguments;
	protected Data returnValue;
	protected boolean hasAppendData = false;

	public String getOpName() {
		return opName;
	}
	
	public int getArgumentsNum() {
		return arguments == null ? -1 : arguments.length; // -1 as not initialized.
	}
	
	public Data getArgumentAt(int argumentIndex) {
		if (argumentIndex < getArgumentsNum()) {
			return arguments[argumentIndex];
		}
		return null;
	}
	
	public void execute(NameSpace space) throws MuaError {
	} // Subclass overrides and implements this to set return value or handle I/O or operate name space.
	
	public boolean getHasReturnValue() {
		return hasReturnValue;
	}
	
	public Data getReturnValue() {
		return hasReturnValue ? returnValue : null;
	}

	public boolean getHasAppendedData() {
		return hasAppendData;
	}

	public Data getAppendedData() {
		return null;
	} // Subclass overrides and implements this to add data to the operation stack. 

}
