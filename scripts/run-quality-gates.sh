#!/bin/bash

# =============================================================================
# Script para executar Quality Gates manualmente
# =============================================================================
# Este script executa os mesmos goals do GitHub Actions localmente
# para verificar a qualidade do código antes do commit/push.
# =============================================================================

set -e  # Exit on any error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Configuration
MAVEN_CMD="./mvnw"
BATCH_MODE="--batch-mode"
QUIET_MODE="--quiet"

# Function to print colored output
print_status() {
    echo -e "${BLUE}[INFO]${NC} $1"
}

print_success() {
    echo -e "${GREEN}[SUCCESS]${NC} $1"
}

print_warning() {
    echo -e "${YELLOW}[WARNING]${NC} $1"
}

print_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to check if we're in the right directory
check_project_structure() {
    if [ ! -f "pom.xml" ]; then
        print_error "pom.xml não encontrado. Execute este script a partir do diretório raiz do projeto."
        exit 1
    fi
    
    if [ ! -f "mvnw" ]; then
        print_error "Maven wrapper (mvnw) não encontrado. Execute 'mvn wrapper:wrapper' para criar."
        exit 1
    fi
    
    print_success "Estrutura do projeto validada"
}

# Function to check Java version
check_java_version() {
    local java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$java_version" != "21" ]; then
        print_warning "Java 21 é recomendado. Versão atual: Java $java_version"
        read -p "Continuar mesmo assim? (y/N): " -n 1 -r
        echo
        if [[ ! $REPLY =~ ^[Yy]$ ]]; then
            exit 1
        fi
    fi
    print_success "Java 21 detectado"
}

# Function to run Maven goals
run_maven_goal() {
    local goal="$1"
    local description="$2"
    
    print_status "Executando: $description"
    if $MAVEN_CMD $goal $BATCH_MODE $QUIET_MODE; then
        print_success "$description concluído"
    else
        print_error "$description falhou"
        return 1
    fi
}

# Function to run quality checks
run_quality_checks() {
    print_status "Iniciando verificações de qualidade..."
    
    # PMD Check
    if ! run_maven_goal "pmd:check" "Análise estática PMD"; then
        print_error "PMD encontrou problemas de qualidade de código"
        print_status "Execute 'mvn pmd:pmd' para ver o relatório detalhado"
        return 1
    fi
    
    # SpotBugs Check
    if ! run_maven_goal "spotbugs:check" "Análise de bugs SpotBugs"; then
        print_error "SpotBugs encontrou possíveis bugs"
        print_status "Execute 'mvn spotbugs:spotbugs' para ver o relatório detalhado"
        return 1
    fi
    
    # Checkstyle Check
    if ! run_maven_goal "checkstyle:check" "Verificação de estilo Checkstyle"; then
        print_error "Checkstyle encontrou violações de estilo"
        print_status "Execute 'mvn checkstyle:checkstyle' para ver o relatório detalhado"
        return 1
    fi
    
    print_success "Todas as verificações de qualidade passaram"
}

# Function to run tests
run_tests() {
    print_status "Executando testes..."
    
    # Unit tests
    if ! run_maven_goal "test" "Testes unitários"; then
        print_error "Testes unitários falharam"
        return 1
    fi
    
    # Integration tests
    if ! run_maven_goal "verify" "Testes de integração"; then
        print_error "Testes de integração falharam"
        return 1
    fi
    
    print_success "Todos os testes passaram"
}

# Function to generate reports
generate_reports() {
    print_status "Gerando relatórios..."
    
    # JaCoCo report
    if ! run_maven_goal "jacoco:report" "Relatório de cobertura JaCoCo"; then
        print_warning "Falha ao gerar relatório JaCoCo"
    fi
    
    print_success "Relatórios gerados"
}

# Function to show summary
show_summary() {
    echo
    echo "=============================================================================="
    echo "🎉 QUALITY GATES CONCLUÍDOS COM SUCESSO!"
    echo "=============================================================================="
    echo "✅ Estrutura do projeto validada"
    echo "✅ Java 21 verificado"
    echo "✅ Testes unitários passaram"
    echo "✅ Testes de integração passaram"
    echo "✅ Análise PMD passou"
    echo "✅ Análise SpotBugs passou"
    echo "✅ Verificação Checkstyle passou"
    echo "✅ Relatórios gerados"
    echo
    echo "📊 Relatórios disponíveis em:"
    echo "   - PMD: target/pmd/"
    echo "   - SpotBugs: target/spotbugs/"
    echo "   - Checkstyle: target/site/checkstyle.html"
    echo "   - JaCoCo: target/site/jacoco/"
    echo "   - Maven Site: target/site/"
    echo
    echo "🚀 Código pronto para commit/push!"
    echo "=============================================================================="
}

# Function to show usage
show_usage() {
    echo "Uso: $0 [OPÇÕES]"
    echo
    echo "OPÇÕES:"
    echo "  --help, -h          Mostra esta ajuda"
    echo "  --skip-tests        Pula os testes (apenas quality checks)"
    echo "  --skip-quality      Pula os quality checks (apenas testes)"
    echo "  --skip-reports      Pula a geração de relatórios"
    echo "  --verbose, -v       Modo verboso (mostra mais detalhes)"
    echo
    echo "EXEMPLOS:"
    echo "  $0                  # Executa todos os checks"
    echo "  $0 --skip-tests     # Apenas quality checks"
    echo "  $0 --skip-quality   # Apenas testes"
    echo "  $0 --verbose        # Modo verboso"
}

# Parse command line arguments
SKIP_TESTS=false
SKIP_QUALITY=false
SKIP_REPORTS=false
VERBOSE=false

while [[ $# -gt 0 ]]; do
    case $1 in
        --help|-h)
            show_usage
            exit 0
            ;;
        --skip-tests)
            SKIP_TESTS=true
            shift
            ;;
        --skip-quality)
            SKIP_QUALITY=true
            shift
            ;;
        --skip-reports)
            SKIP_REPORTS=true
            shift
            ;;
        --verbose|-v)
            VERBOSE=true
            QUIET_MODE=""
            shift
            ;;
        *)
            print_error "Opção desconhecida: $1"
            show_usage
            exit 1
            ;;
    esac
done

# Main execution
main() {
    echo "=============================================================================="
    echo "🔍 MAVEN QUALITY GATES - EXECUÇÃO MANUAL"
    echo "=============================================================================="
    echo "Executando verificações de qualidade..."
    echo
    
    # Validate project structure
    check_project_structure
    
    # Check Java version
    check_java_version
    
    # Run the complete pipeline (same as GitHub Actions)
    if ! run_maven_goal "clean" "Limpeza do projeto"; then
        exit 1
    fi
    
    # Run tests (unless skipped)
    if [ "$SKIP_TESTS" = false ]; then
        if ! run_tests; then
            exit 1
        fi
    else
        print_warning "Testes ignorados (--skip-tests)"
    fi
    
    # Run quality checks (unless skipped)
    if [ "$SKIP_QUALITY" = false ]; then
        if ! run_quality_checks; then
            exit 1
        fi
    else
        print_warning "Quality checks ignorados (--skip-quality)"
    fi
    
    # Generate reports (unless skipped)
    if [ "$SKIP_REPORTS" = false ]; then
        if ! generate_reports; then
            print_warning "Alguns relatórios falharam, mas continuando..."
        fi
    else
        print_warning "Relatórios ignorados (--skip-reports)"
    fi
    
    # Show success summary
    show_summary
}

# Handle script interruption
trap 'print_error "Script interrompido pelo usuário"; exit 1' INT TERM

# Run main function
main "$@"
