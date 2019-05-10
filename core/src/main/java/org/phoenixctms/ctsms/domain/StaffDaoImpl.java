// Generated by: hibernate/SpringHibernateDaoImpl.vsl in andromda-spring-cartridge.
// license-header java merge-point
/**
 * This is only generated once! It will never be overwritten.
 * You can (and have to!) safely modify it by hand.
 */
package org.phoenixctms.ctsms.domain;

import java.awt.Dimension;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.phoenixctms.ctsms.enumeration.DBModule;
import org.phoenixctms.ctsms.enumeration.FileModule;
import org.phoenixctms.ctsms.enumeration.NotificationType;
import org.phoenixctms.ctsms.query.CriteriaUtil;
import org.phoenixctms.ctsms.query.QueryUtil;
import org.phoenixctms.ctsms.query.SubCriteriaMap;
import org.phoenixctms.ctsms.util.CoreUtil;
import org.phoenixctms.ctsms.vo.CriteriaInstantVO;
import org.phoenixctms.ctsms.vo.DepartmentVO;
import org.phoenixctms.ctsms.vo.PSFVO;
import org.phoenixctms.ctsms.vo.StaffCategoryVO;
import org.phoenixctms.ctsms.vo.StaffImageInVO;
import org.phoenixctms.ctsms.vo.StaffImageOutVO;
import org.phoenixctms.ctsms.vo.StaffInVO;
import org.phoenixctms.ctsms.vo.StaffOutVO;
import org.phoenixctms.ctsms.vo.UserOutVO;
import org.phoenixctms.ctsms.vocycle.DeferredVO;
import org.phoenixctms.ctsms.vocycle.StaffReflexionGraph;

/**
 * @see Staff
 */
public class StaffDaoImpl
		extends StaffDaoBase {

	private org.hibernate.Criteria createStaffCriteria() {
		org.hibernate.Criteria staffCriteria = this.getSession().createCriteria(Staff.class);
		return staffCriteria;
	}

	@Override
	protected Collection<Staff> handleFindByCriteria(
			CriteriaInstantVO criteria, PSFVO psf) throws Exception {
		Query query = QueryUtil.createSearchQuery(
				criteria,
				DBModule.STAFF_DB,
				psf,
				this.getSessionFactory(),
				this.getCriterionTieDao(),
				this.getCriterionPropertyDao(),
				this.getCriterionRestrictionDao());
		return query.list();
	}

	@Override
	protected Collection<Staff> handleFindByDepartmentNotificationType(
			Long departmentId, NotificationType notificationType)
			throws Exception {
		org.hibernate.Criteria staffCriteria = createStaffCriteria();
		if (departmentId != null) {
			staffCriteria.add(Restrictions.eq("department.id", departmentId.longValue()));
		}
		if (notificationType != null) {
			staffCriteria.createCriteria("category", CriteriaSpecification.INNER_JOIN).createCriteria("sendDepartmentNotificationTypes", CriteriaSpecification.INNER_JOIN)
					.add(Restrictions.eq("type", notificationType));
		}
		return staffCriteria.list();
	}

	@Override
	protected Collection<Staff> handleFindByDepartmentStatusInterval(Long departmentId, Timestamp from, Timestamp to, Boolean staffActive, Boolean allocatable,
			Boolean hideAvailability, PSFVO psf)
			throws Exception {
		org.hibernate.Criteria staffCriteria = createStaffCriteria();
		SubCriteriaMap criteriaMap = new SubCriteriaMap(Staff.class, staffCriteria);
		if (departmentId != null) {
			staffCriteria.add(Restrictions.eq("department.id", departmentId.longValue()));
		}
		if (allocatable != null) {
			staffCriteria.add(Restrictions.eq("allocatable", allocatable.booleanValue()));
		}
		org.hibernate.Criteria statusEntryCriteria = criteriaMap.createCriteria("statusEntries");
		CriteriaUtil.applyStopOpenIntervalCriterion(statusEntryCriteria, from, to, null);
		if (staffActive != null) {
			criteriaMap.createCriteria("statusEntries.type").add(Restrictions.eq("staffActive", staffActive.booleanValue()));
		}
		if (hideAvailability != null) {
			criteriaMap.createCriteria("statusEntries.type").add(Restrictions.eq("hideAvailability", hideAvailability.booleanValue()));
		}
		return CriteriaUtil.listDistinctRootPSFVO(criteriaMap, psf, this);
	}

	@Override
	protected Collection<Staff> handleFindByIdDepartment(Long staffId,
			Long departmentId, PSFVO psf) throws Exception {
		org.hibernate.Criteria staffCriteria = createStaffCriteria();
		SubCriteriaMap criteriaMap = new SubCriteriaMap(Staff.class, staffCriteria);
		CriteriaUtil.applyIdDepartmentCriterion(staffCriteria, staffId, departmentId);
		CriteriaUtil.applyPSFVO(criteriaMap, psf);
		return staffCriteria.list();
	}

	@Override
	protected long handleGetChildrenCount(Long staffId) throws Exception {
		org.hibernate.Criteria staffCriteria = createStaffCriteria();
		staffCriteria.add(Restrictions.eq("parent.id", staffId.longValue()));
		return (Long) staffCriteria.setProjection(Projections.rowCount()).uniqueResult();
	}

	@Override
	protected long handleGetCountByCriteria(CriteriaInstantVO criteria, PSFVO psf) throws Exception {
		return QueryUtil.getSearchQueryResultCount(
				criteria,
				DBModule.STAFF_DB,
				psf,
				this.getSessionFactory(),
				this.getCriterionTieDao(),
				this.getCriterionPropertyDao(),
				this.getCriterionRestrictionDao());
	}

	@Override
	protected long handleGetCountByDepartmentStatusInterval(Long departmentId, Timestamp from, Timestamp to, Boolean staffActive, Boolean allocatable, Boolean hideAvailability)
			throws Exception {
		org.hibernate.Criteria staffCriteria = createStaffCriteria();
		if (departmentId != null) {
			staffCriteria.add(Restrictions.eq("department.id", departmentId.longValue()));
		}
		if (allocatable != null) {
			staffCriteria.add(Restrictions.eq("allocatable", allocatable.booleanValue()));
		}
		org.hibernate.Criteria statusEntryCriteria = staffCriteria.createCriteria("statusEntries", CriteriaSpecification.INNER_JOIN);
		CriteriaUtil.applyStopOpenIntervalCriterion(statusEntryCriteria, from, to, null);
		if (staffActive != null || hideAvailability != null) {
			Criteria statusTypeCriteria = statusEntryCriteria.createCriteria("type", CriteriaSpecification.INNER_JOIN);
			if (staffActive != null) {
				statusTypeCriteria.add(Restrictions.eq("staffActive", staffActive.booleanValue()));
			}
			if (hideAvailability != null) {
				statusTypeCriteria.add(Restrictions.eq("hideAvailability", hideAvailability.booleanValue()));
			}
		}
		return (Long) staffCriteria.setProjection(Projections.countDistinct("id")).uniqueResult();
	}

	private void loadDeferredUserOutVOs(HashMap<Class, HashMap<Long, Object>> voMap) {
		HashMap<Long, Object> modifiedUserVOMap = voMap.get(UserOutVO.class);
		UserDao userDao = this.getUserDao();
		if (modifiedUserVOMap != null) {
			Iterator<Entry<Long, Object>> modifiedUserVOMapIt = (new HashSet<Entry<Long, Object>>(modifiedUserVOMap.entrySet())).iterator();
			while (modifiedUserVOMapIt.hasNext()) {
				Entry<Long, Object> modifiedUserVO = modifiedUserVOMapIt.next();
				DeferredVO deferredVO = (DeferredVO) modifiedUserVO.getValue();
				if (deferredVO.isDeferred()) {
					deferredVO.setDeferred(false);
					userDao.toUserOutVO(userDao.load(modifiedUserVO.getKey()), (UserOutVO) deferredVO.getVo(), voMap, modifiedUserVOMap.size(), 0, 0);
				}
			}
		}
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private Staff loadStaffFromStaffImageInVO(StaffImageInVO staffImageInVO) {
		Long id = staffImageInVO.getId();
		Staff staff = null;
		if (id != null) {
			staff = this.load(id);
		}
		if (staff == null) {
			staff = Staff.Factory.newInstance();
		}
		return staff;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private Staff loadStaffFromStaffImageOutVO(StaffImageOutVO staffImageOutVO) {
		// TODO implement loadStaffFromStaffImageOutVO
		throw new UnsupportedOperationException("org.phoenixctms.ctsms.domain.loadStaffFromStaffImageOutVO(StaffImageOutVO) not yet implemented.");
		/* A typical implementation looks like this: Staff staff = this.load(staffImageOutVO.getId()); if (staff == null) { staff = Staff.Factory.newInstance(); } return staff; */
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private Staff loadStaffFromStaffInVO(StaffInVO staffInVO) {
		Staff staff = null;
		Long id = staffInVO.getId();
		if (id != null) {
			staff = this.load(id);
		}
		if (staff == null) {
			staff = Staff.Factory.newInstance();
		}
		return staff;
	}

	/**
	 * Retrieves the entity object that is associated with the specified value object
	 * from the object store. If no such entity object exists in the object store,
	 * a new, blank entity is created
	 */
	private Staff loadStaffFromStaffOutVO(StaffOutVO staffOutVO) {
		throw new UnsupportedOperationException("out value object to recursive entity not supported");
		// Staff staff = this.load(staffOutVO.getId());
		// if (staff == null)
		// {
		// staff = Staff.Factory.newInstance();
		// }
		// return staff;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Staff staffImageInVOToEntity(StaffImageInVO staffImageInVO) {
		Staff entity = this.loadStaffFromStaffImageInVO(staffImageInVO);
		this.staffImageInVOToEntity(staffImageInVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void staffImageInVOToEntity(
			StaffImageInVO source,
			Staff target,
			boolean copyIfNull) {
		super.staffImageInVOToEntity(source, target, copyIfNull);
		PersonContactParticulars personParticulars = null;
		if (target.isPerson()) {
			personParticulars = target.getPersonParticulars();
			if (personParticulars == null) {
				personParticulars = PersonContactParticulars.Factory.newInstance();
				target.setPersonParticulars(personParticulars);
			}
		} else if (copyIfNull) {
			target.setPersonParticulars(null);
		}
		if (personParticulars != null) {
			if (copyIfNull || source.getFileName() != null) {
				personParticulars.setFileName(source.getFileName());
			}
			if (copyIfNull || source.getShowCv() != false) {
				personParticulars.setShowCv(source.getShowCv());
			}
			if (copyIfNull || source.getFileTimestamp() != null) {
				personParticulars.setFileTimestamp((source.getFileTimestamp() == null ? null : new Timestamp(source.getFileTimestamp().getTime())));
			}
			if (source.getDatas() != null && source.getDatas().length > 0) {
				personParticulars.setData(source.getDatas());
				personParticulars.setFileSize((long) source.getDatas().length);
				personParticulars.setContentType(this.getMimeTypeDao().findByMimeTypeModule(source.getMimeType(), FileModule.STAFF_IMAGE).iterator().next());
				Dimension imageDimension = CoreUtil.getImageDimension(source.getDatas());
				if (imageDimension != null) {
					personParticulars.setWidth((long) imageDimension.width);
					personParticulars.setHeight((long) imageDimension.height);
				}
			} else if (copyIfNull) {
				personParticulars.setData(null);
				personParticulars.setFileSize(0l);
				personParticulars.setContentType(null);
				personParticulars.setWidth(null);
				personParticulars.setHeight(null);
			}
		}
		OrganisationContactParticulars organisationParticulars = null;
		if (!target.isPerson()) {
			organisationParticulars = target.getOrganisationParticulars();
			if (organisationParticulars == null) {
				organisationParticulars = OrganisationContactParticulars.Factory.newInstance();
				target.setOrganisationParticulars(organisationParticulars);
			}
		} else if (copyIfNull) {
			target.setOrganisationParticulars(null);
		}
		if (organisationParticulars != null) {
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public Staff staffImageOutVOToEntity(StaffImageOutVO staffImageOutVO) {
		// TODO verify behavior of staffImageOutVOToEntity
		Staff entity = this.loadStaffFromStaffImageOutVO(staffImageOutVO);
		this.staffImageOutVOToEntity(staffImageOutVO, entity, true);
		return entity;
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void staffImageOutVOToEntity(
			StaffImageOutVO source,
			Staff target,
			boolean copyIfNull) {
		// TODO verify behavior of staffImageOutVOToEntity
		super.staffImageOutVOToEntity(source, target, copyIfNull);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Staff staffInVOToEntity(StaffInVO staffInVO) {
		Staff entity = this.loadStaffFromStaffInVO(staffInVO);
		this.staffInVOToEntity(staffInVO, entity, true);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void staffInVOToEntity(
			StaffInVO source,
			Staff target,
			boolean copyIfNull) {
		super.staffInVOToEntity(source, target, copyIfNull);
		Long departmentId = source.getDepartmentId();
		Long categoryId = source.getCategoryId();
		Long parentId = source.getParentId();
		if (departmentId != null) {
			target.setDepartment(this.getDepartmentDao().load(departmentId));
		} else if (copyIfNull) {
			target.setDepartment(null);
		}
		if (categoryId != null) {
			target.setCategory(this.getStaffCategoryDao().load(categoryId));
		} else if (copyIfNull) {
			target.setCategory(null);
		}
		if (parentId != null) {
			if (target.getParent() != null) {
				target.getParent().removeChildren(target);
			}
			Staff parent = this.load(parentId);
			target.setParent(parent);
			parent.addChildren(target);
		} else if (copyIfNull) {
			Staff parent = target.getParent();
			target.setParent(null);
			if (parent != null) {
				parent.removeChildren(target);
			}
		}
		PersonContactParticulars personParticulars = null;
		if (source.isPerson()) {
			personParticulars = target.getPersonParticulars();
			if (personParticulars == null) {
				personParticulars = PersonContactParticulars.Factory.newInstance();
				target.setPersonParticulars(personParticulars);
			}
		} else if (copyIfNull) {
			target.setPersonParticulars(null);
		}
		if (personParticulars != null) {
			if (copyIfNull || source.getPrefixedTitle1() != null) {
				personParticulars.setPrefixedTitle1(source.getPrefixedTitle1());
			}
			if (copyIfNull || source.getPrefixedTitle2() != null) {
				personParticulars.setPrefixedTitle2(source.getPrefixedTitle2());
			}
			if (copyIfNull || source.getPrefixedTitle3() != null) {
				personParticulars.setPrefixedTitle3(source.getPrefixedTitle3());
			}
			if (copyIfNull || source.getFirstName() != null) {
				personParticulars.setFirstName(source.getFirstName());
			}
			if (copyIfNull || source.getLastName() != null) {
				personParticulars.setLastName(source.getLastName());
			}
			if (copyIfNull || source.getPostpositionedTitle1() != null) {
				personParticulars.setPostpositionedTitle1(source.getPostpositionedTitle1());
			}
			if (copyIfNull || source.getPostpositionedTitle2() != null) {
				personParticulars.setPostpositionedTitle2(source.getPostpositionedTitle2());
			}
			if (copyIfNull || source.getPostpositionedTitle3() != null) {
				personParticulars.setPostpositionedTitle3(source.getPostpositionedTitle3());
			}
			if (copyIfNull || source.getCvAcademicTitle() != null) {
				personParticulars.setCvAcademicTitle(source.getCvAcademicTitle());
			}
			if (copyIfNull || source.getDateOfBirth() != null) {
				personParticulars.setDateOfBirth(source.getDateOfBirth());
			}
			if (copyIfNull || source.getGender() != null) {
				personParticulars.setGender(source.getGender());
			}
			if (copyIfNull || source.getCitizenship() != null) {
				personParticulars.setCitizenship(source.getCitizenship());
			}
		}
		OrganisationContactParticulars organisationParticulars = null;
		if (!source.isPerson()) {
			organisationParticulars = target.getOrganisationParticulars();
			if (organisationParticulars == null) {
				organisationParticulars = OrganisationContactParticulars.Factory.newInstance();
				target.setOrganisationParticulars(organisationParticulars);
			}
		} else if (copyIfNull) {
			target.setOrganisationParticulars(null);
		}
		if (organisationParticulars != null) {
			if (copyIfNull || source.getOrganisationName() != null) {
				organisationParticulars.setOrganisationName(source.getOrganisationName());
			}
			if (copyIfNull || source.getCvOrganisationName() != null) {
				organisationParticulars.setCvOrganisationName(source.getCvOrganisationName());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Staff staffOutVOToEntity(StaffOutVO staffOutVO) {
		Staff entity = this.loadStaffFromStaffOutVO(staffOutVO);
		this.staffOutVOToEntity(staffOutVO, entity, true);
		return entity;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void staffOutVOToEntity(
			StaffOutVO source,
			Staff target,
			boolean copyIfNull) {
		super.staffOutVOToEntity(source, target, copyIfNull);
		StaffCategoryVO categoryVO = source.getCategory();
		DepartmentVO departmentVO = source.getDepartment();
		StaffOutVO parentVO = source.getParent();
		UserOutVO modifiedUserVO = source.getModifiedUser();
		if (categoryVO != null) {
			target.setCategory(this.getStaffCategoryDao().staffCategoryVOToEntity(categoryVO));
		} else if (copyIfNull) {
			target.setCategory(null);
		}
		if (departmentVO != null) {
			target.setDepartment(this.getDepartmentDao().departmentVOToEntity(departmentVO));
		} else if (copyIfNull) {
			target.setDepartment(null);
		}
		if (parentVO != null) {
			if (target.getParent() != null) {
				target.getParent().removeChildren(target);
			}
			Staff parent = this.staffOutVOToEntity(parentVO);
			target.setParent(parent);
			parent.addChildren(target);
		} else if (copyIfNull) {
			Staff parent = target.getParent();
			target.setParent(null);
			if (parent != null) {
				parent.removeChildren(target);
			}
		}
		if (modifiedUserVO != null) {
			target.setModifiedUser(this.getUserDao().userOutVOToEntity(modifiedUserVO));
		} else if (copyIfNull) {
			target.setModifiedUser(null);
		}
		PersonContactParticulars personParticulars = null;
		if (source.isPerson()) {
			personParticulars = target.getPersonParticulars();
			if (personParticulars == null) {
				personParticulars = PersonContactParticulars.Factory.newInstance();
				target.setPersonParticulars(personParticulars);
			}
		} else if (copyIfNull) {
			target.setPersonParticulars(null);
		}
		if (personParticulars != null) {
			if (copyIfNull || source.getPrefixedTitle1() != null) {
				personParticulars.setPrefixedTitle1(source.getPrefixedTitle1());
			}
			if (copyIfNull || source.getPrefixedTitle2() != null) {
				personParticulars.setPrefixedTitle2(source.getPrefixedTitle2());
			}
			if (copyIfNull || source.getPrefixedTitle3() != null) {
				personParticulars.setPrefixedTitle3(source.getPrefixedTitle3());
			}
			if (copyIfNull || source.getFirstName() != null) {
				personParticulars.setFirstName(source.getFirstName());
			}
			if (copyIfNull || source.getLastName() != null) {
				personParticulars.setLastName(source.getLastName());
			}
			if (copyIfNull || source.getPostpositionedTitle1() != null) {
				personParticulars.setPostpositionedTitle1(source.getPostpositionedTitle1());
			}
			if (copyIfNull || source.getPostpositionedTitle2() != null) {
				personParticulars.setPostpositionedTitle2(source.getPostpositionedTitle2());
			}
			if (copyIfNull || source.getPostpositionedTitle3() != null) {
				personParticulars.setPostpositionedTitle3(source.getPostpositionedTitle3());
			}
			if (copyIfNull || source.getCvAcademicTitle() != null) {
				personParticulars.setCvAcademicTitle(source.getCvAcademicTitle());
			}
			if (copyIfNull || source.getDateOfBirth() != null) {
				personParticulars.setDateOfBirth(source.getDateOfBirth());
			}
			if (copyIfNull || source.getGender() != null) { // ......wrong....
				personParticulars.setGender(source.getGender().getSex());
			}
			if (copyIfNull || source.getCitizenship() != null) {
				personParticulars.setCitizenship(source.getCitizenship());
			}
			if (copyIfNull || source.getImageShowCv() != false) {
				personParticulars.setShowCv(source.getImageShowCv());
			}
		}
		OrganisationContactParticulars organisationParticulars = null;
		if (!source.isPerson()) {
			organisationParticulars = target.getOrganisationParticulars();
			if (organisationParticulars == null) {
				organisationParticulars = OrganisationContactParticulars.Factory.newInstance();
				target.setOrganisationParticulars(organisationParticulars);
			}
		} else if (copyIfNull) {
			target.setOrganisationParticulars(null);
		}
		if (organisationParticulars != null) {
			if (copyIfNull || source.getOrganisationName() != null) {
				organisationParticulars.setOrganisationName(source.getOrganisationName());
			}
			if (copyIfNull || source.getCvOrganisationName() != null) {
				organisationParticulars.setCvOrganisationName(source.getCvOrganisationName());
			}
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public StaffImageInVO toStaffImageInVO(final Staff entity) {
		return super.toStaffImageInVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toStaffImageInVO(
			Staff source,
			StaffImageInVO target) {
		super.toStaffImageInVO(source, target);
		if (source.isPerson()) {
			PersonContactParticulars personParticulars = source.getPersonParticulars();
			if (personParticulars != null) {
				MimeType contentType = personParticulars.getContentType();
				target.setFileName(personParticulars.getFileName());
				target.setFileTimestamp(personParticulars.getFileTimestamp());
				target.setDatas(personParticulars.getData());
				if (contentType != null) {
					target.setMimeType(contentType.getMimeType());
				}
				target.setShowCv((personParticulars.getShowCv() == null ? false : personParticulars.getShowCv().booleanValue()));
			}
		}
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public StaffImageOutVO toStaffImageOutVO(final Staff entity) {
		return super.toStaffImageOutVO(entity);
	}

	/**
	 * @inheritDoc
	 */
	@Override
	public void toStaffImageOutVO(
			Staff source,
			StaffImageOutVO target) {
		super.toStaffImageOutVO(source, target);
		User modifiedUser = source.getModifiedUser();
		if (modifiedUser != null) {
			target.setModifiedUser(this.getUserDao().toUserOutVO(modifiedUser));
		}
		PersonContactParticulars personParticulars = source.getPersonParticulars();
		if (source.isPerson()) {
			if (personParticulars != null) {
				MimeType contentType = personParticulars.getContentType();
				target.setFileName(personParticulars.getFileName());
				target.setFileSize(personParticulars.getFileSize());
				target.setFileTimestamp(personParticulars.getFileTimestamp());
				target.setHeight(personParticulars.getHeight());
				target.setWidth(personParticulars.getWidth());
				target.setShowCv(personParticulars.getShowCv() == null ? false : personParticulars.getShowCv().booleanValue());
				target.setHasImage(personParticulars.getFileSize() != null && personParticulars.getFileSize() > 0l);
				if (contentType != null) {
					target.setContentType(this.getMimeTypeDao().toMimeTypeVO(contentType));
				}
				target.setDatas(personParticulars.getData());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StaffInVO toStaffInVO(final Staff entity) {
		return super.toStaffInVO(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toStaffInVO(
			Staff source,
			StaffInVO target) {
		super.toStaffInVO(source, target);
		Department department = source.getDepartment();
		StaffCategory category = source.getCategory();
		Staff parent = source.getParent();
		if (department != null) {
			target.setDepartmentId(department.getId());
		}
		if (category != null) {
			target.setCategoryId(category.getId());
		}
		if (parent != null) {
			target.setParentId(parent.getId());
		}
		if (source.isPerson()) {
			PersonContactParticulars personParticulars = source.getPersonParticulars();
			if (personParticulars != null) {
				target.setPrefixedTitle1(personParticulars.getPrefixedTitle1());
				target.setPrefixedTitle2(personParticulars.getPrefixedTitle2());
				target.setPrefixedTitle3(personParticulars.getPrefixedTitle3());
				target.setFirstName(personParticulars.getFirstName());
				target.setLastName(personParticulars.getLastName());
				target.setPostpositionedTitle1(personParticulars.getPostpositionedTitle1());
				target.setPostpositionedTitle2(personParticulars.getPostpositionedTitle2());
				target.setPostpositionedTitle3(personParticulars.getPostpositionedTitle3());
				target.setCvAcademicTitle(personParticulars.getCvAcademicTitle());
				target.setDateOfBirth(personParticulars.getDateOfBirth());
				target.setGender(personParticulars.getGender());
				target.setCitizenship(personParticulars.getCitizenship());
			}
		} else {
			OrganisationContactParticulars organisationParticulars = source.getOrganisationParticulars();
			if (organisationParticulars != null) {
				target.setOrganisationName(organisationParticulars.getOrganisationName());
				target.setCvOrganisationName(organisationParticulars.getCvOrganisationName());
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public StaffOutVO toStaffOutVO(final Staff entity) {
		return super.toStaffOutVO(entity);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void toStaffOutVO(
			Staff source,
			StaffOutVO target) {
		HashMap<Class, HashMap<Long, Object>> voMap = new HashMap<Class, HashMap<Long, Object>>();
		(new StaffReflexionGraph(this, this.getStaffCategoryDao(), this.getDepartmentDao(), this.getUserDao())).toVOHelper(source, target, voMap);
		loadDeferredUserOutVOs(voMap);
	}

	@Override
	public void toStaffOutVO(
			Staff source,
			StaffOutVO target, HashMap<Class, HashMap<Long, Object>> voMap) {
		(new StaffReflexionGraph(this, this.getStaffCategoryDao(), this.getDepartmentDao(), this.getUserDao())).toVOHelper(source, target, voMap);
		loadDeferredUserOutVOs(voMap);
	}

	@Override
	public void toStaffOutVO(
			Staff source,
			StaffOutVO target, HashMap<Class, HashMap<Long, Object>> voMap, Integer... maxInstances) {
		(new StaffReflexionGraph(this, this.getStaffCategoryDao(), this.getDepartmentDao(), this.getUserDao(), maxInstances)).toVOHelper(source, target, voMap);
		loadDeferredUserOutVOs(voMap);
	}

	@Override
	public void toStaffOutVO(
			Staff source,
			StaffOutVO target, Integer... maxInstances) {
		HashMap<Class, HashMap<Long, Object>> voMap = new HashMap<Class, HashMap<Long, Object>>();
		(new StaffReflexionGraph(this, this.getStaffCategoryDao(), this.getDepartmentDao(), this.getUserDao(), maxInstances)).toVOHelper(source, target, voMap);
		loadDeferredUserOutVOs(voMap);
	}
}