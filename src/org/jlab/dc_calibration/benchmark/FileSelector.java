/**
 * @author Latif Kabir < jlab.org/~latif >
 *
 */
package org.jlab.dc_calibration.benchmark;

import java.io.File;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class FileSelector
{
    JFileChooser fc = new JFileChooser();
    File[] fileList = null;
    public ArrayList<String> fileArray = new ArrayList<String>();

	/**
	 * Constructor
	 */
	public FileSelector()
	{
		Init();
	}

	void Init()
	{
		System.out.println("Select the files .......");

		fc.setMultiSelectionEnabled(true);
		int result = fc.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION)
		{

			fileList = fc.getSelectedFiles();
			for (File file : fileList)
				fileArray.add(file.toString());
		}
		else if (result == JFileChooser.CANCEL_OPTION)
		{
			System.out.println("Cancel was selected");
			return;
		}
	}
}
