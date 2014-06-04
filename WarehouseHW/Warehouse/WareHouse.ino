#include "InventoryStation.h"
#include "WHServerSerialInterface.h"

#define NUM_OF_STATIONS 4
#define LIGHT_THRESHOLD 600

InventoryStation stations[NUM_OF_STATIONS] = {
	InventoryStation(0, LIGHT_THRESHOLD, 3),
	InventoryStation(1, LIGHT_THRESHOLD, 5),
	InventoryStation(2, LIGHT_THRESHOLD, 6),
	InventoryStation(3, LIGHT_THRESHOLD, 9)
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
