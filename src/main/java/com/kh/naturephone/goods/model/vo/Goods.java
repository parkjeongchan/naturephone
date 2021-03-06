package com.kh.naturephone.goods.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Goods {
	private Integer goodsNo;
	private Integer userNo;
	private Integer proNo;
	private Integer carrNo;
	private String goodsTitle;
	private String content;
	private Date createDate;
	private Date modifyDate;
	private String dealWay;
	private String condition;
	private String component;
	private Integer price;
	private Integer deliveryFee;
	private String dealAdd;
	private String drctStatus;
	private String status;
	private String sbank;
	private String saccount;
	private String openDate;
}
