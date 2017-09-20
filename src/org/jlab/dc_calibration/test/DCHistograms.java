/**
 * 
 */
package org.jlab.dc_calibration.test;

import java.util.Map;
import java.util.HashMap;

import org.jlab.dc_calibration.init.Coordinate;
import org.jlab.geom.base.Sector;
import org.jlab.groot.data.H1F;
import org.jlab.groot.data.H2F;

import static org.jlab.dc_calibration.test.DCConstants.nSectors;
import static org.jlab.dc_calibration.test.DCConstants.nSL;
import static org.jlab.dc_calibration.test.DCConstants.nThBins;
import static org.jlab.dc_calibration.test.DCConstants.thRange;

/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
public class DCHistograms
{

	//----------------- Variable definitions------------------
	//  S : Sector
	//  SL: Super Layer
	//  m3 : Three dimensional map
	//  h3 : Three dimensional histogram
	
	//---------- List of Histograms ----------------
	Map<Coordinate, H2F> m3h2timeVsTrkDoca = new HashMap<Coordinate, H2F>();  // m3: (S, SL, Angle)
	Map<Coordinate, H1F> m2h1Residual = new HashMap<Coordinate, H1F>(); // m2: (S, SL)
	
	String histTitle;
	
	
// 	protected	H2F h2timeVsTrkDoca = new H2F("time vs trkDoca", 100, -150, 800.0,100, 0, 2.0 );
//	protected	H1F h1Residual = new H1F("Time Residual", 100, -1.0, 1.0);
	
	
	//-------------- Initialize all histograms -------------------
	protected void InitilizeHistograms()
	{
		for ( int sector = 0; sector < nSectors; ++sector)
		{
			for (int superLayer = 0; superLayer < nSL; ++superLayer)
			{
				
				//--------------- Time vs trkDoca for (S, SL, Angle) -------------
				for(int angBin = 0; angBin < nThBins; ++ angBin)
				{
					histTitle = "Time vs trkDoca S" + sector + " SL" + superLayer + " AngBin: ( " + thRange[angBin] + "," + thRange[angBin+1] + ")";  
					m3h2timeVsTrkDoca.put(new Coordinate(sector,superLayer,angBin), new H2F(histTitle , 100, 0.0, 2.0, 100, -150, 800));
				}				
				//--------------- Time Residual for (S, SL) -------------
				histTitle = "Time Residual" + sector + " SL" + superLayer;
				m2h1Residual.put(new Coordinate(sector, superLayer), new H1F(histTitle, 100, -1.0, 1.0));				
			}
		}
	}
}
