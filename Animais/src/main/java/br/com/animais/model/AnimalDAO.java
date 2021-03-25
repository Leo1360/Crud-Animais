/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.animais.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe que intermedia o acesso ao bando de dados e as operações referentes aos objetos da classe Animal
 * @author Leonardo
 */
public class AnimalDAO {
    private Connection con;

    public AnimalDAO(){
        try{
            con = ConnectionFactory.getConnection();
            con.setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Falha ao criar conexão com o banco");
        }
    }
    
    /**
     * Cadastra o animal no bando de dados
     * @param animal
     * @return int id do novo animal cadastrado ou zero caso não seja possivel cadastrar
     */
    public int cadastrar(Animal animal) {
    int id = 0;
        try(PreparedStatement stm = con.prepareStatement("INSERT INTO cad_animais(nome,nomeCientifico,peso) VALUES ( ? , ? , ? )",Statement.RETURN_GENERATED_KEYS)){
            stm.setString(1, animal.getNome());
            stm.setString(2, animal.getNomeCientifico());
            stm.setFloat(3, animal.getPeso());
            
            stm.execute();
            try(ResultSet rst = stm.getGeneratedKeys()){
                while(rst.next()){
                    id = rst.getInt(1);
                }
            }
            
            con.commit();
            
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Iniciando Rollback");
            
            try {
                con.rollback();
                System.out.println("Rollback Concluido com sucesso");
            }catch (SQLException ex) {
                ex.printStackTrace();
                System.out.println("Erro no rollback");
            }
            
        }
        
        return id;
    }

    /**
     * Altera o registro do banco de dados de acordo com o id do animal
     * @param animal
     * @return boolean true caso o cadastro seja executado com sucesso ou false caso o id seja invalido
     */
    public boolean alterar(Animal animal){
        String sql = "UPDATE cad_animais SET @ WHERE ID= ?";
        sql = prepararQueryAlteracao(sql,animal);
        System.out.println("Query="+sql);
        try(PreparedStatement stm = con.prepareStatement(sql)){
            int k = 1;
            if(sql.contains("nome=")){
                System.out.println("nome");
                stm.setString(k, animal.getNome());
                k++;
            }
            if(sql.contains("nomeCientifico=")){
                System.out.println("NomeCientifico");
                stm.setString(k, animal.getNomeCientifico());
                k++;
            }
            if(sql.contains("peso=")){
                System.out.println("Peso");
                stm.setFloat(k, animal.getPeso());
                k++;
            }
            stm.setInt(k, animal.getId());
            
            stm.execute();
            con.commit();
            int ct = stm.getUpdateCount();
            if(ct>0){
                return true;
            }else{
                return false;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erro no stm");
            try{
                con.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
          return false;
        }

    }
    /**
     * Prepara uma query SQL inserindo "@" pelos campos não nulos de um animal
     * @param sql String com a query SQL em que @ sera substituido pelo campos não nulos do animal
     * @param animal
     * @return String SQL com os campos que serão setados
     */
    private String prepararQueryAlteracao(String sql,Animal animal) {
        String colunas = "";
        if(animal.getNome() != ""){
            colunas += "nome= ?";
        }
        if(animal.getNomeCientifico() != ""){
            if(!colunas.equals("")){
                colunas += ",";
            }
            colunas += "nomeCientifico= ?";
        }
        if(animal.getPeso() != 0){
            if(!colunas.equals("")){
                colunas += ",";
            }
            colunas += "peso= ?";
        }
        sql = sql.replaceFirst("@", colunas);
        System.out.println(sql);
        return sql;
        
    }
    /**
     * Deleta um registro do banco de dados
     * @param id
     * @return boolean true caso o resgistro seja cadastrado corretamente ou false o id seja invalido
     */
    public boolean deletar(int id){
        try(PreparedStatement stm = con.prepareStatement("DELETE FROM cad_animais WHERE id= ?")){
            stm.setInt(1, id);
            
            stm.execute();
            con.commit();
            
        } catch (SQLException e) {
            e.printStackTrace();
            
            try{
                con.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        }
        return true;
        
    }
    /**
     * Localiza um animal no banco de dados apartir do id
     * @param id
     * @return Animal de acordo com id ou null em caso o id não seja loalizado
     */
    public Animal buscarPorId(int id){
        try(PreparedStatement stm = con.prepareStatement("SELECT * FROM cad_animais WHERE id= ?")){
            stm.setInt(1, id);
            
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                while(rst.next()){
                    id = rst.getInt("id");
                    String nome = rst.getString("nome");
                    String nomeCientifico = rst.getString("nomeCientifico");
                    float peso = rst.getFloat("peso");
                    Animal animal = new Animal();
                    animal.setId(id);
                    animal.setNome(nome);
                    animal.setNomeCientifico(nomeCientifico);
                    animal.setPeso(peso);
                    return animal;
                }  
            }
            
             
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * Lista todos os animais existentes na base de dados
     * @return Lista de animais existentes
     */
    public List<Animal> verTodos(){
        List<Animal> animais = new ArrayList();
        try(PreparedStatement stm = con.prepareStatement("SELECT * FROM cad_animais")){
            
            stm.execute();
            try(ResultSet rst = stm.getResultSet()){
                while(rst.next()){
                    int id = rst.getInt("id");
                    String nome = rst.getString("nome");
                    String nomeCientifico = rst.getString("nomeCientifico");
                    float peso = rst.getFloat("peso");
                    Animal animal = new Animal();
                    animal.setId(id);
                    animal.setNome(nome);
                    animal.setNomeCientifico(nomeCientifico);
                    animal.setPeso(peso);
                    animais.add(animal);
                }  
            }
            
             
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return animais;
        
    }
    
}
