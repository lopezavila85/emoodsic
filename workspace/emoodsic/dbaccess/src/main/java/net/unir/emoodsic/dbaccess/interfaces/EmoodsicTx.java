/**
 * 
 */
package net.unir.emoodsic.dbaccess.interfaces;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Transactional;

import net.unir.emoodsic.common.classes.EmoodsicException;
import net.unir.emoodsic.dbaccess.definitions.DbAccessDefs;

/**
 * @author Álvaro
 *
 */

@Target({ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value = DbAccessDefs.EMOODSIC_TM,
    rollbackFor = {EmoodsicException.class, DataAccessException.class })
public @interface EmoodsicTx {
}
