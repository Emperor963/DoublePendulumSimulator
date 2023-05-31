/**
 * Runs a particular example of the Double Pendulum Simulator
 * The parameters here are completely adjustable according to necessary requirements
 * @author Sarannya Bhattacharya
 */
import java.io.*;
public class DriverMain {
	
	/**
	 * Writes the values of Theta1 and Theta2 into .txt files in /data
	 * @param Theta1 values of theta1 in an array
	 * @param Theta2 values of theta2 in an array
	 */
	public static void writer(float[] Theta1, float[] Theta2) {
		try {
			File theta1 = new File("data" + File.separator + "Theta1.txt");
			File theta2 = new File("data" + File.separator + "Theta2.txt");
			
			FileWriter writer1 = new FileWriter(theta1);
			FileWriter writer2 = new FileWriter(theta2);
			
			for(int i = 0; i < Theta1.length; i++) {
				if(i % 1 == 0) {
					writer1.write(i + " " + Theta1[i] + "\n");
					writer2.write(i + " " + Theta2[i] + "\n");
				}
			}
			
			writer1.close();
			writer2.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		float length1 = 1;
		float length2 = /*(float)Math.sqrt(2) **/ length1;
		float mass1 = 1f;
		float mass2 = 1.5f;
		
		DoublePendulumDriver pendulumSystem = new DoublePendulumDriver(mass1, mass2, length1, length2);
		pendulumSystem.secularEquationSolver();
		float[] Bw1 = pendulumSystem.eigenmodeFinder(pendulumSystem.w[0]);
		float[] Bw2 = pendulumSystem.eigenmodeFinder(pendulumSystem.w[1]);
		float[] time = new float[1000];
		for(int i  = 0; i < time.length; i++) {
			time[i] = i;
		}
		float[] n1 = new float[time.length];
		float[] n2 = new float[time.length];
		/**
		 * We assume that Theta1 = B11 + B12
		 * 				  Theta2 = B21 + B22 initially at t = 0
         *
		 * float n10 = 1;
		 * float n20 = 1;
		 * n1 = pendulumSystem.normalModes(pendulumSystem.w[0], time, n10, 0);
		 * n2 = pendulumSystem.normalModes(pendulumSystem.w[1], time, n20, 0);
		 *
         * THIS IS NOT USED IN LIEU OF ISSUES WITH NORMALIZATION OF THE COEFFICIENTS. WE IMPLICITLY ASSUME THAT THE NORMAL MODES
		 * ARE GIVEN AS COSINE FUNCTIONS RATHER THAN NUMERICALLY SOLVE THE EQUATION.
		 *
		 * Numerical solution using ODESolver.java's FDM method causes the solution to blow up, presumably due to the lack of normalization
		 * of the eigenmodes of this algorithm. Hence we stuck to an analytical solution of using the normal modes as the real part of the 
		 * exp(iwt), which is cos (wt).
		*/
		for(int i = 0; i < time.length; i++) {
			n1[i] = (float) Math.cos(pendulumSystem.w[0] * time[i]);
			n2[i] = (float) Math.cos(pendulumSystem.w[1] * time[i]);
		}
		System.out.println(pendulumSystem.massTensor[0] + " " + pendulumSystem.massTensor[1]);
		System.out.println(pendulumSystem.massTensor[2] + " " + pendulumSystem.massTensor[3]);
		System.out.println();
		System.out.println(pendulumSystem.responseTensor[0] + " " + pendulumSystem.responseTensor[1]);
		System.out.println(pendulumSystem.responseTensor[2] + " " + pendulumSystem.responseTensor[3]);
		System.out.println();
		System.out.println(pendulumSystem.w[0] + "   " + pendulumSystem.w[1]);
		System.out.println();
		System.out.println(Bw1[0] + "  " + Bw1[1]);
		System.out.println(Bw2[0] + "  " + Bw2[1]);
		System.out.println();
		for(int i = 0; i < n1.length; i++) {
			System.out.println(n1[i] + " " + n2[i] +  "        " + time[i]);
		}
		float[] Theta1 = new float[time.length];
		float[] Theta2 = new float[time.length];
		for(int i = 0; i < time.length; i++) {
			Theta1[i] = Bw1[0] * n1[i] + Bw2[0] * n2[i];
			Theta2[i] = Bw1[1] * n1[i] + Bw2[1] * n2[i];
		}
		
		writer(Theta1, Theta2);
	}
}
