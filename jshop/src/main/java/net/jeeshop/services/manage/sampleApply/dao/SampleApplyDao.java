package net.jeeshop.services.manage.sampleApply.dao;

import net.jeeshop.core.DaoManager;
import net.jeeshop.services.manage.sampleApply.bean.SampleApply;

public interface SampleApplyDao extends DaoManager<SampleApply>{

	int judgeSampleNumber(SampleApply sa);

	int selectCount(SampleApply sa);

}
