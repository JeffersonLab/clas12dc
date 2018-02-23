/**
 * 
 */
package org.jlab.dc_calibration.benchmark;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.jlab.dc_calibration.constants.Constants;
import org.jlab.dc_calibration.core.EstimateT0correctionDeprecated;
import org.jlab.dc_calibration.fit.TimeToDistanceFitter;
import org.jlab.dc_calibration.init.Configure;
import org.jlab.dc_calibration.io.FileOutputWriter;
import org.jlab.dc_calibration.ui.CalibStyle;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.math.F1D;
import org.jlab.groot.ui.TCanvas;

/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
public class FitterForT0Static extends TBTimeDistribution
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
	F1D myFnc[][][][] = new F1D[nSec][nSL][nSlots][nCables];
	/**
	 * 
	 */
	public FitterForT0Static()
	{
		try
		{
			file = new FileOutputWriter(Constants.dataOutputDir + "T0Estimation.txt", append_to_file);
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
		
		myFnc[sec][sl][slot][cable] = new F1D("f1", "[a]*x + [b]", histogram[sec][sl][slot][cable].getDataX(minBin), histogram[sec][sl][slot][cable].getDataX(minBin + nPoints));
		myFnc[sec][sl][slot][cable].setParameter(0, 1.0);
		myFnc[sec][sl][slot][cable].setParameter(1,25.0);
		
		DataFitter.fit(myFnc[sec][sl][slot][cable], gr, "E");  // Other option is Q, no option uses error for sigma

		a = myFnc[sec][sl][slot][cable].getParameter(0);
		delta_a = myFnc[sec][sl][slot][cable].parameter(0).error();
		b = myFnc[sec][sl][slot][cable].getParameter(1);
		delta_b = myFnc[sec][sl][slot][cable].parameter(1).error();
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

	}
	
	public void DrawHist()
	{
		JFrame frame = new JFrame();
		JTabbedPane sectorPanes = new JTabbedPane();
		for (int sec = 0; sec < nSec; ++sec)
		{
			JTabbedPane superLayerPanes = new JTabbedPane();
			for (int sl = 0; sl < nSL; ++sl)
			{
				JTabbedPane slotPanes = new JTabbedPane();
				for (int slot = 0; slot < nSlots; ++slot)
				{
					EmbeddedCanvas canvas = new EmbeddedCanvas();
					canvas.setSize(3 * 400, 2 * 400);
					canvas.divide(3, 2);
					for (int cable = 0; cable < nCables; ++cable)
					{
						canvas.cd(cable);						
						canvas.draw(histogram[sec][sl][slot][cable]);
						canvas.draw(myFnc[sec][sl][slot][cable],"same");
					}
					slotPanes.add(canvas, "Slot " + (slot + 1));
				}
				superLayerPanes.add(slotPanes, "SuperLayer " + (sl + 1));
			}
			sectorPanes.add(superLayerPanes, "Sector " + (sec + 1));
		}
		
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int) (screensize.getWidth() * .9), (int) (screensize.getHeight() * .9));
		frame.setLocationRelativeTo(null);
		frame.add(sectorPanes);
		frame.setVisible(true);
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
			Logger.getLogger(EstimateT0correctionDeprecated.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String arg[])
	{
		CalibStyle.setStyle();
		Configure.setConfig();
		FitterForT0Static t0Fit = new FitterForT0Static();
		t0Fit.FillHistograms();
		t0Fit.DoFitting();	
		t0Fit.DrawHist();
	}		
}
