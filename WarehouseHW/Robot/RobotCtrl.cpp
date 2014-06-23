#include "RobotCtrl.h"

#define DEBUG 1

#define QTI_BLACK_THRESHOLD 70

void RobotCtrl::init(int leftServoPin, int rightServoPin)
{
	leftServo.attach(leftServoPin);
	rightServo.attach(rightServoPin);
	stop();

	centerSensor.set(2, QTI_BLACK_THRESHOLD);
	leftSensor.set(9, QTI_BLACK_THRESHOLD);
	rightSensor.set(8, QTI_BLACK_THRESHOLD);
}

void RobotCtrl::goForward()
{
	leftServo.write(FULLCCW);
	rightServo.write(FULLCW);
}

void RobotCtrl::goBackward()
{
	leftServo.write(FULLCW);
	rightServo.write(FULLCCW);
}

void RobotCtrl::turnLeft()
{
	leftServo.write(FULLCW);
	rightServo.write(FULLCW);
}

void RobotCtrl::turnRight()
{
	leftServo.write(FULLCCW);
	rightServo.write(FULLCCW);
}

void RobotCtrl::stop()
{
	leftServo.write(FULLSTOP);
	rightServo.write(FULLSTOP);
}

void RobotCtrl::goNextStation()
{
	updateSensors();

	if (isOnNavLine()) {
		if (!isCenterSensorBlack && (isLeftSensorBlack || isRightSensorBlack)) {
			if (isLeftSensorBlack)
				turnLeft();
			else if (isRightSensorBlack)
				turnRight();
		} else {
			goForward();
		}
	} else {
		if (isCenterSensorBlack && (!isLeftSensorBlack || !isRightSensorBlack)) {
			if (!isLeftSensorBlack)
				turnLeft();
			else if (!isRightSensorBlack)
				turnRight();
		} else if (!isCenterSensorBlack && !isRightSensorBlack) {
			// We prefer to turn right at the station
			turnRight();
			delay(5);
		} else {
			goForward();
		}
	}
}

void RobotCtrl::nearStation()
{
	int count = 100;

	for (int i = 0; i < count; i++) {
		goForward();

		// dealy(5); // XXX:
		updateSensors();

		if (isLeftSensorBlack &&
			isCenterSensorBlack && isRightSensorBlack) {
#if DEBUG
			Serial.print("nearStation: start count -> ");
			Serial.println(i);
#endif
			break;
		}
	}

	delay(150);

	count = 0;
	while (1) {
		turnRight();
		//delay(5); // XXX:

		updateSensors();
		if (!isCenterSensorBlack)
			break;
		count++;
	}

	stop();
#if DEBUG
	Serial.print("nearStation: end count -> ");
	Serial.println(count);
#endif
}

boolean RobotCtrl::isOnNavLine()
{
	int black_count = 0;

	if (isCenterSensorBlack)
		black_count++;
	if (isLeftSensorBlack)
		black_count++;
	if (isRightSensorBlack)
		black_count++;

#if DEBUG
	// Serial.print("isOnNavLine: ");
	// Serial.println(black_count < 2 ? true : false);
#endif
	return black_count < 2 ? true : false;
}

void RobotCtrl::updateSensors()
{
#if DEBUG
	// Serial.println("updateSensors..........");
#endif
	isCenterSensorBlack  = centerSensor.isBlack();
	isLeftSensorBlack = leftSensor.isBlack();
	isRightSensorBlack = rightSensor.isBlack();
}
