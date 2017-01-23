package com.canoo.dolphin.server.spring;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties("dolphinPlatform")
public class DolphinPlatformProperties {

    private Boolean useSessionInvalidationServlet;

    private Boolean useCrossSiteOriginFilter;

    private Boolean mBeanRegistration;

    private String dolphinPlatformServletMapping;

    private String rootPackageForClasspathScan;

    private String eventbusType;

    private List<String> idFilterUrlMappings;

    private Integer sessionTimeout;

    private Long maxPollTime;

    private Integer maxClientsPerSession;

    private Boolean useGc;

    private Boolean showRemotingLogging;

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
}
