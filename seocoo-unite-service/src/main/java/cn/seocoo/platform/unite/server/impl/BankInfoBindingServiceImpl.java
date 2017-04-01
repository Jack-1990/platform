package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.Area;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserBank;
import cn.seocoo.platform.service.area.inf.AreaService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userBank.inf.UserBankService;
import cn.seocoo.platform.unite.Result;

public class BankInfoBindingServiceImpl implements Service{
	private Logger logger = Logger.getLogger(this.getClass());
	private UserBankService userBankService;
	private MerchantUserService merchantUserService;
	private MerchantInfoService merchantInfoService;
	private AreaService areaService;
	
	/**
	 * 用户基本银行卡信息绑定
	 */
	public Object sevice(String param){
		logger.debug("BankInfoBindingServiceImpl 请求参数：" + param);
		UserBank user = JSON.parseObject(param, UserBank.class);
		// 参数验证
		String validateRes = validateParam(user);
		Result result = new Result();
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			String merchantCode=getMerchantCode(user.getLoginName());
			String province=getAreaName("province",user.getBankAreaCode());
			String city=getAreaName("city",user.getBankAreaCode());
			
			UserBank m = new UserBank();
			m.setMerchantCode(merchantCode);
			List<UserBank> userList=userBankService.queryUserBankList(m);
			if(userList !=null && userList.size()>0){
				//判断是否可以更改信息
				MerchantInfo u=new MerchantInfo();
				u.setMerchantCode(merchantCode);
				List<MerchantInfo> uList=merchantInfoService.queryMerchantInfoList(u);
                if(uList !=null && uList.size()>0){
                	if(!StringUtil.isEmpty(uList.get(0).getCertifyStatus().toString())){
                		int status=uList.get(0).getCertifyStatus();
                		if(status ==1 ){
                			result.setResultMsg("审核中的信息，不能修改");
                		}else if(status==2){
                			result.setResultMsg("审核通过的信息，不能修改");
                		}else{
                			//修改
        					m.setId(userList.get(0).getId());
        					m.setCardNumber(user.getCardNumber());
        					m.setAffiliatedBank(user.getAffiliatedBank());
        					m.setBankAreaCode(user.getBankAreaCode());
        					m.setBankArea(user.getBankArea());
        					m.setProvince(province);
        					m.setCity(city);
        					m.setBankNumber(user.getBankNumber());
        					m.setBankName(user.getBankName());
        					//审核不通过的为2，可以修改再次审核
        					//m.setAuditStatus(Constant.COMMON_STATUS_0);

        					userBankService.updateUserBank(m);
        					result.setResultCode(Constant.RESULT_CODE_SUCCESS);
        					result.setResultMsg("用户银行卡信息修改成功");
                		}
    				}else{
    					result.setResultMsg("该用户审核信息不明确");
    				}
                }else{
                	result.setResultMsg("该用户审核信息不明确");
                }
			}else{
				//新增
				m.setCardNumber(user.getCardNumber());
				m.setAffiliatedBank(user.getAffiliatedBank());
				m.setBankAreaCode(user.getBankAreaCode());
				m.setBankArea(user.getBankArea());
				m.setProvince(province);
				m.setCity(city);
				m.setBankNumber(user.getBankNumber());
				m.setBankName(user.getBankName());
				m.setAuditStatus(Constant.COMMON_STATUS_0);
		        m.setCreateUser(user.getLoginName());
		        m.setCreateTime(new Date());
				
				userBankService.saveUserBank(m);
				
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("用户银行卡信息绑定成功");
			}
		} else{
			result.setResultMsg(validateRes);
		}
		String res = JSON.toJSONString(result);
		logger.debug("BankInfoBindingServiceImpl 返回信息报文：" + res);
		return res;
	 }
	
	
	/**
	 * 获取省市中文
	 * @param string
	 * @param bankAreaCode
	 * @return
	 */
	private String getAreaName(String area, String bankAreaCode) {
		String str="";
		Area a=new Area();
		if("province".equals(area) && bankAreaCode.length() >2){
			//省
			a.setCode(bankAreaCode.substring(0, 2)+"0000");
		}
	    if("city".equals(area)){
	    	//市
	    	a.setCode(bankAreaCode);
	    }
	    List<Area> alist=areaService.queryAreaList(a);
	    if(alist !=null && alist.size()>0){
	    	str=alist.get(0).getName();
	    }
		return str;
	}


	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(UserBank user){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
		}
		if(StringUtil.isEmpty(user.getCardNumber())){
			return "用户银行卡账号不能为空";
		}
		if(user.getCardNumber().length()<16 || user.getCardNumber().length()>19 || !isNumeric(user.getCardNumber())){
			return "用户银行卡账号格式不对";
		}
		if(StringUtil.isEmpty(user.getAffiliatedBank())){
			return "所属银行不能为空";
		}
		if(StringUtil.isEmpty(user.getBankAreaCode())){
			return "银行开户地区编码不能为空";
		}
		if(StringUtil.isEmpty(user.getBankArea())){
			return "银行开户地区名称不能为空";
		}
		if(StringUtil.isEmpty(user.getBankNumber())){
			return "银行开户行号不能为空";
		}
		if(StringUtil.isEmpty(user.getBankName())){
			return "银行开户行名称不能为空";
		}
		return "OK";
	}

    /**
     * 判断是否是数字
     * @param str
     * @return
     */
	public boolean isNumeric(String str){ 
		   Pattern pattern = Pattern.compile("[0-9]*"); 
		   Matcher isNum = pattern.matcher(str);
		   if( !isNum.matches() ){
		       return false; 
		   } 
		   return true; 
	}
	
	/**
	 * 获取门店编码
	 * @param loginName
	 * @return
	 */
	public String getMerchantCode(String loginName){
		String merchantCode="";
		MerchantUser user=new MerchantUser();
		user.setLoginName(loginName);
		List<MerchantUser> list=merchantUserService.queryMerchantUserList(user);
		if(list !=null && list.size() >0){
			merchantCode=list.get(0).getMerchantCode();
		}
		return merchantCode;
	}
	
	
	public UserBankService getUserBankService() {
		return userBankService;
	}


	public void setUserBankService(UserBankService userBankService) {
		this.userBankService = userBankService;
	}


	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}


	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}


	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}


	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
}
