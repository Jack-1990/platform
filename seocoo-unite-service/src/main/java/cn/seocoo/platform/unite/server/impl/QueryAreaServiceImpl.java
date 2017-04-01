package cn.seocoo.platform.unite.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.Area;
import cn.seocoo.platform.model.AreaInfo;
import cn.seocoo.platform.service.area.inf.AreaService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;

import com.alibaba.fastjson.JSON;

public class QueryAreaServiceImpl implements Service{
	private static final Logger logger = Logger.getLogger(QueryAreaServiceImpl.class);
	private AreaService areaService;

	/**
	 * 地区省市列表返回，无需参数
	 */
	public Object sevice(String param) {
		logger.debug("QueryAreaServiceImpl 请求报文 :"+param);
		Result reslut=new Result();
		
		//返回的list
		List<AreaInfo> areaList=new ArrayList<AreaInfo>();
		//获取省的列表
		Area area=new Area();
		area.setPcode(Constant.DIMDIC_PCODE);
		area.setLevel(Constant.COMMON_STATUS);
		List<AreaInfo> proveList=areaService.queryAreaInfoList(area);
		
		for(int i=0;i<proveList.size();i++){
			//获取省对应市 的列表
			Area aa=new Area();
			aa.setPcode(proveList.get(i).getCode());
			List<AreaInfo>  cityList=areaService.queryAreaInfoList(aa);
			
			//返回
			AreaInfo aInfo=new AreaInfo();
			aInfo=proveList.get(i);
			if(cityList !=null && cityList.size()>0){
				aInfo.setCityList(cityList);
			}
			areaList.add(aInfo);
		}
		
		reslut.setResultCode(Constant.RESULT_CODE_SUCCESS);
		reslut.setResultMsg("获取成功");
		reslut.setResultData(areaList);
		
		String msg = JSON.toJSONString(reslut);
		logger.debug("QueryAreaServiceImpl 返回报文 :"+msg);
		return msg;
	}

	
	public AreaService getAreaService() {
		return areaService;
	}

	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
}
