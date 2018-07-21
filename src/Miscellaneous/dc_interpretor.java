
/* Commands Implemented So Far (Can currently recognize all arbitrary precision real base-10 numbers) */

/// Printing commands:
///// p, n, f

/// Arithmetic commands:
///// +, -, *, /, %, ~, v

/// Stack Control (Done!):
///// c, r, d

/// Register commands (Done!):
///// lr, sr, Lr, Sr

/// Parameters:
///// k, K

/// String Commands (Done!):
///// \\[+.*\\]+, x, a, ALL comparison commands, ?, q, Q

/// Status Inquiry Commands:
///// Z, z

import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.lang.*;

public class dc_interpretor {
    
    int Input_Counter;
    int PREC = 0;
    int QUIT = 0;
    ArrayList<String> Main_Stack = new ArrayList<String>();
    ArrayList<ArrayList<String>> Registers = new ArrayList<ArrayList<String>>();
    String Program_Pattern = "\\?|x|v|p|a|q|Q|n|c|k|K|z|Z|r|~|_?\\d+\\.?\\d*|_|\\+|\\*|\\-|/|%|s\\w|l\\w|L\\w|S\\w|(?:>|<|!>|!<|=|!=)\\w|d|f| ";
    Pattern Regex = Pattern.compile(Program_Pattern);

    String[] Equalize(String A, String B) {
	int G = A.replaceFirst("\\.\\d*","").length();
	int H = B.replaceFirst("\\.\\d*","").length();
	while (G != H) {
	    if (G < H) {
		A = '0' + A;
		G = A.replaceFirst("\\.\\d*","").length();
	    }
	    else {
		B = '0' + B;
		H = B.replaceFirst("\\.\\d*","").length();
	    }
	}
	G = A.replaceFirst("\\d+","").length();
	H = B.replaceFirst("\\d+","").length();
	A = (G < 1 && H > 0) ? A + '.' : A;
	B = (H < 1 && G > 0) ? B + '.' : B;
	while (G != H) {
	    if (G < H) {
		A += '0';
		G = A.replaceFirst("\\d+","").length();
	    }
	    else {
		B += '0';
		H = B.replaceFirst("\\d+","").length();
	    }
	}
	/* System.out.println("A = " + A); 
	   System.out.println("B = " + B); */
	return new String[] {A, B};
    }

    String Add(String A, String B) {
	String[] Equalized = Equalize(A, B);
	A = Equalized[0];
	B = Equalized[1];
	String Sum = "";
	int Carry = 0;
	for(int a = A.length() - 1;a > -1;a--) {
	    if (A.charAt(a) == '.') {
		Sum = '.' + Sum;
	    }
	    else {
		int Digit = A.charAt(a) + B.charAt(a) - 96 + Carry;
		Carry = (Digit > 9) ? 1:0;
		Digit = (a < 1) ? Digit : Digit % 10;
		Sum = (a > 0) ? (char)('0' + Digit) + Sum : Integer.toString(Digit) + Sum;
	    }
	}
	Sum = Sum.replaceFirst("0*","");
	return Sum;
    }

    String Multiply (String A, String B) {
	String[] Equalized = Equalize(A, B);
	A = Equalized[0];
	B = Equalized[1];
	int Point_Index = (A.indexOf('.') + B.indexOf('.') > -2) ? (A.length() - 1) * 2 - A.indexOf('.') - B.indexOf('.') : 0;
	A = A.replaceFirst("\\.","");
	B = B.replaceFirst("\\.","");
	ArrayList<String> Sums = new ArrayList<String>();
	int Carry = 0;
	for(int a = A.length() - 1;a > -1;a--) {
	    String Sum = "";
	    for(int y = 0;y < A.length() - 1 - a;y++) {
		Sum += '0';
	    }
	    for(int b = B.length() - 1;b > -1;b--) {	
		int Digit = (A.charAt(a) - '0') * (B.charAt(b) - '0') + Carry;
		Carry = (b > 0) ? Digit / 10 : 0;
		Digit = (b < 1) ? Digit : Digit % 10;
		Sum = (b > 0) ? (char)('0' + Digit) + Sum: Integer.toString(Digit) + Sum;
	    }
	    Sums.add(Sum);
	}
	String Product = "";
        for (int c = 0;c < Sums.size();c++){
	    Product = Add(Product,Sums.get(c));
	}
	Product = (Point_Index > 0) ? Product.substring(0, Product.length() - Point_Index) + '.' + Product.substring(Product.length() - Point_Index, Product.length()) : Product;
	return Product;
    }

    String Subtract(String A, String B) {
	String[] Equalized = Equalize(A, B);
	A = Equalized[0];
	B = Equalized[1];
	// System.out.println(A + " and " + B);
	String Max = A;
	for(int u = 0;u < A.length(); u++) {
	    if (A.charAt(u) > B.charAt(u)) {
		break;
	    }
	    else if (B.charAt(u) > A.charAt(u)) {
		Max = B;
		break;
	    }
	}
	// System.out.println(C);
	String C = Max.charAt(0) == '9' ? "0" + Max : Max;
	// System.out.println(C);
	String Nines_Complement = "";
	for(int h = 0;h < C.length();h++) {
	    char M = C.charAt(h);
	    Nines_Complement += (M < 47) ? "." : 9 - M + '0';
	}
	// System.out.println("Nines_Complement = " + Nines_Complement);
	String Summed = Add(Nines_Complement, Max.equals(A) ? B : A);
	// System.out.println("Summed = " + Summed);
	String Difference = "";
	for(int g = 0;g < Summed.length();g++) {
	    char N = Summed.charAt(g);
	    Difference += (N < 47) ? "." : 9 - N + '0';
	}
	Difference = (C.equals(B) && !B.equals(A)) ? '_' + Difference : Difference;
	return Difference.length() > 1 ? Difference.replaceFirst("0*","") : Difference;
    }

    void Remove(int n) {
	int T = Main_Stack.size();
	for(int i = T - 1;i > T - n - 1;i--) {
	    Main_Stack.remove(i);
	}
    }

    boolean Comparator(String A, String B, String C) {
	boolean T = (A.charAt(0) > 94) ? true : false;
	boolean R = (B.charAt(0) > 94) ? true : false;
	boolean Q = ((T == true && R == false && (C.equals(">") || C.equals("!<"))) || (T == false && R == true && (C.equals("<") || C.equals("!>")))) ? true : false;
	String I = A;
	A = (T == R && T == true) ? B.replaceFirst("_","") : A.replaceFirst("_","");
	B = (T == R && T == true) ? I.replaceFirst("_","") : B.replaceFirst("_","");
	String[] Equalized = Equalize(A, B);
	A = Equalized[0];
	B = Equalized[1];
	int Equality = 0;
	for (int o = 0;o < A.length() && T==R;o++) {
	    if (B.charAt(o) < A.charAt(o)) {
		Q = (C.equals("<") || C.equals("!>")) ? true : Q;
		break;
	    }
	    else if (B.charAt(o) > A.charAt(o)) {
		Q = (C.equals(">") || C.equals("!<")) ? true: Q;
		break;
	    }
	    Equality += 1;
	}
	Q = (Equality == A.length() && (C.equals("!<") || C.equals("!>") || C.equals("="))) ? true : Q;
	Q = (Equality < A.length() && C.equals("!=")) ? true : Q;
	return Q;
    }

    String Bracketize(String A) {
	String H = "";
	for(int u = 0;u < A.length();u++) {
	   int Open = 0;
	   int Closed = 0;
	   H += A.charAt(u);
	   for(int p = 0;p < H.length();p++) {
	       Open += (H.charAt(p) == '[') ? 1 : 0;
	       Closed += (H.charAt(p) ==']') ? 1: 0;
	   }
	   if (Open == Closed) {
	       break;
	   }
	}
	return H;
    }

    String Divide(String A, String B, boolean Modulus) {
	String O = A;
	String L = B;
	int Z = A.replaceFirst("\\d+\\.?","").length();
	int Y = B.replaceFirst("\\d+\\.?","").length();
        A = A.replaceFirst("\\.","");
	B = B.replaceFirst("\\.","");
	for (int v = 0;v < ((Z > Y) ? Z - Y : Y - Z);v++) {
	    if (Z > Y) {
		B += "0";
	    }
	    else {
		A += "0";
	    }
	}
	//System.out.println("A = " + A + " and B = " + B + " with Z = " + Z + " and Y = " + Y);
	String Q = "";
	String G = "";
	for(int y = 0;y < O.length() + PREC;y++) {
	    String I = G + ((A.length() > 0) ? A.substring(0,1) : "0");
	    if (A.length() < 1 && Q.indexOf(".") < 0) {
		Q += ".";
	    }
	    //System.out.println("I = " + I);
	    A = (A.length() > 0)? A.substring(1) : "";
	    //System.out.println("A = " + A);
	    int P = 0;
	    //System.out.println("I = " + I);
	    while (I.length() > 0 && Comparator(I,B,"!>")) {
	    	I = Subtract(I,B);
		//System.out.println("In Loop: I = " + I);
	    	P++;
	    }
	    if (Modulus == true && y == O.length() - 1) {
		return I;
	    }
	    //System.out.println("I2 = " + I);
	    G = (I.equals("0")) ? "" : I;
	    Q += Integer.toString(P);
	    //System.out.println("Q = " + Q + " with P = " + P + " and G = " + G);
	}
	//System.out.println("Q = " + Q);
	Q = Q.length() > 1 ? Q.replaceFirst("0+","").replaceAll("0+$","").replaceAll("\\.$","") : Q;
	return Q;
    }

    String Square_Root(String X) {
	// Using Newton's Method
	String Square = X;
	String Y = "";
	while (!X.equals(Y)) {
	    Y = X;
	    // System.out.println("Here: " + Divide("0"+Subtract(Multiply(X,X),Square),"0"+Multiply(X,"2"),false));
	    X = Subtract(X,Divide("0"+Subtract(Multiply(X,X),Square),"0"+Multiply(X,"2"),false));
	    System.out.println("X = " + X);
	}
	return X;
    }

	
    int interpret(String program) {
	//System.out.println("Program is: " + program);
	program = program.replaceAll("(?<!\\[)_(?=[^0-9])(?!=\\])","0");
	for(int r = 0;r < 256;r++) {
	    Registers.add(new ArrayList<String>(1));
	}
	while (program.length() > 0) {
	    int Second_Index = Main_Stack.size() - 2;
	    int First_Index = Main_Stack.size() - 1;
	    String First_Element = "";
	    String Second_Element = "";
	    Matcher Match = Regex.matcher(program);
	    Match.find();
	    String S = "";
	    int O = 0;
	    if (program.charAt(0) == '[') {
		S = Bracketize(program);
		Main_Stack.add(S);
		O = 1;
		//System.out.println("Matching Macro = " + S + " with program = " + program);
	    }
	    else {
		S = Match.group();
	    }
	    //System.out.println("Match is: " + S);
	    if (O < 1) {
		switch (S) {
		case " ":
		    break;
		case "+":
		    First_Element = Main_Stack.get(Second_Index).toString();
		    Second_Element = Main_Stack.get(First_Index).toString();
		    if (!First_Element.matches("\\[.*\\]") && !Second_Element.matches("\\[.*\\]")) {
			String Y = "";
			if (First_Element.charAt(0) > '^' && Second_Element.charAt(0) < '_' ) {
			    Y = Subtract(Second_Element,First_Element.replaceFirst("_",""));
			}
			else if (First_Element.charAt(0) < '_' && Second_Element.charAt(0) > '^') {
			    Y = Subtract(First_Element,Second_Element.replaceFirst("_",""));
			}
			else if (First_Element.charAt(0) + Second_Element.charAt(0) > 189) {
			    Y = "_" + Add(First_Element.replaceFirst("_",""),Second_Element.replaceFirst("_",""));
			}
			else {
			    Y = Add(First_Element, Second_Element);
			}
			Remove(2);
			Main_Stack.add(Y);
		    }
		    else {
			System.out.println("Non-Numeric Value");
		    }
		    break;
		case "*":
		    First_Element = Main_Stack.get(Second_Index).toString();
		    Second_Element = Main_Stack.get(First_Index).toString();
		    if (!First_Element.matches("\\[.*\\]") && !Second_Element.matches("\\[.*\\]")) {
			int Sum = First_Element.charAt(0) + Second_Element.charAt(0);
			String Z = ((142 < Sum && Sum < 190) ? "_" : "") + Multiply(First_Element.replaceFirst("_",""),Second_Element.replaceFirst("_",""));
			Remove(2);
			Main_Stack.add(Z);
		    }
		    else {
			System.out.println("Non-Numeric Value");
		    }
		    break;
		case "/":
		    First_Element = Main_Stack.get(Second_Index).toString();
		    Second_Element = Main_Stack.get(First_Index).toString();
		    if (!First_Element.matches("\\[.*\\]") && !Second_Element.matches("\\[.*\\]")) {
			int Sm = First_Element.charAt(0) + Second_Element.charAt(0);
			if (Second_Element.equals("0")) {
			    int VV=1/0;
			}
			else {
			    String Entry = ((142 < Sm && Sm < 190) ? "_" : "") + Divide(First_Element.replaceFirst("_",""),Second_Element.replaceFirst("_",""),false);
			    Remove(2);
			    Main_Stack.add(Entry);
			}
		    }
		    else {
			System.out.println("Non-Numeric Value");
		    }
		    break;
		case "%":
		    First_Element = Main_Stack.get(Second_Index).toString();
		    Second_Element = Main_Stack.get(First_Index).toString();
		    if (!First_Element.matches("\\[.*\\]") && !Second_Element.matches("\\[.*\\]")) {
			if (Second_Element.equals("0")) {
			    int VVV = 1/0;
			}
			else {
			    String N_Entry = ((First_Element.charAt(0) == '_') ? "_" : "") + Divide(First_Element.replaceFirst("_",""),Second_Element.replaceFirst("_",""),true);
			    Remove(2);
			    Main_Stack.add(N_Entry);
			}
		    }
		    else {
			System.out.println("Non-Numeric Value");
		    }
		    break;
		case "~":
		    First_Element = Main_Stack.get(Second_Index).toString();
		    Second_Element = Main_Stack.get(First_Index).toString();
		    if (!First_Element.matches("\\[.*\\]") && !Second_Element.matches("\\[.*\\]")) {
			Remove(2);
			interpret(First_Element + " " + Second_Element + "/");
			String Element = Main_Stack.get(Second_Index).toString().replaceFirst("\\.\\d*","");
			Remove(1);
			Main_Stack.add((!Element.equals("_") && Element.length() > 0) ? Element : "0");
			interpret(First_Element + " " + Second_Element + "%");
		    }
		    else {
			System.out.println("Non-Numeric Value");
		    }
		    break;
		case "v":
		    First_Element = Main_Stack.get(First_Index).toString();
		    Remove(1);
		    Main_Stack.add(Square_Root(First_Element));
		    break;
		case "r":
		    First_Element = Main_Stack.get(Second_Index).toString();
		    Second_Element = Main_Stack.get(First_Index).toString();
		    Remove(2);
		    Main_Stack.add(Second_Element);
		    Main_Stack.add(First_Element);
		    break;
		case "Z":
		    Main_Stack.add(Integer.toString(Main_Stack.get(First_Index).toString().replaceAll("^\\[|\\]$","").length()));
		    break;
		case "k":
		    PREC = Integer.decode(Main_Stack.get(First_Index));
		    Remove(1);
		    break;
		case "K":
		    Main_Stack.add(Integer.toString(PREC));
		    break;
		case "a":
		    First_Element = Main_Stack.get(First_Index).toString();
		    if (!First_Element.matches("\\[.*\\]")) {
			Remove(1);
			Main_Stack.add("[" + String.format("%c",Integer.parseInt(Divide(First_Element,"256",true))) + "]");
		    }
		    else {
			Remove(1);
			Main_Stack.add("[" + First_Element.substring(1,2) + "]");
		    }
		    break;
		case "q":
		    QUIT = 1;
		    return 0;
		case "Q":
		    QUIT = Integer.parseInt(Main_Stack.get(First_Index).toString()) - 1;
		    Remove(1);
		    return 0;
		case "c":
		    Main_Stack.clear();
		    break;
		case "z":
		    Main_Stack.add(Integer.toString(Main_Stack.size()));
		    break;
		case "p":
		    System.out.println(Main_Stack.get(First_Index).replaceFirst("(?<!\\[)_(?!=\\])","-").replaceAll("^\\[|\\]$",""));
		    break;
		case "-":
		    Second_Element = Main_Stack.get(Second_Index).toString();
		    First_Element = Main_Stack.get(First_Index).toString();
		    if (!First_Element.matches("\\[.*\\]") && !Second_Element.matches("\\[.*\\]")) {
			String X = "";
			if (First_Element.charAt(0) > '^' && Second_Element.charAt(0) < '_' ) {
			    X = Add(Second_Element,First_Element.replaceFirst("_",""));
			}
			else if (First_Element.charAt(0) < '_' && Second_Element.charAt(0) > '^') {
			    X = "_" + Add(Second_Element.replaceFirst("_",""),First_Element);
			}
			else if (First_Element.charAt(0) + Second_Element.charAt(0) > 189) {
			    X = Subtract(Second_Element.replaceFirst("_",""),First_Element.replaceFirst("_",""));
			}
			else {
			    X = Subtract(Second_Element, First_Element);
			}
			Remove(2);
			Main_Stack.add(X);
		    }
		    else {
			System.out.println("Non-Numeric Value");
		    }
		    break;
		case "x":
		    String Macro = Main_Stack.get(First_Index).toString().replaceAll("^\\[|\\]$","");
		    Remove(1);
		    interpret(Macro);
		    break;
		case "d":
		    Main_Stack.add(Main_Stack.get(Main_Stack.size() - 1));
		    break;
		case "f":
		    for (String g : Main_Stack) {
			System.out.print(g.replaceFirst("(?<!\\[)_(?!=\\])","-") + " ");
		    }
		    System.out.println();
		    break;
		case "n":
		    System.out.printf("%s",Main_Stack.get(First_Index).replaceFirst("(?<!\\[)_(?!=\\])","-").replaceAll("^\\[|\\]$",""));
		    Remove(1);
		    break;
		case "?":
		    Scanner Ask = new Scanner(System.in);
		    String Input_Program = Ask.nextLine();
		    interpret(Input_Program);
		    break;
		default:
		    if (S.matches("s\\w")) {
			int Size = Registers.get(S.charAt(S.length() - 1)).size();
			if (Size < 1) {
			    Registers.get(S.charAt(S.length() - 1)).add(Main_Stack.get(First_Index).toString());
			}
			else {
			    Registers.get(S.charAt(S.length() - 1)).set(Size - 1,Main_Stack.get(First_Index).toString());
			}
			Remove(1);
			break;
		    }
		    else if (S.matches("l\\w")) {
			ArrayList<String> Get_Register = Registers.get(S.charAt(S.length() - 1));
			String U = Get_Register.get(Get_Register.size() - 1);
			Main_Stack.add(U);
			break;
		    }
		    else if (S.matches("(?:>|<|!>|!<|=|!=)\\w")) {
			First_Element = Main_Stack.get(Second_Index).toString();
			Second_Element = Main_Stack.get(First_Index).toString();
			if (!First_Element.matches("\\[.*\\]") && !Second_Element.matches("\\[.*\\]")) {
			    boolean I = Comparator(Main_Stack.get(Second_Index).toString(), Main_Stack.get(First_Index).toString(), S.substring(0, S.length() - 1));
			    Remove(2);
			    if (I == true) {
				ArrayList<String> Register_Execute = Registers.get(S.charAt(S.length() - 1));
				String B = Register_Execute.get(Register_Execute.size() - 1);
				interpret(B);
			    }
			}
			else {
			    System.out.println("Non-Numeric Value");
			}
			break;
		    }
		    else if (S.matches("L\\w")) {
			ArrayList<String> Get_Pop_Register = Registers.get(S.charAt(S.length() - 1));
			First_Element = Get_Pop_Register.get(Get_Pop_Register.size() - 1);
			Get_Pop_Register.remove(Get_Pop_Register.size() - 1);
			Registers.set(S.charAt(S.length() - 1), Get_Pop_Register);
			Main_Stack.add(First_Element);
			break;
		    }
		    else if (S.matches("S\\w")) {
			Registers.get(S.charAt(S.length() - 1)).add(Main_Stack.get(First_Index).toString());
			Remove(1);
			break;
		    }
		    Main_Stack.add(S);
		}
	    }
	    if (program.charAt(0) == '[') {
		program = program.substring(S.length());
	    }
	    else {
		program = Match.replaceFirst("");
	    }
	    //System.out.println("New String is: " + program);
	    if (QUIT > 0) {
		QUIT--;
		return 0;
	    }
	}
	/* for (int u = 0;u < Main_Stack.size(); u++) {
	    System.out.println(Main_Stack.get(u));
	    } */
	return 0;
    }

    public static void main(String[] args){
	dc_interpretor Invocation = new dc_interpretor();
	Invocation.interpret(args[0]);
	/*String R = "567";
	  System.out.println(String.format("%0$5s",R));*/
    }
}
