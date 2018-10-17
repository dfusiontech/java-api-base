package com.dfusiontech.server.rest;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;

/**
 * Simple implementation of the Application properties helper. Used in API calls, etc.
 *
 * @author Eugene A. Kalosha <ekalosha@dfusiontech.com>
 */
@Getter
public class ApplicationProperties {

	@Value("${build.version}")
	private String buildVersion;

	@Value("${build.artifactId}")
	private String buildArtifact;

}
