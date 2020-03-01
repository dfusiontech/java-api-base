package com.dfusiontech.server.service;

import com.dfusiontech.server.model.jpa.domains.RoleType;
import com.dfusiontech.server.model.jpa.entity.UserPasswordResetLinks;
import com.dfusiontech.server.model.jpa.entity.Users;
import com.dfusiontech.server.repository.jpa.UserPasswordResetLinksRepository;
import com.dfusiontech.server.rest.ApplicationProperties;
import com.dfusiontech.server.rest.exception.ApplicationExceptionCodes;
import com.dfusiontech.server.rest.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.UUID;

/**
 * User Password Reset Links management Service
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  0.1.1
 * @since    2018-12-06
 */
@Service
public class UserPasswordResetLinksService {

	@Autowired
	private UserPasswordResetLinksRepository userPasswordResetLinksRepository;

	@Autowired
	private ApplicationProperties applicationProperties;

	@Autowired
	UserService userService;

	/**
	 * Create Reset Link for User
	 *
	 * @param user
	 * @return
	 */
	public UserPasswordResetLinks create(Users user) {

		UUID uuid = UUID.randomUUID();
		String resetPasswordGuid = uuid.toString();

		UserPasswordResetLinks entity = new UserPasswordResetLinks();
		entity.setUser(user);
		entity.setCode(resetPasswordGuid);
		entity.setActive(true);
		entity.setCreatedAt(new Date());
		entity.setExpiredAt(new Date(System.currentTimeMillis() + (long) 259200 * 1000L));

		UserPasswordResetLinks result = userPasswordResetLinksRepository.save(entity);

		return result;
	}

	/**
	 * Get URL for reset Link
	 *
	 * @param linkDetails
	 * @return
	 */
	public String getLinkUrl(UserPasswordResetLinks linkDetails) {
		String linkUrl;

		if (userService.isAuthorized() && userService.hasRole(RoleType.ADMIN)) {
			linkUrl = applicationProperties.getAdminUiUrl() + "/public/change-password/" + linkDetails.getCode();
		} else {
			linkUrl = applicationProperties.getUiUrl() + "/public/change-password/" + linkDetails.getCode();
		}

		return linkUrl;
	}

	/**
	 * Get URL for reset Link
	 *
	 * @param linkCode
	 * @return
	 */
	public UserPasswordResetLinks verifyLinkByCode(String linkCode) {
		UserPasswordResetLinks result = userPasswordResetLinksRepository.findFirstByCode(linkCode)
			.orElseThrow(() -> new BadRequestException(MessageFormat.format("Reset password link not found [{0}]", linkCode), ApplicationExceptionCodes.RESET_PASSWORD_LINK_NOT_EXISTS));

		// Check is link valid
		if (result.getExpiredAt().before(new Date())) {
			throw new BadRequestException(MessageFormat.format("Reset password link expired [{0}]", linkCode), ApplicationExceptionCodes.RESET_PASSWORD_LINK_EXPIRED);
		}

		// Check is it already applied
		if (!result.getActive()) {
			throw new BadRequestException(MessageFormat.format("Reset password link already applied [{0}]", linkCode), ApplicationExceptionCodes.RESET_PASSWORD_LINK_ALREADY_APPLIED);
		}

		return result;
	}

	/**
	 * Get URL for reset Link
	 *
	 * @param linkCode
	 * @return
	 */
	public Users applyPassword(String linkCode, String password) {
		UserPasswordResetLinks linkDetails = verifyLinkByCode(linkCode);

		// Check password
		if (StringUtils.isEmpty(password.trim())) {
			throw new BadRequestException(MessageFormat.format("Reset password is too weak [{0}]", password), ApplicationExceptionCodes.RESET_PASSWORD_TOO_WEAK);
		}

		Users result = userService.changePassword(linkDetails.getUser(), password);

		linkDetails.setActive(false);
		userPasswordResetLinksRepository.save(linkDetails);

		return result;
	}


}
