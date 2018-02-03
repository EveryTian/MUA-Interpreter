package mua.error;

import mua.Data;

@SuppressWarnings("serial")
public final class NameError extends MuaError {
	
	public NameError(Data errorData, String message) {
		super("NameError", errorData, message);
		generateDetailedMessage();
	}
	
	public NameError(String errorDataString, String message) {
		super("NameError", errorDataString, message);
		generateDetailedMessage();
	}
	
}
