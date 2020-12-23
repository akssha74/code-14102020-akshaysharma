package com.relayr.pcs.util;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/**
 * @author asharma2
 *
 */
public class UniqueIdGenerator implements IdentifierGenerator {

	/**
	 *Returns unique identifier
	 */
	@Override
	public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        Serializable id = session.getEntityPersister(null, obj).getClassMetadata().getIdentifier(obj, session);
        return !CommonUtils.isNull(id) ? id : UUID.randomUUID().toString();
	}
}