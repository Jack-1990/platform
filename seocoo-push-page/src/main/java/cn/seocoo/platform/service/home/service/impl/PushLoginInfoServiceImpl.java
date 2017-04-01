package cn.seocoo.platform.service.home.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.constant.PushConstant;
import cn.seocoo.platform.common.util.BapUtil;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.SystemConfigUtil;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.model.PushMsg;
import cn.seocoo.platform.push.AppPushMsg;
import cn.seocoo.platform.service.home.service.inf.Service;
import cn.seocoo.platform.service.server.ConnectionPool;
import cn.seocoo.platform.service.server.MsgTcpInfo;
import cn.seocoo.platform.unite.Result;
import javapns.devices.Device;
import javapns.devices.implementations.basic.BasicDevice;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

public class PushLoginInfoServiceImpl implements Service {
	private static final Logger logger = Logger.getLogger(PushLoginInfoServiceImpl.class);

	@Override
	public Object sevice(String param) {
		logger.debug(param);
		AppPushMsg pushMsg = JSONObject.parseObject(param, AppPushMsg.class);
		Result reslut = new Result();
		// 目标转小写
		pushMsg.setTarget(pushMsg.getTarget().toLowerCase());
		logger.debug("push目标=========" + pushMsg.getTarget() + ",消息类型==========" + pushMsg.getMsgType() + ",消息:"
				+ pushMsg.getMsg());

		// 获取目标信息
		 MerchantUser userInfo = handlerUserInfo(pushMsg.getTarget(), pushMsg.getMerchantCode());
		if (userInfo == null) {
			logger.debug("push目标=========" + pushMsg.getTarget() + ",消息类型==========" + pushMsg.getMsgType() + ",消息:"
					+ pushMsg.getMsg() + ",异常:未找到对应的员工信息!");
		} else {
			if (PushConstant.TERMINAL_ANDROID.equals(userInfo.getTerminal())) {
				// android 消息推送
				androidPush(pushMsg, reslut, userInfo);
			} else if (PushConstant.TERMINAL_IOS.equals(userInfo.getTerminal())) {
				// ios 消息推送
				reslut = iosPush(pushMsg, userInfo);
			}
		}

		String msg = JSON.toJSONString(reslut);
		return msg;
	}

	/**
	 * 获取用户对象信息
	 * 
	 * @param target
	 * @return
	 */
	private MerchantUser handlerUserInfo(String target, String merchantCode) {
		// TODO Auto-generated method stub
		MerchantUser userInfo = new MerchantUser();
		userInfo.setLoginName(target);
		userInfo.setMerchantCode(merchantCode);
		String param = JSONObject.toJSONString(userInfo);
		String msg = BapUtil.generateRequestMessage(param, "waiterInfo");
		String res = BapUtil.httpSendJson(SystemConfigUtil.getSingleProperty("interface_url").getPropertyValue(), msg);
		Result reslut = BapUtil.parseRes(res);
		MerchantUser user = JSONObject.parseObject(reslut.getResultData().toString(), MerchantUser.class);
		return user;
	}

	/**
	 * Android 消息推送
	 * 
	 * @param pushMsg
	 * @return
	 */
	public Result androidPush(AppPushMsg pushMsg, Result reslut, MerchantUser userInfo) {
		pushMsg.setTarget(userInfo.getDeviceToken());
		MsgTcpInfo target = ConnectionPool.tcpPools.get(pushMsg.getTarget());
		if (target == null) {
			// 对方不在线,加到消息队列
			ConnectionPool.savePushMsg(pushMsg);
			// 返回处理结果
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			logger.debug(pushMsg.getTarget() + " 目标不在线!");
			reslut.setResultMsg(pushMsg.getTarget() + " 目标不在线!");
		} else {
			logger.debug("推送目标信息:" + target.getS());
			target.send(pushMsg);
			reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
			reslut.setResultMsg("发送成功!");
		}
		return reslut;
	}

	/**
	 * ios消息推送
	 * 
	 * @param pushMsg
	 * @return
	 */
	public Result iosPush(AppPushMsg pushMsg, MerchantUser userInfo) {

		Result reslut = new Result();
		if (!StringUtils.isEmpty(userInfo.getDeviceToken())) {
			int badge = 1;// 图标小红圈的数值
			String sound = "default";// 铃音

			List<String> tokens = new ArrayList<String>();
			tokens.add(userInfo.getDeviceToken());
			// String certificatePath =
			// this.getClass().getResource("/").getPath()+"developeMent0808.p12";
			String certificatePath = this.getClass().getResource("/").getPath() + "ProductPush.p12";
			String certificatePassword = "123456";// 此处注意导出的证书密码不能为空因为空密码会报错
			boolean sendCount = true;
			try {
				PushNotificationPayload payLoad = new PushNotificationPayload();
				payLoad.addAlert(pushMsg.getMsg()); // 消息内容
				payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
				if (!StringUtils.isEmpty(sound)) {
					payLoad.addSound(sound);// 铃音
				}
				PushNotificationManager pushManager = new PushNotificationManager();
				// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
				pushManager.initializeConnection(
						new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, true));
				List<PushedNotification> notifications = new ArrayList<PushedNotification>();
				// 发送push消息
				if (sendCount) {
					Device device = new BasicDevice();
					device.setToken(tokens.get(0));
					PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
					notifications.add(notification);
				} else {
					List<Device> device = new ArrayList<Device>();
					for (String token : tokens) {
						device.add(new BasicDevice(token));
					}
					notifications = pushManager.sendNotifications(payLoad, device);
				}
				// 返回apple 数据处理结果 successful>0 表示发送成功 failed>0 表示发送失败
				// List<PushedNotification> failedNotifications =
				// PushedNotification.findFailedNotifications(notifications);
				// List<PushedNotification> successfulNotifications =
				// PushedNotification.findSuccessfulNotifications(notifications);
				// int failed = failedNotifications.size();
				// int successful = successfulNotifications.size();
				pushManager.stopConnection();
				reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
			} catch (Exception e) {
				// e.printStackTrace();
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);
				reslut.setResultMsg(userInfo.getLoginName() + " send exception !");
			}

		} else {
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultMsg(userInfo.getLoginName() + " device token not found !");
		}
		return reslut;
	}

	public static void main(String[] args) throws Exception {
		String deviceToken = "24fddb963e0039f8c3dbd082ff990dadbce9b6cbec2fbd411be245047f3272f4";
		String alert = "测试内容!";// push的内容
		int badge = 999;// 图标小红圈的数值
		String sound = "default";// 铃音

		List<String> tokens = new ArrayList<String>();
		tokens.add(deviceToken);
		String certificatePath = "D:/1111.p12";
		String certificatePassword = "123456";// 此处注意导出的证书密码不能为空因为空密码会报错
		boolean sendCount = true;

		try {
			PushNotificationPayload payLoad = new PushNotificationPayload();
			payLoad.addAlert(alert); // 消息内容
			payLoad.addBadge(badge); // iphone应用图标上小红圈上的数值
			// payLoad.addCustomAlertBody("噢嘿嘿~捏嘻嘻~哇咔咔咔~");

			if (!StringUtils.isEmpty(sound)) {
				payLoad.addSound(sound);// 铃音
			}
			PushNotificationManager pushManager = new PushNotificationManager();
			// true：表示的是产品发布推送服务 false：表示的是产品测试推送服务
			pushManager.initializeConnection(
					new AppleNotificationServerBasicImpl(certificatePath, certificatePassword, false));
			List<PushedNotification> notifications = new ArrayList<PushedNotification>();
			// 发送push消息
			if (sendCount) {
				Device device = new BasicDevice();
				device.setToken(tokens.get(0));
				PushedNotification notification = pushManager.sendNotification(device, payLoad, true);
				notifications.add(notification);
			} else {
				List<Device> device = new ArrayList<Device>();
				for (String token : tokens) {
					device.add(new BasicDevice(token));
				}
				notifications = pushManager.sendNotifications(payLoad, device);
			}
			List<PushedNotification> failedNotifications = PushedNotification.findFailedNotifications(notifications);
			List<PushedNotification> successfulNotifications = PushedNotification
					.findSuccessfulNotifications(notifications);
			int failed = failedNotifications.size();
			int successful = successfulNotifications.size();
			System.out.println(failedNotifications);
			System.out.println(successfulNotifications);
			pushManager.stopConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
