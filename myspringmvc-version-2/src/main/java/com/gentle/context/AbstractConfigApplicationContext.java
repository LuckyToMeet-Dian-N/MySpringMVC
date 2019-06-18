package com.gentle.context;


import com.gentle.beanfactory.BeanFactory;
import com.gentle.beanfactory.DefaultBeanFactory;

/**
 * @author Gentle
 * @date 2019/06/18 : 14:18
 */
public abstract class AbstractConfigApplicationContext extends AbstractApplicationContext{

    private DefaultBeanFactory beanFactory;

    @Override
    public DefaultBeanFactory getBeanFactory() {
        if (beanFactory==null){
            createBeanFactory();
        }
        return beanFactory;
    }

    /**
     * 创建 beanFactory 对象
     */
    private void createBeanFactory() {

        synchronized (this) {
            if (beanFactory==null){
                this.beanFactory =new DefaultBeanFactory();
            }
        }
    }



}
