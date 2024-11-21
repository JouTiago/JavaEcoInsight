package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.IUsuarioDao;
import br.com.ecoinsight.model.Usuario;
import br.com.ecoinsight.util.JWTUtil;

class UsuarioBo implements IUsuarioBo { // Package-private
    private final IUsuarioDao usuarioDao;

    UsuarioBo(IUsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public boolean solicitarAlteracaoSenha(String email) throws Exception {
        Usuario usuario = usuarioDao.pesquisarUsuarioPorEmail(email);
        if (usuario != null) {
            String resetToken = JWTUtil.gerarToken(usuario.getId(), usuario.getEmail()); // Passa o userId e o email
            boolean tokenSalvo = usuarioDao.salvarTokenRedefinicao(email, resetToken);
            if (tokenSalvo) {
                System.out.println("Link de redefinição: /reset-password?token=" + resetToken);
                return true;
            }
        }
        throw new Exception("Usuário não encontrado.");
    }


    @Override
    public boolean alterarSenha(String token, String novaSenha) throws Exception {
        String email = JWTUtil.validarToken(token);
        boolean tokenValido = usuarioDao.validarToken(email, token);
        if (tokenValido) {
            return usuarioDao.alterarSenha(email, novaSenha);
        }
        throw new Exception("Token inválido ou expirado.");
    }

    @Override
    public String login(String email, String senha) throws Exception {
        Usuario usuario = usuarioDao.pesquisarUsuarioPorEmail(email);
        if (usuario != null && usuario.getSenha().equals(senha)) {
            return JWTUtil.gerarToken(usuario.getId(), usuario.getEmail()); // Passa o userId e o email
        }
        throw new Exception("Credenciais inválidas.");
    }

    @Override
    public boolean cadastrar(Usuario usuario) throws Exception {
        return usuarioDao.cadastrarUsuario(usuario);
    }
}
