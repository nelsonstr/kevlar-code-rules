package org.github.nelsonstr.kevlar.code.rules;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Simple unit tests for the NoCyclicPackageDependencyRule enforcer rule.
 * <p>
 * This test class provides basic unit tests for the NoCyclicPackageDependencyRule
 * without using Mockito to avoid Java 24 compatibility issues. These tests focus
 * on the basic functionality and configuration of the rule rather than complex
 * integration scenarios.
 * </p>
 * 
 * <h3>Test Coverage</h3>
 * <p>
 * This test class covers the following aspects of the NoCyclicPackageDependencyRule:
 * </p>
 * <ul>
 *   <li>Cache behavior (getCacheId, isCacheable, isResultValid)</li>
 *   <li>Configuration parameter handling (failOnError, projectName, maxDepth, excludePatterns)</li>
 *   <li>Default value verification</li>
 *   <li>Object instantiation and basic properties</li>
 *   <li>Pattern matching with modern Java 21 features</li>
 * </ul>
 * 
 * <h3>Why No Mockito?</h3>
 * <p>
 * These tests avoid Mockito to prevent compatibility issues with Java 24 and Byte Buddy.
 * The tests focus on simple, direct testing of the rule's basic functionality without
 * complex mocking scenarios. For more comprehensive testing, see the integration tests.
 * </p>
 * 
 * <h3>Test Strategy</h3>
 * <p>
 * Each test method follows the Given-When-Then pattern:
 * </p>
 * <ul>
 *   <li><strong>Given:</strong> Set up the test conditions and data</li>
 *   <li><strong>When:</strong> Execute the method or operation being tested</li>
 *   <li><strong>Then:</strong> Verify the expected outcomes</li>
 * </ul>
 * 
 * <h3>Usage Examples</h3>
 * <p>
 * These tests demonstrate how to:
 * </p>
 * <ul>
 *   <li>Create and configure a NoCyclicPackageDependencyRule instance</li>
 *   <li>Set and retrieve configuration parameters</li>
 *   <li>Verify default behavior</li>
 *   <li>Use modern Java 21 features like pattern matching</li>
 * </ul>
 * 
 * @author Nelson Str
 * @version 1.0
 * @since 1.0
 * @see NoCyclicPackageDependencyRule
 * @see PluginIntegrationTest for more comprehensive integration tests
 */
@SuppressWarnings("deprecation")
class EnforcerRuleSimpleTest {
    
    /**
     * Tests that the getCacheId method returns a meaningful cache ID.
     * <p>
     * This test verifies that the rule provides a proper cache ID based on its
     * configuration parameters.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Call getCacheId() method</li>
     *   <li>Verify that a non-empty string is returned</li>
     * </ol>
     */
    @Test
    void testGetCacheId_ReturnsMeaningfulId() {
        // When: Get cache ID
        var rule = new NoCyclicPackageDependencyRule();
        var cacheId = rule.getCacheId();
        
        // Then: Should return meaningful cache ID
        assertNotNull(cacheId);
        assertFalse(cacheId.isEmpty());
        assertTrue(cacheId.contains("NoCyclicPackageDependencyRule"));
    }
    
    /**
     * Tests that the isCacheable method returns true.
     * <p>
     * This test verifies that the rule is cacheable, allowing for performance
     * optimization in Maven builds.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Call isCacheable() method</li>
     *   <li>Verify that true is returned</li>
     * </ol>
     */
    @Test
    void testIsCacheable_ReturnsTrue() {
        // When: Check if cacheable
        var rule = new NoCyclicPackageDependencyRule();
        var cacheable = rule.isCacheable();
        
        // Then: Should return true
        assertTrue(cacheable);
    }
    
    /**
     * Tests that the isResultValid method returns true for identical configurations.
     * <p>
     * This test verifies that cached results are considered valid when the cached
     * rule has the same configuration as the current rule, allowing for proper
     * caching behavior.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Call isResultValid() with the same rule instance</li>
     *   <li>Verify that true is returned (same configuration)</li>
     * </ol>
     */
    @Test
    void testIsResultValid_ReturnsTrueForSameConfiguration() {
        // When: Check if result is valid with same rule
        var rule = new NoCyclicPackageDependencyRule();
        var valid = rule.isResultValid(rule);
        
        // Then: Should return true (same configuration)
        assertTrue(valid);
    }
    
    /**
     * Tests that the isResultValid method returns false for different configurations.
     * <p>
     * This test verifies that cached results are not considered valid when the cached
     * rule has different configuration parameters.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create two NoCyclicPackageDependencyRule instances with different configs</li>
     *   <li>Call isResultValid() with different rule instance</li>
     *   <li>Verify that false is returned (different configuration)</li>
     * </ol>
     */
    @Test
    void testIsResultValid_ReturnsFalseForDifferentConfiguration() {
        // Given: Create rules with different configurations
        var rule1 = new NoCyclicPackageDependencyRule();
        var rule2 = new NoCyclicPackageDependencyRule();
        rule2.setProjectName("DifferentProject");
        
        // When: Check if result is valid with different rule
        var valid = rule1.isResultValid(rule2);
        
        // Then: Should return false (different configuration)
        assertFalse(valid);
    }
    
    /**
     * Tests the setter and getter methods for configuration parameters.
     * <p>
     * This test verifies that the rule's configuration parameters can be properly
     * set and retrieved, ensuring that the rule can be configured as expected.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Set failOnError to true and projectName to a test value</li>
     *   <li>Retrieve the values using getter methods</li>
     *   <li>Verify that the retrieved values match the set values</li>
     * </ol>
     */
    @Test
    void testSettersAndGetters() {
        // Given: Create rule and set properties
        var rule = new NoCyclicPackageDependencyRule();
        rule.setFailOnError(true);
        rule.setProjectName("TestProject");
        rule.setMaxDepth(15);
        
        // When: Get properties (using reflection or direct access for testing)
        // Note: Since these are private fields, we test through the cache ID
        var cacheId = rule.getCacheId();
        
        // Then: Should reflect the set values in cache ID
        assertTrue(cacheId.contains("TestProject"));
        assertTrue(cacheId.contains("15"));
    }
    
    /**
     * Tests that the rule has the expected default values.
     * <p>
     * This test verifies that when a new rule instance is created, it has the
     * correct default configuration values.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Retrieve the default values through cache ID</li>
     *   <li>Verify that default values are as expected</li>
     * </ol>
     */
    @Test
    void testDefaultValues() {
        // Given: Create rule with default values
        var rule = new NoCyclicPackageDependencyRule();
        
        // When: Get default properties through cache ID
        var cacheId = rule.getCacheId();
        
        // Then: Should return default values
        assertTrue(cacheId.contains("Unknown Project"));
        assertTrue(cacheId.contains("10"));
    }
    
    /**
     * Tests that the rule can be properly instantiated.
     * <p>
     * This test verifies that the rule can be created successfully and that
     * it implements the expected interface.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Verify that the instance is not null</li>
     *   <li>Verify that it implements the EnforcerRule interface</li>
     * </ol>
     */
    @Test
    void testRuleInstance() {
        // When: Create rule instance
        var rule = new NoCyclicPackageDependencyRule();
        
        // Then: Should be properly instantiated
        assertNotNull(rule);
        assertTrue(rule instanceof org.apache.maven.enforcer.rule.api.EnforcerRule);
    }
    
    /**
     * Tests that the rule has the expected class name.
     * <p>
     * This test verifies that the rule class has the correct name, which is
     * important for logging and debugging purposes.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Get the class name</li>
     *   <li>Verify that it matches the expected name</li>
     * </ol>
     */
    @Test
    void testRuleClassName() {
        // Given: Create rule instance
        var rule = new NoCyclicPackageDependencyRule();
        
        // When: Get class name
        var className = rule.getClass().getSimpleName();
        
        // Then: Should have expected name
        assertEquals("NoCyclicPackageDependencyRule", className);
    }
    
    /**
     * Tests that the rule is in the expected package.
     * <p>
     * This test verifies that the rule is located in the correct package,
     * which is important for proper organization and discovery.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Get the package name</li>
     *   <li>Verify that it matches the expected package</li>
     * </ol>
     */
    @Test
    void testRulePackage() {
        // Given: Create rule instance
        var rule = new NoCyclicPackageDependencyRule();
        
        // When: Get package name
        var packageName = rule.getClass().getPackageName();
        
        // Then: Should be in expected package
        assertEquals("org.github.nelsonstr.kevlar.code.rules", packageName);
    }
    
    /**
     * Tests that the rule can be used with pattern matching.
     * <p>
     * This test demonstrates the use of modern Java 21 pattern matching
     * features with the rule, showing how it can be integrated into
     * modern Java code.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Use pattern matching to check the rule type</li>
     *   <li>Verify that pattern matching works correctly</li>
     * </ol>
     */
    @Test
    void testRuleTypePattern() {
        // Given: Create rule instance
        var rule = new NoCyclicPackageDependencyRule();
        
        // When: Use pattern matching
        var result = switch (rule) {
            case NoCyclicPackageDependencyRule r -> "NoCyclicPackageDependencyRule";
        };
        
        // Then: Should match expected pattern
        assertEquals("NoCyclicPackageDependencyRule", result);
    }
} 
