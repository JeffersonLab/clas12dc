/**
 * 
 */
package org.jlab.dc_calibration.test;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jlab.dc_calibration.core.EstimateT0correction;
import org.jlab.dc_calibration.fit.TimeToDistanceFitter;
import org.jlab.dc_calibration.io.FileOutputWriter;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.math.F1D;
import org.jlab.groot.ui.TCanvas;

/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
public class FitterForT0Static extends HBTimeDistribution
{
	double val1;
	double val2;
	double val3;
	int minBin = 0;
	int maxBin = 0;
	int offset = 15;
	int nPoints = 30;
	double maxVal;
	double a;
	double b;
	double delta_a;
	double delta_b;
	double T0;
	double delta_T0;
	FileOutputWriter file = null;
	boolean append_to_file = false;
	String result;
	TCanvas c1 = new TCanvas("Fit Result",1200,800);

	/**
	 * 
	 */
	public FitterForT0Static()
	{
		c1.divide(6, 6);
		try
		{
			file = new FileOutputWriter("T0Estimation.txt", append_to_file);
			file.Write("Sec  SL  Slot  Cable  T0 T0err");
		}
		catch (IOException ex)
		{
			Logger.getLogger(TimeToDistanceFitter.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	public void FitLeadingEdge(int sec, int sl, int slot, int cable)
	{
		GraphErrors gr = new GraphErrors();
				
		minBin = 120;
		
		for(int i = minBin; i< (minBin + nPoints); ++i)
		{
			gr.addPoint(histogram[sec][sl][slot][cable].getDataX(i), histogram[sec][sl][slot][cable].getBinContent(i), histogram[sec][sl][slot][cable].getDataEX(i), histogram[sec][sl][slot][cable].getDataEY(i));		
		}
		
	
		double pedestal = 0.0;
		for(int i = 0; i< 10; i++)
		{
			pedestal += histogram[sec][sl][slot][cable].getBinContent(i);
		}
		pedestal /= 10.0;
		System.out.println("The pedestal is: " + pedestal);
		
		//F1D myFnc = new F1D("f1", "[a]*x + [b]", histogram[sec][sl][slot][cable].getDataX(minBin), histogram[sec][sl][slot][cable].getDataX(maxBin - offset));
		F1D myFnc = new F1D("f1", "[a]*x + [b]", histogram[sec][sl][slot][cable].getDataX(minBin), histogram[sec][sl][slot][cable].getDataX(minBin + nPoints));
		myFnc.setParameter(0, 1.0);
		myFnc.setParameter(1,25.0);
		
		DataFitter.fit(myFnc, gr, "E");  // Other option is Q, no option uses error for sigma

		a = myFnc.getParameter(0);
		delta_a = myFnc.parameter(0).error();
		b = myFnc.getParameter(1);
		delta_b = myFnc.parameter(1).error();
		//T0 = -1.0*b/a;		
		T0 = (pedestal - b)/a;		
		/*
		 * Y = aT + b -----> T_0 = (Y_0 - b)/a
		 * (delta T_0)^2 = (delta_b/a)^2 + (b x delta_a/a^2)^2 + (T_0 x delta_a/a^2)^2 
		 * 
		 */
		//delta_T0 = Math.sqrt( Math.pow(delta_b/a, 2) + Math.pow(b*(delta_a/Math.pow(a,2)),2) );
		delta_T0 = Math.sqrt( Math.pow(delta_b/a, 2) + Math.pow(b*(delta_a/Math.pow(a,2)),2) + Math.pow(pedestal*(delta_a/Math.pow(a,2)),2));
		//System.out.println("Estimated T0 : " + T0 + " +/- " + delta_T0);
		System.out.println((sec + 1) + "\t" + (sl + 1) + "\t" + (slot + 1) + "\t" + (cable + 1) +"\t" + T0 + " \t " + delta_T0);
		
		result = String.format("%d %d %d %d %4.3f %4.3f", (sec + 1), (sl + 1), (slot + 1), (cable + 1), T0, delta_T0 );
		file.Write(result);

		if(slot == 0 && cable == 0)
		{
			 c1.cd(sec * 6 + sl);	
			 c1.draw(histogram[sec][sl][slot][cable].getGraph());
			 c1.draw(myFnc,"same");
		}		
		
//		 TCanvas c1 = new TCanvas("Strainght line", 800, 600);
//		 c1.draw(histogram[sec][sl][slot][cable].getGraph());
//		 c1.draw(myFnc,"same");
	}
	
	public void DoFitting()
	{
		if(file == null)
		{
			System.out.println("Unable to create output file");
			return;
		}
		for (int sec = 0; sec < nSec; ++sec) 
		{
			for (int sl = 0; sl < nSL; ++sl)
			{
				for (int slot = 0; slot < nSlots; ++slot)
				{
					for (int cable = 0; cable < nCables; ++cable)
					{
						FitLeadingEdge(sec, sl, slot, cable);
					}
				}
			}
		}
		try
		{
			file.Close();
		}
		catch (IOException ex)
		{
			Logger.getLogger(EstimateT0correction.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String arg[])
	{
		FitterForT0Static test = new FitterForT0Static();
		test.FillHistograms();
		//test.FitLeadingEdge(1, 2, 0, 0);
		//test.FitLeadingEdge(1, 0, 6, 2);
		//test.FitLeadingEdge(1, 5, 4, 2);
		test.DoFitting();		
		// TCanvas c1 = new TCanvas("c1", 800, 600);
		// c1.draw(test.histogram[1][3][0][0]);
	}		
}
