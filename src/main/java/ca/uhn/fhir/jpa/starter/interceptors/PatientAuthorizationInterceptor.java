package ca.uhn.fhir.jpa.starter.interceptors;

import ca.uhn.fhir.i18n.Msg;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;
import org.hl7.fhir.r4.model.IdType;
import java.util.List;

public class PatientAuthorizationInterceptor extends AuthorizationInterceptor {

	@Override
	public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {
		IdType userIdPatientId = null;
		String token = theRequestDetails.getHeader("token");
		if (tokenExists(token) && tokenIsValid(token)) {
			// This user has access only to his own Patient resources
			userIdPatientId = new IdType("Patient", getPatientIDThroughToken(token));
		} else {
			// Throw an HTTP 401
			throw new AuthenticationException(Msg.code(644) + "Missing or invalid Authorization header value");
		}

		if (userIdPatientId != null) {
			return new RuleBuilder()
				.allow().read().allResources().inCompartment("Patient", userIdPatientId).andThen()
				.allow().write().allResources().inCompartment("Patient", userIdPatientId).andThen()
				.denyAll()
				.build();
		}

		return new RuleBuilder()
			.denyAll()
			.build();
	}

	private Boolean tokenIsValid(String token) {
		return true;
	}

	private Boolean tokenExists(String token) {
		return true;
	}

	private String getPatientIDThroughToken(String token) {
		return "test";
	}
}
