package cn.seocoo.platform.admin;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.WithdrawCash;
import cn.seocoo.platform.service.withdrawCash.inf.WithdrawCashService;
import com.alibaba.fastjson.JSONObject;
import com.odchina.micro.shiro.ShiroUser;
import com.odchina.micro.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WithdrawCashAction extends BaseAction
{
	private static final long serialVersionUID = 1L;
	private WithdrawCashService withdrawCashService;

	public String withdrawCashPage()
	{

		return "withdrawCashPage";
	}

	/**
	 * 显示 提现申请列表
	 * 
	 * @return
	 */
	public String withdrawCashList()
	{
		String input = request.getParameter("input");
		String pageIndex = request.getParameter("pageIndex");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		Integer beginRow = (Integer.parseInt(pageIndex)) * Constant.PAGESIZE_TEN;
		Map map = new HashMap();
		map.put("beginRow", beginRow);
		map.put("pageSize", Constant.PAGESIZE_TEN);
		// 查询
		// 手机输入框 没有值则不查询
		if (StringUtils.isNotBlank(input))
		{
			map.put("loginName", input);
		}
		// 时间查询
		if (StringUtils.isNotBlank(startTime))
		{
			map.put("startDate", startTime + " 00:00:00");
			if (StringUtils.isNotBlank(endTime + " 23:59:59"))
			{
				map.put("endDate", endTime + " 23:59:59");
			} else
			{
				map.put("endDate", new Date());
			}
		}
		// 查询申请中的数据
		map.put("withdrawStatus", 0);
		List<WithdrawCash> withdrawCashPage = withdrawCashService.queryWithdrawCashPage(map);
		// 记录总数
		int totalMerchant = 0;
		totalMerchant = withdrawCashService.queryWithdrawCashPageCount(map);
		// 求余 获取分页总数
		int totalPage = 0;
		int remainder = totalMerchant % Constant.PAGESIZE_TEN;
		if (remainder == 0)
		{
			totalPage = totalMerchant / Constant.PAGESIZE_TEN;
		} else
		{
			totalPage = totalMerchant / Constant.PAGESIZE_TEN + 1;
		}

		request.setAttribute("withdrawCashPage", withdrawCashPage);
		request.setAttribute("totalPage", totalPage);

		return "withdrawCashList";
	}

	/**
	 * 提交已支付
	 * 
	 * @throws IOException
	 */
	public void submitPay() throws IOException
	{
		// 获取当前角色
		ShiroUser su = queryCurrentShiroUser();
		String withdrawCashId = request.getParameter("withdrawCashId");
		WithdrawCash withdrawCash = new WithdrawCash();
		JSONObject json = new JSONObject();
		try
		{
			withdrawCash.setId(withdrawCashId == "" ? 0 : Integer.valueOf(withdrawCashId));
			withdrawCash.setWithdrawStatus(1);
			withdrawCash.setOpreator(su.getLoginName());
			withdrawCash.setFinishTime(new Date());
			withdrawCashService.updateWithdrawCash(withdrawCash);
			json.put("resultCode", Constant.RESULT_CODE_SUCCESS);
		} catch (Exception e)
		{
			json.put("resultCode", Constant.RESULT_CODE_FAIL);
		}
		this.sendMessages(json.toJSONString());
	}

	/**
	 * 导出 xlm
	 */
	public void getOutMsg()
	{
		String input = request.getParameter("input");
		String pageIndex = request.getParameter("pageIndex");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String getOutFlag = request.getParameter("getOutFlag");

		Integer beginRow = (Integer.parseInt(pageIndex)) * Constant.PAGESIZE_TEN;
		Map map = new HashMap();
		map.put("beginRow", 0);
		map.put("pageSize", Integer.MAX_VALUE);
		if (StringUtils.equals(getOutFlag, "false"))
		{
			// 查询
			// 手机输入框 没有值则不查询
			if (StringUtils.isNotBlank(input))
			{
				map.put("loginName", input);
			}
			// 时间查询
			if (StringUtils.isNotBlank(startTime))
			{
				map.put("startDate", startTime + " 00:00:00");
				if (StringUtils.isNotBlank(endTime + " 23:59:59"))
				{
					map.put("endDate", endTime + " 23:59:59");
				} else
				{
					map.put("endDate", new Date());
				}
			}
		}
		// 查询申请中的数据
		map.put("withdrawStatus", 0);
		List<WithdrawCash> withdrawCashPage = withdrawCashService.queryWithdrawCashPage(map);
		
		exportExcel(response,withdrawCashPage);
	}
	
	/**
	 * 生成 Excel文件
	 * @param response
	 * @param list
	 */
	 public void exportExcel(HttpServletResponse response,List<WithdrawCash> list) 
	   {   
	    HSSFWorkbook wb = new HSSFWorkbook();  
	    HSSFSheet sheet = wb.createSheet("表");  
	    HSSFRow row = sheet.createRow((int) 0);  
	    HSSFCellStyle style = wb.createCellStyle();  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

	    HSSFCell cell = row.createCell((short) 0);  
	    cell.setCellValue("外部商户号");  
	    cell.setCellStyle(style);  
	    cell = row.createCell((short) 1);  
	    cell.setCellValue("商户名称");  
	    cell.setCellStyle(style);  
	    cell = row.createCell((short) 2);  
	    cell.setCellValue("体现金额(元)");  
	    cell.setCellStyle(style);  
	    cell = row.createCell((short) 3);  
	    cell.setCellValue("开户银行");  
	    cell.setCellStyle(style);
	    cell = row.createCell((short) 4);  
	    cell.setCellValue("银行卡号");  
	    cell.setCellStyle(style);
	    cell = row.createCell((short) 5);  
	    cell.setCellValue("手机号");  
	    cell.setCellStyle(style);
	    cell = row.createCell((short) 6);  
	    cell.setCellValue("申请时间");  
	    cell.setCellStyle(style);

	    for (int i = 0; i < list.size(); i++)  
	    {  
	        row = sheet.createRow((int) i + 1);  
	        WithdrawCash wc = (WithdrawCash) list.get(i); 
	        row.createCell((short) 0).setCellValue(wc.getMerchantCode()); 
	        row.createCell((short) 1).setCellValue(wc.getMerchantName());  
	        row.createCell((short) 2).setCellValue(wc.getWithdrawAmount()/100); 
	        row.createCell((short) 3).setCellValue(wc.getBankName());  
	        row.createCell((short) 4).setCellValue(wc.getCardNumber());
	        row.createCell((short) 5).setCellValue(wc.getLoginName());
	        row.createCell((short) 6).setCellValue(DateUtils.toString(wc.getApplyTime(), "yyyy-MM-dd HH:mm:ss")); 
	    }  
	    
	    sheet.autoSizeColumn((short)0); 
      sheet.autoSizeColumn((short)1); 
      sheet.autoSizeColumn((short)2); 
      sheet.autoSizeColumn((short)3); 
      sheet.autoSizeColumn((short)4);
      
	    try  
	    {  
	      //输出Excel文件
	        OutputStream output=response.getOutputStream();
	        response.reset();
			String dateTime = DateUtils.toString(new Date(), "yyyy-MM-dd HH:mm:ss");
			response.setHeader("Content-disposition", "attachment; filename=Cash_"+ dateTime +".xls");
			response.setContentType("application/vnd.ms-excel");
	        wb.write(output);
			output.flush();
	        output.close();
	    }  
	    catch (Exception e)  
	    {  
	        e.printStackTrace();  
	    }  
	}

	public WithdrawCashService getWithdrawCashService()
	{
		return withdrawCashService;
	}

	public void setWithdrawCashService(WithdrawCashService withdrawCashService)
	{
		this.withdrawCashService = withdrawCashService;
	}

}
