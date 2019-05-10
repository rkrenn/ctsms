// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.query.CriteriaUtil;
import org.phoenixctms.ctsms.query.SubCriteriaMap;
import org.phoenixctms.ctsms.vo.PSFVO;
import org.phoenixctms.ctsms.vo.TrialOutVO;
import org.phoenixctms.ctsms.vo.TrialTagVO;
import org.phoenixctms.ctsms.vo.TrialTagValueInVO;
import org.phoenixctms.ctsms.vo.TrialTagValueOutVO;
import org.phoenixctms.ctsms.vo.UserOutVO;

/**
 * @see TrialTagValue
 */
public class TrialTagValueDaoImpl
		extends TrialTagValueDaoBase {

	private org.hibernate.Criteria createTagValueCriteria() {
		org.hibernate.Criteria tagValueCriteria = this.getSession().createCriteria(TrialTagValue.class);
		return tagValueCriteria;
	}

	@Override
	protected Collection<TrialTagValue> handleFindByTrial(Long trialId,
			PSFVO psf) throws Exception {
		org.hibernate.Criteria tagValueCriteria = createTagValueCriteria();
		SubCriteriaMap criteriaMap = new SubCriteriaMap(TrialTagValue.class, tagValueCriteria);
		if (trialId != null) {
			tagValueCriteria.add(Restrictions.eq("trial.id", trialId.longValue()));
		}
		CriteriaUtil.applyPSFVO(criteriaMap, psf);
		return tagValueCriteria.list();
	}

	@Override
	protected Collection<TrialTagValue> handleFindByTrialExcelPayoffsSorted(Long trialId,
			Boolean payoffs, Boolean excel) throws Exception {
		org.hibernate.Criteria tagValueCriteria = createTagValueCriteria();
		tagValueCriteria.add(Restrictions.eq("trial.id", trialId.longValue()));
		Criteria tagCriteria = tagValueCriteria.createCriteria("tag", CriteriaSpecification.INNER_JOIN);
		if (excel != null) {
			tagCriteria.add(Restrictions.eq("excel", excel.booleanValue()));
		}
		if (payoffs != null) {
			tagCriteria.add(Restrictions.eq("payoffs", payoffs.booleanValue()));
		}
		tagCriteria.addOrder(Order.asc("nameL10nKey"));
		return tagValueCriteria.list();
	}

	@Override
	protected long handleGetCount(Long trialId,
			Boolean payoffs, Boolean excel) throws Exception {
		org.hibernate.Criteria tagValueCriteria = createTagValueCriteria();
		if (trialId != null) {
			tagValueCriteria.add(Restrictions.eq("trial.id", trialId.longValue()));
		}
		if (excel != null || payoffs != null) {
			Criteria tagCriteria = tagValueCriteria.createCriteria("tag", CriteriaSpecification.INNER_JOIN);
			if (excel != null) {
				tagCriteria.add(Restrictions.eq("excel", excel.booleanValue()));
			}
			if (payoffs != null) {
				tagCriteria.add(Restrictions.eq("payoffs", payoffs.booleanValue()));
			}
		}
		return (Long) tagValueCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private TrialTagValue loadTrialTagValueFromTrialTagValueInVO(TrialTagValueInVO trialTagValueInVO) {
		// TODO implement loadTrialTagValueFromTrialTagValueInVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadTrialTagValueFromTrialTagValueInVO(TrialTagValueInVO) not yet implemented.");
		TrialTagValue trialTagValue = null;
		Long id = trialTagValueInVO.getId();
		if (id != null) {
			trialTagValue = this.load(id);
		}
		if (trialTagValue == null) {
			trialTagValue = TrialTagValue.Factory.newInstance();
		}
		return trialTagValue;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private TrialTagValue loadTrialTagValueFromTrialTagValueOutVO(TrialTagValueOutVO trialTagValueOutVO) {
		// TODO implement loadTrialTagValueFromTrialTagValueOutVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadTrialTagValueFromTrialTagValueOutVO(TrialTagValueOutVO) not yet implemented.");
		TrialTagValue trialTagValue = this.load(trialTagValueOutVO.getId());
		if (trialTagValue == null) {
			trialTagValue = TrialTagValue.Factory.newInstance();
		}
		return trialTagValue;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public TrialTagValueInVO toTrialTagValueInVO(final TrialTagValue entity) {
		return super.toTrialTagValueInVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toTrialTagValueInVO(
			TrialTagValue source,
			TrialTagValueInVO target) {
		super.toTrialTagValueInVO(source, target);
		TrialTag tag = source.getTag();
		Trial trial = source.getTrial();
		if (tag != null) {
			target.setTagId(tag.getId());
		}
		if (trial != null) {
			target.setTrialId(trial.getId());
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public TrialTagValueOutVO toTrialTagValueOutVO(final TrialTagValue entity) {
		return super.toTrialTagValueOutVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toTrialTagValueOutVO(
			TrialTagValue source,
			TrialTagValueOutVO target) {
		super.toTrialTagValueOutVO(source, target);
		// WARNING! No conversion for target.trial (can't convert source.getTrial():org.phoenixctms.ctsms.domain.Trial to org.phoenixctms.ctsms.vo.TrialOutVO
		// WARNING! No conversion for target.tag (can't convert source.getTag():org.phoenixctms.ctsms.domain.TrialTag to org.phoenixctms.ctsms.vo.TrialTagVO
		// WARNING! No conversion for target.modifiedUser (can't convert source.getModifiedUser():org.phoenixctms.ctsms.domain.User to org.phoenixctms.ctsms.vo.UserOutVO
		TrialTag tag = source.getTag();
		Trial trial = source.getTrial();
		User modifiedUser = source.getModifiedUser();
		if (tag != null) {
			target.setTag(this.getTrialTagDao().toTrialTagVO(tag));
		}
		if (trial != null) {
			target.setTrial(this.getTrialDao().toTrialOutVO(trial));
		}
		if (modifiedUser != null) {
			target.setModifiedUser(this.getUserDao().toUserOutVO(modifiedUser));
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public TrialTagValue trialTagValueInVOToEntity(TrialTagValueInVO trialTagValueInVO) {
		TrialTagValue entity = this.loadTrialTagValueFromTrialTagValueInVO(trialTagValueInVO);
		this.trialTagValueInVOToEntity(trialTagValueInVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void trialTagValueInVOToEntity(
			TrialTagValueInVO source,
			TrialTagValue target,
			boolean copyIfNull) {
		super.trialTagValueInVOToEntity(source, target, copyIfNull);
		Long tagId = source.getTagId();
		Long trialId = source.getTrialId();
		if (tagId != null) {
			target.setTag(this.getTrialTagDao().load(tagId));
		} else if (copyIfNull) {
			target.setTag(null);
		}
		if (trialId != null) {
			Trial trial = this.getTrialDao().load(trialId);
			target.setTrial(trial);
			trial.addTagValues(target);
		} else if (copyIfNull) {
			Trial trial = target.getTrial();
			target.setTrial(null);
			if (trial != null) {
				trial.removeTagValues(target);
			}
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public TrialTagValue trialTagValueOutVOToEntity(TrialTagValueOutVO trialTagValueOutVO) {
		TrialTagValue entity = this.loadTrialTagValueFromTrialTagValueOutVO(trialTagValueOutVO);
		this.trialTagValueOutVOToEntity(trialTagValueOutVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void trialTagValueOutVOToEntity(
			TrialTagValueOutVO source,
			TrialTagValue target,
			boolean copyIfNull) {
		super.trialTagValueOutVOToEntity(source, target, copyIfNull);
		TrialTagVO tagVO = source.getTag();
		TrialOutVO trialVO = source.getTrial();
		UserOutVO modifiedUserVO = source.getModifiedUser();
		if (tagVO != null) {
			target.setTag(this.getTrialTagDao().trialTagVOToEntity(tagVO));
		} else if (copyIfNull) {
			target.setTag(null);
		}
		if (trialVO != null) {
			Trial trial = this.getTrialDao().trialOutVOToEntity(trialVO);
			target.setTrial(trial);
			trial.addTagValues(target);
		} else if (copyIfNull) {
			Trial trial = target.getTrial();
			target.setTrial(null);
			if (trial != null) {
				trial.removeTagValues(target);
			}
		}
		if (modifiedUserVO != null) {
			target.setModifiedUser(this.getUserDao().userOutVOToEntity(modifiedUserVO));
		} else if (copyIfNull) {
			target.setModifiedUser(null);
		}
	}
}