package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.Usuario;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IUsuarioDao {
    boolean cadastrarUsuario(Usuario usuario) throws Exception;
    Usuario pesquisarUsuarioPorEmail(String email) throws Exception;
    boolean salvarTokenRedefinicao(String email, String token) throws Exception;
    boolean alterarSenha(String email, String novaSenha) throws Exception;
    boolean validarToken(String email, String token) throws Exception;
}
