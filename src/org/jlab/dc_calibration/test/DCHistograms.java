/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
package org.jlab.dc_calibration.test;

import java.io.File;
import java.util.ArrayList;

import org.jlab.groot.data.GraphErrors;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;
import org.jlab.groot.ui.TCanvas;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;


public class DCHistograms
{
	HipoDataSource reader = new HipoDataSource();
	ArrayList<String> fileList = new ArrayList<>();
	DataEvent event;
	DataBank TBHits;
		
	//---------- List of Histograms ----------------
	H2F h2d_time_vs_trkDoca = new H2F("time vs trkDoca", 100, -150, 800.0,100, 0, 2.0 );
	H1F h1d_timeResidual = new H1F("Time Residual", 100, -1.0, 1.0);
	
	/**
	 * Constructor for DC calibration histogram
	 */
	public DCHistograms()
	{
		Init();
	}

	void Init()
	{
		fileList = new FileSelector().fileArray;
	}
	
	public void FillHistograms()
	{
		//------------ Loop over all selected files -------------
		for (String filePath : fileList)
		{
			System.out.println(filePath);

			if (!(new File(filePath).exists()))
			{
				System.out.println("\nThe file: " + filePath + " does NOT exist ..... SKIPPED.");
				continue;
			}
			else
				System.out.println("\nNow filling file: " + filePath);
			
			//-------- Read the data file -----------
			reader.open(filePath);

			//------------ Loop over all events in the file -------
			while (reader.hasEvent())
			{
				//--------- Load Event ---------------------------------
				event = reader.getNextEvent();
				if (!event.hasBank("TimeBasedTrkg::TBHits"))
					continue;

				//---------------- Load All Desired DC Banks -----------------------
				TBHits = event.getBank("TimeBasedTrkg::TBHits");
				// ---- Read any other desired bank here ----
				
				//----------- Loop over all entries (hits) of the event --------
				for (int k = 0; k < TBHits.rows(); k++)
				{
					//--------- Include data selection or cut here ----------------------
					if (TBHits.getByte("sector", k) == 2 && TBHits.getByte("superlayer", k) == 1)
					{
						// --------- Fill variables from TBHits bank  --------------
						h2d_time_vs_trkDoca.fill(TBHits.getFloat("time", k), TBHits.getFloat("trkDoca", k));
						h1d_timeResidual.fill(TBHits.getFloat("timeResidual", k));
					
					    //-------- Fill other variables here ------
					
					    //------- Fill other banks here ---------						
					}
				}
			}			
			reader.close();
		}		
		new TCanvas("c1", 800, 500).draw(h2d_time_vs_trkDoca);
		new TCanvas("c2", 800, 500).draw(h1d_timeResidual);
	}
		
	public void drawProfileHistogram()
	{
		GraphErrors prof_time_vs_trkDoca = h2d_time_vs_trkDoca.getProfileY(); 
		new TCanvas("c3", 800, 500).draw(prof_time_vs_trkDoca);
	}
		
	public static void main(String arg[])
	{
		DCHistograms test = new DCHistograms();
	    test.FillHistograms();
	    test.drawProfileHistogram();
	}

}
