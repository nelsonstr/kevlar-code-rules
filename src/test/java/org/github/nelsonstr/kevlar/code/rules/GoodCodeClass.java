package org.github.nelsonstr.kevlar.code.rules;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

/**
 * Sunny test class demonstrating good code patterns that should pass PMD checks.
 * <p>
 * This class serves as a positive example of well-written Java code that follows
 * best practices and should pass all PMD static analysis checks. It demonstrates
 * modern Java 21 features and proper coding patterns.
 * </p>
 * 
 * <h2>Purpose</h2>
 * <p>
 * This class is used to test PMD (Programming Mistake Detector) configuration
 * and ensure that good code patterns are not incorrectly flagged as violations.
 * It serves as a reference implementation for:
 * </p>
 * <ul>
 *   <li>Proper exception handling</li>
 *   <li>Modern Java 21 features</li>
 *   <li>Clean code practices</li>
 *   <li>Pattern matching</li>
 *   <li>Optional API usage</li>
 *   <li>Proper equals/hashCode implementations</li>
 * </ul>
 * 
 * <h2>PMD Compliance</h2>
 * <p>
 * This class is designed to pass all PMD checks including:
 * </p>
 * <ul>
 *   <li><strong>Best Practices:</strong> Proper exception handling, resource management</li>
 *   <li><strong>Code Style:</strong> Consistent formatting, naming conventions</li>
 *   <li><strong>Design:</strong> Proper encapsulation, method design</li>
 *   <li><strong>Documentation:</strong> Comprehensive JavaDoc</li>
 *   <li><strong>Error Prone:</strong> Null safety, proper validation</li>
 * </ul>
 * 
 * <h2>Modern Java 21 Features</h2>
 * <p>
 * This class demonstrates several modern Java 21 features:
 * </p>
 * <ul>
 *   <li><strong>Optional API:</strong> Null-safe operations and functional programming</li>
 *   <li><strong>Pattern Matching:</strong> instanceof with pattern variables</li>
 *   <li><strong>Switch Expressions:</strong> Expression-based switch with pattern matching</li>
 *   <li><strong>Objects Utility:</strong> Null-safe equals and hashCode methods</li>
 *   <li><strong>String Formatting:</strong> Modern string formatting with String.format()</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * <p>
 * This class demonstrates how to:
 * </p>
 * <ul>
 *   <li>Use Optional for null-safe operations</li>
 *   <li>Implement pattern matching with instanceof</li>
 *   <li>Use switch expressions for type-based logic</li>
 *   <li>Write proper equals and hashCode methods</li>
 *   <li>Handle exceptions correctly</li>
 *   <li>Use modern Java APIs effectively</li>
 * </ul>
 * 
 * @author Nelson Str
 * @version 1.0
 * @since 1.0
 * @see BadCodeClass for examples of code that should fail PMD checks
 * @see <a href="https://pmd.github.io/">PMD Documentation</a>
 */
public class GoodCodeClass {
    
    /**
     * A constant string value used throughout the class.
     * <p>
     * This constant demonstrates proper constant naming conventions and usage.
     * Constants should be static, final, and in UPPER_SNAKE_CASE.
     * </p>
     */
    private static final String CONSTANT = "constant";
    
    /**
     * The instance field that holds the main data for this class.
     * <p>
     * This field is final to ensure immutability and thread safety.
     * It's private to maintain proper encapsulation.
     * </p>
     */
    private final String instanceField;
    
    /**
     * Constructs a new GoodCodeClass with the specified instance field value.
     * <p>
     * This constructor demonstrates proper constructor design with:
     * </p>
     * <ul>
     *   <li>Single responsibility</li>
     *   <li>Clear parameter naming</li>
     *   <li>Proper field initialization</li>
     * </ul>
     * 
     * @param instanceField the value to set for the instance field
     */
    public GoodCodeClass(String instanceField) {
        this.instanceField = instanceField;
    }
    
    /**
     * Gets the instance field value.
     * <p>
     * This getter method follows proper naming conventions and provides
     * controlled access to the private field.
     * </p>
     * 
     * @return the instance field value
     */
    public String getInstanceField() {
        return instanceField;
    }
    
    /**
     * Performs a good method with proper exception handling.
     * <p>
     * This method demonstrates proper exception handling patterns including:
     * </p>
     * <ul>
     *   <li>Try-catch blocks with specific exception types</li>
     *   <li>Proper exception logging</li>
     *   <li>Graceful error handling</li>
     *   <li>No empty catch blocks</li>
     * </ul>
     * 
     * <h4>Exception Handling Best Practices:</h4>
     * <ul>
     *   <li>Always handle exceptions appropriately</li>
     *   <li>Log exceptions with meaningful messages</li>
     *   <li>Don't suppress exceptions without good reason</li>
     *   <li>Use specific exception types when possible</li>
     * </ul>
     */
    public void goodMethod() {
        // Good: Proper exception handling
        try {
            doSomething();
        } catch (Exception e) {
            // Good: Proper logging or handling
            System.err.println("Error occurred: " + e.getMessage());
        }
    }
    
    /**
     * Performs a risky operation that may throw an exception.
     * <p>
     * This method demonstrates proper exception declaration and validation.
     * It throws a specific exception type and includes proper validation.
     * </p>
     * 
     * @throws Exception if the operation fails
     * @throws IllegalArgumentException if the instance field is null
     */
    private void doSomething() throws Exception {
        // Good: Method throws declared exception
        if (instanceField == null) {
            throw new IllegalArgumentException("Field cannot be null");
        }
    }
    
    /**
     * Validates whether the instance field is valid.
     * <p>
     * This method demonstrates modern Java 21 features using the Optional API
     * for null-safe operations. It shows how to chain operations and provide
     * default values.
     * </p>
     * 
     * <h4>Optional API Benefits:</h4>
     * <ul>
     *   <li>Null-safe operations</li>
     *   <li>Functional programming style</li>
     *   <li>Chainable operations</li>
     *   <li>Explicit handling of absence</li>
     * </ul>
     * 
     * @return true if the instance field is valid (not null and not blank), false otherwise
     */
    public boolean isValid() {
        // Good: Clear boolean logic with modern Java features
        return Optional.ofNullable(instanceField)
                .map(String::isBlank)
                .map(blank -> !blank)
                .orElse(false);
    }
    
    /**
     * Processes different types of objects using pattern matching.
     * <p>
     * This method demonstrates modern Java 21 pattern matching in switch expressions.
     * It shows how to handle different object types in a type-safe and readable way.
     * </p>
     * 
     * <h4>Pattern Matching Benefits:</h4>
     * <ul>
     *   <li>Type-safe object handling</li>
     *   <li>Exhaustive pattern matching</li>
     *   <li>Null case handling</li>
     *   <li>Clean, readable code</li>
     * </ul>
     * 
     * @param obj the object to process
     * @return a string description of the object type and content
     */
    public String processObject(Object obj) {
        return switch (obj) {
            case String s -> "String: " + s;
            case Integer i -> "Integer: " + i;
            case List<?> list -> "List with " + list.size() + " elements";
            case null -> "null";
            default -> "Unknown type: " + obj.getClass().getSimpleName();
        };
    }
    
    /**
     * Gets type information about an object using pattern matching.
     * <p>
     * This method demonstrates instanceof pattern matching, which allows you to
     * test the type of an object and extract it to a variable in a single expression.
     * </p>
     * 
     * <h4>Instanceof Pattern Matching Benefits:</h4>
     * <ul>
     *   <li>Combines type checking and variable assignment</li>
     *   <li>Reduces boilerplate code</li>
     *   <li>Improves readability</li>
     *   <li>Prevents ClassCastException</li>
     * </ul>
     * 
     * @param obj the object to analyze
     * @return a string describing the object type and characteristics
     */
    public String getTypeInfo(Object obj) {
        if (obj instanceof String s && s.length() > 10) {
            return "Long string: " + s.substring(0, 10) + "...";
        } else if (obj instanceof Number n) {
            return "Number: " + n.doubleValue();
        }
        return "Other type";
    }
    
    /**
     * Returns a string representation of this object.
     * <p>
     * This method demonstrates proper toString implementation using modern
     * string formatting. It provides a clear, readable representation of the
     * object's state.
     * </p>
     * 
     * <h4>ToString Best Practices:</h4>
     * <ul>
     *   <li>Include all relevant fields</li>
     *   <li>Use consistent formatting</li>
     *   <li>Handle null values appropriately</li>
     *   <li>Use modern string formatting</li>
     * </ul>
     * 
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return String.format("GoodCodeClass{instanceField='%s'}", instanceField);
    }
    
    /**
     * Compares this object with another for equality.
     * <p>
     * This method demonstrates proper equals implementation using modern Java
     * pattern matching and the Objects utility class. It follows the equals
     * contract and provides efficient comparison.
     * </p>
     * 
     * <h4>Equals Contract Requirements:</h4>
     * <ul>
     *   <li>Reflexive: x.equals(x) returns true</li>
     *   <li>Symmetric: x.equals(y) returns same as y.equals(x)</li>
     *   <li>Transitive: if x.equals(y) and y.equals(z), then x.equals(z)</li>
     *   <li>Consistent: multiple calls return same result</li>
     *   <li>Null handling: x.equals(null) returns false</li>
     * </ul>
     * 
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // Good: Proper equals implementation with pattern matching
        if (this == obj) return true;
        if (obj instanceof GoodCodeClass that) {
            return Objects.equals(instanceField, that.instanceField);
        }
        return false;
    }
    
    /**
     * Returns a hash code value for this object.
     * <p>
     * This method demonstrates proper hashCode implementation using the Objects
     * utility class. It ensures consistency with the equals method and provides
     * good hash distribution.
     * </p>
     * 
     * <h4>HashCode Contract Requirements:</h4>
     * <ul>
     *   <li>Consistent: multiple calls return same value</li>
     *   <li>Equals consistency: if x.equals(y), then x.hashCode() == y.hashCode()</li>
     *   <li>Good distribution: minimize hash collisions</li>
     * </ul>
     * 
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        // Good: Proper hashCode implementation
        return Objects.hashCode(instanceField);
    }
} 