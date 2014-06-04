#include "Arduino.h"
#include "WHServerInterface.h"

#ifndef WHServerSerialInterface_h
#define WHServerSerialInterface_h

class WHServerSerialInterface : public WHServerInterface {
public:
	WHServerSerialInterface();

	int reportStatus(InventoryStation *stations, int numOfStations);
};

#endif

