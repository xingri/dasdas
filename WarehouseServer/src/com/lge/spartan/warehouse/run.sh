cd worker;./run.sh;cd -
javac -classpath .:./worker:../../../../../../WarehouseHW/StationManager/lib/RXTXcomm.jar:../../../../../../WarehouseHW/StationManager WarehouseMain.java

java -Djava.library.path=../../../../../../WarehouseHW/StationManager/lib -cp .:../../../../../../log4j/log4j-core-2.0-rc1.jar:../../../../../../log4j/log4j-api-2.0-rc1.jar:./worker:../../../../../../DAL/dist/DAL.jar:../../../../../../DAL/jdbc/mysql/mysql-connector-java-5.1.30-bin.jar:../../../../../../WarehouseHW/StationManager/lib/RXTXcomm.jar:../../../../../../WarehouseHW/StationManager/ WarehouseMain 128.237.124.48
