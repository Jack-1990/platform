package cn.seocoo.platform.dao.adviceImage.inf;

import java.util.List;
import java.util.Map;

import cn.seocoo.platform.model.AdviceImage;

public interface AdviceImageDao {

    public AdviceImage queryAdviceImage(AdviceImage adviceImage);

    public List<AdviceImage> queryAdviceImageList(AdviceImage adviceImage);

    public void saveAdviceImage(AdviceImage adviceImage);

    public void updateAdviceImage(AdviceImage adviceImage);

    public void deleteAdviceImage(AdviceImage adviceImage);

    public List<AdviceImage> queryAdviceImagePage(Map map);

    public Integer queryAdviceImagePageCount(Map map);

	public void batchInsertAdviceImage(List<AdviceImage> adviceImage);
}
