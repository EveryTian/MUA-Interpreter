package mua;

import java.util.Vector;

import mua.error.*;

public class Data {
	
	public enum Type {
		UNDEFINED, NUMBER, WORD, LIST, BOOL, OP
		// UNDFINED: Used to mark an uninitialized data.
		// OP: Includes operation, function, parameter, stores in wordValue.
	}
	
	private Type type = Type.UNDEFINED;
	private double numberValue;
	private String wordValue;
	private Vector<Data> listValue;
	private boolean boolValue;
	
	public Data(String literal, Object o) throws MuaError
	{ // Object o is used for distinguishing constructor from literal and string.
		if (literal == null || literal.isEmpty()) {
			return;
		}
		char firstChar = literal.charAt(0);
		if (firstChar == '-' || ('0' <= firstChar && firstChar <= '9')) {
			asNumber(literal);
		} else if (firstChar == '"') {
			asWord(literal);
		} else if (firstChar == ':' && literal.length() == 1) {
			throw new SyntaxError(":", "colon shall not appear by itself");
		} else if (literal.equals("true") || literal.equals("false")) {
			asBool(literal);
		} else if (literal.equals("[")) {
			type = Type.LIST;
			listValue = new Vector<Data>();
		} else {
			asOp(literal);
		}
	}
	
	public Data(Data data) {
		copy(data);
	}
	
	public Data(double numberValue) {
		type = Type.NUMBER;
		this.numberValue = numberValue;
	}
	
	public Data(boolean boolValue) {
		type = Type.BOOL;
		this.boolValue = boolValue;
	}
	
	public Data(String wordValue) {
		if (wordValue == null) {
			return;
		}
		type = Type.WORD;
		this.wordValue = wordValue;
	}
	
	public Data(Vector<Data> listValue) {
		if (listValue == null) {
			return;
		}
		type = Type.LIST;
		this.listValue = listValue;
	}
	
	public Type getType() {
		return type;
	}
	
	public String getTypeString() {
		return type.name().toLowerCase();
	}
	
	public double getNumberValue() throws TypeError {
		if (type != Type.NUMBER) {
			throw new TypeError(this, "not a number");
		}
		return numberValue;
	}
	
	public String getWordValue() throws TypeError {
		if (type != Type.WORD) {
			throw new TypeError(this, "not a word");
		}
		return wordValue;
	}
	
	public String getOpValue() throws TypeError {
		if (type != Type.OP) {
			throw new TypeError(this, "not an operation");
		}
		return wordValue;
	}
	
	public Vector<Data> getListValue() throws TypeError {
		if (type != Type.LIST) {
			throw new TypeError(this, "not a list");
		}
		return listValue;
	}
	
	public boolean getBoolValue() throws TypeError {
		if (type != Type.BOOL) {
			throw new TypeError(this, "not a bool");
		}
		return boolValue;
	}
	
	public void copy(Data data) {
		if (data == null) {
			return;
		}
		type = data.type;
		numberValue = data.numberValue;
		wordValue = data.wordValue;
		listValue = data.listValue;
		boolValue = data.boolValue;
	}
	
	public boolean addListItem(Data data) throws MuaError {
		if (type != Type.LIST || listValue == null) {
			throw new TypeError(this, "not a list");
		}
		if (data == null) {
			return false;
		}
		return listValue.add(data); // TODO WHETHER NEW NEEDED CHANGE
	}
	
	public boolean addListItem(String literal) throws MuaError {
		if (type != Type.LIST || listValue == null) {
			throw new TypeError(this, "not a list");
		}
		if (literal == null || literal.isEmpty()) {
			return false;
		}
		return listValue.add(new Data(literal, null));
	}
	
	@Override
	public String toString() {
		switch (type) {
		case BOOL:
			return boolValue ? "true" : "false";
		case NUMBER:
			return Double.toString(numberValue);
		case OP:
			return wordValue;
		case WORD:
			return "\"" + wordValue;
		case LIST:
			return listValueToString().toString();
		default:
			return "undefined";
		}
	}
	
	private StringBuffer listValueToString() {
		StringBuffer listStringBuffer = new StringBuffer("[ "); 
		for (Data listItem: listValue) {
			listStringBuffer.append(listItem.toString());
			listStringBuffer.append(' ');
		}
		listStringBuffer.append(']');
		return listStringBuffer;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || o.getClass() != getClass()) {
			return false;
		}
		Data dataObject = (Data)o;
		if (type != dataObject.type) {
			return false;
		}
		switch (type) {
		case BOOL:
			return boolValue == dataObject.boolValue;
 		case NUMBER:
 			return numberValue == dataObject.numberValue;
		case OP:
		case WORD:
			return wordValue.equals(dataObject.wordValue);
		case LIST:
			return listValue.equals(dataObject.listValue);
		default:
			return false;
		}
	}
	
	// Functions asTypes are used for initializing the data to the corresponding type.
	private double asNumber(String literal) throws SyntaxError {
		type = Type.NUMBER;
		try {
			numberValue = Double.parseDouble(literal);
		} catch (Exception e) {
			throw new SyntaxError(literal, "illegal number literal");
		}
		return numberValue;
	}
	
	private String asWord(String literal) throws SyntaxError {
		type = Type.WORD;
		String tempWordValue = literal.substring(1);
		if (!isLegalWord(tempWordValue)) {
			throw new SyntaxError(literal, "illegal word literal");
		}
		wordValue = tempWordValue;
		return wordValue;
	}
	
	private String asOp(String literal) throws SyntaxError {
		type = Type.OP;
		if (!isLegalOp(literal)) {
			throw new SyntaxError(literal, "illegal operation name");
		}
		wordValue = literal;
		return wordValue;
	}
	
	private boolean asBool(String literal) {
		type = Type.BOOL;
		boolValue = literal.equals("true");
		return boolValue;
	}
	
	private boolean isLegalWord(String word) {
		return !containsChars(word, ":\"[]()");
	} // Word type data shall not contain ':', '"', '[', ']', '(' and ')'.
	
	private boolean isLegalOp(String word) {
		if (word == null || word.isEmpty()) {
			return false;
		}
		char fstCh = word.charAt(0);
		if (!(fstCh == '_' || ('a' <= fstCh && fstCh <= 'z') || ('A' <= fstCh && fstCh <= 'Z'))) {
			return false;
		} // A legal OP type data literal shall begin with '_' or letters.
		String legalChars = "abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_'";
		for (int i = 1; i < word.length(); ++i) {
			if (legalChars.indexOf(word.charAt(i)) == -1) {
				return false;
			}
		}
		return true;
	}
	
	private boolean containsChars(String str, char[] chars) {
		if (str == null || str.length() == 0) {
			return false;
		}
		if (chars == null || chars.length == 0) {
			return true;
		}
		for (char c : chars) {
			if (str.indexOf(c) != -1) {
				return true;
			}
		}
		return false;
	}
	
	private boolean containsChars(String str, String charsStr) {
		if (charsStr == null) {
			return true;
		}
		return containsChars(str, charsStr.toCharArray());
	}
	
}
