#include <SPI.h>
#include <WiFi.h>
#include <Servo.h> 
 
#define LTSERVOPIN  5          // Left servo pin
#define RTSERVOPIN  6          // Right servo pin
#define FULLSTOP  90           // PWM value for servo full stop
#define FULLCW  0              // PWM value for servo full stop
#define FULLCCW  180           // PWM value for servo full stop
#define NINEDEGR 500

#define CSENPIN  2
#define LSENPIN  9
#define RSENPIN  8

#define PORTID 501             // IP socket port#

Servo lSv; // create servo object to control a servo
Servo rSv; // create another
// a maximum of eight servo objects can be created

int pos = 0; // variable to store the servo position
int tslot = 0;
int stop = 0;

//char ssid[] = "CMU";           // The network SSID (name) 
char ssid[] = "Shadyside Inn";
char pass[] = "hotel5405";
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
  Serial.begin(9600);
  lSv.attach(LTSERVOPIN); // attaches the servo on pin 9 to the servo object
  rSv.attach(RTSERVOPIN); // attaches the servo on pin 9 to the servo object
  
   // Attempt to connect to WIfI network indicated by SSID.
   while ( status != WL_CONNECTED) 
   { 
     Serial.print("Attempting to connect to SSID: ");
     Serial.println(ssid);
     status = WiFi.begin(ssid, pass);
   }  
   
   // Print connection information to the debug terminal
   printConnectionStatus();
   
   // Start the server and print a message to the terminial.
   server.begin();
   Serial.println("The Command Server is started.");
   Serial.println("--------------------------------------\n\n");
}

void loop()
{
  tslot++; 
  int center, left, right; 
  if (tslot == 1000) {
    tslot = 0;
  }
  
  WiFiClient client = server.available();
  if(client) {
    Serial.print("Client connected..."); 
    Serial.println( "Waiting for a command..." );
       
    while ( client.available() == 0 ) 
    {
      delay( 500 );
    }
    
    command = client.read();         // Read a character from the client.  
    Serial.print( "Command:: " );
    Serial.println( command );       
    
    switch ( command )
    {
      case 'X': // This is an exit command and disconnects from the client
      case 'x': 
        done= true;
        break;
        
      case 'S': // Stops the bot
      case 's':
        stop = 1;
        break;
        
      case 'G': // Go Next Station
      case 'g': 
        Right();
        delay(NINEDEGR);
        stop = 0;
        break;
      
      default:
        Serial.print( "Unrecognized Command:: " );
        Serial.println( command );
    }
    
    client.stop();
    Serial.println( "Client Disconnected.\n" );
    Serial.println( "........................." );    
  }
  
  if(stop == 1) {
    Stop();
  } else {
    center = RCTime(CSENPIN);
    left = RCTime(LSENPIN);
    right = RCTime(RSENPIN);
    
    Serial.print("Left: ");
    Serial.print(left);
    
    Serial.print(", Center: ");
    Serial.print(center);
    
    Serial.print(", Rigth: ");
    Serial.print(right);
   
    int w_cnt = 0;
    if (center < 60)
      w_cnt++;
    if (left < 60)
      w_cnt++;
    if (right < 60)
      w_cnt++;
    if (w_cnt >= 2)
      Serial.println(" in Route");
    else
      Serial.println(" in Statin");
   
    if (w_cnt >= 2) {
      if (center < 60 && (left > 60 || right > 60)) { 
        if (left > 60) {
          Left();
        } else if(right > 60) {
          Right();
        }
      } else {
          Forward();
      }
    } else {
       if (center > 60 && (left < 60 || right < 60)) {  
         if(left < 60) {
            Left();
         } else if(right < 60) {
            Right();
         }
      } else {
          Forward();
          delay(100);
          Stop();
      }
    }
  }
  delay(10);
}

void Stop() {
  lSv.write(FULLSTOP);
  rSv.write(FULLSTOP);    
}

void Forward() {
  lSv.write(FULLCCW);
  rSv.write(FULLCW);
}

void Backward() {
  lSv.write(FULLCW);
  rSv.write(FULLCCW);
}


void Right(){
     rSv.write(FULLCCW); // tell servo to move as indicated by  variable 'rspeed'
     lSv.write(FULLCCW); // tell servo to move as indicated by  variable 'lspeed'
     //delay(200);
}

void Left(){
     rSv.write(FULLCW); // tell servo to move as indicated by  variable 'rspeed'
     lSv.write(FULLCW); // tell servo to move as indicated by  variable 'lspeed'
     //delay(200);
}

long RCTime(int sensorIn)
{
    long duration = 0;
    pinMode(sensorIn, OUTPUT);         // Sets pin as OUTPUT
    digitalWrite(sensorIn, HIGH);    // Pin HIGH
    delay(1);
    pinMode(sensorIn, INPUT);          // Sets pin as INPUT
    digitalWrite(sensorIn, LOW);       // Pin LOW
    //Serial.println(digitalRead(sensorIn));
    //delay(1000);
    //Serial.println("in the RCTime loop");
    
    while(digitalRead(sensorIn) != 0) {  // Count until the pin goes
       //Serial.print(digitalRead(sensorIn));
       //Serial.print(" : ");
       //Serial.println(analogRead(sensorIn));
       //delay(1000);
       duration++;                     // LOW (cap discharges)
    }   
    return duration;                   // Returns the duration of the pulse
}

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
