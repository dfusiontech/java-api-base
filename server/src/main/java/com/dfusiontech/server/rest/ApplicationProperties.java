package com.dfusiontech.server.rest;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 * Simple implementation of the Application properties helper. Used in API calls, etc.
 *
 * @author   Eugene A. Kalosha <ekalosha@dfusiontech.com>
 * @version  1.11.1
 * @since    2018-10-17
 */
@Getter
public class ApplicationProperties {

	@Value("${build.version}")
	private String buildVersion;

	@Value("${build.artifactId}")
	private String buildArtifact;

}
