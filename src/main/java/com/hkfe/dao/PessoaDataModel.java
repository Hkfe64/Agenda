package com.hkfe.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.hkfe.modelo.Pessoa;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

public class PessoaDataModel extends LazyDataModel<Pessoa> {

    private List<Pessoa> datasource;

    @Override
    public Pessoa getRowData(String rowKey) {
        try {
            for (Pessoa pessoa : datasource) {
                if (pessoa.getId().equals(Integer.parseInt(rowKey))) {
                    return pessoa;
                }
            }
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    @Override
    public Object getRowKey(Pessoa pessoa) {
        return pessoa.getId();
    }

    @Override
    public List<Pessoa> load(int first, int pageSize, Map<String, SortMeta> sortMeta,
            Map<String, FilterMeta> filterMeta) {
        try {
            EntityManager entityManager = new PessoaDAO().getEntityManager();

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Pessoa> cr = cb.createQuery(Pessoa.class);
            Root<Pessoa> root = cr.from(Pessoa.class);
            cr.select(root);

            // Filtro
            List<Predicate> predicates = new ArrayList<>();
            for (FilterMeta meta : filterMeta.values()) {
                try {
                    String filterField = meta.getFilterField();
                    Object filterValue = meta.getFilterValue();

                    if (filterValue != null) {
                        predicates.add(cb.like(root.get(filterField), "%" + filterValue + "%"));
                    }
                } catch (Exception e) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                }
            }
            if (!predicates.isEmpty())
                cr.where(predicates.toArray(new Predicate[] {}));

            List<Order> orders = new ArrayList<>();
            // Ordem
            if (sortMeta != null && !sortMeta.isEmpty()) {
                for (SortMeta meta : sortMeta.values()) {
                    orders.add(SortOrder.ASCENDING.equals(meta.getSortOrder()) ? cb.asc(root.get(meta.getSortField()))
                            : cb.desc(root.get(meta.getSortField())));
                }
            }
            if (!orders.isEmpty())
                cr.orderBy(orders);

            Query query = entityManager.createQuery(cr);
            query.setMaxResults(pageSize);
            query.setFirstResult(first);
            datasource = query.getResultList();

            // rowCount
            this.setRowCount(
                    Integer.parseInt(String
                            .valueOf(entityManager.createQuery("SELECT count(id) FROM Pessoa p").getSingleResult())));

            return datasource;
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
            return null;
        }
    }
}
