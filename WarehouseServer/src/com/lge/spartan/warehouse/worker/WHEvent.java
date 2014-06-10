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
