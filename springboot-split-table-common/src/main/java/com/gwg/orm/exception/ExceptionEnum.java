package com.gwg.orm.exception;


/**
 * <p>Class: ErrorCode</p>
 * <p>Description: 异常Enum,定义各类常用的异常</p>
 */
public enum ExceptionEnum implements ErrorCode {

    INITIALIZATION_EXCEPTION("SYS_000", "{0}初始化失败，参数列表:{1}"),
    PARAMETER_EXCEPTION("SYS_001", "{0}参数异常,参数列表:{1}"),
    FILE_EXCEPTION("SYS_002", "{0}操作文件发生异常"),

    HTTP_UNKNOWN_ERROR("SOA_001", "HttpClient未知错误"),
    HTTP_PARAM_ERROR("SOA_002", "参数有且只能有一个"),
    HTTP_ANNOTATION_PARAM_ERROR("SOA_003", "HttpParam注解与每个形参相对应"),
    HTTP_NNOTATION_LACK_ERROR("SOA_004", "目标方法必须标注一个注解(HttpExecute或HttpJsonExecute)"),
    HTTP_ANNOTATION_ERROR("SOA_005", "目标方法只能标注一个注解(HttpExecute或HttpJsonExecute)"),
    HTTP_REQUEST_ERROR("SOA_006", "HTTP请求错误,errorMessage:{0}"),
    HTTP_REQUEST_CONSTRUCTOR_ERROR("SOA_007", "Http 请求body构造器配置异常 必须为HttpRequestBodyConstructor 的实现类并注册为spring容器管理的bean"),
    HTTP_REQUEST_ENTITY_ERROR("SOA_008", "Http请求实体错误"),
    HTTP_CONSTRUCTOR_CREATE_ERROR("SOA_009","Http请求构造器创建错误"),

    ORM_TRANSACTION_ERROR("ORM_001", "存在多个TransactionManager Bean 请在@MultiTransactional 中明确指定"),
    ORM_CONFIG_NAME_ERROR("ORM_002", "@MultiTransactional 中指定的configName 错误");

    private String code;
    private String message;

    ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }


}
