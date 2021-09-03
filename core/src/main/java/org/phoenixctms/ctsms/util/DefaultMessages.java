package org.phoenixctms.ctsms.util;

public interface DefaultMessages {

	public static final String PROPERTY_NOT_FOUND_OR_INVALID_TYPE = "property {0} not found in class {1} or of invalid type";
	public static final String CRITERIA_PROPERTY_TYPE_NOT_SUPPORTED = "type {0} of property {1} not supported";
	public static final String NAMED_PARAMETER_UNKNOWN_USER = "active user is unknown";
	public static final String NAMED_PARAMETER_UNKNOWN_USER_DEPARTMENT = "active user's department is unknown";
	public static final String NAMED_PARAMETER_UNKNOWN_IDENTITY = "active person is unknown";
	public static final String NAMED_PARAMETER_UNKNOWN_IDENTITY_DEPARTMENT = "active person's department is unknown";
	public static final String NAMED_PARAMETER_EXPLICIT_VARIABLE_PERIOD = "period in days not supported";
	public static final String UNSUPPORTED_DB_MODULE = "unsupported DB module {0}";
	public static final String SQL_SET_OPERATION_KEYWORD_UNDEFINED = "SQL set operation keyword for {0} undefined";
	public static final String UNBALANCED_SET_PARENTHESES = "unbalanced parentheses for set operations";
	public static final String UNSUPPORTED_CRITERION_TIE = "unsupported criterion conjunction {0}";
	public static final String UNSUPPORTED_CRITERION_RESTRICTION = "unsupported criterion comparison operator {0}";
	public static final String INVALID_PROPERTY_ASSOCIATION_PATH = "invalid property association path {0}";
	public static final String NO_CRITERIA_FOR_ASSOCIATION_PATH = "no criteria for property association path {0}";
	public static final String CRITERION_NOT_IMPLEMENTED_SYNTAX_ERROR = "syntax error {0} not implemented";
	public static final String CRITERION_CLASS_NOT_SUPPORTED = "unsupported criterion class {0}";
	public final static String UNSUPPORTED_VARIABLE_PERIOD = "variable period {0} is not supported";
	public final static String EASTER_DATE_YEAR_UNSUPPORTED = "easter date calculation for year {0} is not supported";
	public final static String UNSUPPORTED_WEEKDAY = "weekday {0} is not supported";
	public final static String HOLIDAY_MONTH_UNDEFINED = "Holiday month undefined";
	public final static String HOLIDAY_DAY_UNDEFINED = "holiday day undefined";
	public final static String HOLIDAY_WEEKDAY_UNDEFINED = "holiday weekday undefined";
	public final static String HOLIDAY_N_UNDEFINED = "holiday nth weekday undefined";
	public final static String UNSUPPORTED_HOLIDAY_BASE_DATE = "holiday base date {0} is not supported";
	public final static String INVALID_DATE_INTERVAL = "invalid date interval";
	public final static String INVALID_TIME_ZONE = "time zone {0} is not supported";
	public final static String INVALID_LOCALE = "locale {0} is not supported";
	public final static String EXTERNAL_FILE_DATADIR_ACCESS_ERROR = "external file data directory {0} has restricted access";
	public final static String EXTERNAL_FILE_DATADIR_NOT_ABSOLUTE_ERROR = "external file data directory {0} is not an absolute path";
	public final static String EXTERNAL_FILE_DATADIR_CREATION_ERROR = "external file data directory {0} could not be created";
	public final static String EXTERNAL_FILE_DATADIR_NOTADIR_ERROR = "external file data directory {0} is not a directory";
	public final static String PDF_TEMPLATE_FILE_ACCESS_ERROR = "PDF template file {0} has restricted access";
	public final static String PDF_TEMPLATE_FILE_NOTAFILE_ERROR = "PDF template file {0} is not a file";
	public final static String PDF_TEMPLATE_FILE_DOES_NOT_EXIST_ERROR = "PDF template file {0} does not exist";
	public final static String PDF_TEMPLATE_FILE_INVALID_PDF_ERROR = "PDF template file {0} is an invalid PDF file";
	public final static String JS_FILE_SCRIPT_ERROR = "error in javascript file {0}: {1}";
	public final static String JS_FILE_ACCESS_ERROR = "javascript file {0} has restricted access";
	public final static String JS_FILE_NOTAFILE_ERROR = "javascript file {0} is not a file";
	public final static String JS_FILE_DOES_NOT_EXIST_ERROR = "javascript file {0} does not exist";
	public final static String IMAGE_FILE_ACCESS_ERROR = "image file {0} has restricted access";
	public final static String IMAGE_FILE_NOTAFILE_ERROR = "image file {0} is not a file";
	public final static String IMAGE_FILE_DOES_NOT_EXIST_ERROR = "image file {0} does not exist";
	public final static String IMAGE_FILE_INVALID_IMAGE_ERROR = "image file {0} is an invalid image";
	public final static String TTF_INVALID_TTF_ERROR = "True Type Font {0} file {1} is an invalid TTF file";
	public final static String TTF_FILE_ACCESS_ERROR = "True Type Font {0} file {1} has restricted access";
	public final static String TTF_NOTAFILE_ERROR = "True Type Font {0} file {1} is not a file";
	public final static String TTF_FILE_DOES_NOT_EXIST_ERROR = "True Type Font {0} file {1} does not exist";
	public final static String TRUE = "true";
	public final static String FALSE = "false";
	public final static String ENCRYPTED_PROBAND_NAME = "<encrypted proband name>";
	public final static String BLINDED_PROBAND_NAME = "<blinded>";
	public final static String NEW_BLINDED_PROBAND_NAME = "<{0}>";
	public final static String ENCRYPTED_PROBAND_ADDRESS = "<encrypted proband address>";
	public final static String ENCRYPTED_BANK_ACCOUNT_NAME = "<encrypted bank account name>";
	public final static String PASSWORD_SMALL_LETTERS = "small letters";
	public final static String PASSWORD_CAPITAL_LETTERS = "capital letters";
	public final static String PASSWORD_DIGITS = "digits";
	public final static String PASSWORD_UMLAUTS = "umlauts and \u00DF";
	public final static String PASSWORD_WHITESPACES = "whitespace";
	public final static String PASSWORD_ALT_SYMBOLS = "other symbols {0}";
	public final static String PASSWORD_SYMBOLS = "symbols {0}";
	public final static String PASSWORD_TOO_SHORT = "password has less than {0} characters";
	public final static String PASSWORD_TOO_LONG = "password has more than {0} characters";
	public final static String PASSWORD_TOO_FEW_OCCURRENCES = "password has less than {0} {1}";
	public final static String PASSWORD_TOO_MANY_OCCURRENCES = "password has more than {0} {1}";
	public final static String PASSWORD_INVALID_CHAR_FOUND = "password contains other characters than {0}";
	public final static String PASSWORD_TOO_SIMILAR_TO_PREVIOUS_PASSWORDS = "password requires an edit-distance of at least {0} to previous passwords";
	public final static String PASSWORD_MIN_LENGTH_REQUIREMENT = "a minimum of {0} characters";
	public final static String PASSWORD_MAX_LENGTH_REQUIREMENT = "a maximum of {0} characters";
	public final static String PASSWORD_CHARACTER_CLASS_MIN_REQUIREMENT = "a minimum of {0} {1}";
	public final static String PASSWORD_CHARACTER_CLASS_MAX_REQUIREMENT = "a maximum of {0} {1}";
	public final static String PASSWORD_COMPLETE_PASSWORD_HISTORY_REQUIREMENT = "new password requires an edit-distance of at least {0} to all historical passwords";
	public final static String PASSWORD_DISTANCE_PASSWORD_HISTORY_REQUIREMENT = "new password requires an edit-distance of at least {0} to the last {1} historical passwords";
	public final static String PASSWORD_ADMIN_IGNORE_POLICY_REQUIREMENT = "admins may ignore this password policy when setting passwords";
	public final static String PASSWORD_INVALID_LENGTH_DEF = "invalid password minimum {0} or maximum {1} length requirement definition";
	public final static String PASSWORD_CHAR_MIN_OCCURRENCES_SANITY_ERROR = "password minimum character occurrence requirements force password lengths greater than or equal to {0}, but maximum length is set to {1}";
	public final static String PASSWORD_CHAR_MAX_OCCURRENCES_SANITY_ERROR = "password maximum character occurrence requirements force password lengths less than or equal to {0}, but minimum length is set to {1}";
	public final static String PASSWORD_INVALID_CHARSET_OCCURRENCE_DEF = "invalid password character occurrence definition {0}: min {1}, max {2}";
	public final static String PBE_PASSWORD_ZERO_LENGTH_ERROR = "password has zero-length";
	public final static String REMINDER_ENTITY_NOT_SUPPORTED = "reminder entity {0} not supported";
	public final static String UNSUPPORTED_FILE_MODULE = "unsupported file module {0}";
	public final static String UNSUPPORTED_HYPERLINK_MODULE = "unsupported hyperlink module {0}";
	public final static String UNSUPPORTED_JOURNAL_MODULE = "unsupported journal module {0}";
	public final static String UNSUPPORTED_INPUT_FIELD_TYPE = "unsupported input field type {0}";
	public final static String UNSUPPORTED_PICKER_DB_MODULE = "unsupported picker DB module {0}";
	public static final String UNSUPPORTED_SERVICE_METHOD_PARAMETER_RESTRICTION = "unsupported service method parameter restriction {0}";
	public static final String UNSUPPORTED_SERVICE_METHOD_PARAMETER_TRANSFORMATION = "unsupported service method parameter transformation {0}";
	public static final String UNSUPPORTED_SERVICE_METHOD_PARAMETER_OVERRIDE = "unsupported service method parameter override {0}";
	public static final String UNSUPPORTED_AUTHENTICATION_TYPE = "unsupported authentication method {0}";
	public static final String UNKNOWN_LDAP_SERVICE = "unknown LDAP service {0}";
	public static final String AUTHORISATIONEXCEPTION_MESSAGE = "authorisation error";
	public static final String AUTHENTICATIONEXCEPTION_MESSAGE = "authentication error";
	public static final String SERVICEEXCEPTION_MESSAGE = "service error";
	public static final String DEPARTMENT_NAME = "<department>";
	public static final String INVENTORY_CATEGORY_NAME = "<inventory category>";
	public static final String INVENTORY_STATUS_TYPE_NAME = "<inventory status>";
	public static final String INVENTORY_TAG_NAME = "<inventory tag>";
	public static final String MAINTENANCE_TYPE_NAME = "<maintenance type>";
	public static final String MAINTENANCE_TITLE_PRESET = "<maintenance title preset>";
	public static final String STAFF_CATEGORY_NAME = "<staff category>";
	public static final String STAFF_TAG_NAME = "<staff tag>";
	public static final String ADDRESS_TYPE_NAME = "<address type>";
	public static final String CONTACT_DETAIL_TYPE_NAME = "<contact detail type>";
	public static final String COURSE_PARTICIPATION_STATUS_TYPE_NAME = "<course participation status type>";
	public static final String CV_SECTION_NAME = "<cv section name>";
	public static final String CV_SECTION_TITLE_PRESET = "<cv section title preset>"; // < and > cause ajax xhr xml parse error
	public static final String CV_SECTION_DESCRIPTION = "";
	public static final String TRAINING_RECORD_SECTION_NAME = "<training record section name>";
	public static final String TRAINING_RECORD_SECTION_DESCRIPTION = "";
	public static final String STAFF_STATUS_TYPE_NAME = "<staff status>";
	public static final String COURSE_CATEGORY_NAME = "<course category>";
	public static final String LECTURER_COMPETENCE_NAME = "<lecturer competence>";
	public static final String PROBAND_CATEGORY_NAME = "<proband category>";
	public static final String PRIVACY_CONSENT_STATUS_TYPE_NAME = "<privacy consent status type name>";
	public static final String AUTHENTICATION_TYPE_NAME = "<authentication type>";
	public static final String VARIABLE_PERIOD_NAME = "<period>";
	public static final String INPUT_FIELD_TYPE_NAME = "<field type>";
	public static final String EVENT_IMPORTANCE_NAME = "<event importance>";
	public static final String SEX_NAME = "<sex>";
	public static final String RANDOMIZATION_MODE_NAME = "<randomization mode>";
	public static final String JOB_STATUS_NAME = "<job status>";
	public static final String ECRF_VALIDATION_STATUS_NAME = "<eCRF validation status>";
	public static final String PAYMENT_METHOD_NAME = "<payment method>";
	public static final String VISIT_SCHEDULE_DATE_MODE_NAME = "<visit schedule date mode>";
	public static final String DB_MODULE_NAME = "<db module>";
	public static final String JOURNAL_MODULE_NAME = "<journal module>";
	public static final String FILE_MODULE_NAME = "<file module>";
	public static final String JOB_MODULE_NAME = "<job module>";
	public static final String HYPERLINK_MODULE_NAME = "<hyperlink module>";
	public final static String CRITERION_TIE_NAME = "<conjuction>";
	public final static String CRITERION_RESTRICTION_NAME = "<operator>";
	public final static String CRITERION_PROPERTY_NAME = "<field>";
	public static final String HYPERLINK_CATEGORY_NAME = "<hyperlink category>";
	public static final String HYPERLINK_TITLE_PRESET = "<maintenance title preset>";
	public static final String JOURNAL_CATEGORY_NAME = "<journal category>";
	public static final String JOURNAL_TITLE_PRESET = "<journal title preset>";
	public static final String SYSTEM_MESSAGE_TITLE = "<system message title>";
	public static final String SYSTEM_MESSAGE_COMMENT = "<system message comment>";
	public static final String SYSTEM_MESSAGE_COMMENT_FIELD_LABEL = "<system message comment field label>";
	public static final String AUDIT_TRAIL_CHANGE_COMMENT_FIELD_LABEL = "<audit trail change comment field label>";
	public static final String HOLIDAY_NAME = "<holiday>";
	public static final String NOTIFICATION_TYPE_NAME = "<notification type name>";
	public static final String NOTIFICATION_SUBJECT = "<notification subject>";
	public static final String NOTIFICATION_MESSAGE_TEMPLATE = "";
	public static final String VISIT_TYPE_NAME = "<visit type name>";
	public static final String TRIAL_TAG_NAME = "<trial tag name>";
	public static final String TRIAL_STATUS_TYPE_NAME = "<trial status type name>";
	public static final String ECRF_STATUS_TYPE_NAME = "<eCRF status type name>";
	public static final String ECRF_FIELD_STATUS_TYPE_NAME = "<eCRF field status type name>";
	public static final String TRIAL_TYPE_NAME = "<trial type name>";
	public static final String SPONSORING_TYPE_NAME = "<sponsoring type name>";
	public static final String SURVEY_STATUS_TYPE_NAME = "<survey status type name>";
	public static final String PROBAND_STATUS_TYPE_NAME = "<proband status type name>";
	public static final String PROBAND_TAG_NAME = "<proband tag name>";
	public static final String TIMELINE_EVENT_TYPE_NAME = "<timeline event type name>";
	public static final String TIMELINE_EVENT_TITLE_PRESET = "<timeline event title preset>";
	public static final String TEAM_MEMBER_ROLE_NAME = "<team member role name>";
	public static final String PROBAND_LIST_STATUS_TYPE_NAME = "<proband status list type name>";
	public static final String MASS_MAIL_STATUS_TYPE_NAME = "<mass mail status type>";
	public static final String MASS_MAIL_TYPE_NAME = "<mass mail type>";
	public static final String INPUT_FIELD_SELECTEION_SET_VALUE_NAME = "<input field selection set value name>";
	public static final String INPUT_FIELD_VALIDATION_ERROR_MSG = "<input field validation error message>";
	public static final String INPUT_FIELD_TITLE = "<input field title>";
	public static final String INPUT_FIELD_TEXT_PRESET = "<input field text preset>";
	public static final String INPUT_FIELD_NAME = "<input field name>";
	public static final String INPUT_FIELD_COMMENT = "<input field comment>";
	public static final String PERMISSION_PROFILE_NAME = "<permission profile>";
	public static final String PERMISSION_PROFILE_GROUP_NAME = "<permission profile group>";
	public static final String MD5_CHECK_FAILED = "MD5 checksum wrong";
	public static final String NO_DECRYPTABLE_PDF_FILES = "no (decryptable) PDF files";
	public static final String INTERVAL_STOP_BEFORE_START = "interval end before start";
	public static final String DIFFERRING_ORIGINAL_AND_UPDATED_VO_TYPES = "differring original ({0}) and updated ({1}) types";
	public static final String UNSUPPORTED_ENUMERATION_OR_VALUE = "unsupported enumeration {0} or value {1}";
	public static final String UNSUPPORTED_ENTITY = "unsupported entity {0}";
	public static final String UNSUPPORTED_MAIL_RECIPIENT_ENTITY = "entity {0} cannot be used as recipient";
	public static final String UNSUPPORTED_MAIL_RECIPIENT_PROPERTY = "unknown property {0}";
	public static final String SIGNEE_IDENTITY_LABEL = "{0} ({1})";
	public static final String TRIAL_SIGNATURE_VALID_DESCRIPTION = "Successful signature validation!\nTrial {0} database records content from {2}, signed by {1} is evidentially untampered.\nSignature: {4}\nTime of verification: {3}";
	public static final String TRIAL_SIGNATURE_INVALID_DESCRIPTION = "Signature validation FAILED!\nTrial {0} database records content from {2}, signed by {1} was modified in meantime.\nTime of verification: {3}";
	public static final String TRIAL_SIGNATURE_AVAILABLE = "Signature available.\nClick \"Verify signature\" to check if trial {0} database records content from {1} were modified or tampered with in meantime.";
	public static final String ECRF_SIGNATURE_VALID_DESCRIPTION = "Successful signature validation!\neCRF {0} database records content for subject {1} from {3}, signed by {2} is evidentially untampered.\nSignature: {5}\nTime of verification: {4}";
	public static final String ECRF_SIGNATURE_INVALID_DESCRIPTION = "Signature validation FAILED!\neCRF {0} database records content for subject {1} from {3}, signed by {2} was modified in meantime.\nTime of verification: {4}";
	public static final String ECRF_SIGNATURE_AVAILABLE = "Signature available.\nClick \"Verify signature\" to check if eCRF {0} database records content for subject {1} from {2} were tampered with ever since.";
	public static final String ECRF_VALIDATION_FAILED_RESPONSE = "error executing field validation: {0}";
	public static final String ECRF_VALIDATION_OK_ERRORS_RESPONSE = "validation successful - {0} error(s)";
	public static final String ECRF_VALIDATION_OK_NO_ERROR_RESPONSE = "validation successful - no errors";
	public static final String NOTIFICATION_INPUT_FIELD_VALUE_CHECKBOX_CHECKED = "checked";
	public static final String NOTIFICATION_INPUT_FIELD_VALUE_CHECKBOX_UNCHECKED = "unchecked";
	public static final String MASS_MAIL_INPUT_FIELD_VALUE_CHECKBOX_CHECKED = "checked";
	public static final String MASS_MAIL_INPUT_FIELD_VALUE_CHECKBOX_UNCHECKED = "unchecked";
	public static final String MASS_MAIL_CACNELLED_NO_RECIPIENTS = "invalid proband/no email addresses";
	public static final String UNSUPPORTED_RANDOMIZATION_MODE = "unsupported randomization method {0}";
	public static final String UNSUPPORTED_RANDOMIZATION_TYPE = "unsupported randomization type {0}";
	public static final String UNSUPPORTED_JOB_MODULE = "unsupported job module {0}";
	public static final String JOB_TYPE_NAME = "<job type>";
	public static final String JOB_TYPE_DESCRIPTION = "<job description>";
	public static final String START_JOB_ERROR = "could not start job: {0}";
	public static final String LOOP_PATH_SEPARATOR = " -> ";
	public static final String LOOP_PATH_COURSE_LABEL = "{0}";
	public static final String LOOP_PATH_INVENTORY_LABEL = "{0}";
	public static final String LOOP_PATH_PROBAND_LABEL = "proband ID {0}";
	public static final String LOOP_PATH_STAFF_LABEL = "{0}";
	public static final String LOOP_PATH_TIMELINE_EVENT_LABEL = "{0}";
	public static final String LOOP_PATH_USER_LABEL = "{0}";
	public static final String UNSUPPORTED_VISIT_SCHEDULE_DATE_MODE = "unsupported visit schedule date mode {0}";
	public static final String UNSUPPORTED_RANGE_PERIOD = "range period {0} is not supported";
}
