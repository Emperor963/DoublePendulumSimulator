/**
 * Solves Ordinary Differential Equations of the form:
 *  dy/dx = f(x, y), Given boundary condition f(x0)
 *  Uses Euler Method for solution currently.
 *  
 *  Also uses the Simpson's method to integrate a given function
 *  
 * @author Sarannya Bhattacharya.
 *
 */
package MathClasses;
public class IntegralCalculator{
	
	functionCall func;
	
	/**
	 * Constructor method
	 * @param func: sets an Interface object as an input paramater that is used as the function f(x, y)
	 * for the purpose of integration or solving an ODE
	 */
	public IntegralCalculator(functionCall func) {
		this.func = func;
	}
	
	/**
	 * Method that allows customization of the function other than the data field of this Object
	 * @param x argument of the function
	 * @param func customizable function
	 * @return output of given function
	 */
	protected float f(float y, float x, functionCall func) {
		return func.function(y, x);
	}
	
	/**
	 * Overload method that sets customizable function in previous method to the one
	 * in this Object
	 * @param x argument of the function
	 * @return output of the function
	 */
	protected float f(float y, float x) {
		return f(y,  x, this.func);
	}
	
	/**
	 * Single Variable Integrator using the IntegratorSV class
	 * @param a lower bound
	 * @param b upper bound
	 * @param n number of passes
	 * @return integrated value
	 */
	public float SVIntegrator(float a, float b, int n) {
		IntegratorSV integrator = new IntegratorSV(this.func);
		return integrator.Integration(a, b, n);
	}
	
	/**
	 * ODE Solver using Euler's method implemented in ODESolver class
	 * @param x0: x value of boundary condition
	 * @param y0: Given y value of boundary condition
	 * @param x: the value of x at which the approx is required
	 * @param n: number of partitions, a greater n means greater precision (but slower speed)
	 * @return: approximate value of y at x given dy/dx = f(y,x) and y = y0 at x = x0
	 * 
	 * The error in this calculation is of the order of O(1/n^2)
	 */
	public float EulerODE(float x0, float y0, float x, int n) {
		ODESolver solver = new ODESolver(this.func);
		return solver.Euler(x0, y0, x, n);
	}
	
	/**
	 * ODE Solver for second order differential equation using Finite Difference Method
	 * 
	 * @param v0 initial velocity of the object
	 * @param x0 initial position of the object
	 * @param t time in which we want to know the x value
	 * @param t0 initial time where the other initial values are located	
	 * @param n number of passes, accuracy increases with n, speed decreases
	 * @param w the frequency of the oscillator
	 * @return the value of x of the oscillator at a given time
	 */
	public float HarmonicFDM2(float v0, float x0, float t, float t0, int n, float w) {
		ODESolver solver = new ODESolver(this.func);
		return solver.FDM2Harmonic(v0, x0, t, t0, n, w);
	}
	
	

}
