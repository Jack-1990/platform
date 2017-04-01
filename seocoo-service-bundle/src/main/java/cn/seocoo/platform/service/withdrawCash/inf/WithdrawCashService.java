package cn.seocoo.platform.service.withdrawCash.inf;

import java.util.List;
import java.util.Map;
import cn.seocoo.platform.model.WithdrawCash;

public interface WithdrawCashService {

    public WithdrawCash queryWithdrawCash(WithdrawCash withdrawCash);

    public List<WithdrawCash> queryWithdrawCashList(WithdrawCash withdrawCash);

    public void saveWithdrawCash(WithdrawCash withdrawCash);

    public void updateWithdrawCash(WithdrawCash withdrawCash);

    public void deleteWithdrawCash(WithdrawCash withdrawCash);

    public List<WithdrawCash> queryWithdrawCashPage(Map map);

    public Integer queryWithdrawCashPageCount(Map map);
}
