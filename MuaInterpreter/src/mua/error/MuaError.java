package mua.error;

import mua.Data;

@SuppressWarnings("serial")
public class MuaError extends Exception {
	
	private Data errorData;
	private String errorDataString;
	private String errorTypeName;
	private StringBuffer detailedMessage;
	
	public MuaError(String errorTypeName, Data errorData, String message) {
		super(message);
		this.errorTypeName = errorTypeName;
		this.errorData = errorData;
	}
	
	public MuaError(String errorTypeName, String errorDataString, String message) {
		super(message);
		this.errorTypeName = errorTypeName;
		this.errorDataString = errorDataString;
	}
	
	public Data getErrorData() {
		return errorData;
	}
	
	public String getErrorTypeName() {
		return errorTypeName;
	}
	
	public void generateDetailedMessage() {
		detailedMessage = new StringBuffer();
		detailedMessage.append(errorTypeName)
					   .append(": ")
					   .append(getMessage())
					   .append('\n');
		if (errorData != null) {
			detailedMessage.append("  At ")
						   .append(errorData.toString())
						   .append(" :: ")
						   .append(errorData.getTypeString())
						   .append('\n');
		} else if (errorDataString != null) {
			detailedMessage.append("  At ")
						   .append(errorDataString)
						   .append('\n');
		}
	}
	
	public String getDetailedMessage() {
		return detailedMessage.toString();
	}
	
	public void printErrorInfomation() {
		System.err.println(detailedMessage);
	}
	
}
