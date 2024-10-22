package tcc.superdev.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tcc.superdev.model.Mensagem;

public interface MensagensRepository extends JpaRepository<Mensagem, Integer>{

}
