package com.cydeer.task.core.quartz;

import com.cydeer.task.core.api.TaskConsumerService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;

/**
 * Created by zhangsong on 2016/10/13.
 */
public class TaskQuartzJob implements Job {

	private static final String APPLICATION_CONTEXT_KEY = "taskApplicationContext";

	private volatile static boolean init = false;

	private TaskConsumerService taskConsumerService;

	@Override public void execute(JobExecutionContext context) throws JobExecutionException {
		initTaskConsumerService(context);
		String taskId = context.getJobDetail().getKey().getName();
		taskConsumerService.dispatch(Integer.valueOf(taskId));
	}

	private void initTaskConsumerService(JobExecutionContext context) throws JobExecutionException {
		ApplicationContext applicationContext = null;
		try {
			applicationContext = (ApplicationContext) context.getScheduler().getContext()
			                                                 .get(APPLICATION_CONTEXT_KEY);

		} catch (SchedulerException e) {
			e.printStackTrace();
		}
		if (applicationContext == null) {
			throw new JobExecutionException("没有获取到具体的ApplicationContext");
		}
		taskConsumerService = applicationContext.getBean(TaskConsumerService.class);
		init = true;
	}
}
