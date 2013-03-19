package prog03;

import prog02.*;

public class Main {
	/** Use this variable to store the result of each call to fib. */
	public static double fibn;
	static UserInterface ui = new GUI();

	/**
	 * Determine the time in milliseconds it takes to calculate the n'th
	 * Fibonacci number averaged over ncalls calls.
	 * 
	 * @param fib
	 *            an object that implements the Fib interface
	 * @param n
	 *            the index of the Fibonacci number to calculate
	 * @param ncalls
	 *            the number of calls to average over
	 * @return the average time per call
	 */
	static double time(Fib fib, int n, int ncalls) {
		// Get the current time in milliseconds. This is a static
		// method in the System class. Actually, it is the time in
		// milliseconds since midnight, January 1, 1970. What type
		// should you use to store the current time? Why?
		long start = System.currentTimeMillis();
		// Calculate the n'th Fibonacci number ncalls times. Each
		// time store the result in fibn.
		for (int i = 0; i < ncalls; i++) {
			fibn = fib.fib(n);
		}
		// Get the current time in milliseconds.
		long end = System.currentTimeMillis();

		return (double) (end - start) / ncalls;
		// Using ncalls and the starting and ending times, calculate
		// the average time per call and return it. Make sure to use
		// double precision arithmetic for the calculation by casting
		// it to double.
	}

	/**
	 * Determine the time in milliseconds it takes to to calculate the n'th
	 * Fibonacci number ACCURATE TO THREE SIGNIFICANT FIGURES.
	 * 
	 * @param fib
	 *            an object that implements the Fib interface
	 * @param n
	 *            the index of the Fibonacci number to calculate
	 * @return the time it it takes to compute the n'th Fibonacci number
	 */
	public static double time(Fib fib, int n) {
		// Since the clock is only accurate to the millisecond, we
		// need to use a value of ncalls such that the total time is a
		// second. First we need to figure that value of ncalls.
		int ncalls = 1;
		double time = 0.0;
		while (time * ncalls <= 1000) {
			time = time(fib, n, ncalls);
			ncalls *= 2;
		}
		// Starting with ncalls=1, calculate the total time, which is
		// ncalls times the average time. Use the method
		// time(fib,n,ncalls) method to get the average time. Keep
		// multiplying ncalls times 2 until the total time is more
		// than a second.

		// Return the average time for that value of ncalls. As a
		// test, print out ncalls times this average time to make sure
		// it is more than a second.
		return time;
	}

	/**
	 * Calculate fibonacci execution time. Calculate constant. Display to user.
	 * If this is not the first input, estimate time for n.
	 * @param fib
	 *            an object that implements the Fib interface
	 */
	public static void doExperiments(Fib fib) {
		//UserInterface ui = new GUI();
		String n = ui.getInfo("Please enter n");
		double c = 0;
		double fibTime;
		while (n != null) {
			try {
				int fibNumber;
				String[] commands = { "Yes", "No" };
				boolean isHour = false;

				fibNumber = Integer.parseInt(n);

				if (c > 0) {
					double timeEstimate = c * fib.o(fibNumber);
					ui.sendMessage("Estimated time for fib(" + fibNumber
							+ ") = " + timeEstimate + " milliseconds");
					if (timeEstimate > 3600000) {
						isHour = true;
					}
					if (isHour) {
						ui.sendMessage("The estimated time is over 1 hour.  Do you wish to proceed?");
						int i = ui.getCommand(commands);
						switch (i) {
						case 0:
							isHour = false;
							break;
						case 1:
							break;
						default:
							break;
						}

					}

				}
				if (!isHour) {
					fibTime = time(fib, fibNumber);
					ui.sendMessage("fib(" + fibNumber + ") = "
							+ fib.fib(fibNumber) + " in " + fibTime
							+ " milliseconds");
					c = fibTime / fib.o(fibNumber);
					ui.sendMessage("New constant = " + c);
				}
			} catch (NumberFormatException e) {
				ui.sendMessage(n + " is not a number");
			}
			n = ui.getInfo("Please enter n");
		}

	}

	/**
	 * Processes user's commands on a fib object.
	 */
	public static void doExperiments() {
		//UserInterface ui = new GUI();
		Fib fib;
		String[] commands = { "Linear Fib", "Logarithmic Fib", "Constant Fib",
				"Exponential Fib" };
		int c = ui.getCommand(commands);
		switch (c) {
		case 0:
			fib = new LinearFib();
			doExperiments(fib);
			break;
		case 1:
			fib = new LogFib();
			doExperiments(fib);
			break;
		case 2:
			fib = new ConstantFib();
			doExperiments(fib);
			break;
		case 3:
			fib = new ExponentialFib();
			doExperiments(fib);
			break;

		}

	}

	public static void main(String[] args) {
		doExperiments();

		/*
		 * // Create (Exponential ti`me) Fib object and test it. Fib efib = new
		 * ConstantFib(); System.out.println(efib); for (int i = 0; i < 11; i++)
		 * System.out.println(i + " " + efib.fib(i));
		 * 
		 * // Determine running time for n1 = 20 and print it out. int n1 = 20;
		 * double time1 = time(efib, n1, 1000); System.out.println("n1 " + n1 +
		 * " time1 " + time1); // Calculate constant: time = constant times
		 * O(n). double c = time1 / efib.o(n1); System.out.println("c " + c); //
		 * Estimate running time for n2=30. int n2 = 30; double time2est = c /
		 * efib.o(n2); System.out.println("n2 " + n2 + " estimated time " +
		 * time2est);
		 * 
		 * // Calculate actual running time for n2=30. double time2 = time(efib,
		 * n2); System.out.println("n2 " + n2 + " actual time " + time2);
		 */

	}
}
