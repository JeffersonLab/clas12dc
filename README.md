CLAS12 DC Software
=================

- CLAS12 DC Calibration: DC Calibration suite for CLAS12. 

- DC Monitoring: DC Monitoring GUI. 

- CLAS12 Data Explorer: Data Explorer for CLAS12 data. It allows plotting any CLAS12 detector sub-syatem with cuts with few clicks.

Please read the README file inside each package for detailed instructions for that specific package.


Installation
--------------
Use the following commands to install all DC software packages.
It will download the installer script, make it executable and install all packages:

```
wget https://userweb.jlab.org/~latif/Hall_B/clas12dc_install.sh 
chmod +x clas12dc_install.sh
./clas12dc_install.sh
```

The utility `clas` inside the directory `clas12dc` will act as the interface for all CLAS12 related software. 

Interface for all DC related software:`clas` Utility
---------------------------------------------------


```
user@ifarm1402$ clas -h

          ---------------------------------------------------------------------------------
          |										  |
          |                      Welcome to CLAS12 Software Packages			  |
          |	        	  For a list of options type "clas -h"	                  |
          |		           Report issues to: latif@jlab.org			  |
          ---------------------------------------------------------------------------------
                   
          Syntax: clas <OPTION>
                   
          ------------------------------------- List of available options --------------------------------
          tbh               : Plot different variables from TBHits bank
          t2d               : Compare time-to-distance function used in reconstruction and calibration suite
          explorer          : Open Data Explorer GUI
          t0                : Estimate T0 correction
          calib             : Open DC Calibration GUI
          clas12mon         : Open Clas12 monitoring GUI
          dcmon             : Open DC monitoring GUI
          effi              : Open DC efficiency studies GUI
          decode            : Decode evio files to Hipo files
          cook_local        : Data Cooking on local machine
          cook_farm         : Data Cooking on farm machine
          cooking_status    : Farm Data Cooking Status
          cancel_jobs       : Cancel submitted farm jobs
          det_def           : Print Bank information of the detector given as 2nd argument
          bank_def          : Print variable information of the detector and bank.
                              Issue detector name followed by bank name
          ced               : Open CLAS12 event display
          update            : Update CLARA and CoatJava to latest version
          add2ccdb          : Upload file to CCDB
          addT02ccdb        : Upload T0 correction to CCDB
          run_info          : Get Run Information
          elog              : Make an elog
          email             : Send email to jlab user or staff
          log_book_search   : Retrieve log book info for specified word
          rcdb_info         : Retrieve rcdb info
          install_clara     : Install or update Clara
          install_ced       : Install or update CED
          version           : Print version information for CoatJava and CED
          ------------------------------------------------------------------------------------------------
                    
```
