package ca.uhn.fhir.jpa.starter.interceptors;

import ca.uhn.fhir.i18n.Msg;
import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Interceptor;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import org.apache.commons.codec.binary.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Interceptor
public class GenericAuthenticationInterceptor {

	@Hook(Pointcut.SERVER_INCOMING_REQUEST_POST_PROCESSED)
	public boolean incomingRequestPostProcessed(
		RequestDetails theRequestDetails,
		HttpServletRequest theRequest,
		HttpServletResponse theResponse
	) throws AuthenticationException {
		String authHeader = theRequest.getHeader("Auth");

		// Some authentication check of the auth that is gathered.
		if (someAuthenticationCheck()) {
			throw new AuthenticationException(Msg.code(642) + "Missing or invalid Authorization header");
		}

		// Some checks consisting of the username:password and/or token, checking if the user is authenticated.
		// Throw new AuthenticationException if something isn't correct.

		// Return true to allow the request to proceed
		return true;
	}

	private Boolean someAuthenticationCheck() {
		return true;
	}
}