public class WHConfig {

    private static String dbIP = "127.0.0.1";
    private static String robotIP = "127.0.0.1";
    private static boolean isEmulator = true;
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
