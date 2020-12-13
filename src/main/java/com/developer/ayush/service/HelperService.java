package com.developer.ayush.service;

import java.sql.Connection;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.developer.ayush.dao.RegisterDAO;
import com.developer.ayush.entities.RelationDTO;
import com.developer.ayush.entities.TestDTO;

@Component
public class HelperService {

	@Autowired
	private RegisterDAO registerdao;

	@Transactional
	public int addProfile(TestDTO testdto, Connection con) {
		return registerdao.addProfile(testdto, con);
	}
	
	@Transactional
	public int addFriend(RelationDTO relationdto, Connection con) {
		return registerdao.addFriend(relationdto, con);
	}
	
	@Transactional
	public ArrayList<Long> viewFriend(Long parentLid, Connection con) {
		return registerdao.viewFriend(parentLid, con);
	}

	@Transactional
	public int removeFriend(Long parentLid, Long Lid, Connection con) {
		return registerdao.removeFriend(parentLid, Lid,  con);
	}
}
