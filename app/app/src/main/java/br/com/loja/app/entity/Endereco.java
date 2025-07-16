package br.com.loja.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "enderecos")
public class Endereco {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long enderecoId;

@Column(nullable = false, length = 150)
private String rua;

@Column(nullable = false, length = 50)
private String cidade;

@Column(nullable = false)
private String uf;

@Column(nullable = false, length = 10)
private String numero;

@Column(nullable = false, length = 100)
private String complemento;

@Column(nullable = false)
private String cep;

@Column(nullable = false, length = 15)
private String bairro;

@OneToOne
@JoinColumn(name = "Cliente_id", nullable = false, unique = true) // coluna será criada em Endereco
private Cliente cliente;



}
