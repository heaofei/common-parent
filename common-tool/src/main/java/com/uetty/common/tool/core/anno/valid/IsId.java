package com.uetty.common.tool.core.anno.valid;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author vince
 * <p>为了方便阅读，这里的中文暂时没有用unicode编码，正式使用的时候再修改掉
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
public @interface IsId {
	
	String msg() default "ID非法";
}
