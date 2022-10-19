package cn.cleanarch.dp.gateway.infra.service.db;

import cn.cleanarch.dp.infra.dataobject.db.DataSourceConfigDO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigCreateReqVO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigUpdateReqVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据源配置 Service 接口
 *
 * @author li7hai26@gmail.com
 */
public interface DataSourceConfigService {

    /**
     * 创建数据源配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    String createDataSourceConfig(@Valid DataSourceConfigCreateReqVO createReqVO);

    /**
     * 更新数据源配置
     *
     * @param updateReqVO 更新信息
     */
    void updateDataSourceConfig(@Valid DataSourceConfigUpdateReqVO updateReqVO);

    /**
     * 删除数据源配置
     *
     * @param id 编号
     */
    void deleteDataSourceConfig(Long id);

    /**
     * 获得数据源配置
     *
     * @param id 编号
     * @return 数据源配置
     */
    DataSourceConfigDO getDataSourceConfig(Long id);

    /**
     * 获得数据源配置列表
     *
     * @return 数据源配置列表
     */
    List<DataSourceConfigDO> getDataSourceConfigList();

}
