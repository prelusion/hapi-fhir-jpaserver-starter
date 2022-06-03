package ca.uhn.fhir.jpa.starter.interceptors;

import ca.uhn.fhir.jpa.starter.auth.AuthUser;
import ca.uhn.fhir.jpa.starter.auth.ruleBuilders.AdminAuthorization;
import ca.uhn.fhir.jpa.starter.auth.ruleBuilders.PatientAuthorization;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;

import java.util.List;

public class AuthHandleInterceptor extends AuthorizationInterceptor {

	AuthUser user;

	public AuthHandleInterceptor(AuthUser user) {
		super();
		this.user = user;
	}

	@Override
	public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {
		System.out.println("888");
		switch (this.user.getRole()) {
			case "patient": {
				System.out.println("patient 999");
				return new PatientAuthorization().buildRuleList(this.user);
			}
			case "admin": {
				System.out.println("admin 999");
				return new AdminAuthorization().buildRuleList(this.user);
			}
			default: {
				System.out.println("default 999");
				return new RuleBuilder()
						.allowAll()
						.build();
			}
		}
	}
}
