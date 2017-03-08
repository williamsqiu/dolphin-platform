/*
 * Copyright 2015-2016 Canoo Engineering AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.canoo.dolphin.server.spring;

import com.canoo.dolphin.server.bootstrap.DolphinPlatformBootstrap;
import com.canoo.dolphin.server.config.ConfigurationFileLoader;
import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.logging.Level;

/**
 * Basic Bootstrap for Spring based application. The bootstrap automatically starts the dolphin platform bootstrap.
 *
 * @author Hendrik Ebbers
 */
@Configuration
@EnableConfigurationProperties(DolphinPlatformProperties.class)

public class DolphinPlatformSpringBootstrap implements ServletContextInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(DolphinPlatformSpringBootstrap.class);

    @Autowired
    private DolphinPlatformProperties platformProperties;

    @Override
    public void onStartup(final ServletContext servletContext) throws ServletException {
        DolphinPlatformConfiguration configuration = ConfigurationFileLoader.loadConfiguration();

        updateConfigurationBySpring(configuration);
        if(configuration.isActive()) {
            final DolphinPlatformBootstrap bootstrap = new DolphinPlatformBootstrap(servletContext, configuration);
            bootstrap.start();
        }
    }

    private void updateConfigurationBySpring(DolphinPlatformConfiguration configuration) {
        if(platformProperties.getmBeanRegistration() != null) {
            configuration.setmBeanRegistration(platformProperties.getmBeanRegistration());
        }
        if(platformProperties.getDolphinPlatformServletMapping() != null) {
            configuration.setDolphinPlatformServletMapping(platformProperties.getDolphinPlatformServletMapping());
        }
        if(platformProperties.getIdFilterUrlMappings() != null && !platformProperties.getIdFilterUrlMappings().isEmpty()) {
            configuration.setIdFilterUrlMappings(platformProperties.getIdFilterUrlMappings());
        }
        if(platformProperties.getMaxClientsPerSession() != null) {
            configuration.setMaxClientsPerSession(platformProperties.getMaxClientsPerSession());
        }
        if(platformProperties.getMaxPollTime() != null) {
            configuration.setMaxPollTime(platformProperties.getMaxPollTime());
        }
        if(platformProperties.getUseCrossSiteOriginFilter() != null) {
            configuration.setUseCrossSiteOriginFilter(platformProperties.getUseCrossSiteOriginFilter());
        }
        if(platformProperties.getAccessControlAllowHeaders() != null) {
            configuration.setAccessControlAllowHeaders(platformProperties.getAccessControlAllowHeaders());
        }
        if(platformProperties.getAccessControlAllowMethods() != null) {
            configuration.setAccessControlAllowMethods(platformProperties.getAccessControlAllowMethods());
        }
        if(platformProperties.getAccessControlAllowCredentials() != null) {
            configuration.setAccessControlAllowCredentials(platformProperties.getAccessControlAllowCredentials());
        }
        if(platformProperties.getAccessControlMaxAge() != null) {
            configuration.setAccessControlMaxAge(platformProperties.getAccessControlMaxAge());
        }
        if(platformProperties.getUseGc() != null) {
            configuration.setUseGc(platformProperties.getUseGc());
        }
        if(platformProperties.getSessionTimeout() != null) {
            configuration.setSessionTimeout(platformProperties.getSessionTimeout());
        }
        if(platformProperties.getRootPackageForClasspathScan() != null) {
            configuration.setRootPackageForClasspathScan(platformProperties.getRootPackageForClasspathScan());
        }
        if(platformProperties.getUseSessionInvalidationServlet() != null) {
            configuration.setUseSessionInvalidationServlet(platformProperties.getUseSessionInvalidationServlet());
        }
        if(platformProperties.getShowRemotingLogging() != null) {
            configuration.setOpenDolphinLogLevel(platformProperties.getShowRemotingLogging()? Level.INFO : Level.OFF);
        }
        if(platformProperties.getActive() != null) {
            configuration.setActive(platformProperties.getActive());
        }
    }


}
