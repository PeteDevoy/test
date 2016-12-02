package com.cn.pflow.interceptor;



import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;

@SuppressWarnings("rawtypes")
public class SampleInterceptor extends  AbstractPhaseInterceptor {

	public SampleInterceptor() {
		super(Phase.PRE_INVOKE);
		
	}

	public SampleInterceptor(String str) {
		super(str);
		
	}
	

	public void handleMessage(Message message) throws Fault {

	        System.out.println(message);
	 
	        if (message.getDestination() != null) {
	            System.out.println(message.getId() + "*" + message.getDestination().getMessageObserver());
	        }
	        if (message.getExchange() != null) {
	            System.out.println(message.getExchange().getInMessage() + "*" + message.getExchange().getInFaultMessage());
	            System.out.println(message.getExchange().getOutMessage() + "*" + message.getExchange().getOutFaultMessage());
	        }
	}

}
