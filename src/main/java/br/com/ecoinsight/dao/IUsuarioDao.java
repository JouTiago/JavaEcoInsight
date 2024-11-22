package br.com.ecoinsight.dao;

import br.com.ecoinsight.model.Usuario;
import org.glassfish.jersey.spi.Contract;

@Contract
public interface IUsuarioDao {
    boolean cadastrarUsuario(Usuario usuario);
    Usuario pesquisarUsuarioPorEmail(String email);
    boolean salvarTokenRedefinicao(String email, String token);
    boolean alterarSenha(String email, String novaSenha);
    boolean validarToken(String email, String token);
}
