/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
package org.jlab.dc_calibration.test;

import java.io.File;
import java.util.ArrayList;

import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;

public class DataProcessor extends DCHistograms
{
	HipoDataSource reader = new HipoDataSource();
	ArrayList<String> fileList = new ArrayList<>();
	DataEvent event;
	DataBank TBHits;
	DataBank TBSegments;
	DataBank TBTracks;
			
	/**
	 * Constructor for DC calibration histogram
	 */
	public DataProcessor()
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

			//Common contents
			int sector;
			int superLayer;
			double angDeg;
			
			// Content from TBTracks. For each track there are three cross ids belonging to three regions.
			// Each track ----> 3 Crosses (from three regions), Each Cross -----> 2 Segments (from two SL), Each segment ------> Several hits (Clusters)
			// Each cluster ----> Several hits
			
			int[] Cross1_Id;  // size of array == number of tracks
			int[] Cross2_Id;
			int[] Cross3_Id;
			
			
			//------------ Loop over all events in the file -------
			while (reader.hasEvent())
			{
				//--------- Load Event ---------------------------------
				event = reader.getNextEvent();
				if (!event.hasBank("TimeBasedTrkg::TBHits") || !event.hasBank("TimeBasedTrkg::TBSegments") || !event.hasBank("TimeBasedTrkg::TBTracks"))
					continue;

				//---------------- Load All Desired DC Banks -----------------------
				TBHits = event.getBank("TimeBasedTrkg::TBHits");
				TBSegments = event.getBank("TimeBasedTrkg::TBSegments");
				TBTracks = event.getBank("TimeBasedTrkg::TBTracks");
				
				//~~~~~~~~if no track continue~~~~~~~~~~~~~
				// ~~~~~~~~~~ Read track info here ~~~~~
				// Read Cross IDs for each track
				//~~~~~~~ Go to corresponding crosses ~~~~
				//~~~~ Go to corresponding segments ~~~~~
				//~~~~~ Read corresponding hits ~~~~~~~~~
				
				//----------- Loop over all entries (hits) of the event --------
				for (int k = 0; k < TBHits.rows(); k++)
				{
					//--------- Include data selection or cut here ----------------------
					//if (TBHits.getByte("sector", k) == 2 && TBHits.getByte("superlayer", k) == 1)
					{
						sector = TBHits.getByte("sector", k);
						superLayer = TBHits.getByte("superlayer", k);
						angDeg = Math.toDegrees(Math.atan2((double) TBSegments.getFloat("fitSlope", k), 1.0));						
					}
				}
			}			
			reader.close();
		}		
//		new TCanvas("c1", 800, 500).draw(h2d_time_vs_trkDoca);
//		new TCanvas("c2", 800, 500).draw(h1d_timeResidual);
	}
	
	
	public void collectTrackInfo()
	{
		
		
		
	}
	
	public void drawProfileHistogram()
	{
//		GraphErrors prof_time_vs_trkDoca = h2d_time_vs_trkDoca.getProfileY(); 
//		new TCanvas("c3", 800, 500).draw(prof_time_vs_trkDoca);
	}
		
	public static void main(String arg[])
	{
		DataProcessor test = new DataProcessor();
	    test.FillHistograms();
	    test.drawProfileHistogram();
	}

}
