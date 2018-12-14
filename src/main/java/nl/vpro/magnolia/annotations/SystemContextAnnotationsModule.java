package nl.vpro.magnolia.annotations;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Slf4j
public class SystemContextAnnotationsModule implements ModuleLifecycle {

    @Inject
    public SystemContextAnnotationsModule() {

    }

    @Override
    public void start(ModuleLifecycleContext moduleLifecycleContext) {

    }

    @Override
    public void stop(ModuleLifecycleContext moduleLifecycleContext) {


    }
}
