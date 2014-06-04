#include "InventoryStation.h"

InventoryStation::InventoryStation(int lightPin, int lightThreshold, int switchPin)
{
	_lightPin = lightPin;
	_lightThreshold = lightThreshold;
	_switchPin = switchPin;

	pinMode(_switchPin, INPUT);
}

boolean InventoryStation::isRobotArrived()
{
	return analogRead(_lightPin) < _lightThreshold ? 1 : 0;
}

boolean InventoryStation::isLoadingDone()
{
	return !digitalRead(_switchPin);
}

