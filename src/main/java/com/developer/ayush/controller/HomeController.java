package com.developer.ayush.controller;

import java.sql.Connection;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.developer.ayush.constants.Errors;
import com.developer.ayush.constants.TableList;
import com.developer.ayush.dao.CommonDAO;
import com.developer.ayush.entities.RelationDTO;
import com.developer.ayush.entities.TestDTO;
import com.developer.ayush.service.HelperService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Service
@RestController
@RequestMapping(value = "/home")
public class HomeController {

	private static final Logger logger = LogManager.getLogger(HomeController.class.getName());

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HelperService helperservice;

	@Autowired
	private CommonDAO commonDAO;

	@Qualifier("datasource")
	@Autowired
	private javax.sql.DataSource dataSource;

	@RequestMapping(value = "/createProfile", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getCreateProfile() {

		JSONObject responseJson = new JSONObject();
		int errNum = Errors.UNKNOWN;
		Connection conn = null;

		try {

			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");

			logger.info("REQUEST_PROFILE_CREATION {" + "name : " + name + "," + "email : " + email + "," + "password : "
					+ password + "," + "}");

			if (name == null || email == null || password == null) {

				logger.info("Missing Parameters");
				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("message", "Missing Parameters");
				return responseJson.toString();

			}

			Long check = commonDAO.checkIfExist(conn, email);

			if (check != null) {
				logger.info("Profile already Exist");
				responseJson.put("msgType", Errors.RETRY_LATER);
				responseJson.put("Message", "Profile already Exist");
				return responseJson.toString();

			}

			Long lid = commonDAO.getNextSequenceID(conn, TableList.USER);

			TestDTO testdto = new TestDTO();
			testdto.setLid(lid);
			testdto.setName(name);
			testdto.setEmail(email);
			testdto.setPassword(password);

			int err = helperservice.addProfile(testdto, conn);

			if (err != Errors.OK) {

				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("Message", "Failed to add User Profile");
				return responseJson.toString();
			}

			logger.info("Successfully added Category");
			responseJson.put("msgType", Errors.OK);
			responseJson.put("Message", "Profile Added Successfully");
			return responseJson.toString();

		} catch (Exception e) {

			logger.info("Some Exeption Occured");
			responseJson.put("msgType", Errors.UNKNOWN);
			responseJson.put("message", "Some Exeption Occured");
			return responseJson.toString();

		}

	}

	@RequestMapping(value = "/addFriend", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getaddFriend() {

		JSONObject responseJson = new JSONObject();
		int errNum = Errors.UNKNOWN;
		Connection conn = null;

		try {

			String userName = request.getParameter("userName");
			String friendName = request.getParameter("friendName");

			logger.info(
					"REQUEST_ADD_FRIEND {" + "userName : " + userName + "," + "friendName : " + friendName + "," + "}");

			if (userName == null || friendName == null) {
				logger.info("Missing Parameters");
				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("message", "Missing Parameters");
				return responseJson.toString();
			}

			Long parentLid = commonDAO.getLoginId(conn, userName);

			Long Lid = commonDAO.getLoginId(conn, friendName);

			if (parentLid == null || Lid == null) {
				logger.info("Users doesnot exist");
				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("message", "Users doesnot exist");
				return responseJson.toString();
			}

			RelationDTO relationdto = new RelationDTO();
			relationdto.setParentId(parentLid);
			relationdto.setLid(Lid);
			relationdto.setRelation("friend");

			int err = helperservice.addFriend(relationdto, conn);

			if (err != Errors.OK) {

				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("Message", "Failed to add Friend in FriendList");
				return responseJson.toString();
			}

			RelationDTO mutualdto = new RelationDTO();
			mutualdto.setParentId(Lid);
			mutualdto.setLid(parentLid);
			mutualdto.setRelation("friend");

			int err1 = helperservice.addFriend(mutualdto, conn);

			if (err != Errors.OK) {

				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("Message", "Failed to add Friend Mutual Relationship");
				return responseJson.toString();
			}

			logger.info("Friend added Successsfully to friendList");
			responseJson.put("msgType", Errors.OK);
			responseJson.put("Message", "Friend added Successsfully to friendList");
			return responseJson.toString();

		} catch (Exception e) {

			logger.info("Some Exeption Occured");
			responseJson.put("msgType", Errors.UNKNOWN);
			responseJson.put("message", "Some Exeption Occured");
			return responseJson.toString();

		}

	}

	@RequestMapping(value = "/viewFriendList", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getviewFriendList() {

		JSONObject responseJson = new JSONObject();
		int errNum = Errors.UNKNOWN;
		Connection conn = null;

		try {

			String userName = request.getParameter("userName");

			logger.info("REQUEST_ADD_FRIEND {" + "userName : " + userName + "}");

			if (userName == null) {
				logger.info("Missing Parameters");
				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("message", "Missing Parameters");
				return responseJson.toString();
			}

			Long parentLid = commonDAO.getLoginId(conn, userName);

			if (parentLid == null) {
				logger.info("Users doesnot exist");
				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("message", "Users doesnot exist");
				return responseJson.toString();
			}

			ArrayList<Long> getfriendId = helperservice.viewFriend(parentLid, conn);

			if (getfriendId == null) {

				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("Message", "No friends found in your freindList");
				return responseJson.toString();
			}

			ArrayList<String> getFriendName = new ArrayList<String>();

			for (int i = 0; i < getfriendId.size(); i++) {
				String temp = commonDAO.getName(conn, getfriendId.get(i));

				if (temp != null) {
					getFriendName.add(temp);
				}
			}

			logger.info("friendList fetched Successfully");
			responseJson.put("msgType", Errors.OK);
			responseJson.put("Friend List", getFriendName);
			return responseJson.toString();

		} catch (Exception e) {

			logger.info("Some Exeption Occured");
			responseJson.put("msgType", Errors.UNKNOWN);
			responseJson.put("message", "Some Exeption Occured");
			return responseJson.toString();

		}

	}

	@RequestMapping(value = "/removeFriend", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public String getremoveFriend() {

		JSONObject responseJson = new JSONObject();
		int errNum = Errors.UNKNOWN;
		Connection conn = null;

		try {

			String userName = request.getParameter("userName");
			String friendToRemove = request.getParameter("friendToRemove");

			logger.info("REQUEST_ADD_FRIEND {" + "userName : " + userName + "friendToRemove : " + friendToRemove + "}");

			if (userName == null || friendToRemove == null) {
				logger.info("Missing Parameters");
				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("message", "Missing Parameters");
				return responseJson.toString();
			}

			Long parentLid = commonDAO.getLoginId(conn, userName);
			
			logger.info("parentLid : " +parentLid);

			Long Lid = commonDAO.getLoginId(conn, friendToRemove);
			
			logger.info("Lid : " +Lid);

			if (parentLid == null || Lid == null) {
				logger.info("Friends Or User doesnot exist");
				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("message", "Friends Or User doesnot exist");
				return responseJson.toString();
			}

			int err = helperservice.removeFriend(parentLid,Lid,conn);

			int err1 = helperservice.removeFriend(Lid,parentLid, conn);

			if (err != Errors.OK || err1 != Errors.OK) {
				responseJson.put("msgType", Errors.UNKNOWN);
				responseJson.put("Message", "Failed to Remove Friend in UserList");
				return responseJson.toString();
			}

			logger.info("friend Removed Successfully");
			responseJson.put("msgType", Errors.OK);
			return responseJson.toString();

		} catch (Exception e) {

			logger.info("Some Exeption Occured");
			responseJson.put("msgType", Errors.UNKNOWN);
			responseJson.put("message", "Some Exeption Occured");
			return responseJson.toString();

		}

	}

}
