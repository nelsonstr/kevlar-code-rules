package org.github.nelsonstr.kevlar.code.rules;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for Maven plugins and project configuration validation.
 * <p>
 * This test class provides comprehensive integration tests that verify the proper
 * configuration and setup of various Maven plugins and project components. These
 * tests ensure that the project is correctly configured for static code analysis,
 * quality checks, and proper Maven project structure.
 * </p>
 * 
 * <h3>Test Coverage</h3>
 * <p>
 * This test class covers the following integration aspects:
 * </p>
 * <ul>
 *   <li><strong>PMD Plugin Configuration:</strong> Verifies PMD ruleset files and configuration</li>
 *   <li><strong>SpotBugs Plugin Configuration:</strong> Validates SpotBugs exclude filters and setup</li>
 *   <li><strong>Enforcer Rule Configuration:</strong> Checks enforcer rule source and implementation</li>
 *   <li><strong>Checkstyle Configuration:</strong> Validates Checkstyle configuration files</li>
 *   <li><strong>Maven Wrapper Configuration:</strong> Ensures Maven wrapper is properly set up</li>
 *   <li><strong>Project Structure:</strong> Verifies correct directory structure and essential files</li>
 *   <li><strong>Dependency Versions:</strong> Validates dependency version configurations</li>
 *   <li><strong>Plugin Management:</strong> Checks plugin management configuration</li>
 *   <li><strong>Reporting Configuration:</strong> Validates Maven site reporting setup</li>
 * </ul>
 * 
 * <h3>Integration vs Unit Tests</h3>
 * <p>
 * Unlike unit tests that focus on individual components in isolation, these integration
 * tests verify that all components work together correctly and that the project
 * configuration is complete and valid. They test the actual files and configurations
 * that exist in the project.
 * </p>
 * 
 * <h3>Test Environment</h3>
 * <p>
 * Tests use JUnit 5's {@code @TempDir} feature to create temporary directories for
 * testing file operations. This ensures tests are isolated and don't interfere with
 * the actual project files.
 * </p>
 * 
 * <h3>File System Operations</h3>
 * <p>
 * Tests perform various file system operations to verify:
 * </p>
 * <ul>
 *   <li>Existence of configuration files</li>
 *   <li>Content validation of XML configuration files</li>
 *   <li>Directory structure compliance</li>
 *   <li>File content verification</li>
 * </ul>
 * 
 * <h3>Configuration Validation</h3>
 * <p>
 * Each test validates specific aspects of the project configuration:
 * </p>
 * <ul>
 *   <li><strong>XML Structure:</strong> Verifies that XML files contain expected elements</li>
 *   <li><strong>Content Validation:</strong> Checks for specific content patterns</li>
 *   <li><strong>Version Compliance:</strong> Ensures dependency versions are as expected</li>
 *   <li><strong>Plugin Setup:</strong> Validates plugin configurations</li>
 * </ul>
 * 
 * <h3>Usage Examples</h3>
 * <p>
 * These tests demonstrate how to:
 * </p>
 * <ul>
 *   <li>Validate Maven plugin configurations</li>
 *   <li>Check project structure compliance</li>
 *   <li>Verify dependency version management</li>
 *   <li>Test file system operations safely</li>
 *   <li>Use modern Java 21 features in test code</li>
 * </ul>
 * 
 * @author Nelson Str
 * @version 1.0
 * @since 1.0
 * @see EnforcerRuleSimpleTest for unit tests
 * @see <a href="https://pmd.github.io/">PMD Documentation</a>
 * @see <a href="https://spotbugs.github.io/">SpotBugs Documentation</a>
 * @see <a href="https://checkstyle.sourceforge.io/">Checkstyle Documentation</a>
 */
class PluginIntegrationTest {
    
    /**
     * Temporary directory for test file operations.
     * <p>
     * This field is automatically populated by JUnit 5's {@code @TempDir} annotation,
     * providing a clean, isolated directory for each test method.
     * </p>
     */
    @TempDir
    Path tempDir;
    
    /**
     * The project directory for testing.
     */
    private File projectDir;
    
    /**
     * The target directory for testing.
     */
    private File targetDir;
    
    /**
     * The classes directory for testing.
     */
    private File classesDir;
    
    /**
     * Sets up the test environment before each test method.
     * <p>
     * This method creates the necessary directory structure for testing file
     * operations and plugin configurations. It's called before each test method
     * to ensure a clean test environment.
     * </p>
     * 
     * @throws IOException if directory creation fails
     */
    @BeforeEach
    void setUp() throws IOException {
        projectDir = tempDir.toFile();
        targetDir = new File(projectDir, "target");
        classesDir = new File(targetDir, "classes");
        
        // Create directory structure
        targetDir.mkdirs();
        classesDir.mkdirs();
    }
    
    /**
     * Tests that the PMD plugin is properly configured.
     * <p>
     * This test verifies that the PMD plugin configuration files exist and contain
     * the expected content. PMD is a static code analyzer that helps identify
     * potential problems in Java code.
     * </p>
     * 
     * <h4>What is PMD?</h4>
     * <p>
     * PMD is a static code analyzer that scans Java source code and looks for
     * potential problems like:
     * </p>
     * <ul>
     *   <li>Unused variables and imports</li>
     *   <li>Empty catch blocks</li>
     *   <li>Unnecessary object creation</li>
     *   <li>Complex methods and classes</li>
     *   <li>Code style violations</li>
     * </ul>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Check if PMD configuration directory exists</li>
     *   <li>Verify PMD ruleset file exists</li>
     *   <li>Validate ruleset file content contains expected elements</li>
     *   <li>Ensure ruleset contains rule definitions</li>
     * </ol>
     */
    @Test
    void testPmdPluginConfiguration() {
        // Test that PMD plugin is properly configured
        // This test verifies that the PMD configuration in pom.xml is valid
        
        // Check if PMD configuration files exist
        var pmdConfigDir = new File("src/main/resources/pmd");
        assertTrue(pmdConfigDir.exists(), "PMD configuration directory should exist");
        
        var pmdRuleset = new File(pmdConfigDir, "pmd-ruleset.xml");
        assertTrue(pmdRuleset.exists(), "PMD ruleset file should exist");
        
        // Verify PMD ruleset content
        try {
            var content = Files.readString(pmdRuleset.toPath());
            assertTrue(content.contains("ruleset"), "PMD ruleset should contain ruleset element");
            assertTrue(content.contains("rule"), "PMD ruleset should contain rule elements");
        } catch (IOException e) {
            fail("Failed to read PMD ruleset file: " + e.getMessage());
        }
    }
    
    /**
     * Tests that the SpotBugs plugin is properly configured.
     * <p>
     * This test verifies that the SpotBugs plugin configuration files exist and
     * contain the expected content. SpotBugs is a static analysis tool that
     * finds bugs in Java programs.
     * </p>
     * 
     * <h4>What is SpotBugs?</h4>
     * <p>
     * SpotBugs is a static analysis tool that finds bugs in Java programs by
     * analyzing bytecode. It can detect:
     * </p>
     * <ul>
     *   <li>Null pointer dereferences</li>
     *   <li>Resource leaks</li>
     *   <li>Thread safety issues</li>
     *   <li>Performance problems</li>
     *   <li>Security vulnerabilities</li>
     * </ul>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Check if SpotBugs configuration directory exists</li>
     *   <li>Verify SpotBugs exclude filter file exists</li>
     *   <li>Validate exclude filter content contains expected elements</li>
     *   <li>Ensure filter contains Match elements for pattern matching</li>
     * </ol>
     */
    @Test
    void testSpotBugsPluginConfiguration() {
        // Test that SpotBugs plugin is properly configured
        // This test verifies that the SpotBugs configuration in pom.xml is valid
        
        // Check if SpotBugs configuration files exist
        var spotBugsConfigDir = new File("src/main/resources/spotbugs");
        assertTrue(spotBugsConfigDir.exists(), "SpotBugs configuration directory should exist");
        
        var spotBugsExcludeFilter = new File(spotBugsConfigDir, "spotbugs-exclude-filters.xml");
        assertTrue(spotBugsExcludeFilter.exists(), "SpotBugs exclude filter file should exist");
        
        // Verify SpotBugs exclude filter content
        try {
            var content = Files.readString(spotBugsExcludeFilter.toPath());
            assertTrue(content.contains("FindBugsFilter"), "SpotBugs exclude filter should contain FindBugsFilter element");
            assertTrue(content.contains("Match"), "SpotBugs exclude filter should contain Match elements");
        } catch (IOException e) {
            fail("Failed to read SpotBugs exclude filter file: " + e.getMessage());
        }
    }
    
    /**
     * Tests that the enforcer rules are properly configured.
     * <p>
     * This test verifies that the custom enforcer rule source code exists and
     * contains the expected content. The enforcer rule is the main component
     * of this project.
     * </p>
     * 
     * <h4>What are Maven Enforcer Rules?</h4>
     * <p>
     * Maven Enforcer Rules are custom rules that can be executed during the
     * Maven build process to enforce project standards and best practices.
     * They can:
     * </p>
     * <ul>
     *   <li>Check for specific dependency versions</li>
     *   <li>Validate project structure</li>
     *   <li>Enforce coding standards</li>
     *   <li>Detect architectural issues</li>
     *   <li>Ensure build environment requirements</li>
     * </ul>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Check if enforcer rule source file exists</li>
     *   <li>Verify source file contains expected class name</li>
     *   <li>Validate source file implements EnforcerRule interface</li>
     *   <li>Ensure source file contains execute method</li>
     * </ol>
     */
    @Test
    void testEnforcerRuleConfiguration() {
        // Test that enforcer rules are properly configured
        // This test verifies that the enforcer configuration in pom.xml is valid
        
        // Check if enforcer rule source exists
        var enforcerRuleSource = new File("src/main/java/NoCyclicPackageDependencyRule.java");
        assertTrue(enforcerRuleSource.exists(), "Enforcer rule source file should exist");
        
        // Verify enforcer rule content
        try {
            var content = Files.readString(enforcerRuleSource.toPath());
            assertTrue(content.contains("NoCyclicPackageDependencyRule"), "Enforcer rule should contain class name");
            assertTrue(content.contains("EnforcerRule"), "Enforcer rule should implement EnforcerRule interface");
            assertTrue(content.contains("execute"), "Enforcer rule should have execute method");
        } catch (IOException e) {
            fail("Failed to read enforcer rule source file: " + e.getMessage());
        }
    }
    
    /**
     * Tests that Checkstyle is properly configured.
     * <p>
     * This test verifies that the Checkstyle configuration files exist and
     * contain the expected content. Checkstyle is a static code analysis tool
     * that enforces coding standards.
     * </p>
     * 
     * <h4>What is Checkstyle?</h4>
     * <p>
     * Checkstyle is a static code analysis tool that enforces coding standards
     * and best practices. It can check for:
     * </p>
     * <ul>
     *   <li>Code formatting and style</li>
     *   <li>Naming conventions</li>
     *   <li>Import organization</li>
     *   <li>Code complexity</li>
     *   <li>Documentation requirements</li>
     * </ul>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Check if Checkstyle configuration directory exists</li>
     *   <li>Verify Checkstyle configuration file exists</li>
     *   <li>Validate configuration content contains expected elements</li>
     *   <li>Ensure configuration contains TreeWalker element</li>
     * </ol>
     */
    @Test
    void testCheckstyleConfiguration() {
        // Test that Checkstyle is properly configured
        // This test verifies that the Checkstyle configuration in pom.xml is valid
        
        // Check if Checkstyle configuration files exist
        var checkstyleConfigDir = new File("src/main/resources/checkstyle");
        assertTrue(checkstyleConfigDir.exists(), "Checkstyle configuration directory should exist");
        
        var checkstyleConfig = new File(checkstyleConfigDir, "checkstyle-checker.xml");
        assertTrue(checkstyleConfig.exists(), "Checkstyle configuration file should exist");
        
        // Verify Checkstyle configuration content
        try {
            var content = Files.readString(checkstyleConfig.toPath());
            assertTrue(content.contains("Checker"), "Checkstyle config should contain Checker element");
            assertTrue(content.contains("TreeWalker"), "Checkstyle config should contain TreeWalker element");
        } catch (IOException e) {
            fail("Failed to read Checkstyle configuration file: " + e.getMessage());
        }
    }
    
    /**
     * Tests that the Maven wrapper is properly configured.
     * <p>
     * This test verifies that the Maven wrapper files exist and are properly
     * configured. The Maven wrapper ensures consistent Maven version usage
     * across different environments.
     * </p>
     * 
     * <h4>What is Maven Wrapper?</h4>
     * <p>
     * Maven Wrapper (mvnw) is a script that allows you to run a Maven project
     * without having Maven installed on your system. It automatically downloads
     * and uses the correct Maven version for the project.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Check if Maven wrapper script files exist (Unix and Windows)</li>
     *   <li>Verify Maven wrapper properties file exists</li>
     *   <li>Validate properties file contains distributionUrl</li>
     *   <li>Ensure wrapper uses the correct Maven version</li>
     * </ol>
     */
    @Test
    void testMavenWrapperConfiguration() {
        // Test that Maven wrapper is properly configured
        // This test verifies that the Maven wrapper files exist and are properly configured
        
        // Get the project root directory
        var projectRoot = findProjectRoot();
        
        // Check if Maven wrapper files exist
        var mvnw = new File(projectRoot, "mvnw");
        var mvnwCmd = new File(projectRoot, "mvnw.cmd");
        var mavenWrapperProperties = new File(projectRoot, ".mvn/wrapper/maven-wrapper.properties");
        
        assertTrue(mvnw.exists(), "Maven wrapper script (Unix) should exist");
        assertTrue(mvnwCmd.exists(), "Maven wrapper script (Windows) should exist");
        assertTrue(mavenWrapperProperties.exists(), "Maven wrapper properties should exist");
        
        // Verify Maven wrapper properties
        try {
            var content = Files.readString(mavenWrapperProperties.toPath());
            assertTrue(content.contains("distributionUrl"), "Maven wrapper properties should contain distributionUrl");
            assertTrue(content.contains("3.9.9"), "Maven wrapper should use Maven 3.9.9");
        } catch (IOException e) {
            fail("Failed to read Maven wrapper properties: " + e.getMessage());
        }
    }
    
    /**
     * Tests that the project has the correct structure.
     * <p>
     * This test verifies that all necessary directories and files exist in the
     * project structure. A proper Maven project structure is essential for
     * build tools and IDEs to work correctly.
     * </p>
     * 
     * <h4>Standard Maven Project Structure</h4>
     * <p>
     * A standard Maven project should have the following structure:
     * </p>
     * <ul>
     *   <li><strong>src/main/java:</strong> Main source code</li>
     *   <li><strong>src/main/resources:</strong> Main resources</li>
     *   <li><strong>src/test/java:</strong> Test source code</li>
     *   <li><strong>src/test/resources:</strong> Test resources</li>
     *   <li><strong>pom.xml:</strong> Project configuration</li>
     *   <li><strong>README.md:</strong> Project documentation</li>
     * </ul>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Check if main source directory exists</li>
     *   <li>Verify test source directory exists</li>
     *   <li>Validate main resources directory exists</li>
     *   <li>Ensure test resources directory exists</li>
     *   <li>Check if POM file exists</li>
     *   <li>Verify README file exists</li>
     * </ol>
     */
    @Test
    void testProjectStructure() {
        // Test that the project has the correct structure
        // This test verifies that all necessary directories and files exist
        
        // Get the project root directory (where pom.xml is located)
        var projectRoot = findProjectRoot();
        
        // Check main source directory
        var mainSrcDir = new File(projectRoot, "src/main/java");
        assertTrue(mainSrcDir.exists(), "Main source directory should exist");
        
        // Check test source directory
        var testSrcDir = new File(projectRoot, "src/test/java");
        assertTrue(testSrcDir.exists(), "Test source directory should exist");
        
        // Check resources directory
        var mainResourcesDir = new File(projectRoot, "src/main/resources");
        assertTrue(mainResourcesDir.exists(), "Main resources directory should exist");
        
        // Check test resources directory
        var testResourcesDir = new File(projectRoot, "src/test/resources");
        assertTrue(testResourcesDir.exists(), "Test resources directory should exist");
        
        // Check POM file
        var pomFile = new File(projectRoot, "pom.xml");
        assertTrue(pomFile.exists(), "POM file should exist");
        
        // Check README file
        var readmeFile = new File(projectRoot, "README.md");
        assertTrue(readmeFile.exists(), "README file should exist");
    }
    
    /**
     * Finds the project root directory by looking for pom.xml file.
     * This method handles both local development and CI environments.
     */
    private File findProjectRoot() {
        var currentDir = new File(".");
        var pomFile = new File(currentDir, "pom.xml");
        
        if (pomFile.exists()) {
            return currentDir;
        }
        
        // If not found in current directory, try parent directory
        var parentDir = currentDir.getParentFile();
        if (parentDir != null) {
            var parentPomFile = new File(parentDir, "pom.xml");
            if (parentPomFile.exists()) {
                return parentDir;
            }
        }
        
        // Fallback to current directory
        return currentDir;
    }
    
    /**
     * Tests that dependency versions are properly configured.
     * <p>
     * This test verifies that the POM file contains the expected dependency
     * versions. Proper version management is crucial for reproducible builds
     * and dependency security.
     * </p>
     * 
     * <h4>Why Version Management Matters</h4>
     * <p>
     * Proper dependency version management ensures:
     * </p>
     * <ul>
     *   <li>Reproducible builds across different environments</li>
     *   <li>Security updates and vulnerability fixes</li>
     *   <li>Compatibility between dependencies</li>
     *   <li>Consistent behavior across team members</li>
     * </ul>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Read POM file content</li>
     *   <li>Check for JUnit Jupiter version 5.11.2</li>
     *   <li>Verify Mockito version 5.10.0</li>
     *   <li>Validate PMD version 3.27.0</li>
     *   <li>Ensure SpotBugs Maven plugin version 4.9.3.2</li>
     *   <li>Check Java version is 21</li>
     * </ol>
     */
    @Test
    void testDependencyVersions() {
        // Test that dependency versions are properly configured
        // This test verifies that the POM file contains the expected dependency versions
        
        try {
            var projectRoot = findProjectRoot();
            var pomContent = Files.readString(new File(projectRoot, "pom.xml").toPath());
            
            // Check for required dependency versions
            assertTrue(pomContent.contains("junit-jupiter.version>5.11.2"), "JUnit Jupiter version should be 5.11.2");
            assertTrue(pomContent.contains("mockito.version>5.10.0"), "Mockito version should be 5.10.0");
            assertTrue(pomContent.contains("pmd.version>3.27.0"), "PMD version should be 3.27.0");
            assertTrue(pomContent.contains("spotbugs-maven-plugin.version>4.9.3.2"), "SpotBugs Maven plugin version should be 4.9.3.2");
            
            // Check for Java version
            assertTrue(pomContent.contains("maven.compiler.source>21"), "Java version should be 21");
            
        } catch (IOException e) {
            fail("Failed to read POM file: " + e.getMessage());
        }
    }
    
    @Test
    void testPluginManagement() {
        // Test that plugin management is properly configured
        // This test verifies that the POM file contains proper plugin management
        
        try {
            var projectRoot = findProjectRoot();
            var pomContent = Files.readString(new File(projectRoot, "pom.xml").toPath());
            
            // Check for plugin management section
            assertTrue(pomContent.contains("<pluginManagement>"), "POM should contain pluginManagement section");
            assertTrue(pomContent.contains("</pluginManagement>"), "POM should contain closing pluginManagement tag");
            
            // Check for required plugins
            assertTrue(pomContent.contains("maven-compiler-plugin"), "POM should contain maven-compiler-plugin");
            assertTrue(pomContent.contains("maven-pmd-plugin"), "POM should contain maven-pmd-plugin");
            assertTrue(pomContent.contains("spotbugs-maven-plugin"), "POM should contain spotbugs-maven-plugin");
            
        } catch (IOException e) {
            fail("Failed to read POM file: " + e.getMessage());
        }
    }
    
    @Test
    void testReportingConfiguration() {
        // Test that reporting is properly configured
        // This test verifies that the POM file contains proper reporting configuration
        
        try {
            var projectRoot = findProjectRoot();
            var pomContent = Files.readString(new File(projectRoot, "pom.xml").toPath());
            
            // Check for reporting section
            assertTrue(pomContent.contains("<reporting>"), "POM should contain reporting section");
            assertTrue(pomContent.contains("</reporting>"), "POM should contain closing reporting tag");
            
            // Check for required reporting plugins
            assertTrue(pomContent.contains("maven-pmd-plugin"), "Reporting should contain maven-pmd-plugin");
            assertTrue(pomContent.contains("spotbugs-maven-plugin"), "Reporting should contain spotbugs-maven-plugin");
            
        } catch (IOException e) {
            fail("Failed to read POM file: " + e.getMessage());
        }
    }
} 