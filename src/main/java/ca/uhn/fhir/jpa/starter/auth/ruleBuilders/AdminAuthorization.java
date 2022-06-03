package ca.uhn.fhir.jpa.starter.auth.ruleBuilders;

import ca.uhn.fhir.jpa.starter.auth.AuthUser;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;

import java.util.List;
public class AdminAuthorization implements CustomAuth  {
	public AdminAuthorization() {
		super();
	}

	public List<IAuthRule> buildRuleList(AuthUser user) {
		RuleBuilder ruleBuilder = new RuleBuilder();

		if (user.getIdType() != null) {
			ruleBuilder.allow().read()
				.allResources().inCompartment("Patient", user.getIdType());
			ruleBuilder.allow().write()
				.allResources().inCompartment("Patient", user.getIdType());
		}

		return ruleBuilder
			.denyAll()
			.build();
	}
}