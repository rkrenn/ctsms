// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.compare.FieldComparator;
import org.phoenixctms.ctsms.util.CommonUtil;
import org.phoenixctms.ctsms.vo.OpsSystBlockVO;
import org.phoenixctms.ctsms.vo.OpsSystCategoryVO;
import org.phoenixctms.ctsms.vo.OpsSystVO;

/**
 * @see OpsSyst
 */
public class OpsSystDaoImpl
		extends OpsSystDaoBase {

	// Notation: erste drei Stellen numerisch, 4. Stelle alphanumerisch, 5./6. Stelle numerisch oder: x - sonstige Prozeduren, y - nicht naeher bezeichnet
	private final static Pattern OPS_CODE_PATTERN_REGEXP = Pattern.compile("^(\\d-)(\\d{2,2}[\\da-z])((\\.[\\da-z])([\\da-z])?)?(#)?$");

	private org.hibernate.Criteria createOpsSystCriteria() {
		org.hibernate.Criteria opsSystCriteria = this.getSession().createCriteria(OpsSyst.class);
		return opsSystCriteria;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	protected OpsSyst handleFindByOpsCode(String firstCode, String secondCode, String revision) {
		String code = CommonUtil.isEmptyString(firstCode) ? secondCode : firstCode;
		if (!CommonUtil.isEmptyString(code)) {
			Matcher matcher = OPS_CODE_PATTERN_REGEXP.matcher(code);
			if (matcher.find()) {
				StringBuilder search = new StringBuilder(matcher.group(1));
				search.append(matcher.group(2));
				String detail = matcher.group(3);
				if (detail != null && detail.length() > 0) {
					search.append(detail);
				}
				org.hibernate.Criteria opsSystCriteria = createOpsSystCriteria();
				opsSystCriteria.setCacheable(true);
				opsSystCriteria.add(Restrictions.eq("revision", revision));
				org.hibernate.Criteria categoriesCriteria = opsSystCriteria.createCriteria("categories");
				categoriesCriteria.add(Restrictions.eq("code", search.toString()));
				categoriesCriteria.add(Restrictions.eq("last", true));
				opsSystCriteria.setMaxResults(1);
				return (OpsSyst) opsSystCriteria.uniqueResult();
			}
		}
		return null;
	}

	@Override
	protected long handleGetProcedureCount(String revision) throws Exception {
		org.hibernate.Criteria opsSystCriteria = createOpsSystCriteria();
		opsSystCriteria.add(Restrictions.eq("revision", revision));
		opsSystCriteria.createCriteria("codes", CriteriaSpecification.INNER_JOIN)
				.createCriteria("procedures", CriteriaSpecification.INNER_JOIN);
		return (Long) opsSystCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	public void handleRemoveAllTxn(Set<OpsSyst> opsSysts) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			Iterator<OpsSyst> it = opsSysts.iterator();
			while (it.hasNext()) {
				removeOpsSyst(it.next().getId());
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void handleRemoveTxn(Long opsSystId) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			removeOpsSyst(opsSystId);
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	@Override
	public void handleRemoveTxn(OpsSyst opsSyst) throws Exception {
		Transaction transaction = this.getSession(true).beginTransaction();
		try {
			removeOpsSyst(opsSyst.getId());
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			throw e;
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private OpsSyst loadOpsSystFromOpsSystVO(OpsSystVO opsSystVO) {
		// TODO implement loadOpsSystFromOpsSystVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadOpsSystFromOpsSystVO(OpsSystVO) not yet implemented.");
		Long id = opsSystVO.getId();
		OpsSyst opsSyst = null;
		if (id != null) {
			opsSyst = this.load(id);
		}
		if (opsSyst == null) {
			opsSyst = OpsSyst.Factory.newInstance();
		}
		return opsSyst;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public OpsSyst opsSystVOToEntity(OpsSystVO opsSystVO) {
		OpsSyst entity = this.loadOpsSystFromOpsSystVO(opsSystVO);
		this.opsSystVOToEntity(opsSystVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void opsSystVOToEntity(
			OpsSystVO source,
			OpsSyst target,
			boolean copyIfNull) {
		super.opsSystVOToEntity(source, target, copyIfNull);
		Collection blocks = source.getBlocks();
		if (blocks.size() > 0) {
			this.getOpsSystBlockDao().opsSystBlockVOToEntityCollection(blocks);
			source.setBlocks(blocks);
		} else if (copyIfNull) {
			target.getBlocks().clear();
		}
		Collection categories = source.getCategories();
		if (categories.size() > 0) {
			this.getOpsSystCategoryDao().opsSystCategoryVOToEntityCollection(categories);
			source.setCategories(categories);
		} else if (copyIfNull) {
			target.getCategories().clear();
		}
	}

	private void removeOpsSyst(Long opsSystId) {
		OpsSyst opsSyst = get(opsSystId);
		this.getHibernateTemplate().deleteAll(opsSyst.getCodes()); // constraint error if used by procedure
		opsSyst.getCodes().clear();
		// Long codesIt = opsSyst.getCodes().iterator();
		// while (codesIt.hasNext()) {
		// Long code = codesIt.next();
		// code.setSystematics(null);
		// this.getHibernateTemplate().update(code);
		// }
		// opsSyst.getCodes().clear();
		Iterator<OpsSystCategory> it = opsSyst.getCategories().iterator();
		while (it.hasNext()) {
			this.getHibernateTemplate().deleteAll(it.next().getModifiers());
		}
		this.getHibernateTemplate().deleteAll(opsSyst.getCategories());
		this.getHibernateTemplate().deleteAll(opsSyst.getBlocks());
		this.getHibernateTemplate().delete(opsSyst);
	}

	private ArrayList<OpsSystBlockVO> toOpsSystBlockVOCollection(Collection<OpsSystBlock> blocks) {
		// related to http://forum.andromda.org/viewtopic.php?t=4288
		OpsSystBlockDao opsSystBlockDao = this.getOpsSystBlockDao();
		ArrayList<OpsSystBlockVO> result = new ArrayList<OpsSystBlockVO>(blocks.size());
		Iterator<OpsSystBlock> it = blocks.iterator();
		while (it.hasNext()) {
			result.add(opsSystBlockDao.toOpsSystBlockVO(it.next()));
		}
		Collections.sort(result, new FieldComparator(false, "getLevel"));
		return result;
	}

	private ArrayList<OpsSystCategoryVO> toOpsSystCategoryVOCollection(Collection<OpsSystCategory> categories) {
		// related to http://forum.andromda.org/viewtopic.php?t=4288
		OpsSystCategoryDao opsSystCategoryDao = this.getOpsSystCategoryDao();
		ArrayList<OpsSystCategoryVO> result = new ArrayList<OpsSystCategoryVO>(categories.size());
		Iterator<OpsSystCategory> it = categories.iterator();
		while (it.hasNext()) {
			result.add(opsSystCategoryDao.toOpsSystCategoryVO(it.next()));
		}
		Collections.sort(result, new FieldComparator(false, "getLevel"));
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public OpsSystVO toOpsSystVO(final OpsSyst entity) {
		return super.toOpsSystVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toOpsSystVO(
			OpsSyst source,
			OpsSystVO target) {
		super.toOpsSystVO(source, target);
		// WARNING! No conversion for target.blocks (can't convert source.getBlocks():org.phoenixctms.ctsms.domain.OpsSystBlock to org.phoenixctms.ctsms.vo.OpsSystBlockVO
		// WARNING! No conversion for target.categories (can't convert source.getCategories():org.phoenixctms.ctsms.domain.OpsSystCategory to org.phoenixctms.ctsms.vo.OpsSystCategoryVO
		target.setBlocks(toOpsSystBlockVOCollection(source.getBlocks()));
		target.setCategories(toOpsSystCategoryVOCollection(source.getCategories()));
	}
}