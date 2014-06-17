class TestClient {
	public static void main(String argv[]) {
//		String robotIP = "128.237.254.190";
		String robotIP = "127.0.0.1";
		int	clientPortNum = 501;				// Port number for server socket

		WHRobotMonitor monitor = new WHRobotMonitor(robotIP, clientPortNum);

		Thread tm = new Thread(monitor);
		tm.start();

		while (true)
			;
	}
}