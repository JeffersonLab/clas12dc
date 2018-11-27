// Filename: ReconTimeFunction.java
// Description: Plot the DC time function used in the reconstruction
// Author: Latif Kabir < latif@jlab.org >
// Created: Sat Aug 26 10:24:56 2017 (-0400)
// URL: latifkabir.github.io

package org.jlab.dc_calibration.benchmark;

import org.jlab.rec.dc.timetodistance.TableLoader;
import org.jlab.utils.groups.IndexedTable;

import static org.jlab.dc_calibration.constants.Constants.wpdist;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.image.AreaAveragingScaleFilter;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.jlab.dc_calibration.benchmark.CalibTimeFunction;
import org.jlab.dc_calibration.benchmark.ReconTimeFunction;
import org.jlab.dc_calibration.io.ReadT2DparsFromCCDB;
import org.jlab.dc_calibration.ui.CalibStyle;
import org.jlab.detector.calib.utils.DatabaseConstantProvider;
import org.jlab.groot.base.GStyle;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.ui.TCanvas;

public class CompareT2DReconVsCalib
{
	int secIndex = 0;
	int slIndex = 0;
	double bField = 0.0;
	double min_ang_degree = 0;
	double max_ang_degree = 29;  // Reconstruction has issues with 30 degree 

	int run_number = 100;
	String ccdb_variation = "default";

	// For Calibration side
	CalibTimeFunction t2dFncCalib;
	ReadT2DparsFromCCDB defPars; 
	double maxRange = 2.0 * wpdist[slIndex];
	double minRange = 0.0;

	 // For Reconstruction Side
	 ReconTimeFunction recon;
	 DatabaseConstantProvider provider;
	 IndexedTable table;
	 double maxTime = 200;
	 double minTime = 0;

	JTabbedPane sectorPanes = new JTabbedPane();
	JTabbedPane sectorPanesA = new JTabbedPane();
	JTabbedPane sectorPanesB = new JTabbedPane();
	EmbeddedCanvas canvasA[] = new EmbeddedCanvas[6];
	EmbeddedCanvas canvasB[] = new EmbeddedCanvas[6];
	 
	public CompareT2DReconVsCalib(int runNumber, String variation)
	{	
		run_number = runNumber;
		ccdb_variation = variation;
		init();
	}

	void init()
	{
		//-------------- Load Calibration constants to be use in calibration T2D function -----------------------
		defPars = new ReadT2DparsFromCCDB(ccdb_variation, run_number);
		defPars.LoadCCDB();

		//-------------- Load Calibration constants to be use in reconstruction T2D function -----------------------         
		provider = new DatabaseConstantProvider(run_number,ccdb_variation);
		table = provider.readTable("/calibration/dc/time_to_distance/time2dist");			
		TableLoader.Fill(table);
		
		//----------------- Tabbed Pane ----------------
		for (int sec = 0; sec < 6; ++sec)
		{
		    canvasA[sec] = new EmbeddedCanvas();
		    canvasA[sec].divide(3, 2);
		    sectorPanesA.add(canvasA[sec],"Sec " + (sec + 1));
		    
		    canvasB[sec] = new EmbeddedCanvas();
		    canvasB[sec].divide(4, 2);
		    sectorPanesB.add(canvasB[sec],"Sec " + (sec + 1));
		}
	}
    	
	public void plotWithoutBfield(int secIndex, int slIndex)
	{
		maxTime = defPars.parsFromCCDB[secIndex][slIndex][2];
		maxRange = 2.0 * wpdist[slIndex];

		recon = new ReconTimeFunction(secIndex, slIndex);
		
		canvasA[secIndex].cd(slIndex);		
		GStyle.getGraphErrorsAttributes().setTitle("S " + (secIndex + 1) + " SL " + (slIndex + 1)
				+ ", Reconstruction (dotted line) vs Calibration (solid line), theta = " + min_ang_degree + "-" +  max_ang_degree + ", B = " + bField + " T");

		for (double angDegree = min_ang_degree; angDegree <= max_ang_degree; angDegree += 5)
		{
	        //-------------------------- T2D Functional form from Reconstruction --------------------
//			GStyle.getGraphErrorsAttributes().setMarkerColor((int) (angDegree / 5) + 1);
//			canvasA[secIndex].draw(recon.getGraph(bField, angDegree, minTime, maxTime), "same");
						
	        //-------------------------- T2D Functional form from Calibration --------------------			
			t2dFncCalib = new CalibTimeFunction("myFnc", minRange, maxRange * Math.cos(Math.toRadians(30 - angDegree)));
			t2dFncCalib.setParameters(defPars.parsFromCCDB[secIndex][slIndex]);
			t2dFncCalib.setValues(secIndex, slIndex, bField, angDegree);
			t2dFncCalib.setLineColor((int) (angDegree / 5) + 2);
			canvasA[secIndex].draw(t2dFncCalib, "same");
		}
	}

	public void plotWithBfield(int secIndex, int slIndex, double B_field)
	{
		maxTime = defPars.parsFromCCDB[secIndex][slIndex][2];
		maxRange = 2.0 * wpdist[slIndex];

		recon = new ReconTimeFunction(secIndex, slIndex);
		int Bfield_bin = (int)(B_field/0.5);
		canvasB[secIndex].cd((slIndex - 2)*4 + Bfield_bin -1);		
		
		GStyle.getGraphErrorsAttributes().setTitle("S " + (secIndex + 1) + " SL " + (slIndex + 1)
				+ " Recon (dotted line) vs Calib (solid line),theta = " + min_ang_degree + "-" +  max_ang_degree + " and B = " + B_field + " T");
		
		for (double angDegree = min_ang_degree; angDegree <= max_ang_degree; angDegree += 5)
		{
	        //-------------------------- T2D Functional form from Calibration --------------------			
			t2dFncCalib = new CalibTimeFunction("myFnc", minRange, maxRange * Math.cos(Math.toRadians(30 - angDegree)));
			t2dFncCalib.setParameters(defPars.parsFromCCDB[secIndex][slIndex]);
			t2dFncCalib.setValues(secIndex, slIndex, B_field, angDegree);
			t2dFncCalib.setLineColor((int) (angDegree / 5) + 2);
			canvasB[secIndex].draw(t2dFncCalib, "same");
            
			// In the presence of B-field Tmax can be higher than default
			maxTime = t2dFncCalib.evaluate(maxRange * Math.cos(Math.toRadians(30 - angDegree)));
	        //-------------------------- T2D Functional form from Reconstruction --------------------
//			GStyle.getGraphErrorsAttributes().setMarkerColor((int) (angDegree / 5) + 1);
//			canvasB[secIndex].draw(recon.getGraph(B_field, angDegree, minTime, maxTime), "same");	
		}
	}

	public void MakeComparison()
	{
		CalibStyle.setStyle();
		GStyle.getGraphErrorsAttributes().setMarkerSize(2);
		GStyle.getGraphErrorsAttributes().setTitleX("Distance [cm]");
		GStyle.getGraphErrorsAttributes().setTitleY("Time [ns]");
		
		for (int secI = 0; secI < 6; ++secI)
		{
			for (int slI = 0; slI < 6; ++slI)
			{
				plotWithoutBfield(secI, slI);
			}
		}
		
		for (int secI = 0; secI < 6; ++secI)
		{
			for (int slI = 2; slI < 4; ++slI)
			{
				 for( double bField = 0.5; bField <= 2.0; bField += 0.5)
				 {
					 plotWithBfield(secI, slI, bField);
				 }
			}
		}
		
		sectorPanes.add("No B field", sectorPanesA);
		sectorPanes.add("With B field", sectorPanesB);
		
		JFrame frame = new JFrame();
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int) (screensize.getWidth() * .9), (int) (screensize.getHeight() * .9));
		frame.setLocationRelativeTo(null);
		frame.add(sectorPanes);
		frame.setVisible(true);
		    	    
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);					
		
	}
		
	public void CompareSingleSL(int sec_index, int sl_index)
	{
		secIndex = sec_index;
		slIndex = sl_index;
		maxRange = 2.0 * wpdist[slIndex];
		minRange = 0.0;
		maxTime = defPars.parsFromCCDB[secIndex][slIndex][2];
		minTime = 0;
		double angDegree = 20;
		
		CalibStyle.setStyle();
		GStyle.getGraphErrorsAttributes().setMarkerSize(2);
		GStyle.getGraphErrorsAttributes().setTitleX("Distance [cm]");
		
		GStyle.getGraphErrorsAttributes().setTitleY("Time [ns]");
		

		TCanvas c1 = new TCanvas("c1", 800, 600);
		c1.setTitle("Distance (cm) vs Time (ns)");

		 GStyle.getGraphErrorsAttributes().setTitle("Time (ns) vs Distance (cm) for S " + (secIndex + 1) + " SL " + (slIndex + 1) 
				 + " Reconstruction (dotted line) vs Calibration (solid line) for 10 degree and B = 0.0 , 0.5, 1.0, 1.5 T");

		ReconTimeFunction recon = new ReconTimeFunction(secIndex, slIndex);
	
		
		for (bField = 0.0; bField <= 1.5; bField += 0.5)
		{
				// -------------------------- T2D Functional form from Reconstruction --------------------
				GStyle.getGraphErrorsAttributes().setMarkerColor((int) (angDegree / 5) + 1);
				c1.draw(recon.getGraph(bField, angDegree, minTime, maxTime), "same");

				// -------------------------- T2D Functional form from Calibration --------------------
				CalibTimeFunction myFnc = new CalibTimeFunction("myFnc", minRange, maxRange * Math.cos(Math.toRadians(30 - angDegree)));
				myFnc.setParameters(defPars.parsFromCCDB[secIndex][slIndex]);
				myFnc.setValues(secIndex, slIndex, bField, angDegree);
				myFnc.setLineColor((int) (angDegree / 5) + 2);
				c1.draw(myFnc, "same");
		}
	}
	
	public static void main(String[] args)
	{
		CompareT2DReconVsCalib t2d = new CompareT2DReconVsCalib(Integer.valueOf(args[0]), "default");
		t2d.MakeComparison();
		//t2d.CompareSingleSL(0,0);
	}
}
