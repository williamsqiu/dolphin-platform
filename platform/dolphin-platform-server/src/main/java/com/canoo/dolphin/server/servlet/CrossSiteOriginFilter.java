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
package com.canoo.dolphin.server.servlet;

import com.canoo.dolphin.impl.PlatformConstants;
import com.canoo.dolphin.server.config.DolphinPlatformConfiguration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CrossSiteOriginFilter implements Filter {

    private final DolphinPlatformConfiguration configuration;

    public CrossSiteOriginFilter(final DolphinPlatformConfiguration configuration){
        this.configuration = configuration;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //Nothing to do here
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String accessControlAllowHeaders = PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME;
        String headerValues = getvalues(configuration.getAccessControlAllowHeaders());
        if(!headerValues.isEmpty()){
            accessControlAllowHeaders = accessControlAllowHeaders + ", " + headerValues;
        }



        String clientOrigin = req.getHeader("origin");
        resp.setHeader("Access-Control-Allow-Origin", clientOrigin);
        resp.setHeader("Access-Control-Allow-Methods", getvalues(configuration.getAccessControlAllowMethods()));
        resp.setHeader("Access-Control-Allow-Headers", accessControlAllowHeaders);
        resp.setHeader("Access-Control-Expose-Headers", PlatformConstants.CLIENT_ID_HTTP_HEADER_NAME);
        resp.setHeader("Access-Control-Allow-Credentials", "" + configuration.isAccessControlAllowCredentials());
        resp.setHeader("Access-Control-Max-Age", "" + configuration.getAccessControlMaxAge());

        chain.doFilter(request, response);
    }

    private String getvalues(List<String> list){
        StringBuilder values = new StringBuilder("");
        if(null != list && list.size() > 0){
            for(String value:list){
                values.append(value).append(",");
            }
            values.deleteCharAt(values.length() - 1);
        }
        return values.toString();
    }

    @Override
    public void destroy() {
        //Nothing to do here
    }

}
