package brickstream.http.model;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SimpleTimeZone;
import java.util.Map.Entry;


import brickstream.exception.SmartCameraInternalException;
import brickstream.metrics.model.SmartCameraCountRecord;
import brickstream.metrics.model.SmartCameraXMLParser;
import brickstream.metrics.utils.HttpConnection;
import brickstream.metrics.utils.KeyValuePair;



public class SmartCameraDataRetrieval {

	// report parameters
	static final String REPORT_START_INTERVAL = "REPORT_START_INTERVAL";
	static final String REPORT_END_INTERVAL = "REPORT_END_INTERVAL";
	static final String REPORT_AGGREGATION_LEVEL = "REPORT_AGGREGATION_LEVEL";
	static final String REPORT_DAY = "REPORT_DAY";
	
	// CGI function to invoke when getting reports
	static final String GET_REPORT = "genReport.cgi";
	
	private static final int NUMBER_OF_DAYS = 10;
	private static final long NUMBER_OF_MILLISECONDS_PER_DAY = 60 * 60 * 1000 * 24;
	
	private final String iIP;
	private final int iDataPort;
	private final int iAggregation;
	private final String iDirName;
	
	// No DST timezone
	SimpleTimeZone iSourceTZ = new SimpleTimeZone(0, "GMT");
	
	// The requested report date format
	static String REPORT_DATE_FORMAT = "MM/dd/yyyy";
	
	// This datetime pattern is used internally to query the camera for report days 
	SimpleDateFormat iReportDateTimeFmt = new SimpleDateFormat(REPORT_DATE_FORMAT);
	
	public SmartCameraDataRetrieval(String ip, int dataPort, int aggregation,
			String dirName) {
		
		iIP = ip;
		iDataPort = dataPort;
		iAggregation = aggregation;
		iDirName = dirName;
		
		// No timezone info
        iReportDateTimeFmt.setTimeZone(iSourceTZ);
	}
	
	public String[] getLast10Days()
	{
        int dateLocation=0;
        long previousDay = 0;
        String[] reportDates = new String[NUMBER_OF_DAYS];
        for (int i=NUMBER_OF_DAYS-1;i>=0;i--)
        {
        	// We want to set the calendar to 4 am of the day for DST purposes.
        	// When it is 11:00PM on Nov 5th we seem to pick up Nov 1st (DST change)multiple 
        	// time when we subtract one day from the calendar
        	Calendar cal = Calendar.getInstance();
        	cal.set(Calendar.HOUR_OF_DAY, 4); 
        	Date d = cal.getTime();
        	previousDay = d.getTime() - (i* NUMBER_OF_MILLISECONDS_PER_DAY );
        	d.setTime(previousDay);
        	//System.err.println(d);
        	reportDates[dateLocation++] = iReportDateTimeFmt.format(d);        	
        }
        return reportDates;
	}
	
	// re-sorts the records by record id
	HashMap<String, ArrayList<SmartCameraCountRecord>> getRecordsById(
			ArrayList<SmartCameraCountRecord> countRecords) {

		String lastReportObjectId = "-1";
		HashMap<String, ArrayList<SmartCameraCountRecord>> recordById = new HashMap<String, ArrayList<SmartCameraCountRecord>>();
		ArrayList<SmartCameraCountRecord> smartCameraList = null;
		;
		for (int c = 0; c < countRecords.size(); c++) {
			if (!lastReportObjectId.equals(countRecords.get(c)
					.getReportObjectId())) {
				smartCameraList = new ArrayList<SmartCameraCountRecord>();
				recordById.put(countRecords.get(c).getReportObjectId(),
						smartCameraList);
			}
			smartCameraList.add(countRecords.get(c));
			lastReportObjectId = countRecords.get(c).getReportObjectId();
		}
		return recordById;
	}
	
	public void getReport(String reportDay) {
		// List used for parameters
		List<KeyValuePair> theList = new ArrayList<KeyValuePair>();

		// reportDay = "05/27/2010";
		// Get the user Info

		try {
			// Clear the list in between runs
			theList.clear();


			HttpConnection conn = null;
			// Build the http url to connect to
			String device = "http://"
					+ iIP + ":"
					+ iDataPort + "/";
			

//				// Now send the data
//				conn = new HttpConnection(device, userInfo.getUserId(),
//						userInfo.getPassword());
//			else
			conn = new HttpConnection(device);

			theList.add(new KeyValuePair(REPORT_START_INTERVAL,
					"0"));
			theList.add(new KeyValuePair(REPORT_END_INTERVAL,
					"24"));
			theList.add(new KeyValuePair(REPORT_AGGREGATION_LEVEL,
					String.valueOf(iAggregation)));
			// theList.add(new KeyValuePair(REPORT_DAY, "10/19/2009"));
			// If we want a day other than today, than we need to
			// specify the REPORT_DAY
			if (reportDay != null)
				theList.add(new KeyValuePair(REPORT_DAY, reportDay));
			String theString = HttpConnection
					.getBufferFromList(theList);
			// Now get the params from the camera
			String theXML = conn.sendGetReturnString(GET_REPORT + "?"
					+ theString, theList);
			
			SmartCameraXMLParser theParser = new SmartCameraXMLParser();

			theParser.parseXML(theXML);
			//System.err.println(theXML);

			// get the records by id
			HashMap<String, ArrayList<SmartCameraCountRecord>> recordById = getRecordsById(theParser
					.getCountRecords());

			Iterator<Entry<String, ArrayList<SmartCameraCountRecord>>> iterator = recordById
					.entrySet().iterator();
			boolean dayRollover = false;
			SmartCameraRecordWriter writer = new SmartCameraRecordWriter(
					iDirName);
			// Create a summary record
			SmartCameraRecordSummary summary = null;
			if (theParser.getCountRecords().size() > 0) {
					SmartCameraCountRecord record = (SmartCameraCountRecord) theParser.getCountRecords().get(theParser.getCountRecords().size() -1);
					if (record != null) {					
						summary = new SmartCameraRecordSummary(theParser
								.getHeaderRecord().getDeviceId(), record.getEndTimeStamp(), theParser.getCountRecords(), theParser
								.getHeaderRecord().getDST(), theParser
								.getHeaderRecord().getTimeZoneOffset());
					}
				
				
				while (iterator.hasNext()) {
					Entry<String, ArrayList<SmartCameraCountRecord>> mapEntry = iterator
							.next();
					ArrayList<SmartCameraCountRecord> list = mapEntry.getValue();
					for (int i=0;i<list.size();i++)
						writer.write(summary, theParser.getHeaderRecord(), list.get(i));
	
				}
			}
			else
				System.err.println("No data found  for " + reportDay);
	

		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}

	/**
	 * Update the summary object with the latest enter and exits counts
	 * 
	 * @throws SmartCameraInternalException
	 */
	private void updateDevice(SmartCameraRecordSummary summary,
			SmartCameraXMLParser xmlParser,
			SmartCameraCountRecord countRecord)
			{

		//Date dt = countRecord.getTimeStamp();
		summary.setEntersAndExits(countRecord);
		summary.setDSTEnabled(xmlParser.getHeaderRecord().getDST());

		summary.setDestTimeZoneOffset(xmlParser.getHeaderRecord()
				.getTimeZoneOffset());
	}
	
	public void getReportUnsorted(String reportDay) {
		// List used for parameters
		List<KeyValuePair> theList = new ArrayList<KeyValuePair>();

		// reportDay = "05/27/2010";
		// Get the user Info

		try {
			// Clear the list in between runs
			theList.clear();


			HttpConnection conn = null;
			// Build the http url to connect to
			String device = "http://"
					+ iIP + ":"
					+ iDataPort + "/";
			

//				// Now send the data
//				conn = new HttpConnection(device, userInfo.getUserId(),
//						userInfo.getPassword());
//			else
			conn = new HttpConnection(device);

			theList.add(new KeyValuePair(REPORT_START_INTERVAL,
					"0"));
			theList.add(new KeyValuePair(REPORT_END_INTERVAL,
					"24"));
			theList.add(new KeyValuePair(REPORT_AGGREGATION_LEVEL,
					String.valueOf(iAggregation)));
			// theList.add(new KeyValuePair(REPORT_DAY, "10/19/2009"));
			// If we want a day other than today, than we need to
			// specify the REPORT_DAY
			if (reportDay != null)
				theList.add(new KeyValuePair(REPORT_DAY, reportDay));
			String theString = HttpConnection
					.getBufferFromList(theList);
			// Now get the params from the camera
			String theXML = conn.sendGetReturnString(GET_REPORT + "?"
					+ theString, theList);
			
			SmartCameraXMLParser theParser = new SmartCameraXMLParser();

			theParser.parseXML(theXML);
			//System.err.println(theXML);

			// get the records by id

			ArrayList<SmartCameraCountRecord> list = theParser.getCountRecords();
			boolean dayRollover = false;
			SmartCameraRecordWriter writer = new SmartCameraRecordWriter(
					iDirName);
			// Create a summary record
			SmartCameraRecordSummary summary = null;
			if (theParser.getCountRecords().size() > 0) {
					SmartCameraCountRecord record = (SmartCameraCountRecord) theParser.getCountRecords().get(theParser.getCountRecords().size() -1);
					if (record != null) {					
						summary = new SmartCameraRecordSummary(theParser
								.getHeaderRecord().getDeviceId(), record.getEndTimeStamp(), theParser.getCountRecords(), theParser
								.getHeaderRecord().getDST(), theParser
								.getHeaderRecord().getTimeZoneOffset());
					}
				
					for (int i=0;i<list.size();i++)
					{
						record = list.get(i);

						writer.write(summary, theParser.getHeaderRecord(), record);
						updateDevice(summary, theParser, record);
					}
	
				}
			
			else
				System.err.println("No data found  for " + reportDay);
	

		}
		catch (Exception e)
		{
			System.err.println(e.getMessage());
		}
	}	

	private void process() {
		
		String[] reportDates = getLast10Days();
		
		for (int i=0;i<reportDates.length;i++)
		{
			System.out.println("Retrieving data for " + reportDates[i]);
			getReportUnsorted(reportDates[i]);			
		}
		
	}	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String dirName;
		int dataPort;
		int aggregation = 15;
		String ip;
		if (args.length < 4)
		{
			System.err.println("Usage is: <IP> <Port> <Aggregation> <OutputDir>");
			System.exit(-1);
		}
		
		ip = args[0];
		try {
			dataPort = Integer.parseInt(args[1]);

		} catch (Exception e) {
			dataPort = 2000;			
		}
		
		try {
			aggregation = Integer.parseInt(args[2]);

		} catch (Exception e) {
			aggregation = 15;			
		}
		
		dirName = args[3];
		if (dirName == null || dirName.equals(""))
			dirName = "C://temp/";

		System.out.println("Using IP '" + ip + "' Port '" + dataPort + "' Aggreation '" + aggregation + "' Output Directory '" + dirName + "'");
		SmartCameraDataRetrieval retrieval = new SmartCameraDataRetrieval(ip, dataPort, aggregation, dirName);
		
		retrieval.process();
	}



}
