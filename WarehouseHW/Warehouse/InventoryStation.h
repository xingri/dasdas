#include "Arduino.h"

#ifndef InventoryStation_h
#define InventoryStation_h

class InventoryStation {
public:
	InventoryStation(int lightPin, int switchPin);
	boolean isRobotArrived();
	boolean isLoadingDone();
private:
	int _lightPin;
	int _switchPin;
};

#endif

