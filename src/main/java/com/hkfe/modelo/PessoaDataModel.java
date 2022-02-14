package com.hkfe.modelo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;

public class PessoaDataModel extends LazyDataModel<Pessoa> {

    private List<Pessoa> datasource;

    public PessoaDataModel(List<Pessoa> datasource) {
        this.datasource = datasource;
    }

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
        List<Pessoa> data = new ArrayList<>();
        // Filtro
        for (Pessoa pessoa : datasource) {
            boolean pertence = true;
            if (filterMeta != null) {
                for (FilterMeta meta : filterMeta.values()) {
                    try {
                        String filterField = meta.getFilterField();
                        Object filterValue = meta.getFilterValue();
                        Field field = Pessoa.class.getDeclaredField(filterField);
                        field.setAccessible(true);
                        String fieldValue = String.valueOf(field.get(pessoa));
                        if (filterValue == null || fieldValue.contains(filterValue.toString())) {
                            pertence = true;
                        } else {
                            pertence = false;
                            break;
                        }
                    } catch (Exception e) {
                        pertence = false;
                    }
                }
            }

            if (pertence) {
                data.add(pessoa);
            }
        }

        // Ordem
        if (sortMeta != null && !sortMeta.isEmpty()) {
            for (SortMeta meta : sortMeta.values()) {
                Collections.sort(data, (pessoa1, pessoa2) -> {
                    try {
                        Field field = Pessoa.class.getDeclaredField(meta.getSortField());
                        field.setAccessible(true);
                        Object value1 = field.get(pessoa1);
                        Object value2 = field.get(pessoa2);

                        // Independe do tipo do objeto
                        int value = ((Comparable<Object>) value1).compareTo(value2);

                        return SortOrder.ASCENDING.equals(meta.getSortOrder()) ? value : -1 * value;
                    } catch (Exception e) {
                        Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
                        throw new RuntimeException();
                    }
                });
            }
        }

        // rowCount
        int dataSize = data.size();
        this.setRowCount(dataSize);

        // paginate
        if (dataSize > pageSize) {
            try {
                return data.subList(first, first + pageSize);
            } catch (IndexOutOfBoundsException e) {
                return data.subList(first, first + (dataSize % pageSize));
            }
        } else {
            return data;
        }
    }
}
