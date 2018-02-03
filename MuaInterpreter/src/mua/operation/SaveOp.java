package mua.operation;

import java.io.FileWriter;
import java.io.IOException;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;

public final class SaveOp extends Operation {

	public SaveOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = false;
		opName = "save";
	}
	
	@Override
	public void execute(NameSpace space) throws TypeError {
		try {
			FileWriter fileWriter = new FileWriter(arguments[0].getWordValue());
			for (String key : space.getBindingsKeySet()) {
				fileWriter.write(key);
				fileWriter.write('\n');
				fileWriter.write(space.getlocalSpaceBindingData(key).toString());
				fileWriter.write('\n');
			}
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			System.err.println("InterpreterRunTimeError:");
			e.printStackTrace();
			System.exit(-1);
		}
	}
	
}
