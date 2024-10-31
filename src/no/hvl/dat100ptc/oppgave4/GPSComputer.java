package no.hvl.dat100ptc.oppgave4;

import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave2.GPSData;
import no.hvl.dat100ptc.oppgave2.GPSDataFileReader;
import no.hvl.dat100ptc.oppgave3.GPSUtils;

public class GPSComputer {
    
    private GPSPoint[] gpspoints;

    public GPSComputer(String filename) {
        GPSData gpsdata = GPSDataFileReader.readGPSFile(filename);
        gpspoints = gpsdata.getGPSPoints();
    }

    public GPSComputer(GPSPoint[] gpspoints) {
        this.gpspoints = gpspoints;
    }

    public GPSPoint[] getGPSPoints() {
        return this.gpspoints;
    }

    public double totalDistance() {
        double distance = 0;
        for (int i = 0; i < gpspoints.length - 1; i++) {
            distance += GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
        }
        return distance;
    }

    public double totalElevation() {
        double elevation = 0;
        for (int i = 0; i < gpspoints.length - 1; i++) {
            double heightDiff = gpspoints[i + 1].getElevation() - gpspoints[i].getElevation();
            if (heightDiff > 0) {
                elevation += heightDiff;
            }
        }
        return elevation;
    }

    public int totalTime() {
        return gpspoints[gpspoints.length - 1].getTime() - gpspoints[0].getTime();
    }

    public double[] speeds() {
        double[] speeds = new double[gpspoints.length - 1];
        for (int i = 0; i < gpspoints.length - 1; i++) {
            double distance = GPSUtils.distance(gpspoints[i], gpspoints[i + 1]);
            int timeDiff = gpspoints[i + 1].getTime() - gpspoints[i].getTime();
            speeds[i] = (timeDiff > 0) ? distance / timeDiff : 0;
        }
        return speeds;
    }

    public double maxSpeed() {
        double maxspeed = 0;
        double[] speeds = speeds();
        for (double speed : speeds) {
            if (speed > maxspeed) {
                maxspeed = speed;
            }
        }
        return maxspeed;
    }

    public double averageSpeed() {
        double totalDistance = totalDistance();
        double totalTime = totalTime();
        return (totalTime > 0) ? (totalDistance / totalTime) : 0;
    }

    public static final double MS = 2.23;

    public double kcal(double weight, int secs, double speed) {
        double met = 0;
        double speedmph = speed * MS;
        if (speedmph < 10) met = 4.0;
        else if (speedmph < 12) met = 6.0;
        else if (speedmph < 14) met = 8.0;
        else if (speedmph < 16) met = 10.0;
        else if (speedmph < 20) met = 12.0;
        else met = 16.0;
        return met * weight * (secs / 3600.0);
    }

    public double totalKcal(double weight) {
        double totalkcal = 0;
        double[] speeds = speeds();
        for (int i = 0; i < speeds.length; i++) {
            int secs = gpspoints[i + 1].getTime() - gpspoints[i].getTime();
            totalkcal += kcal(weight, secs, speeds[i]);
        }
        return totalkcal;
    }

    private static double WEIGHT = 80.0;

    public void displayStatistics() {
        int hrs = totalTime() / 3600;
        int mins = (totalTime() % 3600) / 60;
        int secs = totalTime() % 60;
        System.out.println("==============================================");
        System.out.printf("Total Time     :   %02d:%02d:%02d\n", hrs, mins, secs);
        System.out.printf("Total distance :      %.2f km\n", totalDistance() / 1000);
        System.out.printf("Total elevation:     %.2f m\n", totalElevation());
        System.out.printf("Max speed      :      %.2f km/t\n", maxSpeed() * 3.6);
        System.out.printf("Average speed  :      %.2f km/t\n", averageSpeed());
        System.out.printf("Energy         :     %.2f kcal\n", totalKcal(WEIGHT));
        System.out.println("==============================================");
    }
}
