package mua;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.Vector;

import mua.error.MuaError;
import mua.error.SyntaxError;

public class DataGenerator { // Works with a WordCtrler class, used for generating data from input.
	
	private static class ExpressionProcessor { // Expression process on string level.
		
		@SuppressWarnings("serial")
		private static final HashMap<String, Integer> infixOpPriority = new HashMap<String, Integer>() {{
			put("*", 7); put("/", 7); put("%", 7); put("+", 6); put("-", 6);
			put("&&", 3); put("||", 2);
			put(">", 4); put("<", 4); put("==", 4);
			put("++", 5);
		}}; // Defines the priority of the infix operation, the larger values means higher priority.
		
		@SuppressWarnings("serial")
		private static final HashMap<String, String> infixPrefixOpPair = new HashMap<String, String>() {{
			put("*", "mul");
			put("/", "div");
			put("%", "mod");
			put("+", "add");
			put("-", "sub");
			put("&&", "and");
			put("||", "or");
			put(">", "gt");
			put("<", "lt");
			put("==", "eq");
			put("++", "sentence");
		}}; // Infix operation will be transformed to the corresponding prefix operation. 
		
		public static int getInfixOpPriority(String infixOp) {
			return infixOpPriority.get(infixOp);
		}
		
		public static String getPrefixOpName(String infixOp) {
			return infixPrefixOpPair.get(infixOp);
		}
		
		public static boolean isInfixOp(String name) {
			return infixPrefixOpPair.containsKey(name);
		}
		
		private Vector<String> processResult; // Result with prefix style in string. 
		// Here word block means a number of words separated by infix separators.
		private Stack<Vector<String>> wordBlockStack = new Stack<Vector<String>>();
		private Stack<String> infixOpStack = new Stack<String>();
		private boolean nextNewWordBlock = true; // Whether the next word word belongs to a new word block.
		private ExpressionProcessor insideProcessor; // Use to handle the inside expression recursively.
		private int depth; // Begin from 1, which presents the number of brackets.
		
		ExpressionProcessor(int depth) {
			this.depth = depth;
		}
		
		Vector<String> getProcessResult() {
			return processResult;
		}
		
		void push(String word, int wordDepth) throws SyntaxError {
			if (insideProcessor != null) { // Recursion implementation.
				insideProcessor.push(word, wordDepth); // Processes and generates word with prefix operations recursively.
				if (wordDepth == depth && word.equals(")")) { // End of the current inside expression process.
					if (nextNewWordBlock) {
						wordBlockStack.push(insideProcessor.getProcessResult());
					} else {
						wordBlockStack.peek().addAll(insideProcessor.getProcessResult());
					} // The result of the insideProcessor is a word block.
					insideProcessor = null;
				}
				return;
			}
			switch (word) {
			case "(": // "(" create a new inside expression.
				insideProcessor = new ExpressionProcessor(depth + 1);
				break;
			case ")": // ")" end a expression.
				generateResult(); // Generate result here and handle result in higher ExpressionProcessor.
				break;
			default:
				if (isInfixOp(word)) {
					nextNewWordBlock = true; // Meeting infix operator means next word belongs to a new word block.
					infixOpStack.push(word); // Push infix operator into infixOpStack.
				} else {
					if (nextNewWordBlock) {
						wordBlockStack.push(new Vector<String>());
					}
					wordBlockStack.peek().add(word);
					nextNewWordBlock = false;
				}
				break;
			}
		}

		private void generateResult() throws SyntaxError {
			if (wordBlockStack.size() != infixOpStack.size() + 1) { // Shall be n + 1 arguments for n infix operators. 
				StringBuffer errorDataMessageBuffer = new StringBuffer(); // For building error message.
				if (!wordBlockStack.isEmpty()) {
					Vector<String> lastElementOfWordBlockVector = wordBlockStack.peek();
					if (!lastElementOfWordBlockVector.isEmpty()) {
						errorDataMessageBuffer.append("(Data Block:");
						for (String blockWord : lastElementOfWordBlockVector) {
							errorDataMessageBuffer.append(' ').append(blockWord);
						}
						errorDataMessageBuffer.append(')');
					}
				}
				if (!infixOpStack.isEmpty()) {
					errorDataMessageBuffer.append("(Infix Operator: ").append(infixOpStack.peek()).append(')');
				}
				if (errorDataMessageBuffer.length() == 0) {
					throw new SyntaxError("()", "empty expression");
				} else {
					throw new SyntaxError(errorDataMessageBuffer.toString(), "infix opeartor lacks argument");
				}
			}
			Vector<Vector<String>> tempResult = new Vector<Vector<String>>();
			Stack<String> tempOpStack = new Stack<String>();
			// The following part implements infix to prefix:
			// First get reversed prefix order:
			tempResult.add(wordBlockStack.pop());
			while (!infixOpStack.isEmpty()) {
				while (!tempOpStack.isEmpty() && getInfixOpPriority(tempOpStack.peek()) > getInfixOpPriority(infixOpStack.peek())) {
					tempResult.add(new Vector<String>());
					tempResult.lastElement().add(getPrefixOpName(tempOpStack.pop()));
				} // Pop from tempOpStack and push into tempResult while operator on the top of tempOpStack has higher priority.
				tempOpStack.push(infixOpStack.pop());
				tempResult.add(wordBlockStack.pop());
			}
			while (!tempOpStack.isEmpty()) {
				tempResult.add(new Vector<String>());
				tempResult.lastElement().add(getPrefixOpName(tempOpStack.pop()));
			} // Then push and pop all operator.
			Collections.reverse(tempResult); // Then reverse to get prefix order.
			processResult = new Vector<String>();
			for (Vector<String> wordBlock : tempResult) {
				for (String word : wordBlock) {
					processResult.add(word);
				}
			} // Flatten the tempResult to processResult.
		}
		
		void clear() { 
			wordBlockStack.clear();
			infixOpStack.clear();
			nextNewWordBlock = true;
			processResult = null;
			insideProcessor = null; 
		}
	}
	
	private WordCtrler wordCtrler;
	private String curWord; // The current word being processed.
	private boolean metColon = false; // For handling colon operator.
	private ExpressionProcessor expressionProcessor = new ExpressionProcessor(1); // The number of brackets begins from 1.
	private int leftBracketsCount = 0;
	private Queue<String> wordQueue = new LinkedList<String>(); // The place where word caches.
	
	DataGenerator(WordCtrler wordCtrler) {
		this.wordCtrler = wordCtrler;
	}
	
	Data getNextData() throws MuaError {
		while (wordQueue.isEmpty()) {
			generateWordQueueOnce();
		} // Use while because function generateWordQueueOnce may not push words into queue.
		String word = wordQueue.poll();;
		if (word.equals("]")) {
			return null; // As the end of List.
		}
		Data data = new Data(word, null);
		if (data.getType() == Data.Type.UNDEFINED) {
			return null;
		}
		if (word.equals("[")) { // Create a list recursively.
			Data listItem;
			while ((listItem = getNextData()) != null) {
				data.addListItem(listItem);
			}
		}
		return data;
	}
	
	private void generateWordQueueOnce() throws SyntaxError {
		String word = getNextWordWithColonPreprocess();
		if (word.equals("(")) {
			if (++leftBracketsCount == 1) {
				return; // The first bracket will be handled in this function and shall not be pushed.
			}
		} else if (word.equals(")")) {
			if (--leftBracketsCount == 0) { // Expression end.
				expressionProcessor.generateResult();
				if (expressionProcessor.getProcessResult() != null) {
					for (String wordInExpression : expressionProcessor.getProcessResult()) {
						wordQueue.add(wordInExpression);
					}
				} // Generate the result and push it into wordQueue. 
				initExpressionProcessor();
				return;
			}
			if (leftBracketsCount < 0) {
				throw new SyntaxError(") " + wordCtrler.getCurLeftLine(), "unmatched expression bracket");
			}
		}
		if (leftBracketsCount > 0) {
			expressionProcessor.push(word, leftBracketsCount);
		} else {
			wordQueue.add(word);
		}
	}
	
	private String getNextWordWithColonPreprocess() { // make :word thing "word.
		if (metColon) {
			metColon = false;
			return "\"" + curWord.substring(1); // Then return word with colon replaced with double quote.
		}
		curWord = wordCtrler.getNextWord();
		if (!curWord.isEmpty() && curWord.charAt(0) == ':' && curWord.length() > 1) {
			metColon = true;
			return "thing"; // First return "thing".
		}
		return curWord; // Normal condition without colon.
	}
	
	private void initExpressionProcessor() {
		expressionProcessor.clear();
		leftBracketsCount = 0;
	}
	
	public void clear() {
		metColon = false;
		initExpressionProcessor();
		wordQueue.clear();
	}
	
}