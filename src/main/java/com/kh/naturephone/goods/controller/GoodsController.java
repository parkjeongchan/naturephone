package com.kh.naturephone.goods.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kh.naturephone.attachment.model.vo.Attachment;
import com.kh.naturephone.common.PageInfo;
import com.kh.naturephone.common.Pagination;
import com.kh.naturephone.goods.model.service.GoodsService;
import com.kh.naturephone.goods.model.vo.Cart;
import com.kh.naturephone.goods.model.vo.Deal;
import com.kh.naturephone.goods.model.vo.Goods;
import com.kh.naturephone.member.model.vo.Member;
import com.kh.naturephone.support.model.vo.Phone;


@Controller
@RequestMapping("/goods")
@SessionAttributes({"msg"})
public class GoodsController {
	@Autowired
	private GoodsService gService;
	int count = 0;

	@PostMapping("/insert")
	public String goodsInsert(@ModelAttribute() Goods g,
							  @RequestParam(value="att") MultipartFile[] files,
							  @RequestParam(value="year") String year,
							  @RequestParam(value="month", required = false) String month,
							  @RequestParam(value="day", required = false) String day,// defaultValue="1" ?????????
							  @RequestParam(value="gphone") String modelName, // ?????? ?????? ???
							  HttpServletRequest request) {
		
		Phone phoneForName = gService.selectPFN(modelName);  // ????????? ???????????? ?????? ?????????	
		//System.out.println(phoneForName);
		int proNo = phoneForName.getProno();			// ???????????? ???????????? ????????? ??????
		
		g.setProNo(proNo);								   // ????????? ?????? ????????? ???????????? ?????? 
			
		String openDate = year + " " + month + " " + day; // ????????? ????????? ?????????
		g.setOpenDate(openDate); // ????????? ??????
		// System.out.println(g.getUserNo());

		System.out.println(g);
		

		int insertReslut = gService.insertGoods(g); 
		
		
		
		
		// ?????? ????????? ?????? ?????????
		int length = files.length;
		String[] renameFileName = new String[length];		
		
		for(int i = 0; i < length; i++) {
			Attachment att = new Attachment();
			if(!files[i].getOriginalFilename().equals("")) {			
				renameFileName[i] = saveFile(files[i], request);
				
				if(renameFileName[i] != null) {	
					att.setGoodsNo(insertReslut - 1);				
					att.setGaFileName(files[i].getOriginalFilename());
					att.setGaRename(renameFileName[i]);
					att.setGaOrder(i + 1);
					att.setGaFilePath("C:\\workspace\\Spring\\workspace\\naturephone\\src\\main\\webapp\\resources\\guploadFiles");					
				}			
			}
			
			// ??????????????? ?????? DB insert
			gService.insertAtt(att);
			
		}
		
		if(insertReslut > 0) {
			return "redirect:/goods/list";
		} else {
			return "common/errorPage";
		}
	}
	
	public String saveFile(MultipartFile files, HttpServletRequest reqeust) {
		String root = reqeust.getSession().getServletContext().getRealPath("resources");
		String savePath = root + "\\guploadFiles";
		File folder = new File(savePath);
		if(!folder.exists()) folder.mkdir();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String originalFileName = files.getOriginalFilename();
		String renameFileName = sdf.format(new Date()) + "_"
								+ (int)(Math.random() * 100000)
								+ originalFileName.substring(originalFileName.lastIndexOf("."));
		
		String renamePath = folder + "\\" + renameFileName;
		
		try {
			files.transferTo(new File(renamePath));
		} catch (IllegalStateException | IOException e) {
			System.out.println("?????? ????????? ?????? : " + e.getMessage());
		}
		
		return renameFileName;
	}
	
	@GetMapping("/ginsertpage")
	public String insertPage() {
		return "goods/goodsInsert";
	}
	
	
	@GetMapping("/productName")
	public void productName(HttpServletResponse response,
							HttpServletRequest request) throws IOException {  
		String keyword = request.getParameter("keyword");
		
		if(keyword != "" && keyword != null) {
			List<Phone> list = gService.productName(keyword);
			response.setContentType("application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(new Gson().toJson(list));
			
			out.flush();
			out.close();
		}

	}
	
	@GetMapping("/list")
	public ModelAndView SelectgoodsList(ModelAndView mv,
			 					  @RequestParam(value="page", required=false, defaultValue="1") int currentPage) {
		
		int listCount = gService.selectGoodsListCount();
		
		// System.out.println(listCount);
		
		// ?????? ???????????? ?????? ????????? ????????? ??????
		PageInfo pi = Pagination.getPageInfo(currentPage, listCount);
		
		List<Map<String, String>> list = gService.selectGoodsList(pi);
		// System.out.println(list.get(0));
		
		List<Phone> phoneNameList = new ArrayList<Phone>();
		
		// ?????? ????????? ??? ?????? ?????? ????????????
		for(Map<String, String> m : list) {
			String proNoString = String.valueOf(m.get("proNo"));
			int proNo = Integer.parseInt(proNoString);
			Phone p = gService.selectPhoneNameList(proNo);
			phoneNameList.add(p);
		}
		
		// System.out.println(phoneNameList);
		
		// ???????????? ????????????
		List<Attachment> attList = gService.selectAttList();
		
		
		// System.out.println(list.get(0));
		
		if(list.get(0) != null) {
			mv.addObject("list", list);
			mv.addObject("pi", pi);
			mv.addObject("attList", attList);
			mv.addObject("phoneNameList", phoneNameList);
			mv.setViewName("goods/goodsList");
		} else {
			mv.addObject("msg", "?????? ?????? ??????!");
			mv.setViewName("common/errorPage");
		}
		return mv;
	}
	
	@GetMapping("/detail")
	public String goodsDetail(int goodsNo,
							  Model model) {
		
		Goods g = gService.selectDetailGoods(goodsNo);
		
		int proNo = g.getProNo();
		int userNo = g.getUserNo();
		
		Phone p = gService.selectPhoneNameList(proNo);
		
		// ?????? ????????? ????????? ??????
		Member m = gService.selectMember(userNo);
		
		// ?????? ????????? ???????????? ??????
		List<Attachment> aList = gService.selectGAList(g.getGoodsNo());
		
		// System.out.println(aList);
		
		
		// ??????, ??????, ?????????, ???????????? ?????? 
		model.addAttribute("g", g);
		model.addAttribute("p", p);
		model.addAttribute("m", m);
		model.addAttribute("aList", aList);
		
		return "goods/goodsDetail";
	}
	
	@RequestMapping("/order")
	public String goodsOrder(int goodsNo,
							  Model model
							  ) {
		
		
		
		
		
		Goods g = gService.selectDetailGoods(goodsNo);
		int proNo = g.getProNo();
		Phone p = gService.selectPhoneNameList(proNo);
		
		model.addAttribute("p", p);
		model.addAttribute("g", g);
		
		return "goods/goodsPay";
	}
	
	@PostMapping("/dealInsert")
	public String dealInsert(Deal d,
							 Model model) {
		
		System.out.println(d);
		
		int result = gService.insertDeal(d);
		
		
		
		if(result > 0) {
			model.addAttribute("msg", "?????? ?????? ??????. ????????? ??????????????????");
			return "redirect:/goods/detail?goodsNo=" + d.getGoodsNo();
		} else {
			model.addAttribute("msg", "???????????? ??????");		
			return "common/errorPage";
		}
		
	}
	
	@GetMapping("/cartPage")
	public String cartPage(@SessionAttribute("loginUser") Member loginUser,
						   Model model) {
		// System.out.println(loginUser);
		
		int userNo = loginUser.getUserNo();
		
		List<Cart> cList = gService.selecteCart(userNo);
		List<Goods> g = new ArrayList<Goods>();
		List<Phone> p = new ArrayList<Phone>();
		List<Member> m = new ArrayList<Member>();
		
		for(int i = 0; i < cList.size(); i++) {			
			Cart c = cList.get(i);
			int gn = c.getGoodsNo();
			Goods gg = gService.selectGoods(gn);
			
			// ???????????? ????????? ????????????
			int sellerNo = gg.getUserNo();
			Member seller = gService.selectSeller(sellerNo);
			m.add(seller);
			//System.out.println(seller);
			
			g.add(gg);
			int pn = gg.getProNo();
			Phone pp = gService.selectPhoneNameList(pn);
			p.add(pp);
		}
	
		
		if(cList != null) {
			model.addAttribute("g", g);
			model.addAttribute("p", p);
			model.addAttribute("m", m);
			model.addAttribute("cList", cList);
			
		}
		return "goods/goodsCart";
		
	}
	
	@GetMapping("/insertCart")
	public String insertCart(@SessionAttribute("loginUser") Member loginUser,
							 int goodsNo,
							 Model model) {
		int userNo = loginUser.getUserNo();

		int result = gService.insertCart(goodsNo, userNo);
		
		if(result > 0) {
			return "redirect:/goods/cartPage";		
		} else {
			return "common/errorPage";
		}
		
	}
	
	@PostMapping("/carttoorder")
	public String carttoorder() {
		
		
		
		return "";
	}
	
	
	
}
