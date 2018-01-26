/**
 * Copyright 2014-2015, NetEase, Inc. All Rights Reserved.
 * 
 * Date: 2017年5月14日
 */

package demo.mbassdor.bot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Desc:TODO 
 * @author wei.zw
 * @since 2017年5月14日 下午6:51:24
 * @version  v 0.1
 */
@Documented 
@Retention( RetentionPolicy.RUNTIME ) 
@Target( ElementType.TYPE ) 
public @interface HandlerContainer 
{ 
}
