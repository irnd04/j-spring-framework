package j.spring.framework.core.web.handlermapping;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class AntPatternComparatorTest {

    private static Stream<Arguments> provideAntPatternComparatorTest()  {
        return Stream.of(
            Arguments.of("/good/z",
                    Lists.newArrayList("/good/{a}", "/**", "/good*/**", "/good**", "/good/z"),
                    Lists.newArrayList("/good/z", "/good/{a}", "/good**", "/good*/**", "/**")),
            Arguments.of("/good/ww/1",
                    Lists.newArrayList("/good/ww/{g}", "/{g}/ww/1"),
                    Lists.newArrayList("/good/ww/{g}", "/{g}/ww/1"))
        );
    }

    @ParameterizedTest
    @MethodSource("provideAntPatternComparatorTest")
    void antPatternComparatorTest(String path, List<String> actual, List<String> expected) {
        actual.sort(new AntPatternComparator(path));
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> provideAmbiguousRequestMapping() {
        return Stream.of(
            Arguments.of("/goo/woo", "/{ggg}/woo", "/goo/{g}"),
            Arguments.of("/fff/zzz/qqq", "/fff/zzz/{G}", "/fff/zzz/{GG}"),
            Arguments.of("/a/b/c/d/e/f/g", "/a/b/{G}/{G}/e/f/g", "/a/b/{G}/c/{G}/f/g"),
            Arguments.of("/woo/wa/han", "/{G}/wa/{G}", "/{zdfe}/wa/{cdwh1}"),
            Arguments.of("/ww", "/{var1}", "/{ww}")
        );
    }

    @ParameterizedTest
    @MethodSource("provideAmbiguousRequestMapping")
    @DisplayName("모호한 url 테스트")
    void ambiguousRequestMapping(String path, String a, String b) {
        AntPatternComparator comparator = new AntPatternComparator(path);
        int actual = comparator.compare(a, b);
        assertSame(0, actual);
    }

}