package com.kh.naturephone.goods.model.service;

import java.util.List;
import java.util.Map;

import com.kh.naturephone.attachment.model.vo.Attachment;
import com.kh.naturephone.common.PageInfo;
import com.kh.naturephone.goods.model.vo.Cart;
import com.kh.naturephone.goods.model.vo.Deal;
import com.kh.naturephone.goods.model.vo.Goods;
import com.kh.naturephone.member.model.vo.Member;
import com.kh.naturephone.support.model.vo.Phone;

public interface GoodsService {
	// 물품 등록
	int insertGoods(Goods g);
	
	int insertAtt(Attachment att);

	List<Phone> productName(String keyword);

	Phone selectPFN(String modelName);
	
	int selectGoodsListCount();

	List<Map<String, String>> selectGoodsList(PageInfo pi);

	List<Attachment> selectAttList();

	Phone selectPhoneNameList(int proNo);

	Goods selectDetailGoods(int goodsNo);

	Member selectMember(int userNo);

	int insertDeal(Deal d);

	int insertCart(int goodsNo, int userNo);

	List<Cart> selecteCart(int userNo);

	Goods selectGoods(int gn);

	Member selectSeller(int sellerNo);

	List<Attachment> selectGAList(Integer goodsNo);

	

	




	

}
