package ca.uhn.fhir.jpa.starter.auth.ruleBuilders;

import ca.uhn.fhir.jpa.starter.auth.AuthUser;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;

import java.util.List;

public interface CustomAuth {
	List<IAuthRule> buildRuleList(AuthUser user);
}
