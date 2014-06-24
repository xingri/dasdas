find . -name '*.class' | xargs rm -rf 
cd com/lge/spartan/warehouse/main; ./run.sh; cd -
java -cp .:../../log4j/log4j-core-2.0-rc1.jar:../../log4j/log4j-api-2.0-rc1.jar:../../DAL/dist/DAL.jar:../../DAL/jdbc/mysql/mysql-connector-java-5.1.30-bin.jar:../order com.lge.spartan.warehouse.main.WarehouseManagementSystemMain
