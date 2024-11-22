package br.com.ecoinsight.bo;

import br.com.ecoinsight.dao.IUsuarioDao;
import br.com.ecoinsight.exception.*;
import br.com.ecoinsight.model.Usuario;
import br.com.ecoinsight.util.JWTUtil;

class UsuarioBo implements IUsuarioBo {
    private final IUsuarioDao usuarioDao;

    UsuarioBo(IUsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    @Override
    public String solicitarAlteracaoSenha(String email) {
        try {
            Usuario usuario = usuarioDao.pesquisarUsuarioPorEmail(email);
            if (usuario != null) {
                String resetToken = JWTUtil.gerarToken(usuario.getId(), usuario.getEmail());
                boolean tokenSalvo = usuarioDao.salvarTokenRedefinicao(email, resetToken);
                if (tokenSalvo) {
                    System.out.println("Link de redefinição: /reset-password?token=" + resetToken);
                    return resetToken;
                }
            }
            throw new ResourceNotFoundException("Usuário com o email " + email + " não encontrado.");
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao processar a solicitação de alteração de senha.", e);
        }
    }

    @Override
    public void alterarSenha(String token, String novaSenha) {
        try {
            String email = JWTUtil.validarToken(token);
            boolean tokenValido = usuarioDao.validarToken(email, token);

            if (!tokenValido) {
                throw new UnauthorizedException("Token inválido ou expirado.");
            }
            if (novaSenha == null || novaSenha.trim().isEmpty()) {
                throw new ValidationException("A nova senha não pode ser vazia.");
            }

            Usuario usuario = usuarioDao.pesquisarUsuarioPorEmail(email);
            if (usuario == null) {
                throw new ResourceNotFoundException("Usuário com o email " + email + " não encontrado.");
            }
            if (!usuario.isSenhaForte(novaSenha)) {
                throw new ValidationException("A nova senha não é forte o suficiente.");
            }

            usuarioDao.alterarSenha(usuario.getEmail(), novaSenha);
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao processar a alteração de senha.", e);
        }
    }

    @Override
    public String login(String email, String senha) {
        try {
            Usuario usuario = usuarioDao.pesquisarUsuarioPorEmail(email);
            if (usuario != null && usuario.getSenha().equals(senha)) {
                return JWTUtil.gerarToken(usuario.getId(), usuario.getEmail());
            }
            throw new UnauthorizedException("Credenciais inválidas.");
        } catch (DatabaseException e) {
            throw new InternalServerErrorException("Erro ao processar o login.", e);
        }
    }

    @Override
    public void cadastrar(Usuario usuario) {
        try {
            System.out.println("Validando e cadastrando usuário: " + usuario);
            if (!usuario.isSenhaForte(usuario.getSenha())) {
                throw new ValidationException("Senha fraca.");
            }
            usuarioDao.cadastrarUsuario(usuario);
            System.out.println("Usuário cadastrado com sucesso no DAO.");
        } catch (Exception e) {
            System.err.println("Erro em UsuarioBo.cadastrar: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

}
