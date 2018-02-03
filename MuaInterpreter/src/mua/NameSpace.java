package mua;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import mua.error.NameError;

public class NameSpace {
	
	@SuppressWarnings("serial")
	private static final HashSet<String> reservedWord = new HashSet<String>() {{
		add("true"); add("false");
		add("make"); add("thing"); add("erase"); add("isname"); add("print"); add("read"); add("readlist");
		add("add"); add("sub"); add("mul"); add("div"); add("mod");
		add("eq"); add("gt"); add("lt"); add("and"); add("or"); add("not");
		add("repeat"); add("output"); add("export"); add("stop");
		add("random"); add("sqrt"); add("int");
		add("isnumber"); add("isword"); add("islist"); add("isbool"); add("isempty");
		add("word"); add("sentence"); add("list"); add("join");
		add("first"); add("last"); add("butfirst"); add("butlast");
		add("wait");
		add("erall"); add("poall");
		add("save"); add("load");
		add("if");
	}};
	
	private HashMap<String, Data> wordDataBindings = new HashMap<String, Data>();
	private Data returnValue;
	private String functionName;     // Not function space will be "[Main]".
	private boolean isFunctionSpace; // Whether the space is for a function.
	private NameSpace parentSpace;   // The parent space of a function is where its caller from. 
	
	public NameSpace() {
		functionName = "[Main]";
		isFunctionSpace = false;
		parentSpace = null;
	}
	
	public NameSpace(String functionName, NameSpace parentSpace) {
		if (functionName != null && !functionName.isEmpty() && parentSpace != null) {
			this.functionName = functionName;
			isFunctionSpace = true;
			this.parentSpace = parentSpace;
		} else {
			this.functionName = "[Main]";
			isFunctionSpace = false;
			this.parentSpace = null;
		}
	}
	
	public void setReturnValue(Data newReturnValue) {
		returnValue = newReturnValue;
	}
	
	public Data getReturnValue() {
		return returnValue;
	}
	
	public String getFunctionName() {
		return functionName;
	}
	
	public boolean getIsFunctionSpace() {
		return isFunctionSpace;
	}
	
	public Data addBinding(String word, Data data) throws NameError {
		if (word == null || word.isEmpty()) {
			return null;
		}
		if (reservedWord.contains(word)) {
			throw new NameError(word, "name is reserved (in " + functionName + ")");
		}
		return wordDataBindings.put(word, data);
	}
	
	public Data eraseBinding(String word) {
		return localSpaceContainsBinding(word) ? wordDataBindings.remove(word) : null;
	}
	
	public void clearBindings() {
		wordDataBindings.clear();
	}
	
	public Set<String> getBindingsKeySet() {
		return wordDataBindings.keySet();
	}
	
	// Local space means the wordDataBindings segment.
	// Whole space means the wordDataBindings segment and the recursive parentSpace.
	public Data getlocalSpaceBindingData(String word) {
		return localSpaceContainsBinding(word) ? wordDataBindings.get(word) : null;
	}
	
	public Data getWholeSpaceBindingData(String word) {
		if (word == null || word.isEmpty()) {
			return null;
		}
		if (wordDataBindings.containsKey(word)) {
			return wordDataBindings.get(word);
		}
		if (parentSpace == null) {
			return null;
		}
		return parentSpace.getWholeSpaceBindingData(word);
	}
	
	public boolean localSpaceContainsBinding(String word) {
		if (word == null || word.isEmpty()) {
			return false;
		}
		return wordDataBindings.containsKey(word);
	}
	
	public boolean wholeSpaceContainsBinding(String word) {
		if (word == null || word.isEmpty()) {
			return false;
		}
		if (wordDataBindings.containsKey(word)) {
			return true;
		}
		if (parentSpace == null) {
			return false;
		}
		return parentSpace.wholeSpaceContainsBinding(word);
	}
	
}
