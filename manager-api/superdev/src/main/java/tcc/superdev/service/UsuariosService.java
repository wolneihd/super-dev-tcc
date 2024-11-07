package tcc.superdev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.superdev.model.Usuario;
import tcc.superdev.model.Mensagem;
import tcc.superdev.repository.MensagensRepository;
import tcc.superdev.repository.UsuariosRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private MensagensRepository mensagensRepository;

    public List<Usuario> getAllUsuariosWithMessages() {
        List<Usuario> usuarios = usuariosRepository.findAll();
        List<Mensagem> mensagens = mensagensRepository.findAll();

        Map<Long, List<Mensagem>> mensagensPorUsuario = new HashMap<>();

        // Preencher o mapa com mensagens
        for (Mensagem mensagem : mensagens) {
            if (mensagem.getUsuario() != null && mensagem.getUsuario().getId() != null) {
                Long usuarioId = mensagem.getUsuario().getId();
                mensagensPorUsuario.computeIfAbsent(usuarioId, k -> new ArrayList<>()).add(mensagem);
            }
        }

        // Associar mensagens aos usuários
        for (Usuario usuario : usuarios) {
            List<Mensagem> mensagensDoUsuario = mensagensPorUsuario.get(usuario.getId());
            if (mensagensDoUsuario != null) {
                for (Mensagem mensagem : mensagensDoUsuario) {
                    // Verifica se a mensagem já existe
                    if (!usuario.getMensagens().contains(mensagem)) {
                        usuario.addMensagem(mensagem);
                    }
                }
            }
        }
        return usuarios;
    }

    @Transactional
    public Usuario addNewMessages(Usuario usuario) {
        Usuario novoUsuario = new Usuario();
        Usuario usuarioExistente = usuariosRepository.findByUserId(usuario.getUserId());

        if (usuarioExistente == null) {
            novoUsuario.setFirstName(usuario.getFirstName());
            novoUsuario.setLastName(usuario.getLastName());
            novoUsuario.setUserId(usuario.getUserId());
            usuariosRepository.save(novoUsuario);

            for (Mensagem mensagem : usuario.getMensagens()) {
                Mensagem novaMensagem = new Mensagem();
                novaMensagem.setTipoMensagem(mensagem.getTipoMensagem());
                novaMensagem.setTimestamp(mensagem.getTimestamp());
                novaMensagem.setTextMsg(mensagem.getTextMsg());
                novaMensagem.setFeedback(mensagem.getFeedback());
                novaMensagem.setCategoria(mensagem.getCategoria());
                novaMensagem.setAnalise_ia(mensagem.getAnalise_ia());
                novaMensagem.setUsuario(novoUsuario);
                mensagensRepository.save(novaMensagem);
            }

        } else {
            for (Mensagem mensagem : usuario.getMensagens()) {
                Mensagem novaMensagem = new Mensagem();
                novaMensagem.setTipoMensagem(mensagem.getTipoMensagem());
                novaMensagem.setTimestamp(mensagem.getTimestamp());
                novaMensagem.setTextMsg(mensagem.getTextMsg());
                novaMensagem.setFeedback(mensagem.getFeedback());
                novaMensagem.setCategoria(mensagem.getCategoria());
                novaMensagem.setAnalise_ia(mensagem.getAnalise_ia());
                novaMensagem.setUsuario(usuarioExistente);
                mensagensRepository.save(novaMensagem);
            }
        }

        return usuario;
    }

}
