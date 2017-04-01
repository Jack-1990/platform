package cn.seocoo.platform.model;

import java.util.List;

/** 
 * @ClassName: PrintObj 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author lenovo
 * @date 2016-9-7 下午4:01:15 
 *  封装打印数据对象
*/ 
public class PrintObj {

	private String printerIp;//打印机ip
	private int printerPort;//打印机端口
	private byte[] printData;//打印数据
	private List<Order> orderList;
	
	public String getPrinterIp() {
		return printerIp;
	}
	public void setPrinterIp(String printerIp) {
		this.printerIp = printerIp;
	}
	public int getPrinterPort() {
		return printerPort;
	}
	public void setPrinterPort(int printerPort) {
		this.printerPort = printerPort;
	}
	
	public byte[] getPrintData() {
		return printData;
	}
	public void setPrintData(byte[] printData) {
		this.printData = printData;
	}
	public List<Order> getOrderList() {
		return orderList;
	}
	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}
	
}
