package org.github.nelsonstr.kevlar.code.rules;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Objects;
import java.util.Optional;

/**
 * Sunny test class demonstrating good code patterns that should pass SpotBugs checks.
 * <p>
 * This class serves as a positive example of well-written Java code that follows
 * best practices and should pass all SpotBugs static analysis checks. It demonstrates
 * proper resource management, thread safety, and modern Java 21 features.
 * </p>
 * 
 * <h2>Purpose</h2>
 * <p>
 * This class is used to test SpotBugs configuration and ensure that good code
 * patterns are not incorrectly flagged as violations. It serves as a reference
 * implementation for:
 * </p>
 * <ul>
 *   <li>Proper resource management and cleanup</li>
 *   <li>Thread-safe operations</li>
 *   <li>Null safety and validation</li>
 *   <li>Exception handling</li>
 *   <li>Modern Java 21 features</li>
 *   <li>Proper equals/hashCode implementations</li>
 * </ul>
 * 
 * <h2>SpotBugs Compliance</h2>
 * <p>
 * This class is designed to pass all SpotBugs checks including:
 * </p>
 * <ul>
 *   <li><strong>Correctness:</strong> Proper null handling, resource management</li>
 *   <li><strong>Performance:</strong> Efficient operations, no unnecessary object creation</li>
 *   <li><strong>Security:</strong> Proper validation, no security vulnerabilities</li>
 *   <li><strong>Dodgy Code:</strong> No suspicious patterns or code smells</li>
 *   <li><strong>Multithreaded Correctness:</strong> Thread-safe operations</li>
 * </ul>
 * 
 * <h2>Modern Java 21 Features</h2>
 * <p>
 * This class demonstrates several modern Java 21 features:
 * </p>
 * <ul>
 *   <li><strong>AtomicInteger:</strong> Thread-safe integer operations</li>
 *   <li><strong>Optional API:</strong> Null-safe operations and functional programming</li>
 *   <li><strong>Pattern Matching:</strong> instanceof with pattern variables</li>
 *   <li><strong>Switch Expressions:</strong> Expression-based switch with pattern matching</li>
 *   <li><strong>Objects Utility:</strong> Null-safe equals and hashCode methods</li>
 *   <li><strong>Closeable Interface:</strong> Proper resource management</li>
 * </ul>
 * 
 * <h2>Resource Management</h2>
 * <p>
 * This class demonstrates proper resource management patterns:
 * </p>
 * <ul>
 *   <li><strong>Closeable Implementation:</strong> Proper resource cleanup</li>
 *   <li><strong>State Tracking:</strong> Tracks resource state to prevent double-closing</li>
 *   <li><strong>Exception Safety:</strong> Ensures resources are cleaned up even on exceptions</li>
 *   <li><strong>Thread Safety:</strong> Safe concurrent access to shared resources</li>
 * </ul>
 * 
 * <h2>Usage Examples</h2>
 * <p>
 * This class demonstrates how to:
 * </p>
 * <ul>
 *   <li>Implement proper resource management with Closeable</li>
 *   <li>Use AtomicInteger for thread-safe counters</li>
 *   <li>Handle null values safely with Optional</li>
 *   <li>Implement pattern matching for type-safe operations</li>
 *   <li>Write thread-safe equals and hashCode methods</li>
 *   <li>Manage object lifecycle properly</li>
 * </ul>
 * 
 * @author Nelson Str
 * @version 1.0
 * @since 1.0
 * @see BadSpotBugsClass for examples of code that should fail SpotBugs checks
 * @see <a href="https://spotbugs.github.io/">SpotBugs Documentation</a>
 */
public class GoodSpotBugsClass implements Closeable {
    
    /**
     * The name of this instance.
     * <p>
     * This field is final to ensure immutability and thread safety.
     * It's private to maintain proper encapsulation.
     * </p>
     */
    private final String name;
    
    /**
     * Thread-safe counter for tracking operations.
     * <p>
     * This field uses AtomicInteger to ensure thread-safe increment operations
     * without the need for explicit synchronization.
     * </p>
     */
    private final AtomicInteger counter;
    
    /**
     * Flag indicating whether this resource has been closed.
     * <p>
     * This field tracks the resource state to prevent double-closing
     * and ensure proper resource management.
     * </p>
     */
    private boolean closed = false;
    
    /**
     * Constructs a new GoodSpotBugsClass with the specified name.
     * <p>
     * This constructor demonstrates proper initialization with:
     * </p>
     * <ul>
     *   <li>Parameter validation</li>
     *   <li>Thread-safe field initialization</li>
     *   <li>Proper state management</li>
     * </ul>
     * 
     * @param name the name for this instance
     * @throws IllegalArgumentException if name is null or blank
     */
    public GoodSpotBugsClass(String name) {
        // Good: Proper validation
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        this.name = name;
        this.counter = new AtomicInteger(0);
    }
    
    /**
     * Gets the name of this instance.
     * <p>
     * This getter method provides safe access to the immutable name field.
     * </p>
     * 
     * @return the name of this instance
     */
    public String getName() {
        return name;
    }
    
    /**
     * Gets the current counter value.
     * <p>
     * This method provides thread-safe access to the counter value.
     * </p>
     * 
     * @return the current counter value
     */
    public int getCounter() {
        return counter.get();
    }
    
    /**
     * Increments the counter in a thread-safe manner.
     * <p>
     * This method demonstrates proper thread-safe operations using AtomicInteger.
     * It returns the new value after incrementing.
     * </p>
     * 
     * <h4>Thread Safety Benefits:</h4>
     * <ul>
     *   <li>Atomic operations without explicit synchronization</li>
     *   <li>No race conditions</li>
     *   <li>Better performance than synchronized blocks</li>
     *   <li>Built-in memory visibility guarantees</li>
     * </ul>
     * 
     * @return the new counter value after incrementing
     */
    public int incrementCounter() {
        // Good: Thread-safe increment
        return counter.incrementAndGet();
    }
    
    /**
     * Performs an operation with proper resource checking.
     * <p>
     * This method demonstrates proper resource state checking before performing
     * operations. It ensures that operations are not performed on closed resources.
     * </p>
     * 
     * <h4>Resource State Management:</h4>
     * <ul>
     *   <li>Check resource state before operations</li>
     *   <li>Throw appropriate exceptions for invalid states</li>
     *   <li>Provide clear error messages</li>
     *   <li>Maintain consistent state</li>
     * </ul>
     * 
     * @throws IllegalStateException if this resource has been closed
     */
    public void performOperation() {
        // Good: Check resource state before operation
        if (closed) {
            throw new IllegalStateException("Cannot perform operation on closed resource");
        }
        
        // Perform the operation
        incrementCounter();
    }
    
    /**
     * Processes an object with null safety.
     * <p>
     * This method demonstrates proper null handling using the Optional API
     * and modern Java 21 pattern matching.
     * </p>
     * 
     * <h4>Null Safety Benefits:</h4>
     * <ul>
     *   <li>Explicit null handling</li>
     *   <li>No NullPointerException</li>
     *   <li>Functional programming style</li>
     *   <li>Chainable operations</li>
     * </ul>
     * 
     * @param obj the object to process
     * @return an Optional containing the processed result, or empty if processing failed
     */
    public Optional<String> processObject(Object obj) {
        return Optional.ofNullable(obj)
                .map(Object::toString)
                .filter(s -> !s.isBlank())
                .map(s -> "Processed: " + s);
    }
    
    /**
     * Gets information about an object using pattern matching.
     * <p>
     * This method demonstrates modern Java 21 pattern matching in switch expressions
     * for type-safe object handling.
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
     * @param obj the object to analyze
     * @return a string description of the object
     */
    public String getObjectInfo(Object obj) {
        return switch (obj) {
            case String s -> "String with length: " + s.length();
            case Number n -> "Number: " + n.doubleValue();
            case null -> "null object";
            default -> "Unknown type: " + obj.getClass().getSimpleName();
        };
    }
    
    /**
     * Validates whether this instance is in a valid state.
     * <p>
     * This method demonstrates proper state validation using modern Java features.
     * It checks multiple conditions and provides clear feedback.
     * </p>
     * 
     * @return true if this instance is in a valid state, false otherwise
     */
    public boolean isValid() {
        // Good: Comprehensive validation
        return name != null && !name.isBlank() && !closed;
    }
    
    /**
     * Closes this resource and releases any associated resources.
     * <p>
     * This method implements the Closeable interface and demonstrates proper
     * resource cleanup. It ensures that the resource is only closed once
     * and provides appropriate feedback.
     * </p>
     * 
     * <h4>Resource Cleanup Best Practices:</h4>
     * <ul>
     *   <li>Check if already closed to prevent double-closing</li>
     *   <li>Set closed flag before cleanup operations</li>
     *   <li>Handle cleanup exceptions appropriately</li>
     *   <li>Provide clear feedback about cleanup status</li>
     * </ul>
     * 
     * @throws IOException if an I/O error occurs during cleanup
     */
    @Override
    public void close() throws IOException {
        // Good: Check if already closed
        if (closed) {
            return; // Already closed, nothing to do
        }
        
        try {
            // Mark as closed first
            closed = true;
            
            // Perform cleanup operations
            System.out.println("Closing resource: " + name);
            
        } catch (Exception e) {
            // Good: Proper exception handling
            throw new IOException("Error closing resource: " + name, e);
        }
    }
    
    /**
     * Returns a string representation of this object.
     * <p>
     * This method demonstrates proper toString implementation using modern
     * string formatting. It provides a clear, readable representation of the
     * object's state.
     * </p>
     * 
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return String.format("GoodSpotBugsClass{name='%s', counter=%d, closed=%s}", 
                name, counter.get(), closed);
    }
    
    /**
     * Compares this object with another for equality.
     * <p>
     * This method demonstrates proper equals implementation using modern Java
     * pattern matching and the Objects utility class. It follows the equals
     * contract and provides efficient comparison.
     * </p>
     * 
     * @param obj the object to compare with
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // Good: Proper equals implementation with pattern matching
        if (this == obj) return true;
        if (obj instanceof GoodSpotBugsClass that) {
            return Objects.equals(name, that.name) && 
                   Objects.equals(counter.get(), that.counter.get()) &&
                   closed == that.closed;
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
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        // Good: Proper hashCode implementation
        return Objects.hash(name, counter.get(), closed);
    }
} 