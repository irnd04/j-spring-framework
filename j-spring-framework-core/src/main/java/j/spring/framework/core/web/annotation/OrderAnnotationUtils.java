package j.spring.framework.core.web.annotation;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

public class OrderAnnotationUtils {

    public static final int DEFAULT_ORDER = 0;

    private static class HasOrderItem<T> implements Comparable<HasOrderItem<T>> {
        private final int order;
        private final T o;

        HasOrderItem(int order, T o) {
            this.order = order;
            this.o = o;
        }


        @Override
        public int compareTo(HasOrderItem o) {
            return order - o.order;
        }
    }

    public static <T> List<T> ordered(List<T> list) {
        List<HasOrderItem<T>> result = Lists.newArrayList();

        for (T o : list) {
            Order orderAnnotation = o.getClass().getAnnotation(Order.class);
            int order = DEFAULT_ORDER;
            if (orderAnnotation != null) {
                order = orderAnnotation.value();
            }
            result.add(new HasOrderItem<>(order, o));
        }

        return ImmutableList.copyOf(result.stream().sorted().map(item -> item.o).collect(Collectors.toList()));
    }

}
