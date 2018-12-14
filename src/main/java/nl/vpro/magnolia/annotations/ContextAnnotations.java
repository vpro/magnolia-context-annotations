package nl.vpro.magnolia.annotations;

import lombok.extern.slf4j.Slf4j;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;

/**
 * @author Michiel Meeuwissen
 * @since ...
 */
@Slf4j
public class ContextAnnotations extends AbstractModule  {


    @Override
    protected void configure() {
        DoInSystemContextInterceptor doInSystemContextInterceptor = new DoInSystemContextInterceptor();
        requestInjection(doInSystemContextInterceptor);
        bindInterceptor(Matchers.annotatedWith(MgnlSystemContext.class), Matchers.any(), doInSystemContextInterceptor);
        bindInterceptor(Matchers.any(), Matchers.annotatedWith(MgnlSystemContext.class), doInSystemContextInterceptor);

    }

}
