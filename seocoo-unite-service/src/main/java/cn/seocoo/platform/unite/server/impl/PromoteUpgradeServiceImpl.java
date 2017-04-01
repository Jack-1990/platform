package cn.seocoo.platform.unite.server.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.odchina.micro.util.DateUtils;
import com.odchina.micro.util.StringTools;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.Upgrade;
import cn.seocoo.platform.model.UserUpgrade;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.service.upgrade.inf.UpgradeService;
import cn.seocoo.platform.service.userUpgrade.inf.UserUpgradeService;
import cn.seocoo.platform.service.userUpgradeOrder.inf.UserUpgradeOrderService;
import cn.seocoo.platform.unite.Result;

/**
 * 推广升级接口
 */
public class PromoteUpgradeServiceImpl implements Service {
	private Logger logger = Logger.getLogger(this.getClass());
	private UserUpgradeOrderService userUpgradeOrderService;
	private UpgradeService upgradeService;
	private UserUpgradeService userUpgradeService;
	private MerchantInfoService merchantInfoService;

	@Override
	public Object sevice(String param) {
		logger.debug("SubmitOrderServiceImpl提交订单请求参数：" + param);
		Result reslut = new Result();
		// 参数验证
		String validateRes = validateParam(param);

		UserUpgrade order = JSON.parseObject(param, UserUpgrade.class);
		reslut.setResultCode(Constant.RESULT_CODE_FAIL);

		if ("ok".equals(validateRes)) {
			String upgradeStatus = "FAIL";

			String merchantCode = order.getMerchantCode();
			String applyUser = order.getApplyUser();
			String merchantName = order.getMerchantName();

			//String attrValue = order.getAttrValue();// 当前用户发展对应的人数
			
			Map map =new HashMap();
			map.put("parentMerchantCode", merchantCode);
			map.put("certifyStatus", 2);
			Integer attrValue= merchantInfoService.queryMerchantInfoPageCount(map);// 当前用户发展对应的人数
			        attrValue=attrValue==null?0:attrValue;

			// 检验是否已经发起了申请
			Upgrade ue = new Upgrade();
			ue.setId(order.getUpGrade_id());
			List<Upgrade> ueList = upgradeService.queryUpgradeList(ue);
			if (ueList != null && ueList.size() > 0) {
				ue = ueList.get(0);
				String num = ue.getAttrValue();// 升级规则中需要发展的人员数
				if (attrValue.intValue() < Integer.parseInt(num)) {
					upgradeStatus = "belowGrade";// 您发展的人数不能满足当前升级申请条件
				} else {
					UserUpgrade uug = new UserUpgrade();
					uug.setFromLevelCode(ue.getFromLevelCode());
					uug.setToLevelCode(ue.getToLevelCode());
					uug.setMerchantCode(order.getMerchantCode());

					// 查询当前的商户有没有当前的等级的升级请求
					List<UserUpgrade> uugList = userUpgradeService.queryUserUpgradeInfos(uug);
					if (uugList != null && uugList.size() > 0) {
						upgradeStatus = "FAIL";// 当前的商户已有当前的等级的升级请求
					} else {
						upgradeStatus = "OK";
					}
				}
			}

			if ("OK".equals(upgradeStatus)) {// 提交推广升级的请求
				Upgrade grade = ueList.get(0);
				UserUpgrade userUpgrade = new UserUpgrade();
				String applyCode = StringTools.getRandomString(10);
				userUpgrade.setApplyCode(applyCode);
				userUpgrade.setFromLevelCode(grade.getFromLevelCode());
				userUpgrade.setToLevelCode(grade.getToLevelCode());
				userUpgrade.setAttrValue(grade.getAttrValue());
				userUpgrade.setAttrStyle(grade.getAttrStyle());
				userUpgrade.setMerchantCode(merchantCode);
				userUpgrade.setDescription(grade.getDescription());
				userUpgrade.setApplyUser(applyUser);
				userUpgrade.setApplyTime(new Date());
				userUpgrade.setApplyStatus(0);
				userUpgrade.setMerchantName(merchantName);

				userUpgradeService.saveUserUpgrade(userUpgrade);
				reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				reslut.setResultMsg("您的升级等级申请已经提交成功");

			} else if ("belowGrade".equals(upgradeStatus)) {
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultMsg("您发展的会员人数不能满足当前升级申请的条件");
			} else {
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultMsg("您的升级请求正在处理中，请耐心等待");
			}
		} else {
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultMsg(validateRes);
		}
		String res = JSON.toJSONString(reslut);
		return res;
	}

	/**
	 * @return 生成订单号
	 */
	private String generateOrderNumber() {
		return new StringBuffer().append(Constant.PREFIX_UPORDER)
				.append(DateUtils.toString(new Date(), Constant.WECHATDATEFORMAT))
				.append((int) (Math.random() * 9000 + 1000) + "").toString();
	}

	// 验证参数
	public String validateParam(String param) {
		UserUpgrade order = JSON.parseObject(param, UserUpgrade.class);
		if (order == null) {
			return "param is null";
		}
		if (order.getMerchantCode() == null || "".equals(order.getMerchantCode())) {
			return "商家编码不能为空";
		}
		if (order.getMerchantName() == null || "".equals(order.getMerchantName())) {
			return "商户名不能为空";
		}
		if (order.getApplyUser() == null || "".equals(order.getApplyUser())) {
			return "升级用户名不能为空";
		}
		if (order.getUpGrade_id() == null || "".equals(order.getUpGrade_id())) {
			return "升级规则id不能为空";
		}
		if (order.getAttrValue() == null || "".equals(order.getAttrValue())) {
			return "当前商户升级发展的人数不能为空";
		}

		return "ok";
	}

	public UserUpgradeOrderService getUserUpgradeOrderService() {
		return userUpgradeOrderService;
	}

	public void setUserUpgradeOrderService(UserUpgradeOrderService userUpgradeOrderService) {
		this.userUpgradeOrderService = userUpgradeOrderService;
	}

	public UpgradeService getUpgradeService() {
		return upgradeService;
	}

	public void setUpgradeService(UpgradeService upgradeService) {
		this.upgradeService = upgradeService;
	}

	public UserUpgradeService getUserUpgradeService() {
		return userUpgradeService;
	}

	public void setUserUpgradeService(UserUpgradeService userUpgradeService) {
		this.userUpgradeService = userUpgradeService;
	}

	public MerchantInfoService getMerchantInfoService() {
		return merchantInfoService;
	}

	public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
		this.merchantInfoService = merchantInfoService;
	}

}