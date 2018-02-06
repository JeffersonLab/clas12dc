/*  +__^_________,_________,_____,________^-.-------------------,
 *  | |||||||||   `--------'     |          |                   O
 *  `+-------------USMC----------^----------|___________________|
 *    `\_,---------,---------,--------------'
 *      / X MK X /'|       /'
 *     / X MK X /  `\    /'
 *    / X MK X /`-------'
 *   / X MK X /
 *  / X MK X /
 * (________(                @author m.c.kunkel, kpadhikari, Latif Kabir
 *  `------'
 */
package org.jlab.dc_calibration.ui;

import org.jlab.dc_calibration.init.Configure;

public class Application
{
	public static void main(String[] args)
	{
		CalibStyle.setStyle();
		Configure.setConfig();
		DC_Calibration DcCalib = new DC_Calibration();
		DcCalib.Initialize();
	}
}
