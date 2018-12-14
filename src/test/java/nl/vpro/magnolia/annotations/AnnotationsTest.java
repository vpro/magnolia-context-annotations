package nl.vpro.magnolia.annotations;

import info.magnolia.context.SystemContext;
import info.magnolia.objectfactory.Components;
import info.magnolia.test.mock.MockComponentProvider;
import info.magnolia.test.mock.MockContext;

import org.junit.Before;
import org.junit.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since ...
 */
public class AnnotationsTest {


    public static class A {
        @MgnlSystemContext
        public String stuff() {
            return "aa";
        }
    }

    Injector injector;
    A instance;
    SystemContext systemContext = new MockContext();

    @Before
    public void setup() {
        MockComponentProvider mocks = new MockComponentProvider();
        Components.setComponentProvider(mocks);
        mocks.setInstance(SystemContext.class, systemContext);

        injector = Guice.createInjector(new ContextAnnotations());
        instance = injector.getInstance(A.class);



    }

    @Test
    public void test() {
        assertThat(instance.stuff()).isEqualTo("aa");
    }
}
