/*
 ***************************************************************************************
 * All rights Reserved, Designed By www.cqhyrc.com.cn
 * @Title:  NotColumn.java   
 * @Package io.github.junxworks.ep.core.annotations   
 * @Description: (用一句话描述该文件做什么)   
 * @author: AOC
 * @date:   2019-9-29 13:24:07   
 * @version V1.0 
 * @Copyright: 2019 重庆华宇集团. All rights reserved. 
 * 注意：本内容仅限于公司内部使用，禁止外泄以及用于其他的商业目
 *  ---------------------------------------------------------------------------------- 
 * 文件修改记录
 *     文件版本：         修改人：             修改原因：
 ***************************************************************************************
 */
package io.github.junxworks.ep.core.orm.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 不是字段，例如序列化id
 *
 * @ClassName:  NotColumn
 * @author: 王兴
 * @date:   2019-9-29 13:24:07
 * @since:  v1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotColumn {

}
