
// VERY IMPORTANT RESOURCE: http://docs.oracle.com/javase/8/
// A great tutorial: https://docs.oracle.com/javase/tutorial/index.html

// How does this `import' statement work?
// `import' in Java simply shorthand method to reference class(es) in a package, unlike #include directives in C, which literally include the contents of the referenced files in the compiled source code that references them.
// `import' pretty much syntactical sugar, to make programmers life a bit easier.
// Each `.' usually represents a shift into a new `package' or directory. Java classes organized into packages, which can be in more packages, so on so forth.
// For example, `java.util.function' simply means the class file(s) needed are in `.../java/util/function'
// Here, `...' represents the search path.
// The class loader can even search `.jar' Java archives for classes!

// The `*' is simply a wildcard. The `*' represents one or more characters. Therefore, `java.util.function.*' references *all classes and packages* in the directory `.../java/util/function/'.
// `.*' IS REQUIRED FOR ALL *STATIC* IMPORTS; OTHERWISE ONLY FOR PACKAGES (i.e. `import java.lang.Math' works but `import static java.lang.Math' does not (as `Math' is a class)  whereas `import static java.lang.Math.*' does).
// This all being said, you can even get rid of the `java.util.function.*' import and literally replace all references to classes in the `java.util.function' package with `java.util.function.{CLASS NAME}'.

// The default location of all JDK classes (at least on macOS) is actually a `.jar' archive named `rt.jar' located at `/Library/Java/JavaVirtualMachines/jdk1.8.0_92.jdk/Contents/Home/jre/lib'
// This is the first place where the class loader searches for classes. `rt' stands for `runtime'. On macOS, `/Library/Java' is the Oracle default Java directory.
// You can execute `java' with the option `-verbose:class' to get information during runtime about where external loaded classes are located.

// Adding `static' before a package name to `import' allows you to reference *all* public methods in classes without having to prefix them with class name (i.e. `cos(...)' vs. `Math.cos(...)')
// Without `static' you CAN reference classes without having to prefix package name, but you CANNOT reference class methods without having to prefix the class name!

// To list all items in any `.jar' file, simply run command `jar tf {Name}.jar' in shell.

// Even though say, in the `java.lang.*' package, the `Math' class file is named `Math.class', to reference it in other files you *must always* use `java.lang.Math' as, to the compiler, that is its only name!
// The class name, appended to the provided `package' declaration, is known as the "qualified class name", which is the only way the class loader can reference it!

// More information: http://stackoverflow.com/questions/227486/find-where-java-class-is-loaded-from
// More information: http://stackoverflow.com/questions/1053658/how-is-import-done-in-java
// More information: http://stackoverflow.com/questions/12620369/how-java-import-works
// More information: http://stackoverflow.com/questions/11547458/what-is-the-difference-between-jvm-jdk-jre-openjdk
// More information: http://stackoverflow.com/questions/2294733/java-package-and-classpath-question
// More information: http://stackoverflow.com/questions/8227682/whats-the-default-classpath-when-not-specifying-classpath
// More information: http://stackoverflow.com/questions/8934780/how-jvm-starts-looking-for-classes
// More information: http://stackoverflow.com/questions/1031962/multiple-class-files-generated-for-a-class
// More information: http://stackoverflow.com/questions/7486012/static-classes-in-java
// More information: https://docs.oracle.com/javase/8/docs/technotes/tools/findingclasses.html

package Package.MyPack; // In the *class destination directory*, upon successful compilation, this statement creates a new directory tree `Package/MyPack/' which contains the compiled class file named `Java_Experiment_File.class'
                        // With this, the compiled `.class' file can *only* be invoked or imported with `Package.MyPack.Java_Experiment_File' with the *directory the package was created in* in the class path!
                        // By default, the directory a class is located in is the Java class path.


import java.lang.*; // NOT REQUIRED! Already implicity imported during compilation.
import java.util.function.*; // New package of functional interfaces (interfaces with one abstract method) mainly for Java 1.8 lambda expressions
import java.io.*; // Java package to manage input streams (i.e. files, standard input, etc.)
import java.util.*; // Java utilities, including the `Collections' framework and the `Scanner' class

// Separate class file named `Hi.class' created for following interface upon compilation.
interface Hi {
    // MUST BE IMPLEMENTED IN IMPLEMENTING CLASS!!
    void Okay(String q);
}

// What is going on here? Extending an interface?
// Well, this is known as "evolving an interface".
// This way you can have a new interface that inherits all the abstract methods of the interface it extends while also defining new ones.
// For example, the interface `HiPlus' below inherits the abstract method `Okay' of the interface `Hi' while also defining the new abstract method `Wow'.
// Therefore, any implementing classes must implement the methods `Okay' and `Wow'.
// Also, upong compilation, another separate class file named `HiPlus.class' is created for the following interface
interface HiPlus extends Hi {
    void Wow(String m);
}

// Replacing `class' with `abstract class' results in following class being `abstract', which most importantly means that it can no longer be instantiated!
// There are also other restrants, such as no static fields, etc.
// Abstract classes are very similar to interfaces.
public class Java_Experiment_File implements Hi { // Class declaration; Rest inside first pair of following brackets is class definition
                                                  // Source: http://stackoverflow.com/questions/11715485/what-is-the-difference-between-declaration-and-definition-in-java
    
    // Variables where object stores inital "state" known as `field' variables
    // BEGIN FIELDS
    // Non-static fields (instance variables); opposite of static fields (class variables)
    int F;
    String Q;
    double R = Math.cos(3); 
    // Static fields (class variables); The same *regardless* of object instance
    static int E = 5;
    static ArrayList<Integer> Y = new ArrayList<Integer>();;
    // Static initialization blocks, to initialize static variables when more logic is needed, as constructors are not invoked when static variables are declared and initialized
    // `static' can be removed to form an instance initialization block as an alternative to a constructor
    // Can also initialize `final' variables, whereas constructors cannot!
    static { for (int i = 0; i < 3; i++) {
	    Y.add(i);
	}
	System.out.println(Y); // Output: [0, 1, 2] 
    }
    // END FIELDS
    
    // Parameter is a variable in a method declaration; Argument is actual value passed to parameter
    // For example, `integer' is a parameter of the constructor declared below, whereas the `3' in `new Java_Experiment_File(3)' is an argument
    // Source: http://stackoverflow.com/questions/156767/whats-the-difference-between-an-argument-and-a-parameter
    // Constructor; Invoked upon instantiation of object from class
    // Absence of return type is how a constructor is distinguished from a method with the same name as the class
    // Source: http://stackoverflow.com/questions/6801500/why-do-constructors-in-java-not-have-a-return-type
    public Java_Experiment_File(int integer) /* Declaration */ {
	// Begin definition
	// In this constructor, `this(1)' results in compile-time exception as it makes constructor recursive, which is *not* allowed!
	F = integer;
	System.out.println("Instance variable `F' is set to " + F + "!");
	// NO `return' statements allowed in constructors!
	// End definiton
    }
    
    // A second constructor! This is only invoked upon instantiation iff the arguments provided fulfill the parameter reuqirements (i.e. an integer and string are provided)!
    // Constructors distinguished from one another based on parameter requirements!
    // Not allowed to have recursive constructors, nor two or more constructors with same parameter requirements, in any class!
    public Java_Experiment_File(int F, String string) {
	// The `this' keyword references the field rather than the parameter! For example, the following assigns the argument provided to parameter `F' to the class's `F'.
	// Also, `this(...)' invokes the contructor for which the parameter requirements are fulfilled by the arguments provided.
	// More information: http://stackoverflow.com/questions/285177/how-do-i-call-one-constructor-from-another-in-java
	this.F = F;
	Q = string;
	System.out.println("Instance variable `F' set to " + F + " and `Q' set to '" + Q + "'!");
    }

    // NOT A CONSTRUCTOR; Notice the presence of return type; Instead just a method with same name as class
    public Java_Experiment_File Java_Experiment_File(String string) {
	System.out.println(string);
	// This is possible!!! NO error generated!!!
	return new Java_Experiment_File(3);
    }

    // MUST BE PUBLIC AS CLASS IS DEFINED PUBLIC!!!
    public void Okay(String q) {
	System.out.println("q = " + q);
    }
    
    // Static (class) method; The same behaviour *regardless* of object instance (as opposed to non-static (instance) methods)
    // Iff variable `E' NOT declared `static', and method `Method' tried to access it, then upon compilation an error would be thrown:
    ///// `Java_Lambda_Expression_Example.java:11: error: non-static variable E cannot be referenced from a static context'
    // Also, the following is a Generic method, denoted by the type parameter placeholder in angle brackets (<>)
    // Learn more: http://stackoverflow.com/questions/6008241/java-generics-e-and-t-what-is-the-difference
    static <T> void Method(T arg_1) {
	System.out.println(arg_1);
	// Local variable (variable declared inside a method); ONLY visible to method
	// MUST be initialized before it can be accessed later on inside the method!
	// Relevant: https://stackoverflow.com/questions/1560685/why-must-local-variables-including-primitives-always-be-initialized-in-java
	int U = 5;
	System.out.println(U); // If `U' had not been initialized by this point, a compile-time error would be thrown!
    }
    
    // CANNOT be declared inside methods
    // For first lambda expression
    // Separate class file named `Java_Experiment_File$Sample.class' created for the following interface.
    interface Sample {
	void method(String a);
	// New in Java 1.8 - default interface methods
	default void method_default() {
	    System.out.println("Hello from default method!");
	}
    }

    // Foundation for a recursive lambda function!
    static Function<Integer, Integer> factorial;

    // Used for the Anonymous class example that is to come in the `main' method
    // The following interface can be completely replaced with `class AnonymousClass {}' (an empty local inner class) and the Anonymous class will still work!
    // Static nested classes are just like classes defined outside the enclosing class; in other words, they cannot access non-static fields of the class they are nested in!
    interface AnonymousClass {
	String greet(String name);
	int okay(int input);
    }

    public static void main(String[] args) throws IOException { // `throws IOException' is an alternative to the `try-catch-[finally]' block
	
	// Lambda expression 
	Sample Lambda = String -> System.out.println(String);
	// Invocation
	Lambda.method("Hello, World!"); // Output: Hello, World!

	// Or, using new Generic Functional Interfaces provided in the new package `java.util.function' bundled with Java 1.8
	// (NO custom interface declaration required!):
	// More Information: http://docs.oracle.com/javase/8/docs/api/java/util/function/package-summary.html
	Consumer<String> Lambda_2 = String -> System.out.println(String);
	// Invocation
	Lambda_2.accept("Hello, World 2!"); // Output: Hello, World 2!
	// A recursive lambda function!
	// Relevant: https://pysaumont.github.io/2014/09/01/Recursive-lambdas-in-Java-8.html
	factorial = a -> a > 1 ? a * factorial.apply(a - 1) : a;
	System.out.println(factorial.apply(10)); // Output: 3628800

	System.out.println(E); // Output: 5
	                       // `E' is a static variable! It can be accessed even when the class has not been instantiated yet!
	Java_Experiment_File One = new Java_Experiment_File(3); // Output: Instance variable `F' is set to 3!
	System.out.println(One); // Output: Package.MyPack.Java_Experiment_File@...
	                         // ... represents the hexadecimal memory address where object is stored
	Java_Experiment_File Two = new Java_Experiment_File(4, "Hi"); // Output: Instance variable `F' set to 4 and `Q' set to 'Hi'!
	Two.E = 9;
	System.out.println(One.E + " " + Two.E + " " + E); // Output: 9 9 9
	                                                   // Since variable `E' is static, if value associated with one object instance changes, value associated with *all* object instances changes!
	
	// Two ways to call static generic method `Method'
	Java_Experiment_File.<Integer>Method(3); // Output: 3
        Method("Hello"); // This way the method can take an argument of *any* type (e.g. Method(3) also works) unlike previous way
	                 // This is known as `Type Inference' : http://docs.oracle.com/javase/tutorial/java/generics/genTypeInference.html
	                 // Output: Hello

	Java_Experiment_File[] Array = new Java_Experiment_File[3]; // Initializes `Array' as an array of objects of type `Java_Experiment_File'
	                                                            // Any type referring to a class and declaring an object is called a `reference type'; otherwise a `primitive type'
	                                                            // Also, any variable which holds an object is known as a `reference'
                                                                    // Is the constructor still called? No! Nothing is output! Creates array with 3 uninstantiated references to `Java_Experiment_File'!
	                                                            // Relevant: http://stackoverflow.com/questions/3426843/what-is-the-default-initialization-of-an-array-in-java
	// Java enhanced (`for-each') loops
	// Iterates through array `Array' initialized above
	for (Java_Experiment_File U : Array) {
	    System.out.println(U); // Output: null
	    // Calling `U(4)' or `new U(4)' or anything along lines causes compile-time error!
	}
	
	// To create object instances in array `Array' do following
	for (int index = 0;index < 3;index++) {
	    Array[index] = new Java_Experiment_File(5); // Output: Instance variable `F' is set to 5!
	    System.out.println(Array[index]); // Output: Package.MyPack.Java_Experiment_File@...
	}
	System.out.println(Array[0]); // Output: Package.MyPack.Java_Experiment_File@...

	Scanner in_2 = new Scanner(System.in).useDelimiter("\\n"); // Very versatile input reading object; best way to read from STDIN
        System.out.println(in_2.next()); // Read next token of input up to the delimiter provided as argument to `Scanner' method `useDelimiter(...)'; In this case, it reads the first line of the input
        in_2.close(); // Always remember to close streams to avoid memory leaks!

	// try-catch-[finally] block; Used to handle exceptions; the `finally' block is optional and *always* invoked if present
	// Since the `InputStreamReader' class *can* throw an `IOException', it must either be handled in a `try-catch-[finally]' block *or* be declared using the `throws' keyword in the method declaration
	// This is true for *all* exceptions!
	// More information: http://stackoverflow.com/questions/1989077/the-throws-keyword-for-exceptions-in-java
	// More information: http://stackoverflow.com/questions/3794910/difference-between-try-catch-and-throw-in-java
	try {
	    InputStreamReader in = new InputStreamReader(System.in); // Instantiate object with methods capable of reading from STDIN in reference `in'
	                                                             // `System.in' is a static field which allows access to STDIN. It is statically defined in the class `java.lang.System'.
	
	    System.out.println((char)in.read()); // Reads single character from STDIN and outputs it. As `in.read()' returns codepoint representing character, return value must be casted to `char'.
	                                         // Output: {The first character of input}

	    in.close(); // ALWAYS remember to close streams to free allocated memory! Otherwise, a memory leak could occur (https://en.wikipedia.org/wiki/Memory_leak)!
	    throw new Exception(); // Manually throw an exception
	}
	catch (Exception e) {
	    // Do something if exception thrown.
	}
	finally {
	    // Do something regardless of whether exception thrown or not.
	}

	// ArrayList: An extensible, dynamic array; Much more versatile than the built-in arrays
	// Now, before we move on, notice that `List<E>' is an interface in the `java.util' package. In that case, how can it be a type?
	// Well, whenever an interface is used as a type for a reference variable, the variable may only be assigned an object which is an instance of a class which implements that interface!
	// For example, the `ArrayList<E>' class implements the `List<E>' interface. Therefore, I can assign a `ArrayList<E>' object to a reference varaible of type `List<E>'.
	// However, if I were to assign an instance of the `Java_Experiment_File' class to a reference variable of type `List<E>', since `Java_Experiment_File' does *not* implement `List<E>', a compile-time exception would occur.
	List<Integer> arrayList = new ArrayList<Integer>(Arrays.asList(123, 56, 365, 34, -1, -456, 4563));

	// Lambda expression as an argument to a method which sorts the `ArrayList<E>'
	// The lambda experession is outmatically being casted to `Comparator<E>' where type inference based on `ArrayList' type is used to figure out `E'
	// The `Comparator<E>' functional interface is apart of the `java.utils' package
	// The following utilizes merge sort for sorting purposes
	arrayList.sort((a,b) -> a.compareTo(b));

	System.out.println(arrayList); // Output: [-456, -1, 34, 56, 123, 365, 4563]

	List<Integer> arrayList2 = new ArrayList<Integer>(Arrays.asList(25, 46, 89, 53, 1265, 43, 8234509, -45234895, -322345532));

	// The following also works and the argument is known as a `method reference'
	// The part before the colons is the class containing the method and the part after is the mthod to be referenced
	// If the method to be referenced is `new' then the constructor is referenced
	arrayList2.sort(Integer::compareTo);

	System.out.println(arrayList2); // Output: [-322345532, -45234895, 25, 43, 46, 53, 89, 1265, 8234509]

	List<?> array = new ArrayList<Integer>(Arrays.asList(1,2,3,4));

        // Anonymous inner classes! The following compiles and works!
	// NOTE: Constructors *not* allowed in anonymous inner classes!
	// Also, class name must first be declared somewhere else as either interface or class!
	// If the predefined class name is an interface, the anonymous inner class is a new class instantiated with the name of the interface which `implements' the interface's abstract methods!
	// Therefore, the following `AnonymousClass' must implemement the abstract methods `greet' and `okay' defined in the interface `AnonymousClass'.
	// Also, the following creates another class file named `Java_Experiment_File$1.class'
	AnonymousClass D = new AnonymousClass() {
		
		public String greet(String name) {
		    return "Hi " + name + "!";
		}

		public int okay(int input) {
		    return input * 4;
		}

	    }; // One way to instantiate an anonymous inner class
	System.out.println(new AnonymousClass() {

		int i = 4;
		
		public String greet(String name) {
		    return "Hello " + name + "!";
		}

		public int okay(int input) {
		    return input + 3;
		}
		
	    }); // Anonymous class as an argument to a method
	        // Output: Package.MyPack.Java_Experiment_File$1@...
    }
}
