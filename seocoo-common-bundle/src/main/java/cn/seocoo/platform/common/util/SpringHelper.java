package cn.seocoo.platform.common.util;

import org.springframework.context.ApplicationContext;

public class SpringHelper {
    private SpringHelper(){}
    /**
     * 单态
     */
    private final static  SpringHelper springHelper=new SpringHelper();
    /**
     * spring bean容器
     */
    private ApplicationContext context;
    /**
     * 获取SpringHelper实例
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static SpringHelper getSpringHelper(){
        return springHelper;
    }
    /**
     * 初始化SpringHelper容器
     * <功能详细描述>
     * @param context spring容器变量
     * 根据环境不同分别可以是用 WebApplicationContext
     * 或者ClassPathApplicationContext进行初始化
     * @see [类、类#方法、类#成员]
     */
    public void initApplicationContext(ApplicationContext context){
        this.context=context;
    }
    
    /**
     * 根据spring中配置的beanid获取bean实例。
     * @param beanId
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Object getBean(String beanId){
       return this.context.getBean(beanId);
    }
}

