package ca.uhn.fhir.jpa.starter.interceptors;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;

@Interceptor // Optional annotation

public class TestInterceptor {

	public TestInterceptor() {
	}

	// @Hook(Pointcut.SERVER_INCOMING_REQUEST_PRE_PROCESSED) {

	// }

}
