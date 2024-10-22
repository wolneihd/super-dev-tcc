package tcc.superdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.superdev.model.Usuario;

public interface UsuariosRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByUserId(Long userId);    
}
