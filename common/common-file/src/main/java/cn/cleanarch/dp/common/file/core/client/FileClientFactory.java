package cn.cleanarch.dp.common.file.core.client;

import cn.cleanarch.dp.common.file.core.enums.FileStorageEnum;

public interface FileClientFactory {

    /**
     * 获得文件客户端
     *
     * @param configId 配置编号
     * @return 文件客户端
     */
    FileClient getFileClient(String configId);

    /**
     * 创建文件客户端
     *
     * @param configId 配置编号
     * @param storage 存储器的枚举 {@link FileStorageEnum}
     * @param config 文件配置
     */
    <Config extends FileClientConfig> void createOrUpdateFileClient(String configId, Integer storage, Config config);

}
