javac -classpath .:./lib/RXTXcomm.jar SerialInput.java  RobotControl.java StationManager.java HWStationManagerImpl.java
#java -Djava.library.path=./lib -cp .:./lib/RXTXcomm.jar StationManager $1
java -Djava.library.path=./lib -cp .:./lib/RXTXcomm.jar HWStationManagerImpl 128.237.124.48
