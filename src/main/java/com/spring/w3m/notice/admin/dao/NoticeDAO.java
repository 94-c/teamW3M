package com.spring.w3m.notice.admin.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.w3m.notice.admin.vo.NoticeVO;
import com.spring.w3m.paging.common.Pagination;
import com.spring.w3m.paging.common.Search;

@Repository
public class NoticeDAO {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	public NoticeVO getUserNotice(NoticeVO vo) {
		sqlSessionTemplate.update("NoticeCntUpdate", vo);
		return sqlSessionTemplate.selectOne("NoticeDAO.getUserNotice", vo);
	}

	public List<NoticeVO> getUserNoticeList(NoticeVO vo) {
		return sqlSessionTemplate.selectList("NoticeDAO.getUserNoticeList", vo);
	}

	// 공지사항 게시글 목록 가져오기
	public List<NoticeVO> getNoticeList(NoticeVO vo) {
		return sqlSessionTemplate.selectList("NoticeDAO.getNoticeList");
	}

	public int getNoticeSearchCnt(String searchKeyword) {
		System.out.println("getNoticeSearchCnt() 실행");
		return sqlSessionTemplate.selectOne("NoticeDAO.getNoticeSearchCnt");
	}

	public int getNoticeListCnt(Search search) {
		System.out.println("getNoticeListCnt() 실행");
		return sqlSessionTemplate.selectOne("NoticeDAO.getNoticeListCnt");
	}

	public List<NoticeVO> getNoticePageList(Search search) {
		System.out.println("getNoticePageList() 실행");
		return sqlSessionTemplate.selectList("NoticeDAO.getNoticePageList", search);
	}

	public List<NoticeVO> getNoticeSearchPagingList(Pagination pagination) {
		System.out.println("getNoticeSearchPagingList() 실행");
		return sqlSessionTemplate.selectList("NoticeDAO.getNoticeSearchPagingList");
	}

	// 공지사항 글 등록
	public void admin_notice_insert(NoticeVO vo) {
		sqlSessionTemplate.insert("NoticeDAO.admin_notice_insert", vo);
	}

	// 공지사항 수정
	public void admin_notice_update(NoticeVO vo) {
		sqlSessionTemplate.update("NoticeDAO.admin_notice_update", vo);
	}

	// 공지사항 삭제
	public void admin_notice_delete(NoticeVO vo) {
		sqlSessionTemplate.delete("NoticeDAO.admin_notice_delete", vo);
	}

	// 공지사항 상세보기
	public NoticeVO getNotice(NoticeVO vo) {
		return sqlSessionTemplate.selectOne("NoticeDAO.getNotice", vo);
	}

}
