// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.compare.ComparatorFactory;
import org.phoenixctms.ctsms.query.CategoryCriterion;
import org.phoenixctms.ctsms.query.CriteriaUtil;
import org.phoenixctms.ctsms.query.SubCriteriaMap;
import org.phoenixctms.ctsms.security.CipherText;
import org.phoenixctms.ctsms.security.CryptoUtil;
import org.phoenixctms.ctsms.util.CommonUtil;
import org.phoenixctms.ctsms.util.CoreUtil;
import org.phoenixctms.ctsms.util.DefaultSettings;
import org.phoenixctms.ctsms.util.SettingCodes;
import org.phoenixctms.ctsms.util.Settings;
import org.phoenixctms.ctsms.util.Settings.Bundle;
import org.phoenixctms.ctsms.vo.AspSubstanceVO;
import org.phoenixctms.ctsms.vo.AspVO;
import org.phoenixctms.ctsms.vo.DiagnosisOutVO;
import org.phoenixctms.ctsms.vo.MedicationInVO;
import org.phoenixctms.ctsms.vo.MedicationOutVO;
import org.phoenixctms.ctsms.vo.PSFVO;
import org.phoenixctms.ctsms.vo.ProbandOutVO;
import org.phoenixctms.ctsms.vo.ProcedureOutVO;
import org.phoenixctms.ctsms.vo.UserOutVO;

public class MedicationDaoImpl
		extends MedicationDaoBase {

	private final static Comparator<AspSubstanceVO> SUBSTANCE_ID_COMPARATOR = ComparatorFactory.createSafeLong(AspSubstanceVO::getId);
	private static final String MEDICATION_ASP_NAME = "{0}";
	private static final String MEDICATION_SUBSTANCES_NAME = "{0}";

	private static final String getMedicationName(MedicationOutVO medication) {
		if (medication != null) {
			AspVO aspVO = medication.getAsp();
			// Collection<AspSubstanceVO> substancesVO;
			if (aspVO != null) {
				return MessageFormat.format(MEDICATION_ASP_NAME, aspVO.getName());
			} else { // if ((substancesVO = medication.getSubstances()) != null) { // && substancesVO.size() > 0) {
				return MessageFormat.format(MEDICATION_SUBSTANCES_NAME, CommonUtil.aspSubstanceVOCollectionToString(medication.getSubstances()));
			}
		}
		return null;
	}

	public static ArrayList<Long> toAspSubstanceIdCollection(Collection<AspSubstance> substances) { // lazyload persistentset prevention
		// ArrayList<Long> result;
		// if (substances != null && substances.size() > 0) {
		// result = new ArrayList<Long>(substances.size());
		// Iterator<AspSubstance> it = substances.iterator();
		// while (it.hasNext()) {
		// result.add(it.next().getId());
		// }
		// } else {
		// result = new ArrayList<Long>();
		// }
		// return result;
		ArrayList<Long> result = new ArrayList<Long>(substances.size());
		Iterator<AspSubstance> it = substances.iterator();
		while (it.hasNext()) {
			result.add(it.next().getId());
		}
		Collections.sort(result); // InVO ID sorting
		return result;
	}

	private org.hibernate.Criteria createMedicationCriteria() {
		org.hibernate.Criteria medicationCriteria = this.getSession().createCriteria(Medication.class);
		return medicationCriteria;
	}

	@Override
	protected Collection<Medication> handleFindByProband(Long probandId, PSFVO psf) throws Exception {
		org.hibernate.Criteria medicationCriteria = createMedicationCriteria();
		SubCriteriaMap criteriaMap = new SubCriteriaMap(Medication.class, medicationCriteria);
		if (probandId != null) {
			medicationCriteria.add(Restrictions.eq("proband.id", probandId.longValue()));
		}
		CriteriaUtil.applyPSFVO(criteriaMap, psf);
		return medicationCriteria.list();
	}

	@Override
	protected Collection<Medication> handleFindCollidingProbandDiagnosisProcedureAspSubstancesInterval(Long probandId, Long diagnosisId, Long procedureId, Long aspId,
			Set<Long> aspSubstanceIds, Timestamp from, Timestamp to) throws Exception {
		org.hibernate.Criteria medicationCriteria = createMedicationCriteria();
		if (from != null) {
			medicationCriteria.add(Restrictions.isNotNull("start"));
			CriteriaUtil.applyStopOptionalIntervalCriterion(medicationCriteria, from, to, null, true);
		} else { // saved records without start stop
			medicationCriteria.add(Restrictions.isNull("start"));
		}
		// if (probandId != null) {
		medicationCriteria.add(Restrictions.eq("proband.id", probandId.longValue()));
		// }
		if (diagnosisId != null) {
			medicationCriteria.add(Restrictions.eq("diagnosis.id", diagnosisId.longValue()));
		} else {
			medicationCriteria.add(Restrictions.isNull("diagnosis.id"));
		}
		if (procedureId != null) {
			medicationCriteria.add(Restrictions.eq("procedure.id", procedureId.longValue()));
		} else {
			medicationCriteria.add(Restrictions.isNull("procedure.id"));
		}
		if (aspId != null) {
			medicationCriteria.add(Restrictions.eq("asp.id", aspId.longValue()));
		}
		if (aspSubstanceIds != null && aspSubstanceIds.size() > 0) {
			org.hibernate.Criteria substancesCriteria = medicationCriteria.createCriteria("substances", CriteriaSpecification.INNER_JOIN);
			substancesCriteria.add(Restrictions.in("id", aspSubstanceIds));
		}
		return medicationCriteria.list();
	}

	@Override
	protected Collection<String> handleFindDoseUnits(String doseUnitPrefix, Integer limit) throws Exception {
		org.hibernate.Criteria medicationCriteria = createMedicationCriteria();
		// medicationCriteria.setCacheable(true);
		CategoryCriterion.apply(medicationCriteria, new CategoryCriterion(doseUnitPrefix, "doseUnit", MatchMode.START));
		medicationCriteria.add(Restrictions.not(Restrictions.or(Restrictions.eq("doseUnit", ""), Restrictions.isNull("doseUnit"))));
		// aspAtcCodeCriteria.add(Restrictions.eq("revision",
		// Settings.getString(SettingCodes.ASP_REVISION, Bundle.SETTINGS, DefaultSettings.ASP_REVISION)));
		medicationCriteria.addOrder(Order.asc("doseUnit"));
		medicationCriteria.setProjection(Projections.distinct(Projections.property("doseUnit")));
		CriteriaUtil.applyLimit(limit, Settings.getIntNullable(SettingCodes.MEDICATION_DOSE_UNIT_AUTOCOMPLETE_DEFAULT_RESULT_LIMIT, Bundle.SETTINGS,
				DefaultSettings.MEDICATION_DOSE_UNIT_AUTOCOMPLETE_DEFAULT_RESULT_LIMIT), medicationCriteria);
		return medicationCriteria.list();
	}

	@Override
	protected long handleGetCount(Long probandId, Long diagnosisId, Long procedureId) throws Exception {
		org.hibernate.Criteria medicationCriteria = createMedicationCriteria();
		if (probandId != null) {
			medicationCriteria.add(Restrictions.eq("proband.id", probandId.longValue()));
		}
		if (diagnosisId != null) {
			medicationCriteria.add(Restrictions.eq("diagnosis.id", diagnosisId.longValue()));
		}
		if (procedureId != null) {
			medicationCriteria.add(Restrictions.eq("procedure.id", procedureId.longValue()));
		}
		return (Long) medicationCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private Medication loadMedicationFromMedicationInVO(MedicationInVO medicationInVO) {
		// TODO implement loadMedicationFromMedicationInVO
		// throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadMedicationFromMedicationInVO(MedicationInVO) not yet implemented.");
		Long id = medicationInVO.getId();
		Medication medication = null;
		if (id != null) {
			medication = this.load(id);
		}
		if (medication == null) {
			medication = Medication.Factory.newInstance();
		}
		return medication;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private Medication loadMedicationFromMedicationOutVO(MedicationOutVO medicationOutVO) {
		// TODO implement loadMedicationFromMedicationOutVO
		//throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadMedicationFromMedicationOutVO(MedicationOutVO) not yet implemented.");
		Medication medication = this.load(medicationOutVO.getId());
		if (medication == null) {
			medication = Medication.Factory.newInstance();
		}
		return medication;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Medication medicationInVOToEntity(MedicationInVO medicationInVO) {
		Medication entity = this.loadMedicationFromMedicationInVO(medicationInVO);
		this.medicationInVOToEntity(medicationInVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void medicationInVOToEntity(
			MedicationInVO source,
			Medication target,
			boolean copyIfNull) {
		super.medicationInVOToEntity(source, target, copyIfNull);
		Long probandId = source.getProbandId();
		Long aspId = source.getAspId();
		Long diagnosisId = source.getDiagnosisId();
		Long procedureId = source.getProcedureId();
		if (diagnosisId != null) {
			Diagnosis diagnosis = this.getDiagnosisDao().load(diagnosisId);
			target.setDiagnosis(diagnosis);
			diagnosis.addMedications(target);
		} else if (copyIfNull) {
			Diagnosis diagnosis = target.getDiagnosis();
			target.setDiagnosis(null);
			if (diagnosis != null) {
				diagnosis.removeMedications(target);
			}
		}
		if (procedureId != null) {
			Procedure procedure = this.getProcedureDao().load(procedureId);
			target.setProcedure(procedure);
			procedure.addMedications(target);
		} else if (copyIfNull) {
			Procedure procedure = target.getProcedure();
			target.setProcedure(null);
			if (procedure != null) {
				procedure.removeMedications(target);
			}
		}
		if (probandId != null) {
			Proband proband = this.getProbandDao().load(probandId);
			target.setProband(proband);
			proband.addMedications(target);
		} else if (copyIfNull) {
			Proband proband = target.getProband();
			target.setProband(null);
			if (proband != null) {
				proband.removeMedications(target);
			}
		}
		if (aspId != null) {
			Asp asp = this.getAspDao().load(aspId);
			target.setAsp(asp);
			asp.addMedications(target);
		} else if (copyIfNull) {
			Asp asp = target.getAsp();
			target.setAsp(null);
			if (asp != null) {
				asp.removeMedications(target);
			}
		}
		Collection substanceIds;
		if ((substanceIds = source.getSubstanceIds()).size() > 0 || copyIfNull) {
			target.setSubstances(toAspSubstanceSet(substanceIds));
		}
		try {
			if (copyIfNull || source.getComment() != null) {
				CipherText cipherText = CryptoUtil.encryptValue(source.getComment());
				target.setCommentIv(cipherText.getIv());
				target.setEncryptedComment(cipherText.getCipherText());
				target.setCommentHash(CryptoUtil.hashForSearch(source.getComment()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	// private HashSet<AspSubstance> toAspSubstanceSet(Collection<AspSubstanceVO> aspSubstances) { // lazyload persistentset prevention
	// AspSubstanceDao aspSubstanceDao = this.getAspSubstanceDao();
	// HashSet<AspSubstance> result = new HashSet<AspSubstance>(aspSubstances.size());
	// Iterator<AspSubstanceVO> it = aspSubstances.iterator();
	// while (it.hasNext()) {
	// result.add(aspSubstanceDao.load(it.next().getId()));
	// }
	// return result;
	// }

	/**
	 * @inheritDoc
	 */
	@Override
	public Medication medicationOutVOToEntity(MedicationOutVO medicationOutVO) {
		// TODO verify behavior of medicationOutVOToEntity
		Medication entity = this.loadMedicationFromMedicationOutVO(medicationOutVO);
		this.medicationOutVOToEntity(medicationOutVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void medicationOutVOToEntity(
			MedicationOutVO source,
			Medication target,
			boolean copyIfNull) {
		super.medicationOutVOToEntity(source, target, copyIfNull);
		ProbandOutVO probandVO = source.getProband();
		AspVO aspVO = source.getAsp();
		// Collection substances = source.getSubstances();
		DiagnosisOutVO diagnosisVO = source.getDiagnosis();
		ProcedureOutVO procedureVO = source.getProcedure();
		UserOutVO modifiedUserVO = source.getModifiedUser();
		// VariablePeriodVO dosePeriodVO = source.getDosePeriod();
		if (procedureVO != null) {
			Procedure procedure = this.getProcedureDao().procedureOutVOToEntity(procedureVO);
			target.setProcedure(procedure);
			procedure.addMedications(target);
		} else if (copyIfNull) {
			Procedure procedure = target.getProcedure();
			target.setProcedure(null);
			if (procedure != null) {
				procedure.removeMedications(target);
			}
		}
		if (diagnosisVO != null) {
			Diagnosis diagnosis = this.getDiagnosisDao().diagnosisOutVOToEntity(diagnosisVO);
			target.setDiagnosis(diagnosis);
			diagnosis.addMedications(target);
		} else if (copyIfNull) {
			Diagnosis diagnosis = target.getDiagnosis();
			target.setDiagnosis(null);
			if (diagnosis != null) {
				diagnosis.removeMedications(target);
			}
		}
		if (probandVO != null) {
			Proband proband = this.getProbandDao().probandOutVOToEntity(probandVO);
			target.setProband(proband);
			proband.addMedications(target);
		} else if (copyIfNull) {
			Proband proband = target.getProband();
			target.setProband(null);
			if (proband != null) {
				proband.removeMedications(target);
			}
		}
		if (aspVO != null) {
			Asp asp = this.getAspDao().aspVOToEntity(aspVO);
			target.setAsp(asp);
			asp.addMedications(target);
		} else if (copyIfNull) {
			Asp asp = target.getAsp();
			target.setAsp(null);
			if (asp != null) {
				asp.removeMedications(target);
			}
		}
		// if (copyIfNull || substances.size() > 0) {
		// this.getAspSubstanceDao().aspSubstanceVOToEntityCollection(substances); // copyifnull!!
		// target.setSubstances((Collection<AspSubstance>) substances);
		// }
		Collection substances;
		if ((substances = source.getSubstances()).size() > 0 || copyIfNull) {
			this.getAspSubstanceDao().aspSubstanceVOToEntityCollection(substances);
			target.setSubstances(substances); // hashset-exception!!!
		}
		// if (dosePeriodVO != null) {
		// target.setDosePeriod(dosePeriodVO.getPeriod());
		// } else if (copyIfNull) {
		// target.setDosePeriod(null);
		// }
		if (modifiedUserVO != null) {
			target.setModifiedUser(this.getUserDao().userOutVOToEntity(modifiedUserVO));
		} else if (copyIfNull) {
			target.setModifiedUser(null);
		}
		try {
			if (copyIfNull || source.getComment() != null) {
				CipherText cipherText = CryptoUtil.encryptValue(source.getComment());
				target.setCommentIv(cipherText.getIv());
				target.setEncryptedComment(cipherText.getCipherText());
				target.setCommentHash(CryptoUtil.hashForSearch(source.getComment()));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private HashSet<AspSubstance> toAspSubstanceSet(Collection<Long> aspSubstanceIds) { // lazyload persistentset prevention
		AspSubstanceDao aspSubstanceDao = this.getAspSubstanceDao();
		HashSet<AspSubstance> result = new HashSet<AspSubstance>(aspSubstanceIds.size());
		Iterator<Long> it = aspSubstanceIds.iterator();
		while (it.hasNext()) {
			result.add(aspSubstanceDao.load(it.next()));
		}
		return result;
	}

	// private ArrayList<AspSubstanceVO> toAspSubstancesVOCollection(Collection<AspSubstance> substances) { // lazyload
	// // persistentset
	// // prevention
	// // ArrayList<AspSubstanceVO> result;
	// // if (substances != null && substances.size() > 0) {
	// // AspSubstanceDao aspSubstanceDao = this.getAspSubstanceDao();
	// // result = new ArrayList<AspSubstanceVO>(substances.size());
	// // Iterator<AspSubstance> it = substances.iterator();
	// // while (it.hasNext()) {
	// // result.add(aspSubstanceDao.toAspSubstanceVO(it.next()));
	// // }
	// // } else {
	// // result = new ArrayList<AspSubstanceVO>();
	// // }
	// // return result;
	//
	// AspSubstanceDao aspSubstanceDao = this.getAspSubstanceDao();
	// ArrayList<AspSubstanceVO> result = new ArrayList<AspSubstanceVO>(substances.size());
	// Iterator<AspSubstance> it = substances.iterator();
	// while (it.hasNext()) {
	// result.add(aspSubstanceDao.toAspSubstanceVO(it.next()));
	// }
	// x
	// return result;
	// }
	private ArrayList<AspSubstanceVO> toAspSubstanceVOCollection(Collection<AspSubstance> substances) { // lazyload persistentset prevention
		// related to http://forum.andromda.org/viewtopic.php?t=4288
		AspSubstanceDao aspSubstanceDao = this.getAspSubstanceDao();
		ArrayList<AspSubstanceVO> result = new ArrayList<AspSubstanceVO>(substances.size());
		Iterator<AspSubstance> it = substances.iterator();
		while (it.hasNext()) {
			result.add(aspSubstanceDao.toAspSubstanceVO(it.next()));
		}
		result.sort(SUBSTANCE_ID_COMPARATOR);
		return result;
	}

	@Override
	public MedicationInVO toMedicationInVO(final Medication entity) {
		return super.toMedicationInVO(entity);
	}

	@Override
	public void toMedicationInVO(
			Medication source,
			MedicationInVO target) {
		super.toMedicationInVO(source, target);
		Asp asp = source.getAsp();
		Proband proband = source.getProband();
		Diagnosis diagnosis = source.getDiagnosis();
		Procedure procedure = source.getProcedure();
		if (asp != null) {
			target.setAspId(asp.getId());
		}
		target.setSubstanceIds(toAspSubstanceIdCollection(source.getSubstances()));
		if (procedure != null) {
			target.setProcedureId(procedure.getId());
		}
		if (diagnosis != null) {
			target.setDiagnosisId(diagnosis.getId());
		}
		if (proband != null) {
			target.setProbandId(proband.getId());
		}
		try {
			target.setComment((String) CryptoUtil.decryptValue(source.getCommentIv(), source.getEncryptedComment()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public MedicationOutVO toMedicationOutVO(final Medication entity) {
		return super.toMedicationOutVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toMedicationOutVO(
			Medication source,
			MedicationOutVO target) {
		super.toMedicationOutVO(source, target);
		// WARNING! No conversion for target.proband (can't convert source.getProband():org.phoenixctms.ctsms.domain.Proband to org.phoenixctms.ctsms.vo.ProbandOutVO
		// WARNING! No conversion for target.modifiedUser (can't convert source.getModifiedUser():org.phoenixctms.ctsms.domain.User to org.phoenixctms.ctsms.vo.UserOutVO
		// WARNING! No conversion for target.diagnosis (can't convert source.getDiagnosis():org.phoenixctms.ctsms.domain.Diagnosis to org.phoenixctms.ctsms.vo.DiagnosisOutVO
		// WARNING! No conversion for target.asp (can't convert source.getAsp():org.phoenixctms.ctsms.domain.Asp to org.phoenixctms.ctsms.vo.AspVO
		// WARNING! No conversion for target.substances (can't convert source.getSubstances():org.phoenixctms.ctsms.domain.AspSubstance to org.phoenixctms.ctsms.vo.AspSubstanceVO
		Asp asp = source.getAsp();
		Diagnosis diagnosis = source.getDiagnosis();
		Procedure procedure = source.getProcedure();
		Proband proband = source.getProband();
		User modifiedUser = source.getModifiedUser();
		if (asp != null) {
			target.setAsp(this.getAspDao().toAspVO(asp));
		}
		target.setSubstances(toAspSubstanceVOCollection(source.getSubstances()));
		if (procedure != null) {
			target.setProcedure(this.getProcedureDao().toProcedureOutVO(procedure));
		}
		if (diagnosis != null) {
			target.setDiagnosis(this.getDiagnosisDao().toDiagnosisOutVO(diagnosis));
		}
		if (proband != null) {
			target.setProband(this.getProbandDao().toProbandOutVO(proband));
		}
		if (modifiedUser != null) {
			target.setModifiedUser(this.getUserDao().toUserOutVO(modifiedUser));
		}
		// target.setDosePeriod(L10nUtil.createVariablePeriodVO(Locales.USER, source.getDosePeriod()));
		try {
			if (!CoreUtil.isPassDecryption()) {
				throw new Exception();
			}
			target.setComment((String) CryptoUtil.decryptValue(source.getCommentIv(), source.getEncryptedComment()));
			target.setDecrypted(true);
		} catch (Exception e) {
			target.setComment(null);
			target.setDecrypted(false);
		}
		target.setName(getMedicationName(target));
	}
}