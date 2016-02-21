package com.wlp.core.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wlp.api.entity.CommonCst;
import com.wlp.api.entity.WlpActivecode;
import com.wlp.api.service.WlpActivecodeService;
import com.wlp.core.dao.WlpActivecodeMapper;

@Service
public class WlpActivecodeServiceImpl implements WlpActivecodeService {
	@Autowired
	private WlpActivecodeMapper wlpActivecodeMapper;

	@Override
	public List<WlpActivecode> getCanUseActivecodes(String email) {
		List<WlpActivecode> result = null;
		WlpActivecode condition = new WlpActivecode();
		condition.setEmail(email);
		condition.setStatus(CommonCst.NOT_USED);
		result = wlpActivecodeMapper.selectByCondition(condition, null, null);
		return result;
	}

	@Override
	public List<WlpActivecode> getUsedActivecodes(String email) {
		List<WlpActivecode> result = null;
		WlpActivecode condition = new WlpActivecode();
		condition.setEmail(email);
		condition.setStatus(CommonCst.USED);
		result = wlpActivecodeMapper.selectByCondition(condition, null, null);
		return result;
	}

	@Override
	public List<WlpActivecode> getSharedActivecodes(String email) {
		List<WlpActivecode> result = null;
		WlpActivecode condition = new WlpActivecode();
		condition.setShareEmail(email);
		result = wlpActivecodeMapper.selectByCondition(condition, null, null);
		return result;
	}

	@Override
	public WlpActivecode shareActivecode(String fromEmail, String toEmail) {
		List<WlpActivecode> codes = this.getCanUseActivecodes(fromEmail);
		if(codes != null && !codes.isEmpty()){
			WlpActivecode code = codes.get(0);
			code.setEmail(toEmail);
			code.setShareEmail(fromEmail);
			code.setShareTime(new Date());
			wlpActivecodeMapper.updateByPrimaryKeySelective(code);
			return code;
		}
		return null;
	}

	public WlpActivecodeMapper getWlpActivecodeMapper() {
		return wlpActivecodeMapper;
	}

	public void setWlpActivecodeMapper(WlpActivecodeMapper wlpActivecodeMapper) {
		this.wlpActivecodeMapper = wlpActivecodeMapper;
	}

	@Override
	public void activeUser(String email,String code) {
		WlpActivecode condition = new WlpActivecode();
		condition.setEmail(email);
		List<WlpActivecode> result  = wlpActivecodeMapper.selectByCondition(condition, null, null);
		if(result != null && !result.isEmpty()){
			for(WlpActivecode acode:result){
				if(acode.getCode().equals(code)){
					acode.setStatus(CommonCst.USED);
					wlpActivecodeMapper.updateByPrimaryKeySelective(acode);
					break;
				}
			}
		}

	}

}
