package st.ict.hrs.hrauto.educationfacility;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class EducationFacilityRequestBean 
{
	private		String				requestId;
	private     String 				employeeName;
	private     String 				employeeId;
	private     UtilJsonDate 		dateOfJoining;
	private     String 				mobile;
	private     String 				department;
	private     String 				designation;
	private     String 				nameOfInstitution;
	private     String 				programTitle;
	private     float 				estimatedDurationOfCourse;
	private     UtilJsonDate 		dateOfJoiningOfCourse;
	private     float 				totalCost;
	private     float 				costOfProgram;
	private     String 				hrResponsible;
	private     float 				approvedAmount;
	private     String 				howProgrmContributes;
	private		String				requestStatus;
	private		String				insertionTime;

	@JsonProperty("isPartOfDevelopmentPlan")
	private     boolean 			isPartOfDevelopmentPlan;
	private     String 				availedPastCourseName;
	private     UtilJsonDate 		availedPastCouseStartDate;
	private     UtilJsonDate 		availedPastCouseEndDate;

	@JsonProperty("isAgree")
	private     boolean 			isAgree;

	List<AttachmentModel> attachments;
	String attachmentIdsToRemove;

	public EducationFacilityRequestBean() 
	{
		//		employeeName = "Anuj Dawar";
		//		employeeId = "278782";
		//		dateOfJoining = new UtilJsonDate(1, 6, 2017);
		department = "DIT";
		designation = "Application Software Developer";
		attachments = new LinkedList<>();
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public UtilJsonDate getDateOfJoining() {
		return dateOfJoining;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getNameOfInstitution() {
		return nameOfInstitution;
	}

	public void setNameOfInstitution(String nameOfInstitution) {
		this.nameOfInstitution = nameOfInstitution;
	}

	public String getProgramTitle() {
		return programTitle;
	}

	public void setProgramTitle(String programTitle) {
		this.programTitle = programTitle;
	}

	public float getEstimatedDurationOfCourse() {
		return estimatedDurationOfCourse;
	}

	public void setEstimatedDurationOfCourse(float estimatedDurationOfCourse) {
		this.estimatedDurationOfCourse = estimatedDurationOfCourse;
	}

	public float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(float totalCost) {
		this.totalCost = totalCost;
	}

	public float getCostOfProgram() {
		return costOfProgram;
	}

	public void setCostOfProgram(float costOfProgram) {
		this.costOfProgram = costOfProgram;
	}

	public String getHrResponsible() {
		return hrResponsible;
	}

	public void setHrResponsible(String hrResponsible) {
		this.hrResponsible = hrResponsible;
	}

	public float getApprovedAmount() {
		return approvedAmount;
	}

	public void setApprovedAmount(float approvedAmount) {
		this.approvedAmount = approvedAmount;
	}

	public String getHowProgrmContributes() {
		return howProgrmContributes;
	}

	public void setHowProgrmContributes(String howProgrmContributes) {
		this.howProgrmContributes = howProgrmContributes;
	}

	public boolean isPartOfDevelopmentPlan() {
		return isPartOfDevelopmentPlan;
	}

	public void setPartOfDevelopmentPlan(boolean isPartOfDevelopmentPlan) {
		this.isPartOfDevelopmentPlan = isPartOfDevelopmentPlan;
	}

	public String getAvailedPastCourseName() {
		return availedPastCourseName;
	}

	public void setAvailedPastCourseName(String availedPastCourseName) {
		this.availedPastCourseName = availedPastCourseName;
	}

	public boolean isAgree() {
		return isAgree;
	}

	public void setAgree(boolean isAgree) {
		this.isAgree = isAgree;
	}

	public UtilJsonDate getDateOfJoiningOfCourse() {
		return dateOfJoiningOfCourse;
	}

	public void setDateOfJoiningOfCourse(UtilJsonDate dateOfJoiningOfCourse) {
		this.dateOfJoiningOfCourse = dateOfJoiningOfCourse;
	}

	public UtilJsonDate getAvailedPastCouseStartDate() {
		return availedPastCouseStartDate;
	}

	public void setAvailedPastCouseStartDate(UtilJsonDate availedPastCouseStartDate) {
		this.availedPastCouseStartDate = availedPastCouseStartDate;
	}

	public UtilJsonDate getAvailedPastCouseEndDate() {
		return availedPastCouseEndDate;
	}

	public void setAvailedPastCouseEndDate(UtilJsonDate availedPastCouseEndDate) {
		this.availedPastCouseEndDate = availedPastCouseEndDate;
	}

	public void setDateOfJoining(UtilJsonDate dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public List<AttachmentModel> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<AttachmentModel> attachments) {
		this.attachments = attachments;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getRequestStatus() {
		return requestStatus;
	}

	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	public String getInsertionTime() {
		return insertionTime;
	}

	public void setInsertionTime(String insertionTime) {
		this.insertionTime = insertionTime;
	}

	public String getAttachmentIdsToRemove() {
		return attachmentIdsToRemove;
	}

	public void setAttachmentIdsToRemove(String attachmentIdsToRemove) {
		this.attachmentIdsToRemove = attachmentIdsToRemove;
	}

	@Override
	public String toString() {
		return "EducationFacilityRequestBean [requestId=" + requestId + ", employeeName=" + employeeName
				+ ", employeeId=" + employeeId + ", dateOfJoining=" + dateOfJoining + ", mobile=" + mobile
				+ ", department=" + department + ", designation=" + designation + ", nameOfInstitution="
				+ nameOfInstitution + ", programTitle=" + programTitle + ", estimatedDurationOfCourse="
				+ estimatedDurationOfCourse + ", dateOfJoiningOfCourse=" + dateOfJoiningOfCourse + ", totalCost="
				+ totalCost + ", costOfProgram=" + costOfProgram + ", hrResponsible=" + hrResponsible
				+ ", approvedAmount=" + approvedAmount + ", howProgrmContributes=" + howProgrmContributes
				+ ", requestStatus=" + requestStatus + ", insertionTime=" + insertionTime + ", isPartOfDevelopmentPlan="
				+ isPartOfDevelopmentPlan + ", availedPastCourseName=" + availedPastCourseName
				+ ", availedPastCouseStartDate=" + availedPastCouseStartDate + ", availedPastCouseEndDate="
				+ availedPastCouseEndDate + ", isAgree=" + isAgree + ", attachments=" + attachments
				+ ", attachmentIdsToRemove=" + attachmentIdsToRemove + "]";
	}
}
