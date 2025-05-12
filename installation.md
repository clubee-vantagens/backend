## 1. Instale o Docker

Antes de tudo, certifique-se de que o Docker está instalado em sua máquina.  
Acesse a documentação oficial para realizar a instalação conforme seu sistema operacional:

[Instalar Docker](https://docs.docker.com/get-docker/)

> Se você utiliza **Windows**, pode acessar diretamente este link:  
> [Instalar Docker no Windows](https://docs.docker.com/desktop/install/windows-install/)

---

## 2. Clone o repositório do GitHub e acesse a branch develop

```bash
git clone https://github.com/clubee-vantagens/backend.git
git checkout develop
```

## 2. Navegue até o diretório do projeto

Utilize o terminal para acessar o diretório onde está localizado o microserviço:

```bash
cd /caminho/para/sua/pasta/backend
```

---

## 3. Escolha o seu ambiente

Utilize o terminal para navegar até o ambiente ao qual você executará:

### Ambiente de homologação (QA)

```bash
cd qa
```

### Ambiente de desenvolvimento (DEV)

```bash
cd dev
```

Depois de acessado a pasta executar o seguinte comando no terminal:

```bash
docker-compose up -d
```

> **OBS.:** Esses comandos irão subir os containers definidos nos arquivos correspondentes, incluindo a API, o banco de dados PostgreSQL e o RabbitMQ configurados para cada ambiente localmente.