package com.wlp.web;


import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.wlp.api.entity.WlpActivecode;
import com.wlp.api.service.WlpActivecodeService;


/**
 * 激活码管理
 * 规则说明：
 * 		激活交易激活码30元
 * @author 明华
 *
 */
@Controller()
public class ActiveCodeController {
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	WlpActivecodeService wlpActivecodeService;
	
	private static final String USER_NAME = "USER_NAME";
	
	@RequestMapping(value = "/wlp/getCanUseActivecodes", method = RequestMethod.GET)
	public @ResponseBody 		ArrayList<WlpActivecode>  getCanUseActivecodes() {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpActivecode>codes= (ArrayList<WlpActivecode>) wlpActivecodeService.getCanUseActivecodes(username);
		return codes;
	}
	
	@RequestMapping(value = "/wlp/getCanUseActivecodesBySearch", method = RequestMethod.POST)
	public @ResponseBody 		ArrayList<WlpActivecode>  getCanUseActivecodesBySearch(@RequestParam(required = true) String keyword) {
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute(USER_NAME);
		if (username == null) {
			return null;
		}
		ArrayList<WlpActivecode>results=new 	ArrayList<WlpActivecode>();
		ArrayList<WlpActivecode>codes= (ArrayList<WlpActivecode>) wlpActivecodeService.getCanUseActivecodes(username);
		if(keyword==null||keyword.isEmpty()){
			return codes;
		}

		if(codes!=null&&codes.size()>0){
			for(WlpActivecode code:codes){
				String state="未激活";
				if("1".equals(code.getStatus())){
					state="激活";
				}
				String email=code.getEmail();
 				if(email.contains(keyword)||state.contains(keyword)||code.getCode().contains(keyword)){							
					
					if(email.contains(keyword)){
						email=email.replaceAll(keyword, " <span style='color:red'>"+keyword+"</span>");
						code.setEmail(email);	
					}
					if(state.contains(keyword)){
						code.setStatus(state.replaceAll(keyword, " <span style='color:red'>"+keyword+"</span>"));		
					}
					if(code.getCode().contains(keyword)){
						code.setCode(code.getCode().replaceAll(keyword, " <span style='color:red'>"+keyword+"</span>"));			
					}
					
					results.add(code);
				}
			}
			
		}
		return results;
	}
}
