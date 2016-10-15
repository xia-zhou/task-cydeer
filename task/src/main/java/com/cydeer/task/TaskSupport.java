package com.cydeer.task;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;
import com.cydeer.task.api.TaskDispatcher;
import com.cydeer.task.impl.TaskDispatcherImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * 收集系统的所有定时任务,存放到本地map中。暴漏soa接口供task系统调用。
 * Created by zhangsong on 16/9/25.
 */
public class TaskSupport
		implements BeanPostProcessor, ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {

	private final static Logger LOGGER = LoggerFactory.getLogger(TaskSupport.class);
	/**
	 * 上下文环境
	 */
	private ApplicationContext context;

	/**
	 * <dubbo:registry></dubbo:registry>
	 */
	private RegistryConfig registryConfig;

	/**
	 * 系统的soa配置信息 对应xml中的<dubbo:application></dubbo:application>
	 */
	private ApplicationConfig applicationConfig;
	/**
	 * <dubbo:registry protocol=""></dubbo:registry>
	 */
	private ProtocolConfig protocolConfig;

	/**
	 * 定时任务 本地存储map。
	 */
	private Map<String, Object> taskBeanMap = new HashMap<String, Object>();

	/**
	 * 缓存配置
	 */
	private ServiceConfig<Object> serviceConfig;

	@Override public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		collectTask(bean, beanName);
		return bean;
	}

	/**
	 * 对于aop的bean,不做定时任务检查,收集
	 */
	/*private Object getTarget(Object bean) {
		Object target = bean;
		while (target instanceof Advised) {
			try {
				target = ((Advised) bean).getTargetSource().getTarget();
			} catch (Exception e) {
				target = null;
				break;
			}
		}
		return target;
	}*/

	/**
	 * 收集所有的定时任务 有@task注解的bean。该bean可以用过xml配置,不一定是要使用@service注解的bean
	 *
	 * @param bean     bean
	 * @param beanName bean名字
	 */
	private void collectTask(Object bean, String beanName) {
		if (bean != null) {
			Class<?> clazz = bean.getClass();
			if (!clazz.isAnnotationPresent(Task.class)) {
				return;
			}
			if (!taskBeanMap.containsKey(beanName)) {
				LOGGER.info("add task bean to local map,beanName:{}", beanName);
				taskBeanMap.put(beanName, bean);
			}
		}
	}

	@Override public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.context = applicationContext;
	}

	@Override public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext() == context) {
			// 程序暴漏 soa接口
			exportTaskDispatcher();
		}

	}

	/**
	 * 构造任务分配bean的代理,并发布成soa接口
	 */
	private void exportTaskDispatcher() {
		if (serviceConfig != null && serviceConfig.isExported()) {
			return;
		}
		applicationConfig = context.getBean(ApplicationConfig.class);
		protocolConfig = context.getBean(ProtocolConfig.class);
		registryConfig = context.getBean(RegistryConfig.class);
		LOGGER.info("create proxy for appName:{}", applicationConfig.getName());
		// 构造 taskDispatcherBean
		TaskDispatcherImpl taskDispatcher = wireTaskDispatcherProxy();
		exportServiceConfig(taskDispatcher);
	}

	/**
	 * 暴漏soa接口
	 * 使用dubbo的export接口暴漏服务
	 *
	 * @param taskDispatcher 具体的 任务分配者代理bean
	 */
	private void exportServiceConfig(TaskDispatcher taskDispatcher) {
		serviceConfig = new ServiceConfig<Object>();
		serviceConfig.setApplication(applicationConfig);
		serviceConfig.setRegistry(registryConfig);
		serviceConfig.setProtocol(protocolConfig);
		serviceConfig.setInterface(TaskDispatcher.class);
		serviceConfig.setRef(taskDispatcher);
		serviceConfig.setRetries(0);
		serviceConfig.setGroup(applicationConfig.getName());
		serviceConfig.export();
	}

	/**
	 * 生成 taskDispatcherBeanProxy
	 *
	 * @return 具体的taskDispatcherBeanProxy
	 */
	private TaskDispatcherImpl wireTaskDispatcherProxy() {
		AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
		// beanFactory 这里本身就是defaultListBeanFactory
		DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
		TaskDispatcherImpl taskDispatcherProxy = (TaskDispatcherImpl) beanFactory.createBean(
				TaskDispatcherImpl.class, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
		taskDispatcherProxy.setTaskBeanMap(taskBeanMap);
		taskDispatcherProxy.setApplicationConfig(applicationConfig);
		taskDispatcherProxy.setRegistryConfig(registryConfig);
		taskDispatcherProxy = (TaskDispatcherImpl) beanFactory
				.initializeBean(taskDispatcherProxy, "taskDispatcherProxy");
		defaultListableBeanFactory.registerSingleton("taskDispatcherProxy", taskDispatcherProxy);
		return taskDispatcherProxy;
	}
}
