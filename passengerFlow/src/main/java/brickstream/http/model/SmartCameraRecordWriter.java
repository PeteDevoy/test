/*
 * Created on Dec 20, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package brickstream.http.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.cn.pflow.domain.Pflowcount;
import com.cn.pflow.listener.SpringContextListener;
import com.cn.pflow.service.IEquipmentService;
import com.cn.pflow.service.IPfcountService;


import brickstream.exception.SmartCameraInternalException;
import brickstream.metrics.model.RecordSummary;
import brickstream.metrics.model.SmartCameraCountRecord;
import brickstream.metrics.model.SmartCameraDetectionRecord;
import brickstream.metrics.model.SmartCameraHeaderRecord;
import brickstream.metrics.model.SmartCameraQueueRecord;
import brickstream.metrics.model.SmartCameraServiceRecord;
import brickstream.metrics.model.SmartCameraWaitTimeRecord;
import brickstream.metrics.model.SmartCameraXMLParser;
import brickstream.metrics.model.SmartCameraXMLParser.EventRecord;
import brickstream.utilities.FileHelper;

/**
 * This class is reponsible for writing the data records and log files to 
 * the output directory.  It toggles files based on end of day.
 * 
 */
@ContextConfiguration(locations = {"classpath:spring-mybatis.xml"})
public class SmartCameraRecordWriter {

	@Resource
	private IPfcountService pfcountService = null;
	
	IPfcountService ipfcountservice = (IPfcountService) SpringContextListener.getApplicationContext().getBean(IPfcountService.class);
	IEquipmentService iequipmentservice = (IEquipmentService) SpringContextListener.getApplicationContext().getBean(IEquipmentService.class);
	
	// Format used to ouput timestamp in log and data files
	static SimpleDateFormat cISODateTimeFileDTFmt = new SimpleDateFormat(
			SmartCameraXMLParser.ISO_DATETIME_FORMAT);
	
	// Format used to generate date file name
	static SimpleDateFormat cISODateFileDTFmt = new SimpleDateFormat(
			SmartCameraXMLParser.ISO_DATE_FORMAT);

	// Directory where the file is written to
	String iDirName;

	// Current File count data being written to
	FileWriter iCurrentCountDataFile = null;
	
	// Current File queue data being written to
	FileWriter iCurrentQueueDataFile = null;
	
	// Current File wait time data being written to
	FileWriter iCurrentWaitTimeDataFile = null;
	
	// Current File service data being written to
	FileWriter iCurrentServiceDataFile = null;
	
	// Current File detection data being written to
	FileWriter iCurrentDetectionDataFile = null;

	//	 Current File being written to
	FileWriter iCurrentLogFile = null;

	// The name suffix used to generate the data filename.  Currently the file name 
	// is "CountDataRecords_"
	private String iCurrentCountDataSuffix = "";
	
	// The name suffix used to generate the data filename.  Currently the file name 
	// is "QueueDataRecords_"
	private String iCurrentQueueDataSuffix = "";
	
	// The name suffix used to generate the data filename.  Currently the file name 
	// is "ServiceDataRecords_"
	private String iCurrentServiceDataSuffix = "";
	
	// The name suffix used to generate the data filename.  Currently the file name 
	// is "DetectionDataRecords_"
	private String iCurrentDetectionDataSuffix = "";

	// The name suffix used to generate the log filename.  Currently the file name 
	// is "Log_"
	private String iCurrentLogSuffix = "";
	

	/**
	 * Public constructor: This contructor creates the directory where the
	 * output will be stored. i.e. D:/Temp
	 */
	public SmartCameraRecordWriter(String dirName)
			throws SmartCameraInternalException {
		super();

		// Strip any forward or backslashed from the directory name
		iDirName = FileHelper.trim(dirName, "/\\");

		File theFQDirName = FileHelper.createDir(iDirName, false);

		if (theFQDirName == null)
			throw new SmartCameraInternalException("Unable to create diretory "
					+ iDirName);
	}

	/**
	 * This method creates the output file in the output directory if one does
	 * not exist. The file format is: DataRecords_YYYY-MM-DD
	 * (CountDataRecords_2007-04-06.txt). If a current file is open , it will close
	 * it
	 */
	void createCountRecordFile(String suffix) throws SmartCameraInternalException {
		// Create the count file
		String theFQFileName = iDirName + '/' + "CountDataRecords" + '_' + suffix
				+ ".txt";

		try {
			// If there is a file open close it first
			if (iCurrentCountDataFile != null)
				iCurrentCountDataFile.close();
			iCurrentCountDataFile = new FileWriter(theFQFileName, true);
			iCurrentCountDataSuffix = suffix;

		} catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}
	
	/**
	 * This method creates the output file in the output directory if one does
	 * not exist. The file format is: DataRecords_YYYY-MM-DD
	 * (CountDataRecords_2007-04-06.txt). If a current file is open , it will close
	 * it
	 */
	void createDetectionRecordFile(String suffix) throws SmartCameraInternalException {
		// Create the count file
		String theFQFileName = iDirName + '/' + "DetectionDataRecords" + '_' + suffix
				+ ".txt";

		try {
			// If there is a file open close it first
			if (iCurrentDetectionDataFile != null)
				iCurrentDetectionDataFile.close();
			iCurrentDetectionDataFile = new FileWriter(theFQFileName, true);
			iCurrentDetectionDataSuffix = suffix;

		} catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}
	
	/**
	 * This method creates the output file in the output directory if one does
	 * not exist. The file format is: DataRecords_YYYY-MM-DD
	 * (QueueDataRecords_2007-04-06.txt). If a current file is open , it will close
	 * it
	 */
	void createQueueRecordFile(String suffix) throws SmartCameraInternalException {
		// Create the Queue
		String theQueueFQFileName = iDirName + '/' + "QueueDataRecords" + '_' + suffix
				+ ".txt";
		// Create the Wait time
		String theWaitTimeFQFileName = iDirName + '/' + "WaitTimeDataRecords" + '_' + suffix
				+ ".txt";

		try {
			// If there is a file open close it first
			if (iCurrentQueueDataFile != null)
				iCurrentQueueDataFile.close();

			if (iCurrentWaitTimeDataFile != null)				
				iCurrentWaitTimeDataFile.close();

			iCurrentQueueDataFile = new FileWriter(theQueueFQFileName, true);
			iCurrentWaitTimeDataFile = new FileWriter(theWaitTimeFQFileName, true);
			iCurrentQueueDataSuffix = suffix;

		} catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}
	
	/**
	 * This method creates the output file in the output directory if one does
	 * not exist. The file format is: DataRecords_YYYY-MM-DD
	 * (ServiceDataRecords_2007-04-06.txt). If a current file is open , it will close
	 * it
	 */
	void createServiceRecordFile(String suffix) throws SmartCameraInternalException {
		// Create the Queue
		String theServiceFQFileName = iDirName + '/' + "ServiceDataRecords" + '_' + suffix
				+ ".txt";
		// Create the Wait time
		String theWaitTimeFQFileName = iDirName + '/' + "WaitTimeDataRecords" + '_' + suffix
				+ ".txt";
		try {
			// If there is a file open close it first
			if (iCurrentServiceDataFile != null)
				iCurrentServiceDataFile.close();

			if (iCurrentWaitTimeDataFile != null)				
				iCurrentWaitTimeDataFile.close();

			iCurrentServiceDataFile = new FileWriter(theServiceFQFileName, true);
			iCurrentWaitTimeDataFile = new FileWriter(theWaitTimeFQFileName, true);
			iCurrentServiceDataSuffix = suffix;

		} catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}

	/**
	 * This method creates the log file in the output directory if one does not
	 * exist. The file format is: Log_YYYY-MM-DD (Log_2007-04-06.txt). If a
	 * current file is open , it will close it. The log file gets toggled on a
	 * daily basis
	 */
	void createLogRecordFile(String suffix)
			throws SmartCameraInternalException {
		// Create the log
		String theFQFileName = iDirName + '/' + "Log" + '_' + suffix + ".txt";

		try {
			// If there is a file open close it first
			if (iCurrentLogFile != null)
				iCurrentLogFile.close();
			iCurrentLogFile = new FileWriter(theFQFileName, true);
			iCurrentLogSuffix = suffix;

		} catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}

	/**
	 * This method writes the count record to the file. It derives the number
	 * of people entered and exited based on the previous record.
	 */
	synchronized void write(SmartCameraRecordSummary summary,
			SmartCameraHeaderRecord metricsRecord,
			SmartCameraCountRecord countRecord)
			throws SmartCameraInternalException {
		StringBuffer sb = new StringBuffer();

		Date lastTS = summary.getCountRecordReceived(countRecord.getReportObjectId());
		try {
			// parse the timestamps and figure out whether we have changed files
			// to see if we
			// need to toggle the output file
			Date startTS = countRecord.getStartTimeStamp();
			String startTSStr = cISODateFileDTFmt.format(summary
					.getDateBasedOnLocalTime(startTS));			
			Date endTS = countRecord.getEndTimeStamp();
			String endTSStr = cISODateFileDTFmt.format(summary
					.getDateBasedOnLocalTime(endTS));


			// Create the file if needed. see if we have crossed the boundary of
			// the day
			if (!startTSStr.equals(endTSStr)
					|| !iCurrentCountDataSuffix.equals(startTSStr))
				createCountRecordFile(startTSStr);
			// Get the number to enter and exit from the record parsed.  
			// The enter and exits are NO longer cumulatives, rather the 
			// number to enter and exit for that bucket
			int numberToEnter = countRecord.getNumToEnter();
			int numberToExit = countRecord.getNumToExit();

			// This is to make up for when the camera is restarted and since the
			// counts are not persistent, we need to ignore the first count
			if (numberToEnter >= 0 && numberToExit >= 0) {
				//if (endTS.getTime() - lastTS.getTime() != 1000 *60)
				//	System.err.println("Error in TS EndTS " + endTS + " LastTS " + lastTS);
				// Create a comma delimited record in the output file
				sb.append(metricsRecord.getDeviceId()
						+ ","
						+ cISODateTimeFileDTFmt.format(summary
								.getDateBasedOnLocalTime(startTS))
						+ ","
						+ cISODateTimeFileDTFmt.format(summary
								.getDateBasedOnLocalTime(endTS)) + ","
						+ countRecord.getReportObjectId() + ","
						+ (summary.getNumToEnter(countRecord.getReportObjectId()) +  numberToEnter) + ","
						+ (summary.getNumToExit(countRecord.getReportObjectId()) + numberToExit) + "," 
						+ numberToEnter
						+ "," + numberToExit
						+ "," 
						+ countRecord.getOfflineInd()		
						+ System.getProperty("line.separator"));
				
				
				
				
				Pflowcount pfcount = new Pflowcount();

				pfcount.setEquipmentid(iequipmentservice.GetEquipmentIdByMac(metricsRecord.getDeviceId()));
				pfcount.setMac(metricsRecord.getDeviceId());
				pfcount.setStartdate(cISODateTimeFileDTFmt.format(summary
							.getDateBasedOnLocalTime(startTS)).split(",")[0]);
				pfcount.setStarttime(cISODateTimeFileDTFmt.format(summary
							.getDateBasedOnLocalTime(startTS)).split(",")[1]);
				pfcount.setEnddate(cISODateTimeFileDTFmt.format(summary
							.getDateBasedOnLocalTime(endTS)).split(",")[0]);
				pfcount.setEndtime(cISODateTimeFileDTFmt.format(summary
							.getDateBasedOnLocalTime(endTS)).split(",")[1]);
				pfcount.setZoneid(summary.getDestTimeZoneOffset());
				pfcount.setTotalenters(Long.valueOf(summary.getNumToEnter(countRecord.getReportObjectId()) +  numberToEnter));
				pfcount.setTotalexits(Long.valueOf(summary.getNumToExit(countRecord.getReportObjectId()) + numberToExit));
				pfcount.setEnters(numberToEnter);
				pfcount.setExits(numberToExit);
				pfcount.setStatus(countRecord.getOfflineInd());	
				
				ipfcountservice.addPfcountInfo(pfcount);
						
				// write the record and flush it
				iCurrentCountDataFile.write(sb.toString());
				iCurrentCountDataFile.flush();
			}
		} catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}

	public void write(RecordSummary summary,
			SmartCameraHeaderRecord headerRecord,
			SmartCameraDetectionRecord detectionRecord) throws SmartCameraInternalException {
		
		StringBuffer sb = new StringBuffer();

		try {
			// parse the timestamps and figure out whether we have changed files
			// to see if we
			// need to toggle the output file
			Date startTS = detectionRecord.getStartTimeStamp();
			String startTSStr = cISODateFileDTFmt.format(summary
					.getDateBasedOnLocalTime(startTS));
			Date endTS = detectionRecord.getEndTimeStamp();
			String endTSStr = cISODateFileDTFmt.format(summary
					.getDateBasedOnLocalTime(endTS));


			// Create the file if needed. see if we have crossed the boundary of
			// the day
			if (!startTSStr.equals(endTSStr)
					|| !iCurrentDetectionDataSuffix.equals(startTSStr))
				createDetectionRecordFile(startTSStr);

			// Create a comma delimited record in the output file
			sb.append(headerRecord.getDeviceId()
					+ ","
					+ cISODateTimeFileDTFmt.format(summary
							.getDateBasedOnLocalTime(startTS))
					+ ","
					+ cISODateTimeFileDTFmt.format(summary
							.getDateBasedOnLocalTime(endTS)) 
					+ ","
					+ detectionRecord.getReportObjectId() 
					+ ","
					+ detectionRecord.getSecondsOccupied()
					+ "," 
					+ detectionRecord.getAverageValue()
					+ "," 
					+ detectionRecord.getOfflineInd()				
					+ System.getProperty("line.separator"));
			
			// write the record and flush it
			iCurrentDetectionDataFile.write(sb.toString());
			iCurrentDetectionDataFile.flush();
		
		} catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}

		
	}
	
	/**
	 * Write the log message that is received from the camera
	 *  
	 */
	public void write(RecordSummary logSummary, Date currentTS,
			SmartCameraHeaderRecord headerRecord, EventRecord diagRecord)
			throws SmartCameraInternalException {

		// Gets the last record received date timestamp
		Date lastTS = logSummary.getLastRecordReceived();
		String lastTSStr = cISODateFileDTFmt.format(lastTS);
		StringBuffer sb = new StringBuffer();
		try {
			// Create a new log file if we have switched day boundaries
			if (!lastTSStr.equals(lastTSStr)
					|| !iCurrentLogSuffix.equals(lastTSStr))
				createLogRecordFile(lastTSStr);
			// Build a comma delimited record
			sb.append(headerRecord.getDeviceId()
					+ ","
					+ headerRecord.getIPAddress()
					+ ","
					+ cISODateTimeFileDTFmt.format(currentTS)
					+ ","
					+ cISODateTimeFileDTFmt.format(logSummary
							.getDateBasedOnLocalTime(diagRecord.getTimeStamp()))
					+ "," + diagRecord.getEventId() + ","
					+ diagRecord.getLevelId() + "," + diagRecord.getMessage() + ","
					+ "[ "
					+ getStats(diagRecord)
					+ " ]"
					+ System.getProperty("line.separator"));

			// Write the log record
			iCurrentLogFile.write(sb.toString());
			iCurrentLogFile.flush();
		} catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}

	}

	// This function iterates over the Stats Map and dumps its content (key and value)
	// to a srtring to be returned
	private String getStats(EventRecord diagRecord) {
		StringBuffer sb = new StringBuffer();
		
		Map stats = diagRecord.getStats();
	    Iterator it = stats.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pairs = (Map.Entry)it.next();
	        sb.append(pairs.getKey() + " = " + pairs.getValue());
	        if (it.hasNext())
	        	sb.append(", ");
	    }
		return sb.toString();
	}

	/**
	 * Write the Queue record to a file
	 *  
	 */	
	public void write(RecordSummary queueSummary,
			SmartCameraHeaderRecord headerRecord,
			SmartCameraQueueRecord queueRecord) throws SmartCameraInternalException {
		StringBuffer sb = new StringBuffer();
		
		try
		{
		// parse the timestamps and figure out whether we have changed files
		// to see if we
		// need to toggle the output file
		Date startTS = queueRecord.getStartTimeStamp();
		String startTSStr = cISODateFileDTFmt.format(queueSummary
				.getDateBasedOnLocalTime(startTS));
		Date endTS = queueRecord.getEndTimeStamp();
		String endTSStr = cISODateFileDTFmt.format(queueSummary
				.getDateBasedOnLocalTime(endTS));

		// Create the file if needed. see if we have crossed the boundary of
		// the day
		if (!startTSStr.equals(endTSStr)
				|| !iCurrentQueueDataSuffix.equals(endTSStr))
			createQueueRecordFile(endTSStr);
		
		// Create a comma delimited record in the output file
		sb.append(headerRecord.getDeviceId()
				+ ","
				+ cISODateTimeFileDTFmt.format(queueSummary
						.getDateBasedOnLocalTime(startTS))
				+ ","
				+ cISODateTimeFileDTFmt.format(queueSummary
						.getDateBasedOnLocalTime(endTS)) 
				+ ","
				+ queueRecord.getReportObjectId() 
				+ ","
				+ queueRecord.getAvgQLength() 
				+ "," 
				+ queueRecord.getMaxQLength()  
				+ "," 
				+ queueRecord.getMinQLength()
				+ "," 
				+ queueRecord.getSecsOpen() 
				+ "," 
				+ queueRecord.getExitCount() 
				+ "," 
				+ queueRecord.getOfflineInd()		
				+ System.getProperty("line.separator"));
		// Write the data
		iCurrentQueueDataFile.write(sb.toString());
		iCurrentQueueDataFile.flush();
		
		}catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}
	
	/**
	 * Write the Service record to a file
	 *  
	 */	
	public void write(RecordSummary serviceSummary,
			SmartCameraHeaderRecord headerRecord,
			SmartCameraServiceRecord serviceRecord) throws SmartCameraInternalException {
		StringBuffer sb = new StringBuffer();
		
		try
		{
		// parse the timestamps and figure out whether we have changed files
		// to see if we
		// need to toggle the output file
		Date startTS = serviceRecord.getStartTimeStamp();
		String startTSStr = cISODateFileDTFmt.format(serviceSummary
				.getDateBasedOnLocalTime(startTS));
		Date endTS = serviceRecord.getEndTimeStamp();
		String endTSStr = cISODateFileDTFmt.format(serviceSummary
				.getDateBasedOnLocalTime(endTS));

		// Create the file if needed. see if we have crossed the boundary of
		// the day
		if (!startTSStr.equals(endTSStr)
				|| !iCurrentServiceDataSuffix.equals(endTSStr))
			createServiceRecordFile(endTSStr);
		
		// Create a comma delimited record in the output file
		sb.append(headerRecord.getDeviceId()
				+ ","
				+ cISODateTimeFileDTFmt.format(serviceSummary
						.getDateBasedOnLocalTime(startTS))
				+ ","
				+ cISODateTimeFileDTFmt.format(serviceSummary
						.getDateBasedOnLocalTime(endTS)) 
				+ ","
				+ serviceRecord.getReportObjectId() 
				+ ","
				+ serviceRecord.getNumberServed()  
				+ "," 
				+ serviceRecord.getSecondsOccupied()
				+ "," 
				+ serviceRecord.getOfflineInd()				
				+ System.getProperty("line.separator"));
		// Write the data
		iCurrentServiceDataFile.write(sb.toString());
		iCurrentServiceDataFile.flush();
		
		}catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}
		
	
	/**
	 * Write the A wait time record to a file
	 *  
	 */	
	public void write(RecordSummary queueSummary,
			SmartCameraHeaderRecord headerRecord,
			SmartCameraWaitTimeRecord waitTimeRecord) throws SmartCameraInternalException {
		StringBuffer sb = new StringBuffer();
		
		try
		{
		// parse the timestamps and figure out whether we have changed files
		// to see if we
		// need to toggle the output file
		Date startTS = waitTimeRecord.getStartTimeStamp();
		String startTSStr = cISODateFileDTFmt.format(queueSummary
				.getDateBasedOnLocalTime(startTS));
		Date endTS = waitTimeRecord.getEndTimeStamp();
		String endTSStr = cISODateFileDTFmt.format(queueSummary
				.getDateBasedOnLocalTime(endTS));

		// Create the file if needed. see if we have crossed the boundary of
		// the day
		if (!startTSStr.equals(endTSStr)
				|| !iCurrentQueueDataSuffix.equals(endTSStr))
			createQueueRecordFile(endTSStr);
		
		// Create a comma delimited record in the output file
		sb.append(headerRecord.getDeviceId()
				+ ","
				+ cISODateTimeFileDTFmt.format(queueSummary
						.getDateBasedOnLocalTime(startTS))
				+ ","
				+ cISODateTimeFileDTFmt.format(queueSummary
						.getDateBasedOnLocalTime(endTS)) 
				+ ","
				+ waitTimeRecord.getReportObjectId()	
				+ ","
				+ waitTimeRecord.getDuration()	
				+ ","
				+ waitTimeRecord.getRecordType()	
				+ System.getProperty("line.separator"));
		// Write the data
		iCurrentWaitTimeDataFile.write(sb.toString());
		iCurrentWaitTimeDataFile.flush();
		
		}catch (IOException e) {
			throw new SmartCameraInternalException(e.getMessage());
		}
	}



}
