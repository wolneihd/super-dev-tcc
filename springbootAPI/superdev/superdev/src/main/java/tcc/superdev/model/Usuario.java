package tcc.superdev.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usuarios")
public class Usuario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long userId;
    private String firstName;
    private String lastName;

    @OneToMany
    @JoinColumn(name = "user_id")
    @JsonManagedReference // Para controlar a serialização
    private List<Mensagem> mensagens = new ArrayList<Mensagem>();


    public Usuario() {}
 
    // Getters e Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<Mensagem> mensagens) {
        this.mensagens = mensagens;
    }

    // Método para adicionar uma mensagem à lista
    public void addMensagem(Mensagem mensagem) {
        mensagens.add(mensagem); // Adiciona a nova mensagem
    }

}
