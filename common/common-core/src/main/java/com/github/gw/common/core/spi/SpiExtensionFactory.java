package com.github.gw.common.core.spi;

/**
 * SpiExtensionFactory.
 */
@Join
public class SpiExtensionFactory implements ExtensionFactory {

    @Override
    public <T> T getExtension(final String key, final Class<T> clazz) {
        if (clazz.isInterface() && clazz.isAnnotationPresent(SPI.class)) {
            ExtensionLoader<T> extensionLoader = ExtensionLoader.getExtensionLoader(clazz);
            return extensionLoader.getDefaultJoin();
        }
        return null;
    }
}
