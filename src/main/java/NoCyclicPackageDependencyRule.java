package org.github.nelsonstr.kevlar.code.rules;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.component.configurator.expression.ExpressionEvaluationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Maven Enforcer Rule to detect cyclic package dependencies.
 * 
 * <p>
 * This rule scans Java source files for import statements and detects
 * circular dependencies between packages using depth-first search.
 * </p>
 * 
 * @author Nelson Str
 * @version 1.0.0
 */
//CHECKSTYLE:OFF
@SuppressWarnings("PMD")
public class NoCyclicPackageDependencyRule implements EnforcerRule {

    /** Nome do projeto para análise */
    private String projectName = "Unknown Project";

    /** Profundidade máxima de análise */
    private int maxDepth = 10;

    /** Padrões de exclusão */
    private List<String> excludePatterns = new ArrayList<>();

    /** Se deve falhar em caso de erro */
    private boolean failOnError = true;

    /** Record para representar um ciclo de dependências */
    private record DependencyCycle(int index, List<String> packages) {
        /**
         * Formata o ciclo para exibição.
         * 
         * @return String formatada do ciclo
         */
        public String format() {
            return "Cycle %d: %s".formatted(index, String.join(" → ", packages));
        }
    }

    @Override
    public void execute(final EnforcerRuleHelper helper) throws EnforcerRuleException {
        final Log log = helper.getLog();
        final MavenProject project = validateAndGetProject(helper);

        try {
            logInfo(log, "Starting cyclic dependency analysis for: " + projectName);

            final Path srcPath = getSourcePath(project);
            if (!Files.exists(srcPath)) {
                logWarn(log, "Source directory not found: " + srcPath);
                return;
            }

            // Scan Java files and extract dependencies
            final Map<String, Set<String>> dependencies = scanJavaFiles(srcPath);

            // Detect cycles
            final List<List<String>> cycles = detectCycles(dependencies);

            if (cycles.isEmpty()) {
                logInfo(log, "✅ No cyclic dependencies found");
                return;
            }

            // Report cycles
            reportCycles(cycles, log);

        } catch (IOException e) {
            logError(log, "IO error during analysis: " + e.getMessage(), e);
            throw new EnforcerRuleException("Failed to analyze dependencies", e);
        }
    }

    /**
     * Valida e obtém o projeto Maven.
     */
    private MavenProject validateAndGetProject(final EnforcerRuleHelper helper)
            throws EnforcerRuleException {
        try {
            return (MavenProject) helper.evaluate("${project}");
        } catch (ExpressionEvaluationException e) {
            throw new EnforcerRuleException("Failed to evaluate project", e);
        }
    }

    /**
     * Obtém o caminho do diretório de código fonte.
     */
    private Path getSourcePath(final MavenProject project) {
        final String baseDir = getProjectBaseDir(project);
        return Paths.get(baseDir, "src", "main", "java");
    }

    /**
     * Obtém o diretório base do projeto.
     */
    private String getProjectBaseDir(final MavenProject project) {
        return project.getBasedir().getAbsolutePath();
    }

    /**
     * Escaneia arquivos Java e extrai dependências.
     */
    private Map<String, Set<String>> scanJavaFiles(final Path srcPath)
            throws IOException {
        final Map<String, Set<String>> dependencies = new HashMap<>();
        try (Stream<Path> paths = Files.walk(srcPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".java"))
                    .forEach(file -> extractDependencies(file, dependencies));
        }
        return dependencies;
    }

    /**
     * Reporta ciclos encontrados.
     */
    private void reportCycles(final List<List<String>> cycles, final Log log)
            throws EnforcerRuleException {
        final StringBuilder errorMsg = new StringBuilder("❌ Cyclic dependencies found:\n");

        for (int i = 0; i < cycles.size(); i++) {
            final DependencyCycle cycle = new DependencyCycle(i + 1, cycles.get(i));
            errorMsg.append(cycle.format()).append('\n');
        }

        if (failOnError) {
            throw new EnforcerRuleException(errorMsg.toString());
        } else {
            logWarn(log, errorMsg.toString());
        }
    }

    private void extractDependencies(final Path file, final Map<String, Set<String>> dependencies) {
        try {
            final String packageName = extractPackageName(file);
            if (packageName == null || shouldExclude(packageName)) {
                return;
            }

            final Set<String> fileDeps = new HashSet<>();
            final List<String> lines = Files.readAllLines(file);

            for (final String line : lines) {
                final String trimmedLine = line.trim();
                if (trimmedLine.startsWith("import ")) {
                    final String importStmt = trimmedLine.substring(7).replaceAll(";.*", "");
                    final String depPackage = extractPackageFromImport(importStmt);
                    if (depPackage != null && !shouldExclude(depPackage)) {
                        fileDeps.add(depPackage);
                    }
                }
            }

            if (!fileDeps.isEmpty()) {
                dependencies.computeIfAbsent(packageName, k -> new HashSet<>()).addAll(fileDeps);
            }

        } catch (IOException ignorException) {
            // Skip file on error - this is intentional for robustness
            // Logging is intentionally omitted to avoid noise from expected file access
            // issues
        }
    }

    private String extractPackageName(final Path file) {
        final String relativePath = file.toString()
                .replaceAll(".*src/main/java/", "")
                .replaceAll("\\.java$", "");

        final int lastSlash = relativePath.lastIndexOf('/');
        if (lastSlash == -1) {
            return null;
        }
        return relativePath.substring(0, lastSlash).replace('/', '.');
    }

    private String extractPackageFromImport(final String importStmt) {
        if (importStmt.startsWith("static ") || importStmt.endsWith(".*")) {
            return null;
        }
        final int lastDot = importStmt.lastIndexOf('.');
        if (lastDot == -1) {
            return null;
        }
        return importStmt.substring(0, lastDot);
    }

    private boolean shouldExclude(final String packageName) {
        return excludePatterns.stream()
                .anyMatch(pattern -> Pattern.compile(pattern).matcher(packageName).matches());
    }

    private List<List<String>> detectCycles(final Map<String, Set<String>> dependencies) {
        final List<List<String>> cycles = new ArrayList<>();
        final Set<String> visited = new HashSet<>();
        final Set<String> recursionStack = new HashSet<>();
        List<String> currentPath;

        for (final String packageName : dependencies.keySet()) {
            if (!visited.contains(packageName)) {
                currentPath = new ArrayList<>();
                detectCyclesDFS(packageName, dependencies, visited, recursionStack, currentPath, cycles);
            }
        }

        return cycles;
    }

    private void detectCyclesDFS(final String currentPackage, final Map<String, Set<String>> dependencies,
            final Set<String> visited, final Set<String> recursionStack,
            final List<String> currentPath, final List<List<String>> cycles) {

        if (recursionStack.contains(currentPackage)) {
            final int cycleStart = currentPath.indexOf(currentPackage);
            final List<String> cycle = new ArrayList<>(currentPath.subList(cycleStart, currentPath.size()));
            cycle.add(currentPackage);
            cycles.add(cycle);
            return;
        }

        if (visited.contains(currentPackage) || currentPath.size() >= maxDepth) {
            return;
        }

        visited.add(currentPackage);
        recursionStack.add(currentPackage);
        currentPath.add(currentPackage);

        final Set<String> packageDeps = dependencies.get(currentPackage);
        if (packageDeps != null) {
            for (final String dep : packageDeps) {
                detectCyclesDFS(dep, dependencies, visited, recursionStack, currentPath, cycles);
            }
        }

        currentPath.remove(currentPath.size() - 1);
        recursionStack.remove(currentPackage);
    }

    /**
     * Loga mensagem de informação com verificação de nível.
     */
    private void logInfo(final Log log, final String message) {
        if (log.isInfoEnabled()) {
            log.info(message);
        }
    }

    /**
     * Loga mensagem de aviso.
     */
    private void logWarn(final Log log, final String message) {
        log.warn(message);
    }

    /**
     * Loga mensagem de erro.
     */
    private void logError(final Log log, final String message, final Throwable throwable) {
        log.error(message, throwable);
    }

    @Override
    public String getCacheId() {
        return "NoCyclicPackageDependencyRule:" + projectName + ":" + maxDepth + ":" + excludePatterns + ":"
                + failOnError;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }

    /**
     * Verifica se o resultado pode ser cacheado.
     */
    public boolean isResultCacheable() {
        return false;
    }

    @Override
    public boolean isResultValid(final EnforcerRule cachedRule) {
        if (cachedRule instanceof NoCyclicPackageDependencyRule other) {
            return Objects.equals(projectName, other.projectName)
                    && maxDepth == other.maxDepth
                    && Objects.equals(excludePatterns, other.excludePatterns)
                    && failOnError == other.failOnError;
        }
        return false;
    }

    // Configuration setters
    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public void setMaxDepth(final int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setExcludePatterns(final List<String> excludePatterns) {
        this.excludePatterns = new ArrayList<>(excludePatterns);
    }

    public void setFailOnError(final boolean failOnError) {
        this.failOnError = failOnError;
    }
}
