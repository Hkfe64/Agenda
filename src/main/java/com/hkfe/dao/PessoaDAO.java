/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hkfe.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.hkfe.modelo.Pessoa;

/**
 *
 * @author hkfe6
 */
public class PessoaDAO {

    private EntityManager getEntityManager() {
        EntityManagerFactory factory = null;
        EntityManager entityManager = null;
        try {
            // Obtém o factory a partir da unidade de persistência.
            factory = Persistence.createEntityManagerFactory("AgendaPU");
            // Cria um entity manager.
            entityManager = factory.createEntityManager();
            // Fecha o factory para liberar os recursos utilizado.
        } catch (Exception e) {
            System.out.println("Teste");
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
        return entityManager;
    }

    public Pessoa createPessoa(Pessoa pessoa) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            entityManager.close();
        }

        return pessoa;
    }

    public List<Pessoa> readPessoas() {

        List<Pessoa> pessoas = new ArrayList<>();

        EntityManager entityManager = getEntityManager();
        try {
            pessoas = entityManager.createQuery("FROM " + Pessoa.class.getName()).getResultList();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            entityManager.close();
        }
        return pessoas;
    }

    public Pessoa readPessoa(int pessoa_id) {
        Pessoa pessoa = null;

        EntityManager entityManager = getEntityManager();
        try {
            pessoa = entityManager.find(Pessoa.class, pessoa_id);
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            entityManager.close();
        }
        return pessoa;
    }

    public Pessoa updatePessoa(Pessoa pessoa) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(pessoa);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            entityManager.close();
        }
        return pessoa;
    }

    public void deletePessoa(Pessoa pessoa) {
        EntityManager entityManager = getEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(entityManager.contains(pessoa) ? pessoa : entityManager.merge(pessoa));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        } finally {
            entityManager.close();
        }
    }
}
