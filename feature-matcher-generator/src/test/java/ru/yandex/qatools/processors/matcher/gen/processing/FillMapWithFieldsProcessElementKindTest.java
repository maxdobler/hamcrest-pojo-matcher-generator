package ru.yandex.qatools.processors.matcher.gen.processing;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.rules.ExternalResource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.processors.matcher.gen.util.helpers.GeneratorHelper;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * User: lanwen
 * Date: 19.10.14
 * Time: 20:06
 */
@RunWith(Parameterized.class)
public class FillMapWithFieldsProcessElementKindTest {

    @Parameterized.Parameters(name = "{0}")
    public static Collection<ElementKind> data() {
        return Stream.of(ElementKind.values())
                .filter(kind -> !asList(ElementKind.FIELD, ElementKind.CLASS).contains(kind))
                .collect(Collectors.toList());
    }

    private static FillMapWithFieldsProcess process;

    @ClassRule
    public static ExternalResource prepare = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            process = FillMapWithFieldsProcess.fillMapOfClassDescriptionsProcess(
                    mock(GeneratorHelper.class)
            );
        }
    };

    @Parameterized.Parameter
    public ElementKind kind;


    @Test
    public void shouldProcessOnlyFieldsAndClasses() throws Exception {
        Element element = mock(Element.class);
        when(element.getKind()).thenReturn(kind);

        process.accept(element);

        verify(element, only()).getKind();
        verifyNoMoreInteractions(element);
    }
}
