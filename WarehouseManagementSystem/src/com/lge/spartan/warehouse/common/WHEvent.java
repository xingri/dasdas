package com.lge.spartan.warehouse.common;

public class WHEvent {

    private String[] inputList;
    private boolean isValid = false;

    private int ssStartIdx = 1;
    private int swStartIdx = 5;
    private int stNum = 4;

    public WHEvent(String eventInput) {
        inputList = eventInput.split(",");
        if(inputList.length == 9) {
            isValid = true;
        }
    }

    public boolean isValid() {
        return isValid;
    }

    public boolean isSwitch() {
        return (isSSSwitch() || isISSwitch());
    }

    public int getSwitchIdx() {
        for(int idx=swStartIdx; idx < swStartIdx+stNum; idx++) 
            if(inputList[idx].equals("1")) return idx-swStartIdx+1 ;

        return -1;
    }

    public int getSensorIdx() {
        for(int idx=ssStartIdx; idx < ssStartIdx+stNum; idx++) 
            if(inputList[idx].equals("1")) return idx-ssStartIdx+1 ;

        return -1;
    }

    public String[] getEventList() {
        String[] currList = inputList;
        return currList;
    }

    public boolean isSameSensor(String[] argList) {

       if(!isValid) return false; 

        for(int idx=ssStartIdx; idx < ssStartIdx+stNum; idx++) {
            //System.out.println("Local value: " + inputList[idx]);
            //System.out.println("Arg value: " + argList[idx]);
            if(argList[idx].equals("1") && inputList[idx].equals( argList[idx] )) {
                //System.out.println("------------true -----------");
                return true;
            }
        }

        //System.out.println("------------false -----------");
        return false;
    }

    public boolean isSSSwitch() {
        if(!isValid) return false;
        if(inputList[swStartIdx+stNum-1].equals("1")) return true;
        return false;
    }

    public boolean isSSSensor() {
        if(!isValid) return false;
        if(inputList[ssStartIdx+stNum-1].equals("1")) return true;
        return false;
    }

    public boolean isISSwitch() {
        if(!isValid) return false;
        for(int idx=swStartIdx; idx < (swStartIdx+stNum-1); idx++) {
            if(inputList[idx].equals("1")) {
                return true;
            }
        }
        return false;
    }

    public boolean isISSensor() {
        if(!isValid) return false;
        for(int idx=ssStartIdx; idx < ssStartIdx+stNum-1; idx++) {
            if(inputList[idx].equals("1")) {
                return true;
            }
        }
        return false;
    }
}
