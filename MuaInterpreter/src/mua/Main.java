package mua;

import mua.error.*;

public class Main {
	
	static WordCtrler wordCtrler = new WordCtrler();
	static DataGenerator dataGenerator = new DataGenerator(wordCtrler);
	static NameSpace mainSpace = new NameSpace();
	static DataOperationStack dataStack = new DataOperationStack(mainSpace);
	
	public static void main(String[] args) {
		try {
			preload();			
		} catch (MuaError muaError) {
			System.err.println("Preloading code meets error:");
			handleError(muaError);
			System.exit(-1);
		}
		System.out.println("Welcome to MUA Interpreter!");
		System.out.println("    By: RenHaotian (3150104714@zju.edu.cn)");
		Data data;
		while (true) {
			try {
				data = dataGenerator.getNextData();
				if (data == null) {
					handleError(new SyntaxError("] " + wordCtrler.getCurLeftLine(), "unmatched bracket"));
				} else {
					dataStack.push(data);
				}
			} catch (MuaError muaError) {
				handleError(muaError);
			} catch (Exception e) {
				System.err.println("InterpreterRunTimeError:");
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
	
	static void preload() throws MuaError {
		final String preloadContent = new StringBuffer()
				.append("make \"pi [[][output 3.14159]] ")
				.append("make \"run [[l][repeat 1 :l]] ")
				.toString();
		DataOperationStack tempDataStack = new DataOperationStack(mainSpace);
		WordCtrler tempWordCtrler = new WordCtrler(preloadContent);
		DataGenerator tempDataGenerator = new DataGenerator(tempWordCtrler);
		Data data;
		while (true) {
			data = tempDataGenerator.getNextData();
			if (data == null) {
				break;
			}
			tempDataStack.push(data);
		}
	}

	static void handleError(MuaError error) {
		System.err.print("[Line ");
		System.err.print(wordCtrler.getLineNumber());
		System.err.print("] ");
		error.printErrorInfomation();
		wordCtrler.clear();
		dataGenerator.clear();
		dataStack.clear();
	}
	
	public static NameSpace getMainSpace() {
		return mainSpace;
	}

}
