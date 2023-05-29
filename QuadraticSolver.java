/**
 * Solves a quadratic equation using the Quadratic formula
 * Numerical methods not used in order to improve performance
 * Quadratic equations accepted are of the form ax^2 + bx + c
 * @author Sarannya Bhattacharya
 *
 */
public class QuadraticSolver {
	
	float a; //Coefficient of quadratic term
	float b; //Coefficient of linear term
	float c; //Constant term
	
	/**
	 * Constructor Method
	 * @param a: //Coefficient of quadratic term
	 * @param b: //Coefficient of linear term
	 * @param c: //Constant term
	 */
	public QuadraticSolver(float a, float b, float c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	/**
	 * Solver method
	 * @return two roots of the quadratic equation
	 */
	public float[] Solver() {
		float[] roots = new float[2];
		
		float denom = 1 / ( 2 * a);
		float determinant = b * b - 4 * a * c;
		float sqrtTerm = (float) Math.sqrt(determinant);
		if(determinant < 0) sqrtTerm = (float) Math.sqrt(- determinant); //temporary solution to imaginary solutions
		
		roots[0] = (b * b + sqrtTerm) * denom;
		roots[1] = (b * b - sqrtTerm) * denom;
		
		return roots;
	}
}
