package com.cydeer.task;

import java.lang.annotation.*;

/**
 * Created by zhangsong on 16/9/25.
 * 定时任务注解,使用该注解到定时任务的bean上面,有该注解的bean,会当作定时任务记录下来。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Task {
}
