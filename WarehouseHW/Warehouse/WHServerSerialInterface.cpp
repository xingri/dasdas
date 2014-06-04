#include "WHServerSerialInterface.h"

WHServerSerialInterface::WHServerSerialInterface()
{
	Serial.begin(9600);
}

int WHServerSerialInterface::reportStatus(InventoryStation *stations, int numOfStations)
{
	Serial.print(numOfStations);

	for (int i = 0; i < numOfStations; i++) {
		Serial.print(",");
		Serial.print(stations[i].isRobotArrived());
	}

	for (int i = 0; i < numOfStations; i++) {
		Serial.print(",");
		Serial.print(stations[i].isLoadingDone());
	}

	Serial.println("");
}

