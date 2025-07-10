package br.com.loja.app.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table
public class Cliente {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long clienteId;

@Column(nullable = false, length = 150)
private String nome;

@Column(nullable = false, length = 11, unique = true)
private String cpf;

@Column(nullable = false, length = 1)
private Character sexo;

@Temporal(TemporalType.DATE)
private LocalDate dataNascimento;

@OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
private Endereco endereco;

@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
private List<Telefone> telefones = new ArrayList<>();


}
