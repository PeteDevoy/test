package brickstream.http.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ConcurrentMap;

import redis.clients.jedis.Jedis;




import com.cn.pflow.util.JedisUtil;

import brickstream.exception.*;
import brickstream.metrics.model.RecordSummary;
import brickstream.metrics.model.SmartCameraCountRecord;
import brickstream.metrics.model.SmartCameraDetectionRecord;
import brickstream.metrics.model.SmartCameraQueueRecord;
import brickstream.metrics.model.SmartCameraServiceRecord;
import brickstream.metrics.model.SmartCameraWaitTimeRecord;
import brickstream.metrics.model.SmartCameraXMLParser;

/**
 * Class used to process the socket requests. This class implements runnable to
 * allow it to run as a thread .
 */
class HttpRequestHandler implements Runnable {

	Socket socket;

	// Socket input and Output Stream
	InputStream iInputStream;

	OutputStream iOutputStream;

	// Socket's Buffered reader and writer
	BufferedReader iReader;
	BufferedWriter iWriter;

	// Directory name where the file is created
	SmartCameraRecordWriter iRecordWriter;

	// Map containing all known devices
	ConcurrentMap iDevices = null;

	SmartCameraXMLParser iXmlParser;

	RecordSummary iLogSummary = null;
	RecordSummary iQueueSummary = null;
	RecordSummary iServiceSummary = null;
	RecordSummary iDetectionSummary = null;
	
	// Redis
	Jedis _jedisclient = null;
	private final String JEDIS_CHNL_REALTIMEDATA = "jedisRealtimeData";

	// Constructor
	public HttpRequestHandler(Socket socket,
			SmartCameraRecordWriter recordWriter, ConcurrentMap devices,
			SmartCameraXMLParser xmlParser) throws Exception {
		this.socket = socket;
		this.iInputStream = socket.getInputStream();
		this.iOutputStream = socket.getOutputStream();
		iReader = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
		iWriter = new BufferedWriter(new OutputStreamWriter(socket
				.getOutputStream()));

		iRecordWriter = recordWriter;
		// A list of cameras that we keep around for historical purposes, such
		// as
		// total number of records received, etc...
		iDevices = devices;
		// Accept an XML parser. We will clone thsi XML parser to make it thread
		// safe
		// since we could have different types of parsers passed in
		iXmlParser = xmlParser;

	}

	// Implement the run() method of the Runnable interface.
	public void run() {
		try {
			// Invoke the processRequest to receive and send data
			processRequest();
			iXmlParser = null;
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * returns true if the device is already in the map or no if it is not
	 * Updates the last record received in the map
	 */
	private void addDevice(SmartCameraXMLParser xmlParser)
			throws ParseException, SmartCameraInternalException {
		SmartCameraRecordSummary summary = getDevice(xmlParser);
		boolean dtsEnabled;

		if (summary == null) {
			// Right now, let's just grab the first element since all ttimestamps are the same per XML packet
			// one
			// report object per camera
			if (xmlParser.getCountRecords().size() > 0) {
				SmartCameraCountRecord record = (SmartCameraCountRecord) xmlParser.getCountRecords().get(0);
				if (record != null) {					
					summary = new SmartCameraRecordSummary(xmlParser
							.getHeaderRecord().getDeviceId(), record.getEndTimeStamp(), xmlParser.getCountRecords(), xmlParser
							.getHeaderRecord().getDST(), xmlParser
							.getHeaderRecord().getTimeZoneOffset());

					iDevices.put(xmlParser.getHeaderRecord().getDeviceId(),
							summary);
				}
			}

		} else
			throw new SmartCameraInternalException(
					"Record already exists for device id "
							+ summary.getDeviceId());

	}

	/**
	 * Update the summary object with the latest enter and exits counts
	 * 
	 * @throws SmartCameraInternalException
	 */
	private void updateDevice(SmartCameraRecordSummary summary,
			SmartCameraXMLParser xmlParser,
			SmartCameraCountRecord countRecord)
			throws ParseException, SmartCameraInternalException {

		//Date dt = countRecord.getTimeStamp();
		summary.setEntersAndExits(countRecord);
		summary.setDSTEnabled(xmlParser.getHeaderRecord().getDST());

		summary.setDestTimeZoneOffset(xmlParser.getHeaderRecord()
				.getTimeZoneOffset());
	}

	/**
	 * 
	 * @param xmlParser
	 * @return Returns the record summary
	 */
	private SmartCameraRecordSummary getDevice(SmartCameraXMLParser xmlParser) {
		return (SmartCameraRecordSummary) (iDevices.get(xmlParser
				.getHeaderRecord().getDeviceId()));
	}

	/**
	 * This method processes the Count records that were just parsed by the XML
	 * parser If this is the first record to be received from a remote system,
	 * it creates a summary record for that report object, otherwise it updates
	 * the record with the latest enters and exits counts. It then writes the
	 * record to the file
	 * 
	 * @throws ParseException
	 * @throws SmartCameraInternalException
	 */
	private void processCounts() throws ParseException,
			SmartCameraInternalException {
		boolean firstRecord = false;
		SmartCameraRecordSummary recordSummary = null;
		int recordStart = 0;
		if ((recordSummary = getDevice(iXmlParser)) == null) {
			// We just want to add the device and return
			addDevice(iXmlParser);
			// Skip the first record and evaluate the rest. This is to handle
			// multiple counts within a single
			// XML packet
			recordStart = 0;
			recordSummary = getDevice(iXmlParser);

			// Set the first record as true
			firstRecord = true;
		}

		if (recordSummary != null) {
			ArrayList countRecords = iXmlParser.getCountRecords();
			for (int i = recordStart; i < countRecords.size(); i++) {
				SmartCameraCountRecord countRecord = (SmartCameraCountRecord) countRecords
						.get(i);
				// CHANGETS
				Date dt = countRecord.getStartTimeStamp();
				// Ignore the check the first time
				if (!firstRecord)
					try {
						// If this is a duplicate
						if (dt.before(recordSummary
								.getCountRecordReceived(countRecord
										.getReportObjectId()))
								|| dt.equals(recordSummary
										.getCountRecordReceived(countRecord
												.getReportObjectId()))) {
				
							System.err.println("Received a duplicate record from "
									+ recordSummary.getDeviceId()
									+ " at "
									+ recordSummary.getDateBasedOnLocalTime(dt)
									+ " Previous Record ts "
									+ recordSummary.getDateBasedOnLocalTime(recordSummary
											.getCountRecordReceived(countRecord
													.getReportObjectId())) );
							// Sync up and continue
							updateDevice(recordSummary, iXmlParser, countRecord);
							continue;
				
						}
					} catch (SmartCameraInternalException e) {
						// Sync up and continue
						updateDevice(recordSummary, iXmlParser, countRecord);
						continue;
	
					}

				// If we had no errors, first try and find if this camera
				// in our list. If not, add it.
				iRecordWriter.write(recordSummary,
						iXmlParser.getHeaderRecord(), countRecord);
				// Update the record info
				updateDevice(recordSummary, iXmlParser, countRecord);

			}
		}

	}

	/**
	 * This method processes the Diag record that was just parsed by the XML
	 * parser If this is the first record to be received from a remote system,
	 * it creates a summary record for it, otherwise it updates the record with
	 * the latest date timestamp. It then writes the record to the file
	 * 
	 * @throws ParseException
	 * @throws SmartCameraInternalException
	 */
	private void processDiagnostics() throws ParseException,
			SmartCameraInternalException {
		ArrayList diagRecords = iXmlParser.getDiagsRecords();

		Date currentTS = new Date();

		// If the log summary has not been created, create it and
		// use the local PC date as the last record received
		if (iLogSummary == null)
			iLogSummary = new LogRecordSummary(currentTS, iXmlParser
					.getHeaderRecord().getDST(), iXmlParser.getHeaderRecord()
					.getTimeZoneOffset());

		for (int i = 0; i < diagRecords.size(); i++) {

			SmartCameraXMLParser.EventRecord diagRecord = (SmartCameraXMLParser.EventRecord) diagRecords
					.get(i);
			if (diagRecord instanceof SmartCameraXMLParser.EventRecord) {
				Date dt = diagRecord.getTimeStamp();
				iRecordWriter.write(iLogSummary, currentTS, iXmlParser
						.getHeaderRecord(), diagRecord);

			}
		}

		// Update the last record received to be used for file rolling
		iLogSummary.setLastRecordReceived(currentTS);

	}

	/**
	 * This method processes the Queue records that were just parsed by the XML
	 * parser If this is the first record to be received from a remote system,
	 * it creates a summary record for that report object and queue, otherwise
	 * it updates the record with the latest record received. It then writes the
	 * record to the file
	 * 
	 * @throws SmartCameraInternalException
	 * 
	 * @throws ParseException
	 * @throws SmartCameraInternalException
	 * @throws IOException
	 */
	private void processQueues() throws SmartCameraInternalException {
		ArrayList queueRecords = iXmlParser.getQueueRecords();

		// If the queue summary has not been created, create it and
		// use the local PC date as the last record received
		if (iQueueSummary == null && queueRecords.size() > 0) {
			SmartCameraQueueRecord queueRecord = (SmartCameraQueueRecord) queueRecords
					.get(0);
			iQueueSummary = new QueueRecordSummary(queueRecord
					.getEndTimeStamp(), iXmlParser.getHeaderRecord().getDST(),
					iXmlParser.getHeaderRecord().getTimeZoneOffset());
		}

		for (int i = 0; i < queueRecords.size(); i++) {
			SmartCameraQueueRecord queueRecord = (SmartCameraQueueRecord) queueRecords
					.get(i);
			if (queueRecord instanceof SmartCameraQueueRecord) {
				iRecordWriter.write(iQueueSummary,
						iXmlParser.getHeaderRecord(), queueRecord);
				// Update the last record received to be used for file rolling
				iQueueSummary.setLastRecordReceived(queueRecord
						.getEndTimeStamp());
			}
		}


	}

	/**
	 * This method processes the Service records that were just parsed by the XML
	 * parser If this is the first record to be received from a remote system,
	 * it creates a summary record for that report object and service, otherwise
	 * it updates the record with the latest record received. It then writes the
	 * record to the file
	 * 
	 * @throws SmartCameraInternalException
	 * 
	 * @throws ParseException
	 * @throws SmartCameraInternalException
	 * @throws IOException
	 */
	private void processService() throws SmartCameraInternalException {
		ArrayList serviceRecords = iXmlParser.getServiceRecords();

		// If the queue summary has not been created, create it and
		// use the local PC date as the last record received
		if (iServiceSummary == null && serviceRecords.size() > 0) {
			SmartCameraServiceRecord queueRecord = (SmartCameraServiceRecord) serviceRecords
					.get(0);
			iServiceSummary = new QueueRecordSummary(queueRecord
					.getEndTimeStamp(), iXmlParser.getHeaderRecord().getDST(),
					iXmlParser.getHeaderRecord().getTimeZoneOffset());
		}

		for (int i = 0; i < serviceRecords.size(); i++) {
			SmartCameraServiceRecord serviceRecord = (SmartCameraServiceRecord) serviceRecords
					.get(i);
			if (serviceRecord instanceof SmartCameraServiceRecord) {
				iRecordWriter.write(iServiceSummary,
						iXmlParser.getHeaderRecord(), serviceRecord);
				// Update the last record received to be used for file rolling
				iServiceSummary.setLastRecordReceived(serviceRecord
						.getEndTimeStamp());
			}
		}

	}


	/**
	 * This method processes the Detection records that were just parsed by the XML
	 * parser If this is the first record to be received from a remote system,
	 * it creates a summary record for that report object and detection, otherwise
	 * it updates the record with the latest record received. It then writes the
	 * record to the file
	 * 
	 * @throws SmartCameraInternalException
	 * 
	 * @throws ParseException
	 * @throws SmartCameraInternalException
	 * @throws IOException
	 */
	private void processDetection() throws SmartCameraInternalException {
		ArrayList detectionRecords = iXmlParser.getDetectionRecords();

		// If the queue summary has not been created, create it and
		// use the local PC date as the last record received
		if (iDetectionSummary == null && detectionRecords.size() > 0) {
			SmartCameraDetectionRecord detectionRecord = (SmartCameraDetectionRecord) detectionRecords
					.get(0);
			iDetectionSummary = new RecordSummary(detectionRecord
					.getEndTimeStamp(), iXmlParser.getHeaderRecord().getDST(),
					iXmlParser.getHeaderRecord().getTimeZoneOffset());
		}

		for (int i = 0; i < detectionRecords.size(); i++) {
			SmartCameraDetectionRecord detectionRecord = (SmartCameraDetectionRecord) detectionRecords
					.get(i);
			if (detectionRecord instanceof SmartCameraDetectionRecord) {
				iRecordWriter.write(iDetectionSummary,
						iXmlParser.getHeaderRecord(), detectionRecord);
				// Update the last record received to be used for file rolling
				iDetectionSummary.setLastRecordReceived(detectionRecord
						.getEndTimeStamp());
			}
		}

	}
	
	private void processWaitTimes() throws SmartCameraInternalException
	{
		ArrayList waitTimeRecords = iXmlParser.getWaitTimeRecords();
		for (int i = 0; i < waitTimeRecords.size(); i++) {
			SmartCameraWaitTimeRecord  waitTimeRecord =  (SmartCameraWaitTimeRecord) waitTimeRecords.get(i);
			if (waitTimeRecord.getRecordType().equals(SmartCameraXMLParser.WAIT_TIME_RECORD_TYPE))
				iRecordWriter.write(iQueueSummary,
						iXmlParser.getHeaderRecord(), waitTimeRecord);
			else
				if (waitTimeRecord.getRecordType().equals(SmartCameraXMLParser.SERVICE_TIME_RECORD_TYPE))
					iRecordWriter.write(iServiceSummary,
							iXmlParser.getHeaderRecord(), waitTimeRecord);
			
		}
	}
	
	/**
	 * This method reads an XML record from the socket and invokes the XML
	 * parser to process it. If this is a timesyncs request from the camera, it
	 * formats a timesync response and sends it back. Otherwise, it treats the
	 * received XML record as data and based on the type, processes it as a
	 * Metric or a diagnostic message
	 * 
	 * @throws Exception
	 */
	private void processRequest() throws Exception {
		SmartCameraHttpProcessor processor = null;
		try {
			// Declare the Http processor that would read the request
			processor = new SmartCameraHttpProcessor(iReader, iWriter);
			int i = 0;
			SmartCameraRecordSummary summary = null;
			
//			BufferedReader r =new BufferedReader(new FileReader(new File("c:/temp/s.xml")));
//			String line = null;
//			String s = "";
//	        while ((line=r.readLine()) != null) {
//	            s += line;
//	            s+= "\r\n";   // Write system dependent end of line.
//	        }
//	        r.close();
//	        System.out.println(s);
			// Do forever until the connection is closed which will happen after
			// every request
			// unless there are some QUEUED up messages. In that case, the
			// messages will be
			// sent back to back, but waiting for an ack or nack in between
			// Keep reading requests until the socket is closed by the client
			while (true) {
				// Read the request
				String xmlRecord = processor.readRequest();
				if (xmlRecord != null)
					System.out.println(xmlRecord);
				//xmlRecord =s; 
				// If the method type is a GET and the target specified in the
				// header
				// is /UTCTime, then treat this as a TimeSync request
				if ((processor.getType()
						.equals(SmartCameraHttpProcessor.GET_METHOD_TYPE))
						&& (processor.getTarget()
								.equals(SmartCameraHttpProcessor.UTC_TIME))) {
					// Send the timesync response
					processor.sendTimeSyncUTC();
				} else {
					// This is a POST. Treat it as data. Parse the data
					// and send the ack if no exception is raised. A developer
					// may
					// want to check for other conditions such as duplicate data
					// etc...
					int messageType = iXmlParser.parseXML(xmlRecord);
					String zRealtimeData = "";
					
					if (messageType == SmartCameraXMLParser.COUNTS) {
						processMetrics();

					} else if (messageType == SmartCameraXMLParser.DIAGNOSTICS) {
						processDiagnostics();
						
					} else if(null != (zRealtimeData = PflowHandler.parseXml2(xmlRecord))) {
						PflowHandler.parseXml2Alarm(zRealtimeData);
						
						{
							if(null == _jedisclient)
								_jedisclient = JedisUtil.getJedisEx();
							if(null != _jedisclient)
								_jedisclient.publish(JEDIS_CHNL_REALTIMEDATA, zRealtimeData);
						}
					}

					// Send an ACK
					processor.sendAck();
				}
			}

		} catch (SmartCameraNullDataException e) {
			// Received a null on the socket. Socket was closed
			System.out.println("Socket closed");
		} catch (Exception e) {
			System.out.println("Caught an exception: " + e.getMessage());
			e.printStackTrace();

			// Send a NACK. A developer may want to be more specific
			// about individual exceptions and ack/nack accordingly.
			if (processor != null)
				processor.sendNack();
		}

		finally {
			// Close the connection and exit. Don't worry about any exceptions
			try {
				iOutputStream.close();
				iInputStream.close();
				iReader.close();
				iWriter.close();
				socket.close();
			}

			catch (Exception ex) {
				// Print out the Message to the screen
				System.out
						.println("Received an Exception while closing socket.  Exception - "
								+ ex);
			}
		}
	}

	private void processMetrics() throws ParseException,
			SmartCameraInternalException {
		processCounts();
		processQueues();
		processService();
		processDetection();
		processWaitTimes();
	}

}
