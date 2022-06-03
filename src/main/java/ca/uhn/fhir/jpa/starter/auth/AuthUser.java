package ca.uhn.fhir.jpa.starter.auth;

import ca.uhn.fhir.jpa.starter.AppProperties;
import org.hl7.fhir.r4.model.IdType;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthUser {
	@Autowired
	AppProperties appProperties;
	private String id = "";
	private String role = "";
	private IdType idType = null;

	public AuthUser() {
		super();
	}

	public AuthUser(String id, String role, IdType idType) {
		super();
		this.setId(id);
		this.setRole(role);
		this.setIdType(idType);
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}
}
