package com.kh.naturephone.member.model.service;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kh.naturephone.common.PageInfo;
import com.kh.naturephone.member.model.dao.MemberDao;
import com.kh.naturephone.member.model.vo.Member;
import com.kh.naturephone.member.model.vo.MyBoard;
import com.kh.naturephone.member.model.vo.MyReply;

@Service
public class MemberServiceImpl implements MemberService{

	@Autowired 
	private MemberDao mDao;
	
	/*----------------- 회원 관련 -----------------*/
	
	@Override
	public Member loginMember(Member m) {
		return mDao.selectMember(m);
	}

	@Override
	public void idOverlapCheck(String id, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.println(mDao.idOverlapCheck(id));
		out.flush();
		out.close();
	}
	
	@Override
	public void emailOverlapCheck(String email, HttpServletResponse response) throws Exception {
		PrintWriter out = response.getWriter();
		out.println(mDao.emailOverlapCheck(email));
		out.flush();
		out.close();		
	}

	@Override
	public int insertMember(Member m) {
		return mDao.insertMember(m);
	}

	@Override
	public int updateMember(Member m) {
		return mDao.updateMember(m);
	}

	@Override
	public int pwdUpdate(Member loginUser) {
		return mDao.pwdUpdate(loginUser);
	}
	
	@Override
	public int deleteMember(Member m) {
		return mDao.deleteMember(m);
	}

	@Override
	public String findIdSendMail(String findIdEmail) {
		return mDao.findIdSendMail(findIdEmail);
	}

	@Override
	public int findPwdSendEmail(Member m) {
		return mDao.findPwdSendEmail(m);
	}
	
	/*----------------- 네아로 관련 -----------------*/
	
	@Override
	public Member searchNEmail(String nEmail) {
		return mDao.searchNEmail(nEmail);
	}
	
	@Override
	public void naverInsert(Member m) {
		mDao.naverInsert(m);
	}

	/*----------------- 나의 게시글, 나의 댓글 조회 -----------------*/
	
	@Override
	public List<MyBoard> selectMyBoardList(int userNo, PageInfo pi) {
		return mDao.selectMyBoardList(userNo, pi);
	}
	
	@Override
	public int selectListCount(int userNo) {
		return mDao.selectListCount(userNo);
	}

	@Override
	public List<MyReply> selectMyReplyList(int userNo, PageInfo pi) {
		return mDao.selectMyReplyList(userNo, pi);
	}

	@Override
	public int selectReplyListCount(int userNo) {
		return mDao.selectReplyListCount(userNo);
	}



	
	
	

	


	

	
}
