package mua.operation;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;

import mua.Data;
import mua.NameSpace;
import mua.WordCtrler;
import mua.error.MuaError;
import mua.error.NameError;

public final class LoadOp extends Operation {
	
	WordCtrler wordCtrler;
	
	public LoadOp(Data var0) {
		arguments = new Data[] { var0 }; 
		hasReturnValue = false;
		opName = "save";
	}
	
	@Override
	public void execute(NameSpace space) throws MuaError {
		try {
			FileReader fileReader = new FileReader(arguments[0].getWordValue());
			BufferedReader bufferReader = new BufferedReader(fileReader);
			while (true) {
				String key = bufferReader.readLine();
				if (key == null) {
					break;
				}
				key = key.trim();
				if (key.isEmpty() || !isLegalOp(key)) {
					break;
				}
				String value = bufferReader.readLine();
				if (value == null) {
					break;
				}
				value = value.trim();
				if (value.isEmpty() || !isBracketsNumLegal(value)) {
					break;
				}
				wordCtrler = new WordCtrler(value);
				Data bindingData = generateData();
				if (bindingData == null) {
					break;
				}
				space.addBinding(key, bindingData);
			}
			bufferReader.close();
			fileReader.close();
		} catch (FileNotFoundException e) {
			throw new NameError(arguments[0], "file not exist (in " + space.getFunctionName() + ")");
		} catch (IOException e) {
			System.err.println("InterpreterRunTimeError:");
			e.printStackTrace();
			System.exit(-1);
		}
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
	
	private boolean isBracketsNumLegal(String str) {
		int unmatchedLeftBracketsNum = 0;
		for (int i = 0; i < str.length(); ++i) {
			switch (str.charAt(i)) {
			case '[':
				++unmatchedLeftBracketsNum;
				break;
			case ']':
				--unmatchedLeftBracketsNum;
				break;
			default:
				break;
			}
			if (unmatchedLeftBracketsNum < 0) {
				return false;
			}
		}
		return unmatchedLeftBracketsNum == 0;
	}
	
	private Data generateData() throws MuaError {
		Data data;
		String word = wordCtrler.getNextWord();
		if (word.equals("") || word.equals("]")) {
			return null; // As the end of List.
		}
		data = new Data(word, null);
		if (word.equals("[")) {
			Data listItem;
			while ((listItem = generateData()) != null) {
				data.addListItem(listItem);
			}
		}
		return data;
	}
	
}
