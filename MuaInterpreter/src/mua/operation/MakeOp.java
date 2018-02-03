package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.NameError;
import mua.error.TypeError;

public final class MakeOp extends Operation {
	
	public MakeOp(Data var0, Data var1) {
		arguments = new Data[] { var0, var1 }; 
		hasReturnValue = false;
		opName = "make";
	}
	
	@Override
	public void execute(NameSpace space) throws TypeError, NameError {
		String wordValue = arguments[0].getWordValue();
		if (!isLegalOp(wordValue)) {
			throw new NameError(arguments[0], "illegal binding name (in " + space.getFunctionName() + ")");
		}
		space.addBinding(arguments[0].getWordValue(), arguments[1]);
	}
	
	private boolean isLegalOp(String word) {
		if (word == null || word.isEmpty()) {
			return false;
		}
		char fstCh = word.charAt(0);
		if (!(fstCh == '_' || ('a' <= fstCh && fstCh <= 'z') || ('A' <= fstCh && fstCh <= 'Z'))) {
			return false;
		}
		String legalChars = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_'";
		for (int i = 1; i < word.length(); ++i) {
			if (legalChars.indexOf(word.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
	
}
