package com.odchina.micro.print;

import org.apache.log4j.Logger;
 
public class QueryOrderPrintAction {
	 
	private static final Logger logger = Logger.getLogger(QueryOrderPrintAction.class);
	private static final long serialVersionUID = 1L;
	
	/**
	 * 打印订单信息（宽纸 80）
	 * @param ob
	 * @param itemList
	 * @param out
	 * @param qrCodePath 二维码图片路径
	 * @param qrInfo 二维码介绍信息
	 * @return
	 *//*
	public static OutputStream installString4Order(OrderBase ob,List<OrderItem> itemList,OutputStream out,String qrCodePath,String qrInfo,boolean isKitchenPrint){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String title=ob.getMerchantName();
		
	    if (title==null) {
			title = "";
		}
		int kblength=(41-title.length()*2)/2;
		String kbStr="";
		for(int i=0;i<kblength;i++){
			kbStr+=" ";
		}
		String kbStr2=kbStr;
		
		title=kbStr+title;
	  try
        {   out.write(getGbk(" "+"\n"));
			out.write(setWH('3'));//字体放大两倍
			out.write(getGbk(title));
			out.write(setWH('1'));//字体改回默认大小
			out.write(getGbk(" "+"\n"));
			out.write(getGbk(" "+"\n"));
			out.write(setWH('1'));//字体改回默认大小
			//订单编号
			out.write(getGbk("订单编号："));
			out.write(getGbk(ob.getOrderNumber()!=null?ob.getOrderNumber():""));
			out.write(getGbk(" \n"));
			out.write(getGbk("==========================================\r\n"));
			out.write(getGbk("品名                           数量  小计\r\n"));
			out.write(getGbk("------------------------------------------\r\n"));
			double oldPrice=0;
			out.write(setWH('3'));//字体放大两倍
			for(OrderItem oi:itemList){
				  oldPrice+=oi.getPrice()*oi.getNum();
				if(oi.getGoodsName().length()<15){
					String goodsName=oi.getGoodsName();
					kblength=33-StringUtil.length(goodsName)-(oi.getNum()+"").length();
					kbStr="";
					for(int i=0;i<kblength;i++){
						kbStr+=" ";
					}
					String priceSpace="";
					int spaceLength=7-(oi.getPrice()*oi.getNum()+"").length();
					for (int i = 0; i < spaceLength; i++) {
						priceSpace+=" ";
					}
				    out.write(getGbk(oi.getGoodsName()+kbStr+"X"+oi.getNum()+priceSpace+oi.getPrice()*oi.getNum()+"\r\n"));
				 
				}else{
					String goodsName=oi.getGoodsName();
					String goodsName1=goodsName.substring(0,15);
					String goodsName2=goodsName.substring(15);
					out.write(getGbk(goodsName1+"\r\n"));
					while(goodsName2.length()>15){
						goodsName1=goodsName2.substring(0,15);
						String str=goodsName2.substring(15);
						goodsName2=str;
						out.write(getGbk(goodsName1+"\r\n"));
					}
					kblength=33-StringUtil.length(goodsName2)-(oi.getNum()+"").length();
					kbStr="";
					for(int i=0;i<kblength;i++){
						kbStr+=" ";
					}
					
					String priceSpace="";
					int spaceLength=7-(oi.getPrice()*oi.getNum()+"").length();
					for (int i = 0; i < spaceLength; i++) {
						priceSpace+=" ";
					}
				   out.write(getGbk(goodsName2+kbStr+"X"+oi.getNum()+priceSpace+oi.getPrice()*oi.getNum()+"\r\n"));
	 
				}
			}

			
			
			out.write(setWH('1'));//字体改回默认大小
			out.write(getGbk("-----------------------------------------"+"\r\n"));
			out.write(getGbk("订单备注："+ob.getOrderRemark()+"\r\n"));
			out.write(getGbk("-----------------------------------------"+"\r\n"));
			DecimalFormat df=new DecimalFormat("0.00"); 
			
			Double totalPrice= ob.getTotalPrice()==null?0:ob.getTotalPrice();
			Double freightPrice=ob.getFreightPrice()==null?0:ob.getFreightPrice();
			Double orderPrice=ob.getOrderPrice()==null?0:ob.getOrderPrice();
			
		 
			out.write(getGbk("原单合计：                       "+df.format(totalPrice)+"\r\n"));
			out.write(getGbk("邮费金额：                       "+df.format(freightPrice)+"\r\n"));
	 
			out.write(getGbk("应收金额：                     "));
			out.write(setWH('3'));//字体横向纵向都放大两倍
			out.write(getGbk(df.format(orderPrice)+""));
			out.write(setWH('1'));//字体改回默认大小
			out.write(getGbk("元\r\n"));
	 
			out.write(getGbk("========================================="+"\r\n"));
			out.write(getGbk("下单时间："+sdf.format(ob.getOrderDate())+"\r\n"));
			//后厨打印机不打印这块内容
			if (!isKitchenPrint) {
				out.write(getGbk(" \n"));
				out.write(getGbk("         ******谢谢惠顾******"+"\r\n"));
				out.write(getGbk(" \n"));
				try
				{
				  if(qrCodePath!=null&&!"".equals(qrCodePath))
				  {   
					  //二维码图片打印
					  byte[] b=QRCodePrintUtil.getQrcodePrintInfo2(qrCodePath);
					  out.write(b);
					  out.write(getGbk(" \n"));
					  //二维码介绍信息打印
					  if(qrInfo!=null&&!"".equals(qrInfo))
					  {
						  int dsclength=(35-qrInfo.length()*2)/2;
							String dscStr="";
							for(int i=0;i<dsclength;i++){
								dscStr+=" ";
							}
						  out.write(getGbk(dscStr+qrInfo));
					 }
				  }
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			
			out.write(getGbk(" \n"));
			out.write(getGbk(" \n"));
			out.write(getGbk(" \n"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return out;
	}
	*/
	 
	/**
	 * 
	 * 打印订单信息（窄纸 58）
	 * @param ob
	 * @param itemList
	 * @param out
	 * @param qrCodePath
	 * @param qrInfo
	 * @param isKitchenPrint
	 * @return
	 */
	/*public static OutputStream installString4Order2(OrderBase ob,List<OrderItem> itemList,OutputStream out,String qrCodePath,String qrInfo,boolean isKitchenPrint){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String title=ob.getMerchantName();
		
	    if (title==null) {
			title = "";
		}
		int kblength=(32-title.length()*2)/2;
		String kbStr="";
		for(int i=0;i<kblength;i++){
			kbStr+=" ";
		}
		String kbStr2=kbStr;
		
		title=kbStr+title;
	  try
        {   out.write(getGbk(" "+"\n"));
			out.write(setWH('3'));//字体放大两倍
			out.write(getGbk(title));
			out.write(setWH('1'));//字体改回默认大小
			out.write(getGbk(" "+"\n"));
			out.write(getGbk(" "+"\n"));
			out.write(setWH('1'));//字体放大两倍
			 
			out.write(getGbk("订单编号："));
			out.write(getGbk(ob.getOrderNumber()!=null?ob.getOrderNumber():""));
			out.write(getGbk(" \n"));
			out.write(getGbk("===============================\r\n"));
			out.write(getGbk("品名                  数量  小计\r\n"));
			out.write(getGbk("-------------------------------\r\n"));
			double oldPrice=0;
			out.write(setWH('3'));//字体放大两倍
			for(OrderItem oi:itemList){
				  oldPrice+=oi.getPrice()*oi.getNum();
				if(oi.getGoodsName().length()<11){
					String goodsName=oi.getGoodsName();
					kblength=24-StringUtil.length(goodsName)-(oi.getNum()+"").length();
					kbStr="";
					for(int i=0;i<kblength;i++){
						kbStr+=" ";
					}
					String priceSpace="";
					int spaceLength=7-(oi.getPrice()*oi.getNum()+"").length();
					for (int i = 0; i < spaceLength; i++) {
						priceSpace+=" ";
					}
				    out.write(getGbk(oi.getGoodsName()+kbStr+"X"+oi.getNum()+priceSpace+oi.getPrice()*oi.getNum()+"\r\n"));
				 
				}else{
					String goodsName=oi.getGoodsName();
					String goodsName1=goodsName.substring(0,10);
					String goodsName2=goodsName.substring(10);
					out.write(getGbk(goodsName1+"\r\n"));
					while(goodsName2.length()>10){
						goodsName1=goodsName2.substring(0,10);
						String str=goodsName2.substring(10);
						goodsName2=str;
						out.write(getGbk(goodsName1+"\r\n"));
					}
					kblength=24-StringUtil.length(goodsName2)-(oi.getNum()+"").length();
					kbStr="";
					for(int i=0;i<kblength;i++){
						kbStr+=" ";
					}
					
					String priceSpace="";
					int spaceLength=7-(oi.getPrice()*oi.getNum()+"").length();
					for (int i = 0; i < spaceLength; i++) {
						priceSpace+=" ";
					}
				   out.write(getGbk(goodsName2+kbStr+"X"+oi.getNum()+priceSpace+oi.getPrice()*oi.getNum()+"\r\n"));
	 
				}
			}
			
			out.write(setWH('1'));//字体改回默认大小
			out.write(getGbk("-------------------------------"+"\r\n"));
			out.write(getGbk("订单备注："+ob.getOrderRemark()+"\r\n"));
			out.write(getGbk("-------------------------------"+"\r\n"));
			DecimalFormat df=new DecimalFormat("0.00"); 
			
			Double totalPrice= ob.getTotalPrice()==null?0:ob.getTotalPrice();
			Double freightPrice=ob.getFreightPrice()==null?0:ob.getFreightPrice();
			Double orderPrice=ob.getOrderPrice()==null?0:ob.getOrderPrice();
			
		 
			out.write(getGbk("原单合计：            "+df.format(totalPrice)+"\r\n"));
			out.write(getGbk("邮费金额：            "+df.format(freightPrice)+"\r\n"));
	 
			out.write(getGbk("应收金额：            "));
			out.write(setWH('3'));//字体横向纵向都放大两倍
			out.write(getGbk(df.format(orderPrice)+""));
			out.write(setWH('1')); 
			out.write(getGbk("元\r\n"));
			out.write(getGbk("==============================="+"\r\n"));
			out.write(getGbk("下单时间："+sdf.format(ob.getOrderDate())+"\r\n"));
		 
			if (!isKitchenPrint) {
				out.write(getGbk(" \n"));
				out.write(getGbk("      ******谢谢惠顾******"+"\r\n"));
				out.write(getGbk(" \n"));
				try
				{
				  if(qrCodePath!=null&&!"".equals(qrCodePath))
				  {   
					  byte[] b=QRCodePrintUtil.getQrcodePrintInfo2(qrCodePath);
					  out.write(b);
					  out.write(getGbk(" \n"));
					  if(qrInfo!=null&&!"".equals(qrInfo))
					  {
						  int dsclength=(35-qrInfo.length()*2)/2;
							String dscStr="";
							for(int i=0;i<dsclength;i++){
								dscStr+=" ";
							}
						  out.write(getGbk(dscStr+qrInfo));
					 }
				  }
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
			out.write(getGbk(" \n"));
			out.write(getGbk(" \n"));
			out.write(getGbk(" \n"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return out;
	}
	*/
	
	
	
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
	
	 
	/**
	 * 设置对齐方式
	 * @param dist
	 * @return
	 */
	public static byte[] setAlignCenter(char dist) {
	        byte[] returnText = new byte[3]; // 对齐 ESC a
	        returnText[0] = 0x1B;
	        returnText[1] = 0x61;
	
	        switch (dist) // 1-左对齐；2-居中对齐；3-右对齐
	        {
	        case '2':
	                returnText[2] = 0x01;
	                break;
	        case '3':
	                returnText[2] = 0x02;
	                break;
	        default:
	                returnText[2] = 0x00;
	                break;
	        }
	        return returnText;
	}
	
	/**
	 * 设置字体加粗
	 * @param dist
	 * @return
	 */
	public static byte[] setBold(boolean dist) {
	        byte[] returnText = new byte[3]; // 加粗 ESC E
	        returnText[0] = 0x1B;
	        returnText[1] = 0x45;
	
	        if (dist) {
	                returnText[2] = 0x01; // 表示加粗
	        } else {
	                returnText[2] = 0x00;
	        }
	        return returnText;
	}
}
