package service.back;

import java.util.List;
import java.util.Set;

public interface IService<K, V> {
    boolean insert(V vo) throws Exception;
    List<V> list() throws Exception;
    boolean update(V vo) throws Exception;
    boolean delete(Set<K> ids)throws Exception;
}
