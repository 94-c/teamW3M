package com.spring.w3m.inquiry.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.w3m.inquiry.user.dao.ReplyDAO;
import com.spring.w3m.inquiry.user.vo.ReplyVO;
import com.spring.w3m.review.user.vo.ReviewVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDAO replyDAO;

	@Override
	public List<ReplyVO> getReplyList(int inq_seq) {
		return replyDAO.getReplyList(inq_seq);
	}

	@Override
	public void insertReply(ReplyVO vo) {
		replyDAO.insertReply(vo);
	}

	@Override
	public void deleteReply(ReplyVO vo) {
		replyDAO.deleteReply(vo);
	}

	@Override
	public void updateReply(ReplyVO vo) {
		replyDAO.updateReply(vo);
	}

	@Override
	public List<ReplyVO> getReplyList_review(int review_seq) {
		return replyDAO.getReplyList(review_seq);
	}

	// 후기 댓글 영역
	@Override
	public List<ReplyVO> getReviewReplyList(int review_seq) {
		return replyDAO.getReviewReplyList(review_seq);
	}

	@Override
	public void insertReviewReply(ReplyVO vo) {
		replyDAO.insertReviewReply(vo);
	}

	@Override
	public ReplyVO getReply(ReplyVO vo) {
		return replyDAO.getReply(vo);
	}

	@Override
	public void updateReviewReply(ReplyVO vo) {
		replyDAO.updateReviewReply(vo);
	}

	@Override
	public int count(int review_re_seq) {
		return 0;
	}

	@Override
	public void deleteReviewReply(ReplyVO vo) {
		replyDAO.deleteReviewReply(vo);
	}

	@Override
	public void reviewCnt(ReviewVO vo) {
		replyDAO.reviewCnt(vo);
	}

	@Override
	public void deleteReviewCnt(ReviewVO vo) {
		replyDAO.deleteReviewCnt(vo);
	}

}
