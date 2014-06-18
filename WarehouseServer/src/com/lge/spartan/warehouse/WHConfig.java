public class WHConfig {

    //private static String dbIP = "128.237.247.93";
    private static String dbIP = "127.0.0.1";
    private static String robotIP = "128.237.121.188";

    private static boolean isEmulator = false;
    private static boolean isInputEmulator = false;

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
