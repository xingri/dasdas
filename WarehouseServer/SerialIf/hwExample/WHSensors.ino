
/*********************************************************************************************
* File: CommandServer
* Project: LG Exec Ed Program
* Copyright: Copyright (c) 2014 Anthony J. Lattanze
* Versions:
*	1.0 May 2014.
*
* Description:
*
* This program runs on an Arduio processor and demonstates how to read the photocell (PC) sensors 
* and the switches (SW) located at each of the corners of the gameboard. Currently it will sample
* all of the photosensors and switches every second and print the status to the debug terminal
*
* Compilation and Execution Instructions: Compiled using Arduino IDE 1.0.4
*
* Parameters: None
*
************************************************************************************************/
// Note that these pins are the analog pins A0-A3, not the digital pins. 

int Corner1PCPin = 0;   // Pin A0 photocell for corner 1
int Corner1PCVal;       // Value of photocell for corner 1
int Corner2PCPin = 1;   // Pin A1 photocell for corner 2
int Corner2PCVal;       // Value of photocell for corner 2
int Corner3PCPin = 2;   // Pin A2 photocell for corner 3
int Corner3PCVal;       // Value of photocell for corner 3
int Corner4PCPin = 3;   // Pin A3, photocell for corner 4 
int Corner4PCVal;       // Value of photocell for corner 4

// Note that these pins are the standard digital pins

int Corner1SWPin = 3;   // Switch for corner 1
int Corner1SWVal;       // State of switch for corner 1
int Corner2SWPin = 5;   // Switch for corner 2
int Corner2SWVal;       // State of switch for corner 2
int Corner3SWPin = 6;   // Switch for corner 3
int Corner3SWVal;       // State of switch for corner 3
int Corner4SWPin = 9;   // Switch for corner 4
int Corner4SWVal;       // State of switch for corner 4

// Setup. We set all the switch pins to inputs.

void setup(void) 
{
   Serial.begin(9600);
   pinMode(Corner1SWPin, INPUT);
   pinMode(Corner2SWPin, INPUT);
   pinMode(Corner3SWPin, INPUT);
   pinMode(Corner4SWPin, INPUT);
}
 
void loop(void) 
{
  // Read the values of the photo sensors
  Corner1PCVal = analogRead(Corner1PCPin);  
  Corner2PCVal = analogRead(Corner2PCPin);  
  Corner3PCVal = analogRead(Corner3PCPin);  
  Corner4PCVal = analogRead(Corner4PCPin);    

  // Read the state of the switches
  Corner1SWVal = digitalRead(Corner1SWPin);
  Corner2SWVal = digitalRead(Corner2SWPin);
  Corner3SWVal = digitalRead(Corner3SWPin);
  Corner4SWVal = digitalRead(Corner4SWPin);
  
  // Print the photo sensor results  
  Serial.print("Corner PC 1: ");
  Serial.print(Corner1PCVal); 
  Serial.print(" Corner PC 2: ");
  Serial.print(Corner2PCVal); 
  Serial.print(" Corner PC 3: ");
  Serial.print(Corner3PCVal); 
  Serial.print(" Corner PC 4: ");
  Serial.println(Corner4PCVal); 
  
  // Print the switch sensor results
  
  Serial.print("Corner SW 1: ");
  Serial.print(Corner1SWVal); 
  Serial.print(" Corner SW 2: ");
  Serial.print(Corner2SWVal); 
  Serial.print(" Corner SW 3: ");
  Serial.print(Corner3SWVal); 
  Serial.print(" Corner SW 4: ");
  Serial.println(Corner4SWVal); 
 
  // Sample rate 1 second 
  delay(1000);
}
