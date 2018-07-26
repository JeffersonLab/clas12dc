// Filename: ReconTimeFunction.java
// Description: Plot the DC time function used in the reconstruction
// Author: Latif Kabir < latif@jlab.org >
// Created: Sat Aug 26 10:24:56 2017 (-0400)
// URL: latifkabir.github.io

package org.jlab.dc_calibration.benchmark;

import org.jlab.groot.ui.TCanvas;
import org.jlab.rec.dc.timetodistance.TimeToDistanceEstimator;
import org.jlab.dc_calibration.ui.CalibStyle;
import org.jlab.groot.base.GStyle;
import org.jlab.groot.data.GraphErrors;

import org.jlab.rec.dc.timetodistance.*;
import org.jlab.detector.calib.utils.DatabaseConstantProvider;
import org.jlab.utils.groups.IndexedTable;

public class ReconTimeFunction
{
	int SL_index;
	int sec_index;
	double distance;
	
	TimeToDistanceEstimator recFnc = new TimeToDistanceEstimator();
	
    public ReconTimeFunction(int SecIndex,int SLindex)
    {
    	sec_index = SecIndex;
    	SL_index = SLindex;
    	
    	 //Uncomment the following lines if you make the plot independently
		 //DatabaseConstantProvider provider = new DatabaseConstantProvider(2052,"default");
		 //IndexedTable table = provider.readTable("/calibration/dc/time_to_distance/time2dist");	 
		 //TableLoader.Fill(table);
	 }

    public GraphErrors getGraph(double bField, double angDegree, double minTime, double maxTime)
	{
    	GraphErrors gr = new GraphErrors();

    	for (double i = minTime; i< maxTime; i = i + 1.0)
		{			
    		distance = recFnc.interpolateOnGrid(bField, angDegree, i, sec_index, SL_index);
    		gr.addPoint(distance, i, 0, 0);
		}    	
    	return gr;
	}
    
     // --------------------> Run this call to main() from Emacs. Eclipse throws an exception for the loop used for a possible bug in groot <-------------- 
    public static void main(String[] args)
    {
    	CalibStyle.setStyle();
    	GStyle.getGraphErrorsAttributes().setMarkerSize(2);
		GStyle.getGraphErrorsAttributes().setTitleX("Distance [cm]");;
		GStyle.getGraphErrorsAttributes().setTitleY("Time [ns]");;
		
		TCanvas c1 = new TCanvas("c1", 800, 600);
		c1.setTitle("Distance (cm) vs Time (ns)");
		
		double maxTime = 200;
		double minTime = 0;
		
		int secIndex = 0;
		int slIndex = 3;
		double bField = 0.0;

		GStyle.getGraphErrorsAttributes().setTitle("Time (ns) vs Distance (cm) for S " + (secIndex + 1) + " SL " + (slIndex + 1) + " from Reconstruction");
		
		ReconTimeFunction recon = new ReconTimeFunction(secIndex, slIndex);

		// c1.draw(recon.getGraph(bField, 30, minTime, maxTime));		
		for(double angDegree = 0; angDegree < 30; angDegree += 5)
		{
		    GStyle.getGraphErrorsAttributes().setMarkerColor((int)(Math.abs(angDegree)/5) + 1);
			c1.draw(recon.getGraph(bField, angDegree, minTime, maxTime),"same");
		}

/*		 for( bField = 0.0; bField <= 1.5; bField += 0.5)
		 {
		     for(double angDegree = 0; angDegree < 30; angDegree += 5)
		     {
		 	GStyle.getGraphErrorsAttributes().setMarkerColor((int)(angDegree/5) + 1);
		 	c1.draw(recon.getGraph(bField, angDegree, minTime, maxTime),"same");
		     }		
		 }
*/
	}
}
