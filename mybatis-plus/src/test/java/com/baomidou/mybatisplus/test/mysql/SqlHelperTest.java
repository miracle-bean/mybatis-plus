package com.baomidou.mybatisplus.test.mysql;

import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.baomidou.mybatisplus.test.mysql.entity.TestDemo;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Author: miracle
 * Date: 2023/8/24 18:20
 */

@DirtiesContext
@ExtendWith(SpringExtension.class)
@ContextConfiguration(locations = {"classpath:mysql/spring-test-mysql.xml"})
class SqlHelperTest {

    private static final Log logger = LogFactory.getLog(SqlHelperTest.class);


    @Test
    public void test_table() {
        List<TestDemo> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            TestDemo testDemo = new TestDemo();
            testDemo.setValue(UUID.randomUUID().toString());
            list.add(testDemo);
        }
        SqlHelperTest.saveBatch(list, TestDemo.class);

    }

    /**
     * 批量插入
     *
     * @param entityList 更新数据俩表
     * @param entity     更新表的实体类型
     * @param <T>
     */
    public static <T> void saveBatch(List<T> entityList, Class entity) {
        String sqlStatement = SqlHelper.table(entity).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
        try (SqlSession batchSqlSession = SqlHelper.sqlSessionBatch(entity)) {
            int i = 0;
            for (T anEntityList : entityList) {
                batchSqlSession.insert(sqlStatement, anEntityList);
                if (i >= 1 && i % 100 == 0) {
                    batchSqlSession.flushStatements();
                }
                i++;
            }
            batchSqlSession.flushStatements();
        } catch (Exception e) {
            logger.error("BaseService saveBatch error ", e);
        }
    }

}
