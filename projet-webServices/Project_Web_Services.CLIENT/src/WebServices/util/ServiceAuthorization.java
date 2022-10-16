package WebServices.util;




import javax.ws.rs.client.Invocation.Builder;
import org.glassfish.jersey.internal.util.Base64;




public class ServiceAuthorization
{
	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

	public static Builder getWebTarget(Builder service)
	{
		String authTokken = Constants.username + ":" + Constants.password;
		
		String headerAuthorization = AUTHORIZATION_HEADER_PREFIX + Base64.encodeAsString(authTokken);
		
		return service.header(AUTHORIZATION_HEADER_KEY, headerAuthorization);
	}
}
