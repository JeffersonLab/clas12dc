/**
 * 
 */
package org.jlab.dc_calibration.benchmark;

/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
public final class DCConstants
{

	
	public static final double[] wpdist = // Wire to plane distance:  X_max = 2 x Wire to plane distance
	{ 0.386160, 0.404220, 0.621906, 0.658597, 0.935140, 0.977982 };
	public static final int nSL = 6;  // Number of super layers
	public static final int nSectors = 6;  //  Number of sectors
	public static final int nLayer = 6;  //  Number of layers
	public static final double[] docaBins = { -0.8, -0.6, -0.4, -0.2, -0.0, 0.2, 0.4, 0.6, 0.8 };
	public static final int nFitPars = 10;// 9;//v0, deltamn, tmax, distbeta, delta_bfield_coefficient, b1, b2, b3, b4, deltaT0;	

	
	public static final int nThBins = 16; // Number of theta bins, the number is one less than number of limits
	public static final double[] thRange =
	{ -55, -45, -35, -25, -20, -15, -10, -6, -2, 2, 6, 10, 15, 20, 25, 35, 45 };


}
