package ca.uhn.fhir.jpa.starter;

import ca.uhn.fhir.jpa.starter.auth.AuthUser;
import ca.uhn.fhir.jpa.starter.interceptors.*;
import ca.uhn.fhir.rest.server.interceptor.ResponseHighlighterInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import javax.servlet.ServletException;

@Import(AppProperties.class)
public class JpaRestfulServer extends BaseJpaRestfulServer {

  @Autowired
  AppProperties appProperties;

  private static final long serialVersionUID = 1L;

  public JpaRestfulServer() {
    super();
  }

  @Override
  protected void initialize() throws ServletException {
    super.initialize();

    // Add your own customization here++
//	  AuthUser user = new AuthUser();
//
//    // Format the responses in nice HTML
//	  registerInterceptor(new ResponseHighlighterInterceptor());
//    System.out.println("111");
//	  registerInterceptor(new JWTInterceptor(user));
//    System.out.println("777");
//	  registerInterceptor(new AuthHandleInterceptor(user));

//	  registerInterceptor(new TestAuthNextInterceptor());
//    registerInterceptor(new AdminAuthorizationInterceptor());
//    registerInterceptor(new RequestExceptionInterceptor());
//	 	registerInterceptor(new PatientAuthorizationInterceptor());
  }

}
