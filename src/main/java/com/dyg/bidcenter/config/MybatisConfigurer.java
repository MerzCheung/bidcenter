package com.dyg.bidcenter.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class MybatisConfigurer {


    // 项目基础包名称，根据自己公司的项目修改
    public static final String BASE_PACKAGE = "com.dyg.bidcenter";

    // Model所在包
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".entity";

    // Mapper所在包
    public static final String MAPPER_PACKAGE = BASE_PACKAGE + ".mapper";

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(MODEL_PACKAGE);
        // 配置分页插件
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        // 分页尺寸为0时查询所有纪录不再执行分页
        properties.setProperty("pageSizeZero", "true");
        // 页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("reasonable", "false");
        // 支持通过 Mapper接口参数来传递分页参数
        properties.setProperty("supportMethodsArguments", "true");
        pageHelper.setProperties(properties);
        // 添加插件
        factory.setPlugins(new Interceptor[] { pageHelper });
        // 添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setMapperLocations(resolver.getResources("classpath*:mapper/*.xml"));
        factory.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return factory.getObject();
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactoryBean");
        mapperScannerConfigurer.setBasePackage(MAPPER_PACKAGE);
        return mapperScannerConfigurer;
    }
}
