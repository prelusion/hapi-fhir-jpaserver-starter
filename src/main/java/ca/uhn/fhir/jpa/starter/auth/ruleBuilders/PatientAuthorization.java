package ca.uhn.fhir.jpa.starter.auth.ruleBuilders;

import ca.uhn.fhir.jpa.starter.auth.AuthUser;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;

import java.util.List;

public class PatientAuthorization implements CustomAuth {
	public PatientAuthorization() {
		super();
	}

	public List<IAuthRule> buildRuleList(AuthUser user)  {
		RuleBuilder ruleBuilder = new RuleBuilder();
		ruleBuilder.deny().write().allResources();

		if (user.getIdType() != null) {
			ruleBuilder.allow().read()
				.allResources().inCompartment("Patient", user.getIdType());
		}

		return ruleBuilder
			.denyAll()
			.build();
	}
}
