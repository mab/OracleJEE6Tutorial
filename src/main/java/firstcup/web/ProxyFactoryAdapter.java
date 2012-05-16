package firstcup.web;

import org.jboss.resteasy.client.ProxyFactory;
import org.jboss.resteasy.plugins.providers.RegisterBuiltin;
import org.jboss.resteasy.spi.ResteasyProviderFactory;

public class ProxyFactoryAdapter {

    public static <T> T create(final Class<T> clazz) {
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
        final T t = ProxyFactory.create(clazz, "http://localhost:8080/dukesageservice-0.0.1-SNAPSHOT");
        return t;
    }

}
