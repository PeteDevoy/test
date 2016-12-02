package brickstream.http.model;

import java.net.*;
import java.io.*;

import org.xmlpull.v1.XmlPullParserException;

import java.util.concurrent.*;
import brickstream.metrics.model.*;


/**
 * This class listens on a port (defaults to 2000) and kicks off a 
 * thread when a connection is received to process the request.  
 * Every time a request is received, an instance of HttpRequestHandler
 * is instantiated and runs as a thread.  The server goes back to 
 * listening on new connection requests 
 * 
 */
public class SmartCameraHttpServer extends Thread {

	public int getDataPort() {
		return dataPort;
	}



	public void setDataPort(int dataPort) {
		SmartCameraHttpServer.dataPort = dataPort;
	}



	public String getDirName() {
		return dirName;
	}



	public void setDirName(String dirName) {
		SmartCameraHttpServer.dirName = dirName;
	}


	static int dataPort, timeSyncPort;

	static String dirName = "";

	static void startHttpServer(SmartCameraXMLParser xmlParser) {
		ServerSocket serverSocket = null;
		ConcurrentHashMap devices = new ConcurrentHashMap();

		while (true) {
			try {
				//Create the writer
				SmartCameraRecordWriter writer = new SmartCameraRecordWriter(
						dirName);

				serverSocket = new ServerSocket(dataPort);
				System.out.println("Smart Camera HttpServer running on port "
						+ serverSocket.getLocalPort());

				// server infinite loop
				while (true) {
					Socket socket = serverSocket.accept();

					// Important:  Set the socket timeout to avoid hanging sockets
					socket.setSoTimeout(30000);					
					System.out.println("New connection accepted "
							+ socket.getInetAddress().toString() + ":"
							+ socket.getPort());

					try {
						// Construct handler to process the HTTP request message.
						// We will clone this XML parser to make it thread safe
						// since we could have different types of parsers passed in and 
						// we don't know which xml parser has been created.  The descendants of
						// the SmartCameraXML parser must implement the interface Cloneable
						SmartCameraXMLParser myxmlParser = (SmartCameraXMLParser)xmlParser.clone();												
						HttpRequestHandler handler = new HttpRequestHandler(
								socket, writer, devices, myxmlParser);
						// Create a new thread to process the request.
						Thread thread = new Thread(handler);

						// Start the thread.
						thread.start();
					} catch (Exception e) {
						System.out.println(e);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					// Close the server socket if it has been created 
					if (serverSocket != null)
						serverSocket.close();
					// Wait 10 secs
					Thread.sleep(10000);
				} catch (IOException e) {
					// Just print the error and exit.  There is nothing that
					// can be done
					e.printStackTrace();
				} catch (InterruptedException e) {
					// Just print the error and exit.  There is nothing that
					// can be done
					e.printStackTrace();
				}
			}
		}

	}

	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub

		SmartCameraXMLParser xmlParser;
		try {
			xmlParser = new SmartCameraXMLParser();
			startHttpServer(xmlParser);
		} catch (XmlPullParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		
	}
	
	
	/**
	 * This is the main entry point for the Smart Camera Http server.  It accepts 
	 * 2 parameters as input.  The first one is the port number that the 
	 * SmartCamera Http server listens under.  The other is the output director where the 
	 * where the data and log records are written to (i.e. 2000 D:/Temp
	 * 
	 * @param args
	 * @throws XmlPullParserException
	 */
	public static void main(String args[]) throws XmlPullParserException {

		try {
			dirName = args[1];
			if (dirName == null || dirName.equals(""))
				dirName = "./temp/";

			dataPort = Integer.parseInt(args[0]);

		} catch (Exception e) {
			dataPort = 2000;
			dirName = "./temp/";
		}

		SmartCameraXMLParser xmlParser = new SmartCameraXMLParser();
		startHttpServer(xmlParser);

	}

}
