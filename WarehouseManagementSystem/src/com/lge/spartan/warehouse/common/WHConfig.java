package com.lge.spartan.warehouse.common;

public class WHConfig {

    // School mode
    /*
    private static String dbIP = "128.237.247.93";
    private static String robotIP = "128.237.243.48";
    private static boolean isEmulator = false;
    private static boolean isInputEmulator = false;
    */

    // School test emul mode
    private static String dbIP = "128.237.247.93";
    private static String robotIP = "128.237.243.48";
    private static boolean isEmulator = true;
    private static boolean isInputEmulator = true;

    /*
    //private static String dbIP = "127.0.0.1";
    //private static String dbIP = "10.254.17.157";
    //private static String robotIP = "10.254.18.19";
    //private static String robotIP = "192.168.43.189";
    private static boolean isEmulator = false;
    private static boolean isInputEmulator = true;
    */

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
