#!/bin/bash

# Filename: clas12dc_download.sh
# Description: Installer for CLAS12 DC software packages
# Author: Latif Kabir < latif@jlab.org >
# Created: Tue Mar 13 01:45:37 2018 (-0400)
# URL: jlab.org/~latif

rm -rf clas12dc

wget https://userweb.jlab.org/~latif/Hall_B/clas12dc.tar.gz

tar -xvf clas12dc.tar.gz 

rm -f clas12dc.tar.gz

chmod +x clas12dc/* 
chmod +x clas12dc/bin/* 
