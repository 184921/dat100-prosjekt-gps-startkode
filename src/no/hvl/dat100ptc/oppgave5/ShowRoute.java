package no.hvl.dat100ptc.oppgave5;

import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave1.GPSPoint;
import no.hvl.dat100ptc.oppgave3.GPSUtils;
import no.hvl.dat100ptc.oppgave4.GPSComputer;
import javax.swing.JOptionPane;

public class ShowRoute extends EasyGraphics {
	
	private static final long serialVersionUID = 1L;

    private static final int MARGIN = 50;
    private static final int MAPXSIZE = 800;
    private static final int MAPYSIZE = 800;
    private GPSPoint[] gpspoints;
    private GPSComputer gpscomputer;
    private double minlon, minlat, maxlon, maxlat;
    private double xstep, ystep;

    public ShowRoute() {
        String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
        gpscomputer = new GPSComputer(filename);
        gpspoints = gpscomputer.getGPSPoints();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void run() {
        makeWindow("Route", MAPXSIZE + 2 * MARGIN, MAPYSIZE + 2 * MARGIN);
        minlon = GPSUtils.findMin(GPSUtils.getLongitudes(gpspoints));
        minlat = GPSUtils.findMin(GPSUtils.getLatitudes(gpspoints));
        maxlon = GPSUtils.findMax(GPSUtils.getLongitudes(gpspoints));
        maxlat = GPSUtils.findMax(GPSUtils.getLatitudes(gpspoints));
        xstep = scale(MAPXSIZE, minlon, maxlon);
        ystep = scale(MAPYSIZE, minlat, maxlat);
        showRouteMap(MARGIN + MAPYSIZE);
        replayRoute(MARGIN + MAPYSIZE);
        showStatistics();
    }

    public double scale(int maxsize, double minval, double maxval) {
        return maxsize / (Math.abs(maxval - minval));
    }

    public void showRouteMap(int ybase) {
        for (int i = 1; i < gpspoints.length; i++) {
            int x1 = MARGIN + (int) ((gpspoints[i - 1].getLongitude() - minlon) * xstep);
            int y1 = ybase - (int) ((gpspoints[i - 1].getLatitude() - minlat) * ystep);
            int x2 = MARGIN + (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
            int y2 = ybase - (int) ((gpspoints[i].getLatitude() - minlat) * ystep);
            drawLine(x1, y1, x2, y2);
        }
    }

    public void showStatistics() {
        int TEXTDISTANCE = 20;
        setColor(0, 0, 0);
        setFont("Courier", 12);
        drawString("Total Time: " + gpscomputer.totalTime() + " seconds", MARGIN, TEXTDISTANCE);
        drawString("Total Distance: " + gpscomputer.totalDistance() + " meters", MARGIN, 2 * TEXTDISTANCE);
        drawString("Total Elevation: " + gpscomputer.totalElevation() + " meters", MARGIN, 3 * TEXTDISTANCE);
        drawString("Max Speed: " + gpscomputer.maxSpeed() + " km/h", MARGIN, 4 * TEXTDISTANCE);
        drawString("Average Speed: " + gpscomputer.averageSpeed() + " km/h", MARGIN, 5 * TEXTDISTANCE);
        drawString("Energy: " + gpscomputer.totalKcal(80) + " kcal", MARGIN, 6 * TEXTDISTANCE);
    }

    public void replayRoute(int ybase) {
        int x1, y1, x2, y2;
        setSpeed(1);
        x1 = MARGIN + (int) ((gpspoints[0].getLongitude() - minlon) * xstep);
        y1 = ybase - (int) ((gpspoints[0].getLatitude() - minlat) * ystep);
        int circle = fillCircle(x1, y1, 5);
        for (int i = 1; i < gpspoints.length; i++) {
            x2 = MARGIN + (int) ((gpspoints[i].getLongitude() - minlon) * xstep);
            y2 = ybase - (int) ((gpspoints[i].getLatitude() - minlat) * ystep);
            moveCircle(circle, x2, y2);
            pause(100);
        }
    }
}
