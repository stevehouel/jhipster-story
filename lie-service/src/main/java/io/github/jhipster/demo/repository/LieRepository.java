package io.github.jhipster.demo.repository;

import io.github.jhipster.demo.domain.Lie;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the Lie entity.
 */
@SuppressWarnings("unused")
public interface LieRepository extends MongoRepository<Lie,String> {

}
