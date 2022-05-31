package ca.uhn.fhir.jpa.starter.interceptors;

import ca.uhn.fhir.i18n.Msg;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import ca.uhn.fhir.rest.server.interceptor.auth.AuthorizationInterceptor;
import ca.uhn.fhir.rest.server.interceptor.auth.IAuthRule;
import ca.uhn.fhir.rest.server.interceptor.auth.RuleBuilder;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.hl7.fhir.r4.model.IdType;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.Base64;
import java.util.List;

public class PatientAuthorizationInterceptor extends AuthorizationInterceptor {

	@Override
	public List<IAuthRule> buildRuleList(RequestDetails theRequestDetails) {
		IdType userIdPatientId = null;
		String role = "Patient";
		String jsonString = "";

		String secret = "eb60fe17c4385278ae533d251ea38409ed841b936a148d3daba28b48e0bc064affeb96a02dd2576eac699e21ae193112a84ecbaffd3270a9807f60b417cd7f0b41795ae241eb5ce338798c9f8afbfd2e2c422bfc480dab91ce9ca4cd131693f711e788752910f64088ffb93715b693dcd91ceec8c3915178bcdb51448f4204f6";
		String token = theRequestDetails.getHeader("authorization");

		try {
			System.out.println("Test 1: try {");
			Algorithm algorithm = Algorithm.HMAC256(secret); //use more secure key
			System.out.println("Test 2: JWTVerifier verifier = JWT.require(algorithm)");
			JWTVerifier verifier = JWT.require(algorithm)
				.build(); //Reusable verifier instance
			DecodedJWT jwt = verifier.verify(token);
			jsonString = new String(Base64.getDecoder().decode(jwt.getPayload()));
			System.out.println("Test 3: jsonString = new String(Base64.getDecoder().decode(jwt.getPayload()));");
		} catch (JWTVerificationException exception) {
			System.out.println("Test 4: } catch (JWTVerificationException exception){");
			System.out.println("Catching Verify token & algorithm JWT");
			return new RuleBuilder()
				.denyAll()
				.build();
		} catch (NullPointerException e) {
			System.out.println("No auth token is given");
			return new RuleBuilder()
				.allowAll()
				.build();
		}

		try {
			System.out.println("Test 5: try {");
			JSONObject obj = new JSONObject(jsonString);
			role = (String) obj.get("role");
			userIdPatientId = new IdType("Patient", (String) obj.get("fhir_id"));
		} catch (JSONException exception) {
			System.out.println("Test 6: } catch (JSONException exception) {");
			System.out.println("Didn't contain either Role or FHIR_ID");
		}

		if (!role.equals("patient") && !role.equals("admin")) {
			System.out.println("Test 7: if (!role.equals(patient) && !role.equals(admin))");
			return new RuleBuilder()
				.denyAll()
				.build();
		}

		if (role.equals("patient")) {
			System.out.println("role.equals(\"patient\")");
			System.out.println(userIdPatientId);
			System.out.println(userIdPatientId.toString());
			System.out.println(userIdPatientId.getIdPartAsBigDecimal());
			System.out.println(userIdPatientId.getResourceType());
			return new RuleBuilder()
				.allow().read().allResources().inCompartment("Patient", userIdPatientId).andThen()
				.allow().write().allResources().inCompartment("Patient", userIdPatientId).andThen()
				.denyAll()
				.build();
		}

		if (role.equals("admin")) {
			System.out.println("Test 9: if (role.equals(Admin)) {");
			return new RuleBuilder()
				.allowAll()
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


// Reading payload out of the request
//			Reader otherReader = theRequestDetails.getReader();
//			BufferedReader bufferedReader = new BufferedReader(otherReader);
//			StringBuilder stringBuilder = new StringBuilder();
//
//			String line;
//			while ((line = bufferedReader.readLine()) != null) {
//				stringBuilder.append(line);
//			}
//
//			System.out.println("\n >>> str 3\n");
//			System.out.println(stringBuilder);
//			System.out.println("\n");