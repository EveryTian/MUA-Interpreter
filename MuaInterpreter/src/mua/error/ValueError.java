package mua.error;

import mua.Data;

@SuppressWarnings("serial")
public class ValueError extends MuaError {

	public ValueError(Data errorData, String message) {
		super("ValueError", errorData, message);
		generateDetailedMessage();
	}

	public ValueError(String errorDataString, String message) {
		super("ValueError", errorDataString, message);
		generateDetailedMessage();
	}
	
}
