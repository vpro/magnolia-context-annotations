package nl.vpro.magnolia.annotations;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.objectfactory.configuration.ComponentConfigurer;
import info.magnolia.objectfactory.configuration.ComponentProviderConfiguration;
import lombok.extern.slf4j.Slf4j;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Slf4j
public class ContextAnnotations extends AbstractModule  implements ComponentConfigurer {


    @Override
    protected void configure() {
        DoInSystemContextInterceptor doInSystemContextInterceptor = new DoInSystemContextInterceptor();
        requestInjection(doInSystemContextInterceptor);
        bindInterceptor(Matchers.annotatedWith(MgnlSystemContext.class), Matchers.not(Matchers.annotatedWith(MgnlSystemContext.class)), doInSystemContextInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(MgnlSystemContext.class), doInSystemContextInterceptor);

    }

    @Override
    public void doWithConfiguration(ComponentProvider parentComponentProvider, ComponentProviderConfiguration configuration) {
        log.info("Installed {}", this);

    }
}
