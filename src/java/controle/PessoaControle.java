/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.PessoaDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import modelo.Pessoa;

/**
 *
 * @author hkfe6
 */
@ManagedBean
@ViewScoped
public class PessoaControle implements Serializable {

    private Pessoa pessoa = new Pessoa();
    private List<Pessoa> pessoas = new ArrayList<>();

    @PostConstruct
    public void init() {
        listarPessoas();
    }

    public void listarPessoas() {
        pessoas = new PessoaDAO().readPessoas();
        limparPessoa();
    }
    
    public void limparPessoa() {
        pessoa = new Pessoa();
    }
    
    public void salvarPessoa() {
        if (pessoa.getId() == null) {
            new PessoaDAO().createPessoa(pessoa);
        } else {
            new PessoaDAO().updatePessoa(pessoa);
        }
        listarPessoas();
    }

    public void excluirPessoa() {
        new PessoaDAO().deletePessoa(pessoa);
        listarPessoas();
    }

    /**
     * @return the pessoa
     */
    public Pessoa getPessoa() {
        return pessoa;
    }

    /**
     * @param pessoa the pessoa to set
     */
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    /**
     * @return the pessoas
     */
    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    /**
     * @param pessoas the pessoas to set
     */
    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }

}
