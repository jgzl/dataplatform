package cn.cleanarch.dp.infra.service.db;

import cn.cleanarch.dp.infra.vo.db.DataSourceConfigCreateReqVO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigUpdateReqVO;
import cn.cleanarch.dp.infra.dataobject.db.DataSourceConfigDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 数据源配置 Service 接口
 *
 * @author 芋道源码
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
    DataSourceConfigDO getDataSourceConfig(String id);

    /**
     * 获得数据源配置列表
     *
     * @return 数据源配置列表
     */
    List<DataSourceConfigDO> getDataSourceConfigList();

}
