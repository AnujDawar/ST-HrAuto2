package st.ict.hrs.hrauto.educationfacility;

import java.util.Arrays;

import org.springframework.stereotype.Component;

@Component
public class AttachmentModel 
{
	private String fileName;
	private String contentType;
	private byte[] file;
	private int attachmentId;
	private int requestId;

	public AttachmentModel() {}

	public AttachmentModel(int attachmentId, String fileName)
	{
		this.attachmentId = attachmentId;
		this.fileName = fileName;
	}
	
	public AttachmentModel(String fileName, String contentType, byte[] file, int attachmentId, int requestId) 
	{
		super();
		this.fileName = fileName;
		this.contentType = contentType;
		this.file = file;
		this.attachmentId = attachmentId;
		this.requestId = requestId;
	}

	public String getFileName() 
	{
		return fileName;
	}

	public void setFileName(String fileName) 
	{
		this.fileName = fileName;
	}

	public String getContentType() 
	{
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	public int getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(int attachmentId) {
		this.attachmentId = attachmentId;
	}

	public int getRequestId() {
		return requestId;
	}

	public void setRequestId(int requestId) {
		this.requestId = requestId;
	}

	@Override
	public String toString() {
		return "AttachmentModel [fileName=" + fileName + ", contentType=" + contentType + ", file="
				+ Arrays.toString(file) + ", attachmentId=" + attachmentId + ", requestId=" + requestId + "]";
	}
}
