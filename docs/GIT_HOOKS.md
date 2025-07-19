# Git Hooks - Quality Gates

Este documento descreve os Git hooks configurados no projeto para garantir qualidade de código e segurança.

## Hooks Disponíveis

### 1. Pre-Commit Hook (`.git/hooks/pre-commit`)

**Objetivo**: Executa verificações rápidas antes de cada commit para detectar problemas básicos.

**Verificações Executadas**:
- ✅ Compilação do código
- ✅ Análise estática PMD
- ✅ Análise de bugs SpotBugs
- ✅ Verificação de estilo Checkstyle
- ✅ **Verificação de segurança OWASP Dependency Check**

**Tempo Estimado**: 30-60 segundos

**Como Usar**:
```bash
# Commit normal (hook executa automaticamente)
git commit -m "feat: add new feature"

# Pular o hook (emergências)
SKIP_PRE_COMMIT=true git commit -m "hotfix: urgent fix"
```

### 2. Pre-Push Hook (`.git/hooks/pre-push`)

**Objetivo**: Executa verificações completas antes do push para garantir que o código está pronto para o CI/CD.

**Verificações Executadas**:
- ✅ Validação da estrutura do projeto
- ✅ Verificação da versão do Java
- ✅ Limpeza do projeto
- ✅ Testes unitários
- ✅ Testes de integração
- ✅ Análise estática PMD
- ✅ Análise de bugs SpotBugs
- ✅ Verificação de estilo Checkstyle
- ✅ **Verificação de segurança OWASP Dependency Check**
- ✅ Geração de relatórios

**Tempo Estimado**: 2-5 minutos

**Como Usar**:
```bash
# Push normal (hook executa automaticamente)
git push origin main

# Pular o hook (emergências)
SKIP_PRE_PUSH=true git push origin main
```

## Configuração

### Pré-requisitos

1. **Java 21**: O projeto requer Java 21
2. **Maven Wrapper**: Execute `mvn wrapper:wrapper` se não existir
3. **Permissões**: Os hooks devem ser executáveis

### Instalação

Os hooks são instalados automaticamente quando você clona o repositório. Se precisar reinstalar:

```bash
# Tornar hooks executáveis
chmod +x .git/hooks/pre-commit
chmod +x .git/hooks/pre-push
```

### Configuração Global

Para configurar os hooks globalmente para todos os projetos:

```bash
# Configurar diretório global de hooks
git config --global core.hooksPath ~/.git-hooks

# Copiar hooks para o diretório global
mkdir -p ~/.git-hooks
cp .git/hooks/pre-commit ~/.git-hooks/
cp .git/hooks/pre-push ~/.git-hooks/
chmod +x ~/.git-hooks/*
```

## Relatórios Gerados

Os hooks geram relatórios detalhados em:

- **PMD**: `target/pmd/`
- **SpotBugs**: `target/spotbugs/`
- **Checkstyle**: `target/site/checkstyle.html`
- **OWASP Dependency Check**: `target/dependency-check/`
- **JaCoCo**: `target/site/jacoco/`
- **Maven Site**: `target/site/`

## Troubleshooting

### Hook Falha

Se um hook falhar:

1. **Verifique os relatórios**: Acesse os relatórios gerados para entender o problema
2. **Corrija os problemas**: Resolva as violações de qualidade identificadas
3. **Execute manualmente**: Teste o comando Maven diretamente
4. **Pule temporariamente**: Use as variáveis de ambiente para pular o hook

### Problemas Comuns

#### OWASP Dependency Check Falha
```bash
# Verificar vulnerabilidades
mvn org.owasp:dependency-check-maven:8.4.3:check

# Ver relatório detalhado
open target/dependency-check/dependency-check-report.html
```

#### PMD/SpotBugs/Checkstyle Falham
```bash
# Ver relatórios detalhados
mvn pmd:pmd
mvn spotbugs:spotbugs
mvn checkstyle:checkstyle

# Abrir relatórios
open target/pmd/
open target/spotbugs/
open target/site/checkstyle.html
```

### Performance

Se os hooks estiverem muito lentos:

1. **Use cache**: Os hooks usam cache do Maven automaticamente
2. **Pule relatórios**: Os hooks geram relatórios apenas quando necessário
3. **Configure IDE**: Use plugins da IDE para verificações em tempo real

## Integração com IDEs

### IntelliJ IDEA

1. **Hooks automáticos**: Os hooks funcionam automaticamente
2. **Plugins recomendados**:
   - SonarLint
   - SpotBugs
   - CheckStyle-IDEA

### Eclipse

1. **Hooks automáticos**: Os hooks funcionam automaticamente
2. **Plugins recomendados**:
   - PMD Plugin
   - SpotBugs
   - Checkstyle Plugin

### VS Code

1. **Hooks automáticos**: Os hooks funcionam automaticamente
2. **Extensões recomendadas**:
   - SonarLint
   - Java Extension Pack

## Segurança

### OWASP Dependency Check

O hook inclui verificação automática de vulnerabilidades de segurança:

- **CVSS Threshold**: 7.0 (crítico)
- **Formato**: HTML e JSON
- **Escopo**: Runtime e Test
- **Supressões**: Configuradas para dependências transitivas

### Boas Práticas

1. **Nunca pule hooks em produção**: Use apenas para desenvolvimento
2. **Revise supressões**: Monitore supressões de vulnerabilidades
3. **Atualize dependências**: Mantenha dependências atualizadas
4. **Monitore relatórios**: Revise relatórios regularmente

## Contribuição

Para contribuir com melhorias nos hooks:

1. **Teste localmente**: Sempre teste os hooks antes de commitar
2. **Documente mudanças**: Atualize este documento
3. **Mantenha compatibilidade**: Não quebre hooks existentes
4. **Considere performance**: Mantenha hooks rápidos

## Suporte

Para problemas com hooks:

1. **Verifique logs**: Os hooks fornecem logs detalhados
2. **Consulte documentação**: Este documento e relatórios gerados
3. **Abra issue**: Use o GitHub Issues para problemas persistentes 