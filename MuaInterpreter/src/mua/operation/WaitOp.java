package mua.operation;

import mua.Data;
import mua.NameSpace;
import mua.error.TypeError;
import mua.error.ValueError;

public final class WaitOp extends Operation {

	public WaitOp(Data var0) {
		arguments = new Data[] { var0 };
		hasReturnValue = true;
		opName = "wait";
	}

	@Override
	public void execute(NameSpace space) throws TypeError, ValueError {
		try {
			long sleepTime = (long) arguments[0].getNumberValue();
			if (sleepTime > 0) {
				Thread.sleep(sleepTime);
			}
		} catch (InterruptedException e) {
			System.err.println("InterpreterRunTimeError:");
			e.printStackTrace();
			System.exit(-1);
		}
	}

}
