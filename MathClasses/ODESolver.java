/**
 * Solves Ordinary Differential Equations using Euler's Method and Finite Difference Method for
 * Second Order Ordinary differential equations
 * @author Sarannya Bhattacharya
 *
 */
package MathClasses;
public class ODESolver extends IntegralCalculator{

	/**
	 * Necessary constructor method
	 * @param func
	 */
	public ODESolver(functionCall func) {
		super(func);
	}
	
	/**
	 * Return approximate value of a function at a given x value,
	 * Given the boundary condition of the f(y, x). Presently uses a constant 
	 * value of partition lengths for solving the ODE
	 * @param x0: initial x value at boundary
	 * @param y0: initial y value at boundary
	 * @param x: x at which the approximation is required
	 * @param n: Number of parititions between x and x0. The higher the partitition number, the greater the precision.
	 * Rather than taking the size of the partition as input, the number of partitions allow for the approximation to be 
	 * precisely at point x rather than at its vicinity
	 * @return approximation of y at x, given dy/dx = f(y,x) and y = y0 when x = x0
	 * 
	 * 
	 * The error in this calculation is of the order of O(1/n^2)
	 */
	protected float Euler(float x0, float y0, float x, int n) {
		
		float h = (x - x0) / n;
		float y = y0;
		// this while loop runs from x0 to x, approximating the values in each step.
		// larger the value of n, greater the precision
		while(x > x0) {
			y += h * f(y0, x0);
			x0 += h;
		}
		
		return y;
	}
	
	/**
	 * Finite Difference Method for solving the Oscillator Differential Equation
	 * 
	 * @param v0 initial velocity of the object
	 * @param x0 initial position of the object
	 * @param t time in which we want to know the x value
	 * @param t0 initial time where the other initial values are located	
	 * @param n number of passes, accuracy increases with n, speed decreases
	 * @param w the frequency of the oscillator
	 * @return the value of x of the oscillator at a given time
	 * 
	 * Essentially, we use the following equations:
	 * 
	 * x_n+1 = (2-w^2h^2)x_n - x_n-1
	 * x0 = x0
	 * x1 = x0 + v0 * x0
	 * 
	 * recursively, where h is the time difference dt.
	 */
	protected float FDM2Harmonic(float v0, float x0, float t, float t0, int n, float w) {
		
		float dt = (t - t0) / n;
		float x1 = x0 + v0 * dt;
		float x2 = 0;
		float v = 0;
		while(t > t0) {
			//System.out.println("y");
			x2 = (2 -  w * w * dt * dt) * x1 - x0;
			v = (x1 - x0) / dt;
			x0 = x1;
			x1 = x0 + v * dt;
			t0 += dt;
		}
		
		return x2;
	}

}
