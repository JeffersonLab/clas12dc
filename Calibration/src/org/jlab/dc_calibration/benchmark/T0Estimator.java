/**
 * 
 */
package org.jlab.dc_calibration.benchmark;

import org.jlab.groot.data.H1F;

/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
public class T0Estimator
{
	int		nSec				= 6;
	int		nSL					= 6;
	int		nSlots				= 7;
	int		nCables				= 6;
	H1F		histogram[][][][]	= new H1F[nSec][nSL][nSlots][nCables];
	String	title;

	/**
	 * Constructor
	 */
	public T0Estimator()
	{
		Init();
	}
	
	void Init()
	{
		for (int sec = 0; sec < nSec; ++sec)
		{
			for (int sl = 0; sl < nSL; ++sl)
			{
				for (int slot = 0; slot < nSlots; ++slot)
				{
					for (int cable = 0; cable < nCables; ++cable)
					{
						title = "Time : Sec " + (sec + 1) + " SL " + (sl + 1) + "Slot " + (slot + 1) + "cable" + (cable + 1);
						histogram[sec][sl][slot][cable] = new H1F(title, 1000, 0, 1000);
					}
				}
			}
		}
	}
}
