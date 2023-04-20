package com.serviceops.assetdiscovery.repository;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomRepository {

    private final EntityManager em;

    CriteriaBuilder criteriaBuilder;

    public CustomRepository(EntityManager em) {
        this.em = em;
    }

    @PostConstruct
    void init() {
        this.criteriaBuilder = em.getCriteriaBuilder();
    }

    public <F, T> Optional<T> findByColumn(final String column, final F value, final Class<T> clazz) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> from = query.from(clazz);
        query.select(from).where(criteriaBuilder.equal(from.get(column), value));
        try {
            T result = em.createQuery(query).getSingleResult();
            return Optional.of(result);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public <F, T> List<T> findByColumns(List<String> column, List<F> value, Class<T> clazz) {
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        Predicate[] predicates = new Predicate[column.size()];
        for (int i = 0; i < column.size(); ++i) {
            predicates[i] = criteriaBuilder.equal(root.get(column.get(i)), value.get(i));
        }
        cq.select(root).where(predicates);
        return em.createQuery(cq).getResultList();
    }

    public <T> List<T> findAll(final Class<T> clazz) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> from = query.from(clazz);
        query.select(from);
        return em.createQuery(query).getResultList();
    }

    public <F, T> List<T> findAllByColumnName(final Class<T> clazz, final String column, F value) {
        CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
        Root<T> from = query.from(clazz);
        query.select(from).where(criteriaBuilder.equal(from.get(column), value));
        return em.createQuery(query).getResultList();
    }

    @Transactional
    public <T> void save(T t) {
        if (em.contains(t)) {
            em.merge(t);  // update if already managed
        } else {
            em.persist(t); // insert if not managed
        }
        em.flush();
    }

    @Transactional
    public <T> void deleteById(final Class<T> clazz, Long id, String columnName) {
        CriteriaDelete<T> query = criteriaBuilder.createCriteriaDelete(clazz);
        Root<T> from = query.from(clazz);
        query.where(criteriaBuilder.equal(from.get(columnName),
                criteriaBuilder.parameter(Long.class, columnName)));
        em.createQuery(query).setParameter(columnName, id).executeUpdate();
    }

    public <T> List<T> findPaginatedData(Pageable pageable, String sortBy, String sortDir, Class<T> clazz) {
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        cq.select(root);

        if (sortDir.equals("asc")) {
            return em.createQuery(cq.orderBy(criteriaBuilder.asc(root.get(sortBy))))
                    .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
                    .getResultList();
        } else
            return em.createQuery(cq.orderBy(criteriaBuilder.desc(root.get(sortBy))))
                    .setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize())
                    .getResultList();
    }

    public <T> int getCount(Class<T> clazz) {
        CriteriaQuery<T> cq = criteriaBuilder.createQuery(clazz);
        Root<T> root = cq.from(clazz);
        cq.select(root);
        return em.createQuery(cq).getResultList().size();
    }


}
