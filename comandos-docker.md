# Principais comandos

## Como iniciar um container?

...
    docker run NOME-DA IMAGEM

    docker run docker/getting-started
...

## Para listar os containers que estão em execução

...
    docker ps
...

# Se eu quiser para a execução de um container

...
    docker stop NOMEDOCONTAINER
    EX.: docker stop practical_rosalind
...

# Se eu quiser iniciar um container que já existe

...
    docker start practical_rosalind
...

# Para remover um container

Obs.: O container deve estar parado!!!!

...
    docker rm practical_rosalind
...

# eu posso estipular o nome de um container

Obs.: exemplo na criação de um container
...
    docker run --name NOMEDOONTAINERDESEJO NOMEDAIMAGEM
    docker run --name hello-world  docker/getting-started
...

# redirecionar a requisição da máquina hospedeira para um container docker

Obs: exemplo na criação do container


```
    docker run  --name NOMEDOCONTAINERDESEJO -p PORTA-HOSPEDEIRO:PORTA-CONTAINER  NOMEDAIMAGEM
    Ex: docker run --name hello-world -p 80:80  docker/getting-started
```

# Como podemos acessar o terminal de um container?

Obs.: o container deve estar em execução

...
    docker exec -it NOMEDOCONTAINER /bin/bash ou 
    docker exec -it NOMEDOCONTAINER /bin/sh

    /bin/bash  é o comando que vammos executar quando ao entrar no container
    -it --> 'modo interativo'

    Ex.: docker exec -it hello-word /bin/bash

# Comandos Linux
...
    ls   --> listar arquivos e pasta no Linux
    cd   --> entrar dentro de uma pasta
    pwd  --> mostrar em qual diretório nos estamos
    cd .. -> voltarr um nível no sistema de diretórios
    cd / --> voltar para o diretorio raiz
    mkdir -> criando uma pasta 
    touch -> criar um arquivo vazio
...

# dentro do container, iremos criar uma pasta
...
    mkdir pasta1
Para sair do container, digite: exit
...

# Removendo o container para verificar o que acontece com o seu conteúdo
...
    docker stop hello-word
    docker rm hello-word
...

# Subindo um novo container da mesma imagem
...
    docker run --name hello-world -p 80:80 -p 8000:80  docker/getting-started
    docker exec -it hello-world /bin/sh
...

# Como podemos fazer para ao deletar um container, não perdermos dados do mesmo?

Resp: usando o conceito de volume

```
    docker run -v PASTA_DO_HOSPEDEIRO:PASTA_DO_CONTAINER

    docker rm hello-world
    
    docker run --name hello-world -p 80:80 -p 8000:80 -v meu-volume:/meu-volume-container docker/getting-started
```

Branch do Fabrizio
https://github.com/ffborelli/curso-brq-java-2022-09-05