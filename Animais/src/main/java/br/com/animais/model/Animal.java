/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.animais.model;

/**
 * Classe que representa um animal no sistema
 * @author Leonardo
 */
public class Animal {
    int id;
    private String nome;
    private String nomeCientifico;
    private float peso;

    @Override
    public String toString() {
        return "Animal{" + "id=" + id + ", nome=" + nome + ", nomeCientifico=" + nomeCientifico + ", peso=" + peso + '}';
    }
    
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomeCientifico() {
        return nomeCientifico;
    }

    public void setNomeCientifico(String nomeCientifico) {
        this.nomeCientifico = nomeCientifico;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }
    
}
