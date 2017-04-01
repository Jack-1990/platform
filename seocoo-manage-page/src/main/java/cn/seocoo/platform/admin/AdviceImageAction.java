package cn.seocoo.platform.admin;

import cn.seocoo.platform.common.base.BaseAction;
import cn.seocoo.platform.model.AdviceImage;
import cn.seocoo.platform.service.adviceImage.inf.AdviceImageService;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 上传广告轮播图
 */
public class AdviceImageAction extends BaseAction {

   private AdviceImageService adviceImageService ;
    /**
     * ]
     * 广告轮播图
     *
     * @return
     */
    public String adviceImage() {
        //String merchantCode = request.getParameter("merchantCode");
        List<AdviceImage> adviceImageList = null;
        int size = 0;
        AdviceImage adviceImage = new AdviceImage();
        //adviceImage.setMerchantCode(merchantCode);
        adviceImageList = adviceImageService.queryAdviceImageList(adviceImage);
        if (adviceImageList.size() > 0) {
            size = adviceImageList.size();
        }
        request.setAttribute("picList", adviceImageList);
        request.setAttribute("size", size);
        //request.setAttribute("merchantCode", merchantCode);
        return "adviceImage";
    }

    /**
     * 保存轮播图
     *
     * @throws IOException
     */
    public void saveImage() throws IOException {
        String url = request.getParameter("url");
        //String merchantCode = request.getParameter("merchantCode");
        JSONObject json = new JSONObject();
        // 保存图片
        List<AdviceImage> adviceImageList = new ArrayList<AdviceImage>();
        // 判断多个还是单个
        if (url.indexOf(",") > 0) {
            String[] str = url.split(",");
            for (int i = 0; i < str.length; i++) {
                String carouselPic = str[i];
                AdviceImage adviceImage = new AdviceImage();
                //adviceImage.setMerchantCode(merchantCode);
                adviceImage.setPicUrl(carouselPic);
                adviceImage.setSeq(i + 1);
                adviceImage.setJumpUrl("");
                adviceImageList.add(adviceImage);
            }
        } else {
            AdviceImage adviceImage = new AdviceImage();
            //adviceImage.setMerchantCode(merchantCode);
            adviceImage.setPicUrl(url);
            adviceImage.setSeq(1);
            adviceImage.setJumpUrl("");
            adviceImageList.add(adviceImage);
        }
        try {
            // 先删除后添加
            AdviceImage adviceImage = new AdviceImage();
            //adviceImage.setMerchantCode(merchantCode);
            adviceImageService.deleteAdviceImage(adviceImage);
            adviceImageService.batchInsertAdviceImage(adviceImageList);
            json.put("result", "SUCCESS");
        } catch (Exception e) {
            json.put("result", "FAIL");
        }
        this.sendMessages(json.toJSONString());
    }


    public AdviceImageService getAdviceImageService() {
        return adviceImageService;
    }

    public void setAdviceImageService(AdviceImageService adviceImageService) {
        this.adviceImageService = adviceImageService;
    }
}
