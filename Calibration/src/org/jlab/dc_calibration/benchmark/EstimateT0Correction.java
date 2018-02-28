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
import org.jlab.dc_calibration.ui.DC_Calibration;
import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.fitter.DataFitter;
import org.jlab.groot.graphics.EmbeddedCanvas;
import org.jlab.groot.math.F1D;
import org.jlab.groot.ui.TCanvas;

import com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel;

/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 *To be done:
 *         Discard (replace with neighbor) result if: nEntries < 1000 , error > 50%, T0 < -700 or T0 > -200. Improve pedestal calculation using histogram, Correct error calculation,
 *         include error in pedestal calculation
 *
 */
public class EstimateT0Correction extends TBTimeDistribution
{
	double a;
	double b;
	double delta_a;
	double delta_b;
	double T0;
	double delta_T0;
	double previousT0 = -500.0;
	double previous_delta_T0 = 5.0;
	FileOutputWriter file = null;
	boolean append_to_file = false;
	String result;
	F1D myFnc[][][][] = new F1D[nSec][nSL][nSlots][nCables];
	public int cablesDone;

	/**
	 * 
	 */
	public EstimateT0Correction()
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
		myFnc[sec][sl][slot][cable] = MaxSlopBin(sec, sl, slot, cable);

		double pedestal = 0.0;
		for (int i = 0; i < 25; i++)
		{
			pedestal += histogram[sec][sl][slot][cable].getBinContent(i);
		}
		pedestal /= 25.0;
		// System.out.println("The pedestal is: " + pedestal);

		if (myFnc[sec][sl][slot][cable] != null)
		{
			a = myFnc[sec][sl][slot][cable].getParameter(0);
			delta_a = myFnc[sec][sl][slot][cable].parameter(0).error();
			b = myFnc[sec][sl][slot][cable].getParameter(1);
			delta_b = myFnc[sec][sl][slot][cable].parameter(1).error();
		}
		else
		{
			a = 1;
			b = 0;
			pedestal = 0;
		}
		T0 = (pedestal - b) / a;
		/*
		 * Y = aT + b -----> T_0 = (Y_0 - b)/a (delta T_0)^2 = (delta_b/a)^2 + (b x delta_a/a^2)^2 + (T_0 x delta_a/a^2)^2
		 * 
		 */
		delta_T0 = Math.sqrt(Math.pow(delta_b / a, 2) + Math.pow(b * (delta_a / Math.pow(a, 2)), 2)
				+ Math.pow(pedestal * (delta_a / Math.pow(a, 2)), 2));

		if (histogram[sec][sl][slot][cable].getEntries() < 1000 || T0 < -700 || T0 > -200
				|| 100 * delta_T0 / Math.abs(T0) > 50)
		{
			System.out.println("For Sec:" + sec + " SL: " + sl + " Slot:" + slot + " Cable:" + cable
					+ ", the T0 from the fit is unreliable due to low statistics "
					+ " or other issues. Replacing with a neighboring value.");
			T0 = previousT0;
			delta_T0 = previous_delta_T0;
		}
		else
		{
			previousT0 = T0;
			previous_delta_T0 = delta_T0;
		}
		// System.out.println((sec + 1) + "\t" + (sl + 1) + "\t" + (slot + 1) + "\t" + (cable + 1) +"\t" + T0 + " \t " + delta_T0);
		result = String.format("%d %d %d %d %4.3f %4.3f", (sec + 1), (sl + 1), (slot + 1), (cable + 1), T0, delta_T0);
		file.Write(result);
	}

	public void DoFitting()
	{
		if (file == null)
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
						cablesDone++;
						System.out.println("Fitting done for cable#" + cablesDone + " ... ... ");
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

	public F1D MaxSlopBin(int sec, int sl, int slot, int cable)
	{
		int min_Bin = 50;
		int max_Bin = 300;
		double slope = 0;
		double max_slope = 0;

		F1D my_Fnc = null;

		while (min_Bin <= (max_Bin - 20))
		{
			F1D Fnc = new F1D("f1", "[a]*x + [b]", histogram[sec][sl][slot][cable].getDataX(min_Bin),
					histogram[sec][sl][slot][cable].getDataX(min_Bin + 20));
			Fnc.setParameter(0, 1.0);
			Fnc.setParameter(1, 25.0);

			DataFitter.fit(Fnc, histogram[sec][sl][slot][cable], "Q"); // E,Other option is Q, no option uses error for sigma

			slope = Fnc.getParameter(0);
			if (slope > max_slope)
			{
				max_slope = slope;
				my_Fnc = Fnc;
				// System.out.println("Slope:" + max_slope);
			}
			++min_Bin;
		}
		return my_Fnc;
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
						canvas.draw(myFnc[sec][sl][slot][cable], "same");
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

	public void EstimateT0()
	{
		Configure.setConfig();
		CalibStyle.setStyle();
		if (fileList.size() == 0)
			return;
		System.out.println("Estimating T0 correction. This takes some time. Please wait... ...");
		System.out.println("Finnling the histograms ... ...");
		FillHistograms();
		DoFitting();
		DrawHist();
	}

	public static void main(String[] args)
	{
		Configure.setConfig();
		CalibStyle.setStyle();
		EstimateT0Correction t0Fitter = new EstimateT0Correction();
		if (t0Fitter.fileList.size() == 0)
			return;
		t0Fitter.FillHistograms();
		t0Fitter.DoFitting();
		t0Fitter.DrawHist();
	}
}
