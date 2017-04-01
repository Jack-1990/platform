package cn.seocoo.platform.wap;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import sun.misc.BASE64Encoder;
import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.QRCodeUtil;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.AllLevelRate;
import cn.seocoo.platform.model.PushMsg;
import cn.seocoo.platform.model.Upgrade;
import cn.seocoo.platform.service.allLevelRate.inf.AllLevelRateService;
import cn.seocoo.platform.service.pushMsg.inf.PushMsgService;
import cn.seocoo.platform.service.upgrade.inf.UpgradeService;

public class WapAction extends BaseAction{
	private static final long serialVersionUID = 1L;
	private PushMsgService pushMsgService ;
	private AllLevelRateService allLevelRateService;
	private UpgradeService upgradeService;
	
	/**
	 * 显示发送 的消息 页面
	 * @return
	 */
	public String showPushMsgToUser(){
		int id =  0;
		if (request.getParameter("id") != null && ! "".equals(request.getParameter("id")))
		{
			id = Integer.valueOf(request.getParameter("id")) ;
		}
		PushMsg pushMsg = new PushMsg();
		pushMsg.setId(id);
		List<PushMsg> queryPushMsgList = pushMsgService.queryPushMsgList(pushMsg);
		if (queryPushMsgList != null && queryPushMsgList.size()>0)
		{
			pushMsg = queryPushMsgList.get(0);
		}
		request.setAttribute("pushMsg", pushMsg);
		return "showPushMsgToUser";
	}

	
	
	
	/**
	 * 所有的费率列表,我的费率
	 * @return
	 */
	public  String levelRate(){
		String levelCode=request.getParameter("levelCode");
		String path=SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();

		AllLevelRate all= new AllLevelRate();
		all.setLevelCode(levelCode);
		List<AllLevelRate> levelList=allLevelRateService.queryLevelList(all);
		//返回list
		List<AllLevelRate> list = new ArrayList<AllLevelRate>();
		for(AllLevelRate e:levelList){
			AllLevelRate rate=new AllLevelRate();
			rate.setLevelCode(e.getLevelCode());
			List<AllLevelRate> rateList=allLevelRateService.queryAllLevelRateList(rate);
			
			rate=e;
			rate.setLevelList(rateList);
			list.add(rate);
		}
		request.setAttribute("rateList", list);
		request.setAttribute("path", path);
		return "allLevelRate";
	}
	
	
	/**
	 * 如何下载app
	 * @return
	 */
	public  String upload(){
		return "upload";
	}
	
	
	/**
	 * 如何注册
	 * @return
	 */
	public  String merchantRegister(){
		return "toMerchantRegister";
	}
	
	
	/**
	 * 如何快速规范的通过实名验证
	 * @return
	 */
	public  String validateName(){
		return "validateName";
	}
	
	/**
	 * 如何支付
	 * @return
	 */
	public  String topay(){
		return "topay";
	}
	
	
	/**
	 * 如何结算
	 * @return
	 */
	public  String settleAccount(){
		return "settleAccount";
	}
	
	/**
	 * 如何结算查询账单
	 * @return
	 */
	public  String queryBill(){
		return "queryBill";
	}
	
	
	/**
	 * 如何分享推广
	 * @return
	 */
	public  String spread(){
		return "spread";
	}
	
	/**
	 * 如何修改密码
	 * @return
	 */
	public  String modifyPassword(){
		return "modifyPassword";
	}
	
	
	/**
	 * 二维码分享
	 * @return
	 */
	public String qrcodeSpread(){
		String loginName = request.getParameter("userCode");
		String path=SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
		String url=path + "/merchantRegister!merchantRegister.do" ;
		if(!StringUtil.isEmpty(loginName)){
			url +="?parentUser=" +loginName;
		}
		BufferedImage image = null;
		byte data[] = null;
        try {
        	String picpath = SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
            image = QRCodeUtil.createImage(url, picpath+"/images/paylogo.png", true);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "png", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            int i = is.available(); // 得到文件大小
            data = new byte[i];
            is.read(data); // 读数据
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String encode = encoder.encode(data);
        
		request.setAttribute("image", encode);
		return "qrcodeSpread";
	}
	
	/**
	 * h5静态页面
	 * @return
	 */
	public String htmlPage(){
		String loginName = request.getParameter("userCode");
		String path=SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
		String url=path + "/merchantRegister!merchantRegister.do" ;
		if(!StringUtil.isEmpty(loginName)){
			url +="?parentUser=" +loginName;
		}
		request.setAttribute("url", url);
		return "htmlPage";
	}
	
	/**
	 * 链接分享
	 * @return
	 */
	public String shareLink(){
		String loginName = request.getParameter("userCode");
		String path=SystemConfigUtil.getSingleProperty("manage_visit_path").getPropertyValue();
		String url=path + "/merchantRegister!merchantRegister.do" ;
		BASE64Encoder encoder = new BASE64Encoder();
		String encode = "";
		if(!StringUtil.isEmpty(loginName)){
			url +="?parentUser=" +loginName;
			BufferedImage image = null;
			byte data[] = null;
	        try {
	        	String picpath = SystemConfigUtil.getSingleProperty("userpic_visit_path").getPropertyValue();
	            image = QRCodeUtil.createImage(url, picpath+"/images/paylogo.png", true);
	            ByteArrayOutputStream os = new ByteArrayOutputStream();
	            ImageIO.write(image, "png", os);
	            InputStream is = new ByteArrayInputStream(os.toByteArray());
	            int i = is.available(); // 得到文件大小
	            data = new byte[i];
	            is.read(data); // 读数据
	            is.close();

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        encode = encoder.encode(data);
		}
		request.setAttribute("image", encode);
		return "shareLink";
	}
	
	
	/**
	 * 如何修改密码
	 * @return
	 */
	public  String aboutUs(){
		return "aboutUs";
	}
	
	public PushMsgService getPushMsgService()
	{
		return pushMsgService;
	}

	public void setPushMsgService(PushMsgService pushMsgService)
	{
		this.pushMsgService = pushMsgService;
	}

	public AllLevelRateService getAllLevelRateService() {
		return allLevelRateService;
	}

	public void setAllLevelRateService(AllLevelRateService allLevelRateService) {
		this.allLevelRateService = allLevelRateService;
	}

	public UpgradeService getUpgradeService() {
		return upgradeService;
	}

	public void setUpgradeService(UpgradeService upgradeService) {
		this.upgradeService = upgradeService;
	}
	
	
	
	
}
