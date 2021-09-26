package j.spring.framework.core.ioc;

public abstract class AbstractGenerator implements Generator {

    private final Class<?> type;

    public AbstractGenerator(Class<?> clazz) {
        this.type = clazz;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
