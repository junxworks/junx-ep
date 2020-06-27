/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  EnableYrxdShiroProxy.java   
 * @Package com.yrxd.security.app.shiro   
 * @Description: (用一句话描述该文件做什么)   
 * @author: Administrator
 * @date:   2019-1-17 10:13:13   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * 开启业如小贷的shiro认证机制
 *
 * @ClassName:  EnableYrxdShiroProxy
 * @author: 王兴
 * @date:   2019-1-17 10:13:13
 * @since:  v1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(EPShiroConfiguration.class)
public @interface EnableEPShiroProxy {

}
