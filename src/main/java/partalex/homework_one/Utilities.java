package partalex.homework_one;

public class Utilities {
	public Utilities() {
	}

	public static double clamp(double value, double min, double max) {
		double result = value;
		if (value < min) {
			result = min;
		}

		if (result > max) {
			result = max;
		}

		return result;
	}

	public static boolean inBetween(double value, double min, double max) {
		return value >= min && value <= max;
	}

	public static boolean areEqual(double value0, double value1, double epsilon) {
		return Math.abs(value0 - value1) <= epsilon;
	}
}
