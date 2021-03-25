# Crud-Animais
O projeto apresenta um crud simples para cadastro de animais com java utilizando o gerenciador de dependencias Maven.

## O projeto
No projeto um susposto problema de um zoológico na hora de guardar as infomações dos animais foi usado como base. A documentação está disponivel na pasta documentação.

## Animal
No projeto foram capturados apenas as seguintes caracteristica dos animais
* nome - que corresponde ao nome pelo qual o animal seria chamado
* nomeCientifico - que corresponde ao nome cientifico do animal
* peso - que corresponde ao peso do animal

## Como usar:
A aplicação se comunica via Http, logo para realizar as 4 operações basta enviar as requisições conforme abaixo:

* Cadastrar - Basta enviar uma requisição do tipo POST com os parametros "nome","nomecientifico" e "peso" para realizar o cadastro que será retornado o "id" com a qual o animal foi cadastrado

* Consultar - Basta enviar uma requisição do tipo GET com o parametro "id" que será retornado um Json com o animal cadastrado nesse id

* Alterar - Basta enviar uma requisição do tipo PUT com os parametros "nome","nomecientifico", "peso" e "id" para alterar que sera retonardo um "ok" caso a alteração seja um sucesso.

* Deletar - Basta enviar uma requisição do tipo DELETE com o parametro "id" que o respectivo animal será deletado do banco.
