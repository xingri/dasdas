public class WHWorkerFactory {
    public static WHWorker create(String eventInput) {
        if(eventInput == null) {
            System.out.println("invalid Event!!");
        }

        WHEvent whEvt = new WHEvent(eventInput);
        if ( whEvt.isSSSensor() ) {
            return new SSSensorWorker();
        } else if ( whEvt.isSSSwitch() ) {
            return new SSSwitchWorker();
        } else if ( whEvt.isISSensor() ) {
            return new ISSensorWorker();
        } else if ( whEvt.isISSwitch() ) {
            return new ISSwitchWorker();
        } else {
            System.out.println("unknown Event Type");
            return null;
        }
    }
}
