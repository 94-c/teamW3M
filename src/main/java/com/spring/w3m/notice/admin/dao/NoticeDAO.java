package com.spring.w3m.notice.admin.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.w3m.notice.admin.vo.NoticeVO;
import com.spring.w3m.paging.common.Pagination;

@Repository
public class NoticeDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public void insertNotice(NoticeVO vo) {
		sqlSessionTemplate.insert("NoticeDAO.insertNotice", vo);
	}

	public void updateNotice(NoticeVO vo) {
		sqlSessionTemplate.update("NoticeDAO.updateNotice", vo);
	}

	public void deleteNotice(NoticeVO vo) {
		sqlSessionTemplate.delete("NoticeDAO.deleteNotice", vo);
	}

	public NoticeVO getNotice(NoticeVO vo) {
		sqlSessionTemplate.update("NoticeCntUpdate", vo);
		return sqlSessionTemplate.selectOne("NoticeDAO.getNotice", vo);
	}

	public List<NoticeVO> getNoticeList(NoticeVO vo) {
		return sqlSessionTemplate.selectList("NoticeDAO.getNoticeList", vo);
	}
	
	public int getNoticeListCnt() {
		return sqlSessionTemplate.selectOne("NoticeDAO.getNoticeListCnt");
	}
	
	public List<NoticeVO> getPageList(Pagination pagination){
		return sqlSessionTemplate.selectList("NoticeDAO.getPageList", pagination);
	}
}
