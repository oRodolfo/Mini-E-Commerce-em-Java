# 🛒 Mini E-Commerce em Java (POO)

Projeto final desenvolvido para o curso de **Programação Orientada a Objetos com Java**, com foco em **boas práticas de design**, **regras de negócio**, **testes unitários** e **arquitetura em camadas**.

O sistema simula um **mini e-commerce**, permitindo o gerenciamento de **produtos, clientes e pedidos**, com aplicação de **política de desconto**, **controle de estoque** e **menu interativo via terminal (CLI)**.

---

## 📌 Funcionalidades

### Produtos
- Cadastrar produto
- Listar produtos
- Remover produto
- Controle de estoque

### Clientes
- Cadastrar cliente
- Listar clientes
- Remover cliente
- Validação de e-mail

### Pedidos
- Criar pedido para um cliente
- Adicionar item ao pedido
- Remover item do pedido
- Fechar pedido
- Aplicar política de desconto
- Atualizar estoque automaticamente
- Listar pedidos
- Tratamento completo de exceções

---

## 🧠 Conceitos de POO aplicados

- **Encapsulamento**
- **Herança**
- **Polimorfismo**
- **Interfaces**
- **Composição**
- **Baixo acoplamento**
- **Law of Demeter**
- **Exceções customizadas**
- **Separação de responsabilidades (Model / Repository / Service / CLI)**

---

## 🏗️ Arquitetura do Projeto
``` bash
├── app → Menu CLI (Main)
├── model → Entidades de domínio
├── repository → Repositórios em memória
├── service → Regras de negócio
├── discount → Política de desconto
├── exception → Exceções do domínio
└── test → Testes unitários (JUnit 4)
```
---
## 💰 Política de Desconto

- Pedidos com valor **≥ R$ 200,00**
- Desconto automático de **10%**
- Implementada via **interface**, permitindo fácil extensão

---

## 🧪 Testes Unitários

- Implementados com **JUnit 4**
- Testes focados em **regras de negócio**, incluindo:
  - Cliente inexistente
  - Produto inexistente
  - Estoque insuficiente
  - Pedido vazio
  - Aplicação correta de desconto
  - Baixa de estoque ao fechar pedido

## ▶️ Como Executar o Projeto

Clone o repositório:
``` bash
git clone https://github.com/seu-usuario/mini-ecommerce-java.git
```
- Execute o menu CLI:
``` bash
mvn exec:java

Ou rode a classe Main diretamente pela IDE.
```
---
🚀 Tecnologias Utilizadas

- Java 17

- Maven

- JUnit 4

- IntelliJ IDEA

- Programação Orientada a Objetos

---

## 🎯 Objetivo do Projeto

Consolidar os principais conceitos de POO em Java, simulando um sistema real de mercado, com código limpo, testável e extensível.

---
## 📌 Próximos Passos (evoluções futuras)

- Persistência com banco de dados

- API REST com Spring Boot

- Autenticação de usuários

- Interface gráfica ou Web
