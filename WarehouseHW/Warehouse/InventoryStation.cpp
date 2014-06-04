#include "InventoryStation.h"

InventoryStation::InventoryStation(int lightPin, int switchPin)
{
	_lightPin = lightPin;
	_switchPin = switchPin;

	pinMode(_switchPin, INPUT);
}

boolean InventoryStation::isRobotArrived()
{
	return analogRead(_lightPin);
}

boolean InventoryStation::isLoadingDone()
{
	return digitalRead(_switchPin);
}

