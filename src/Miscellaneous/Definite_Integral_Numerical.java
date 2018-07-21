//
// Numerically approximate a Definite Integral
import java.util.function.Function;
import java.util.*;
import java.lang.Math.*;
public class Definite_Integral_Numerical {

    // `f' is the integrand, `a' is the starting point, `b' is the end point, `N' is the number of sub-intervals (the higher the better)
    public static double Solver(Function<Double,Double> f, double a, double b, int N) {
	double Total = 0;
	for (double u = 1 ; u <= N ; ++u) {
	    
	    // Trapezoidal Riemann Sum - FASTEST CONVERGING of methods presented here.
	    // Total += (f.apply(a + (b - a) * (u - 1)  / N) + f.apply(a + (b - a) * u / N)) / 2 * ((b - a) / N);
	    
	    // Right-hand Riemann Sum
	    // Total += f.apply(a + (b - a) * u / N) * ((b - a) / N);

	    // Left-hand Riemann Sum
	    // Total += f.apply(a + (b - a) * (u - 1) / N) * ((b - a) / N);

	    // Midpoint Riemann Sum
	    // Total += f.apply(a + (b - a) * (u - 1/2) / N) * ((b - a) / N);

	    // Simpson's Rule - http://tutorial.math.lamar.edu/Classes/CalcII/ApproximatingDefIntegrals.aspx
	    // To see all of the methods listed here in action, invoke the Python file named `Natrix_Operations.py' stored in the Google Drive base directory.
	    
	}
	return Total;
    }

    public static void main(String[] args) {
	System.out.println(Solver(s -> Math.pow(Math.E,Math.pow(s,2)), 0, 2, 1000000));
    }
}
