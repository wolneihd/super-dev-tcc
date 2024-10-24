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

import javax.transaction.Transactional;

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
    public List<Usuario> addNewMessages(List<Usuario> usuarios) {
        System.out.println("-- entrnando no serviçO!");
        List<Usuario> savedUsuarios = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            // Verifica se já existe um usuário com o mesmo userId
            Usuario existingUsuario = usuariosRepository.findByUserId(usuario.getUserId());

            if (existingUsuario == null) {
                // Salva o novo usuário se não existir
                existingUsuario = usuariosRepository.save(usuario);
            } else {
                // Atualiza os dados do usuário existente
                existingUsuario.setFirstName(usuario.getFirstName());
                existingUsuario.setLastName(usuario.getLastName());
                usuariosRepository.save(existingUsuario); // Salva as alterações no usuário
            }

            // Adiciona novas mensagens ao usuário existente
            for (Mensagem mensagem : usuario.getMensagens()) {
                Mensagem newMensagem = new Mensagem();
                newMensagem.setTextMsg(mensagem.getTextMsg());
                newMensagem.setTimestampCod(mensagem.getTimestampCod());
                newMensagem.setTiposMensagem(mensagem.getTiposMensagem());
                newMensagem.setUsuario(existingUsuario); // Associa a nova mensagem ao usuário
                newMensagem.setAnalise_ia(mensagem.getAnalise_ia());
                newMensagem.setCategoria(mensagem.getCategoria());
                newMensagem.setFeedback(mensagem.getFeedback());
                mensagensRepository.save(newMensagem); // Salva como um novo registro
            }

            savedUsuarios.add(existingUsuario);
        }
        return savedUsuarios;
    }
}