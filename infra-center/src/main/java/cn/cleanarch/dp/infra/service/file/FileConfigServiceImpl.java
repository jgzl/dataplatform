package cn.cleanarch.dp.infra.service.file;

import cn.cleanarch.dp.common.core.utils.JacksonUtil;
import cn.cleanarch.dp.common.core.utils.ValidationUtils;
import cn.cleanarch.dp.infra.constants.ErrorCodeConstants;
import cn.cleanarch.dp.infra.mq.producer.file.FileConfigProducer;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.IdUtil;
import cn.cleanarch.dp.common.core.model.PageResult;
import cn.cleanarch.dp.common.file.core.client.FileClient;
import cn.cleanarch.dp.common.file.core.client.FileClientConfig;
import cn.cleanarch.dp.common.file.core.client.FileClientFactory;
import cn.cleanarch.dp.common.file.core.enums.FileStorageEnum;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigCreateReqVO;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigPageReqVO;
import cn.cleanarch.dp.infra.vo.file.config.FileConfigUpdateReqVO;
import cn.cleanarch.dp.infra.convert.file.FileConfigConvert;
import cn.cleanarch.dp.infra.dataobject.file.FileConfigDO;
import cn.cleanarch.dp.infra.mapper.file.FileConfigMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;

import static cn.cleanarch.dp.common.core.exception.util.ServiceExceptionUtil.exception;

/**
 * 文件配置 Service 实现类
 *
 * @author 芋道源码
 */
@Service
@Validated
@Slf4j
public class FileConfigServiceImpl implements FileConfigService {

    @Resource
    private FileClientFactory fileClientFactory;
    /**
     * Master FileClient 对象，有且仅有一个，即 {@link FileConfigDO#getMaster()} 对应的
     */
    @Getter
    private FileClient masterFileClient;

    @Resource
    private FileConfigMapper fileConfigMapper;

    @Resource
    private FileConfigProducer fileConfigProducer;

    @Resource
    private Validator validator;

    @Override
    @PostConstruct
    public void initLocalCache() {
        // 第一步：查询数据
        List<FileConfigDO> configs = fileConfigMapper.selectList();
        log.info("[initLocalCache][缓存文件配置，数量为:{}]", configs.size());

        // 第二步：构建缓存：创建或更新文件 Client
        configs.forEach(config -> {
            fileClientFactory.createOrUpdateFileClient(config.getId(), config.getStorage(), config.getConfig());
            // 如果是 master，进行设置
            if (Boolean.TRUE.equals(config.getMaster())) {
                masterFileClient = fileClientFactory.getFileClient(config.getId());
            }
        });
    }

    @Override
    public String createFileConfig(FileConfigCreateReqVO createReqVO) {
        // 插入
        FileConfigDO fileConfig = FileConfigConvert.INSTANCE.convert(createReqVO)
                .setConfig(parseClientConfig(createReqVO.getStorage(), createReqVO.getConfig()))
                .setMaster(false); // 默认非 master
        fileConfigMapper.insert(fileConfig);
        // 发送刷新配置的消息
        fileConfigProducer.sendFileConfigRefreshMessage();
        // 返回
        return fileConfig.getId();
    }

    @Override
    public void updateFileConfig(FileConfigUpdateReqVO updateReqVO) {
        // 校验存在
        FileConfigDO config = validateFileConfigExists(updateReqVO.getId());
        // 更新
        FileConfigDO updateObj = FileConfigConvert.INSTANCE.convert(updateReqVO)
                .setConfig(parseClientConfig(config.getStorage(), updateReqVO.getConfig()));
        fileConfigMapper.updateById(updateObj);
        // 发送刷新配置的消息
        fileConfigProducer.sendFileConfigRefreshMessage();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFileConfigMaster(String id) {
        // 校验存在
        validateFileConfigExists(id);
        // 更新其它为非 master
        fileConfigMapper.updateBatch(new FileConfigDO().setMaster(false));
        // 更新
        fileConfigMapper.updateById(new FileConfigDO().setId(id).setMaster(true));
        // 发送刷新配置的消息
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {

            @Override
            public void afterCommit() {
                fileConfigProducer.sendFileConfigRefreshMessage();
            }

        });
    }

    private FileClientConfig parseClientConfig(Integer storage, Map<String, Object> config) {
        // 获取配置类
        Class<? extends FileClientConfig> configClass = FileStorageEnum.getByStorage(storage)
                .getConfigClass();
        FileClientConfig clientConfig = JacksonUtil.parseObject(JacksonUtil.toJsonString(config), configClass);
        // 参数校验
        ValidationUtils.validate(validator, clientConfig);
        // 设置参数
        return clientConfig;
    }

    @Override
    public void deleteFileConfig(String id) {
        // 校验存在
        FileConfigDO config = validateFileConfigExists(id);
        if (Boolean.TRUE.equals(config.getMaster())) {
             throw exception(ErrorCodeConstants.FILE_CONFIG_DELETE_FAIL_MASTER);
        }
        // 删除
        fileConfigMapper.deleteById(id);
        // 发送刷新配置的消息
        fileConfigProducer.sendFileConfigRefreshMessage();
    }

    private FileConfigDO validateFileConfigExists(String id) {
        FileConfigDO config = fileConfigMapper.selectById(id);
        if (config == null) {
            throw exception(ErrorCodeConstants.FILE_CONFIG_NOT_EXISTS);
        }
        return config;
    }

    @Override
    public FileConfigDO getFileConfig(String id) {
        return fileConfigMapper.selectById(id);
    }

    @Override
    public PageResult<FileConfigDO> getFileConfigPage(FileConfigPageReqVO pageReqVO) {
        return fileConfigMapper.selectPage(pageReqVO);
    }

    @Override
    public String testFileConfig(String id) throws Exception {
        // 校验存在
        validateFileConfigExists(id);
        // 上传文件
        byte[] content = ResourceUtil.readBytes("file/erweima.jpg");
        return fileClientFactory.getFileClient(id).upload(content, IdUtil.fastSimpleUUID() + ".jpg", "image/jpeg");
    }

    @Override
    public FileClient getFileClient(String id) {
        return fileClientFactory.getFileClient(id);
    }

}
