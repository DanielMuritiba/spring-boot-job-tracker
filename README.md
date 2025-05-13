# 📦 Job Application Tracker - Backend

Este repositório contém o back-end da aplicação Job Application Tracker, desenvolvido com Spring Boot 3 e Java 17+. Ele é responsável por toda a lógica de negócios, persistência de dados, integração com APIs externas e execução de operações assíncronas.

## 🎯 Objetivo

Fornecer uma API robusta para gerenciar candidatos, vagas de emprego e inscrições, com suporte a leitura de arquivos CSV.

## 🔍 Funcionalidades Principais

- 📥 **Importação de CSV**: Leitura e inserção de dados de candidatos a partir de arquivos CSV, tratados no front-end, com tratamento de erros e duplicações.
- 🔗 **Relacionamentos entre entidades**: Modelagem de entidades `Applicant`, `JobPosting` e `Application`, utilizando `FetchType.LAZY` para evitar o problema N+1.
- ⚡ **Consultas Nativas com JPA**: Uso de native queries para buscar candidatos com melhor correspondência de habilidades.
- 🚀 **DTOs**: Mapeamento completo com DTOs para proteger entidades e facilitar a serialização.
- 🔄 **Operações Transacionais**: Salvamento de candidaturas com controle de atomicidade via `@Transactional`.
- ✅ **CRUD completo para JobVacancy**: Com validação e paginação.
- 🏗️ **Arquitetura Limpa**: Separação em camadas: Controller, Service, Repository.
- ⚠️ **Autenticação com JWT**: Implementação do Userprincipal para gerenciar o User a partir do token de autenticação.

## 🛠️ Tecnologias

- Java 17+
- Spring Boot 3
- Spring Data JPA
- PostgreSQL

