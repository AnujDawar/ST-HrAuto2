package st.ict.hrs.hrauto.educationfacility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import st.ict.hrs.hrauto.dao.HrAutoDB;
import st.ict.hrs.hrauto.entity.UserProfile;
import st.ict.hrs.hrauto.entity.UserService;
import st.ict.hrs.hrauto.utility.FileUtil;

@Service
public class EducationFacilityService 
{
	private static final Logger logger = LogManager.getLogger(EducationFacilityService.class);

	@Autowired
	private HrAutoDB dao;
	
	@Autowired
	private UserService userService;

	//	public boolean insertNewApprovalRequest(EducationFacilityRequestBean bean, String fileName, String contentType, byte[] file) throws SQLException
	public boolean insertNewApprovalRequest(EducationFacilityRequestBean bean, List<MultipartFile> files) throws SQLException
	{
		PreparedStatement requestStatement 		= 	null;
		PreparedStatement attachmentsStatement 	= 	null;
		PreparedStatement requestIdStatement 	= 	null;
		ResultSet		  requestIdResult		=	null;

		Connection conn 						= 	null;

		String newRequestId 					= 	null;
		boolean successStatus 					= 	false;

		try
		{
			conn = dao.getConnection();

			requestStatement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("EDUCATION_FACILITY_INSERT_REQUEST"));

			requestStatement.setString(1, bean.getEmployeeId());
			requestStatement.setString(2, bean.getMobile());
			requestStatement.setString(3, bean.getNameOfInstitution());
			requestStatement.setString(4, bean.getProgramTitle());
			requestStatement.setFloat(5, bean.getEstimatedDurationOfCourse());
			requestStatement.setString(6, bean.getDateOfJoiningOfCourse().getSqlFormat());
			requestStatement.setFloat(7, bean.getTotalCost());
			requestStatement.setFloat(8, bean.getCostOfProgram());
			requestStatement.setString(9, bean.getHrResponsible());
			requestStatement.setFloat(10, bean.getApprovedAmount());
			requestStatement.setString(11, bean.getHowProgrmContributes());
			requestStatement.setString(12, bean.isPartOfDevelopmentPlan() ? "Y" : "N");
			requestStatement.setString(13, bean.getAvailedPastCourseName());
			requestStatement.setString(14, bean.getAvailedPastCouseStartDate().getSqlFormat());
			requestStatement.setString(15, bean.getAvailedPastCouseEndDate().getSqlFormat());
			requestStatement.setString(16, bean.isAgree() ? "Y" : "N");

			int updates = requestStatement.executeUpdate();

			if(updates > 0)
			{
				//	get new requestId
				requestIdStatement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("GET_LAST_ID"));
				requestIdResult = requestIdStatement.executeQuery();

				if(requestIdResult.next())
				{
					newRequestId = requestIdResult.getString(1);

					if(Integer.parseInt(newRequestId) > 0)
					{
						//	insert attachments against this request

						for(MultipartFile file: files)
						{
							System.out.println("new Request id: " + newRequestId);

							attachmentsStatement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("EDUCATION_FACILITY_INSERT_NEW_REQUEST_ATTACHMENT"));
							attachmentsStatement.setString(1, newRequestId);
							attachmentsStatement.setString(2, file.getContentType());
							attachmentsStatement.setString(3, file.getOriginalFilename());

							attachmentsStatement.setBytes(4, FileUtil.compressBytes(file.getBytes()));

							int attachmentInserted = attachmentsStatement.executeUpdate();

							if(attachmentInserted > 0)
								successStatus = true;
							else
							{
								successStatus = false;
								break;
							}
						} // loop
					}
				}
			}

			conn.commit();

			System.out.println("committed");

			return successStatus;
		}
		catch(Exception e)
		{
			successStatus = false;

			conn.rollback();

			logger.error("educationfacility - error in inserting new request " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(requestStatement != null)
				requestStatement.close();

			if(attachmentsStatement != null)
				attachmentsStatement.close();

			if(requestIdStatement != null)
				requestIdStatement.close();

			if(requestIdResult != null)
				requestIdResult.close();

			if(conn != null)
				conn.close();
		}

		return successStatus;
	}

	public AttachmentModel getAttachmentById(String attachmentId) throws SQLException 
	{
		PreparedStatement getAttachmentStatement = null;
		Connection conn = null;
		ResultSet attachmentResultSet = null;

		AttachmentModel attachmentModel = new AttachmentModel();

		try
		{
			conn = dao.getConnection();

			getAttachmentStatement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("EDUCATION_FACILITY_GET_ATTACHMENT_BY_ID"));
			getAttachmentStatement.setString(1, attachmentId);

			attachmentResultSet = getAttachmentStatement.executeQuery();

			System.out.println("query executed");

			if(attachmentResultSet.next())
			{
				System.out.println("attachment found");

				attachmentModel.setAttachmentId(attachmentResultSet.getInt("ID"));
				attachmentModel.setContentType(attachmentResultSet.getString("CONTENT_TYPE"));
				attachmentModel.setRequestId(attachmentResultSet.getInt("HRAUTO_EDUCATION_FACILITY_REQUESTS__ID"));
				attachmentModel.setFileName(attachmentResultSet.getString("FILENAME"));
				attachmentModel.setFile(FileUtil.decompressBytes(attachmentResultSet.getBytes("FILE")));

				System.out.println("returning attachment");
				System.out.println(attachmentModel.toString());

				return attachmentModel;
			}
			else
			{
				System.out.println("not found");
			}
		}
		catch(Exception e)
		{
			logger.error("educationfacility - error fetching attachment with id " + attachmentId + ". " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(getAttachmentStatement != null)
				getAttachmentStatement.close();

			if(attachmentResultSet != null)
				attachmentResultSet.close();

			if(conn != null)
				conn.close();
		}

		return attachmentModel;
	}

	public EducationFacilityRequestBean getRequestDetailsByUserId(String userId) throws SQLException 
	{
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement statement = null;

		EducationFacilityRequestBean request = new EducationFacilityRequestBean();
		List<AttachmentModel> attachments = new LinkedList<>();

		UserProfile user = userService.getUserProfileById(userId);
		
		request.setEmployeeId(user.getId());
		request.setEmployeeName(user.getName());
		request.setDateOfJoining(user.getDateOfJoining());
		
		try
		{
			conn = dao.getConnection();
			statement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("EDUCATION_FACILITY_GET_REQUEST_DETAILS_BY_USER_ID"));
			statement.setString(1, userId);

			rs = statement.executeQuery();

			if(rs.next())
			{
				request.setRequestId(rs.getString("ID"));
				request.setEmployeeId(rs.getString("HRAUTO_USERS__ID"));
				request.setMobile(rs.getString("MOBILE"));
				request.setNameOfInstitution(rs.getString("NAME_OF_INSTITUTION"));
				request.setProgramTitle(rs.getString("PROGRAM_TITLE"));
				request.setEstimatedDurationOfCourse(rs.getFloat("ESTIMATED_DURATION_OF_COURSE"));
				request.setDateOfJoiningOfCourse(UtilJsonDate.getDateFromSql(rs.getDate("DATE_OF_JOINING_OF_COUSE")));
				request.setTotalCost(rs.getFloat("TOTAL_COST"));
				request.setCostOfProgram(rs.getFloat("COST_OF_PROGRAM"));
				request.setHrResponsible(rs.getString("HR_RESPONSIBLE"));
				request.setApprovedAmount(rs.getFloat("APPROVED_AMOUNT_HR"));
				request.setHowProgrmContributes(rs.getString("HOW_PROGRAM_CONTRIBUTES"));
				request.setPartOfDevelopmentPlan(rs.getString("PROGRAM_PART_OF_DEV_PLAN").equals("Y"));
				request.setAvailedPastCourseName(rs.getString("PREVIOUS_COURSE_NAME"));
				request.setAvailedPastCouseStartDate(UtilJsonDate.getDateFromSql(rs.getDate("PREVIOUS_COURSE_START_DATE")));
				request.setAvailedPastCouseEndDate(UtilJsonDate.getDateFromSql(rs.getDate("PREVIOUS_COURSE_END_DATE")));
				request.setAgree(rs.getString("AGREEMENT_CHECK").equals("Y"));
				request.setRequestStatus(rs.getString("REQUEST_STATUS"));
				request.setInsertionTime(rs.getString("INSERTION_TIME"));

				String attachmentIdString = rs.getString("ATTACHMENT_ID");

				System.out.println("+" + attachmentIdString + "+");

				//	split on empty string returns array with length 1
				if(!attachmentIdString.isEmpty())
				{
					String[] attachemntIds = attachmentIdString.split(",");
					String[] attachmentNames = rs.getString("FILENAME").split(",");

					for(int i = 0; i < attachemntIds.length; i++)
						attachments.add(new AttachmentModel(Integer.parseInt(attachemntIds[i]), attachmentNames[i]));
				}

				request.setAttachments(attachments);
				return request;
			}
		}
		catch (Exception e) 
		{
			logger.error("educationfacility - error fetching request details for userid " + userId + ". " + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(statement != null)
				statement.close();

			if(rs != null)
				rs.close();

			if(conn != null)
				conn.close();
		}

		return request;
	}

	public boolean updateApprovalRequest(EducationFacilityRequestBean request, List<MultipartFile> files) throws SQLException 
	{
		PreparedStatement attachmentStatement = null, requestStatement = null, deactivateAttachmentStatement = null;
		Connection conn = null;

		try
		{
			conn = dao.getConnection();

			//	update existing request
			requestStatement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("EDUCATION_FACILITY_UPDATE_REQUEST_DETAILS"));

			requestStatement.setString(1, request.getMobile());
			requestStatement.setString(2, request.getNameOfInstitution());
			requestStatement.setString(3, request.getProgramTitle());
			requestStatement.setFloat(4, request.getEstimatedDurationOfCourse());
			requestStatement.setString(5, request.getDateOfJoiningOfCourse().getSqlFormat());
			requestStatement.setFloat(6, request.getTotalCost());
			requestStatement.setFloat(7, request.getCostOfProgram());
			requestStatement.setString(8, request.getHrResponsible());
			requestStatement.setFloat(9, request.getApprovedAmount());
			requestStatement.setString(10, request.getHowProgrmContributes());
			requestStatement.setString(11, request.isPartOfDevelopmentPlan() ? "Y" : "N");
			requestStatement.setString(12, request.getAvailedPastCourseName());
			requestStatement.setString(13, request.getAvailedPastCouseStartDate().getSqlFormat());
			requestStatement.setString(14, request.getAvailedPastCouseEndDate().getSqlFormat());
			requestStatement.setString(15, request.isAgree() ? "Y" : "N");
			requestStatement.setString(16, "DRA");
			requestStatement.setString(17, request.getRequestId());

			int updates = requestStatement.executeUpdate();

			if(updates <= 0)
			{
				System.out.println("error in updating request");
				conn.rollback();
				return false;
			}

			attachmentStatement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("EDUCATION_FACILITY_INSERT_NEW_REQUEST_ATTACHMENT"));

			//	add new attachments
			for(MultipartFile file: files)
			{
				attachmentStatement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("EDUCATION_FACILITY_INSERT_NEW_REQUEST_ATTACHMENT"));
				attachmentStatement.setString(1, request.getRequestId());
				attachmentStatement.setString(2, file.getContentType());
				attachmentStatement.setString(3, file.getOriginalFilename());
				attachmentStatement.setBytes(4, FileUtil.compressBytes(file.getBytes()));

				int attachmentInserted = attachmentStatement.executeUpdate();

				if(attachmentInserted <= 0)
				{
					System.out.println("error in adding attachments");
					conn.rollback();
					return false;
				}
			}

			StringBuffer query = new StringBuffer(HrAutoDB.dbSQLMap.get("EDUCATION_FACILITY_DEACTIVATE_ATTACHMENT"));

			if(!request.getAttachmentIdsToRemove().isEmpty())
			{
				deactivateAttachmentStatement = conn.prepareStatement(query.toString().replaceFirst("REPLACE_WITH_QUESTION", request.getAttachmentIdsToRemove()));

				int update = deactivateAttachmentStatement.executeUpdate();

				if(update <= 0)
				{
					System.out.println("error in removing attachemnts");
					conn.rollback();
					return false;
				}
			}

			conn.commit();
			System.out.println("good");
		}
		catch (Exception e) 
		{
			conn.rollback();
			e.printStackTrace();
		}
		finally
		{
			if(attachmentStatement != null)
				attachmentStatement.close();

			if(requestStatement != null)
				requestStatement.close();

			if(deactivateAttachmentStatement != null)
				deactivateAttachmentStatement.close();

			if(conn != null)
				conn.close();
		}

		return true;
	}
}
