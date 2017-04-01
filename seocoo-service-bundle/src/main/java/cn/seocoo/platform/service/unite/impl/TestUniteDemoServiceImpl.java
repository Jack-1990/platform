package cn.seocoo.platform.service.unite.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

import cn.seocoo.platform.service.demo.inf.DemoService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Demo;
import cn.seocoo.platform.unite.Result;

public class TestUniteDemoServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(TestUniteDemoServiceImpl.class);
	private DemoService demoService;
	@Override
	public Object sevice(String param) {
		logger.info("param:"+param);
	
		List<Demo> demoList=demoService.queryAll();
		Result reslut=new Result();
		reslut.setResultCode("SUCCESS");
		reslut.setResultData(demoList);
		String msg=JSON.toJSONString(reslut);
		return msg;
	}
	public DemoService getDemoService() {
		return demoService;
	}
	public void setDemoService(DemoService demoService) {
		this.demoService = demoService;
	}

}
