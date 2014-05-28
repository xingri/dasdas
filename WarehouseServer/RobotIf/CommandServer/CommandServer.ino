/*********************************************************************************************
* File: CommandServer
* Project: LG Exec Ed Program
* Copyright: Copyright (c) 2014 Anthony J. Lattanze
* Versions:
*	1.0 May 2014.
*
* Description:
*
* This program runs on an Arduio processor with a WIFI shield. This program will act as a 
* general server that runs on the Arbot. Commands are send via a client Java program 
* (RemoteControl.java). This pair of applications serve to provide remote control of the Arbot
* over WiFi. Note that this application acts as a server, the Java program (RemoteControl.java) 
* is the client. Note that fixed IP addresses are not possible for this lab, so you must plug
* the Arbot into your PC to first get the IP address for the client, then you can connect to 
* bot and control it. General flow, is that the client connects to the server (tbe Arbot), sends
* a single command and then closes. The reason for this is that the Arduino WIFI shield socket
* will timeout unless you are streaming data to it. The supported commands are forward, backward
* jog right/left (slight turns), and stop. Other commands can easily be added to this and 
* client applications can easily be extend.
*
* Compilation and Execution Instructions: Compiled using Arduino IDE 1.0.4
*
* Parameters: None
*
************************************************************************************************/

#include <SPI.h>
#include <WiFi.h>
#include <Servo.h> 
 
#define LTSERVOPIN  5          // Left servo pin
#define RTSERVOPIN  6          // Right servo pin
#define FULLSTOP  90           // PWM value for servo full stop
#define FULLCW  0              // PWM value for servo full stop
#define FULLCCW  180           // PWM value for servo full stop
#define PORTID 501             // IP socket port#

int  LtServoVal;               // Left servo PWM value
int  RtServoVal;               // Right servo PWM value
Servo LtServo;                 // Left servo object
Servo RtServo;                 // Right servo object
char ssid[] = "CMU";           // The network SSID (name) 
int status = WL_IDLE_STATUS;   // The status of the network connections
WiFiServer server(PORTID);     // The WIFI status,.. we are using port 500
char inChar;                   // This is a character sent from the client
char command;                  // This is the actual command character
IPAddress ip;                  // The IP address of the shield
IPAddress subnet;              // The subnet we are connected to
long rssi;                     // The WIFI shield signal strength
byte mac[6];                   // MAC address of the WIFI shield
boolean done;                  // Loop flag
boolean commandRead;           // Loop flag

void setup() 
{
   // Initialize serial port. This is used for debug.
   Serial.begin(9600); 

   // Initialize servos
   LtServo.attach(LTSERVOPIN);
   RtServo.attach(RTSERVOPIN);  

   // Attempt to connect to WIfI network indicated by SSID.
   while ( status != WL_CONNECTED) 
   { 
     Serial.print("Attempting to connect to SSID: ");
     Serial.println(ssid);
     status = WiFi.begin(ssid);
   }  
   
   // Print connection information to the debug terminal
   printConnectionStatus();
   
   // Start the server and print a message to the terminial.
   server.begin();
   Serial.println("The Command Server is started.");
   Serial.println("--------------------------------------\n\n");
   
 } // setup

 void loop() 
 {
    // Listen for a new client.
   WiFiClient client = server.available();
 
   // Wait until we are connected to the client.
   if (client) 
   {
     Serial.print("Client connected..."); 

     // Here is the command parser. Commands are in the format of
     // command character. 
     
     Serial.println( "Waiting for a command..." );
       
     while ( client.available() == 0 ) 
     {
        delay( 500 );
     }     
 
     command = client.read();         // Read a character from the client.  
     Serial.print( "Command:: " );
     Serial.println( command );         
    
     // The command interpreter starts here. Bascally, we act on the single character command.

     switch ( command )
     {
        case 'X': // This is an exit command and disconnects from the client
        case 'x': 
             done= true;
             break;
         
        case 'F': // Drives the bot forward
        case 'f': 
             LtServo.write(FULLCCW);  
             RtServo.write(FULLCW); 
             break;

        case 'B': // Drives the bot backward
        case 'b': 
             LtServo.write(FULLCW);  
             RtServo.write(FULLCCW); 
             break;

        case 'S': // Stops the bot
        case 's': 
             LtServo.write(FULLSTOP);  
             RtServo.write(FULLSTOP); 
             break;
         
        case 'R': // Rolls the bot right then stops after 1/2 second
        case 'r': 
             LtServo.write(FULLCCW);  
             RtServo.write(FULLSTOP);
             delay(500);
             LtServo.write(FULLSTOP);  
             RtServo.write(FULLSTOP);             
             break;

        case 'L': // Rolls the bot left then stops after 1/2 second
        case 'l': 
             LtServo.write(FULLSTOP);  
             RtServo.write(FULLCW);
             delay(500);
             LtServo.write(FULLSTOP);  
             RtServo.write(FULLSTOP);             
             break;
          
        default: // If we don't know what the command is, we just say so and exit.
             Serial.print( "Unrecognized Command:: " );
             Serial.println( command );
    
     } // switch

     // Note that we disconnect here because the Arduino server only stays connected as long
     // as the client streams data. As soon as the client stream stops, the server automatically
     // disconnects from the server... so only one command is sent through at a time

     client.stop();
     Serial.println( "Client Disconnected.\n" );
     Serial.println( "........................." );
     
   } // if client is connected
 
 } // loop
 
/************************************************************************************************
* The following method prints out the connection information
************************************************************************************************/

 void printConnectionStatus() 
 {
     // Print the basic connection and network information: Network, IP, and Subnet mask
     ip = WiFi.localIP();
     Serial.print("Connected to ");
     Serial.print(ssid);
     Serial.print(" IP Address:: ");
     Serial.println(ip);
     subnet = WiFi.subnetMask();
     Serial.print("Netmask: ");
     Serial.println(subnet);
   
     // Print our MAC address.
     WiFi.macAddress(mac);
     Serial.print("WiFi Shield MAC address: ");
     Serial.print(mac[5],HEX);
     Serial.print(":");
     Serial.print(mac[4],HEX);
     Serial.print(":");
     Serial.print(mac[3],HEX);
     Serial.print(":");
     Serial.print(mac[2],HEX);
     Serial.print(":");
     Serial.print(mac[1],HEX);
     Serial.print(":");
     Serial.println(mac[0],HEX);
   
     // Print the wireless signal strength:
     rssi = WiFi.RSSI();
     Serial.print("Signal strength (RSSI): ");
     Serial.print(rssi);
     Serial.println(" dBm");

 } // printConnectionStatus
