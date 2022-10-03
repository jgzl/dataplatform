package cn.cleanarch.dp.common.core.handler.mvc;

import cn.cleanarch.dp.common.core.constant.HttpHeaderConstants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.mvc.condition.RequestCondition;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class ApiRequestCondition implements RequestCondition<ApiRequestCondition> {

    private final String apiVersion;

    public ApiRequestCondition(String apiVersion) {
        this.apiVersion = apiVersion;
    }
    public String getApiVersion() {
        return apiVersion;
    }

    @Override
    public ApiRequestCondition combine(ApiRequestCondition other) {
        // 采用最后定义优先原则，则方法上的定义覆盖类上面的定义
        return new ApiRequestCondition(other.getApiVersion());
    }

    @Override
    public ApiRequestCondition getMatchingCondition(HttpServletRequest request) {
        String headApiVersion = request.getHeader(HttpHeaderConstants.X_BUSINESS_API_VERSION);
        if (StringUtils.isBlank(headApiVersion)) {
            headApiVersion = "-1";
        }
        if (compareVersion(headApiVersion, this.apiVersion) >= 0) {
            log.info("version={}, apiVersion={}", headApiVersion, this.apiVersion);
            return this;
        }
        return null;
    }

    @Override
    public int compareTo(ApiRequestCondition other, HttpServletRequest request) {
        return compareVersion(other.getApiVersion(), this.apiVersion);
    }

    private int compareVersion(String version1, String version2) {
        if(version1.equals(version2)){
            return 0;
        }
        String[] versionArray1 = version1.split("\\.");
        String[] versionArray2 = version2.split("\\.");
        int maxflag = 1;
        int minLen = 0;
        if(versionArray1.length > versionArray2.length){
            minLen = versionArray2.length;
        }else{
            minLen = versionArray1.length;
            maxflag = 2;
        }
        for(int i = 0; i < minLen; i++){
            if(Integer.parseInt(versionArray1[i]) - Integer.parseInt(versionArray2[i]) > 0){
                return 1;
            }else if(Integer.parseInt(versionArray1[i]) - Integer.parseInt(versionArray2[i]) < 0){
                return -1;
            }
        }
        if(maxflag == 1){
            for (int j = minLen; j < versionArray1.length; j++) {
                if(Integer.parseInt(versionArray1[j]) > 0){
                    return 1;
                }
            }
        }else{
            for (int k = minLen; k < versionArray2.length; k++) {
                if(Integer.parseInt(versionArray2[k]) > 0){
                    return -1;
                }
            }
        }
        return 0;
    }
}
