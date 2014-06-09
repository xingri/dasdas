cd worker;./run.sh;cd -
javac -classpath .:./worker:../../../../../../WarehouseHW/StationManager/lib/RXTXcomm.jar:../../../../../../WarehouseHW/StationManager  WarehouseMain.java

#java -Djava.library.path=../../../../../../WarehouseHW/StationManager/lib -cp .:./worker:../../../../../../WarehouseHW/StationManager/lib/RXTXcomm.jar:../../../../../../WarehouseHW/StationManager/ WarehouseMain 128.237.124.48
