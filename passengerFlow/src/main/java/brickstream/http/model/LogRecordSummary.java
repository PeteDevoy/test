package brickstream.http.model;

import java.util.Date;

import brickstream.metrics.model.RecordSummary;


/**
 * This class acts like a place holder for the last Log message received
 *  It is used to figure out whether the log should be toggled
 */
public class LogRecordSummary extends RecordSummary {

	/**
	 * @param deviceId
	 * @param lastRecordReceived
	 * @param dstEnabled
	 * @param destTimeZoneOffset
	 */
	public LogRecordSummary(Date lastRecordReceived, boolean dstEnabled,
			int destTimeZoneOffset) {
		super(lastRecordReceived, dstEnabled, destTimeZoneOffset);

	}

}
