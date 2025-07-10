package br.com.loja.app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

@Table(name = "telefones")
public class Telefone {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long telefoneId;

@Column(nullable = false, length = 3)
private String tipo;

@Column(nullable = false, length = 9)
private String numero;

@Column(nullable = false, length = 3)
private String ddd;

@ManyToOne
@JoinColumn(name = "cliente_id", nullable = false) 
private Cliente cliente;


}
