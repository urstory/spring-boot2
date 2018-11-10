package examples.boot.simpleboard.repository;

import examples.boot.simpleboard.base.JpaQueryDslPredicateRepository;
import examples.boot.simpleboard.domain.ImageFile;

public interface ImageFileRepository
        extends JpaQueryDslPredicateRepository<ImageFile, Long> {
}