/**
 *  The fit routine for DC calibration
 */
package org.jlab.dc_calibration.test;

import org.freehep.math.minuit.FCNBase;
import org.freehep.math.minuit.FunctionMinimum;
import org.freehep.math.minuit.MnMigrad;
import org.freehep.math.minuit.MnStrategy;
import org.freehep.math.minuit.MnUserParameters;
import org.jlab.groot.data.GraphErrors;

/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
public class DCFitting
{
	
	//------------------- The fitting -----------------------------------------------------
	public void DoFitting()
	{
		
		GraphErrors gr = new GraphErrors();//FillHist();
		double[] X = new double[100];
		double[] Y = new double[100];
		
		for(int i = 0; i<100; i++)
		{
			System.out.println(i + ": " + gr.getDataX(i) +"\t " + gr.getDataY(i) + "\t" + gr.getDataEX(i) + 
					"\t" + gr.getDataEY(i));
			X[i] = gr.getDataX(i);
			Y[i] = gr.getDataY(i);
		}
	
		
		int npars = 2;

		double aLen = X.length;
		System.out.println("Size or length of array 'measurements' is " + aLen);
		System.out.print("xArray[] = ");

		FitFunction theFCN = new FitFunction(X, Y);

		MnUserParameters upar = new MnUserParameters();
		upar.add("p0", 0.0, 0.001);
		upar.add("p1", 1.5, 0.001);

		System.out.println("Initial parameters: " + upar);

		System.out.println("start migrad");
		MnMigrad migrad = new MnMigrad(theFCN, upar);
		FunctionMinimum min = migrad.minimize();

		if (!min.isValid())
		{
			// try with higher strategy
			System.out.println("FM is invalid, try with strategy = 2.");
			MnMigrad migrad2 = new MnMigrad(theFCN, min.userState(), new MnStrategy(2));
			min = migrad2.minimize();
		}

		System.out.println("minimum: " + min);

		System.out.println("kp: ===================================== ");

		MnUserParameters userpar = min.userParameters();
		System.out.println("par0 = " + userpar.value("p0") + " +/- " + userpar.error("p0"));
		System.out.println("par1 = " + userpar.value("p1") + " +/- " + userpar.error("p1"));
		System.out.println("kp: ===================================== ");

	}


	// static class ReneFcn implements FCNBase
	static class FitFunction implements FCNBase
	{
		private double[] theXvalues;
		private double[] theYvalues;

		FitFunction(double[] xVals, double[] yVals)
		{
			theXvalues = xVals;
			theYvalues = yVals;
		}

		public double errorDef()
		{
			return 1;
		}

		public double valueOf(double[] par)
		{
			double m = par[1]; 
			double c = par[0];
			double chisq = 0.0;
			double yi;
			double xi;
			double ei;
			double yExp;
			
			for (int i = 0; i < theYvalues.length; i++)
			{
				 yi = theYvalues[i]; 
				 xi = theXvalues[i]; 
				 ei = yi;
				 yExp = m * xi*xi + c;
				chisq += (yi - yExp) * (yi - yExp);/// ei;
			}
			return chisq;
		}

	}


}
