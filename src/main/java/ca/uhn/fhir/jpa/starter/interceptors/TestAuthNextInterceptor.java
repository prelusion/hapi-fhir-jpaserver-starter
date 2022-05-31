package ca.uhn.fhir.jpa.starter.interceptors;

import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;

import java.util.List;

public class TestAuthNextInterceptor extends AuthorizationInterceptor {
	@Override
	public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {
			return new RuleBuilder()
				.denyAll()
				.build();
		}
}


