 	program elec_wire_map
c
c	author: Mac Mestayer
c	date:  Nov. 6, 2015
c       I modified dc12-readout-to-wire-map.for to work on
c       an input of dcrb crate, slot, connector, pin.
c 	purpose: for a given crate(1-18),slot(4-10,13-19),connector(1-6),
c       and pin(1-16) return the sector(1-6), layer(1-36)
c       and wire(1-112); i.e. the wire identification as well as the
c       wire's readout status and its cable delay
c
	implicit none
c
c  input arguments: region,sector,slot1,connector1,slot2,connector2
c   where the "1" and "2" refer to the 1st and 2nd of the swapped pair
c
c  Note that crate, slot, connector, pin refers to the DCRB system
c  When referring to the stbboard or stbconnector, they are written out
c
	integer slot,channel,connector,pin,ipin
	integer slot1,slot2,connector1,connector2
c
c
c  output arguments: sector,layer,wire
c
	integer sector,layer,wire
c  other variables
c  loclayer (1-6), locsuplayer (1-2), suplayer (1-6), locwire (1-16)
	integer region,loclayer,locsuplayer,suplayer,locwire
c  nstb is the stbboard number, stbconnector the stb conn. number
	integer nstb,stbconnector
c  these are the board numbers of the two swapped connectors
	integer board1,board2
c
c  the following parameters (1st letter m) are used as array dimensions
c
	integer msector,mlayer,mwire,mcrate,mlocsuplayer,mloclayer
	integer mlocwire,mstbboard,mslot,mconn,mpin,mcable,mdcrbslot
c
	parameter (msector=6)
	parameter (mlayer=36)
	parameter (mwire=112)
	parameter (mcrate=18)
	parameter (mlocsuplayer=2)
	parameter (mloclayer=6)
	parameter (mlocwire=16)
	parameter (mstbboard=7)
	parameter (mslot=20)
	parameter (mconn=6)
	parameter (mpin=16)
	parameter (mcable=84)
	parameter (mdcrbslot=14)
c
	integer iswap,layer1,layer2,wire1,wire2
c 
c
c map of crate to sector
	integer crate_sector(mcrate)
	data crate_sector/1,2,3,4,5,6,1,2,3,4,5,6,1,2,3,4,5,6/
c
c map of crate to region
	integer crate_region(mcrate)
	data crate_region/1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,3,3/
c
c map of pin to local layer for each local stb connector position
c
	integer chan_loclayer(mpin,mconn)
	data chan_loclayer/2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,
	1    3,5,2,4,6,1,3,5,2,4,6,1,3,5,2,4,
	1    6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5,
	2    2,4,6,1,3,5,2,4,6,1,3,5,2,4,6,1,
	2    3,5,2,4,6,1,3,5,2,4,6,1,3,5,2,4,
	3    6,1,3,5,2,4,6,1,3,5,2,4,6,1,3,5/
c
c map of pin to local wire (analagous to chan_loclayer)
c
	integer chan_locwire(mpin,mconn)
	data chan_locwire/1,1,1,1,1,1,2,2,2,2,2,2,3,3,3,3,
	1    3,3,4,4,4,4,4,4,5,5,5,5,5,5,6,6,
	1    6,6,6,6,7,7,7,7,7,7,8,8,8,8,8,8,
	2    9,9,9,9,9,9,10,10,10,10,10,10,11,11,11,11,
	2    11,11,12,12,12,12,12,12,13,13,13,13,13,13,14,14,
	3    14,14,14,14,15,15,15,15,15,15,16,16,16,16,16,16/
c
c  map of DCRB slot, connector to stb board number
c   the numbers in each row are the stb board number
c   for the DCRB connector in question (1,2,3,4,5 and 6)
c
	integer stb(mslot,mconn)
	data stb/0,0,0,1,2,3,4,5,6,7,0,0,1,2,3,4,5,6,7,0,
	1    0,0,0,1,2,3,4,5,6,7,0,0,1,2,3,4,5,6,7,0,
	1    0,0,0,1,2,3,4,5,6,7,0,0,1,2,3,4,5,6,7,0,
	2    0,0,0,1,2,3,4,5,6,7,0,0,1,2,3,4,5,6,7,0,
	2    0,0,0,1,2,3,4,5,6,7,0,0,1,2,3,4,5,6,7,0,
	3    0,0,0,1,2,3,4,5,6,7,0,0,1,2,3,4,5,6,7,0/
c
c  map of DCRB slot, connector to stb connector number
c   the numbers in each row are the stb connector number
c   for the DCRB connector in question (1,2,3,4,5 and 6)
c
	integer stbconn(mslot,mconn)
	data stbconn/0,0,0,1,1,1,1,1,1,1,0,0,1,1,1,1,1,1,1,0,
	1    0,0,0,2,2,2,2,2,2,2,0,0,2,2,2,2,2,2,2,0,
	1    0,0,0,3,3,3,3,3,3,3,0,0,3,3,3,3,3,3,3,0,
	2    0,0,0,4,4,4,4,4,4,4,0,0,4,4,4,4,4,4,4,0,
	2    0,0,0,5,5,5,5,5,5,5,0,0,5,5,5,5,5,5,5,0,
	3    0,0,0,6,6,6,6,6,6,6,0,0,6,6,6,6,6,6,6,0/
c
c  map of DCRB slot to local superlayer
c
	integer slot_locsuplayer(mslot)
	data slot_locsuplayer/0,0,0,1,1,1,1,1,1,1,0,0,2,2,2,2,2,2,2,0/
c  cableid is the cable id. number, status is the status of the
c  an individual crate,slot connector
	integer cableid(mslot,mconn),cable_id
	data cableid/0,0,0,1,2,3,4,5,6,7,0,0,8,9,10,10,12,13,14,0,
	1    0,0,0,15,16,17,18,19,20,21,0,0,22,23,24,25,26,27,28,0,
	1    0,0,0,29,30,31,32,33,34,35,0,0,36,37,38,39,40,41,42,0,
	2    0,0,0,43,44,45,46,47,48,49,0,0,50,51,52,53,54,55,56,0,
	2    0,0,0,57,58,59,60,61,62,63,0,0,64,65,66,67,68,69,70,0,
	3    0,0,0,71,72,73,74,75,76,77,0,0,78,79,80,81,82,83,84,0/
c
c  cabledelay is the delay time of the cable
	real*4 cabledelay(mcable),delay
	data cabledelay/84*5./
c
c  status is the status of the cable, 1 if good
	integer status(mcable),istat
	data status/84*1/
c
c
c  read in values of crate,slot,channel
c
	  print 100
 100	  format('What is the region? (1-3)')
	read (*,101) region
 101	format(i6)
c--------------------------------------------
	  print 105
 105	  format('What is the sector? (1-6)')
	read (*,106) sector
 106	format(i6)
c--------------------------------------------
	  print 198
 198	  format(' what is the local superlayer? (1-2)')
	read (*,199) locsuplayer
 199	format(i6)
c--------------------------------------------
	  print 200
 200	  format(' what is the first board number? (1-7)')
	read (*,201) board1
 201	format(i6)
c--------------------------------------------
	  print 202
 202	  format(' what is the second board number? (1-7)')
 	read (*,203) board2
 203	format(i6)
c--------------------------------------------
	  print 300
 300	  format(' what is the first connector number? (1-6) ')
	read (*,301) connector1
 301	format(i6)
c--------------------------------------------
	  print 302
 302	  format(' what is the second connector number? (1-6) ')
	read (*,303) connector2
 303	format(i6)
c--------------------------------------------
c  look-up values based on input crate, slot, channel
c  first, turn channel into connector and pin
c       
c---------------------------------------------------------------------
c       loop to produce a table of sector1,layer1,wire1,sector2,layer2,wire2
c

	do ipin=1,16
	   do iswap=1,2
	      connector=connector1
	      slot=3+(locsuplayer-1)*9+board1
	      slot1=slot
	      if (iswap.eq.2) then
		 connector=connector2
		 slot=3+(locsuplayer-1)*9+board2
		 slot2=slot
	      endif
c
c       determine channel, pin; sector and region are known
	      channel=(connector-1)*16+ipin
	      pin=ipin
c       
c       table look-ups
c       
	      nstb=stb(slot,connector)
	      stbconnector=stbconn(slot,connector)
	      loclayer=chan_loclayer(pin,connector)
	      locwire=chan_locwire(pin,connector)
	      locsuplayer=slot_locsuplayer(slot)
	      cable_id=cableid(slot,connector)
	      istat=status(cable_id)
	      delay=cabledelay(cable_id)
c       
c       now calculate layer and wire numbers
c       
	      suplayer=(region-1)*2 + locsuplayer
	      layer=(suplayer-1)*6 + loclayer
	      wire=(nstb-1)*16 + locwire
	      if(iswap.eq.1) then
		 layer1=layer
		 wire1=wire
	      else
		 layer2=layer
		 wire2=wire
	      endif
c       
	   enddo
c  only print header once
	   if(pin.eq.1) then
	   print 499
	   endif
 499	   format('sector1,layer1,wire1,sector2,layer2,wire2')
	      print 500,sector,layer1,wire1,sector,layer2,wire2
 500	      format (6i6)

	enddo
c--------------------------------------------------------------
c---------------------------------------------------------------------
c       loop AGAIN to produce a table of sector1,layer1,wire1,sector2,layer2,wire2
C          this time simply change the order in the print-out
	do ipin=1,16
	   do iswap=1,2
	      connector=connector1
	      slot=slot1
	      if (iswap.eq.2) then
		 connector=connector2
		 slot=slot2
	      endif
c       determine channel, pin; sector and region are known
	      channel=(connector-1)*16+ipin
	      pin=ipin
c       
c       table look-ups
c       
	      nstb=stb(slot,connector)
	      stbconnector=stbconn(slot,connector)
	      loclayer=chan_loclayer(pin,connector)
	      locwire=chan_locwire(pin,connector)
	      locsuplayer=slot_locsuplayer(slot)
	      cable_id=cableid(slot,connector)
	      istat=status(cable_id)
	      delay=cabledelay(cable_id)
c       
c       now calculate layer and wire numbers
c       
	      suplayer=(region-1)*2 + locsuplayer
	      layer=(suplayer-1)*6 + loclayer
	      wire=(nstb-1)*16 + locwire
	      if(iswap.eq.1) then
		 layer1=layer
		 wire1=wire
	      else
		 layer2=layer
		 wire2=wire
	      endif
c       
	   enddo
	      print 501,sector,layer2,wire2,sector,layer1,wire1
 501	      format (6i6)

	enddo
c--------------------------------------------------------------

c---------------------------------------------------------------------

	stop
	end
