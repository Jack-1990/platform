package cn.seocoo.platform.service.merchant.impl;

import cn.seocoo.platform.dao.groupMerchantPay.inf.GroupMerchantPayDao;
import cn.seocoo.platform.dao.merchant.inf.MerchantDao;
import cn.seocoo.platform.dao.merchantInfo.inf.MerchantInfoDao;
import cn.seocoo.platform.dao.merchantRate.inf.MerchantRateDao;
import cn.seocoo.platform.dao.merchantUser.inf.MerchantUserDao;
import cn.seocoo.platform.dao.rateDimAttr.inf.RateDimAttrDao;
import cn.seocoo.platform.dao.rateSku.inf.RateSkuDao;
import cn.seocoo.platform.dao.userBank.inf.UserBankDao;
import cn.seocoo.platform.dao.userIdinfo.inf.UserIdinfoDao;
import cn.seocoo.platform.dao.userRelationship.inf.UserRelationshipDao;
import cn.seocoo.platform.model.*;
import cn.seocoo.platform.service.merchant.inf.MerchantService;
import com.tydic.framework.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MerchantServiceImpl  implements MerchantService{

    private MerchantDao merchantDao;
    private UserRelationshipDao userRelationshipDao;
    private MerchantInfoDao merchantInfoDao;
    private GroupMerchantPayDao groupMerchantPayDao;
    private MerchantUserDao merchantUserDao;
    private UserIdinfoDao userIdinfoDao;
    private UserBankDao userBankDao;
    private RateDimAttrDao rateDimAttrDao;
    private RateSkuDao rateSkuDao;
    private MerchantRateDao merchantRateDao;

    public MerchantDao getMerchantDao() {
        return merchantDao;
    }

    public void setMerchantDao(MerchantDao merchantDao) {
        this.merchantDao = merchantDao;
    }

    @Override
    public Merchant queryMerchant(Merchant merchant){
        return merchantDao.queryMerchant(merchant);
    }

    @Override
    public List<Merchant> queryMerchantList(Merchant merchant){
        return merchantDao.queryMerchantList(merchant);
    }
    @Override
    public void saveMerchant(Merchant merchant){
        merchantDao.saveMerchant(merchant);
    }
    @Override
    public void updateMerchant(Merchant merchant){
        merchantDao.updateMerchant(merchant);
    }
    @Override
    public void deleteMerchant(Merchant merchant){
        merchantDao.deleteMerchant(merchant);
    }
    @Override
    public List<Merchant> queryMerchantPage(Map map){
        return merchantDao.queryMerchantPage(map);
    }
    @Override
    public Integer queryMerchantPageCount(Map map){
        return merchantDao.queryMerchantPageCount(map);
    }

    @Override
    public void updateMerchantByOutMchntId(Merchant merchant)
    {
        // TODO Auto-generated method stub
        merchantDao.updateMerchantByOutMchntId(merchant);
    }

    @Override
    public void saveMerchantAndMerchantUser(Merchant merchant,String PlatformId,String txnSeq,String loginName,String receiptQrCode, int isAudito) {
        //保存商户信息以及商户
        try {
            merchant.setCreateTime(new Date());
            merchant.setDevType(1);
            merchant.setFlag(0);
            merchant.setTxnSeq(txnSeq);
            merchant.setPlatformId(PlatformId);
            // 判断拓展人员编号|operId:过长则截取
            if (merchant.getOperId().length() > 15) {
                merchant.setOperId(merchant.getOperId().substring(0, 15));
            }



            //保存商户信息
            MerchantInfo info = new MerchantInfo();

            //代入上级merchantCode
            // 获取当前角色的GroupCode
            if (isAudito != 1) {
                UserRelationship userRelationship = new UserRelationship();
                userRelationship.setLoginName(loginName);
                List<UserRelationship> userRelationshipList = userRelationshipDao.queryUserRelationshipList(userRelationship);

                if (userRelationshipList != null & userRelationshipList.size() > 0) {
                    userRelationship = userRelationshipList.get(0);
                    //获取代理商商户信息
                    GroupMerchantPay groupMerchantPay = new GroupMerchantPay();
                    groupMerchantPay.setGroupCode(userRelationship.getGroupCode());
                    List<GroupMerchantPay> groupMerchantPays = groupMerchantPayDao.queryGroupMerchantPayList(groupMerchantPay);
                    if (groupMerchantPays != null && groupMerchantPays.size() > 0) {
                        info.setParentMerchantCode(groupMerchantPays.get(0).getMerchantCode());
                        info.setParentUser(groupMerchantPays.get(0).getMerchantUser());
                    }
                }
            }
            receiptQrCode += "/codeToPay.do?merchantCode=" + merchant.getOutMchntId();
            info.setReceiptQrCode(receiptQrCode);
            info.setMerchantCode(merchant.getOutMchntId());
            info.setBank("MS");
            info.setCertifyStatus(0);
            info.setCreateTime(new Date());
            info.setCreateUser(loginName);
            info.setCurrentTotalProfit(0.00);
            info.setTotalProfit(0.00);
            info.setLevel(merchant.getMerchantLevel());
            info.setSubmitAuditTime(new Date());


            //身份证信息
            UserIdinfo userId = new UserIdinfo();
            userId.setMerchantCode(merchant.getOutMchntId());
            userId.setRealName(merchant.getCorpName());
            userId.setIDNumber(merchant.getIdtCard());
            userId.setAddress(merchant.getAddress());
            userId.setAuditStatus(1);
            userId.setCreateUser(loginName);
            userId.setCreateTime(new Date());


            // 保存 银行信息
            UserBank userBank = new UserBank();
            userBank.setMerchantCode(merchant.getOutMchntId());
            userBank.setCardNumber(merchant.getCardNumber());
            userBank.setAffiliatedBank(merchant.getAffiliatedBank());
            userBank.setBankName(merchant.getBankName());
            userBank.setBankArea(merchant.getBankProvince() + "" + merchant.getBankCity());
            userBank.setBankAreaCode(merchant.getBankAreaCode());
            userBank.setBankNumber(merchant.getBankNumber());
            userBank.setAuditStatus(1);
            userBank.setCreateUser(loginName);
            userBank.setCreateTime(new Date());
            userBank.setProvince(merchant.getProvince());
            userBank.setCity(merchant.getBankCity());

            // 保存费率
            String skuWxString = "";
            String skuSFBString = "";
            RateDimAttr rateDimAttr = new RateDimAttr();
            rateDimAttr.setDimCode("level");// 等级
            rateDimAttr.setDimAttrCode(merchant.getMerchantLevel());
            List<RateDimAttr> rateDimAttrWithLastLevel = rateDimAttrDao.queryRateDimAttrWithLastLevel(rateDimAttr);
            if (rateDimAttrWithLastLevel != null && rateDimAttrWithLastLevel.size() > 0) {
                rateDimAttr = rateDimAttrWithLastLevel.get(0);
                skuWxString = "bankminsheng" + "tnt001" + rateDimAttr.getDimCode() + rateDimAttr.getDimAttrCode() + "payStyleweixin";
                skuSFBString = "bankminsheng" + "tnt001" + rateDimAttr.getDimCode() + rateDimAttr.getDimAttrCode() + "payStylezhifubao";
            }
            skuWxString = MD5Util.getMD5String(skuWxString);
            skuSFBString = MD5Util.getMD5String(skuSFBString);
            // 根据SKUCode查询费率
            List<MerchantRate> merchantRateList = new ArrayList<MerchantRate>();
            List<RateSku> queryRateSkuList = rateSkuDao.queryRateSkuList(new RateSku());
            for (RateSku rateSku2 : queryRateSkuList) {
                MerchantRate merchantRate = new MerchantRate();
                merchantRate.setCreateTime(new Date());
                merchantRate.setIntoRate(rateSku2.getSetRate());
                merchantRate.setTradeRate(rateSku2.getIntoRate());
                merchantRate.setMerchantCode(merchant.getOutMchntId() + "_temp");
                if (StringUtils.equals(rateSku2.getSkuCode(), skuWxString)) {
                    merchantRate.setPayChannel("weixin");
                    merchantRateList.add(merchantRate);
                }
                if (StringUtils.equals(rateSku2.getSkuCode(), skuSFBString)) {
                    merchantRate.setPayChannel("zhifubao");
                    merchantRateList.add(merchantRate);
                }
            }


            merchantDao.saveMerchant(merchant);
            merchantInfoDao.saveMerchantInfo(info);
            userIdinfoDao.saveUserIdinfo(userId);
            userBankDao.saveUserBank(userBank);

            MerchantRate merchantRate  = new MerchantRate();
            merchantRate.setMerchantCode(merchant.getOutMchntId() + "_temp");
            merchantRateDao.deleteMerchantRate(merchantRate);

            merchantRateDao.batchInsertMerchantRate(merchantRateList);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
    }

    @Override
    public void updateMerchantAndMerchantUser(Merchant merchant) {


        MerchantInfo info = new MerchantInfo();
        info.setMerchantCode(merchant.getOutMchntId());
        info.setLevel(merchant.getMerchantLevel());

        UserIdinfo userId = new UserIdinfo();
        userId.setMerchantCode(merchant.getOutMchntId());
        userId.setRealName(merchant.getCorpName());
        userId.setIDNumber(merchant.getIdtCard());
        userId.setAddress(merchant.getAddress());


        UserBank userBank = new UserBank();
        userBank.setMerchantCode(merchant.getOutMchntId());
        userBank.setCardNumber(merchant.getCardNumber());
        userBank.setAffiliatedBank(merchant.getAffiliatedBank());
        userBank.setBankName(merchant.getBankName());
        userBank.setBankArea(merchant.getBankProvince() + "" + merchant.getBankCity());
        userBank.setBankAreaCode(merchant.getBankAreaCode());
        userBank.setBankNumber(merchant.getBankNumber());
        userBank.setProvince(merchant.getProvince());
        userBank.setCity(merchant.getBankCity());


        // 保存费率
        String skuWxString = "";
        String skuSFBString = "";
        RateDimAttr rateDimAttr = new RateDimAttr();
        rateDimAttr.setDimCode("level");// 等级
        rateDimAttr.setDimAttrCode(merchant.getMerchantLevel());
        List<RateDimAttr> rateDimAttrWithLastLevel = rateDimAttrDao.queryRateDimAttrWithLastLevel(rateDimAttr);
        if (rateDimAttrWithLastLevel != null && rateDimAttrWithLastLevel.size() > 0) {
            rateDimAttr = rateDimAttrWithLastLevel.get(0);
            skuWxString = "bankminsheng" + "tnt001" + rateDimAttr.getDimCode() + rateDimAttr.getDimAttrCode() + "payStyleweixin";
            skuSFBString = "bankminsheng" + "tnt001" + rateDimAttr.getDimCode() + rateDimAttr.getDimAttrCode() + "payStylezhifubao";
        }
        skuWxString = MD5Util.getMD5String(skuWxString);
        skuSFBString = MD5Util.getMD5String(skuSFBString);
        // 根据SKUCode查询费率
        List<MerchantRate> merchantRateList = new ArrayList<MerchantRate>();
        List<RateSku> queryRateSkuList = rateSkuDao.queryRateSkuList(new RateSku());
        for (RateSku rateSku2 : queryRateSkuList) {
            MerchantRate merchantRate = new MerchantRate();
            merchantRate.setCreateTime(new Date());
            merchantRate.setIntoRate(rateSku2.getSetRate());
            merchantRate.setTradeRate(rateSku2.getIntoRate());
            merchantRate.setMerchantCode(merchant.getOutMchntId() + "_temp");
            if (StringUtils.equals(rateSku2.getSkuCode(), skuWxString)) {
                merchantRate.setPayChannel("weixin");
                merchantRateList.add(merchantRate);
            }
            if (StringUtils.equals(rateSku2.getSkuCode(), skuSFBString)) {
                merchantRate.setPayChannel("zhifubao");
                merchantRateList.add(merchantRate);
            }
        }
        try {
            merchantDao.updateMerchant(merchant);
            merchantInfoDao.updateMerchantInfo(info);
            userIdinfoDao.updateUserIdinfo(userId);
            userBankDao.updateUserBank(userBank);

            MerchantRate merchantRate  = new MerchantRate();
            merchantRate.setMerchantCode(merchant.getOutMchntId() + "_temp");
            merchantRateDao.deleteMerchantRate(merchantRate);

            merchantRateDao.batchInsertMerchantRate(merchantRateList);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

    }

    @Override
    public List<Merchant> queryMerchantWithMerchantInfo(Map map) {
        return merchantDao.queryMerchantWithMerchantInfo(map);
    }

    @Override
    public Integer queryMerchantWithMerchantInfoCount(Map map) {
        return merchantDao.queryMerchantWithMerchantInfoCount(map);
    }

    public UserRelationshipDao getUserRelationshipDao() {
        return userRelationshipDao;
    }

    public void setUserRelationshipDao(UserRelationshipDao userRelationshipDao) {
        this.userRelationshipDao = userRelationshipDao;
    }

    public MerchantInfoDao getMerchantInfoDao() {
        return merchantInfoDao;
    }

    public void setMerchantInfoDao(MerchantInfoDao merchantInfoDao) {
        this.merchantInfoDao = merchantInfoDao;
    }

    public GroupMerchantPayDao getGroupMerchantPayDao() {
        return groupMerchantPayDao;
    }

    public void setGroupMerchantPayDao(GroupMerchantPayDao groupMerchantPayDao) {
        this.groupMerchantPayDao = groupMerchantPayDao;
    }

    public MerchantUserDao getMerchantUserDao() {
        return merchantUserDao;
    }

    public void setMerchantUserDao(MerchantUserDao merchantUserDao) {
        this.merchantUserDao = merchantUserDao;
    }

    public UserIdinfoDao getUserIdinfoDao() {
        return userIdinfoDao;
    }

    public void setUserIdinfoDao(UserIdinfoDao userIdinfoDao) {
        this.userIdinfoDao = userIdinfoDao;
    }

    public UserBankDao getUserBankDao() {
        return userBankDao;
    }

    public void setUserBankDao(UserBankDao userBankDao) {
        this.userBankDao = userBankDao;
    }

    public RateDimAttrDao getRateDimAttrDao() {
        return rateDimAttrDao;
    }

    public void setRateDimAttrDao(RateDimAttrDao rateDimAttrDao) {
        this.rateDimAttrDao = rateDimAttrDao;
    }

    public RateSkuDao getRateSkuDao() {
        return rateSkuDao;
    }

    public void setRateSkuDao(RateSkuDao rateSkuDao) {
        this.rateSkuDao = rateSkuDao;
    }

    public MerchantRateDao getMerchantRateDao() {
        return merchantRateDao;
    }

    public void setMerchantRateDao(MerchantRateDao merchantRateDao) {
        this.merchantRateDao = merchantRateDao;
    }
}
