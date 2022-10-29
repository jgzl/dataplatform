package cn.cleanarch.dp.infra.service.db;

import cn.cleanarch.dp.common.data.util.JdbcUtil;
import cn.cleanarch.dp.infra.mapper.db.DataSourceConfigMapper;
import cn.cleanarch.dp.infra.convert.db.DataSourceConfigConvert;
import cn.cleanarch.dp.infra.dataobject.db.DataSourceConfigDO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigCreateReqVO;
import cn.cleanarch.dp.infra.vo.db.DataSourceConfigUpdateReqVO;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

import static cn.cleanarch.dp.common.core.exception.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_EXISTS;
import static cn.cleanarch.dp.common.core.exception.enums.ErrorCodeConstants.DATA_SOURCE_CONFIG_NOT_OK;
import static cn.cleanarch.dp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 数据源配置 Service 实现类
 *
 * @author li7hai26@gmail.com
 */
@Service
@Validated
public class DataSourceConfigServiceImpl implements DataSourceConfigService {

    @Resource
    private DataSourceConfigMapper dataSourceConfigMapper;

    @Override
    public String createDataSourceConfig(DataSourceConfigCreateReqVO createReqVO) {
        DataSourceConfigDO dataSourceConfig = DataSourceConfigConvert.INSTANCE.convert(createReqVO);
        checkConnectionOK(dataSourceConfig);

        // 插入
        dataSourceConfigMapper.insert(dataSourceConfig);
        // 返回
        return dataSourceConfig.getId();
    }

    @Override
    public void updateDataSourceConfig(DataSourceConfigUpdateReqVO updateReqVO) {
        // 校验存在
        validateDataSourceConfigExists(updateReqVO.getId());
        DataSourceConfigDO updateObj = DataSourceConfigConvert.INSTANCE.convert(updateReqVO);
        checkConnectionOK(updateObj);

        // 更新
        dataSourceConfigMapper.updateById(updateObj);
    }

    @Override
    public void deleteDataSourceConfig(Long id) {
        // 校验存在
        validateDataSourceConfigExists(id);
        // 删除
        dataSourceConfigMapper.deleteById(id);
    }

    private void validateDataSourceConfigExists(Long id) {
        if (dataSourceConfigMapper.selectById(id) == null) {
            throw exception(DATA_SOURCE_CONFIG_NOT_EXISTS);
        }
    }

    @Override
    public DataSourceConfigDO getDataSourceConfig(Long id) {
        // 从 DB 中读取
        return dataSourceConfigMapper.selectById(id);
    }

    @Override
    public List<DataSourceConfigDO> getDataSourceConfigList() {
        List<DataSourceConfigDO> result = dataSourceConfigMapper.selectList();
        return result;
    }

    private void checkConnectionOK(DataSourceConfigDO config) {
        boolean success = JdbcUtil.isConnectionOK(config.getUrl(), config.getUsername(), config.getPassword());
        if (!success) {
            throw exception(DATA_SOURCE_CONFIG_NOT_OK);
        }
    }

}
