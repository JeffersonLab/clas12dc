/**
 * 
 */
package org.jlab.dc_calibration.test;

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
	double a;
	double b;
	double delta_a;
	double delta_b;
	double T0;
	double delta_T0;
	/**
	 * 
	 */
	public FitterForT0()
	{
		
	}
	
	public void FitLeadingEdge(int sec, int sl, int slot, int cable)
	{
		GraphErrors gr = new GraphErrors();
		
		double lowerCutOff = 0.30*histogram[sec][sl][slot][cable].getDataY(histogram[sec][sl][slot][cable].getMaximumBin());
		//double upperCutOff = 0.6*histogram[sec][sl][slot][cable].getDataY(histogram[sec][sl][slot][cable].getMaximumBin());
				
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
				System.out.println("The min bin X:" + histogram[sec][sl][slot][cable].getDataX(i) + " bin# " + i);
				System.out.println("The max bin X:" + histogram[sec][sl][slot][cable].getDataX(histogram[sec][sl][slot][cable].getMaximumBin()) + " bin# " + histogram[sec][sl][slot][cable].getMaximumBin());
				break;
			}
		}
		
		//TCanvas c1 = new TCanvas("Strainght line", 800, 600);
		for(int i = minBin; i< (maxBin - offset); ++i)
		{
			gr.addPoint(histogram[sec][sl][slot][cable].getDataX(i), histogram[sec][sl][slot][cable].getBinContent(i), histogram[sec][sl][slot][cable].getDataEX(i), histogram[sec][sl][slot][cable].getDataEY(i));		
		}
		
		F1D myFnc = new F1D("f1", "[a]*x + [b]", histogram[sec][sl][slot][cable].getDataX(minBin), histogram[sec][sl][slot][cable].getDataX(maxBin - offset));
		myFnc.setParameter(0, 1.0);
		myFnc.setParameter(1,5.0);
		
		DataFitter.fit(myFnc, gr, "E");
//		myFnc.show();
//		myFnc.setOptStat(11111);
//		System.out.println("Fit param 1: " + myFnc.getParameter(0) + " +- " + myFnc.parameter(0).error());
//		System.out.println("Fit param 2: " + myFnc.getParameter(1) + " +- " + myFnc.parameter(1).error());
//		System.out.println("Estimated T0 : " + (-1.0*myFnc.getParameter(1)/myFnc.getParameter(0)));

		a = myFnc.getParameter(0);
		delta_a = myFnc.parameter(0).error();
		b = myFnc.getParameter(1);
		delta_b = myFnc.parameter(1).error();
		T0 = -1.0*b/a;		
		/*
		 * Y = aT + b -----> T_0 = -b/a
		 * (delta T_0)^2 = (delta_b/a)^2 + (b x delta_a/a^2)^2 
		 * 
		 */
		delta_T0 = Math.sqrt( Math.pow(delta_b/a, 2) + Math.pow(b*(delta_a/Math.pow(a,2)),2) );
		//System.out.println("Estimated T0 : " + T0 + " +/- " + delta_T0);
		System.out.println(sec + "\t" + sl + "\t" + slot + "\t" + cable +"\t" + T0 + " \t " + delta_T0);
		
//		c1.draw(histogram[sec][sl][slot][cable].getGraph());
//		c1.draw(myFnc,"same");
	}
	
	public static void main(String arg[])
	{
		FitterForT0 test = new FitterForT0();
		test.FillHistograms();
		//test.FitLeadingEdge(1, 4, 0, 0);
				
		for (int sec = 1; sec < 2; ++sec)
		{
			for (int sl = 0; sl < test.nSL; ++sl)
			{
				for (int slot = 0; slot < test.nSlots; ++slot)
				{
					for (int cable = 0; cable < test.nCables; ++cable)
					{
						test.FitLeadingEdge(sec, sl, slot, cable);
					}
				}
			}
		}
				
//		TCanvas c1 = new TCanvas("c1", 800, 600);
//		c1.draw(test.histogram[1][3][0][0]);
	}

}
