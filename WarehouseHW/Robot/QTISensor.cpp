#include "QTISensor.h"

#define DEBUG 0

void QTISensor::set(int pin, int bThreshold)
{
	_pin = pin;
	blackThreshold = bThreshold;
}

long QTISensor::RCTime()
{
	long duration = 0;
	pinMode(_pin, OUTPUT);		// Sets pin as OUTPUT
	digitalWrite(_pin, HIGH);	// Pin HIGH
	delay(1);
	pinMode(_pin, INPUT);		// Sets pin as INPUT
	digitalWrite(_pin, LOW);	// Pin LOW

	while (digitalRead(_pin) != 0) {	// Count until the pin goes
		duration++;					// LOW (cap discharges)
	}

#if DEBUG
	Serial.print("QTISensor: [");
	Serial.print(_pin);
	Serial.print("] ");
	Serial.println(duration);
#endif

	return duration;	// Returns the duration of the pulse
}

boolean QTISensor::isBlack()
{
	if (RCTime() > blackThreshold)
		return true;
	else
		return false;
}
