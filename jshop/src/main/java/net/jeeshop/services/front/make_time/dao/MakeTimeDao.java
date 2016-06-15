package net.jeeshop.services.front.make_time.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.front.make_time.bean.MakeTime;

public interface MakeTimeDao extends DaoManager<MakeTime> {

	MakeTime SelectDays(MakeTime makeTime);

}
