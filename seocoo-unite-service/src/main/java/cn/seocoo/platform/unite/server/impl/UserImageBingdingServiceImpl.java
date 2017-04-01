package cn.seocoo.platform.unite.server.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.UserImage;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.userImage.inf.UserImageService;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class UserImageBingdingServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(UserImageBingdingServiceImpl.class);
	private UserImageService userImageService;
	private MerchantUserService merchantUserService;
	private MerchantInfoService merchantInfoService;


	/**
	 * 用户身份证、银行卡图片绑定
	 */
	public Object sevice(String param) {
		logger.debug("UserImageBingdingServiceImpl 请求报文 :"+param);
		Result result=new Result();
		UserImage  user = JSONObject.parseObject(param, UserImage.class);
		
		// 参数验证
		String validateRes = validateParam(user);
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			String loginName=user.getLoginName();
			String merchantCode=getMerchantCode(user.getLoginName());
			
			UserImage  image = new UserImage();
			image.setMerchantCode(merchantCode);
			List<UserImage> imageList=userImageService.queryUserImageList(image);
			
			//获取图片参数
			image=getUser(image,user);
			if(imageList !=null && imageList.size() >0){
					image.setId(imageList.get(0).getId());
					//审核不通过的为2，可以修改再次审核
					image.setAuditStatus(Constant.COMMON_STATUS_0);
					
					userImageService.updateUserImage(image);
					
					result.setResultCode(Constant.RESULT_CODE_SUCCESS);
					result.setResultMsg("用户图片信息修改成功");
			}else{
				image.setAuditStatus(Constant.COMMON_STATUS_0);
				image.setCreateUser(loginName);
				image.setCreateTime(new Date());
				//代入merchantCode
		        image.setMerchantCode(merchantCode);
				//入库
				userImageService.saveUserImage(image);
				
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
				result.setResultMsg("用户图片信息绑定成功");
			}
			//原样返回
			String visitPath=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
			 
			String iDAfter=image.getiD_after_pic();
			String iDBefore=image.getiD_before_pic();
			String bankBefore=image.getBank_before_pic();
			String bankAfter=image.getBank_after_pic();
			
			if(!StringUtil.isEmpty(iDAfter)){
				iDAfter =visitPath+iDAfter;
             }
			if(!StringUtil.isEmpty(iDBefore)){
				iDBefore =visitPath+iDBefore;
             }
			if(!StringUtil.isEmpty(bankBefore)){
				bankBefore =visitPath+bankBefore;
             }
			if(!StringUtil.isEmpty(bankAfter)){
				bankAfter =visitPath+bankAfter;
             }
			image.setIdAfterStr(iDAfter);
			image.setIdBeforeStr(iDBefore);
			image.setBankBeforeStr(bankBefore);
			image.setBankAfterStr(bankAfter);
			image.setBank_after_pic(null);
			image.setBank_before_pic(null);
			
			result.setResultData(image);
		}else{
			result.setResultMsg(validateRes);
		}
		String msg = JSON.toJSONString(result);
		logger.debug("UserImageBingdingServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	/**
	 * 得到图片和图片地址
	 * @param image
	 * @param user
	 * @return
	 */
	private UserImage getUser(UserImage image,UserImage user) {
		String loginName=user.getLoginName();
		
		String savePath=SystemConfigUtil.getSingleProperty("userpic_save_path").getPropertyValue();
		//String visitPath=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
		
		String pic="/images/userpic/";

//		String savePath="E:\\";
//		String visitPath="E:\\";
		
		if(!StringUtil.isEmpty(user.getIdAfterStr()) && !user.getIdAfterStr().contains("http")){
			   String fileName=getFileName(loginName);
				if(generateImage(user.getIdAfterStr(),savePath +fileName)){
					image.setiD_after_pic(pic + fileName);
				}
		}
		if(!StringUtil.isEmpty(user.getIdBeforeStr()) && !user.getIdAfterStr().contains("http")){
			 String fileName=getFileName(loginName);
				if(generateImage(user.getIdBeforeStr(),savePath +fileName)){
					image.setiD_before_pic(pic + fileName);
				}		
		}
		if(!StringUtil.isEmpty(user.getBankAfterStr()) && !user.getIdAfterStr().contains("http")){
			 String fileName=getFileName(loginName);
				if(generateImage(user.getBankAfterStr(),savePath +fileName)){
					image.setBank_after_pic(pic + fileName);
				}
		}
		if(!StringUtil.isEmpty(user.getBankBeforeStr()) && !user.getIdAfterStr().contains("http")){
			 String fileName=getFileName(loginName);
				if(generateImage(user.getBankBeforeStr(),savePath +fileName)){
					image.setBank_before_pic(pic + fileName);
				}
		}
		return image;
	}


	/**
	 * 获取随机文件名称
	 * @return
	 */
	private String getFileName(String loginName) {
		String fileName=loginName + StringUtil.random6()+".png";
		return fileName;
	}



	/**
	 * 验证参数
	 * @param user
	 * @return
	 */
	public String validateParam(UserImage user){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
		}
		if(StringUtil.isEmpty(user.getIdAfterStr())){
			return "用户身份证背面图片不能为空";
		}
		if(StringUtil.isEmpty(user.getIdBeforeStr())){
			return "用户身份证正面图片不能为空";
		}
		if(StringUtil.isEmpty(user.getBankAfterStr())){
			return "用户银行卡背面图片不能为空";
		}
		if(StringUtil.isEmpty(user.getBankBeforeStr())){
			return "用户银行卡正面图片不能为空";
		}
		if(getStatus(user)){
			return "只有未完善和未通过的信息才可以修改";
		}
		return "OK";
	}

	
	/**
	 * base64  转化为图片
	 * @param imgStr
	 * @param path
	 * @return
	 */
	@SuppressWarnings("restriction")
	public  boolean generateImage(String imgStr, String path) {
		if (imgStr == null){
			 return false;
		}
		BASE64Decoder decoder = new BASE64Decoder();
		try {
		      //解密
		      byte[] b = decoder.decodeBuffer(imgStr);
		     //待处理
			  for (int i = 0; i < b.length; ++i) {
		           if (b[i] < 0) {
			          b[i] += 256;
			       }
			   }
				OutputStream out = new FileOutputStream(path);
				out.write(b);
				out.flush();
				out.close();
			    return true;
		   }catch (Exception e) {
			   return false;
		}
    }
	
	
	/**
	 * 状态判断是否可以更改
	 * @param user
	 * @return
	 */
	private boolean getStatus(UserImage user) {
		String merchantCode=getMerchantCode(user.getLoginName());

		//判断是否可以更改信息
		MerchantInfo u=new MerchantInfo();
		u.setMerchantCode(merchantCode);
		List<MerchantInfo> uList=merchantInfoService.queryMerchantInfoList(u);
        if(uList !=null && uList.size()>0){
        	if(!StringUtil.isEmpty(uList.get(0).getCertifyStatus().toString())){
        		int status=uList.get(0).getCertifyStatus();
        		if(status ==1 || status==2){
        			return true;
        		}else{
        			return false;
        		}
			}else{
				return true;
			}
        }else{
        	return true;
        }
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
	
	public UserImageService getUserImageService() {
		return userImageService;
	}

	public void setUserImageService(UserImageService userImageService) {
		this.userImageService = userImageService;
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
	
	
}
