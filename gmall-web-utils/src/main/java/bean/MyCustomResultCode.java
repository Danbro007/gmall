package bean;

/**
 * @author Danrbo
 * @date 2019/10/21 16:14
 * description
 **/
public interface MyCustomResultCode {
    /**
     * 获得异常信息
     * @return 异常信息
     */
    String getMessage();

    /**
     * 获得异常代码
     * @return 异常代码
     */
    Integer getCode();

}
