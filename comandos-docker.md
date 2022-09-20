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

Branch do Fabrizio
https://github.com/ffborelli/curso-brq-java-2022-09-05