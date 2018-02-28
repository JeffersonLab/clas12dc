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
		System.out.println("Select the input files .......");		
		Init();
	}
	
	public FileSelector(boolean selectDir)
	{
		if(selectDir)
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		System.out.println("Select output directory .......");
		Init();
	}

	void Init()
	{
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
