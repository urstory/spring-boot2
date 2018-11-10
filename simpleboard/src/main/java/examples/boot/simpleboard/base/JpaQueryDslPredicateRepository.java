package examples.boot.simpleboard.base;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

@NoRepositoryBean
public interface JpaQueryDslPredicateRepository<T, ID extends Serializable>
        extends JpaRepository<T, ID>, QuerydslPredicateExecutor<T> {
    @Override
    List<T> findAll(OrderSpecifier<?>... orders);

    @Override
    List<T> findAll(Predicate predicate);

    @Override
    List<T> findAll(Predicate predicate, OrderSpecifier<?>... orders);

    @Override
    List<T> findAll(Predicate predicate, Sort sort);
}
