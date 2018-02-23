CLAS12 DC Software
=================

- CLAS12 DC Calibration: DC Calibration suite for CLAS12. 

- DC Monitoring: DC Monitoring GUI. 

- CLAS12 Data Explorer: Data Explorer for CLAS12 data. It allows plotting any CLAS12 detector sub-syatem with cuts with few clicks.

Please read the README file inside each package for detailed instructions for that specific package.


Interface for all DC related software
-------------------------------------


```
user@ifarm1402$ clas12dc -h
          ---------------------------------------------------------------------------------
          |										  |
          |                     Welcome to DC Software Packages			          |
          |		  For a list of options type "clas12dc -h"			  |
          |		     Report issues to: latif@jlab.org				  |
          ---------------------------------------------------------------------------------
                   
          Syntax: clas12dc <OPTION>
                   
          ------------------------------------- List of available options --------------------------------
          tbh               : Plot different variables from TBHits bank
          t2d               : Compare time-to-distance function used in reconstruction and calibration suite
          explorer          : Open Data Explorer GUI
          t0                : Estimate T0 correction
          calib             : Open DC Calibration GUI
          clas12mon         : Open Clas12 monitoring GUI
          dcmon             : Open DC monitoring GUI
          effi              : Open DC efficiency studies GUI
          cook_local        : Data Cooking on local machine
          cook_farm         : Data Cooking on farm machine
          cooking_status    : Farm Data Cooking Status
          ------------------------------------------------------------------------------------------------
```
