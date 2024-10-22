package tcc.superdev.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "mensagens")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Mensagem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    @JsonBackReference
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_tipo_mensagem")
    private TiposMensagem tiposMensagem;

    private Long timestampCod;
    private String textMsg;

    // Getters e Setters

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public TiposMensagem getTiposMensagem() {
        return this.tiposMensagem;
    }

    public void setTiposMensagem(TiposMensagem tiposMensagem) {
        this.tiposMensagem = tiposMensagem;
    }

    public Long getTimestampCod() {
        return this.timestampCod;
    }

    public void setTimestampCod(Long timestampCod) {
        this.timestampCod = timestampCod;
    }

    public String getTextMsg() {
        return this.textMsg;
    }

    public void setTextMsg(String textMsg) {
        this.textMsg = textMsg;
    }

}
