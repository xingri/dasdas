#include "Arduino.h"
#include "InventoryStation.h"

#ifndef WHServerInterface_h
#define WHServerInterface_h

class WHServerInterface {
public:
	virtual int reportStatus(InventoryStation *stations, int numOfStations) = 0;
	virtual ~WHServerInterface() {}
};

#endif

