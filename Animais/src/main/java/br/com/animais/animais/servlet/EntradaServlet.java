/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.animais.animais.servlet;


import br.com.animais.model.Animal;
import br.com.animais.model.AnimalDAO;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  Classe responsavel por receber os requests e direcionar os dados para a operação solicitada e retornar os resultados via Http
 * @author Leonardo
 */
@WebServlet("/crud")
public class EntradaServlet extends HttpServlet {

    /**
     * Executa pesquisa de acordo com o parametro id e retorna o animal localizado no formato Json
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        int id = 0;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        }catch(NumberFormatException e){
            e.printStackTrace();
            //System.out.println("Erro no parsing");
            response.getWriter().print("{'Erro':'parametro [id] precisa ser um numero'}");
            return;
        }
        
        AnimalDAO animalDAO = new AnimalDAO();
        
        Animal animal = animalDAO.buscarPorId(id);
        if(animal != null){
            Gson gson = new Gson();
            String json = gson.toJson(animal);
            response.setContentType("application/json");
            response.getWriter().print(json);
        }else{
            response.getWriter().print("{'Erro':'Animal com id="+id+" nao localizado'}");
        }
        
    }
    /**
     * Recebe os parametros nome,nomeCientifico,peso e solicita o cadastro do novo animal no banco de dados
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        System.out.println("Post recebido");
        String nome = request.getParameter("nome");
        String nomeCinetifico = request.getParameter("nomecientifico");
        float peso = 0;
        try{
            peso = Float.parseFloat(request.getParameter("peso"));
        }catch(NumberFormatException e){
            response.getWriter().print("{'Erro':'parametro [peso] precisa ser um numero'}");
            return;
        }
        if(nome != null){
            Animal anm = new Animal();
            anm.setNome(nome);
            anm.setNomeCientifico(nomeCinetifico);
            anm.setPeso(peso);
            AnimalDAO animalDAO = new AnimalDAO();
            int id = animalDAO.cadastrar(anm);
            //response.setContentType("application/json");
            if(id>0){
                response.getWriter().print("{'OK':'Animal cadastrado com o id="+id+"'}");
            }else{
                response.getWriter().print("{'Erro':'Não foi possivel cadastrar o animal'}");
            }
        }
        
    }
    /**
     * Recebe os parametros id,nome,nomeCientifico,peso e solicita alteração do anima no banco de dados de acordo com o id
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        int id = 0;
        String nome = request.getParameter("nome");
        String nomecientifico = request.getParameter("nomecientifico");
        float peso = 0;
        try{
            peso = Float.parseFloat(request.getParameter("peso"));
            id = Integer.parseInt(request.getParameter("id"));
        }catch(Exception e){
            e.printStackTrace();
            response.getWriter().print("{'Erro':'parametros [id] e [peso] precisam ser numericos'}");
            return;
        }
        
        Animal animal = new Animal();
        animal.setNome(nome);
        animal.setNomeCientifico(nomecientifico);
        animal.setPeso(peso);
        animal.setId(id);
        
        AnimalDAO animalDAO = new AnimalDAO();
        //response.setContentType("application/json");
        if(animalDAO.alterar(animal)){
            response.getWriter().print("{'OK':'Cadastro alterado'}");
        }else{
            response.getWriter().print("{'Erro':'Animal com id="+id+" nao localizado'}");
        }
    }
    /**
     * Recebe o id e deleta o cadastro com o id correspondente
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        int id = 0;
        try{
            id = Integer.parseInt(request.getParameter("id"));
        }catch(Exception e){
            e.printStackTrace();
            response.getWriter().print("{'Erro':'parametro [id] precisa ser um numero'}");
            return;
        }
        AnimalDAO animalDAO = new AnimalDAO();
        //response.setContentType("application/json");
        if(animalDAO.deletar(id)){
            response.getWriter().print("{'OK':'Animal deletado'}");
        }else{
            response.getWriter().print("{'Erro':'Animal com id="+id+" não localizado'}");
        }
     
        
    }
}
