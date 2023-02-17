package cn.cleanarch.dp.common.job.service.impl;

import cn.cleanarch.dp.common.job.config.XxlJobProperties;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.cleanarch.dp.common.job.model.XxlJobInfo;
import cn.cleanarch.dp.common.job.service.JobInfoService;
import cn.cleanarch.dp.common.job.service.JobLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author : Hydra
 * @date: 2022/9/20 10:36
 * @version: 1.0
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private XxlJobProperties xxlJobProperties;

    @Autowired
    private JobLoginService jobLoginService;

    @Override
    public List<XxlJobInfo> getJobInfo(Integer jobGroupId,String executorHandler) {
        String url=xxlJobProperties.getAdmin().getAddresses()+"/jobinfo/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("jobGroup", jobGroupId)
                .form("executorHandler", executorHandler)
                .form("triggerStatus", -1)
                .cookie(jobLoginService.getCookie())
                .execute();

        String body = response.body();
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);
        List<XxlJobInfo> list = array.stream()
                .map(o -> JSONUtil.toBean((JSONObject) o, XxlJobInfo.class))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public Integer addJobInfo(XxlJobInfo xxlJobInfo) {
        String url=xxlJobProperties.getAdmin().getAddresses()+"/jobinfo/add";
        Map<String, Object> paramMap = BeanUtil.beanToMap(xxlJobInfo);
        HttpResponse response = HttpRequest.post(url)
                .form(paramMap)
                .cookie(jobLoginService.getCookie())
                .execute();

        JSON json = JSONUtil.parse(response.body());
        Object code = json.getByPath("code");
        if (code.equals(200)){
            return Convert.toInt(json.getByPath("content"));
        }
        throw new RuntimeException("add jobInfo error!");
    }

}
