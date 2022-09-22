package cn.cleanarch.gw.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Named;

public interface BaseConvert<Vo, Do> {

    @Named("convertDo2Vo")
    Vo convertDo2Vo(Do model);

    @Named("convertVo2Do")
    Do convertVo2Do(Vo model);

    @Named("convertDo2Vo")
    Page<Vo> convertDo2Vo(Page<Do> model);

    @Named("convertVo2Do")
    Page<Do> convertVo2Do(Page<Vo> model);
}
