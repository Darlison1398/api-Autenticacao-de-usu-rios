API de Gerenciamento de Usuários
Esta é uma API desenvolvida com Spring Boot, Spring Security e Auth0, utilizando o banco de dados PostgreSQL. Ela gerencia autenticação, permissões de usuários e recuperação de senha, com funcionalidades específicas para dois tipos de usuários: usuário comum (user) e administrador (admin).

Funcionalidades
Autenticação e Autorização

Gerenciamento de usuários com JWT (JSON Web Token) para autenticação.
Validação de permissões de acesso com base no tipo de usuário (user ou admin).
Permissões

Usuário Comum (user): Permissões limitadas e específicas para ações básicas.
Administrador (admin): Acesso a recursos avançados, incluindo gerenciamento de usuários.
Recuperação de Senha

Solicitação de redefinição de senha.
Envio de um email com um link contendo um token com validade de 1 hora para redefinição segura.
Configuração de Segurança

Integração com Auth0 para simplificar o gerenciamento de autenticação.
Configuração robusta com Spring Security para proteção de endpoints.
Tecnologias Utilizadas
Java
Spring Boot
Spring Security
Auth0
PostgreSQL
Como Executar o Projeto
Pré-requisitos
Java 17+
Maven
PostgreSQL configurado e em execução
Configurar as variáveis de ambiente ou o arquivo application.properties com as informações necessárias:
Conexão com o banco de dados
Chaves e configurações de segurança
