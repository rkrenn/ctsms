// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.compare.VOIDComparator;
import org.phoenixctms.ctsms.enumeration.InputFieldType;
import org.phoenixctms.ctsms.query.CriteriaUtil;
import org.phoenixctms.ctsms.query.SubCriteriaMap;
import org.phoenixctms.ctsms.util.CommonUtil;
import org.phoenixctms.ctsms.util.CoreUtil;
import org.phoenixctms.ctsms.util.DefaultSettings;
import org.phoenixctms.ctsms.util.L10nUtil;
import org.phoenixctms.ctsms.util.L10nUtil.Locales;
import org.phoenixctms.ctsms.util.ServiceUtil;
import org.phoenixctms.ctsms.util.SettingCodes;
import org.phoenixctms.ctsms.util.Settings;
import org.phoenixctms.ctsms.util.Settings.Bundle;
import org.phoenixctms.ctsms.vo.InputFieldSelectionSetValueJsonVO;
import org.phoenixctms.ctsms.vo.InputFieldSelectionSetValueOutVO;
import org.phoenixctms.ctsms.vo.InquiryOutVO;
import org.phoenixctms.ctsms.vo.InquiryValueInVO;
import org.phoenixctms.ctsms.vo.InquiryValueJsonVO;
import org.phoenixctms.ctsms.vo.InquiryValueOutVO;
import org.phoenixctms.ctsms.vo.PSFVO;
import org.phoenixctms.ctsms.vo.ProbandOutVO;
import org.phoenixctms.ctsms.vo.UserOutVO;

/**
 * @see InquiryValue
 */
public class InquiryValueDaoImpl
extends InquiryValueDaoBase
{

	private final static VOIDComparator SELECTION_SET_VALUE_OUT_VO_ID_COMPARATOR = new VOIDComparator<InputFieldSelectionSetValueOutVO>(false);
	private final static VOIDComparator SELECTION_SET_VALUE_JSON_VO_ID_COMPARATOR = new VOIDComparator<InputFieldSelectionSetValueJsonVO>(false);

	private static void applySortOrders(org.hibernate.Criteria inquiryCriteria) {
		if (inquiryCriteria != null) {
			inquiryCriteria.addOrder(Order.asc("trial"));
			inquiryCriteria.addOrder(Order.asc("category"));
			inquiryCriteria.addOrder(Order.asc("position"));
		}
	}

	private org.hibernate.Criteria createInquiryCriteria(Long probandId, Long trialId) {
		org.hibernate.Criteria inquiryCriteria = this.getSession().createCriteria(Inquiry.class, ServiceUtil.INQUIRY_VALUE_DAO_INQUIRY_ALIAS);
		inquiryCriteria.add(Restrictions.eq("trial.id", trialId.longValue()));

		org.hibernate.Criteria inquiryValueCriteria = inquiryCriteria.createCriteria("inquiryValues", ServiceUtil.INQUIRY_VALUE_DAO_INQUIRY_VALUE_ALIAS,
				CriteriaSpecification.LEFT_JOIN,
				Restrictions.eq(ServiceUtil.INQUIRY_VALUE_DAO_INQUIRY_VALUE_ALIAS + ".proband.id", probandId.longValue()));
		// criteriaMap.createCriteria("ecrfField", CriteriaSpecification.LEFT_JOIN);
		// inquiryValueCriteria.add(Restrictions.or(Restrictions.eq("proband.id", probandId.longValue()),
		// Restrictions.isNull("proband")));
		//		// correlated - slow:
		//		DetachedCriteria subQuery = createEcrfFieldValueDetachedCriteria(ecrfFieldValueCriteria, ecrfFieldCriteria, null, probandListEntryId, null);
		//		// DetachedCriteria subQuery = DetachedCriteria.forClass(ECRFFieldValueImpl.class, "ecrfFieldValue1"); // IMPL!!!!
		//		// subQuery.setProjection(Projections.max("id"));
		//		// subQuery.add(Restrictions.eq("listEntry.id", probandListEntryId.longValue()));
		//		// subQuery.add(Restrictions.eqProperty("ecrfField.id", "ecrfField0.id"));
		//		subQuery.add(Restrictions.or(Restrictions.isNull("index"),
		//				Restrictions.eqProperty("index", ServiceUtil.ECRF_FIELD_VALUE_DAO_ECRF_FIELD_VALUE_ALIAS + ".index")));
		//		ecrfFieldValueCriteria.add(Restrictions.or(
		//				Restrictions.isNull("proband"),
		//				Restrictions.and(
		//						Restrictions.eq("proband.id", probandId.longValue()),
		//						Subqueries.propertyIn("id", subQuery)
		//						)
		//				));

		// System.out.println(CriteriaUtil.criteriaToSql(ecrfFieldCriteria));
		return inquiryCriteria;
	}

	private org.hibernate.Criteria createInquiryValueCriteria() {
		org.hibernate.Criteria inquiryValueCriteria = this.getSession().createCriteria(InquiryValue.class);
		return inquiryValueCriteria;
	}

	@Override
	protected Collection<InquiryValue> handleFindByProbandInquiry(
			Long probandId, Long inquiryId) throws Exception {
		org.hibernate.Criteria inquiryValueCriteria = createInquiryValueCriteria();
		if (probandId != null) {
			inquiryValueCriteria.add(Restrictions.eq("proband.id", probandId.longValue()));
		}
		if (inquiryId != null) {
			inquiryValueCriteria.add(Restrictions.eq("inquiry.id", inquiryId.longValue()));
		}
		return inquiryValueCriteria.list();
	}

	@Override
	protected Collection<Map> handleFindByProbandTrialActiveJs(Long probandId, Long trialId, Boolean active, Boolean activeSignup, boolean sort, Boolean js, PSFVO psf)
			throws Exception {
		org.hibernate.Criteria inquiryCriteria = createInquiryCriteria(probandId, trialId);
		if (active != null) {
			inquiryCriteria.add(Restrictions.eq("active", active.booleanValue()));
		}
		if (activeSignup != null) {
			inquiryCriteria.add(Restrictions.eq("activeSignup", activeSignup.booleanValue()));
		}
		if (js != null) {
			if (js) {
				inquiryCriteria.add(Restrictions.and(Restrictions.ne("jsVariableName", ""), Restrictions.isNotNull("jsVariableName")));
			} else {
				inquiryCriteria.add(Restrictions.or(Restrictions.eq("jsVariableName", ""), Restrictions.isNull("jsVariableName")));
			}
		}
		if (psf != null) {
			SubCriteriaMap criteriaMap = new SubCriteriaMap(Inquiry.class, inquiryCriteria);
			// clear sort and filters?
			CriteriaUtil.applyPSFVO(criteriaMap, psf);
		}
		if (sort) {
			applySortOrders(inquiryCriteria);
		}
		inquiryCriteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return inquiryCriteria.list();
	}

	@Override
	protected Collection<Map> handleFindByProbandTrialCategoryActiveJs(Long probandId, Long trialId, String category, Boolean active, Boolean activeSignup, boolean sort,
			Boolean js, PSFVO psf) throws Exception {
		org.hibernate.Criteria inquiryCriteria = createInquiryCriteria(probandId, trialId);
		if (active != null) {
			inquiryCriteria.add(Restrictions.eq("active", active.booleanValue()));
		}
		if (activeSignup != null) {
			inquiryCriteria.add(Restrictions.eq("activeSignup", activeSignup.booleanValue()));
		}
		if (category != null && category.length() > 0) {
			inquiryCriteria.add(Restrictions.eq("category", category));
		} else {
			inquiryCriteria.add(Restrictions.or(Restrictions.eq("category", ""), Restrictions.isNull("category")));
		}
		if (js != null) {
			if (js) {
				inquiryCriteria.add(Restrictions.and(Restrictions.ne("jsVariableName", ""), Restrictions.isNotNull("jsVariableName")));
			} else {
				inquiryCriteria.add(Restrictions.or(Restrictions.eq("jsVariableName", ""), Restrictions.isNull("jsVariableName")));
			}
		}
		if (psf != null) {
			SubCriteriaMap criteriaMap = new SubCriteriaMap(Inquiry.class, inquiryCriteria);
			// clear sort and filters?
			CriteriaUtil.applyPSFVO(criteriaMap, psf);
		}
		if (sort) {
			applySortOrders(inquiryCriteria);
		}
		inquiryCriteria.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
		return inquiryCriteria.list();
	}

	@Override
	protected Collection<InquiryValue> handleFindByTrialActiveProbandField(
			Long trialId, Boolean active, Boolean activeSignup, Long probandId, Long inputFieldId) throws Exception {
		org.hibernate.Criteria inquiryValueCriteria = createInquiryValueCriteria();
		if (probandId != null) {
			inquiryValueCriteria.add(Restrictions.eq("proband.id", probandId.longValue()));
		}
		if (trialId != null || active != null || inputFieldId != null) {
			Criteria inquiryCriteria = inquiryValueCriteria.createCriteria("inquiry", CriteriaSpecification.INNER_JOIN);
			if (trialId != null) {
				inquiryCriteria.add(Restrictions.eq("trial.id", trialId.longValue()));
			}
			if (active != null) {
				inquiryCriteria.add(Restrictions.eq("active", active.booleanValue()));
			}
			if (activeSignup != null) {
				inquiryCriteria.add(Restrictions.eq("activeSignup", activeSignup.booleanValue()));
			}
			if (inputFieldId != null) {
				inquiryCriteria.add(Restrictions.eq("field.id", inputFieldId.longValue()));
			}
		}
		return inquiryValueCriteria.list();
	}

	@Override
	protected long handleGetCount(Long inquiryId) throws Exception {
		org.hibernate.Criteria inquiryValueCriteria = createInquiryValueCriteria();
		if (inquiryId != null) {
			inquiryValueCriteria.add(Restrictions.eq("inquiry.id", inquiryId.longValue()));
		}
		return (Long) inquiryValueCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	protected long handleGetCount(Long trialId, Boolean active, Boolean activeSignup, Long probandId) throws Exception {
		org.hibernate.Criteria inquiryValueCriteria = createInquiryValueCriteria();
		if (probandId != null) {
			inquiryValueCriteria.add(Restrictions.eq("proband.id", probandId.longValue()));
		}
		if (trialId != null || active != null) {
			org.hibernate.Criteria inquiryCriteria = inquiryValueCriteria.createCriteria("inquiry", CriteriaSpecification.INNER_JOIN);
			if (trialId != null) {
				inquiryCriteria.add(Restrictions.eq("trial.id", trialId.longValue()));
			}
			if (active != null) {
				inquiryCriteria.add(Restrictions.eq("active", active.booleanValue()));
			}
			if (activeSignup != null) {
				inquiryCriteria.add(Restrictions.eq("activeSignup", activeSignup.booleanValue()));
			}
		}
		return (Long) inquiryValueCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	protected long handleGetCount(Long trialId, String category, Boolean active, Boolean activeSignup, Long probandId) throws Exception {
		org.hibernate.Criteria inquiryValueCriteria = createInquiryValueCriteria();
		if (probandId != null) {
			inquiryValueCriteria.add(Restrictions.eq("proband.id", probandId.longValue()));
		}
		org.hibernate.Criteria inquiryCriteria = inquiryValueCriteria.createCriteria("inquiry", CriteriaSpecification.INNER_JOIN);
		if (trialId != null) {
			inquiryCriteria.add(Restrictions.eq("trial.id", trialId.longValue()));
		}
		if (active != null) {
			inquiryCriteria.add(Restrictions.eq("active", active.booleanValue()));
		}
		if (activeSignup != null) {
			inquiryCriteria.add(Restrictions.eq("activeSignup", activeSignup.booleanValue()));
		}
		if (category != null && category.length() > 0) {
			inquiryCriteria.add(Restrictions.eq("category", category));
		} else {
			inquiryCriteria.add(Restrictions.or(Restrictions.eq("category", ""), Restrictions.isNull("category")));
		}
		return (Long) inquiryValueCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	protected long handleGetCountByField(Long inputFieldId) throws Exception {
		org.hibernate.Criteria inquiryValueCriteria = createInquiryValueCriteria();
		if (inputFieldId != null) {
			org.hibernate.Criteria inquiryCriteria = inquiryValueCriteria.createCriteria("inquiry", CriteriaSpecification.INNER_JOIN);
			inquiryCriteria.add(Restrictions.eq("field.id", inputFieldId.longValue()));
		}
		return (Long) inquiryValueCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public InquiryValue inquiryValueInVOToEntity(InquiryValueInVO inquiryValueInVO)
	{
		InquiryValue entity = this.loadInquiryValueFromInquiryValueInVO(inquiryValueInVO);
		this.inquiryValueInVOToEntity(inquiryValueInVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void inquiryValueInVOToEntity(
			InquiryValueInVO source,
			InquiryValue target,
			boolean copyIfNull)
	{
		super.inquiryValueInVOToEntity(source, target, copyIfNull);
		Long probandId = source.getProbandId();
		Long inquiryId = source.getInquiryId();
		if (probandId != null) {
			Proband proband = this.getProbandDao().load(probandId);
			target.setProband(proband);
			proband.addInquiryValues(target);
		} else if (copyIfNull) {
			Proband proband = target.getProband();
			target.setProband(null);
			if (proband != null) {
				proband.removeInquiryValues(target);
			}
		}

		if (inquiryId != null) {
			Inquiry inquiry = this.getInquiryDao().load(inquiryId);
			target.setInquiry(inquiry);
			inquiry.addInquiryValues(target);
		} else if (copyIfNull) {
			Inquiry inquiry = target.getInquiry();
			target.setInquiry(null);
			if (inquiry != null) {
				inquiry.removeInquiryValues(target);
			}
		}
		InputFieldValue value = target.getValue();
		if (value == null) {
			value = InputFieldValue.Factory.newInstance();
			target.setValue(value);
		}
		if (copyIfNull || source.getTextValue() != null) {
			value.setStringValue(source.getTextValue());
			value.setTruncatedStringValue(CommonUtil.truncateStringValue(source.getTextValue(), Settings.getIntNullable(SettingCodes.INPUT_FIELD_TRUNCATED_STRING_VALUE_MAX_LENGTH,
					Bundle.SETTINGS, DefaultSettings.INPUT_FIELD_TRUNCATED_STRING_VALUE_MAX_LENGTH)));
		}
		value.setBooleanValue(source.getBooleanValue());
		// if (copyIfNull || source.getBooleanValue() != null) {
		// value.setBooleanValue(booleanValueIn)
		// }
		if (copyIfNull || source.getLongValue() != null) {
			value.setLongValue(source.getLongValue());
		}
		if (copyIfNull || source.getFloatValue() != null) {
			value.setFloatValue(source.getFloatValue());
		}
		if (copyIfNull || source.getDateValue() != null)
		{
			value.setDateValue(CoreUtil.forceDate(source.getDateValue()));
		}
		if (copyIfNull || source.getTimestampValue() != null)
		{
			value.setTimestampValue((source.getTimestampValue() == null ? null : new Timestamp(source.getTimestampValue().getTime())));
		}
		if (copyIfNull || source.getTimeValue() != null)
		{
			value.setTimeValue(CoreUtil.forceDate(source.getTimeValue()));
		}
		if (copyIfNull || source.getInkValues() != null) {
			value.setInkValue(source.getInkValues());
		}
		Collection selectionValueIds;
		if ((selectionValueIds = source.getSelectionValueIds()).size() > 0 || copyIfNull) {
			value.setSelectionValues(toInputFieldSelectionSetValueSet(selectionValueIds));
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public InquiryValue inquiryValueJsonVOToEntity(InquiryValueJsonVO inquiryValueJsonVO)
	{
		// TODO verify behavior of inquiryValueJsonVOToEntity
		InquiryValue entity = this.loadInquiryValueFromInquiryValueJsonVO(inquiryValueJsonVO);
		this.inquiryValueJsonVOToEntity(inquiryValueJsonVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void inquiryValueJsonVOToEntity(
			InquiryValueJsonVO source,
			InquiryValue target,
			boolean copyIfNull)
	{
		// TODO verify behavior of inquiryValueJsonVOToEntity
		super.inquiryValueJsonVOToEntity(source, target, copyIfNull);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public InquiryValue inquiryValueOutVOToEntity(InquiryValueOutVO inquiryValueOutVO)
	{
		InquiryValue entity = this.loadInquiryValueFromInquiryValueOutVO(inquiryValueOutVO);
		this.inquiryValueOutVOToEntity(inquiryValueOutVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void inquiryValueOutVOToEntity(
			InquiryValueOutVO source,
			InquiryValue target,
			boolean copyIfNull)
	{
		super.inquiryValueOutVOToEntity(source, target, copyIfNull);
		ProbandOutVO probandVO = source.getProband();
		InquiryOutVO inquiryVO = source.getInquiry();
		UserOutVO modifiedUserVO = source.getModifiedUser();
		if (probandVO != null) {
			Proband proband = this.getProbandDao().probandOutVOToEntity(probandVO);
			target.setProband(proband);
			proband.addInquiryValues(target);
		} else if (copyIfNull) {
			Proband proband = target.getProband();
			target.setProband(null);
			if (proband != null) {
				proband.removeInquiryValues(target);
			}
		}
		if (inquiryVO != null) {
			Inquiry inquiry = this.getInquiryDao().inquiryOutVOToEntity(inquiryVO);
			target.setInquiry(inquiry);
			inquiry.addInquiryValues(target);
		} else if (copyIfNull) {
			Inquiry inquiry = target.getInquiry();
			target.setInquiry(null);
			if (inquiry != null) {
				inquiry.removeInquiryValues(target);
			}
		}
		if (modifiedUserVO != null) {
			target.setModifiedUser(this.getUserDao().userOutVOToEntity(modifiedUserVO));
		} else if (copyIfNull) {
			target.setModifiedUser(null);
		}
		InputFieldValue value = target.getValue();
		if (value == null) {
			value = InputFieldValue.Factory.newInstance();
			target.setValue(value);
		}
		if (copyIfNull || source.getTextValue() != null) {
			value.setStringValue(source.getTextValue());
			value.setTruncatedStringValue(CommonUtil.truncateStringValue(source.getTextValue(), Settings.getIntNullable(SettingCodes.INPUT_FIELD_TRUNCATED_STRING_VALUE_MAX_LENGTH,
					Bundle.SETTINGS, DefaultSettings.INPUT_FIELD_TRUNCATED_STRING_VALUE_MAX_LENGTH)));
		}
		value.setBooleanValue(source.getBooleanValue());
		// if (copyIfNull || source.getBooleanValue() != null) {
		// value.setBooleanValue(booleanValueIn)
		// }
		if (copyIfNull || source.getLongValue() != null) {
			value.setLongValue(source.getLongValue());
		}
		if (copyIfNull || source.getFloatValue() != null) {
			value.setFloatValue(source.getFloatValue());
		}
		if (copyIfNull || source.getDateValue() != null) {
			value.setDateValue(CoreUtil.forceDate(source.getDateValue()));
		}
		if (copyIfNull || source.getTimestampValue() != null) {
			value.setTimestampValue((source.getTimestampValue() == null ? null : new Timestamp(source.getTimestampValue().getTime())));
		}
		if (copyIfNull || source.getTimeValue() != null) {
			value.setTimeValue(CoreUtil.forceDate(source.getTimeValue()));
		}
		if (copyIfNull || source.getInkValues() != null) {
			value.setInkValue(source.getInkValues());
		}
		Collection selectionValues;
		if ((selectionValues = source.getSelectionValues()).size() > 0 || copyIfNull) {
			this.getInputFieldSelectionSetValueDao().inputFieldSelectionSetValueOutVOToEntityCollection(selectionValues);
			value.setSelectionValues((Collection<InputFieldSelectionSetValue>) selectionValues); // hashset-exception!!!
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private InquiryValue loadInquiryValueFromInquiryValueInVO(InquiryValueInVO inquiryValueInVO)
	{
		// TODO implement loadInquiryValueFromInquiryValueInVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadInquiryValueFromInquiryValueInVO(InquiryValueInVO) not yet implemented.");
		InquiryValue inquiryValue = null;
		Long id = inquiryValueInVO.getId();
		if (id != null) {
			inquiryValue = this.load(id);
		}
		if (inquiryValue == null)
		{
			inquiryValue = InquiryValue.Factory.newInstance();
		}
		return inquiryValue;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private InquiryValue loadInquiryValueFromInquiryValueJsonVO(InquiryValueJsonVO inquiryValueJsonVO)
	{
		// TODO implement loadInquiryValueFromInquiryValueJsonVO
		throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadInquiryValueFromInquiryValueJsonVO(InquiryValueJsonVO) not yet implemented.");
		/* A typical implementation looks like this: InquiryValue inquiryValue = this.load(inquiryValueJsonVO.getId()); if (inquiryValue == null) { inquiryValue =
		 * InquiryValue.Factory.newInstance(); } return inquiryValue; */
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private InquiryValue loadInquiryValueFromInquiryValueOutVO(InquiryValueOutVO inquiryValueOutVO)
	{
		// TODO implement loadInquiryValueFromInquiryValueOutVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadInquiryValueFromInquiryValueOutVO(InquiryValueOutVO) not yet implemented.");
		InquiryValue inquiryValue = this.load(inquiryValueOutVO.getId());
		if (inquiryValue == null)
		{
			inquiryValue = InquiryValue.Factory.newInstance();
		}
		return inquiryValue;
	}

	// private ArrayList<Long> toInputFieldSelectionSetValueIdCollection(Collection<InputFieldSelectionSetValue> selectionValues) { // lazyload persistentset prevention
	// ArrayList<Long> result;
	// if (selectionValues != null && selectionValues.size() > 0) {
	// result = new ArrayList<Long>(selectionValues.size());
	// Iterator<InputFieldSelectionSetValue> it = selectionValues.iterator();
	// while (it.hasNext()) {
	// result.add(it.next().getId());
	// }
	// } else {
	// result = new ArrayList<Long>();
	// }
	// return result;
	// }

	private ArrayList<InputFieldSelectionSetValueJsonVO> toInputFieldSelectionSetValueJsonVOCollection(Collection<InputFieldSelectionSetValue> selectionValues) { // lazyload
		// persistentset
		// prevention
		// ArrayList<InputFieldSelectionSetValueJsonVO> result;
		// if (selectionValues != null && selectionValues.size() > 0) {
		// InputFieldSelectionSetValueDao inputFieldSelectionSetValueDao = this.getInputFieldSelectionSetValueDao();
		// result = new ArrayList<InputFieldSelectionSetValueJsonVO>(selectionValues.size());
		// Iterator<InputFieldSelectionSetValue> it = selectionValues.iterator();
		// while (it.hasNext()) {
		// result.add(inputFieldSelectionSetValueDao.toInputFieldSelectionSetValueJsonVO(it.next()));
		// }
		// } else {
		// result = new ArrayList<InputFieldSelectionSetValueJsonVO>();
		// }
		// return result;
		InputFieldSelectionSetValueDao inputFieldSelectionSetValueDao = this.getInputFieldSelectionSetValueDao();
		ArrayList<InputFieldSelectionSetValueJsonVO> result = new ArrayList<InputFieldSelectionSetValueJsonVO>(selectionValues.size());
		Iterator<InputFieldSelectionSetValue> it = selectionValues.iterator();
		while (it.hasNext()) {
			result.add(inputFieldSelectionSetValueDao.toInputFieldSelectionSetValueJsonVO(it.next()));
		}
		Collections.sort(result, SELECTION_SET_VALUE_JSON_VO_ID_COMPARATOR);
		return result;
	}

	private ArrayList<InputFieldSelectionSetValueOutVO> toInputFieldSelectionSetValueOutVOCollection(Collection<InputFieldSelectionSetValue> selectionValues) { // lazyload
		// persistentset
		// prevention
		// ArrayList<InputFieldSelectionSetValueOutVO> result;
		// if (selectionValues != null && selectionValues.size() > 0) {
		// InputFieldSelectionSetValueDao inputFieldSelectionSetValueDao = this.getInputFieldSelectionSetValueDao();
		// result = new ArrayList<InputFieldSelectionSetValueOutVO>(selectionValues.size());
		// Iterator<InputFieldSelectionSetValue> it = selectionValues.iterator();
		// while (it.hasNext()) {
		// result.add(inputFieldSelectionSetValueDao.toInputFieldSelectionSetValueOutVO(it.next()));
		// }
		// } else {
		// result = new ArrayList<InputFieldSelectionSetValueOutVO>();
		// }
		// return result;
		InputFieldSelectionSetValueDao inputFieldSelectionSetValueDao = this.getInputFieldSelectionSetValueDao();
		ArrayList<InputFieldSelectionSetValueOutVO> result = new ArrayList<InputFieldSelectionSetValueOutVO>(selectionValues.size());
		Iterator<InputFieldSelectionSetValue> it = selectionValues.iterator();
		while (it.hasNext()) {
			result.add(inputFieldSelectionSetValueDao.toInputFieldSelectionSetValueOutVO(it.next()));
		}
		Collections.sort(result, SELECTION_SET_VALUE_OUT_VO_ID_COMPARATOR);
		return result;
	}

	private HashSet<InputFieldSelectionSetValue> toInputFieldSelectionSetValueSet(Collection<Long> selectionValueIds) { // lazyload persistentset prevention
		InputFieldSelectionSetValueDao inputFieldSelectionSetValueDao = this.getInputFieldSelectionSetValueDao();
		HashSet<InputFieldSelectionSetValue> result = new HashSet<InputFieldSelectionSetValue>(selectionValueIds.size());
		Iterator<Long> it = selectionValueIds.iterator();
		while (it.hasNext()) {
			result.add(inputFieldSelectionSetValueDao.load(it.next()));
		}
		return result;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public InquiryValueInVO toInquiryValueInVO(final InquiryValue entity)
	{
		return super.toInquiryValueInVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toInquiryValueInVO(
			InquiryValue source,
			InquiryValueInVO target)
	{
		super.toInquiryValueInVO(source, target);
		Proband proband = source.getProband();
		Inquiry inquiry = source.getInquiry();
		if (proband != null) {
			target.setProbandId(proband.getId());
		}
		if (inquiry != null) {
			target.setInquiryId(inquiry.getId());
		}
		InputFieldValue value = source.getValue();
		if (value != null) {
			target.setTextValue(value.getStringValue());
			target.setBooleanValue((value.getBooleanValue() == null ? false : value.getBooleanValue().booleanValue()));
			target.setLongValue(value.getLongValue());
			target.setFloatValue(value.getFloatValue());
			target.setDateValue(CoreUtil.forceDate(value.getDateValue()));
			target.setTimestampValue(value.getTimestampValue());
			target.setTimeValue(CoreUtil.forceDate(value.getTimeValue()));
			target.setInkValues(value.getInkValue());
			target.setSelectionValueIds(ServiceUtil.toInputFieldSelectionSetValueIdCollection(value.getSelectionValues()));
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public InquiryValueJsonVO toInquiryValueJsonVO(final InquiryValue entity)
	{
		return super.toInquiryValueJsonVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toInquiryValueJsonVO(
			InquiryValue source,
			InquiryValueJsonVO target)
	{
		super.toInquiryValueJsonVO(source, target);
		Inquiry inquiry = source.getInquiry();
		if (inquiry != null) {
			target.setInquiryId(inquiry.getId());
			target.setPosition(inquiry.getPosition());
			target.setJsVariableName(inquiry.getJsVariableName());
			target.setJsValueExpression(inquiry.getJsValueExpression());
			target.setJsOutputExpression(inquiry.getJsOutputExpression());
			target.setCategory(inquiry.getCategory());
			target.setDisabled(inquiry.isDisabled());
			InputField inputField = inquiry.getField();
			if (inputField != null) {
				target.setInputFieldId(inputField.getId());
				target.setInputFieldType(inputField.getFieldType());
				target.setUserTimeZone(inputField.isUserTimeZone());
				if (inputField.isLocalized()) {
					target.setInputFieldName(L10nUtil.getInputFieldName(Locales.USER, inputField.getNameL10nKey()));
				} else {
					target.setInputFieldName(inputField.getNameL10nKey());
				}
				if (ServiceUtil.isLoadSelectionSet(inputField.getFieldType())) {
					target.setInputFieldSelectionSetValues(toInputFieldSelectionSetValueJsonVOCollection(inputField.getSelectionSetValues()));
				}
			}
		}
		InputFieldValue value = source.getValue();
		if (value != null) {
			target.setTextValue(value.getStringValue());
			target.setBooleanValue((value.getBooleanValue() == null ? false : value.getBooleanValue().booleanValue()));
			target.setLongValue(value.getLongValue());
			target.setFloatValue(value.getFloatValue());
			target.setDateValue(CoreUtil.forceDate(value.getDateValue()));
			target.setTimestampValue(value.getTimestampValue());
			target.setTimeValue(CoreUtil.forceDate(value.getTimeValue()));
			if (InputFieldType.SKETCH.equals(target.getInputFieldType())) {
				target.setInkValues(value.getInkValue());
			} else {
				target.setInkValues(null);
			}
			target.setSelectionValueIds(ServiceUtil.toInputFieldSelectionSetValueIdCollection(value.getSelectionValues()));
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public InquiryValueOutVO toInquiryValueOutVO(final InquiryValue entity)
	{
		return super.toInquiryValueOutVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toInquiryValueOutVO(
			InquiryValue source,
			InquiryValueOutVO target)
	{
		// TODO verify behavior of toInquiryValueOutVO
		super.toInquiryValueOutVO(source, target);
		// WARNING! No conversion for target.proband (can't convert source.getProband():org.phoenixctms.ctsms.domain.Proband to org.phoenixctms.ctsms.vo.ProbandOutVO
		// WARNING! No conversion for target.modifiedUser (can't convert source.getModifiedUser():org.phoenixctms.ctsms.domain.User to org.phoenixctms.ctsms.vo.UserOutVO
		// WARNING! No conversion for target.inquiry (can't convert source.getInquiry():org.phoenixctms.ctsms.domain.Inquiry to org.phoenixctms.ctsms.vo.InquiryOutVO
		Proband proband = source.getProband();
		Inquiry inquiry = source.getInquiry();
		User modifiedUser = source.getModifiedUser();
		if (proband != null) {
			target.setProband(this.getProbandDao().toProbandOutVO(proband));
		}
		if (inquiry != null) {
			target.setInquiry(this.getInquiryDao().toInquiryOutVO(inquiry));
		}
		if (modifiedUser != null) {
			target.setModifiedUser(this.getUserDao().toUserOutVO(modifiedUser));
		}
		InputFieldValue value = source.getValue();
		if (value != null) {
			target.setTextValue(value.getStringValue());
			target.setBooleanValue((value.getBooleanValue() == null ? false : value.getBooleanValue().booleanValue()));
			target.setLongValue(value.getLongValue());
			target.setFloatValue(value.getFloatValue());
			target.setDateValue(CoreUtil.forceDate(value.getDateValue()));
			target.setTimestampValue(value.getTimestampValue());
			target.setTimeValue(CoreUtil.forceDate(value.getTimeValue()));
			if (ServiceUtil.isInputFieldType(target.getInquiry(), InputFieldType.SKETCH)) {
				target.setInkValues(value.getInkValue());
			} else {
				target.setInkValues(null);
			}
			target.setSelectionValues(toInputFieldSelectionSetValueOutVOCollection(value.getSelectionValues()));
		}
	}
}