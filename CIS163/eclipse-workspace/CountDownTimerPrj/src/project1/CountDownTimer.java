package project1;

import java.io.*;
import java.util.*;

/**********************************************************
 * Creates a timer that counts down until the new year and has other
 * functionality
 * 
 * @author AJ Natzic
 * @version Winter 2018
 **********************************************************/
public class CountDownTimer {

	/** current hours on the CountDownTimer object */
	private int hours = 0;

	/** current minutes on the CountDownTimer object */
	private int minutes = 0;

	/** current seconds on the CountDownTimer object */
	private int seconds = 0;

	/** boolean that, when true, will suspend all add/sub methods **/
	public static boolean suspended;

	/**
	 * Default Constructor that sets the CountDownTimer to zero and "suspended"
	 * boolean to false
	 */
	public CountDownTimer() {
		hours = 0;
		minutes = 0;
		seconds = 0;
		suspended = false;
	}

	/**
	 * Constructor that initializes the instance variables with the given values
	 * 
	 * @param hours
	 *            Given hours of the CountDownTimer
	 * @param minutes
	 *            Given minutes of the CountDownTimer
	 * @param seconds
	 *            Given seconds of the CountDownTimer
	 */
	public CountDownTimer(int hours, int minutes, int seconds) {
		if (hours > 24 || hours < 0)
			throw new IllegalArgumentException();
		else if (minutes >= 60 || minutes < 0)
			throw new IllegalArgumentException();
		else if (seconds >= 60 || seconds < 0)
			throw new IllegalArgumentException();

		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	/**
	 * Constructor that initializes the instance variables with the given values
	 * 
	 * @param minutes
	 *            Given minutes of the CountDownTimer
	 * @param seconds
	 *            Given seconds of the CountDownTimer
	 */
	public CountDownTimer(int minutes, int seconds) {
		if (minutes >= 60 || minutes < 0)
			throw new IllegalArgumentException();
		else if (seconds >= 60 || seconds < 0)
			throw new IllegalArgumentException();

		hours = 0;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	/**
	 * Constructor that initializes the instance variables with the given values
	 * 
	 * @param seconds
	 *            Given seconds of the CountDownTimer
	 */
	public CountDownTimer(int seconds) {
		if (seconds >= 60 || seconds < 0)
			throw new IllegalArgumentException();

		hours = 0;
		minutes = 0;
		this.seconds = seconds;
	}

	/**
	 * Constructor that initializes the instance variables with a given object to
	 * the "other" CountDownTimer object
	 * 
	 * @param other
	 *            "other" CountDownTimer object
	 */
	public CountDownTimer(CountDownTimer other) {
		if (other.hours > 24 || hours < 0)
			throw new IllegalArgumentException();
		else if (other.minutes >= 60 || minutes < 0)
			throw new IllegalArgumentException();
		else if (other.seconds >= 60 || seconds < 0)
			throw new IllegalArgumentException();

		this.hours = other.hours;
		this.minutes = other.minutes;
		this.seconds = other.seconds;
	}

	/**
	 * A constructor that accepts the string startTime as a parameter time in the
	 * format "11:30:56" where 11 is hours, 30 is minutes, and 56 is seconds. OR for
	 * 11:56 where 11 is minutes and 56 is seconds OR for 22 where 22 is seconds.
	 * Many other combinations are also taken into consideration such as strings
	 * like "1:1:1" or "15:20"
	 * 
	 * @param startTime
	 *            A parameter for time in the standard colon format stated above
	 */
	public CountDownTimer(String startTime) {
		/*************************************************************
		 * if string startTime has AT LEAST ONE colon and is NOT empty
		 *************************************************************/
		if (startTime.indexOf(':') != -1 && startTime.length() != 0) {
			/**************************************************
			 * if string startTime has TWO COLONS e.g. ##:##:##
			 **************************************************/
			if (startTime.indexOf(':') != startTime.lastIndexOf(':')) {
				if (startTime.indexOf(':') == 2) { // if hours is two digits e.g. ##:##:##
					hours = Integer.parseInt(startTime.substring(0, 2));
					if (startTime.lastIndexOf(':') == 5) { // if minutes is two digits e.g. ##:##:##
						minutes = Integer.parseInt(startTime.substring(3, 5));
						if (startTime.length() == 8) { // if seconds is two digits
							seconds = Integer.parseInt(startTime.substring(6, 8));
						} else { // seconds is not two digits, must be one digit
							seconds = Integer.parseInt(startTime.substring(6, 7));
						}
					} else { // minutes is not two digits, must be one digit
						minutes = Integer.parseInt(startTime.substring(3, 5));
						if (startTime.length() == 7) { // if seconds is two digit
							seconds = Integer.parseInt(startTime.substring(5, 7));
						} else { // seconds is not two digits, must be one digit
							seconds = Integer.parseInt(startTime.substring(5, 6));
						}
					}
				} else { // hours is not two digits it must be one digit e.g. #:##:##
					hours = Integer.parseInt(startTime.substring(0, 1));
					if (startTime.lastIndexOf(':') == 4) { // if minutes is two digits e.g. #:##:##
						minutes = Integer.parseInt(startTime.substring(2, 4));
						if (startTime.length() == 7) { // if seconds is two digits
							seconds = Integer.parseInt(startTime.substring(5, 7));
						} else { // seconds is not two digits, must be one digit
							seconds = Integer.parseInt(startTime.substring(5, 6));
						}
					} else { // minutes is not two digits, must be one digit
						minutes = Integer.parseInt(startTime.substring(2, 3));
						if (startTime.length() == 6) { // if seconds is two digits
							seconds = Integer.parseInt(startTime.substring(4, 6));
						} else { // seconds is not two digits, must be one digit
							seconds = Integer.parseInt(startTime.substring(4, 5));
						}
					}
				}
			}
			/***************************************************
			 * if string startTime only has one colon e.g. ##:##
			 ***************************************************/
			else if (startTime.indexOf(':') == startTime.lastIndexOf(':')) {
				if (startTime.charAt(2) == ':') { // if string startTime has two digits for minutes e.g. "##:#" or
													// "##:##"
					hours = 0;
					minutes = Integer.parseInt(startTime.substring(0, 2));
					if (startTime.length() == 4) { // determines if seconds is one digit
						seconds = Integer.parseInt(startTime.substring(3, 4));
					} else { // since seconds is not one digit it must be two digits
						seconds = Integer.parseInt(startTime.substring(3, 5));
					}
				} else if (startTime.charAt(1) == ':') { // if string startTime has one digit for minutes e.g. "#:#" or
															// "#:##"
					minutes = Integer.parseInt(startTime.substring(0, 1));
					if (startTime.length() == 3) { // determines if seconds is one digit
						seconds = Integer.parseInt(startTime.substring(0, 1));
					} else { // since seconds is not one digit it must be two digits
						seconds = Integer.parseInt(startTime.substring(0, 2));
					}
				}
			}
		}
		/***********************************************************************
		 * if string startTime DOES NOT have a colon but it is NOT empty e.g. ##
		 ***********************************************************************/
		else if (startTime.indexOf(':') == -1 && startTime.length() != 0) {
			minutes = 0;
			if (startTime.length() == 2) { // checks if there is 2 digits for seconds
				seconds = Integer.parseInt(startTime.substring(0, 1));
			} else { // since seconds is not two digits, it must be one digit
				seconds = Integer.parseInt(startTime.substring(0, 1));
			}
		}
		/******************************
		 * if string startTime is EMPTY
		 ******************************/
		else if (startTime.length() == 0) {
			hours = 0;
			minutes = 0;
			seconds = 0;
		}

		// throws exceptions if illegal input is given
		if (startTime.length() > 8)
			throw new IllegalArgumentException();
		if (hours > 24 || hours < 0)
			throw new IllegalArgumentException();
		else if (minutes >= 60 || minutes < 0)
			throw new IllegalArgumentException();
		else if (seconds >= 60 || seconds < 0)
			throw new IllegalArgumentException();

	}

	/**
	 * Method that returns true if the "this" CountDownTimer object is exactly the
	 * same as the other CountDownTimer object
	 */
	public boolean equals(Object other) {
		if (hours > 24 || hours < 0)
			throw new IllegalArgumentException();
		else if (minutes >= 60 || minutes < 0)
			throw new IllegalArgumentException();
		else if (seconds >= 60 || seconds < 0)
			throw new IllegalArgumentException();

		if (compareTo((CountDownTimer) other) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Static method that returns true if CountDownTimer object t1 is exactly the
	 * same as CountDownTimer object t2
	 * 
	 * @param t1
	 *            The first CountDownTimer object
	 * @param t2
	 *            The second CountDownTimer object
	 * @return
	 */
	public static boolean equals(CountDownTimer t1, CountDownTimer t2) {
		if (t1.hours > 24 || t1.hours < 0)
			throw new IllegalArgumentException();
		else if (t2.hours > 24 || t2.hours < 0)
			throw new IllegalArgumentException();
		else if (t1.minutes >= 60 || t1.minutes < 0)
			throw new IllegalArgumentException();
		else if (t2.minutes >= 60 || t2.minutes < 0)
			throw new IllegalArgumentException();
		else if (t1.seconds >= 60 || t1.seconds < 0)
			throw new IllegalArgumentException();
		else if (t2.seconds >= 60 || t2.seconds < 0)
			throw new IllegalArgumentException();

		if (t1.compareTo(t2) == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * A method that returns 1, -1 or 0 depending on whether the "this"
	 * CountDownTimer object is greater than, less than, or equal to the "other"
	 * CountDownTimer object
	 * 
	 * @param other
	 *            The "other" CountDownTimer object
	 * @return 1 if "this" CountDownTimer object is greater than the "other"
	 *         CountDownTimer
	 * @return -1 if "this" CountDownTimer is less than the "other" CountDownTimer
	 * @return 0 if "this" CountDownTimer object and "other" CountDownTimer are
	 *         exactly the same
	 */
	public int compareTo(CountDownTimer other) {
		if (hours > 24 || hours < 0)
			throw new IllegalArgumentException();
		else if (minutes >= 60 || minutes < 0)
			throw new IllegalArgumentException();
		else if (seconds >= 60 || seconds < 0)
			throw new IllegalArgumentException();
		else if (other.hours > 24 || other.hours < 0)
			throw new IllegalArgumentException();
		else if (other.minutes >= 60 || other.minutes < 0)
			throw new IllegalArgumentException();
		else if (other.seconds >= 60 || other.seconds < 0)
			throw new IllegalArgumentException();

		double totalThis = 0.0;
		double totalOther = 0.0;

		totalThis = (hours * 3600) + (minutes * 60) + seconds; // converts "this" CountDownTimer to seconds
		totalOther = (other.hours * 3600) + (other.minutes * 60) + other.seconds; // converts "other" CountDownTimer to
																					// seconds
		if (totalThis > totalOther)
			return 1;
		else if (totalThis < totalOther)
			return -1;
		else
			return 0;
	}

	/**
	 * Static method that returns 1, -1, or 0 depending on whether CountDownTimer
	 * object t1 is greater than, less than, or equal to CountDownTimer object t2
	 * 
	 * @param t1
	 *            the first CountDownTimer object
	 * @param t2
	 *            the second CountDownTimer object
	 * @return 1 if t1 is greater than t2 object
	 * @return 0 if t1 is equal to t2 object
	 * @return -1 if t1 is less than t2 object
	 */
	public static int compareTo(CountDownTimer t1, CountDownTimer t2) {
		if (t1.hours > 24 || t1.hours < 0)
			throw new IllegalArgumentException();
		else if (t1.minutes >= 60 || t1.minutes < 0)
			throw new IllegalArgumentException();
		else if (t1.seconds >= 60 || t1.seconds < 0)
			throw new IllegalArgumentException();
		else if (t2.hours > 24 || t2.hours < 0)
			throw new IllegalArgumentException();
		else if (t2.minutes >= 60 || t2.minutes < 0)
			throw new IllegalArgumentException();
		else if (t2.seconds >= 60 || t2.seconds < 0)
			throw new IllegalArgumentException();

		double totalT1 = 0.0;
		double totalT2 = 0.0;

		totalT1 = (t1.hours * 3600) + (t1.minutes * 60) + t1.seconds; // converts "this" CountDownTimer to seconds
		totalT2 = (t2.hours * 3600) + (t2.minutes * 60) + t2.seconds; // converts "other" CountDownTimer to
																		// seconds
		if (totalT1 > totalT2)
			return 1;
		else if (totalT1 < totalT2)
			return -1;
		else
			return 0;
	}

	/**
	 * Method that subtracts seconds from "this" CountDownTimer object
	 * 
	 * @param secondsSub
	 *            amount of seconds being subtracted from "this" CountDownTimer
	 *            object
	 */
	public void sub(int secondsSub) {
		if (!suspended) {
			if (secondsSub < 0)
				throw new IllegalArgumentException();

			double totalThis = 0.0;

			totalThis = (hours * 3600) + (minutes * 60) + seconds; // converts "this" CountDownTimer to seconds

			totalThis = totalThis - secondsSub; // subtracts parameter secondsSub from total time

			hours = (int) totalThis / 3600;
			minutes = (int) (((totalThis / 3600.0) - hours) * 60.0);
			seconds = (int) Math.round((((((totalThis / 3600.0) - hours) * 60.0) - minutes) * 60.0));
		}
	}

	/**
	 * Method that subtracts "other" CountDownTimer object from "this"
	 * CountDownTimer object
	 * 
	 * @param other
	 *            "other" CountDownTimer object
	 */
	public void sub(CountDownTimer other) {
		if (!suspended) {
			if (other.hours > 24 || other.hours < 0)
				throw new IllegalArgumentException();
			else if (other.minutes >= 60 || other.minutes < 0)
				throw new IllegalArgumentException();
			else if (other.seconds >= 60 || other.seconds < 0)
				throw new IllegalArgumentException();

			double totalThis = 0.0;
			double totalOther = 0.0;

			totalThis = (hours * 3600) + (minutes * 60) + seconds; // converts "this" CountDownTimer to seconds
			totalOther = (other.hours * 3600) + (other.minutes * 60) + other.seconds; // converts "other" CountDownTimer
																						// to
																						// seconds

			totalThis = totalThis - totalOther; // subtracts the "other" CountDownTimer from "this" CountDownTimer

			hours = (int) totalThis / 3600;
			minutes = (int) (((totalThis / 3600.0) - hours) * 60.0);
			seconds = (int) Math.round((((((totalThis / 3600.0) - hours) * 60.0) - minutes) * 60.0));
		}
	}

	/**
	 * Method that decrements "this" CountDownTimer object by one second
	 */
	public void dec() {
		if (!suspended) {
			sub(1);
		}
	}

	/**
	 * Method that adds given number of seconds to "this" CountDownTimer object
	 * 
	 * @param secondsAdd
	 *            positive number of seconds
	 */
	public void add(int secondsAdd) {
		if (!suspended) {
			if (secondsAdd < 0)
				throw new IllegalArgumentException();

			double totalThis = 0.0;

			totalThis = (hours * 3600) + (minutes * 60) + seconds; // converts "this" CountDownTimer to seconds

			totalThis = totalThis + secondsAdd; // adds parameter secondsAdd from total time

			hours = (int) totalThis / 3600;
			minutes = (int) (((totalThis / 3600.0) - hours) * 60.0);
			seconds = (int) Math.round((((((totalThis / 3600.0) - hours) * 60.0) - minutes) * 60.0));
		}
	}

	/**
	 * Method that adds "other" CountDownTimer object to "this" CountDownTimer
	 * object
	 * 
	 * @param other
	 *            "other" CountDownTimer object
	 */
	public void add(CountDownTimer other) {
		if (!suspended) {
			if (other.hours > 24 || other.hours < 0)
				throw new IllegalArgumentException();
			else if (other.minutes >= 60 || other.minutes < 0)
				throw new IllegalArgumentException();
			else if (other.seconds >= 60 || other.seconds < 0)
				throw new IllegalArgumentException();

			double totalThis = 0.0;
			double totalOther = 0.0;

			totalThis = (hours * 3600) + (minutes * 60) + seconds; // converts "this" CountDownTimer to seconds
			totalOther = (other.hours * 3600) + (other.minutes * 60) + other.seconds; // converts "other" CountDownTimer
																						// to
																						// seconds

			totalThis = totalThis + totalOther; // adds the "other" CountDownTimer to "this" CountDownTimer

			hours = (int) totalThis / 3600;
			minutes = (int) (((totalThis / 3600.0) - hours) * 60.0);
			seconds = (int) Math.round((((((totalThis / 3600.0) - hours) * 60.0) - minutes) * 60.0));
		}
	}

	/**
	 * Method that increments one second to "this" CountDownTimer object
	 */
	public void inc() {
		if (!suspended) {
			add(1);
		}
	}

	/**
	 * Method that returns the standard formatted time with colons in-between (e.g.
	 * 11:56:33)
	 * 
	 * @return formatTime the formatted time
	 */
	public String toString() {
		if (hours > 24 || hours < 0)
			throw new IllegalArgumentException();
		else if (minutes >= 60 || minutes < 0)
			throw new IllegalArgumentException();
		else if (seconds >= 60 || seconds < 0)
			throw new IllegalArgumentException();

		String formatTime = "";
		if (minutes < 10) {
			if (seconds < 10) {
				formatTime = hours + ":0" + minutes + ":0" + seconds;
			} else {
				formatTime = hours + ":0" + minutes + ":" + seconds;
			}
		} else if (minutes >= 10) {
			if (seconds < 10) {
				formatTime = hours + ":" + minutes + ":0" + seconds;

			} else {
				formatTime = hours + ":" + minutes + ":" + seconds;
			}
		}
		return formatTime;
	}

	/**
	 * Method that saves the �this� CountDownTimer to a file
	 * 
	 * @param filename
	 *            the name of the file
	 */
	public void save(String fileName) {
		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
		} catch (Exception e) {
			throw new IllegalArgumentException();
		}
		out.println(hours);
		out.println(minutes);
		out.println(seconds);
		out.close();

	}

	/**
	 * Method that loads the "this" CountDownTimer from a file
	 * 
	 * @param filename
	 *            the name of the file
	 */
	public void load(String fileName) {
		try {
			// open the data file
			Scanner fileReader = new Scanner(new File(fileName));
			// read one integer
			hours = fileReader.nextInt();
			System.out.println(hours);

			minutes = fileReader.nextInt();
			System.out.println(minutes);

			seconds = fileReader.nextInt();
			System.out.println(seconds);

			fileReader.close();
		}
		// problem reading the file
		catch (Exception error) {
			throw new IllegalArgumentException();
		}

	}

	/**
	 * Method that prevents any add/sub method from being used
	 * 
	 * @param flag
	 *            boolean value that turns add/sub methods "on" and "off" based on
	 *            true or false value, respectively
	 */
	public static void suspend(Boolean flag) {
		suspended = flag;
	}

	/**
	 * This main method tests all the methods in the class
	 */
	public static void main(String[] args) {

		System.out.println("------Beginning testing-----");
		System.out.println("");

		// tests the string method
		CountDownTimer s = new CountDownTimer("2:59:8");
		System.out.println("Time: " + s);

		s = new CountDownTimer("20:8");
		System.out.println("Time: " + s);

		s = new CountDownTimer("8");
		System.out.println("Time: " + s);

		// tests CountDownTimer(integer, integer, integer) constructor
		CountDownTimer s1 = new CountDownTimer(24, 2, 20);
		System.out.println("Time: " + s1);

		// tests subtraction(integer) method
		s1.sub(1000);
		System.out.println("Time: " + s1);

		// tests add(integer) method
		s1.add(1000);
		System.out.println("Time: " + s1);

		CountDownTimer s2 = new CountDownTimer(13, 10, 19);
		s2.sub(100);

		// tests dec() method
		for (int i = 0; i < 4000; i++)
			s2.dec();

		System.out.println("Time: " + s2);

		// tests inc() method
		for (int i = 0; i < 2000; i++) {
			s2.inc();
		}

		System.out.println("Time: " + s2);
		s1.sub(s2);

		System.out.println("Time: " + s1);
		s1.add(s2);

		System.out.println("Time: " + s1);

		// tests toString() method
		System.out.println("Time: " + s1.toString());

		s2 = new CountDownTimer(s1); // assigns s1 to s2, so they are equal
		// tests equals(object, object) method
		if (equals(s1, s2)) {
			System.out.println("s1 and s2 are equal!");
		} else {
			System.out.println("ERROR: s1 and s2 are NOT equal!");
		}

		// tests equals(object) method
		if (s1.equals(s2)) {
			System.out.println("s1 and s2 are STILL equal!!");
		} else {
			System.out.println("ERROR: s1 and s2 are STILL NOT equal!!");
		}

		s1.inc(); // s1 should be greater than s2 now

		// tests compareTo(CountDownTimer, CountDownTimer) method
		if (compareTo(s1, s2) > 0) {
			System.out.println("s1 is now more than s2!");
		} else {
			System.out.println("ERROR: s1 is NOT more than s2!");
		}

		// tests compareTo(CountDownTimer) method
		if (s1.compareTo(s2) > 0) {
			System.out.println("s1 is STILL more than s2!!");
		} else {
			System.out.println("ERROR: s1 is STILL NOT more than s2!!");
		}

		// tests CountDownTimer(integer, integer) method
		s1 = new CountDownTimer(2, 2);
		System.out.println("Time: " + s1);

		// tests CountDownTimer(integer) method
		s1 = new CountDownTimer(59);
		System.out.println("Time: " + s1);

		System.out.println("");
		System.out.println("-----Testing Completed-----");
	}
}
