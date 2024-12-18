package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {
		double max = da[0];
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		return max;
	}

	public static double findMin(double[] da) {
		double min = da[0];
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		return min;
	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {
		double[] latitudes = new double[gpspoints.length];
		for (int i = 0; i < gpspoints.length; i++) {
			latitudes[i] = gpspoints[i].getLatitude();
		}
		return latitudes;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {
		double[] longitudes = new double[gpspoints.length];
		for (int i = 0; i < gpspoints.length; i++) {
			longitudes[i] = gpspoints[i].getLongitude();
		}
		return longitudes;
	}

	private static final int R = 6371000;

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		double latitude1 = Math.toRadians(gpspoint1.getLatitude());
		double longitude1 = Math.toRadians(gpspoint1.getLongitude());
		double latitude2 = Math.toRadians(gpspoint2.getLatitude());
		double longitude2 = Math.toRadians(gpspoint2.getLongitude());

		double deltaphi = latitude2 - latitude1;
		double deltadelta = longitude2 - longitude1;

		double a = compute_a(latitude1, latitude2, deltaphi, deltadelta);
		double c = compute_c(a);

		return R * c;
	}

	private static double compute_a(double phi1, double phi2, double deltaphi, double deltadelta) {
		return Math.sin(deltaphi / 2) * Math.sin(deltaphi / 2) +
		       Math.cos(phi1) * Math.cos(phi2) *
		       Math.sin(deltadelta / 2) * Math.sin(deltadelta / 2);
	}

	private static double compute_c(double a) {
		return 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {
		int secs = gpspoint2.getTime() - gpspoint1.getTime();
		double distance = distance(gpspoint1, gpspoint2);
		return (secs > 0) ? distance / secs : 0;
	}

	public static String formatTime(int secs) {
	    int hh = secs / 3600;
	    int mm = (secs % 3600) / 60;
	    int ss = secs % 60;
	    return String.format("  %02d:%02d:%02d", hh, mm, ss);
	}

	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {
	    String str = String.format("%.2f", d);
	    int spaces = TEXTWIDTH - str.length();
	    return " ".repeat(Math.max(0, spaces)) + str;
	}
}
