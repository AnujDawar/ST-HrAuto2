package st.ict.hrs.hrauto.security;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import st.ict.hrs.hrauto.entity.CustomLdapLoginUserDetails;
import st.ict.hrs.hrauto.entity.ResponseError;
import st.ict.hrs.hrauto.entity.UserProfile;
import st.ict.hrs.hrauto.entity.UserService;
import st.ict.hrs.hrauto.payload.LoginUser;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/hrauto")
public class LdapAuthenticattionController 
{
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	private ResponseError responseError;

	@Autowired 
	private UserService userService;

	private static Logger logger = LogManager.getLogger(LdapAuthenticattionController.class.getName());

	@PostMapping("/")
	public UserProfile login(@RequestBody LoginUser loginRequest) throws SQLException 
	{
		System.out.println(loginRequest.toString());
		logger.info("In Login.....");
		if(loginRequest.getUsername().isEmpty() || loginRequest.getPassword().isEmpty()) 
		{   		
			responseError.updateResponseError(401,"Unauthorized","NOT OK");	
			UserProfile profile = new UserProfile();
			profile.setResponseError(responseError);
			return profile;
		}    	

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUsername(),
						loginRequest.getPassword()
						)
				);

		String jwt = tokenProvider.generateToken(authentication);       

		CustomLdapLoginUserDetails currentUser = (CustomLdapLoginUserDetails)authentication.getPrincipal();
		logger.info("EmployeeId of current Login User : " + currentUser.getEmployeeNumber());

		UserProfile profile = userService.getUserProfileById(currentUser.getEmployeeNumber());
		//		UserProfile profile = new UserProfile();
		//		profile.setCostcenter("costcenter");
		//		profile.setEmail("email");
		//		profile.setFirstName("Anuj");
		//		profile.setLastName("Dawar");
		//		profile.setName("Anuj Dawar");
		//		profile.setSupervisorId("supervisorId");
		//		profile.setWorkLocationId("workLocationId");
		//		profile.setId("278782");

		//		if(profile==null)
		//		{
		//			responseError.updateResponseError(401,"Unauthorized","NOT OK");	
		//			profile = new UserProfile();
		//			profile.setResponseError(responseError);
		//			return profile;
		//		}
		//		else
		//		{				
		//			List<ENomineeModules> eNomineeModules = enomineeservice.getModulesForUser(profile.getId());
		//			if(eNomineeModules != null && eNomineeModules.size()>0)
		//			{
		responseError.updateResponseError(200,"SUCCESS","OK");	
		profile.setResponseError(responseError);
		profile.setAccessToken(jwt);
		return profile;
		//			}
		//			else
		//			{
		//				responseError.updateResponseError(401,"Unauthorized","NOT OK");	
		//				profile = new UserProfile();
		//				profile.setResponseError(responseError);
		//				return profile;
		//			}			
		//	}		

	}


	@GetMapping("/access-denied")
	public UserProfile showAccess_Denied() {


		logger.info("Invalid Credentials: access-denied");
		responseError.updateResponseError(401,"Invalid Credentials","NOT OK");	
		UserProfile profile = new UserProfile();
		profile.setResponseError(responseError);
		return profile;
	}

}
