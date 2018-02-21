package org.jlab.dc_calibration.benchmark;

import static org.jlab.dc_calibration.constants.Constants.iSecMax;
import static org.jlab.dc_calibration.constants.Constants.iSecMin;
import static org.jlab.dc_calibration.constants.Constants.nSL;
import static org.jlab.dc_calibration.constants.Constants.nThBinsVz2;
import static org.jlab.dc_calibration.constants.Constants.thEdgeVzH2;
import static org.jlab.dc_calibration.constants.Constants.thEdgeVzL2;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

import org.jlab.dc_calibration.fit.TimeToDistanceFitter;
import org.jlab.dc_calibration.init.Coordinate;
import org.jlab.groot.graphics.EmbeddedCanvas;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class CalibWorkFlow extends TimeToDistanceFitter
{

	public CalibWorkFlow(ArrayList<String> files, boolean isLinearFit)
	{
		//------------------ Declare and initialize the Histograms -----------------------------------
		super(files, isLinearFit);
	}

	public void workFlow()
	{
		//~~~~~~~~~~~~ Pre-fit ~~~~~~~~~~~~
		//------------------------ Process the Data / Fill the Histograms -----------------------------
		processData();
		
		//-------------- Some Quick Plots for B-field ------------------------ 
		//drawQuickTestPlots();
		//System.out.println("Called drawQuickTestPlots();");

		//--------- The Time to trkDoca distributions for different angular bins -------------------------		
		MakeAndDrawXTProjectionsOfXTBhists();
		//System.out.println("Called MakeAndDrawXTProjectionsOfXTBhists();");
		MakeAndDrawTBProjectionsOfXTBhists();

		//h3BTXmap.get(new Coordinate(1,1,1)).getBinContent(x, y, z);
		
		//----------------------- Call to fit control -----------------------------
		// SliceViewer(this); //Now can be opened with a button in FitControlUI
		//OpenFitControlUI(this);		
		
		
		//--------------- Fit and plot the fit ---------------------------
		
//		this.runFitterAndDrawPlots(this, jTextArea1, gSector, gSuperlayer,
//				xMeanErrorType, xNormLow, xNormHigh, checkboxVal, checkBoxFixAll,
//				resetFitParsLow, resetFitPars, resetFitParsHigh, resetFitParSteps, selectedAngleBins);
		
		//ALternatively:
//		drawQuickTestPlots();
//		runFitterNew(textArea, Sec, SL, xMeanErrorType, xNormLow, xNormHigh, fixIt, checkBoxFixAll, pLow,
//				pInit, pHigh, pSteps, selectedAngleBins);
        // ~~~~ Post-fit ~~~~~~~~~~		
//		createFitLinesNew(Sec, SL);
//		drawFitLinesNew(frame, Sec, SL);

	}

	
	public void MakeAndDrawTBProjectionsOfXTBhists()
	{
		int nXbins, nTbins, nBbins;
		// Now drawing these projections onto Tabbed Panes:
		String Title = "";
		JFrame frame = new JFrame();
		JTabbedPane sectorPanes = new JTabbedPane();
		for (int i = iSecMin; i < iSecMax; i++)
		{
			JTabbedPane anglePanes = new JTabbedPane();
			for (int k = 0; k < nThBinsVz2; k++)
			{
				EmbeddedCanvas canvas = new EmbeddedCanvas();
				canvas.setSize(3 * 400, 2 * 400);
				canvas.divide(3, 2);
				for (int j = 0; j < nSL; j++)
				{
					canvas.cd(j);
					Title = "Sec=" + (i + 1) + " SL=" + (j + 1)
							+ " theta=(" + thEdgeVzL2[k] + "," + thEdgeVzH2[k] + ")"
							+ " indvFitCol";
					////canvas.draw(h3BTXmap.get(new Coordinate(i, j, k)).getYZProj());
					canvas.getPad(j).setTitle(Title);
					canvas.setPadTitlesX("time");
					canvas.setPadTitlesY("B-field");
				}
				anglePanes.add(canvas, "ThBin" + (k + 1));
			}
			sectorPanes.add(anglePanes, "Sector " + (i + 1));
		}
		Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize((int) (screensize.getWidth() * .9), (int) (screensize.getHeight() * .9));
		frame.setLocationRelativeTo(null);
		frame.add(sectorPanes);
		frame.setVisible(true);
	}

	
	public static void main(String[] args)
	{		 
		CalibWorkFlow fitter = new CalibWorkFlow(new FileSelector().fileArray, false);
		fitter.workFlow();
	}
}

/*
Divide the TimeToDistanceFitter in the following classes:

HistoContainer : Histograms
DataProcessor
DataPlotter : PreFitPlot, PostFitPlot
DataFitter
FitDrawer

Implement this by extending child from parent

*/