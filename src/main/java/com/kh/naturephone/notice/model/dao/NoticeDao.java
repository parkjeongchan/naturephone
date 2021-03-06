package com.kh.naturephone.notice.model.dao;

import java.util.List;

import com.kh.naturephone.common.B_Att_TB;
import com.kh.naturephone.common.Board_TB;
import com.kh.naturephone.common.PageInfo;
import com.kh.naturephone.common.Search;
import com.kh.naturephone.notice.model.vo.Reply;

public interface NoticeDao {

	int selectListCount();

	List<Board_TB> selectList(PageInfo pi);

	int insertNotice(Board_TB notice);

	int insertNoticeAtt(B_Att_TB att);

	List<Board_TB> searchList(Search search, PageInfo pi);
	
	Board_TB selectNotice(int bno);

	void updateReadCount(int bno);

	List<Reply> selectReplyList(int bno);

	B_Att_TB selectNoticeAtt(int bno);

	int updateNotice(Board_TB n);

	int updateNoticeAtt(B_Att_TB na);

	int insertNoticeAtt(B_Att_TB na, int bno);

	int deleteNotice(int bno);

	void deleteNoticeAtt(int bno);

	void insertNoticeReply(Reply r);

	int deleteReply(Reply r);

	int searchListCount(Search search);

	List<Board_TB> selectMainList();
	
}
