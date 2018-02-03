package mua;

import java.io.InputStream;
import java.util.Scanner;

public class WordCtrler { // Here word means the characters segmented naturally.   
	
	private class LineCtrler {
		
		private Scanner input;
		private boolean isFromStdInput;  // Whether scanner is from standard input.
		private String lineContent = ""; // Current line got from input scanner.
		private int lineNumber = 0;      // The line number of the current line.
		private String outPrompt = "> "; // The prompt begins at every line out of list and expression.
		private String inPrompt = "... ";  // The prompt begins at every line in list and expression.
		
		// Use constructor to indicate input scanner.
		// If no scanner indicated, use standard input.
		LineCtrler() {
			input = new Scanner(System.in);
			isFromStdInput = true;
		}
		
		LineCtrler(InputStream inputStream) {
			if (input == null || inputStream == System.in) {
				input = new Scanner(System.in);
				isFromStdInput = true;
			} else {
				this.input = new Scanner(inputStream);
				isFromStdInput = false;
			}
		}
		
		public String getCurLine() {
			return lineContent;
		}
		
		public int getLineNumber() {
			return lineNumber;
		}
		
		public String getOutPrompt() {
			return outPrompt;
		}
		
		public String getInPrompt() {
			return inPrompt;
		}
		
		public String setOutPrompt(String newOutPrompt) {
			if (newOutPrompt != null) {
				outPrompt = newOutPrompt;
			}
			return outPrompt;
		}
		
		public String setInPrompt(String newInPrompt) {
			if (newInPrompt != null) {
				inPrompt = newInPrompt;
			}
			return inPrompt;
		}
		
		public String getNextLine() {
			do {
				if (isFromStdInput) {
					if (leftListBracketsNum == 0 && leftExpBracketsNum == 0) {
						System.out.print(outPrompt);
					} else {
						System.out.print(inPrompt);
					}
				} // Print prompt only when scanner is from standard input.
				lineContent = normalize(input.nextLine());
				++lineNumber;
			} while (lineContent.equals("")); // Ignore lines with meaningful content.
			return lineContent;
		}
		
		private String normalize(String str) {
			if (str == null) {
				return "";
			}
			if (str.indexOf("//") >= 0) { // Remove the comments.
				str = str.substring(0, str.indexOf("//"));
			}
			str = str.trim(); // Remove white spaces at the begin and the end.
			return str;
		}
		
	}

	private LineCtrler lineCtrler;
	private String curLine;
	private String curLeftLine = ""; // The left content of the current line.
	private String curWord = "";     // The current word got from the current line.
	private int leftListBracketsNum = 0;
	private int leftExpBracketsNum = 0;
	
	// Usage 1: Get words from input stream (Need help from LineCtrler class).
	WordCtrler() {
		lineCtrler = new LineCtrler();
	}
	
	WordCtrler(InputStream inputStream) {
		lineCtrler = new LineCtrler(inputStream);
	}
	
	// Usage 2: Get words from a one-line-string. 
	public WordCtrler(String line) {
		curLeftLine = curLine = line;
	}
	
	public String getCurWord() {
		return curWord;
	}
	
	public String getCurLine() {
		if (lineCtrler != null) {
			curLine = lineCtrler.getCurLine();
		}
		return curLine;
	}
	
	public String getCurLeftLine() {
		return curLeftLine;
	}
	
	public String getOutPrompt() {
		if (lineCtrler == null) {
			return null;
		}
		return lineCtrler.getOutPrompt();
	}
	
	public String setOutPrompt(String newOutPrompt) {
		if (lineCtrler == null) {
			return null;
		}
		return lineCtrler.setOutPrompt(newOutPrompt);
	}
	
	public String getInPrompt() {
		if (lineCtrler == null) {
			return null;
		}
		return lineCtrler.getInPrompt();
	}
	
	public String setInPrompt(String newInPrompt) {
		if (lineCtrler == null) {
			return null;
		}
		return lineCtrler.setInPrompt(newInPrompt);
	}
	
	public int getLineNumber() {
		if (lineCtrler == null) {
			return 0;
		}
		return lineCtrler.getLineNumber();
	}
	
	public String getNextWord() {
		curLeftLine = curLeftLine.trim(); // Remove white spaces at the begin and the end.
		if (curLeftLine.equals("")) {
			if (lineCtrler == null) { // For usage 2.
				return "";
			}
			curLeftLine = lineCtrler.getNextLine(); // Get a new meaningful line since the current line has been finished reading. 
		}
		char firstChar = curLeftLine.charAt(0);
		if (firstChar == '[' || firstChar == ']' || firstChar == '(' || firstChar == ')')
		{ // Lists' and expressions' begin and end shall be extracted alone.
			curLeftLine = curLeftLine.substring(1);
			curLeftLine = curLeftLine.trim();
			switch (firstChar) {
			case '[':
				++leftListBracketsNum;
				return "[";
			case ']':
				--leftListBracketsNum;
				return "]";
			case '(':
				++leftExpBracketsNum;
				return "(";
			case ')':
				--leftExpBracketsNum;
				return ")";
			}
		}
		int nextSepIndex = getNextSepIndex(curLeftLine); // Find the index of the next separator.
		if (nextSepIndex == -1) { // No next separator indicates the whole curLeftLine is a single word.
			curWord = curLeftLine;
			curLeftLine = "";
			return curWord;
		}
		curWord = curLeftLine.substring(0, nextSepIndex);
		curLeftLine = curLeftLine.substring(nextSepIndex);
		curLeftLine = curLeftLine.trim();
		return curWord;
	}
	
	public void clear() {
		curLeftLine = "";
		curWord = "";
		leftListBracketsNum = leftExpBracketsNum = 0;
	}
	
	private int getNextSepIndex(String str) {
		String nextCharsStr = " \t[]()";
		for (int i = 0; i < str.length(); ++i) {
			if (nextCharsStr.indexOf(str.charAt(i)) != -1) {
				return i;
			}
		}
		return -1;
	}
	
}
