package cn.cleanarch.dp.common.job.service;

import cn.cleanarch.dp.common.job.model.XxlJobInfo;

import java.util.List;

public interface JobInfoService {

    List<XxlJobInfo> getJobInfo(Integer jobGroupId, String executorHandler);

    Integer addJobInfo(XxlJobInfo xxlJobInfo);

}
