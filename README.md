# ClassManager

## Primeira coisa quando começar a trabalhar

Entrar no diretorio do projeto pelo cmd
Vá para a branch principal

- *git checkout main*

Pegue as atualizações do servidor remoto
- *git pull origin main*

Vá para sua branch de trabalho
- *git checkout {nome-da-branch}*

Atualize sua branch com as mudanças da main
- *git merge main*

## Depois de terminar de trabalhar

Veja os arquivos que foram modificados
- *git status*

Adicione os arquivos modificados.
Todos:
- *git add .*
Separadamentes:
- *git add {nome-do-arquivo}*

Faça o commit com uma mensagem descritiva
- *git commit -m "feat: adiciona botão para salvar turma"*

Verifique se sua branch está atualizada com o servidor
- *git fetch origin*

Se houver mudanças, faça:

- *git checkout main*
- *git pull origin main*
- *git checkout {nome-da-branch}*
- *git merge main*

Se houver conflitos, resolva no código. Depois:

- *git add .*
- *git commit -m "Resolvendo conflitos merge"*

Envie suas mudanças para o GitHub
- *git push origin {nome-da-branch}*

Obs: Se for o primeiro push dessa branch, use invés:
- *git push --set-upstream origin dev-Emanuelly*

## Fim

Comandos extras
Ver em qual branch você está e para onde ela aponta
- *git branch -vv*

Ver todas as branches locais e remotas
- *git branch -a*

Ver um histórico visual simplificado
- *git log --oneline --graph --all*
