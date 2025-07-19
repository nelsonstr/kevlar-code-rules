package org.github.nelsonstr.kevlar.code.rules;

import org.github.nelsonstr.maven.enforcer.rule.NoCyclicPackageDependencyRule;
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
 *   <li>Configuration parameter handling (shouldIfail, ignoredArtifactId)</li>
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
class EnforcerRuleSimpleTest {
    
    /**
     * Tests that the getCacheId method returns an empty string.
     * <p>
     * This test verifies that the rule is not cacheable, which is the expected
     * behavior since dependency analysis should always be performed on the current
     * state of compiled classes.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Call getCacheId() method</li>
     *   <li>Verify that an empty string is returned</li>
     * </ol>
     */
    @Test
    void testGetCacheId_ReturnsEmptyString() {
        // When: Get cache ID
        var rule = new NoCyclicPackageDependencyRule();
        var cacheId = rule.getCacheId();
        
        // Then: Should return empty string
        assertEquals("", cacheId);
    }
    
    /**
     * Tests that the isCacheable method returns false.
     * <p>
     * This test verifies that the rule is not cacheable, ensuring that dependency
     * analysis is always performed fresh on each build.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Call isCacheable() method</li>
     *   <li>Verify that false is returned</li>
     * </ol>
     */
    @Test
    void testIsCacheable_ReturnsFalse() {
        // When: Check if cacheable
        var rule = new NoCyclicPackageDependencyRule();
        var cacheable = rule.isCacheable();
        
        // Then: Should return false
        assertFalse(cacheable);
    }
    
    /**
     * Tests that the isResultValid method returns false.
     * <p>
     * This test verifies that cached results are never considered valid, ensuring
     * that the rule always performs fresh analysis.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Call isResultValid() with the same rule instance</li>
     *   <li>Verify that false is returned</li>
     * </ol>
     */
    @Test
    void testIsResultValid_ReturnsFalse() {
        // When: Check if result is valid
        var rule = new NoCyclicPackageDependencyRule();
        var valid = rule.isResultValid(rule);
        
        // Then: Should return false
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
     *   <li>Set shouldIfail to true and ignoredArtifactId to a test value</li>
     *   <li>Retrieve the values using getter methods</li>
     *   <li>Verify that the retrieved values match the set values</li>
     * </ol>
     */
    @Test
    void testSettersAndGetters() {
        // Given: Create rule and set properties
        var rule = new NoCyclicPackageDependencyRule();
        rule.setShouldIfail(true);
        rule.setIgnoredArtifactId("org.test:ignored");
        
        // When: Get properties
        var shouldFail = rule.isShouldIfail();
        var ignoredArtifactId = rule.getIgnoredArtifactId();
        
        // Then: Should return set values
        assertTrue(shouldFail);
        assertEquals("org.test:ignored", ignoredArtifactId);
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
     *   <li>Retrieve the default values for shouldIfail and ignoredArtifactId</li>
     *   <li>Verify that shouldIfail is false and ignoredArtifactId is empty</li>
     * </ol>
     */
    @Test
    void testDefaultValues() {
        // Given: Create rule with default values
        var rule = new NoCyclicPackageDependencyRule();
        
        // When: Get default properties
        var shouldFail = rule.isShouldIfail();
        var ignoredArtifactId = rule.getIgnoredArtifactId();
        
        // Then: Should return default values
        assertFalse(shouldFail);
        assertEquals("", ignoredArtifactId);
    }
    
    /**
     * Tests that the rule can be properly instantiated.
     * <p>
     * This test verifies that the rule can be created successfully and that
     * the instance is of the correct type.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Verify that the instance is not null</li>
     *   <li>Verify that the instance is of the correct type</li>
     * </ol>
     */
    @Test
    void testRuleInstance() {
        // Given: Create rule instance
        var rule = new NoCyclicPackageDependencyRule();
        
        // When & Then: Should not be null and be correct type
        assertNotNull(rule);
        assertTrue(rule instanceof NoCyclicPackageDependencyRule);
    }
    
    /**
     * Tests that the rule has the correct class name.
     * <p>
     * This test verifies that the rule's class name is as expected, which is
     * useful for debugging and reflection-based operations.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Get the simple class name</li>
     *   <li>Verify that the class name is "NoCyclicPackageDependencyRule"</li>
     * </ol>
     */
    @Test
    void testRuleClassName() {
        // Given: Create rule instance
        var rule = new NoCyclicPackageDependencyRule();
        
        // When: Get class name
        var className = rule.getClass().getSimpleName();
        
        // Then: Should have correct class name
        assertEquals("NoCyclicPackageDependencyRule", className);
    }
    
    /**
     * Tests that the rule has the correct package name.
     * <p>
     * This test verifies that the rule is in the expected package, which is
     * important for Maven plugin discovery and configuration.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Get the package name</li>
     *   <li>Verify that the package name is correct</li>
     * </ol>
     */
    @Test
    void testRulePackage() {
        // Given: Create rule instance
        var rule = new NoCyclicPackageDependencyRule();
        
        // When: Get package name
        var packageName = rule.getClass().getPackageName();
        
        // Then: Should have correct package name
        assertEquals("org.github.nelsonstr.maven.enforcer.rule", packageName);
    }
    
    /**
     * Tests pattern matching with the rule instance using modern Java 21 features.
     * <p>
     * This test demonstrates the use of pattern matching in switch expressions,
     * which is a modern Java 21 feature. It verifies that the rule can be
     * properly matched in pattern matching scenarios.
     * </p>
     * 
     * <h4>Test Steps:</h4>
     * <ol>
     *   <li>Create a new NoCyclicPackageDependencyRule instance</li>
     *   <li>Use pattern matching in a switch expression</li>
     *   <li>Verify that the correct pattern is matched</li>
     * </ol>
     * 
     * <h4>Modern Java 21 Features Demonstrated:</h4>
     * <ul>
     *   <li>Pattern matching in switch expressions</li>
     *   <li>var declarations for type inference</li>
     * </ul>
     */
    @Test
    void testRuleTypePattern() {
        // Given: Create rule instance
        var rule = new NoCyclicPackageDependencyRule();
        
        // When & Then: Test pattern matching
        var result = switch (rule) {
            case NoCyclicPackageDependencyRule r -> "correct type";
        };
        
        assertEquals("correct type", result);
    }
} 