package put.poznan.persistent;

public interface Root {

    void putObject(String name, Object object);

    <T> T getObject(String name, Class<T> aClass);
}
