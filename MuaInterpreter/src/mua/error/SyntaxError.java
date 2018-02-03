package mua.error;

import mua.Data;

@SuppressWarnings("serial")
public final class SyntaxError extends MuaError {

	public SyntaxError(Data errorData, String message) {
		super("SyntaxError", errorData, message);
		generateDetailedMessage();
	}
	
	public SyntaxError(String errorDataString, String message) {
		super("SyntaxError", errorDataString, message);
		generateDetailedMessage();
	}
	
}
