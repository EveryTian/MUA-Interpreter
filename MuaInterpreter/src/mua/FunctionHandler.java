package mua;

import java.util.Vector;

import mua.error.MuaError;
import mua.error.NameError;
import mua.error.TypeError;

public class FunctionHandler {

	private boolean isFunction = false;
	private String functionName;
	private int argsNum;
	private Vector<String> parameters = new Vector<String>();
	private Data functionBody;
	private NameSpace functionSpace;
	private DataOperationStack dataOperationStack;
	private int curArgsNum = 0;
	
	public FunctionHandler(String functionName, NameSpace callerSpace) throws TypeError {
		Data data = callerSpace.getWholeSpaceBindingData(functionName);
		if (data == null || data.getType() != Data.Type.LIST) {
			return;
		}
		Vector<Data> dataContent = data.getListValue();
		if (dataContent == null || dataContent.size() != 2) {
			return;
		}
		Data args = dataContent.get(0);
		if (args.getType() != Data.Type.LIST) {
			return;
		}
		for (Data arg : args.getListValue()) {
			if (arg.getType() != Data.Type.OP) {
				return;
			}
			parameters.add(arg.getOpValue()); // Initialize parameters.
		}
		if (dataContent.get(1).getType() != Data.Type.LIST) {
			return;
		}
		isFunction = true; // Passed function definition format check. 
		this.functionName = functionName;
		argsNum = parameters.size();
		functionBody = dataContent.get(1); // Initialize function body.
		functionSpace = new NameSpace(this.functionName, callerSpace); // Initialize function space.
		dataOperationStack = new DataOperationStack(functionSpace); // Initialize the operation stack for the function.
	}
	
	public boolean getIsFunction() {
		return isFunction;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	
	public int getArgsNum() {
		return argsNum;
	}
	
	public Data getFunctionBody() {
		return functionBody;
	}
	
	public void addArg(Data arg) throws NameError { // Add parameter and argument value binding to function space.
		if (curArgsNum >= argsNum) {
			return;
		}
		functionSpace.addBinding(parameters.get(curArgsNum++), arg);
	}

	public void execute() throws MuaError {
		for (Data data : functionBody.getListValue()) {
			dataOperationStack.push(data);
			if (dataOperationStack.getStopFlagForFunction()) { // Check stop flag.
				break;
			}
		}
	}

	public boolean hasReturnValue() {
		return functionSpace.getReturnValue() != null;
	}

	public Data getReturnValue() {
		return functionSpace.getReturnValue();
	}
	
}
