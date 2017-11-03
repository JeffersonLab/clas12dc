/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
package org.jlab.dc_calibration.test;

import java.io.File;
import java.util.ArrayList;

import org.jlab.dc_calibration.constants.T0SignalCableMap;
import org.jlab.groot.data.H1F;
import org.jlab.groot.ui.TCanvas;
import org.jlab.io.base.DataBank;
import org.jlab.io.base.DataEvent;
import org.jlab.io.hipo.HipoDataSource;

public class HBTimeDistribution
{
	int					nSec				= 6;
	int					nSL					= 6;
	int					nSlots				= 7;
	int					nCables				= 6;
	H1F					histogram[][][][]	= new H1F[nSec][nSL][nSlots][nCables];
	HipoDataSource		reader				= new HipoDataSource();
	ArrayList<String>	fileList			= new ArrayList<>();
	DataEvent			event;
	DataBank			HBHits;
	DataBank			HBSegments;
	DataBank			HBTracks;
	T0SignalCableMap cableMap;
	String title;
	int nEvtProcessed = 0;
	
	/**
	 * Constructor for DC calibration histogram
	 */
	public HBTimeDistribution()
	{
		Init();
	}

	void Init()
	{
		fileList = new FileSelector().fileArray;
		cableMap = new T0SignalCableMap();
		for (int sec = 0; sec < nSec; ++sec)
		{
			for (int sl = 0; sl < nSL; ++sl)
			{
				for (int slot = 0; slot < nSlots; ++slot)
				{
					for (int cable = 0; cable < nCables; ++cable)
					{
						title = "Time Distribution : Sec " + sec + " SL " + sl + "Slot " + slot + "cable" + cable;
						histogram[sec][sl][slot][cable] = new H1F(title, 1000, -200, 800);
					}
				}
			}
		}
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

			// -------- Read the data file -----------
			reader.open(filePath);

			//contents to be read from HBHits
			int sector;
			int superLayer;
			float time;
			float Tprog = 0;
			float Ttof = 0;
			int trkID;
			int slotNo;
			int cableNo;
			int layer;
			int wire;

			// ------------ Loop over all events in the file -------
			while (reader.hasEvent())
			{
				// --------- Load Event ---------------------------------
				event = reader.getNextEvent();
				// Cut 0: Fill only hits that construct a track <---------------- Cut
				//if (!event.hasBank("HitBasedTrkg::HBHits") || !event.hasBank("HitBasedTrkg::HBSegments")
				//		|| !event.hasBank("HitBasedTrkg::HBTracks"))
				//	continue;

				// ---------------- Load All Desired DC Banks -----------------------
				HBHits = event.getBank("HitBasedTrkg::HBHits");
				//HBSegments = event.getBank("HitBasedTrkg::HBSegments");
				//HBTracks = event.getBank("HitBasedTrkg::HBTracks");

				// ----------- Loop over all entries (hits) of the event --------
				for (int k = 0; k < HBHits.rows(); k++)
				{
						sector = HBHits.getByte("sector", k); // sector starts from 1
						superLayer = HBHits.getByte("superlayer", k); // SL starts from 1
						wire = HBHits.getInt("wire", k); // wire goes from 1 to 112 in data
						layer = HBHits.getInt("layer", k); // layer goes from 1 to 6 in data
						slotNo = cableMap.getSlotID1to7(wire); 
						cableNo = cableMap.getCableID1to6(layer, wire);
						
						time = HBHits.getFloat("time", k);
//						Tprog = HBHits.getFloat("Tprog", k);
//						Ttof = HBHits.getFloat("Ttof", k);
						trkID = HBHits.getInt("trkID", k);
						
						//Cut 1: Fill only cut associated hits
						//if(trkID > 0) // <---------------------- Cut
						histogram[sector - 1][superLayer - 1][slotNo - 1][cableNo - 1].fill((time - Tprog - Ttof));
				}
				++nEvtProcessed;
				if(nEvtProcessed % 1000 == 0)
					System.out.println("Number of event processed: " + nEvtProcessed);
			}
			reader.close();
		}
	}

	public static void main(String arg[])
	{
		HBTimeDistribution test = new HBTimeDistribution();
		test.FillHistograms();
		TCanvas c1 = new TCanvas("c1", 800, 600);
		c1.draw(test.histogram[1][3][0][0]);
	}

}
