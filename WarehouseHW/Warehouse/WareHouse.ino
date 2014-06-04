#include "InventoryStation.h"
#include "WHServerSerialInterface.h"

#define NUM_OF_STATIONS 4

InventoryStation stations[NUM_OF_STATIONS] = {
	InventoryStation(0, 3),
	InventoryStation(1, 5),
	InventoryStation(2, 6),
	InventoryStation(3, 9)
};

WHServerInterface * server;

void setup()
{
	server = new WHServerSerialInterface();
}

void loop()
{
	server->reportStatus(stations, NUM_OF_STATIONS);
	delay(100);
}

