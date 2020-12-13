package com.developer.ayush.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.developer.ayush.constants.Errors;
import com.developer.ayush.entities.RelationDTO;
import com.developer.ayush.entities.TestDTO;


@Repository
public class RegisterDAO {
	
private static final Logger logger = LogManager.getLogger(RegisterDAO.class.getName());
	
	@Qualifier("datasource")
	@Autowired
	private javax.sql.DataSource dataSource;
	
	public int addProfile(TestDTO testdto, Connection con) {
		Connection conn = con;
		int errNum = Errors.UNKNOWN;

		try {
			if (conn == null) {
				conn = dataSource.getConnection();
			}

			PreparedStatement stmt = null;
			String sql = "insert into USER (LID, NAME, EMAIL, PASSWORD) values (?,?,?,?)";
			logger.info(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, testdto.getLid());
			stmt.setString(2, testdto.getName());
			stmt.setString(3, testdto.getEmail());
			stmt.setString(4, testdto.getPassword());

			try {
				stmt.executeUpdate();
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}

			stmt.close();
			errNum = Errors.OK;

		} catch (Exception ex) {
			logger.info(ex);
			errNum = Errors.UNKNOWN;
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

		return errNum;
	}
	
	
	public int addFriend(RelationDTO relationdto, Connection con) {
		Connection conn = con;
		int errNum = Errors.UNKNOWN;

		try {
			if (conn == null) {
				conn = dataSource.getConnection();
			}

			PreparedStatement stmt = null;
			String sql = "insert into RELATION (PARENT_ID, LID, RELATION) values (?,?,?)";
			logger.info(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, relationdto.getParentId());
			stmt.setLong(2, relationdto.getLid());
			stmt.setString(3, relationdto.getRelation());

			try {
				stmt.executeUpdate();
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}

			stmt.close();
			errNum = Errors.OK;

		} catch (Exception ex) {
			logger.info(ex);
			errNum = Errors.UNKNOWN;
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

		return errNum;
	}
	
	
	public ArrayList<Long> viewFriend(Long parentLid, Connection con) {
		Connection conn = con;
		int errNum = Errors.UNKNOWN;
		ArrayList<Long> getfriendId =  new ArrayList<Long>();

		try {
			if (conn == null) {
				conn = dataSource.getConnection();
			}

			PreparedStatement stmt = null;
			String sql = "select LID from RELATION where PARENT_ID = ?";
			logger.info(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, parentLid);
			ResultSet rs = stmt.executeQuery();
		
			while(rs.next())
			{
				getfriendId.add(rs.getLong(1));
			}
			
			rs.close();
			stmt.close();

		} catch (Exception ex) {
			logger.info(ex);
			errNum = Errors.UNKNOWN;
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

		return getfriendId;
	}
	
	public int removeFriend(Long parentLid, Long Lid, Connection con) {
		Connection conn = con;
		int errNum = Errors.UNKNOWN;

		try {
			if (conn == null) {
				conn = dataSource.getConnection();
			}

			PreparedStatement stmt = null;
			String sql = "delete from RELATION where PARENT_ID = ? and LID = ?";
			logger.info(sql);
			stmt = conn.prepareStatement(sql);
			stmt.setLong(1, parentLid);
			stmt.setLong(2, Lid);
			
			try {
				stmt.executeUpdate();
			} catch (SQLException e) {
				logger.info(e.getMessage());
			}

			stmt.close();
			errNum = Errors.OK;

		} catch (Exception ex) {
			logger.info(ex);
			errNum = Errors.UNKNOWN;
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

		return errNum;
	}


}
