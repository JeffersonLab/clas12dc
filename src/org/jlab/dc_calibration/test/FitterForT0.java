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
public class FitterForT0 extends HBTimeDistribution
{
	double val1;
	double val2;
	double val3;
	int minBin = 0;
	int maxBin = 0;
	int offset = 15;
	int nPoints = 45;//30;
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
	/**
	 * 
	 */
	public FitterForT0()
	{
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
		
		double lowerCutOff = 0.30*histogram[sec][sl][slot][cable].getDataY(histogram[sec][sl][slot][cable].getMaximumBin());
		//double upperCutOff = 0.6*histogram[sec][sl][slot][cable].getDataY(histogram[sec][sl][slot][cable].getMaximumBin());
		maxVal = histogram[sec][sl][slot][cable].getDataY(histogram[sec][sl][slot][cable].getMaximumBin());		
		for(int i = 0; i< 600; ++i)
		{
			//System.out.println("Bin#: " + i + " X: " + histogram[sec][sl][slot][cable].getDataX(i)  + " Content:" + histogram[sec][sl][slot][cable].getBinContent(i));					
			val1 = histogram[sec][sl][slot][cable].getBinContent(i);
			val2 = histogram[sec][sl][slot][cable].getBinContent(i + 5);
			val3 = histogram[sec][sl][slot][cable].getBinContent(i + 10);

			if(val1 > lowerCutOff && val2 > lowerCutOff && val3 > lowerCutOff)
			{
				minBin = i;
				maxBin = histogram[sec][sl][slot][cable].getMaximumBin();
//				System.out.println("The min bin X:" + histogram[sec][sl][slot][cable].getDataX(i) + " bin# " + i);
//				System.out.println("The max bin X:" + histogram[sec][sl][slot][cable].getDataX(histogram[sec][sl][slot][cable].getMaximumBin()) + " bin# " + histogram[sec][sl][slot][cable].getMaximumBin());
				break;
			}
		}
		
//		if(minBin < 325)
		minBin = 330;
		
		for(int i = minBin; i< (minBin + nPoints); ++i)
//		for(int i = minBin; i< (minBin + 15); ++i)
		{
			gr.addPoint(histogram[sec][sl][slot][cable].getDataX(i), histogram[sec][sl][slot][cable].getBinContent(i), histogram[sec][sl][slot][cable].getDataEX(i), histogram[sec][sl][slot][cable].getDataEY(i));		
		}
		
	
		double pedestal = 0.0;
		for(int i = 0; i< 10; i++)
		{
			pedestal += histogram[sec][sl][slot][cable].getBinContent(290 + i);
		}
		pedestal /= 10.0;
		System.out.println("The pedestal is: " + pedestal);
		
		//F1D myFnc = new F1D("f1", "[a]*x + [b]", histogram[sec][sl][slot][cable].getDataX(minBin), histogram[sec][sl][slot][cable].getDataX(maxBin - offset));
		F1D myFnc = new F1D("f1", "[a]*x + [b]", histogram[sec][sl][slot][cable].getDataX(minBin), histogram[sec][sl][slot][cable].getDataX(minBin + nPoints));
		myFnc.setParameter(0, 1.0);
		myFnc.setParameter(1,25.0);
		
		DataFitter.fit(myFnc, gr, "E");  // Other option is Q, no option uses error for sigma
		//myFnc.show();
		//myFnc.setOptStat(11111);
		//System.out.println("Fit param 1: " + myFnc.getParameter(0) + " +- " + myFnc.parameter(0).error());
		//System.out.println("Fit param 2: " + myFnc.getParameter(1) + " +- " + myFnc.parameter(1).error());
//		System.out.println("Estimated T0 : " + (-1.0*myFnc.getParameter(1)/myFnc.getParameter(0)));

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
		for (int sec = 1; sec < 2; ++sec) // <---------- For KPP do only for sector 2
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
		FitterForT0 test = new FitterForT0();
		test.FillHistograms();
		//test.FitLeadingEdge(1, 2, 0, 0);
		//test.FitLeadingEdge(1, 0, 6, 2);
		//test.FitLeadingEdge(1, 5, 4, 2);
		test.DoFitting();		
		// TCanvas c1 = new TCanvas("c1", 800, 600);
		// c1.draw(test.histogram[1][3][0][0]);
	}		
}
