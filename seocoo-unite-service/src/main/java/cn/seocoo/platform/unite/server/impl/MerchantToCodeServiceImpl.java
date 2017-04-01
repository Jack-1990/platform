package cn.seocoo.platform.unite.server.impl;

import cn.seocoo.platform.common.util.Constant;
import cn.seocoo.platform.model.MerchantInfo;
import cn.seocoo.platform.model.MerchantUser;
import cn.seocoo.platform.service.dimDicInfo.inf.DimDicInfoService;
import cn.seocoo.platform.service.merchantInfo.inf.MerchantInfoService;
import cn.seocoo.platform.service.unite.inf.Service;
import cn.seocoo.platform.unite.Result;
import com.alibaba.fastjson.JSON;
import com.tydic.framework.util.StringUtil;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * APP  我的商户
 */
public class MerchantToCodeServiceImpl implements Service {
    private Logger logger = Logger.getLogger(this.getClass());
    private MerchantInfoService merchantInfoService;
    private DimDicInfoService dimDicInfoService;

    @Override
    public Object sevice(String param) {
        logger.debug("QueryMyMerchantServiceImpl 请求参数：" + param);
        MerchantUser user = JSON.parseObject(param, MerchantUser.class);
        // 参数验证
        String validateRes = validateParam(user);
        Result result = new Result();
        result.setResultCode(Constant.RESULT_CODE_FAIL);
        if ("OK".equals(validateRes)) {
            // 获取 url 链接
            MerchantInfo merchantInfo = new MerchantInfo();
            merchantInfo.setMerchantCode(user.getMerchantCode());
            List<MerchantInfo> merchantInfos = merchantInfoService.queryMerchantInfoList(merchantInfo);
            if (merchantInfos != null && merchantInfos.size()>0){
                merchantInfo = merchantInfos.get(0);
            }
            result.setResultCode(Constant.RESULT_CODE_SUCCESS);
            result.setResultMsg("查询成功");
            result.setResultData(merchantInfo.getReceiptQrCode());
        } else {
            result.setResultMsg(validateRes);
        }
        String res = JSON.toJSONString(result);
        logger.debug("QueryMyMerchantServiceImpl 返回信息报文：" + res);
        return res;
    }


    /**
     * 验证参数
     *
     * @param user
     * @return
     */
    public String validateParam(MerchantUser user) {
        if (user == null) {
            return "入参错误";
        }
        if (StringUtil.isEmpty(user.getMerchantCode())) {
            return "商户号不能为空";
        }
        return "OK";
    }

    public MerchantInfoService getMerchantInfoService() {
        return merchantInfoService;
    }

    public void setMerchantInfoService(MerchantInfoService merchantInfoService) {
        this.merchantInfoService = merchantInfoService;
    }

    public DimDicInfoService getDimDicInfoService() {
        return dimDicInfoService;
    }

    public void setDimDicInfoService(DimDicInfoService dimDicInfoService) {
        this.dimDicInfoService = dimDicInfoService;
    }

}
