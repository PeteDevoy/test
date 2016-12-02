/**
 * This class represents the records summary which holds the last 
 * number to enter/exit along with the device id.  
 *
 **/
package brickstream.http.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import brickstream.exception.SmartCameraInternalException;
import brickstream.metrics.model.RecordSummary;
import brickstream.metrics.model.SmartCameraCountRecord;


public class SmartCameraRecordSummary extends RecordSummary {

	// The summary object device Id
	protected String iDeviceId = "";
	
	// A map of the report objects kept in the cache
	HashMap iReportObjects = new HashMap();

	/**
	 * Public constructor
	 */
	public SmartCameraRecordSummary(String deviceId, Date ts, ArrayList countRecords, boolean dstEnabled,
			int destTimeZoneOffset) {
		// CHANGETS		
		super(ts, dstEnabled, destTimeZoneOffset);
		iDeviceId = deviceId;
		for (int i=0;i<countRecords.size();i++ )
		{
			SmartCameraCountRecord record = (SmartCameraCountRecord) countRecords.get(i);
			
			// Add the report Object
			iReportObjects.put(record.getReportObjectId(), 
					new ReportObjectData(record.getReportObjectId(), 0,0, record.getEndTimeStamp() ));
		}
	}
	
	/**
	 * @return The device id of this summary object
	 */
	public String getDeviceId() {
		return iDeviceId;
	}
	
	/**
	 * @param The device id of this summary object
	 */	
	public void setDeviceId(String deviceId) {
		iDeviceId = deviceId;
	}
	
	/**
	 * @param reportObjectId The report object id for which we are trying to get the number to enter 
	 * @return The current number to enter
	 * @throws SmartCameraInternalException
	 */
	public int getNumToEnter(String reportObjectId) throws SmartCameraInternalException {
		ReportObjectData reportObject = (ReportObjectData)iReportObjects.get(reportObjectId);
		if (reportObject == null)
			throw new SmartCameraInternalException("Invalid report Object Id requested " + reportObjectId);
		else
			return reportObject.getNumToEnter();
	}

	/**
	 * @param reportObjectId The report object id for which we are trying to get the number to exit 
	 * @return The current number to exit
	 * @throws SmartCameraInternalException
	 */
	public int getNumToExit(String reportObjectId) throws SmartCameraInternalException {
		ReportObjectData reportObject = (ReportObjectData)iReportObjects.get(reportObjectId);
		if (reportObject == null)
			throw new SmartCameraInternalException("Invalid report Object Id requested " + reportObjectId);
		else
			return reportObject.getNumToExit();
	}

	/**
	 * This method updates this summary object with the latest enter and exits values using the
	 * report object as a key.  If the report object is not found in the list of objects, a new onbe is created
	 * @param countRecord The current CountRecord object used to update this summary object
	 */
	public void setEntersAndExits(SmartCameraCountRecord countRecord) {
		ReportObjectData reportObject = (ReportObjectData)iReportObjects.get(countRecord.getReportObjectId());
		// if the report object does not exist, create it
		if (reportObject == null)
			// CHANGETS
			iReportObjects.put(countRecord.getReportObjectId(), 
					new ReportObjectData(countRecord.getReportObjectId(), countRecord.getNumToEnter(), countRecord.getNumToExit(), countRecord.getStartTimeStamp() ));
		else
		{	// Update the values if the report object exists
			reportObject.setNumToEnter(countRecord.getNumToEnter());			
			reportObject.setNumToExit(countRecord.getNumToExit());
			super.setLastRecordReceived(countRecord.getStartTimeStamp());
			reportObject.setLastRecordReceived(countRecord.getStartTimeStamp());		
		}
	}

	/**
	 * @param reportObjectId  The report object for which we are trying to get the latest received timestamp
	 * @return The date object that states when the latest record was received
	 */
	public Date getCountRecordReceived(String reportObjectId) throws SmartCameraInternalException {
		ReportObjectData reportObject = (ReportObjectData)iReportObjects.get(reportObjectId);
		if (reportObject == null)
			throw new SmartCameraInternalException("Invalid report Object Id requested " + reportObjectId);
		else
			return reportObject.getLastRecordReceived();
	}
}
