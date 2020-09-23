package st.ict.hrs.hrauto.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:sql.properties")
public class HrAutoDB 
{
	private static final Logger logger = LogManager.getLogger(HrAutoDB.class);

	@Autowired
	JdbcTemplate jdbcTemplate;
	DataSource datasource;

	public static Map<String, String> dbSQLMap;

	@Autowired
	public HrAutoDB(JdbcTemplate jdbcTemplate)
	{
		this.jdbcTemplate = jdbcTemplate;
		this.datasource = this.jdbcTemplate.getDataSource();
		dbSQLMap = new HashMap<String, String>();

		loadSqls();
	}

	private void loadSqls() 
	{
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		Connection connection = null;

		try
		{
			connection = this.datasource.getConnection();

			preparedStatement = connection.prepareStatement("select * from hrauto_sql_config where activity_status__code = '30'");
			resultSet = executeQuery(connection, preparedStatement);

			while(resultSet.next())
			{
				String key = resultSet.getString("sql_key");
				String value = resultSet.getString("sql_value");

				logger.info("RefreshApplication-setSQLMapDetails : " + key + " : " + value);
				System.out.println("RefreshApplication-setSQLMapDetails : " + key + " : " + value);

				dbSQLMap.put(key, value);
			}
		}
		catch(SQLException e)
		{
			logger.error(e);
		}
		finally 
		{
			try
			{
				if(resultSet != null)
					resultSet.close();

				if(preparedStatement != null)
					preparedStatement.close();

				if(connection != null)
					connection.close();
			}
			catch(Exception e)
			{
				logger.error(e);	
			}
		}
	}

	public ResultSet executeQuery(Connection connection, PreparedStatement statement, String ... params)
	{
		ResultSet rs = null;

		try 
		{			
			for(int i = 0; i < params.length; i++)
				statement.setString(i + 1, params[i]);

			rs = statement.executeQuery();
		} 
		catch (SQLException e) 
		{
			logger.error(e);
		}

		return rs;
	}
	
	public Connection getConnection() throws SQLException
	{
		Connection conn = this.datasource.getConnection();
		conn.setAutoCommit(false);
		return conn;
	}
}
