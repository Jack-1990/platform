package cn.seocoo.platform.unite.server.impl;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.log4j.Logger;

import sun.misc.BASE64Decoder;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.merchantUser.inf.MerchantUserService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;

public class UserHeadPicBingdingServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(UserHeadPicBingdingServiceImpl.class);
	private MerchantUserService merchantUserService;


	/**
	 * 用户头像图片绑定
	 */
	public Object sevice(String param) {
		logger.debug("UserHeadPicBingdingServiceImpl 请求报文 :"+param);
		Result result=new Result();
		MerchantUser user = JSON.parseObject(param, MerchantUser.class);
		
		// 参数验证
		String validateRes = validateParam(user);
		result.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("OK".equals(validateRes)){
			String loginName=user.getLoginName();
			
			MerchantUser  u = new MerchantUser();
			u.setLoginName(loginName);
			List<MerchantUser> uList=merchantUserService.queryMerchantUserList(u);
			
			if(uList !=null && uList.size() >0){
				u.setMerchantCode(uList.get(0).getMerchantCode());
				if(!StringUtil.isEmpty(user.getHeadPic()) && !user.getHeadPic().contains("http")){
					//获取图片参数
					u=getUser(u,user);
					
					merchantUserService.updateMerchantUser(u);
				}
				result.setResultMsg("用户头像信息修改成功");
				result.setResultCode(Constant.RESULT_CODE_SUCCESS);
			}else{
				result.setResultMsg("用户不存在");
			}
			if(!StringUtil.isEmpty(u.getHeadPic())){
				String visitPath=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
				u.setHeadPic(visitPath + u.getHeadPic());
             }
			result.setResultData(u);
		}else{
			result.setResultMsg(validateRes);
		}
		String msg = JSON.toJSONString(result);
		logger.debug("UserHeadPicBingdingServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	/**
	 * 得到图片和图片地址
	 * @param user
	 * @return
	 */
	private MerchantUser getUser(MerchantUser us,MerchantUser user) {
		String loginName=user.getLoginName();
		
		String savePath=SystemConfigUtil.getSingleProperty("userpic_save_path").getPropertyValue();
		//String visitPath=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
		
		String pic="/images/userpic/";
		
//		String savePath="E:\\";
//		String visitPath="E:\\";
		
		if(!StringUtil.isEmpty(user.getHeadPic())){
			   String fileName=getFileName(loginName);
				if(generateImage(user.getHeadPic(),savePath +fileName)){
					us.setHeadPic(pic + fileName);
				}
		}
		return us;
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
	public String validateParam(MerchantUser user){
		if(user ==null){
			return "入参错误";
		}
		if(StringUtil.isEmpty(user.getLoginName())){
			return "用户账号不能为空";
		}
		if(StringUtil.isEmpty(user.getHeadPic())){
			return "用户头像不能为空";
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


	public MerchantUserService getMerchantUserService() {
		return merchantUserService;
	}

	public void setMerchantUserService(MerchantUserService merchantUserService) {
		this.merchantUserService = merchantUserService;
	}

	
}
