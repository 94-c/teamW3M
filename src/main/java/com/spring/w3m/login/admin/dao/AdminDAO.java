package com.spring.w3m.login.admin.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.w3m.login.admin.vo.AdminVO;

@Repository
public class AdminDAO {
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public AdminVO getAdmin() {
		return sqlSessionTemplate.selectOne("AdminDAO.getAdmin");
		
	}
}
