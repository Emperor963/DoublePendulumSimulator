/**
 * Driver Class for the Double Pendulum Simulator
 * Executes primary functions, using additional helper classes
 * 
 * @author Sarannya Bhattacharya
 *
 */
public class DoublePendulumDriver {

	float[] massTensor = new float[4]; //Array containing the mass tensor of the system
	float[] responseTensor = new float[4]; //Array containing the response tensor of the system
	final float g = 9.8f; //Value of acceleration due to gravity fixed. Could be made to be a user input as well.
	float m1; //Mass of the first bob
	float m2; //Mass of second bob
	float l1; //Length of first bob
	float l2; //Length of second bob
	float[] w = new float[2]; //eigenfrequencies of the system
	
	/**
	 * Constructor Method
	 * @param m1: Mass of the first bob
	 * @param m2: Mass of the second bob
	 * @param l1: Length of the first pendulum
	 * @param l2: length of the second pendulum, from the first
	 */
	public DoublePendulumDriver(float m1, float m2, float l1, float l2) {
		this.m1 = m1;
		this.m2 = m2;
		this.l1 = l1;
		this.l2 = l2;
		
		massTensor[0] = m1 * l1 * l1 + m2 * l2 * l2;
		massTensor[1] = m2 * l1 * l2; //cosine term omitted as a part of small angle approximation
		massTensor[2] = massTensor[1];
		massTensor[3] = m2 * l2 * l2;
		
		responseTensor[0] = g * (m1 * l1 + m2 * l2);
		responseTensor[3] = g * m2 * l2;
		responseTensor[1] = responseTensor[2] = 0;
	}
	
	/**
	 * Let the response tensor be A and the mass tensor be denoted m. We assume:
	 * Theta1 = B1 exp(iwt)
	 * Theta2 = B2 exp(iwt)
	 * 
	 * Thus we have the secular equation det(A - w^2B) = 0
	 * 
	 * We note that the roots of this equation must always be real, because w itself is always real.
	 * For our purposes, if w was imaginary, we would have a theta that is exponentially increasing. While it is
	 * theoretically possible for the pendulum to go around in a full circle (which is what the imaginary value of w corresponds
	 * to), we shall ignore it for our simplistic case. Thus, w^2 is always real and the quadratic equation CAN NOT have imaginary roots.
	 * 
	 */
	public void secularEquationSolver() {
		
		float[] secular = new float[3];
		
		/*
		 * Let us assume that {A} = [a0  a1]
		 *                          [a2  a3]
		 *                        
		 * and correspondingly {m} = [m0  m1]
		 *                           [m2  m3]
		 *                         
		 * Using |A - w^2m| = 0, we come up with the following:
		 * 
		 * (a0 - m0w^2)(a3 - m3w^2) = (a1 - m1w^2)(a2 - m2w^2)     
		 * 
		 *  (m0m3 - m1m2)w^4 + [(a1m2 + a2m1) - (a3m0 + a0m3)]w^2 + (a0a3 - a1a2) = 0
		 *  
		 *  We use these coefficients to solve for w1 and w2
		 */
		
		secular[0] = massTensor[0] * massTensor[3] - massTensor[1] * massTensor[2];
		secular[1] = (responseTensor[1] * massTensor[2] + responseTensor[2] * massTensor[1]) - (responseTensor[3] * massTensor[0] + responseTensor[0] * massTensor[3]);
		secular[2] = responseTensor[0] * responseTensor[3] - responseTensor[1] * responseTensor[2];
		
		//TODO: Add code for imaginary eigenfrequency
		
		QuadraticSolver solver = new QuadraticSolver(secular[0], secular[1], secular[2]);
		float[] w2 = solver.Solver();
		w[0] = (float) Math.sqrt(w2[0]);
		w[1] = (float) Math.sqrt((float)w2[1]);
	}
	
	/**
	 * Given Theta1 and Theta2, this method calculates the coefficients of the eigenmodes, given a particular eigenfrequency
	 * @param eigenfrequency: particular eigenfrequency for which we aim to calculate the eigenmode
	 * @return array of two eigenmodes
	 */
	public float[] eigenmodeFinder(float eigenfrequency) {
		float B_1;
		float B_2 = 1;
		float w = eigenfrequency;
		/**
		 * Given A and m, we have the following Equation of Motion:
		 * 								(A - w^2m)(B1) = 0
		 * 										  (B2) 
		 * Thus we have (A[0] - w^2m[0])B1 + (A[1] - W^2m[1])B2 = 0
		 * For any given eigenfrequency, if we scale one of the eigenmodes to unity, we can find the vector (B1 B2)
		 * as something like (B' 1). That is why we take B_2 = 1 previously.
		 * 
		 * Therefore we can write B_1 as:
		 * 
		 *  B_1 = (A[1] - w^2m[1])B2 / (A[0] - w^2m[0])
		 */
		
		B_1 =  - (responseTensor[1] - w * w * massTensor[1]) * B_2 / (responseTensor[0] - w * w * massTensor[0]);
		
		
		
		return new float[] {B_1, B_2};
	}
	
	/**
	 * Returns the normal mode for a given eigenfrequency by solving the harmonic differential equation
	 * @param eigenfrequency
	 * @param t: array consisting time frames for which we find the values of the normal mode
	 * @return normal mode for a given frequency
	 */
	public float[] normalModes(float eigenfrequency, float[] t, float n0, float ndot0) {
		float[] normalMode = new float[t.length];
		normalMode[0] = n0;
		normalMode[1] = n0 + ndot0 * ((t[1] - t[0]) / 100);
		float w = eigenfrequency;
		
		functionCall fn = (y, time) -> {return w * w * y;}; 
		
		IntegralCalculator calculator = new IntegralCalculator(fn);

		for(int i = 2; i < t.length; i++) {
			
			normalMode[i] = calculator.HarmonicFDM2(ndot0, n0, t[i], t[i-1], 100, w);
			n0 = normalMode[i-1];
			ndot0 = (normalMode[i-1] - normalMode[i-2]) / (t[i-1] - t[i-2]);
			
		}
		
		return normalMode;
	}
}
