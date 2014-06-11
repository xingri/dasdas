#include "Arduino.h"

#ifndef QTISensor_h
#define QTISensor_h

class QTISensor {
public:
	void set(int pin, int bThreshold);
	boolean isBlack();

private:
	int _pin;
	int blackThreshold;
	long RCTime();
};

#endif