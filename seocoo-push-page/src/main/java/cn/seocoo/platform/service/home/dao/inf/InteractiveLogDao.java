package cn.seocoo.platform.service.home.dao.inf;

import java.util.List;

import cn.seocoo.platform.unite.InteractiveLog;

public interface InteractiveLogDao {
	public List<InteractiveLog> findAll();

	public InteractiveLog find(InteractiveLog iLog);

	public void add(InteractiveLog iLog);

	public void update(InteractiveLog iLog);

	public void delete(InteractiveLog iLog);

}
