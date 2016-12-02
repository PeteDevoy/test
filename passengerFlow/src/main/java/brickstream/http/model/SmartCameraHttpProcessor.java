package brickstream.http.model;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import brickstream.exception.SmartCameraNullDataException;

/**
 * This class is used for managing the header of an http request message.
 * It reads an HTTPO message and sends the appropriate positive or negative 
 * acknowledgement   
 */

public class SmartCameraHttpProcessor {

	// Constants
	static private String CONTENT_LENGTH = "CONTENT-LENGTH";

	static private String CONTENT = "Content";

	static public String UTC_TIME = "/UTCTime";

	static public String GET_METHOD_TYPE = "GET";

	static private String CACHE_CONTROL = "Cache-Control";

	private String cAckContent = "<ErrorList><Success>success</Success></ErrorList>";

	private String cNackContent = "<ErrorList><Error>error</Error></ErrorList>";

	// The line seperator used on the system
	//final static String CRLF = System.getProperty("line.separator");
	final static String CRLF = "\r\n";

	private String iType; //GET PUT ...

	private String iTarget; // /files/foo.gif

	private String iVersion; //HTTP/1.1

	// The reader used by the sockets
	private BufferedReader iInput;

	private BufferedWriter iOutput;

	private String iCommand;

	// The read message from the 'Content' section of the HTTP message
	private String iMessage;

	// A linked hash map used to store the Key/Value pairs read from the
	// HTTP Header
	private LinkedHashMap iProps = new LinkedHashMap();

	// The date format and timezone used to generate the timestamp in GMT time
	DateFormat iPMDTF = new SimpleDateFormat("MMddyyyyHHmmss");

	SimpleTimeZone iTZ = new SimpleTimeZone(0, "GMT");

	/**
	 * @return The type of the request (GET,POST,HEAD,etc)
	 */
	public String getType() {
		return iType;
	}

	/**
	 * Sets the type of the request (GET,POST, HEAD, etc) to the specified type
	 */
	public void setType(String type) {
		this.iType = type;
	}

	/**
	 * @return The target of the request (/foo/bar.html)
	 */
	public String getTarget() {
		return iTarget;
	}

	/**
	 * Sets the target of the request (/foo/bar.txt) to the specified type
	 */
	public void setTarget(String target) {
		this.iTarget = target;
	}

	/**
	 * @return The version of the request (HTTP/1.0)
	 */
	public String getVersion() {
		return iVersion;
	}

	/**
	 * Sets the version of the request (HTTP/1.0) to the specified version
	 */
	public void setVersion(String version) {
		this.iVersion = version;
	}

	/**
	 * @param in A buffered reader used to read incoming data
	 * @param out A Buffered Writer used to write data out to the socket
	 */
	public SmartCameraHttpProcessor(BufferedReader in, BufferedWriter out) {
		iInput = in;
		iOutput = out;
		// Set the timezone to UTC
		iPMDTF.setTimeZone(iTZ);
	}

	/**
	 * This method send a TimeSync response back to the remote camera.  The 
	 * format of the timesync message is MMddyyyyHHmmss, where 
	 * 		MM = month (01-12)
	 * 		dd = day (01-31)
	 * 		yyyy = 4 digit year
	 * 		HH = hour in military format (0-23)
	 * 		mm = minutes (00-59)
	 * 		ss = seconds (00-59) 
	 * The timeSync is sent back to the camera in the format of an HTTP reponse to 
	 * a GET request.  A sample response message will look as follows:
	 * 		HTTP/1.1 200 OK
	 *		Content-type: text/html
	 *		Connection: close
	 *		Content-Length: 14
	 *		<CRLF> <-- End of HTTP header
	 *		07172006220705  
	 *
	 *	@throws IOException
	 */
	void sendTimeSyncUTC() throws IOException {
		// Get the date
		Date dt = new Date();
		// Build the HTTP header
		String statusLine = "HTTP/1.1 200 OK" + CRLF;
		String connectionClose = "Connection: close" + CRLF;
		String contentTypeLine = "Content-type: text/html" + CRLF;
		String contentLengthLine = "Content-Length: ";

		// Format the date to the MMddyyyyHHmmss format 
		String timeSync = iPMDTF.format(dt);

		// Write the TimeSync content to the buffer.  Remember there has to
		// be a CRLF after the 'Content-Length' line 
		String timeSyncMsg = statusLine + contentTypeLine + connectionClose
				+ contentLengthLine
				+ (new Integer(timeSync.length())).toString() + CRLF + CRLF
				+ timeSync;

		// Send a blank line to indicate the end of the header lines.
		iOutput.write(timeSyncMsg);

		// Flush the Output
		iOutput.flush();
		System.out.println("Sent a TimeSync Response");

	}

	/**
	 * This method sends an ACK back to the remote camera.  The ACK is 
	 * an HTTP response with an HTTP_OK (200) response code and a 0 byte 
	 * length content.  A sample Ack will look as follows
	 * 
	 * 		HTTP/1.1 200 OK
	 *		Content-type: text/html
	 *		Connection: close
	 *		Content-Length: 0
	 *		<CRLF> <-- End of HTTP header
	 * 
	 * @throws IOException
	 */
	void sendAck() throws IOException {
		//	Build the HTTP header
		String statusLine = "HTTP/1.1 200 OK" + CRLF;
		String contentTypeLine = "Content-type: text/html" + CRLF;
		String connectionClose = "Connection: close" + CRLF;
		String content = cAckContent;
		//		 Extra CRLF is needed to indicate the end of the Http Header
		String contentLengthLine = "Content-Length: "
				+ (new Integer(content.length())).toString() + CRLF + CRLF;

		// Write out the data to the socket
		iOutput.write(statusLine);

		// Send the content type line.
		iOutput.write(contentTypeLine);

		// Send the Connection close.
		iOutput.write(connectionClose);

		// Send the Content-Length with the extra CRLF at the end
		iOutput.write(contentLengthLine);
		// Send a blank line to indicate the end of the header lines.
		iOutput.write(content);
		iOutput.flush();

		System.out.println("Sent An Ack");
	}

	/**
	 * This method sends an NACK back to the remote camera.  The NACK is 
	 * an HTTP response with a response codeOTHER than HTTP_OK (200) and a 0 byte 
	 * length content.  A sample Ack will look as follows
	 * 
	 * 		HTTP/1.1 400 Bad Request
	 *		Content-type: text/html
	 *		Connection: close
	 *		Content-Length: 0
	 *		<CRLF> <-- End of HTTP header
	 * 
	 * @throws IOException
	 */
	void sendNack() throws IOException {

		//	Build the HTTP header
		String statusLine = "HTTP/1.1 400 Bad Request" + CRLF;
		String contentTypeLine = "Content-type: text/html" + CRLF;
		String connectionClose = "Connection: close" + CRLF;
		String content = cNackContent;
		// Extra CRLF is needed to indicate the end of the Http Header
		String contentLengthLine = "Content-Length: "
				+ (new Integer(content.length())).toString() + CRLF + CRLF;

		// Write out the data to the socket
		iOutput.write(statusLine);

		// Send the content type line.
		iOutput.write(contentTypeLine);

		// Send the Connection close.
		iOutput.write(connectionClose);

		// Send the Content-Length with the extra CRLF at the end
		iOutput.write(contentLengthLine);
		// Send a blank line to indicate the end of the header lines.
		iOutput.write(content);

		// Flush the output Stream
		iOutput.flush();
		System.out.println("Sent a Nack");
	}

	/**
	 * Read an httpRequest from an inputstream and parse the attributes, 
	 * type and version
	 */
	public String readRequest() throws IOException,
			SmartCameraNullDataException {

		// Parse the request 
		read();

		// Populate the attributes, type and version
		parseCommand();
		// return the message
		return iMessage;

	}

	private void parseCommand() {
		StringTokenizer st = new StringTokenizer(iCommand);
		iType = st.nextToken();
		iTarget = st.nextToken();
		if (st.hasMoreTokens())
			iVersion = st.nextToken();
		else
			iVersion = "HTTP/1.0";
	}

	/**
	 * Parses an http message from an iInputStream stream.  The first line of iInputStream is
	 * save in the protected <tt>command</tt> variable.  The subsequent lines are 
	 * put into a linked hash as field/value pairs.  Input is parsed until a blank
	 * line is reached, after which any data should appear.  Unfortunately as this
	 * implementation uses a <tt>Reader</tt> to parse full lines, read ahead buffering
	 * will consume some of this iInputStream.  This means that for now this method cannot
	 * be used to read the contents of a <i>POST</i> message. 
	 *
	 * @throws IOException when an Error is encoutered reading the socket 
	 * 		   SmartCameraNullDataException when 0 byte buffer is read
	 */
	private void read() throws IOException, SmartCameraNullDataException {

		// Re-initalize the message
		iMessage = null;

		System.out.println("Entering the Read Method");
		Pattern p = Pattern.compile(":");
		String currLine = iInput.readLine();
		iCommand = currLine;
		int contentLength = 0;
		String headerFieldName;

		currLine = iInput.readLine();

		if (currLine == null)
			throw new SmartCameraNullDataException(
					"Received null data on the iInputStream socket");

		while (!currLine.equals("")) {

			// If we read a null value, then the connection looks like it was disconnected
			if (currLine == null)
				//throw new IOException("Received null data on the iInputStream socket");
				throw new SmartCameraNullDataException(
						"Received null data on the iInputStream socket");

			StringTokenizer st = new StringTokenizer(currLine, ": \t\n\r\f",
					true);
			// Go ahead and parse the message header i.e. Content-Length: 50
			headerFieldName = st.nextToken();
			String next = st.nextToken();

			while (next != null
					&& next.length() == 1
					&& st.hasMoreTokens()
					&& (Character.isWhitespace(next.charAt(0)) || next
							.charAt(0) == ':'))
				next = st.nextToken();

			if (headerFieldName.toUpperCase().equals(CONTENT_LENGTH))
				contentLength = Integer.parseInt(next);
			// Place the field name ina property map for later processing
			iProps.put(headerFieldName, next);
			// read the next line
			currLine = iInput.readLine();

			//System.out.println("Line: '" + currLine + "'");

		}
		// If there was a Content-Length > 0 included in the message
		// let's go ahead and 
		if (contentLength > 0) {
			int n = 0;
			System.out.println("The Content-Length to be read is "
					+ contentLength);
			char[] buffer = new char[contentLength];
			while (n < contentLength)
				n += iInput.read(buffer, n, contentLength - n);

			String next = new String(buffer);

			// The parser will not parse the header value...remove it if present
			// Strip the <?xml version=\1.0"?> record from the message
			if (next.indexOf("<?xml version") > -1) {
				System.out
						.println("Received data contains header entity...Stripping header.");
				int endHeaderPos = next.indexOf(">");
				next = next.substring(endHeaderPos + 1, next.length());
			}

			iMessage = next;
			iProps.put(CONTENT, next);
			// Dump the read message to the screen 
			//System.out.println(this);
		}

		System.out.println("Exiting the Read method");

	}

	public String toString() {
		return iMessage;
	}

}
