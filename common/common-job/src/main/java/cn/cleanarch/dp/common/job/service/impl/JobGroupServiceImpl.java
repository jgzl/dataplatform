package cn.cleanarch.dp.common.job.service.impl;

import cn.cleanarch.dp.common.job.config.XxlAdminProperties;
import cn.cleanarch.dp.common.job.config.XxlExecutorProperties;
import cn.cleanarch.dp.common.job.config.XxlJobProperties;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.cleanarch.dp.common.job.model.XxlJobGroup;
import cn.cleanarch.dp.common.job.service.JobGroupService;
import cn.cleanarch.dp.common.job.service.JobLoginService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Hydra
 * @date: 2022/9/19 17:34
 * @version: 1.0
 */
@Service
public class JobGroupServiceImpl implements JobGroupService {

    @Autowired
    private XxlJobProperties xxlJobProperties;

    @Autowired
    private JobLoginService jobLoginService;

    @Override
    public List<XxlJobGroup> getJobGroup() {
        XxlAdminProperties xxlAdminProperties = xxlJobProperties.getAdmin();
        XxlExecutorProperties xxlExecutorProperties = xxlJobProperties.getExecutor();
        String url=xxlAdminProperties.getAddresses()+"/jobgroup/pageList";
        HttpResponse response = HttpRequest.post(url)
                .form("appname", xxlExecutorProperties.getAppname())
                .form("title", xxlExecutorProperties.getTitle())
                .cookie(jobLoginService.getCookie())
                .execute();

        String body = response.body();
        JSONArray array = JSONUtil.parse(body).getByPath("data", JSONArray.class);
        List<XxlJobGroup> list = array.stream()
                .map(o -> JSONUtil.toBean((JSONObject) o, XxlJobGroup.class))
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public boolean autoRegisterGroup() {
        XxlAdminProperties xxlAdminProperties = xxlJobProperties.getAdmin();
        XxlExecutorProperties xxlExecutorProperties = xxlJobProperties.getExecutor();
        String addressType = xxlExecutorProperties.getAddressType();
        String addressList = xxlExecutorProperties.getAddress();
        String url=xxlAdminProperties.getAddresses()+"/jobgroup/save";
        HttpRequest httpRequest = HttpRequest.post(url)
                .form("appname", xxlExecutorProperties.getAppname())
                .form("title", xxlExecutorProperties.getTitle());

        httpRequest.form("addressType",addressType);
        if (addressType.equals(1)){
            if (Strings.isBlank(addressList)){
                throw new RuntimeException("手动录入模式下,执行器地址列表不能为空");
            }
            httpRequest.form("addressList",addressList);
        }

        HttpResponse response = httpRequest.cookie(jobLoginService.getCookie())
                .execute();
        Object code = JSONUtil.parse(response.body()).getByPath("code");
        return code.equals(200);
    }

    @Override
    public boolean preciselyCheck() {
        XxlExecutorProperties xxlExecutorProperties = xxlJobProperties.getExecutor();
        List<XxlJobGroup> jobGroup = getJobGroup();
        Optional<XxlJobGroup> has = jobGroup.stream()
                .filter(xxlJobGroup -> xxlJobGroup.getAppname().equals(xxlExecutorProperties.getAppname())
                        && xxlJobGroup.getTitle().equals(xxlExecutorProperties.getTitle()))
                .findAny();
        return has.isPresent();
    }

}
