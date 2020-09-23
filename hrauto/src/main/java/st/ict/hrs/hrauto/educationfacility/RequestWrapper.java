package st.ict.hrs.hrauto.educationfacility;

public class RequestWrapper 
{
	private EducationFacilityRequestBean model;
//	private MultipartFile attachment;

	public RequestWrapper()
	{}

	public RequestWrapper(EducationFacilityRequestBean model) { //, MultipartFile attachment) {
		super();
		this.model = model;
//		this.attachment = attachment;
	}

	public EducationFacilityRequestBean getModel() {
		return model;
	}

	public void setModel(EducationFacilityRequestBean model) {
		this.model = model;
	}

//	public MultipartFile getAttachment() {
//		return attachment;
//	}
//
//	public void setAttachment(MultipartFile attachment) {
//		this.attachment = attachment;
//	}

	@Override
	public String toString() {
		return "RequestWrapper [model=" + model; // + ", attachment=" + attachment + "]";
	}
}
