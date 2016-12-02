package brickstream.http.model;

import java.util.Date;

/**
 * This class hold the total number to enter and exits by report object id  
 */
public class ReportObjectData {


	// This is a cumulative number of all the number to enter since the SmartCameraHttp
	// has been up.  If you reboot the SmartCameraHttp, this count will revert back to 0
	private int iNumToEnter = -1;

//	 This is a cumulative number of all the number to exit since the SmartCameraHttp
	// has been up.  If you reboot the SmartCameraHttp, this count will revert back to 0
	private int iNumToExit = -1;

	protected String iReportObjectId = "";
	
	// The date ts of the last record received from the site
	protected Date iLastRecordReceived = null;
	/**
	 * 
	 */
	public ReportObjectData(String reportObjectId, int lastNumberToEnter, int lastNumberToExit, Date lastRecordReceived) {
		super();
		iReportObjectId = reportObjectId;
		iNumToEnter = lastNumberToEnter;
		iNumToExit = lastNumberToExit;
		iLastRecordReceived = lastRecordReceived;
		
	}
	// Getter and setter functions
	public int getNumToEnter() {
		return iNumToEnter;
	}
	public void setNumToEnter(int lastNumToEnter) {
		iNumToEnter += lastNumToEnter;
	}
	public int getNumToExit() {
		return iNumToExit;
	}
	public void setNumToExit(int lastNumToExit) {
		iNumToExit += lastNumToExit;
	}
	public Date getLastRecordReceived() {
		return iLastRecordReceived;
	}
	public void setLastRecordReceived(Date lastRecordReceived) {
		iLastRecordReceived = lastRecordReceived;
	}
	public String getReportObjectId() {
		return iReportObjectId;
	}
	public void setReportObjectId(String reportObjectId) {
		iReportObjectId = reportObjectId;
	}

}
