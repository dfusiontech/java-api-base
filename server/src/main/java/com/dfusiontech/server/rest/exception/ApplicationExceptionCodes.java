package com.dfusiontech.server.rest.exception;

/**
 * Application Exception Codes
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @since    2018-12-22
 */
public class ApplicationExceptionCodes {
	// Default ERROR codes
	public static final int PASSWORD_DOESNT_MATCH = 1141;

	public static final int CREATE_IS_NOT_ALLOWED_FOR_ITEM_WITH_EXISTING_ID = 1201;
	public static final int UPDATE_IS_NOT_ALLOWED_FOR_ITEM_WITHOUT_ID = 1202;

	// User ERRORs range: 1300-1399
	public static final int USER_WITH_EMAIL_ALREADY_EXISTS = 1301;
	public static final int USER_NOT_EXISTS = 1302;
	public static final int USER_NOT_BELONGS_TO_CURRENT_GROUP = 1303;
	public static final int USER_WITH_EMAIL_NOT_EXISTS = 1304;
	public static final int RESET_PASSWORD_LINK_EMAIL_FAILED = 1323;
	public static final int RESET_PASSWORD_LINK_NOT_EXISTS = 1324;
	public static final int RESET_PASSWORD_LINK_EXPIRED = 1325;
	public static final int RESET_PASSWORD_LINK_ALREADY_APPLIED = 1326;
	public static final int RESET_PASSWORD_TOO_WEAK = 1327;

}
