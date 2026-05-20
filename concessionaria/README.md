# Concessionária API 

Uma API REST desenvolvida em Java com Spring Boot e PostgreSQL para o gerenciamento inteligente de clientes e veículos de uma concessionária. 

Projeto desenvolvido como parte dos requisitos de avaliação do **SERRATEC**.

---

##  Tecnologias Utilizadas

O projeto foi construído utilizando as seguintes tecnologias e dependências:

* **Java 17** 
* **Spring Boot 3.2.5** 
* **Spring Data JPA** 
* **PostgreSQL** 
* **Validation** 
* **Lombok** 
* **Springdoc OpenAPI (Swagger)** 
* **Maven** 


##  Estrutura de Pacotes

O projeto segue rigorosamente as boas práticas de arquitetura em camadas solicitadas na especificação:

```text
org.serratec.concessionaria
├── controller   # Endpoints da API 
├── service      # Regras de negócio e validações
├── repository   # Interfaces de comunicação com o Spring Data JPA
├── entity       # Mapeamento das tabelas do banco de dados 
├── model        # DTOs  para entrada e saída de dados
└── exception    # Exceções customizadas e o Manipulador Global de Erros
```

##  Funcionalidades e Regras de Negócio Implementadas

### Clientes 
* **Cadastrar Cliente:** Validação de campos obrigatórios, formato de e-mail e unicidade de CPF.
    * O sistema limpa automaticamente pontos e traços de CPF e Telefone antes de salvar no banco.
* **Buscar Clientes:** Busca flexível por ID, por CPF ou por Nome.
    * *Formatação de Saída :* Ao listar o cliente, o CPF e o Telefone são devolvidos perfeitamente formatados com pontos, traços e parênteses.
* **Remover Cliente:** Deleção protegida (bloqueia a deleção se o cliente possuir carros associados).

###  Veículos 
* **Cadastrar Veículo:** Validação estrita do estado do veículo:
    * Se `vendido = false`, o sistema impede o cadastro indevido de `clienteId` ou `valorVenda`.
    * Se `vendido = true`, o preenchimento do cliente e do valor da venda tornam-se estritamente obrigatórios.
    * *Inteligência da Placa:* Aceita formatos com ou sem hífen, padroniza em maiúsculas e salva de forma limpa no banco.
* **Buscar Veículos:** Busca por ID ou filtros opcionais por Placa, Marca ou Modelo.
    * *Formatação de Saída:* A placa é devolvida no formato padrão. Se o carro não foi vendido, os campos de venda são omitidos dinamicamente na resposta.
* **Atualizar Veículo:** Permite atualizar os dados estruturais e processar a venda do carro.
    * *Validação de Desconto:* Trava matemática que impede a venda do carro caso o desconto aplicado ultrapasse o `maximoDesconto` permitido.
* **Remover Veículo:** Exclusão completa por ID.

---

## Tratamento de Erros 

A API possui um mecanismo global robusto para capturar erros e devolver retornos HTTP sem expor *stacktraces* internas:

* `400 Bad Request` - Dados inválidos, validações de campos do Bean Validation, ou quebras de regras de negócio (ex: Desconto ultrapassado).
* `404 Not Found` - Recurso (Veículo ou Cliente) não encontrado pelo ID.
* `409 Conflict` - Violação de restrições de banco de dados (CPF ou Placa duplicados, ou tentativa de apagar cliente vinculado a veículo).

---

## Como Executar o Projeto

### Pré-requisitos
1. Possuir o **Java 17** instalado.
2. Possuir o **PostgreSQL** rodando localmente.
3. Criar um banco de dados vazio chamado `concessionaria`.

### Configuração
Verifique as credenciais do banco no arquivo `src/main/resources/application.properties`:

```properties
spring.application.name=concessionaria
spring.datasource.url=jdbc:postgresql://localhost:5432/concessionaria
spring.datasource.username=postgres
spring.datasource.password=1204
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
```
### Execução
Abra o projeto na sua IDE de preferência e execute a classe principal:
`ConcessionariaApplication.java`

A API subirá automaticamente na porta **8080**.

---

##  Documentação da API (Swagger)

Com a aplicação rodando, acesse a interface interativa do Swagger para testar todos os endpoints:

 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)