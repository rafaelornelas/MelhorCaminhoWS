================================================================================================
README
Autor: Rafael Ornelas

Problema: Desenvolver um sistema de entregas, visando o menor custo.

API: Melhor Caminho - A api desenvolvida e um Webservice com seu metodo POST popula uma base com 
dados de mapas e distancias entre dois locais, a api recebe ua malha de dados no formato "A B 90" 
nada mais e que o ponto de origem, destino e a distancia entre os dois pontos. E com o seu metodo 
GET faz a requisao passando 4 parametros mostrados detalhadamente mais abaixo que retorna um JSON
com as informacoes de melhor rota,custo e o caminho a percorrer.
================================================================================================

================================================================================================
Requisitos
================================================================================================
Java 7
Tomcat 7 ou (Wildfly)
Neo4j 2.3 Community
IDE Eclipse Luna

================================================================================================
Instalacao
================================================================================================
Java7
Baixar: http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk7-downloads-1880260.html
Instale a JDK referente de acordo com sua maquina (OSX,Linux...) 

Neo4j
Baixe a versao community de acordo com sua maquina (OSX,Linux...) http://neo4j.com/download/
Instale o Neo4J 
Va até a pasta bin do Neo4J e execute o comando: neo4j start

Tomcat 7
Baixe a versao 7 no link : https://tomcat.apache.org/download-70.cgi
descompate a pasta no local de preferencia

*Caso se preferir a API pode ser executado no Wildfly na versao 9.0.1 basta fazer o mesmo pro-
cesso, baixar e descompactar

Apos descampactar o Tomcat ou Wildfly voce deve criar o server
importe o projeto
Adcione o projeto ao server
Apos isso Start o appserver

================================================================================================
Rodando a API
================================================================================================

Criando um mapa

POST http://localhost:8080/MelhorCaminhoWS/rota/mapa
Accept: application/json
Content-Type: application/json

Devera ser passado um JSON
{
        "nome": "MG",
        "rotas": [
            {
                "dsitancia": 10,
                "origem": "A",
                "destino": "B"
            }
                ]
}

Buscar o menor caminho

Sera uma requisicao via GET
http://localhost:8080/MelhorCaminhoWS/rota/qualcaminho?origem=A&destino=D&autonomia=10&precoCombustivel=2.5"

O retorno serå um JSON no seguinte formato:
{
        "distancia": 25,
        "custo": 6.25,
        "direcoes": [
            "A",
            "B",
            "D"
        ]
}

================================================================================================
TEstes
================================================================================================
No pacote teste, tem uma case de teste para testar os metodos.

Class: PlanoDeRotaTeste.java

