# 📺 PUCFlix 1.0

Este projeto é o primeiro trabalho prático da disciplina de AEDS 3 e tem como objetivo implementar um sistema de gerenciamento de séries e episódios com relacionamento 1:N, utilizando estrutura de arquivos e índices com Árvores B+ e Tabela Hash Extensível.

---

## ✅ O que o trabalho faz?

O sistema permite ao usuário cadastrar, buscar, alterar e excluir **séries** e **episódios**. Cada **série** pode ter vários episódios, e cada episódio pertence a uma única série, estabelecendo o relacionamento 1:N.

Além do CRUD básico, o projeto também:

- Garante que uma série não possa ser excluída caso tenha episódios vinculados.
- Organiza e visualiza episódios por temporada.
- Usa Árvores B+ para manter o relacionamento entre séries e episódios.
- Usa Tabela Hash Extensível como índice direto para melhorar a eficiência das buscas.

---

## 👥 Participantes

- **Alice Salim Khouri Antunes** – Entidade Série + Visão
- **Guilherme Henrique da Silva Teodoro** – Entidade Episódio + Visão
- **Daniel Victor Rocha Costa** – Controle de Séries + Relacionamento
- **Arthur Carvalho Rodrigues** – Controle de Episódios + Integração geral e menus

---

## 📦 Estrutura de Classes

### 📁 `model`
- `Serie.java`  
  Representa a entidade série. Contém os atributos: `id`, `nome`, `anoLancamento`, `sinopse`, `streaming`.

- `Episodio.java`  
  Representa a entidade episódio. Atributos: `id`, `idSerie`, `nome`, `temporada`, `dataLancamento`, `duracao`.

### 📁 `view`
- `VisaoSeries.java`  
  Métodos:
  - `Serie leSerie()`: lê dados do usuário.
  - `void mostraSerie(Serie s)`: exibe os dados de uma série.

- `VisaoEpisodios.java`  
  Métodos:
  - `Episodio leEpisodio(int idSerie)`: lê episódio vinculado a uma série.
  - `void mostraEpisodio(Episodio e)`: exibe dados de um episódio.

### 📁 `controller`
- `ControleSeries.java`  
  Controla o menu de séries. Gerencia:
  - Inclusão, alteração, busca e exclusão de séries
  - Verifica existência de episódios antes de excluir
  - Visualização de episódios por temporada

- `ControleEpisodios.java`  
  Controla os episódios por série.
  - Permite a inclusão, alteração, busca e exclusão
  - Garante que a série exista antes de vincular episódio

- `Main.java`  
  Menu principal:
  - 1) Séries
  - 2) Episódios (somente após escolher série)
  - 0) Sair

---

## 🧠 Experiência de Desenvolvimento

O trabalho exigiu atenção ao relacionamento entre as entidades e à forma como os dados são organizados em disco. A implementação da Árvore B+ e da Tabela Hash Extensível foi desafiadora, mas contribuiu para tornar as buscas e o relacionamento entre séries e episódios mais eficientes.

Conseguimos cumprir todos os requisitos propostos, respeitando a organização em camadas (MVC), e utilizando corretamente os arquivos e índices.

---

## 📋 Checklist

- [x] As operações de inclusão, busca, alteração e exclusão de séries estão implementadas e funcionando corretamente?  
- [x] As operações de inclusão, busca, alteração e exclusão de episódios, por série, estão implementadas e funcionando corretamente?  
- [x] Essas operações usam a classe CRUD genérica para a construção do arquivo e as classes Tabela Hash Extensível e Árvore B+ como índices diretos e indiretos?  
- [x] O atributo de ID de série, como chave estrangeira, foi criado na classe de episódios?  
- [x] Há uma árvore B+ que registre o relacionamento 1:N entre episódios e séries?  
- [x] Há uma visualização das séries que mostre os episódios por temporada?  
- [x] A remoção de séries checa se há algum episódio vinculado a ela?  
- [x] A inclusão da série em um episódio se limita às séries existentes?  
- [x] O trabalho está funcionando corretamente?  
- [x] O trabalho está completo?  
- [x] O trabalho é original e não a cópia de um trabalho de outro grupo?

---

## 🔗 Repositório

[🔗 https://github.com/alicesalim/tp1_aeds3.git](https://github.com/alicesalim/tp1_aeds3.git)  

---

> Projeto desenvolvido como parte do TP1 da disciplina de AEDS 3 — PUC Minas.
