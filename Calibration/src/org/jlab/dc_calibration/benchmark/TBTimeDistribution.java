/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
package org.jlab.dc_calibration.benchmark;

import java.io.File;
import java.util.ArrayList;

import org.jlab.dc_calibration.constants.T0SignalCableMap;
import org.jlab.groot.data.H1F;
import org.jlab.groot.ui.TCanvas;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;

public class TBTimeDistribution extends T0Estimator
{
	HipoDataSource		reader				= new HipoDataSource();
	ArrayList<String>	fileList			= new ArrayList<>();
	DataEvent			event;
	DataBank			TBHits;
	DataBank			TBSegments;
	DataBank			TBTracks;
	T0SignalCableMap cableMap;
	int nEvtProcessed = 0;
	
	/**
	 * Constructor for TBTimeDistribution
	 */
	public TBTimeDistribution()
	{
		fileList = new FileSelector().fileArray;
		cableMap = new T0SignalCableMap();
	}

	public void FillHistograms()
	{
		// ------------ Loop over all selected files -------------
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

			// Read the data file 
			reader.open(filePath);

			//contents to be read from HBHits
			int sector;
			int superLayer;
			int TDC;
			float time;
			float TProp = 0;
			float TFlight = 0;
			float TStart = 0;
			int trkID;
			int slotNo;
			int cableNo;
			int layer;
			int wire;
			int clusterID;

			// ------------ Loop over all events in the file -------
			while (reader.hasEvent())
			{
				// --------- Load Event ---------------------------------
				event = reader.getNextEvent();
				
				if (!event.hasBank("TimeBasedTrkg::TBHits")) 
					continue;
				
				// ---------------- Load All Desired DC Banks -----------------------
				TBHits = event.getBank("TimeBasedTrkg::TBHits");

				// ----------- Loop over all entries (hits) of the event --------
				for (int k = 0; k < TBHits.rows(); k++)
				{
						sector = TBHits.getByte("sector", k);                      // sector starts from 1
						superLayer = TBHits.getByte("superlayer", k);              // SL starts from 1
						wire = TBHits.getInt("wire", k);                           // wire goes from 1 to 112 in data
						layer = TBHits.getInt("layer", k);                         // layer goes from 1 to 6 in data
						slotNo = cableMap.getSlotID1to7(wire);                     // slotNo goes 1 to 7
						cableNo = cableMap.getCableID1to6(layer, wire);            // cableNo goes 1 to 6
						
						TDC = TBHits.getInt("TDC", k);
						TProp = TBHits.getFloat("TProp", k);
						TFlight = TBHits.getFloat("TFlight", k);
						TStart = TBHits.getFloat("TStart", k);
						trkID = TBHits.getInt("trkID", k);
						clusterID = TBHits.getShort("clusterID", k);
						
						time = TDC - TProp - TFlight - TStart;

						//Cut 1: Fill only track associated hits
						if(trkID <= 0 || clusterID == -1)                          // Cut
							continue;	
						if(TProp == 0 || TFlight == 0 || TStart == 0)
							continue;
						histogram[sector - 1][superLayer - 1][slotNo - 1][cableNo - 1].fill((time));
				}
				++nEvtProcessed;
				if(nEvtProcessed % 5000 == 0)
					System.out.println("----->Number of events processed: " + nEvtProcessed + "<--------");
			}
			reader.close();
		}
	}
		
	public static void main(String arg[])
	{
		TBTimeDistribution test = new TBTimeDistribution();
		test.FillHistograms();
		TCanvas c1 = new TCanvas("c1", 800, 600);
		c1.draw(test.histogram[1][3][0][0]);
	}
}
