public class WHConfig {

    //private static String dbIP = "128.237.247.93";
    private static String dbIP = "127.0.0.1";
    //private static String dbIP = "10.254.17.157";
    //private static String robotIP = "128.237.121.188";
    //private static String robotIP = "10.254.18.19";
    private static String robotIP = "192.168.43.121";

    private static boolean isEmulator = true;
    //private static boolean isEmulator = false;
    //private static boolean isInputEmulator = false;
    private static boolean isInputEmulator = true;

    public static String GetDBIP() {
        return dbIP;
    }

    public static String GetRobotIP() {
        return robotIP;
    }

    public static boolean IsEmulator() {
        return isEmulator;
    }

    public static boolean IsInputEmulator() {
        return isInputEmulator;
    }
}
