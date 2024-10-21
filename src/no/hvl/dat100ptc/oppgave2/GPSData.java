package no.hvl.dat100ptc.oppgave2;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSData {

	private GPSPoint[] gpspoints;
	protected int antall = 0;

	public GPSData(int antall) 
	{
		gpspoints = new GPSPoint[antall];
		antall = 0;
		
	}

	public GPSPoint[] getGPSPoints() 
	{
		return this.gpspoints;
	}
	
	protected boolean insertGPS(GPSPoint gpspoint) 
	{
		if (antall < gpspoints.length) {
	        gpspoints[antall] = gpspoint;
	        antall++;
	        return true;
	    }
	    return false;
	}

	public boolean insert(String time, String latitude, String longitude, String elevation) 
	{

		GPSPoint gpspoint;
		
		gpspoint = new GPSPoint
				(
		    
		        GPSDataConverter.toSeconds(time),
		        Double.parseDouble(latitude),
		        Double.parseDouble(longitude),
		        Double.parseDouble(elevation)
		        );

		    return insertGPS(gpspoint);
	}

	public void print() {

		throw new UnsupportedOperationException(TODO.method());

		// TODO 
	}
}
