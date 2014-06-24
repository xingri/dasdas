#include "Arduino.h"
#include <avr/wdt.h>
#include <avr/interrupt.h>

#ifndef Watchdog_h
#define Watchdog_h

typedef void (*WatchdogCallback)();

class Watchdog {
	public:
		static WatchdogCallback cb;
		static int timeOut;
		static bool resetFlag;

		static int getPrescaler() {
			int ret = 0;

			switch (timeOut) {
				/* TODO: we support only 8S */
				case WDTO_8S:
				default:
					ret = _BV(WDP0) | _BV(WDP3); /* 8 secs */
					break;
			}

			return ret;
		}

		static void configure() {
			/* A 'timed' sequence is required for configuring the WDT, so we need to
			 * disable interrupts here.
			 */
			cli();
			wdt_reset();
			MCUSR &= ~_BV(WDRF);
			/* Start the WDT Config change sequence. */
			WDTCSR |= _BV(WDCE) | _BV(WDE);
			/* Configure the prescaler and the WDT for interrupt mode only*/
			WDTCSR =  getPrescaler()| _BV(WDIE);
			sei();
		}

		static void init(WatchdogCallback cbFunc, int timeout) {
			Watchdog::cb = cbFunc;
			Watchdog::timeOut = timeout;

			configure();
		}
};

#endif
