package br.com.ecoinsight.bo;

import br.com.ecoinsight.model.Projeto;
import org.glassfish.jersey.spi.Contract;

import java.util.List;

@Contract
public interface IProjetoBo {
    int cadastrarProjeto(Projeto projeto, int userId) throws Exception;
    List<Projeto> listarProjetosPorUsuario(int userId) throws Exception;
    Projeto obterProjetoPorId(int projectId, int userId) throws Exception;
}
