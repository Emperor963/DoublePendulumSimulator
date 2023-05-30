/**
 * Integrator for functions in Single Variable
 * 
 * Uses Simpson's Method for integration
 * 
 * @author Sarannya Bhattacharya
 *
 */
public class IntegratorSV extends IntegralCalculator{
	
	/**
	 * Necessary Constructor method
	 * @param func: function to integrate
	 */
	public IntegratorSV(functionCall func) {
		super(func);
	}
	
	/**
	 * Changes Function in superclass to a single variable function
	 * @param x: input parameter of SV function f(x)
	 * @return output of function
	 */
	private float f(float x) {
		return super.f(0, x);
	}

	/**
	 * Returns result of Midpoint method of integration
	 * @param a lower bound
 	 * @param b upper bound
 	 * @param n number of partitions
	 * @return integral value
	 */
	private float midpointIntegrator(float a, float b, int n) {
		float h = (b - a) / n; //partitions
		float S = 0; // sums up the individual midpoint sums
		float lower = a; //lower bound for individual sums
		float upper = a + h; //upper bound for individual sums
		for(int i = 1; i < n; i++) {
			S += (upper - lower) * f((upper + lower) / 2);
			lower += h;
			upper += h;
		}
		return S;
	}
	
	/**
	 * Implementation of trapezoidal method of calculating integral
	 * @param a lower bound of integral
	 * @param b upper bound of integral
	 * @param n number of partitions
	 * @return integral value
	 */
	private float trapezoidalIntegrator(float a, float b, int n) {
		float h = (b - a) / n; //partitions
		float S = 0; // sums up the individual midpoint sums
		float lower = a; //lower bound for individual sums
		float upper = a + h; //upper bound for individual sums
		for(int i = 0; i < n; i++) {
			S += 0.5 * (upper - lower) * (f(upper) + f(lower));
			lower += h;
			upper += h;
		}
		return S;
	}
	/**
	 * Implementation of Simpson's method
	 * Uses the fact that Integral = (2/3)M + (1/3)T accounts for errors made in midpoint and trapezoidal
	 * methods. Here M is the result of midpoint integration and T is the result of trapezoidal integration
	 * @param a lower bound of the integral
	 * @param b upper bound of the integral
	 * @param n number of passes to make
	 * @return integral value
	 */
	public float Integration(float a, float b, int n) {
		float h = (b - a) / n; //partitions
		float S = 0; // sums up the individual midpoint sums
		float lower = a; //lower bound for individual sums
		float upper = a + h; //upper bound for individual sums
		for(int i = 0; i < n; i++) {
			S += (2.0 / 3.0) * this.midpointIntegrator(lower, upper, n) + (1.0 / 3.0) * this.trapezoidalIntegrator(lower, upper, n); 
			lower += h;
			upper += h;
		}
		
		
		return S;
	}

}
