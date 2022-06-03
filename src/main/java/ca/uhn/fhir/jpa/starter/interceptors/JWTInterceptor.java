package ca.uhn.fhir.jpa.starter.interceptors;

import ca.uhn.fhir.interceptor.api.Hook;
import ca.uhn.fhir.interceptor.api.Pointcut;
import ca.uhn.fhir.jpa.starter.auth.AuthUser;
import ca.uhn.fhir.rest.api.server.RequestDetails;
import ca.uhn.fhir.rest.server.exceptions.AuthenticationException;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.hl7.fhir.r4.model.IdType;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class JWTInterceptor {
	AuthUser user;
	private String JWTString;

	public JWTInterceptor(AuthUser user) {
		super();
		System.out.println("222");
		this.user = user;
	}

	@Hook(Pointcut.SERVER_INCOMING_REQUEST_POST_PROCESSED)
	public boolean incomingRequestPostProcessed(
			RequestDetails theRequestDetails) throws AuthenticationException {
		String token = theRequestDetails.getHeader("authorization");

		System.out.println("\n333");
		if (!tokenIsValid(token)) {
			return false;
		}
		System.out.println("555");

		try {
			JSONObject obj = new JSONObject(this.JWTString);
			this.user.setId((String) obj.get("fhir_id"));
			this.user.setRole((String) obj.get("role"));
			this.user.setIdType(new IdType("Patient", (String) obj.get("fhir_id")));
			System.out.println("666");
			return true;
		} catch (JSONException exception) {
			System.out.println("Test 6: } catch (JSONException exception) {");
			System.out.println("Didn't contain either Role or FHIR_ID");
			return false;
		}
	}

	private Boolean tokenIsValid(String token) {
		try {
			System.out.println("Test 1: GET SECRET KEY");
			System.out.println(getSecret());
			System.out.println("444");
			Algorithm algorithm = Algorithm.HMAC256(getSecret()); // use
																																																																							// more
																																																																							// secure
																																																																							// key
			System.out.println("Test 2: JWTVerifier verifier = JWT.require(algorithm)");
			JWTVerifier verifier = JWT.require(algorithm)
					.build(); // Reusable verifier instance

			System.out.println("TOKEN");
			System.out.println(token);
			DecodedJWT jwt = verifier.verify(token);
			System.out.println("JWT");
			System.out.println(jwt);
			this.JWTString = new String(Base64.getDecoder().decode(jwt.getPayload()));
			System.out.println("Return TRUE");
			return true;
		} catch (JWTVerificationException exception) {
			System.out.println("Test 4: } catch (JWTVerificationException exception){");
			System.out.println("Catching Verify token & algorithm JWT");
			System.out.println(exception);
			return false;
		} catch (NullPointerException e) {
			System.out.println("No auth token is given");
			return false;
		}
	}

	private String getSecret() {
		String secret = "";

		// The class loader that loaded the class
		// the stream holding the file content
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("s-key.txt");

		if (inputStream == null) {
			throw new IllegalArgumentException("file not found! " + "s-key.txt");
		}

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

			secret = reader.readLine();
			return secret;

		} catch (IOException exception) {
			System.out.println("IOException when trying to read resource key");
			return secret;
		}
	}
}
