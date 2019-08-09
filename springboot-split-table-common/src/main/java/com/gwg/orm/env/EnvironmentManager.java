package com.gwg.orm.env;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.fastjson.JSONObject;
import com.gwg.orm.exception.ErrorCodeException;
import com.gwg.orm.exception.ExceptionEnum;
import com.gwg.orm.util.FileUtil;

/**
 * <p>Class: EnvironmentManager</p>
 * <p>Description: 环境配置管理类,定义了环境的配置信息,及简便的API获取配置信息</p>
 */
public class EnvironmentManager {
    private static String USER_HOME_PATH = System.getProperty("user.home");
    private static String TITANS_FILE_PATH = USER_HOME_PATH + "/.titans/" + getEnv() + "-titans.json";

    private static Logger logger = LoggerFactory.getLogger(EnvironmentManager.class);

    static {
        init();
    }

    private static void init() {
        if (System.getProperty("spring.profiles.active") == null && System.getProperty("env") != null) {
            System.setProperty("spring.profiles.active", System.getProperty("env"));
        }
        //获取操作系统名称，比如Windows 10
        String osName = System.getProperty("os.name");
        //判断是不是linux操作系统
        if (osName != null && osName.toLowerCase().indexOf("linux") >= 0) {
        	//设置属性
            System.setProperty("java.security.egd", "file:/dev/./urandom");
        }
        System.setProperty("banner.location", "classpath*:/titans.txt");
        System.setProperty("es.set.netty.runtime.available.processors", "false");
    }


    /***
     *关于配置中心的配置key
     */
    public static final String CONFIG_CENTER_URL = "config.url";


    /***
     * 关于Mybatis配置的key
     */
    public static final String MYBATIS_CONFIG_NAME = "mybatis.config.name";
    public static final String MYBATIS_CONFIG_BASEPACKAGE = "mybatis.config.basePackage";
    public static final String MYBATIS_CONFIG_DRIVERCLASSNAME = "mybatis.config.driverClassName";
    public static final String MYBATIS_CONFIG_JDBCURL = "mybatis.config.jdbcUrl";
    public static final String MYBATIS_CONFIG_USERNAME = "mybatis.config.username";
    public static final String MYBATIS_CONFIG_PASSWORD = "mybatis.config.password";
    public static final String MYBATIS_CONFIG_READONLY = "mybatis.config.readOnly";
    public static final String MYBATIS_CONFIG_CONNECTIONTIMEOUT = "mybatis.config.connectionTimeout";
    public static final String MYBATIS_CONFIG_IDLETIMEOUT = "mybatis.config.idleTimeout";
    public static final String MYBATIS_CONFIG_MAXLIFETIME = "mybatis.config.maxLifetime";
    public static final String MYBATIS_CONFIG_MAXIMUMPOOLSIZE = "mybatis.config.maximumPoolSize";
    //    public static final String MYBATIS_CONFIG_MAPPERLOCATIONS = "mybatis.config.mapperlocations";
    public static final String MYBATIS_CONFIG_MAPPERLOCATIONS = "mybatis.config.mapperLocations";
    public static final String MYBATIS_CONFIG_MAPPER_ANNOTATION_CLASS = "mybatis.config.mapperAnnotationClass";
    public static final String MYBATIS_CONFIG_TYPEALIASEPACKAGE = "mybatis.config.typeAliasesPackage";
    public static final String MYBATIS_CONFIG_MARKERINTERFACE = "mybatis.config.markerInterface";
    public static final String MYBATIS_CONFIGS_NAME = "mybatis.configs[%s].name";
    public static final String MYBATIS_CONFIGS_BASEPACKAGE = "mybatis.configs[%s].basePackage";
    public static final String MYBATIS_CONFIGS_DRIVERCLASSNAME = "mybatis.configs[%s].driverClassName";
    public static final String MYBATIS_CONFIGS_JDBCURL = "mybatis.configs[%s].jdbcUrl";
    public static final String MYBATIS_CONFIGS_USERNAME = "mybatis.configs[%s].username";
    public static final String MYBATIS_CONFIGS_PASSWORD = "mybatis.configs[%s].password";
    public static final String MYBATIS_CONFIGS_READONLY = "mybatis.configs[%s].readOnly";
    public static final String MYBATIS_CONFIGS_CONNECTIONTIMEOUT = "mybatis.configs[%s].connectionTimeout";
    public static final String MYBATIS_CONFIGS_IDLETIMEOUT = "mybatis.configs[%s].idleTimeout";
    public static final String MYBATIS_CONFIGS_MAXLIFETIME = "mybatis.configs[%s].maxLifetime";
    public static final String MYBATIS_CONFIGS_MAXIMUMPOOLSIZE = "mybatis.configs[%s].maximumPoolSize";
    public static final String MYBATIS_CONFIGS_MAPPERLOCATIONS = "mybatis.configs[%s].mapperLocations";
    public static final String MYBATIS_CONFIGS_TYPEALIASEPACKAGE = "mybatis.configs[%s].typeAliasesPackage";
    public static final String MYBATIS_CONFIGS_MARKERINTERFACE = "mybatis.configs[%s].markerInterface";
    public static final String DB_PASSWORD_PRIVATE_KEY = "db.password.privateKey";

    /**
     * 关于Shardingjdbc的配置key
     */
    public static final String SHARDING_BASEPACKAGE = "sharding.basePackage";
    public static final String SHARDING_DEFATULT_DS_INDEX = "sharding.defaultDSIndex";
    public static final String SHARDING_MAPPERLOCATIONS = "sharding.mapperLocations";
    public static final String SHARDING_TYPEALIASESPACKAGE = "sharding.typeAliasesPackage";
    public static final String SHARDING_MARKERINTERFACE = "sharding.markerInterface";
    public static final String SHARDING_DATASOURCES_NAME = "sharding.dataSources[%s].name";
    public static final String SHARDING_DATASOURCES_DRIVERCLASSNAME = "sharding.dataSources[%s].driverClassName";
    public static final String SHARDING_DATASOURCES_JDBCURL = "sharding.dataSources[%s].jdbcUrl";
    public static final String SHARDING_DATASOURCES_USERNAME = "sharding.dataSources[%s].username";
    public static final String SHARDING_DATASOURCES_PASSWORD = "sharding.dataSources[%s].password";
    public static final String SHARDING_DATASOURCES_READONLY = "sharding.dataSources[%s].readOnly";
    public static final String SHARDING_DATASOURCES_CONNECTIONTIMEOUT = "sharding.dataSources[%s].connectionTimeout";
    public static final String SHARDING_DATASOURCES_IDLETIMEOUT = "sharding.dataSources[%s].idleTimeout";
    public static final String SHARDING_DATASOURCES_MAXLIFETIME = "sharding.dataSources[%s].maxLifetime";
    public static final String SHARDING_DATASOURCES_MAXIMUMPOOSIZE = "sharding.dataSources[%s].maximumPoolSize";
    public static final String SHARDING_DATASOURCES_MINIMUMIDLE = "sharding.dataSources[%s].minimumIdle";
    public static final String SHARDING_DATASOURCES_DATASOURCECLASSNAME = "sharding.dataSources[%s].dataSourceClassName";
    public static final String SHARDING_DATASOURCES_VALIDATIONQUERY = "sharding.dataSources[%s].validationQuery";
    public static final String SHARDING_TABLECONFIGS_NAME = "sharding.tableConfigs[%s].name";
    public static final String SHARDING_TABLECONFIGS_CLOUMN = "sharding.tableConfigs[%s].shardingColumn";
    public static final String SHARDING_TABLECONFIGS_SIZE = "sharding.tableConfigs[%s].size";
    public static final String SHARDING_TABLECONFIGS_FORMAT = "sharding.tableConfigs[%s].format";
    public static final String SHARDING_TABLECONFIGS_STRATEGY = "sharding.tableConfigs[%s].strategy";
    /***
     * 关于应用名称配置的路径
     */
    private static final String APP_PROPERTIES_CLASSPATH = "/META-INF/app.properties";
    private static final String APP_PROPERTIES_KEY = "app.id";
    private static final String APP_PROPERTIES_ENV_PATH = "classpath*:META-INF/titans/env-*.properties";
    private static final String APP_PROPERTIES_ENV_PATH_SUFFIX = ".properties";

    private static String appName;
    private static final Map<String, Properties> propertiesMap = new HashMap<>();
    private static Map<String, String> apolloMap = new HashMap<>();

    private static Properties properties;

    /**
     * 通过Key 获取一个框架内置的配置信息
     *
     * @param key 配置的Key
     * @return 返回一个配置信息, 如果找不到则为空
     */
    public static String getProperty(String key) {
        if (apolloMap.get(key) != null) {  //其次读取apollo线上配置或本机缓存配置
            return apolloMap.get(key);
        }
        if (properties == null) {
            properties = propertiesMap.get(getEnv());
            if (properties == null) {
                properties = new Properties();
            }
        }
        return properties.getProperty(key);
    }

    /**
     * 通过Key 获取一个配置信息 优先读取用户的配置
     *
     * @param env 用户的环境配置类
     * @param key 需要获取的属性KEY
     * @return 返回一个配置信息, 如果找不到则为空
     */
    public static String getProperty(ConfigurableEnvironment env, String key) {
        if (env.getProperty(key) != null) {  //优先读取用户自定义配置
            return env.getProperty(key);
        } else {
            return getProperty(key); //最后读取系统内置配置文件配置.
        }
    }

    /**
     * 设置一个框架内置的配置信息,一般框架内部遇到特殊的配置信息时需要通过编码的方式设置配置信息,则可调用此方法
     *
     * @param key   设置的配置信息的key
     * @param value 设置配置信息的value
     */
    public static void setProperty(String key, String value) {
        if (properties == null) {
            properties = propertiesMap.get(getEnv());
            if (properties == null) {
                properties = new Properties();
            }
        }
        properties.setProperty(key, value);
    }

    /***
     * 获取当前的环境 已知道的有dev fat uat pro
     * @return 返回当前环境字符串, 默认为dev
     */
    public static String getEnv() {
        final String env = System.getProperty("env");
        return env == null ? "dev" : env;
    }

    /***
     * 获取应用名称,一般框架各组件需要依赖应用名称时可调用此方法获取
     * @return 返回一个应用名称 读取META-INF/app.properties中的app.id属性
     */
    public static String getAppName() {
        if (appName == null) {
            final String appId = System.getProperty(APP_PROPERTIES_KEY);
            if (appId != null) {
                appName = appId;
            } else {
                Properties props = new Properties();
                try {
                    props.load(EnvironmentManager.class.getResourceAsStream(APP_PROPERTIES_CLASSPATH));
                } catch (Exception e) {
                    throw new ErrorCodeException(e, ExceptionEnum.FILE_EXCEPTION, "读取app.properties");
                }
                appName = props.getProperty(APP_PROPERTIES_KEY);
            }
        }
        return appName;
    }


    static {
        //读取titans内置的配置文件
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
        	//读取指定目录下面的配置文件
            Resource[] resources = resolver.getResources(APP_PROPERTIES_ENV_PATH);
            if (resources != null && resources.length > 0) {
                for (Resource resource : resources) {
                    Properties props = new Properties();
                    props.load(resource.getInputStream());
                    //key: 属性文件名称 --- value: Properties
                    propertiesMap.put(getEnvPropertyFileName(resource), props);
                }
            }
        } catch (IOException e) {
            throw new ErrorCodeException(e, ExceptionEnum.INITIALIZATION_EXCEPTION, "资源文件");
        }
        //读取Apollo中的配置
        try {
            String url = getProperty("apollo.http.url");
            String result = readApolloConfig(url);
            EnvironmentManager.apolloMap = parseApolloStrResult(result);
            createOrModifyCacheFile(result);
        } catch (Exception e) {
            EnvironmentManager.apolloMap = readTitansCacheConfig();
        }
    }

    /**
     * 读取Apollo 的配置
     *
     * @param url
     * @return
     */
    private static String readApolloConfig(String url) throws IOException {
        String result = "";
        BufferedReader in = null;
        try {
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.connect();
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            throw e;
        } finally {
            in.close();
        }
        return result;
    }

    /**
     * 解析Apollo请求响应的结果集
     *
     * @param result
     * @return
     */
    public static Map<String, String> parseApolloStrResult(String result) {
        JSONObject jsonObject = JSONObject.parseObject(result);
        String configurations = jsonObject.getString("configurations");
        jsonObject = JSONObject.parseObject(configurations);
        Map<String, String> apolloMap = jsonObject.toJavaObject(Map.class);
        return apolloMap;
    }

    /**
     * 从用户目录下读取titans 的缓存文件
     *
     * @return
     */
    public static Map<String, String> readTitansCacheConfig() {
        Map<String, String> apolloMap = new HashMap<>();
        File titansFile = new File(TITANS_FILE_PATH);
        try {
            if (titansFile.exists() && FileUtil.canRead(titansFile)) {
                String result = FileUtils.readFileToString(titansFile);
                if (StringUtils.isNotEmpty(result)) {
                    result.trim();
                    apolloMap = parseApolloStrResult(result);
                } else {
                    logger.error("titan缓存配置读取失败,未读取到任何配置");
                }
            }
        } catch (Exception e) {
            logger.error("titan缓存配置读取失败。错误消息=" + e.getMessage());
        }
        return apolloMap;
    }

    public static void createOrModifyCacheFile(String json) {

        File folderTitans = new File(USER_HOME_PATH + "/.titans");
        try {
            //判断文件夹是否存在
            if (folderTitans.exists() && FileUtil.canRead(folderTitans) && FileUtil.canWrite(folderTitans)) {
                File titansFile = new File(TITANS_FILE_PATH);
                if (titansFile.exists()) {
                    if (StringUtils.isNotEmpty(json) && FileUtil.canWrite(titansFile) && FileUtil.canRead(titansFile)) {
                        //删除已存在的文件
                        FileUtil.deleteFile(titansFile);
                        File file = new File(TITANS_FILE_PATH);
                        //创建新的文件
                        file.createNewFile();
                        //写入文件内容
                        FileUtils.writeStringToFile(file, json);
                    }
                } else {
                    File file = new File(TITANS_FILE_PATH);
                    //创建新的文件
                    file.createNewFile();
                    //写入文件内容
                    FileUtils.writeStringToFile(file, json);
                }
            } else {
                File userHomeFile = new File(USER_HOME_PATH);
                if (FileUtil.canWrite(userHomeFile) && FileUtil.canWrite(userHomeFile)) {
                    folderTitans.mkdir();
                    File file = new File(TITANS_FILE_PATH);
                    //创建新的文件
                    file.createNewFile();
                    //写入文件内容
                    FileUtils.writeStringToFile(file, json);
                }
            }
        } catch (Exception e) {
            logger.error("titan缓存配置文件创建或更新-异常 错误信息=" + e.getMessage());
        }
    }


    private static String getEnvPropertyFileName(Resource resource) {
        return resource.getFilename().substring(4, resource.getFilename().indexOf(APP_PROPERTIES_ENV_PATH_SUFFIX));
    }


}
