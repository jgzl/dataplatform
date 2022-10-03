package cn.cleanarch.dp.common.database;

import cn.cleanarch.dp.common.database.constants.DbType;
import cn.cleanarch.dp.common.database.dialect.DialectRegistry;

/**
 * 方言工厂类
 *
 * @author yuwei
 * @since 2020-03-14
 */
public class DialectFactory {

    private static final DialectRegistry DIALECT_REGISTRY = new DialectRegistry();

    public static DbDialect getDialect(DbType dbType) {
        return DIALECT_REGISTRY.getDialect(dbType);
    }
}
