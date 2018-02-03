package mua.error;

import mua.Data;

@SuppressWarnings("serial")
public final class TypeError extends MuaError {

	public TypeError(Data errorData, String message) {
		super("TypeError", errorData, message);
		generateDetailedMessage();
	}
	
	public TypeError(String errorDataString, String message) {
		super("TypeError", errorDataString, message);
		generateDetailedMessage();
	}
	
}
