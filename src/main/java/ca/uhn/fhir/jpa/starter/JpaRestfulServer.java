package ca.uhn.fhir.jpa.starter;

import ca.uhn.fhir.jpa.starter.interceptors.*;
import ca.uhn.fhir.jpa.starter.interceptors.AdminAuthorizationInterceptor;
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

    // Format the responses in nice HTML
	  registerInterceptor(new ResponseHighlighterInterceptor());

//	  registerInterceptor(new TestAuthNextInterceptor());
//	  registerInterceptor(new TestAuthInterceptor());

//    registerInterceptor(new AdminAuthorizationInterceptor());
//    registerInterceptor(new RequestExceptionInterceptor());
	 	registerInterceptor(new PatientAuthorizationInterceptor());
  }

}
