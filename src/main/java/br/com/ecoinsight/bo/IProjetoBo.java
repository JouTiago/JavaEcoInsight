package br.com.ecoinsight.bo;

import br.com.ecoinsight.model.Projeto;
import org.glassfish.jersey.spi.Contract;

import java.util.List;

@Contract
public interface IProjetoBo {
    int cadastrarProjeto(Projeto projeto, int userId);
    List<Projeto> listarProjetosPorUsuario(int userId);
    Projeto obterProjetoPorId(int projectId, int userId);
    boolean editarProjeto(int projectId, int userId, Projeto projeto);
    boolean excluirProjeto(int projectId, int userId);
}
