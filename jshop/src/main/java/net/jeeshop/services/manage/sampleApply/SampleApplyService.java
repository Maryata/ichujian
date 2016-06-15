package net.jeeshop.services.manage.sampleApply;

import net.jeeshop.core.Services;
import net.jeeshop.services.manage.sampleApply.bean.SampleApply;

public interface SampleApplyService extends Services<SampleApply>{

	int judgeSampleNumber(SampleApply sa);

	int selectCount(SampleApply sa);


}
