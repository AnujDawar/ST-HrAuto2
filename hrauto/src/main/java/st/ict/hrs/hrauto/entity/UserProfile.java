package st.ict.hrs.hrauto.entity;


import org.springframework.beans.factory.annotation.Autowired;

import st.ict.hrs.hrauto.educationfacility.UtilJsonDate;


public class UserProfile 
{
	private String id;
	private String name;
	private String firstName;
	private String lastName;
	private String email;
	private String costcenter;
	private String workLocationId;
	private String homeLocationId;
	private String supervisorId;
	private UtilJsonDate dateOfJoining;
	private String accessToken;

	@Autowired
	private ResponseError responseError;

	public UserProfile() {		

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCostcenter() {
		return costcenter;
	}

	public void setCostcenter(String costcenter) {
		this.costcenter = costcenter;
	}

	public String getWorkLocationId() {
		return workLocationId;
	}


	public void setWorkLocationId(String workLocationId) {
		this.workLocationId = workLocationId;
	}


	public String getHomeLocationId() {
		return homeLocationId;
	}


	public void setHomeLocationId(String homeLocationId) {
		this.homeLocationId = homeLocationId;
	}

	public String getSupervisorId() {
		return supervisorId;
	}


	public void setSupervisorId(String supervisorId) {
		this.supervisorId = supervisorId;
	}


	public ResponseError getResponseError() {
		return responseError;
	}


	public void setResponseError(ResponseError responseError) {
		this.responseError = responseError;
	}


	public String getAccessToken() {
		return accessToken;
	}


	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public UtilJsonDate getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(UtilJsonDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	@Override
	public String toString() {
		return "UserProfile [id=" + id + ", name=" + name + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", email=" + email + ", costcenter=" + costcenter + ", workLocationId=" + workLocationId
				+ ", homeLocationId=" + homeLocationId + ", supervisorId=" + supervisorId + ", accessToken="
				+ accessToken + ", responseError=" + responseError + "]";
	}

}
