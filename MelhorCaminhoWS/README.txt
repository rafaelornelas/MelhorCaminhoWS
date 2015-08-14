================================================================================================================================
README
Autor: Rafael Ornelas

Problema: Desenvolver um sistema de entregas, visando o menor custo.
API: Melhor Caminho -  A api desenvolvida serve para popular uma base com dados de mapas e distancias entre locais, a api recebe
ua malha de dados no formato "A B 90" nada mais e que o ponto de origem, destino e a distancia entre os dois pontos.

================================================================================================================================


================================================================================================================================
Requisitos
================================================================================================================================
Java 7
Tomcat 7
Neo4j 2.3 Community

================================================================================================================================
Instalacao
================================================================================================================================
Java7
Baixe a JDK http://www.oracle.com/technetwork/pt/java/javase/downloads/jdk7-downloads-1880260.html
Instale a JDK referente de acordo com sua maquina (OSX,Linux...) 

Neo4j
Baixe a versao community de acordo com sua maquina (OSX,Linux...) http://neo4j.com/download/
Instale o Neo4J 
Va até a pasta bin do Nei4J e execute o comando: neo4j start

Tomcat 7
Baixe a versao 7 no link : https://tomcat.apache.org/download-70.cgi
descompate a pasta no local de preferencia

*Caso se preferir a API pode ser executado no Wildfly na versao 9.0.1 basta fazer o mesmo processo, baixar e descompactar

================================================================================================================================
Rodando a API
================================================================================================================================

Criando um mapa

POST http://localhost:8080/MelhorCaminhoWS/rota/mapa
Accept: application/json
Content-Type: application/json

que ira receber um JSON

{
        "nome": "SP",
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

com um retorno via JSON

 {
        "distancia": 25,
        "custo": 6.25,
        "direcoes": [
            "A",
            "B",
            "D"
        ]
}