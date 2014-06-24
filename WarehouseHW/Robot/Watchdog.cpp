#include "Watchdog.h"

int Watchdog::timeOut = WDTO_8S;
WatchdogCallback Watchdog::cb = NULL;
bool Watchdog::resetFlag = false;

ISR(WDT_vect)
{
	if (Watchdog::cb != NULL)
		Watchdog::cb();

	if (Watchdog::resetFlag) {
	    // The app has locked up, force a WDR.
	    wdt_enable(WDTO_15MS);
	    while(1);
	} else {
		wdt_reset();
		Watchdog::configure();
	}
}
