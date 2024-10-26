package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class Main 
{

	
	public static void main(String[] args) 
	{
		GPSPoint p1 = new GPSPoint(100, 60.3, 6.7, 50.0);
        GPSPoint p2 = new GPSPoint(200, 65.3, 5.7, 55.0);

        GPSData gpsData = new GPSData(2);

        gpsData.insertGPS(p1);
        gpsData.insertGPS(p2);
  
        gpsData.print();
	}
}
