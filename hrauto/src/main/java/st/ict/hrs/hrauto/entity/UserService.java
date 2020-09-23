package st.ict.hrs.hrauto.entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import st.ict.hrs.hrauto.dao.HrAutoDB;
import st.ict.hrs.hrauto.educationfacility.UtilJsonDate;

@Service
public class UserService 
{
	@Autowired 
	private HrAutoDB dao;

	public UserProfile getUserProfileById(String id) throws SQLException
	{
		UserProfile profile = new UserProfile();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement statement = null;

		try
		{
			conn = dao.getConnection();
			statement = conn.prepareStatement(HrAutoDB.dbSQLMap.get("HRAUTO_GET_USER_PROFILE_BY_ID"));
			statement.setString(1, id);

			rs = statement.executeQuery();

			if(rs.next())
			{
				profile.setId(rs.getString("ID"));
				profile.setCostcenter(rs.getString("HRAUTO_COST_CENTER__ID"));
				profile.setName(rs.getString("NAME"));
				profile.setFirstName(rs.getString("FIRST_NAME"));
				profile.setLastName(rs.getString("LAST_NAME"));
				profile.setEmail(rs.getString("EMAIL"));
				profile.setHomeLocationId(rs.getString("HRAUTO_HOME_LOCATION__ID"));
				profile.setWorkLocationId(rs.getString("HRAUTO_WORK_LOCATION__ID"));
				profile.setDateOfJoining(UtilJsonDate.getDateFromSql(rs.getDate("DATE_OF_JOINING")));
			}
		}
		catch(Exception e)
		{
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

		return profile;
	}
}
