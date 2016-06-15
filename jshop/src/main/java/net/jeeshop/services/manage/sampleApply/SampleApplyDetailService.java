package net.jeeshop.services.manage.sampleApply;

import java.util.List;
import java.util.Map;

import net.jeeshop.core.Services;
import net.jeeshop.services.manage.sampleApply.bean.SampleApply;
import net.jeeshop.services.manage.sampleApply.bean.SampleApplyDetail;

public interface SampleApplyDetailService extends Services<SampleApplyDetail>{

	void inserDetail(SampleApply sa, SampleApplyDetail smd);

	List<SampleApplyDetail> checkMessage(SampleApplyDetail smd);

	List<SampleApplyDetail> checkMoble(SampleApplyDetail smd);

	int getSampleApplyDetailService(SampleApplyDetail smd);

}
