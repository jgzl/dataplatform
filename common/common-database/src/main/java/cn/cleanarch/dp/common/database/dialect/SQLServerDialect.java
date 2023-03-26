package cn.cleanarch.dp.common.database.dialect;

/**
 * SQLServer 数据库方言
 *
 * @author yuwei
 * @since 2020-03-14
 */
public class SQLServerDialect extends SQLServer2008Dialect {

    @Override
    public String buildPaginationSql(String originalSql, long offset, long count) {
        return originalSql + " OFFSET " + offset + " ROWS FETCH NEXT " + count + " ROWS ONLY ";
    }
}
