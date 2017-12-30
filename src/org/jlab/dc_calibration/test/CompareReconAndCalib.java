// Filename: ReconTimeFunction.java
// Description: Plot the DC time function used in the reconstruction
// Author: Latif Kabir < latif@jlab.org >
// Created: Sat Aug 26 10:24:56 2017 (-0400)
// URL: latifkabir.github.io

package org.jlab.dc_calibration.test;

import org.jlab.groot.ui.TCanvas;
import org.jlab.rec.dc.timetodistance.TableLoader;
import org.jlab.rec.dc.timetodistance.TimeToDistanceEstimator;
//import org.jlab.rec.dc.CalibrationConstantsLoader;
import org.jlab.dc_calibration.io.ReadT2DparsFromCCDB;
import org.jlab.dc_calibration.ui.CalibStyle;
import org.jlab.groot.base.GStyle;
import org.jlab.groot.data.GraphErrors;

import org.jlab.dc_calibration.test.CalibTimeFunction;
import org.jlab.dc_calibration.test.ReconTimeFunction;

public class CompareReconAndCalib
{    
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
	double min_ang_degree = 20;
	double max_ang_degree = 20;
		
	GStyle.getGraphErrorsAttributes().setTitle("Time (ns) vs Distance (cm) for S " + (secIndex + 1) + " SL " + (slIndex + 1) + " Reconstruction (dotted line) vs Calibration (solid line) for " + min_ang_degree +" degree and B = 0 T");
	// GStyle.getGraphErrorsAttributes().setTitle("Time (ns) vs Distance (cm) for S " + (secIndex + 1) + " SL " + (slIndex + 1) + " Reconstruction (dotted line) vs Calibration (solid line) for 10 degree and B= 0.0 , 0.5, 1.0, 1.5 T");
		
	ReconTimeFunction recon = new ReconTimeFunction(secIndex, slIndex);

	// c1.draw(recon.getGraph(bField, 30, minTime, maxTime));		
	// for(double angDegree = 0; angDegree <= 0; angDegree += 5)
	// {
	//     GStyle.getGraphErrorsAttributes().setMarkerColor((int)(angDegree/5) + 2);
	// 	c1.draw(recon.getGraph(bField, angDegree, minTime, maxTime),"same");
	// }

		
		
		
	// for( bField = 0.0; bField <= 1.5; bField += 0.5)
	// {
	for(double angDegree = min_ang_degree; angDegree <= max_ang_degree; angDegree += 5)
	{
	    GStyle.getGraphErrorsAttributes().setMarkerColor((int)(angDegree/5) + 1);
	    c1.draw(recon.getGraph(bField, angDegree, minTime, maxTime),"same");
	}		
	// }
		
		
	ReadT2DparsFromCCDB defPars = new ReadT2DparsFromCCDB("default",1000);
	defPars.LoadCCDB();
	double maxRange = 0.8;
	double minRange = 0.0;

	// for( bField = 0.0; bField <= 1.5; bField += 0.5)
	// {		
	for (double angDegree = min_ang_degree; angDegree <= max_ang_degree; angDegree += 5)
	{
	    CalibTimeFunction myFnc = new CalibTimeFunction("myFnc", minRange, maxRange * Math.cos(Math.toRadians(30 - angDegree)));
	    myFnc.setParameters(defPars.parsFromCCDB[secIndex][slIndex]);
	    myFnc.setValues(secIndex, slIndex, bField, angDegree);
	    myFnc.setLineColor((int) (angDegree / 5) + 2);
	    c1.draw(myFnc, "same");
	}
	// }
    }    
}
