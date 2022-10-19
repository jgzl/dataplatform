package cn.cleanarch.dp.common.data.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.toolkit.JdbcUtils;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC 工具类
 *
 * @author li7hai26@gmail.com
 */
public class JdbcUtil {

    /**
     * 判断连接是否正确
     *
     * @param url      数据源连接
     * @param username 账号
     * @param password 密码
     * @return 是否正确
     */
    public static boolean isConnectionOK(String url, String username, String password) {
        try (Connection ignored = DriverManager.getConnection(url, username, password)) {
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * 获得 URL 对应的 DB 类型
     *
     * @param url URL
     * @return DB 类型
     */
    public static DbType getDbType(String url) {
        DbType dbType = JdbcUtils.getDbType(url);
        return dbType;
    }

}
