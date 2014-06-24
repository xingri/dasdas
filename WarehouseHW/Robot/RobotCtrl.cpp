#include "RobotCtrl.h"

#define DEBUG 1

#define QTI_BLACK_THRESHOLD 70

static int sectionChangeCount = 0;

void RobotCtrl::init(int leftServoPin, int rightServoPin)
{
	leftServo.attach(leftServoPin);
	rightServo.attach(rightServoPin);
	stop();

	centerSensor.set(2, QTI_BLACK_THRESHOLD);
	leftSensor.set(9, QTI_BLACK_THRESHOLD);
	rightSensor.set(8, QTI_BLACK_THRESHOLD);

	wrongLocCount = 0;
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
	// Serial.println("turnRight");
	leftServo.write(FULLCCW);
	rightServo.write(FULLCCW);
}

void RobotCtrl::stop()
{
	leftServo.write(FULLSTOP);
	rightServo.write(FULLSTOP);
}

int RobotCtrl::goNextStation()
{
	int ret = 0;
	static bool isLocChanges = false;

	updateSensors();

#if 0
	static int prevOP = OP_FORWARD;

	switch (getLocation()) {
		case LOC_ON_NAVLINE:
		if (!isCenterSensorBlack && (isLeftSensorBlack || isRightSensorBlack)) {
			if (isLeftSensorBlack) {
				//Serial.println("NAVLINE: turnLeft");
				turnLeft();
				prevOP = OP_TURNLEFT;
			} else if (isRightSensorBlack) {
				//Serial.println("NAVLINE: turnRight");
				turnRight();
				prevOP = OP_TURNRIGHT;
			}
		} else {
			//Serial.println("NAVLINE: goForward");
			if (prevOP == OP_TURNLEFT) {
				turnRight();
				delay(50);
			} else if (prevOP == OP_TURNRIGHT) {
				turnLeft();
				delay(50);
			}
			goForward();
			prevOP = OP_FORWARD;
		}
		break;
		case LOC_NEAR_STATION:
		if (isCenterSensorBlack && (!isLeftSensorBlack || !isRightSensorBlack)) {
			if (!isLeftSensorBlack) {
				turnLeft();
				prevOP = OP_TURNLEFT;
			} else if (!isRightSensorBlack) {
				turnRight();
				prevOP = OP_TURNRIGHT;
			}
		} else {
			if (prevOP == OP_TURNLEFT) {
				turnRight();
				delay(50);
			} else if (prevOP == OP_TURNRIGHT) {
				turnLeft();
				delay(50);
			}
			goForward();
			prevOP = OP_FORWARD;
		}
		break;
		case LOC_WHITE:
		case LOC_BLACK:
		switch (prevOP) {
			case OP_FORWARD:
			goForward();
			break;
			case OP_TURNLEFT:
			turnLeft();
			break;
			case OP_TURNRIGHT:
			turnRight();
			break;
			case OP_NONE:
			stop();
			break;
		}
		wrongLocCount++;
		if (wrongLocCount > 5000) {
			stop();
			wrongLocCount = 0;
			Serial.println("WARNING: wrong location");
			prevOP = OP_NONE;
			ret = -1;
		}
		break;
	}
#else
	if (isOnNavLine()) {
		if (isLocChanges) {
			Serial.print(">>>>> On Line: ");
			Serial.println(sectionChangeCount);
			sectionChangeCount = 0;
			isLocChanges = false;
		}

		if (!isCenterSensorBlack && (isLeftSensorBlack || isRightSensorBlack)) {
			if (isLeftSensorBlack)
				turnLeft();
			else if (isRightSensorBlack)
				turnRight();
		} else {
			goForward();
		}
	} else {
		if (!isLocChanges) {
			Serial.print(">>>>> Near Station: ");
			Serial.println(sectionChangeCount);
			sectionChangeCount = 0;
			isLocChanges = true;
		}

		if (isCenterSensorBlack && (!isLeftSensorBlack || !isRightSensorBlack)) {
			if (!isLeftSensorBlack)
				turnLeft();
			else if (!isRightSensorBlack)
				turnRight();
		} else if (!isCenterSensorBlack && !isRightSensorBlack) {
			// We prefer to turn right at the station
			turnRight();
			delay(10);
		// } else if (isLeftSensorBlack && isCenterSensorBlack && isRightSensorBlack) {
		// 	stop();
		} else {
			goForward();
		}
	}
#endif

	return ret;
}

void RobotCtrl::nearStation()
{
	int count = 100;
	wrongLocCount = 0;

	stop();

	for (int i = 0; i < count; i++) {
		goForward();

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

	delay(200);

	count = 200;
	for (int i = 0; i < count; i++) {
		turnRight();

		updateSensors();
		if (!isCenterSensorBlack) {
#if DEBUG
			Serial.print("nearStation: end count -> ");
			Serial.println(i);
#endif
			break;
		}
	}

	// XXX:
	delay(50);

	stop();
}

boolean RobotCtrl::isOnNavLine()
{
	static boolean ret = false;

	// Prevent wrong frequent state change

	// sectionChangeCount--;
	// if (sectionChangeCount > 0)
	//	return ret;
	// sectionChangeCount = 500; // XXX:

	sectionChangeCount++;

	int black_count = 0;

	if (isCenterSensorBlack)
		black_count++;
	if (isLeftSensorBlack)
		black_count++;
	if (isRightSensorBlack)
		black_count++;

	ret = black_count < 2 ? true : false;

#if DEBUG
	// Serial.print("isOnNavLine: ");
	// Serial.println(ret);
#endif

	return ret;
}

int RobotCtrl::getLocation()
{
	int blackCount = 0;
	int ret = 0;

	if (isCenterSensorBlack)
		blackCount++;
	if (isLeftSensorBlack)
		blackCount++;
	if (isRightSensorBlack)
		blackCount++;

	switch (blackCount) {
		case 0:
		ret = LOC_WHITE;
		break;
		case 1:
		ret = LOC_ON_NAVLINE;
		break;
		case 2:
		ret = LOC_NEAR_STATION;
		break;
		case 3:
		ret = LOC_BLACK;
		break;
	}

	// Serial.print("getLocation: ");
	// Serial.println(ret);

	return ret;
}

void RobotCtrl::updateSensors()
{
#if DEBUG
	// Serial.println("updateSensors..........");
#endif

	// XXX: Stop()?

	for (int i = 0; i < 2; i++) {
	isCenterSensorBlack  = centerSensor.isBlack();
	isLeftSensorBlack = leftSensor.isBlack();
	isRightSensorBlack = rightSensor.isBlack();
	}
}
