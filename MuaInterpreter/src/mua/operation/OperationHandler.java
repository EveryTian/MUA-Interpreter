package mua.operation;

import java.util.HashMap;

import mua.Data;
import mua.NameSpace;
import mua.error.*;
import mua.operation.operator.*;

public class OperationHandler {
	
	@SuppressWarnings("serial")
	private static final HashMap<String, Integer> opArgsNumMap = new HashMap<String, Integer>() {{
		put("make", 2); put("thing", 1); put("erase", 1); put("isname", 1);
		put("print", 1); put("read", 0); put("readlist", 0);
		put("add", 2); put("sub", 2); put("mul", 2); put("div", 2); put("mod", 2);
		put("eq", 2); put("gt", 2); put("lt", 2);
		put("and", 2); put("or", 2); put("not", 1);
		put("repeat", 2); put("output", 1); put("export", 1); put("stop", 0);
		put("random", 1); put("sqrt", 1); put("int", 1);
		put("isnumber", 1); put("isword", 1); put("islist", 1); put("isbool", 1); put("isempty", 1);
		put("word", 2); put("sentence", 2); put("list", 2); put("join", 2);
		put("first", 1); put("last", 1); put("butfirst", 1); put("butlast", 1);
		put("wait", 1);
		put("erall", 0); put("poall", 0);
		put("save", 1); put("load", 1);
		put("if", 3);
	}};
	
	public static int getOpArgsNum(String opName) {
		if (!opArgsNumMap.containsKey(opName)) {
			return -1; // -1 as not an operation.
		}
		return opArgsNumMap.get(opName);
	}
	
	public static boolean isOperation(String str) {
		return opArgsNumMap.containsKey(str);
	}
	
	private String opName;
	private int opArgsNum;
	private Operation op;
	private final int maxArgumentsNum = 3; // An operation has 3 arguments at most.
	private Data[] args = new Data[maxArgumentsNum];
	private int curArgsNum = 0;
	private NameSpace space;
	
	public OperationHandler(String opName, NameSpace space) {
		this.opName = opName;
		opArgsNum = OperationHandler.getOpArgsNum(opName);
		this.space = space;
	}
	
	public int addArg(Data arg) {
		if (curArgsNum >= maxArgumentsNum || curArgsNum < 0) { // An operation has 3 arguments at most.
			curArgsNum = -1;
			return -1; // -1 as too much arguments.
		}
		args[curArgsNum++] = arg;
		return curArgsNum;
	}
	
	public boolean execute() throws MuaError {
		if (opArgsNum != curArgsNum || curArgsNum == -1) {
			return false;
		}
		switch (opName) { // Create corresponding operation object.
		case "make":   op = new MakeOp(args[0], args[1]); break;
		case "thing":  op = new ThingOp(args[0]);  break;
		case "erase":  op = new EraseOp(args[0]);  break;
		case "isname": op = new IsnameOp(args[0]); break;
		case "print":  op = new PrintOp(args[0]);  break;
		case "read":   op = new ReadOp();     break;
		case "readlist": op = new ReadlistOp(); break;
		case "add": op = new AddOp(args[0], args[1]); break;
		case "sub": op = new SubOp(args[0], args[1]); break;
		case "mul": op = new MulOp(args[0], args[1]); break;
		case "div": op = new DivOp(args[0], args[1]); break;
		case "mod": op = new ModOp(args[0], args[1]); break;
		case "eq":  op = new EqOp(args[0], args[1]);  break;
		case "gt":  op = new GtOp(args[0], args[1]);  break;
		case "lt":  op = new LtOp(args[0], args[1]);  break;
		case "and": op = new AndOp(args[0], args[1]); break;
		case "or":  op = new OrOp(args[0], args[1]);  break;
		case "not": op = new NotOp(args[0]); break;
		case "repeat": op = new RepeatOp(args[0], args[1]); break;
		case "output": op = new OutputOp(args[0]); break;
		case "export": op = new ExportOp(args[0]); break;
		case "random": op = new RandomOp(args[0]); break;
		case "sqrt": op = new SqrtOp(args[0]); break;
		case "int": op = new IntOp(args[0]); break;
		case "isnumber": op = new IsnumberOp(args[0]); break;
		case "isword": op = new IswordOp(args[0]); break;
		case "islist": op = new IslistOp(args[0]); break;
		case "isbool": op = new IsboolOp(args[0]); break;
		case "isempty": op = new IsemptyOp(args[0]); break;
		case "word": op = new WordOp(args[0], args[1]); break;
		case "sentence": op = new SentenceOp(args[0], args[1]); break;
		case "list": op = new ListOp(args[0], args[1]); break;
		case "join": op = new JoinOp(args[0], args[1]); break;
		case "first": op = new FirstOp(args[0]); break;
		case "last":  op = new LastOp(args[0]);  break;
		case "butfirst": op = new ButfirstOp(args[0]); break;
		case "butlast":  op = new ButlastOp(args[0]);  break;
		case "wait": op = new WaitOp(args[0]); break;
		case "erall": op = new ErallOp(); break;
		case "poall": op = new PoallOp(); break;
		case "save": op = new SaveOp(args[0]); break;
		case "load": op = new LoadOp(args[0]); break;
		case "if": op = new IfOp(args[0], args[1], args[2]); break;
		default: return false;
		}
		op.execute(space);
		return true;
	}
	
	public boolean hasReturnValue() {
		return op != null ? op.getHasReturnValue() : false;
	}
	
	public Data getReturnValue() {
		return hasReturnValue() ? op.getReturnValue() : null;
	}
	
	public boolean hasAppendedData() {
		return op != null ? op.getHasAppendedData() : false;
	}
	
	public Data getAppendedData() {
		return hasAppendedData() ? op.getAppendedData() : null;
	}
	
	public int getArgsNum() {
		return opArgsNum;
	}
	
}
