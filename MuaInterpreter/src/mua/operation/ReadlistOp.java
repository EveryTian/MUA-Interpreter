package mua.operation;

import java.util.Scanner;

import mua.Data;
import mua.NameSpace;
import mua.WordCtrler;
import mua.error.*;

public final class ReadlistOp extends Operation {
	
	WordCtrler wordCtrler;
	
	public ReadlistOp() {
		arguments = new Data[] {}; 
		hasReturnValue = true;
		opName = "readlist";
	}
	
	@Override
	public void execute(NameSpace space) throws MuaError {
		@SuppressWarnings("resource")
		Scanner scanner= new Scanner(System.in);
		String line = scanner.nextLine();
		line = line.trim();
		if (line.isEmpty()) {
			returnValue = new Data("[", null);
			return;
		}
		if (!isBracketsNumLegal(line)) {
			throw new SyntaxError(line, "brackets unmatch (in " + space.getFunctionName() + ")");
		}
		wordCtrler = new WordCtrler(line);
		returnValue = new Data("[", null);
		Data listItem;
		while ((listItem = generateListItem()) != null) {
			returnValue.addListItem(listItem);
		}
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
	
	private Data generateListItem() throws MuaError {
		Data data;
		String word = wordCtrler.getNextWord();
		if (word.equals("") || word.equals("]")) {
			return null; // As the end of List.
		}
		data = new Data(word, null);
		if (word.equals("[")) {
			Data listItem;
			while ((listItem = generateListItem()) != null) {
				data.addListItem(listItem);
			}
		}
		return data;
	}
	
}
