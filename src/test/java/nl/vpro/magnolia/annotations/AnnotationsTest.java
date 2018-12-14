package nl.vpro.magnolia.annotations;

import info.magnolia.context.ContextFactory;
import info.magnolia.context.SystemContext;
import info.magnolia.objectfactory.Components;
import info.magnolia.test.mock.MockComponentProvider;
import info.magnolia.test.mock.MockContext;
import lombok.extern.slf4j.Slf4j;

import org.junit.Before;
import org.junit.Test;
import com.google.inject.Guice;
import com.google.inject.Injector;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Michiel Meeuwissen
 * @since 1.0
 */
@Slf4j
public class AnnotationsTest {


    public static class A {
        @MgnlSystemContext
        public String stuff() {
            log.info("{}", ContextFactory.getInstance().getSystemContext());

            return "aa";
        }
    }

    @MgnlSystemContext
    public static class B {
        public String stuff() {
            log.info("{}", ContextFactory.getInstance().getSystemContext());
            return "bb";
        }
    }

    Injector injector;
    A a;
    B b;
    SystemContext systemContext = new MockContext();

    @Before
    public void setup() {
        MockComponentProvider mocks = new MockComponentProvider();
        Components.setComponentProvider(mocks);
        mocks.setInstance(SystemContext.class, systemContext);

        injector = Guice.createInjector(new ContextAnnotations());
        a = injector.getInstance(A.class);
        b = injector.getInstance(B.class);

    }

    @Test
    public void testa() {
        assertThat(a.stuff()).isEqualTo("aa");
    }

     @Test
    public void testb() {
        assertThat(b.stuff()).isEqualTo("bb");
    }
}
