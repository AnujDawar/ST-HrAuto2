package st.ict.hrs.hrauto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@SpringBootApplication
public class HrautoApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(HrautoApplication.class, args);
	}
	
	@Bean
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(
	            mapper);
	    return converter;
	}
}
