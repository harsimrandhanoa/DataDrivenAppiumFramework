package com.appium.ddf.base;

import java.io.IOException;

public class ServerStarter {

	static boolean serverStarted = false;

	public static void startServer() throws InterruptedException {
		System.out.println("Is the server started " + serverStarted);
		if (!serverStarted) {
			System.out.println("Starting server for very first time");
			Runtime runtime = Runtime.getRuntime();
			try {
				runtime.exec(
						"cmd.exe /c start cmd.exe /k \"appium -a 0.0.0.0 -p 4723 --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
				Thread.sleep(10000);
			} catch (IOException e) {
				e.printStackTrace();
			}
			serverStarted = true;
		}
	}

}
