package st.ict.hrs.hrauto.config;

import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetails;
import org.springframework.security.ldap.userdetails.LdapUserDetailsMapper;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.web.bind.annotation.CrossOrigin;

import st.ict.hrs.hrauto.entity.CustomLdapLoginUserDetails;
import st.ict.hrs.hrauto.entity.ResponseError;
import st.ict.hrs.hrauto.security.JwtAuthenticationEntryPoint;

@CrossOrigin(origins = "http://localhost:4200")
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		securedEnabled = true,
		jsr250Enabled = true,
		prePostEnabled = true
		)
@PropertySource("classpath:ldap.properties")
public class LdapAuthenticationConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env; 

	@Value("${ldap.userSerchBase}")
	private String userSerchBase;

	@Value("${ldap.userSearchFilter}")
	private String userSearchFilter;

	@Value("${ldap.groupSearchBase}")
	private String groupSearchBase;

	@Value("${ldap.groupSearchFilter}")
	private String groupSearchFilter;

	@Value("${ldap.url}")
	private String url;

	@Value("${ldap.port}")
	private String port; 


	@Value("${ldap.managerDn}")
	private String managerDn; 

	@Value("${ldap.managerPasswd}")
	private String managerPasswd; 



	// set up logger for diagnostics

	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static Logger logger = LogManager.getLogger(LdapAuthenticationConfiguration.class.getName());

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		logger.info(">>ldap.userSerchBase :"+ userSerchBase);	
		logger.debug(">>ldap.userSearchFilter :"+userSearchFilter);
		logger.debug(">>jldap.groupSearchBase :"+groupSearchBase);
		logger.debug(">>ldap.groupSearchFilter :"+groupSearchFilter);				
		logger.debug(">>ldap.url :"+url);				
		logger.debug(">>ldap.port :"+port);

		auth     
		.ldapAuthentication()
		.userDetailsContextMapper(userDetailsContextMapper())
		.userSearchFilter(userSearchFilter)
		.userSearchBase(userSerchBase)
		.groupSearchBase(groupSearchBase)
		.groupSearchFilter(groupSearchFilter)
		.contextSource()
		.url(url)
		.port(Integer.parseInt(port))
		.managerDn(managerDn)
		.managerPassword(managerPasswd)
		;

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {


		/*	
		// http.addFilterBefore(new CorsFilter(), ChannelProcessingFilter.class);
		http.authorizeRequests().antMatchers("/eNomineeAPI/**").authenticated()
        .and()
        .csrf().disable()
        //.httpBasic()
        //.and()
        .logout().logoutUrl("/eNomineeAPI/logout")
        .permitAll();

		http.httpBasic().authenticationEntryPoint(authenticationEntryPoint());
        http.exceptionHandling().authenticationEntryPoint(authenticationEntryPoint());

		 */
		http
		.csrf()
		.disable()
		.cors()
		.disable()
		.exceptionHandling()
		.authenticationEntryPoint(unauthorizedHandler)
		.and()
		.authorizeRequests()
		.antMatchers("/hrauto/**")
		.permitAll();
		//.anyRequest()
		//.authenticated();
		//.and().logout().logoutUrl("/eNomineeAPI/logout")
		//.permitAll();



	}

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;


	@Bean(BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}


	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
		return (request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}

	@Bean
	public UserDetailsContextMapper userDetailsContextMapper() 
	{
		return new LdapUserDetailsMapper() {
			@Override
			public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
				UserDetails details = super.mapUserFromContext(ctx, username, authorities);            
				return new CustomLdapLoginUserDetails((LdapUserDetails)details,env,ctx); 
			}
		};
	}

	@Bean
	public ResponseError responseError() {
		return new ResponseError();
	}
}