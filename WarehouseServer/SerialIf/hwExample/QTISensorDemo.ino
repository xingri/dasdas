/****************************************************************
* File: QTISensorDemo
* Project: LG Exec Ed Program
* Copyright: Copyright (c) 2014 Anthony J. Lattanze
* Versions:
* 1.0 April 2014 - Initial version
*
* Description:
*
* This program runs on an Arduino processor with a QTI sensor 
* connected on pin 14. The values read by the QTI sensor are 
* written to the console. This program can be used to calibrate 
* the values read by the QTIs to determine white and black values 
* under the sensor.
*
* Compilation and Execution Instructions: Only Compile using 
* Arduino IDE VERSION 1.0.4
*
* Parameters: None
*
* Internal Methods: void RCTime(int sensorIn) - returns the time 
* it takes for a capacitor to discharge (RC time constant).
*
*****************************************************************/

#define QTIPin 2    // The Arduino pin with QTI sensor

void setup()
{
    // Debug terminal
    Serial.begin(9600);
}

void loop()
{
    // This is it... all the magic happens in the RCTime method
    Serial.println(RCTime(QTIPin)); // Connects to specified pin, displays results
}



/****************************************************************
* RCTime (int pin) - Determines how long it takes the capactior 
* to charge.
* Parameters:
*              int pin - the pin on the Arduino where the QTI 
*                        sensor is connected.
* Description:
* Check out the QTI schematics and specifications at: 
* http://www.parallax.com/product/555-27401
* This method initalizes the sensor pin as output and charges the
* capacitor on the QTI. The QTI emits IR light which is reflected
* off of any surface in front of the sensor. The amount of IR 
* light reflected back is detected by the IR resistor on the QTI.
* This is the resistor that the capacitor discharges through. The
* amount of time it takes to discharge determines how much light, 
* and therefore the lightness or darkness of the material in
* front of the QTI sensor.
****************************************************************/

long RCTime(int sensorIn)
{
    long duration = 0;
    pinMode(sensorIn, OUTPUT);         // Sets pin as OUTPUT
    digitalWrite(sensorIn, HIGH);      // Pin HIGH
    pinMode(sensorIn, INPUT);          // Sets pin as INPUT
    digitalWrite(sensorIn, LOW);       // Pin LOW
    while(digitalRead(sensorIn) != 0)  // Count until the pin goes
       duration++;                     // LOW (cap discharges)
       
    return duration;                   // Returns the duration of the pulse
}
