package st.ict.hrs.hrauto.entity;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.core.env.Environment;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:4200")
public class CustomLdapLoginUserDetails implements LdapUserDetails  {
	
	private static final long serialVersionUID = 1L;
	
	//private Logger logger = LoggerFactory.getLogger(this.getClass());
	private static Logger logger = LogManager.getLogger(CustomLdapLoginUserDetails.class.getName());

	private LdapUserDetails details;
	private Environment env;
	private DirContextOperations ctx;

	public CustomLdapLoginUserDetails(LdapUserDetails details, Environment env,DirContextOperations ctx) {
	    this.details = details;
	    this.env = env; 
	    this.ctx = ctx;
	}

	public boolean isEnabled() {
	    return details.isEnabled() && getUsername().equals(env.getRequiredProperty("ldap.username"));
	}

	public String getDn() {
	    return details.getDn();
	}


	public String getEmployeeNumber() {
		
		Attributes attr = ctx.getAttributes();
		String employeeNumber = "";
		try {
			employeeNumber= attr.get("employeenumber").get().toString();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			
			logger.error(e.getMessage());
			
		}             		
	    return employeeNumber;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
	    return details.getAuthorities();
	}

	public String getPassword() {	
	    return details.getPassword();
	    
	}

	public String getUsername() {
		logger.info("UserName :",details.getUsername());
		logger.info("Dn :",details.getDn());
		logger.info("Authorities :",details.getAuthorities());		
	    return details.getUsername();
	}

	public boolean isAccountNonExpired() {
	    return details.isAccountNonExpired();
	}

	public boolean isAccountNonLocked() {
	    return details.isAccountNonLocked();
	}

	public boolean isCredentialsNonExpired() {
	    return details.isCredentialsNonExpired();
	}

	@Override
	public void eraseCredentials() {
		// TODO Auto-generated method stub
		
	}

	//-- GrantedAuthority Method

	private Map<String, List<String>> attributes;
	public List<String> getAttributeValues(String name) {
		List<String> result = null;
		if (attributes != null) {
			result = attributes.get(name);
		}
		if (result == null) {
			result = Collections.emptyList();
		}
		return result;
	}


}
