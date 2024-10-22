package tcc.superdev.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tcc.superdev.model.Usuario;
import tcc.superdev.model.Mensagem;
import tcc.superdev.repository.MensagensRepository;
import tcc.superdev.repository.UsuariosRepository;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

@Service
public class UsuariosService {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private MensagensRepository mensagensRepository;

    public List<Usuario> getAllUsuariosWithMessages() {
        List<Usuario> usuarios = usuariosRepository.findAll();
        List<Mensagem> mensagens = mensagensRepository.findAll(); // Move a busca para fora do loop

        for (Usuario usuario : usuarios) {
            for (Mensagem mensagem : mensagens) {
                // Verifica se o usuário da mensagem é o mesmo que o usuário atual
                if (mensagem.getUsuario() != null && mensagem.getUsuario().getId() != null &&
                        mensagem.getUsuario().getId().equals(usuario.getId())) {
                    usuario.addMensagem(mensagem);
                    System.out.println("-- Mensagem adicionada para usuário " + usuario.getId());
                } else {
                    System.out.println("-- Mensagem não pertence ao usuário " + usuario.getId());
                }
            }
        }
        return usuarios;
    }

    @Transactional
    public List<Usuario> addNewMessages(List<Usuario> usuarios) {
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
            mensagensRepository.save(newMensagem); // Salva como um novo registro
        }

        savedUsuarios.add(existingUsuario);
    }
    return savedUsuarios;
}
}
