package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.MuaError;

public interface IOperation {
	public String getOpName();
	public int getArgumentsNum();
	public Data getArgumentAt(int argumentIndex);
	public void execute(NameSpace space) throws MuaError;
	public boolean getHasReturnValue();
	public boolean getHasAppendedData();
	public Data getReturnValue();
	public Data getAppendedData();
}
