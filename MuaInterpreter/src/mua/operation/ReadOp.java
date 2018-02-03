package mua.operation;

import java.util.Scanner;

import mua.Data;
import mua.NameSpace;
import mua.error.MuaError;

public final class ReadOp extends Operation {
	
	public ReadOp() {
		arguments = new Data[] {}; 
		hasReturnValue = true;
		opName = "read";
	}
	
	@Override
	public void execute(NameSpace space) throws MuaError {
		@SuppressWarnings("resource")
		Scanner scanner= new Scanner(System.in);
		String word = scanner.next();
		returnValue = new Data(word, null);
	}
	
}
