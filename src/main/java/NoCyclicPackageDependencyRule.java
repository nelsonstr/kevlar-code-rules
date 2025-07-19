package org.github.nelsonstr.kevlar.code.rules;

import org.apache.maven.enforcer.rule.api.EnforcerRule;
import org.apache.maven.enforcer.rule.api.EnforcerRuleException;
import org.apache.maven.enforcer.rule.api.EnforcerRuleHelper;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Maven Enforcer Rule to detect cyclic package dependencies.
 * 
 * <p>This rule scans Java source files for import statements and detects
 * circular dependencies between packages using depth-first search.</p>
 * 
 * @author Nelson Str
 * @version 1.0.0
 */
public class NoCyclicPackageDependencyRule implements EnforcerRule {

    private String projectName = "Unknown Project";
    private int maxDepth = 10;
    private List<String> excludePatterns = new ArrayList<>();
    private boolean failOnError = true;

    @Override
    public void execute(EnforcerRuleHelper helper) throws EnforcerRuleException {
        Log log = helper.getLog();
        MavenProject project;
        try {
            project = (MavenProject) helper.evaluate("${project}");
        } catch (Exception e) {
            throw new EnforcerRuleException("Failed to evaluate project", e);
        }
        
        try {
            log.info("Starting cyclic dependency analysis for: " + projectName);
            
            Path srcPath = Paths.get(project.getBasedir().getAbsolutePath(), "src", "main", "java");
            if (!Files.exists(srcPath)) {
                log.warn("Source directory not found: " + srcPath);
                return;
            }

            // Scan Java files and extract dependencies
            Map<String, Set<String>> dependencies = new HashMap<>();
            try (var paths = Files.walk(srcPath)) {
                paths.filter(Files::isRegularFile)
                     .filter(path -> path.toString().endsWith(".java"))
                     .forEach(file -> extractDependencies(file, dependencies));
            }

            // Detect cycles
            List<List<String>> cycles = detectCycles(dependencies);
            
            if (cycles.isEmpty()) {
                log.info("✅ No cyclic dependencies found");
                return;
            }

            // Report cycles
            StringBuilder errorMsg = new StringBuilder("❌ Cyclic dependencies found:\n");
            for (int i = 0; i < cycles.size(); i++) {
                errorMsg.append("Cycle ").append(i + 1).append(": ");
                errorMsg.append(String.join(" → ", cycles.get(i))).append("\n");
            }

            if (failOnError) {
                throw new EnforcerRuleException(errorMsg.toString());
            } else {
                log.warn(errorMsg.toString());
            }

        } catch (Exception e) {
            log.error("Analysis failed: " + e.getMessage(), e);
            throw new EnforcerRuleException("Failed to analyze dependencies", e);
        }
    }

    private void extractDependencies(Path file, Map<String, Set<String>> dependencies) {
        try {
            String packageName = extractPackageName(file);
            if (packageName == null || shouldExclude(packageName)) {
                return;
            }

            Set<String> fileDeps = new HashSet<>();
            List<String> lines = Files.readAllLines(file);
            
            for (String line : lines) {
                line = line.trim();
                if (line.startsWith("import ")) {
                    String importStmt = line.substring(7).replaceAll(";.*", "");
                    String depPackage = extractPackageFromImport(importStmt);
                    if (depPackage != null && !shouldExclude(depPackage)) {
                        fileDeps.add(depPackage);
                    }
                }
            }

            if (!fileDeps.isEmpty()) {
                dependencies.computeIfAbsent(packageName, k -> new HashSet<>()).addAll(fileDeps);
            }

        } catch (IOException e) {
            // Skip file on error
        }
    }

    private String extractPackageName(Path file) {
        String relativePath = file.toString()
            .replaceAll(".*src/main/java/", "")
            .replaceAll("\\.java$", "");
        
        int lastSlash = relativePath.lastIndexOf('/');
        return lastSlash == -1 ? null : relativePath.substring(0, lastSlash).replace('/', '.');
    }

    private String extractPackageFromImport(String importStmt) {
        if (importStmt.startsWith("static ") || importStmt.endsWith(".*")) {
            return null;
        }
        int lastDot = importStmt.lastIndexOf('.');
        return lastDot == -1 ? null : importStmt.substring(0, lastDot);
    }

    private boolean shouldExclude(String packageName) {
        return excludePatterns.stream()
            .anyMatch(pattern -> Pattern.compile(pattern).matcher(packageName).matches());
    }

    private List<List<String>> detectCycles(Map<String, Set<String>> dependencies) {
        List<List<String>> cycles = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Set<String> recursionStack = new HashSet<>();

        for (String packageName : dependencies.keySet()) {
            if (!visited.contains(packageName)) {
                List<String> currentPath = new ArrayList<>();
                detectCyclesDFS(packageName, dependencies, visited, recursionStack, currentPath, cycles);
            }
        }

        return cycles;
    }

    private void detectCyclesDFS(String currentPackage, Map<String, Set<String>> dependencies,
                                Set<String> visited, Set<String> recursionStack,
                                List<String> currentPath, List<List<String>> cycles) {
        
        if (recursionStack.contains(currentPackage)) {
            int cycleStart = currentPath.indexOf(currentPackage);
            List<String> cycle = new ArrayList<>(currentPath.subList(cycleStart, currentPath.size()));
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

        Set<String> packageDeps = dependencies.get(currentPackage);
        if (packageDeps != null) {
            for (String dep : packageDeps) {
                detectCyclesDFS(dep, dependencies, visited, recursionStack, currentPath, cycles);
            }
        }

        currentPath.remove(currentPath.size() - 1);
        recursionStack.remove(currentPackage);
    }

    @Override
    public String getCacheId() {
        return "NoCyclicPackageDependencyRule:" + projectName + ":" + maxDepth + ":" + excludePatterns + ":" + failOnError;
    }

    @Override
    public boolean isCacheable() {
        return true;
    }


    public boolean isResultCacheable() {
        return false;
    }

    @Override
    public boolean isResultValid(EnforcerRule cachedRule) {
        if (!(cachedRule instanceof NoCyclicPackageDependencyRule)) {
            return false;
        }
        NoCyclicPackageDependencyRule other = (NoCyclicPackageDependencyRule) cachedRule;
        return Objects.equals(projectName, other.projectName) &&
               maxDepth == other.maxDepth &&
               Objects.equals(excludePatterns, other.excludePatterns) &&
               failOnError == other.failOnError;
    }

    // Configuration setters
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public void setExcludePatterns(List<String> excludePatterns) {
        this.excludePatterns = new ArrayList<>(excludePatterns);
    }

    public void setFailOnError(boolean failOnError) {
        this.failOnError = failOnError;
    }
}

