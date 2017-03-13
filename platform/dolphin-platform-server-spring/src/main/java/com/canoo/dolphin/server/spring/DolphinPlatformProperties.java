/*
 * Copyright 2015-2017 Canoo Engineering AG.
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

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("dolphinPlatform")
public class DolphinPlatformProperties {

    private Boolean useSessionInvalidationServlet;

    private Boolean useCrossSiteOriginFilter;

    private Boolean mBeanRegistration;

    private Boolean active;

    private String dolphinPlatformServletMapping;

    private String rootPackageForClasspathScan;

    private String eventbusType;

    private List<String> idFilterUrlMappings;

    private Integer sessionTimeout;

    private Long maxPollTime;

    private Integer maxClientsPerSession;

    private Boolean useGc;

    private Boolean showRemotingLogging;

    private List<String> accessControlAllowHeaders;

    private List<String> accessControlAllowMethods;

    private Boolean accessControlAllowCredentials;

    private Long accessControlMaxAge;

    public Boolean getUseSessionInvalidationServlet() {
        return useSessionInvalidationServlet;
    }

    public void setUseSessionInvalidationServlet(Boolean useSessionInvalidationServlet) {
        this.useSessionInvalidationServlet = useSessionInvalidationServlet;
    }

    public Boolean getUseCrossSiteOriginFilter() {
        return useCrossSiteOriginFilter;
    }

    public void setUseCrossSiteOriginFilter(Boolean useCrossSiteOriginFilter) {
        this.useCrossSiteOriginFilter = useCrossSiteOriginFilter;
    }

    public Boolean getmBeanRegistration() {
        return mBeanRegistration;
    }

    public void setmBeanRegistration(Boolean mBeanRegistration) {
        this.mBeanRegistration = mBeanRegistration;
    }

    public String getDolphinPlatformServletMapping() {
        return dolphinPlatformServletMapping;
    }

    public void setDolphinPlatformServletMapping(String dolphinPlatformServletMapping) {
        this.dolphinPlatformServletMapping = dolphinPlatformServletMapping;
    }

    public String getRootPackageForClasspathScan() {
        return rootPackageForClasspathScan;
    }

    public void setRootPackageForClasspathScan(String rootPackageForClasspathScan) {
        this.rootPackageForClasspathScan = rootPackageForClasspathScan;
    }

    public String getEventbusType() {
        return eventbusType;
    }

    public void setEventbusType(String eventbusType) {
        this.eventbusType = eventbusType;
    }

    public List<String> getIdFilterUrlMappings() {
        return idFilterUrlMappings;
    }

    public void setIdFilterUrlMappings(List<String> idFilterUrlMappings) {
        this.idFilterUrlMappings = idFilterUrlMappings;
    }

    public Integer getSessionTimeout() {
        return sessionTimeout;
    }

    public void setSessionTimeout(Integer sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

    public Long getMaxPollTime() {
        return maxPollTime;
    }

    public void setMaxPollTime(Long maxPollTime) {
        this.maxPollTime = maxPollTime;
    }

    public Integer getMaxClientsPerSession() {
        return maxClientsPerSession;
    }

    public void setMaxClientsPerSession(Integer maxClientsPerSession) {
        this.maxClientsPerSession = maxClientsPerSession;
    }

    public Boolean getUseGc() {
        return useGc;
    }

    public void setUseGc(Boolean useGc) {
        this.useGc = useGc;
    }

    public Boolean getShowRemotingLogging() {
        return showRemotingLogging;
    }

    public void setShowRemotingLogging(Boolean showRemotingLogging) {
        this.showRemotingLogging = showRemotingLogging;
    }

    public List<String> getAccessControlAllowHeaders() {
        return accessControlAllowHeaders;
    }

    public void setAccessControlAllowHeaders(List<String> accessControlAllowHeaders) {
        this.accessControlAllowHeaders = accessControlAllowHeaders;
    }

    public List<String> getAccessControlAllowMethods() {
        return accessControlAllowMethods;
    }

    public void setAccessControlAllowMethods(List<String> accessControlAllowMethods) {
        this.accessControlAllowMethods = accessControlAllowMethods;
    }

    public Boolean getAccessControlAllowCredentials() {
        return accessControlAllowCredentials;
    }

    public void setAccessControlAllowCredentials(Boolean accessControlAllowCredentials) {
        this.accessControlAllowCredentials = accessControlAllowCredentials;
    }

    public Long getAccessControlMaxAge() {
        return accessControlMaxAge;
    }

    public void setAccessControlMaxAge(Long accessControlMaxAge) {
        this.accessControlMaxAge = accessControlMaxAge;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
