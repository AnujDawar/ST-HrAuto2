package st.ict.hrs.hrauto.educationfacility;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/hrauto")
public class EducationFacilityController 
{
	@Autowired
	EducationFacilityService service;
	
	@PostMapping("/educationFacility/submit")
	private String submitNewRequest(@RequestParam("body") String body, 
			@RequestParam(value="attachment1", required=false) MultipartFile file1, 
			@RequestParam(value="attachment2", required=false) MultipartFile file2,
			@RequestParam(value="attachment3", required=false) MultipartFile file3,
			@RequestParam(value="attachment4", required=false) MultipartFile file4,
			@RequestParam(value="attachment5", required=false) MultipartFile file5) throws IOException
	{
		Gson g = new Gson(); 
		EducationFacilityRequestBean request = g.fromJson(body, EducationFacilityRequestBean.class);

		System.out.println(request.toString());

		List<MultipartFile> files = new LinkedList<>();

		if(file1 != null)
			files.add(file1);

		if(file2 != null)
			files.add(file2);

		if(file3 != null)
			files.add(file3);

		if(file4 != null)
			files.add(file4);

		if(file5 != null)
			files.add(file5);
		try 
		{
			if(request.getRequestId().isEmpty())
			{
				System.out.println("no req id");
				return String.valueOf(service.insertNewApprovalRequest(request, files));
			}
			else
			{
				System.out.println("req id exists");
				return String.valueOf(service.updateApprovalRequest(request, files));
			}			
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
			return "ERROR";
		}
	}

	@GetMapping("/educationFacility/checkStatus")
	private ResponseEntity<Object> checkStatus()
	{
		System.out.println("App is up!");
		return new ResponseEntity<Object>(HttpStatus.OK);
	}

	@GetMapping("/educationFacility/getRequestDetails/{userId}")
	public EducationFacilityRequestBean getRequestDetails(@PathVariable("userId") String userId)
	{
		try 
		{
			EducationFacilityRequestBean request = service.getRequestDetailsByUserId(userId);

			System.out.println(request);

			return request;
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}

		return new EducationFacilityRequestBean();
	}

	@GetMapping(path = {"/educationFacility/getFile/{attachmentId}" })
	public ResponseEntity<byte[]> getAttachment(@PathVariable("attachmentId") String attachmentId)
	{
		System.out.println("attachemntId: " + attachmentId);
		try
		{
			AttachmentModel model = service.getAttachmentById(attachmentId);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + model.getFileName() + "\"")
					.body(model.getFile());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return (ResponseEntity<byte[]>) ResponseEntity.noContent();
		}
	}
}
