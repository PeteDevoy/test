package com.cn.pflow.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class parseTest {

	
	 public static void main(String[] args) throws Exception
	    {


	        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	        DocumentBuilder db = dbf.newDocumentBuilder();
	        String str = "<RealTimeMetrics  SiteId=\"China Telecom 2\"><Properties><MacAddress>74:da:ea:4f:3b:84</MacAddress><IpAddress>192.168.1.8</IpAddress><HostName>Cam-16080554</HostName><HttpPort>80</HttpPort><HttpsPort>443</HttpsPort><Timezone>8</Timezone><DST>0</DST><HwPlatform>3210</HwPlatform><SerialNumber>16080554</SerialNumber><DeviceType>0</DeviceType></Properties><RTReport  Date=\"2016-11-02T16:07:39\"><RTObject  Id=\"0\" DeviceId=\"China Telecom 2\" Devicename=\"China Telecom 2\" ObjectType=\"0\" Name=\"Main2\"><RTCount  TotalEnters=\"13\" TotalExits=\"6\"/></RTObject></RTReport></RealTimeMetrics>";
	        InputStream is =  new ByteArrayInputStream(str.getBytes());
	        Document document = db.parse(is);
	        System.out.println(document.getElementsByTagName("MacAddress").item(0).getFirstChild().getNodeValue());
	        System.out.println(document.getElementsByTagName("RTReport").item(0).getAttributes().getNamedItem("Date").getNodeValue()); 
	        System.out.println(document.getElementsByTagName("RTCount").item(0).getAttributes().getNamedItem("TotalEnters").getNodeValue()); 
	        System.out.println(document.getElementsByTagName("RTCount").item(0).getAttributes().getNamedItem("TotalExits").getNodeValue());  
	
	    }

}
