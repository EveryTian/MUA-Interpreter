package mua;

import java.util.Stack;
import java.util.Vector;

import mua.error.*;
import mua.operation.OperationHandler;

public class DataOperationStack { // The core of the interpreter.

	private class ArgsNumRecord { // Record the arguments number of operation and function and store handler.
		
		private int oriArgsNum;     // The arguments number of the operation or the function. 
		private int curLeftArgsNum; // When 1 argument push into operation stack, --curLeftArgsNum.
		private boolean isFunction; // Whether the record for a function.
		private OperationHandler operationHandler;
		private FunctionHandler functionHandler;
		
		ArgsNumRecord(OperationHandler operationHandler) { // Use to record operation.
			isFunction = false;
			this.operationHandler = operationHandler;
			oriArgsNum = curLeftArgsNum = this.operationHandler.getArgsNum();
		}
		
		ArgsNumRecord(FunctionHandler functionHandler) { // Use to record function.
			isFunction = true;
			this.functionHandler = functionHandler;
			oriArgsNum = curLeftArgsNum = this.functionHandler.getArgsNum();
		}
		
		int getOriArgsNum() {
			return oriArgsNum;
		}
		
		int decCurLeftArgsNum() {
			return --curLeftArgsNum;
		}
		
		boolean getIsFunction() {
			return isFunction;
		}
		
		OperationHandler getOperationHandler() {
			return operationHandler;
		}
		
		FunctionHandler getFunctionHandler() {
			return functionHandler;
		}
		
	}
	
	private Vector<Data> dataVector = new Vector<Data>(); // Where real operation stack stores.
	private Stack<ArgsNumRecord> sucArgsNumStack = new Stack<ArgsNumRecord>();
	private NameSpace space; // A DataOperationStack is binding with a NameSpace.
	private boolean stopFlagForFunction = false; // Communicate with corresponding FunctionHandler.
	
	public DataOperationStack(NameSpace space) {
		if (space == null) {
			this.space = new NameSpace();
		} else {
			this.space = space;
		}
	}
	
	public NameSpace getNameSpace() {
		return space;
	}
	
	public boolean getStopFlagForFunction() {
		return stopFlagForFunction;
	}
	
	private boolean getIsFunctionMode() {
		return space.getIsFunctionSpace();
	}
	
	private String getFunctionName() {
		return space.getFunctionName();
	}
	
	void push(Data data) throws MuaError { // Core.
/*		 BELOW4DEBUG
		 Print DataOperationStack Info:*/
//		 System.out.print(" " + getFunctionName() + " : ");
//		 for (Data x : dataVector) {
//		 System.out.print(x.toString() + " ");
//		 }
//		 System.out.println("+" + data);
/*		 ABOVE4DEBUG*/
		if (data == null) {
			return;
		}
		if (data.getType() == Data.Type.OP) {
			int argsNum = OperationHandler.getOpArgsNum(data.getOpValue());
			if (argsNum == -1) { // -1 means not an operation.
				FunctionHandler functionHandler = new FunctionHandler(data.getOpValue(), space);
				if (functionHandler.getIsFunction()) {
					if (functionHandler.getArgsNum() == 0) { // A function accepting no argument shall be executed immediately.
						functionHandler.execute();
						Data functionReturnValue = functionHandler.getReturnValue();
						if (functionReturnValue != null) {
							push(functionReturnValue);
						}
					} else {
						dataVector.add(data);
						sucArgsNumStack.push(new ArgsNumRecord(functionHandler)); // Use sucArgsNumStack to record.
					}
				} else { // Neither an operation nor a function.
					throw new NameError(data, "undefined name (in " + getFunctionName() + ")");
				}
			} else if (argsNum == 0) { // An operation accepting no argument shall be executed immediately.
				String opValue = data.getOpValue();
				if (opValue.equals("stop") && getIsFunctionMode()) {
					stopFlagForFunction = true; // Signal function handler to stop.
					clear();
				} else {
					OperationHandler operationHandler = new OperationHandler(opValue, space);
					operationHandler.execute();
					if (operationHandler.hasReturnValue()) {
						data = operationHandler.getReturnValue();
						push(data);
					}
				}
			} else {
				OperationHandler operationHandler = new OperationHandler(data.getOpValue(), space);
				dataVector.add(data);
				sucArgsNumStack.push(new ArgsNumRecord(operationHandler));
			}
		} else if (sucArgsNumStack.isEmpty()) { // List shall be run when operation stack is empty.
			if (data.getType() == Data.Type.LIST) {
				for (Data listItem : data.getListValue()) {
					push(listItem);
				}
			} else if (!getIsFunctionMode()) { // The ways to handle other data types.
				System.out.print("// ");
				System.out.print(data);
				System.out.print(" :: ");
				System.out.println(data.getTypeString());
			}
		} else { // Data whose type is not OP.
			dataVector.add(data);
			if (sucArgsNumStack.peek().decCurLeftArgsNum() == 0) { // Data number reaches arguments number. 
				ArgsNumRecord argsNumRecord = sucArgsNumStack.pop();
				int oriArgsNum = argsNumRecord.getOriArgsNum();
				if (argsNumRecord.getIsFunction()) { // Execute function. 
					FunctionHandler functionHandler = argsNumRecord.getFunctionHandler();
					for (int i = oriArgsNum - 1; i >= 0; --i) {
						functionHandler.addArg(peekAt(i));
					}
					functionHandler.execute();
					removeFromTop(oriArgsNum + 1); // Remove function and its arguments from operation stack.
					Data returnValue = functionHandler.getReturnValue();
					if (returnValue != null) {
						push(returnValue);
					}
				} else { // Execute operation.
					OperationHandler operationHandler = argsNumRecord.getOperationHandler();
					for (int i = oriArgsNum - 1; i >= 0; --i) {
						operationHandler.addArg(peekAt(i));
					}
					operationHandler.execute();
					removeFromTop(oriArgsNum + 1); // Remove operation and its arguments from operation stack.
					if (operationHandler.hasAppendedData()) { // Operation can append data to operation stack.
						Data appendedData;
						while ((appendedData = operationHandler.getAppendedData()) != null) {
							if (appendedData.getType() == Data.Type.OP && appendedData.getOpValue().equals("stop")) {
								break;
							}
							push(appendedData);
						}
					}
					if (operationHandler.hasReturnValue()) {
						Data returnValue = operationHandler.getReturnValue();
						push(returnValue);
					}
				}
			}
		}
	}
	
	int size() {
		return dataVector.size();
	}
	
	Data peekAt(int n) { // Return the nth data from the top of operation stack.
		if (size() <= n) {
			return null;
		}
		return dataVector.get(size() - n - 1);
	}
	
	Data pop() {
		if (size() == 0) {
			return null;
		}
		return dataVector.remove(size() - 1);
	}
	
	void removeFromTop(int n) { // Remove n data from the top of operation stack.
		for (int i = 0; i < n; ++i) {
			pop();
		}
	}
	
	void clear() {
		dataVector.clear();
		sucArgsNumStack.clear();
	}
	
}
