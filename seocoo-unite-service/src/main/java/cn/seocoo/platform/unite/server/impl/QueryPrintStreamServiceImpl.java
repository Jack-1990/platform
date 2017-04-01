package cn.seocoo.platform.unite.server.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.common.util.StringUtil;
import cn.seocoo.platform.model.Merchant;
import cn.seocoo.platform.model.Order;
import cn.seocoo.platform.service.merchant.inf.MerchantService;
import cn.seocoo.platform.service.order.inf.OrderService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;
import com.google.zxing.common.StringUtils;

public class QueryPrintStreamServiceImpl implements Service{
	private OrderService orderService;
	private MerchantService merchantService;
	@Override
	public Object sevice(String param) {
		Order order = JSONObject.parseObject(param, Order.class);
		String validateRes = validate(order);
		order.setStatus(1);
		Result reslut=new Result();
		byte[] orderByte = null;
		if ("ok".equals(validateRes)){
			order=orderService.queryOrder(order);
			if(order!=null){
				//获取商户名称
				Merchant merchant =new Merchant();
				merchant.setOutMchntId(order.getMerchantCode());
				merchant=merchantService.queryMerchant(merchant);
					String title = null;
				if(merchant!=null){
					title=merchant.getMchntName();
				}
				if ("weixin".equals(order.getPayChannel())) {
					order.setPayChannel("微信");
				}
				if ("zhifubao".equals(order.getPayChannel())) {
					order.setPayChannel("支付宝");
				}
				orderByte = installString4Order(order,title);
				reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
				try {
					reslut.setResultData(new String(orderByte,"gbk"));
				} catch (UnsupportedEncodingException e) {
				}
			}else{
				reslut.setResultCode(Constant.RESULT_CODE_FAIL);	
			}
		}
		else{
			reslut.setResultCode(Constant.RESULT_CODE_FAIL);
			reslut.setResultData(validateRes);
		}
		String res = JSON.toJSONString(reslut);
		return res;
	}
	// 验证参数
	public String validate(Order order)
	{
		if(order==null){
			return "入参错误";
		}else if(StringUtil.isEmpty(order.getMerchantCode())){
			return "商户编码不能为空";
			
		}else if(StringUtil.isEmpty(order.getOrderNumber()))
		{
			return "订单号不能为空";
		}
		return "ok";
	}
	public static byte[] installString4Order(Order order,String title){
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		DecimalFormat df=new DecimalFormat("0.00"); 
		if (title==null) {
			title = "";
		}
		int kblength=(24-title.length()*2)/2;
		String kbStr="";
		for(int i=0;i<kblength;i++){
			kbStr+=" ";
		}
		title=kbStr+title;
		try {
			
			out.write(getGbk(" "+"\n"));
			out.write(setWH('4'));//字体放大两倍
			out.write(getGbk(title+"\r\n"));
			out.write(setWH('3'));
			out.write(getGbk(" "+"\n"));
			out.write(getGbk("订单编号："));
			out.write(getGbk(order.getOrderNumber()!=null?order.getOrderNumber():""));
			out.write(getGbk(" \n"));
			out.write(setWH('1'));//字体改回默认大小
			out.write(getGbk("---------------------------------------------\r\n"));
			out.write(setWH('3'));
			out.write(getGbk("实收金额：                          "+df.format(Double.valueOf(order.getAmount())/100.0)+"元\r\n"));
			out.write(getGbk("付款方式：                          "+order.getPayChannel()+"\r\n"));
			out.write(setWH('1'));//字体改回默认大小
			out.write(getGbk("---------------------------------------------\r\n"));
			out.write(setWH('3'));
			out.write(getGbk("交易时间："+sdf.format(order.getTransTime())+"\r\n"));
			out.write(getGbk(" \n"));
			out.write(getGbk(" \n"));
			out.write(getGbk(" \n"));
			//4个换行
			out.write(getGbk("\r\n"));
			out.write(getGbk("\r\n"));
			out.write(getGbk("\r\n"));
			out.write(getGbk("\r\n"));
			byte[] carvePaper = new byte[3]; // GS V m 切纸
			carvePaper[0] = 0x1D;
			carvePaper[1] = 0x56;
			carvePaper[2] = 0x01;
			out.write(carvePaper);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
		
	}
	/**
	 * 将字符串转化成byte数组
	 * @param stText
	 * @return
	 */
	public static byte[] getGbk(String stText) {
        byte[] returnText = null;
        try {
            returnText = stText.getBytes("GBK"); // 必须放在try内才可以
        } catch (Exception ex) {
            ;
        }
        return returnText;
	}
	
	/**
	 * 设置字体倍高倍宽
	 * @param dist
	 * @return
	 */
	public static byte[] setWH(char dist) {
	        byte[] returnText = new byte[3]; // GS ! 11H 倍宽倍高
	        returnText[0] = 0x1D;
	        returnText[1] = 0x21;
	
	        switch (dist) // 1-无；2-倍宽；3-倍高； 4-倍宽倍高
	        {
	        case '2':
	                returnText[2] = 0x10;
	                break;
	        case '3':
	                returnText[2] = 0x01;
	                break;
	        case '4':
	                returnText[2] = 0x11;
	                break;
	        default:
	                returnText[2] = 0x00;
	                break;
	        }
	
	        return returnText;
	}
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}
	public OrderService getOrderService() {
		return orderService;
	}
	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}
	public MerchantService getMerchantService() {
		return merchantService;
	}

}
