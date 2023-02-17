package cn.cleanarch.dp.common.job.service;

import cn.cleanarch.dp.common.job.model.XxlJobGroup;

import java.util.List;

public interface JobGroupService {

    List<XxlJobGroup> getJobGroup();

    boolean autoRegisterGroup();

    boolean preciselyCheck();

}
