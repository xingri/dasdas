javac -classpath .:./lib/RXTXcomm.jar SerialInput.java  RobotControl.java StationManager.java
java -Djava.library.path=./lib -cp .:./lib/RXTXcomm.jar StationManager $1
