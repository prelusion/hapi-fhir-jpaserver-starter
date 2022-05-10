package ca.uhn.fhir.jpa.starter.interceptors;

import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.BaseServerResponseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Interceptor //Optional annotation
public class RequestExceptionInterceptor {

	public RequestExceptionInterceptor() {
	}

	// A must, points out that it should call after a certain request

	private int myRequestCount;

	public int getRequestCount() {
		return myRequestCount;
	}

	/**
	 * Override the incomingRequestPreProcessed method, which is called
	 * for each incoming request before any processing is done
	 */
	@Hook(Pointcut.SERVER_INCOMING_REQUEST_PRE_PROCESSED)
	public boolean incomingRequestPreProcessed(HttpServletRequest theRequest, HttpServletResponse theResponse) {
		myRequestCount++;
		System.out.print(myRequestCount);
		System.out.println(myRequestCount);
		return true;
	}


}
