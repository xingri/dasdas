#include "RobotCtrl.h"

#define QTI_BLACK_THRESHOLD 60

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

		delay(5);
		updateSensors();

		if (isLeftSensorBlack &&
			isCenterSensorBlack && isRightSensorBlack)
			break;
	}

	delay(300);

	while (1) {
		turnRight();
		delay(5);

		updateSensors();
		if (!isCenterSensorBlack)
			break;
	}

	stop();
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

	return black_count < 2 ? true : false;
}

void RobotCtrl::updateSensors()
{
	isCenterSensorBlack  = centerSensor.isBlack();
	isLeftSensorBlack = leftSensor.isBlack();
	isRightSensorBlack = rightSensor.isBlack();
}
