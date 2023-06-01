# DoublePendulumSimulator

It is well known that Python has extremely powerful libraries to simulate physical systems in direct contrast with the likes of Java. Therefore, I wondered if it would be possible to create a project that simulates a very special system under specific circumstances, the Double Pendulum. This physical simulation is constrained by the initial values of the deflection of both pendula to be very small (to support the small angle approximation). It makes use of mass and response tensors in order to calculate the values of theta and stores them in .dat files wherefrom one can use a plotting software to plot these values.

The DriverMain class is a specific example with specific conditions that makes use of the aforementioned properties. Users can, of course, choose to store it in a different file format, lengths, masses, etc by conveniently altering the DriverMain class. Additionally, the value of g has been hard coded as 9.8, although that can be changed in the datafields of the DoublePendulumDriver class. 

One might also notice that there is an IntegralCalculator parent class with subclasses capable of numerically calculating integrals and ordinary differential equations using Simpson's method(IntegratorSV, single variable integrals), Euler's Method and Finite Difference Method respectively (the last two are present in the ODESolver class). Originally, the idea was to numerically solve the values of the "normal modes" and hence calculate the theta values. However in lieu of issues with normalization (or so I assume) the numerical calculation doesn't exactly work and I had to resort to implicitly assuming that the normal modes are cosine functions. Making use of the FDM Solver does render an oscillatory solution, but the amplitude increases exponentially, telling us that it must be correct to a certain extent.

There also exists a QuadraticSolver class that is used to find the eigenfrequencies from the secular equation, but there is no way of handling cases where the eigenfrequency is imaginary (meaning the theta value is exponential, usually a case for larger initial deflections). This is technically not an issue for this specific use case, but I do intend on making this a more general simulator capable of handling chaotic simulations, and therefore fixing would be required in this class to handle complex cases.

Presently I am also working on making a visual demonstration of this system using the PApplet class in order to bring out the simulation beyond data and graphs, into real time visualization of the system's changes.

![Theta-Plot](https://github.com/Emperor963/DoublePendulumSimulator/assets/83712724/0af8a8e0-cc89-4aa0-9b62-ef4e2967159c)

This is a GNUPlot of the theta values for the current values in DriverMain

Here is a graphic representation using pygame captured with a phone camera. Credits for the GUI using the Theta files goes to Soummadeep Sengupta (@Bumblemoo)

https://github.com/Emperor963/DoublePendulumSimulator/assets/83712724/0caafe0b-83c4-4d2d-a076-a3601d8eaf45

The above GUI refers to a particular mass ratio of 15:1 between the two pendula with equal lengths. The angles of the pendula have been exaggerated slightly in order for the simulation to look visual. However, in practice, very large angles would break down the small angle approximations made in this simulator and the behavior would mimic real life with certain errors.

