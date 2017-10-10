/**
 * This package contains the SPI for Dolphin Platform server modules. All modules that are based on this API and are at the classpath at the server start will be automatically loaded and started. A module is defined by a {@link com.canoo.platform.server.spi.ServerModule} implementation and must be annotated by {@link com.canoo.platform.server.spi.ModuleDefinition}. All modules will be loaded by the default Java SPI implementation. See {@link java.util.ServiceLoader} for more information.
 *
 *
 *
 * @author Hendrik Ebbers
 *
 * @see com.canoo.platform.server.spi.ServerModule
 * @see com.canoo.platform.server.spi.ModuleDefinition
 * @see java.util.ServiceLoader
 */
package com.canoo.platform.server.spi;