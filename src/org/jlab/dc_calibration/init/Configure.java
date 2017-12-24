//Filename: Config.java
//Description: 
//Author: Latif Kabir < latif@jlab.org >
//Created: Sun Dec 24 00:16:14 2017 (-0500)
//URL: jlab.org/~latif

package org.jlab.dc_calibration.init;

import java.util.Properties;

public class Configure
{
	Properties configFile;
	public static int Sector;
	public static int iSecMax;
	public static int iSecMin;
	public static String BField;
	
	public Configure(String config_file)
	{
		configFile = new java.util.Properties();
		try
		{
			configFile.load(this.getClass().getClassLoader().getResourceAsStream(config_file));
		}
		catch (Exception eta)
		{
			eta.printStackTrace();
		}
	}

	public String getProperty(String key)
	{
		String value = configFile.getProperty(key);
		return value;
	}

	public static void setConfig()	
	{
		Configure config = new Configure("config/config.cfg");
		Sector = Integer.parseInt(config.getProperty("sector"));
		iSecMax = Integer.parseInt(config.getProperty("iSecMax"));
		iSecMin = Integer.parseInt(config.getProperty("iSecMin"));
		BField = config.getProperty("BField");		
	}
	
	public static void main(String[] args)
	{
		Configure.setConfig();
		System.out.println(" Sector: " + Configure.Sector + " iSecMax: " + Configure.iSecMax + " iSecMin: " + Configure.iSecMin + " BField: " + Configure.BField);
	}
}
