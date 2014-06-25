find . -name '*.class' | xargs rm -rf 

cd com/lge/spartan/warehouse/common; ./build.sh; cd -
cd com/lge/spartan/warehouse/order; ./build.sh; cd -
cd com/lge/spartan/warehouse/main; ./build.sh; cd -
cd com/lge/spartan/warehouse/worker; ./build.sh; cd -
