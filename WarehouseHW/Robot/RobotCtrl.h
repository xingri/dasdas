#include "Arduino.h"
#include <Servo.h>
#include "QTISensor.h"

#ifndef RobotCtrl_h
#define RobotCtrl_h

#define FULLSTOP  90           // PWM value for servo full stop                                                        
#define FULLCW  0              // PWM value for servo full stop
#define FULLCCW  180           // PWM value for servo full stop

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
	void updateSensors();

public:

	void init(int leftServoPin, int rightServoPin);
	void goForward();
	void goBackward();
	void turnLeft();
	void turnRight();
	void goNextStation();
	void nearStation();
	void stop();
};

#endif

