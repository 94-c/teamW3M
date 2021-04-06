package com.spring.w3m.statistics.admin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.w3m.statistics.admin.dao.StatisticsDAO;
import com.spring.w3m.statistics.admin.vo.StatisticsVO;
@Service
public class StatisticsServiceImpl implements StatisticsService{

	@Autowired
	private StatisticsDAO dao;
	
	@Override
	public List<StatisticsVO> Gender_Money() {
		
		return dao.Gender_Money();
	}
	
}