#include "Arduino.h"
#include <Servo.h>
#include "QTISensor.h"

#ifndef RobotCtrl_h
#define RobotCtrl_h

#define FULLSTOP  90           // PWM value for servo full stop                                                        
#define FULLCW  0              // PWM value for servo full stop
#define FULLCCW 180           // PWM value for servo full stop

/* Location */
#define LOC_ON_NAVLINE 0
#define LOC_NEAR_STATION 1
#define LOC_WHITE 2
#define LOC_BLACK 3

#define OP_FORWARD 0
#define OP_TURNLEFT 1
#define OP_TURNRIGHT 2
#define OP_NONE 4

class RobotCtrl {
private:
	Servo leftServo;
	Servo rightServo;

	QTISensor centerSensor;
	QTISensor leftSensor;
	QTISensor rightSensor;

	boolean isCenterSensorBlack;
	boolean isLeftSensorBlack;
	boolean isRightSensorBlack;

	boolean isOnNavLine();
	int getLocation();
	void updateSensors();

	int wrongLocCount;

public:
	void init(int leftServoPin, int rightServoPin);
	void goForward();
	void goBackward();
	void turnLeft();
	void turnRight();
	int goNextStation();
	void nearStation();
	void stop();
};

#endif

