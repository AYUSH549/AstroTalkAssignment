package com.developer.ayush.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.developer.ayush.constants.TableList;


@Repository
public class CommonDAO {

	private static final Logger logger = LogManager.getLogger(CommonDAO.class.getName());

	@Qualifier("datasource")
	@Autowired
	private javax.sql.DataSource dataSource;

	public Long getNextSequenceID(Connection con, TableList.TableWrapper tableName) {
		Connection conn = con;
		Long id = 0L;
		try {

			if (conn == null) {
				conn = dataSource.getConnection();
			}

			synchronized (tableName) {
				String sql = "select nextID from raSequencer where tableName = ?";
				logger.info(sql + ";" + tableName.getTableName());
				PreparedStatement stmt = conn.prepareStatement(sql);
				stmt.setString(1, tableName.getTableName());
				ResultSet rs = stmt.executeQuery();
				if (rs.next()) {
					id = rs.getLong(1);
				}
				rs.close();
				stmt.close();

				sql = "update raSequencer set nextID = ?, modificationTime = ? where tableName = ?";
				logger.info(sql + ";" + tableName.getTableName());
				stmt = conn.prepareStatement(sql);
				stmt.setLong(1, (id + 1));
				stmt.setLong(2, System.currentTimeMillis());
				stmt.setString(3, tableName.getTableName());

				stmt.executeUpdate();
				stmt.close();
			}
		} catch (Exception ex) {
			logger.info(ex);

		} finally {
			if (con == null && conn != null) {
				try {
					conn.close();
					logger.info("Connection closed Finally");

				} catch (Exception e) {
					logger.info(e);
				}
			}
		}

		return id;
	}

	public Long checkIfExist(Connection con, String email) {
		Connection conn = con;
		Long lid = null;
		Long errNum = null;
		try {
			if (conn == null) {
				conn = dataSource.getConnection();
			}

			ResultSet rs = null;
			PreparedStatement stmt = null;
			String sql = "select LID from USER where email = ?";
			logger.info(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			rs = stmt.executeQuery();
			if (rs.next()) {
				lid = rs.getLong(1);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			return errNum;

		}
		
		finally {
			if (con == null && conn != null) {
				try {
					conn.close();
					logger.info("Connection closed Finally");

				} catch (Exception e) {
					logger.info(e);
				}
			}
		}

		return lid;

	}
	
	
	public Long getLoginId(Connection con, String name) {
		Connection conn = con;
		Long lid = null;
		Long errNum = null;
		try {
			if (conn == null) {
				conn = dataSource.getConnection();
			}

			ResultSet rs = null;
			PreparedStatement stmt = null;
			String sql = "select LID from USER where name = ?";
			logger.info(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);
			rs = stmt.executeQuery();
			if (rs.next()) {
				lid = rs.getLong(1);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			return errNum;

		}
		
		finally {
			if (con == null && conn != null) {
				try {
					conn.close();
					logger.info("Connection closed Finally");

				} catch (Exception e) {
					logger.info(e);
				}
			}
		}

		return lid;

	}
	
	public String getName(Connection con, Long Lid) {
		Connection conn = con;
		String friendName = "";
		Long errNum = null;
		try {
			if (conn == null) {
				conn = dataSource.getConnection();
			}

			ResultSet rs = null;
			PreparedStatement stmt = null;
			String sql = "select NAME from USER where LID = ?";
			logger.info(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, Lid);
			rs = stmt.executeQuery();
			if (rs.next()) {
				friendName = rs.getString(1);
			}
			rs.close();
			stmt.close();

		} catch (Exception e) {
			logger.info(e.getMessage(), e);
			

		}
		
		finally {
			if (con == null && conn != null) {
				try {
					conn.close();
					logger.info("Connection closed Finally");

				} catch (Exception e) {
					logger.info(e);
				}
			}
		}

		return friendName;

	}



}