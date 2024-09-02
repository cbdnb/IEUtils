package de.dnb.ie.einzelablage;

import de.dnb.basics.applicationComponents.MyFileUtils;
import de.dnb.basics.clientServer.Client;

public class EinzelClient extends Client {

	public EinzelClient(int port, String classpath, String server) {
		super(port, classpath, server);
		showOutput = false;
	}

	public static void main(String[] args) {
		
		EinzelClient client;
//		 client = new EinzelClient(
//		 4711,
//		 FileUtils.getExecutionDirectory(EinzelClient.class)
//		 + ";D:\\eclipse-java-kepler-R-win32\\workspace\\zmarc\\bin",
//		 "de.dnb.ie.einzelablage.EinzelServer");

		String exDir = MyFileUtils.getExecutionDirectory();
		System.err.println(exDir);
		client = new EinzelClient(4711, null, exDir + "\\" + "EinzelServer.jar");
		
		client.work();
	}
}
