package no.hvl.dat100ptc.oppgave5;

import javax.swing.JOptionPane;
import easygraphics.EasyGraphics;
import no.hvl.dat100ptc.oppgave4.GPSComputer;

public class ShowSpeed extends EasyGraphics {
    
    private static final long serialVersionUID = 1L;
    
    private static int MARGIN = 50;
    private static int BARHEIGHT = 100; 

    private GPSComputer gpscomputer;
    
    public ShowSpeed() {
        String filename = JOptionPane.showInputDialog("GPS data filnavn: ");
        gpscomputer = new GPSComputer(filename);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    public void run() {
        makeWindow("Speed profile", 
                2 * MARGIN + 
                2 * gpscomputer.speeds().length, 2 * MARGIN + BARHEIGHT);
        
        showSpeedProfile(MARGIN + BARHEIGHT);
    }
    
    public void showSpeedProfile(int ybase) {
        double[] speeds = gpscomputer.speeds();
        double averageSpeed = gpscomputer.averageSpeed();
        double maxSpeed = gpscomputer.maxSpeed();
        int x = MARGIN;

        if (maxSpeed > 0) {
            setColor(0, 255, 0);
            int avgHeight = (int)(averageSpeed * BARHEIGHT / (maxSpeed * 3.6));
            drawLine(MARGIN, ybase - avgHeight, MARGIN + speeds.length * 2, ybase - avgHeight);
        }

        for (int i = 0; i < speeds.length; i++) {
            int barHeight = (int)(speeds[i] * BARHEIGHT / (maxSpeed * 3.6));
            setColor(0, 0, 255);
            drawLine(x, ybase, x, ybase - barHeight);
            x += 2;
        }
    }
}
