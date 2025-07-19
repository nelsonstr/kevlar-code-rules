package org.github.nelsonstr.kevlar.code.rules;

/**
 * Test classes demonstrating cyclic dependencies for enforcer rule testing.
 * <p>
 * This file contains various classes that demonstrate both good and bad dependency
 * patterns. These classes are used to test the NoCyclicPackageDependencyRule
 * enforcer rule, which detects circular dependencies between packages.
 * </p>
 * 
 * <h3>Purpose</h3>
 * <p>
 * The classes in this file serve as test data for the cyclic dependency detection
 * functionality. They provide concrete examples of:
 * </p>
 * <ul>
 *   <li><strong>Bad Examples:</strong> Classes with circular dependencies that should be detected</li>
 *   <li><strong>Good Examples:</strong> Classes with proper dependency patterns</li>
 *   <li><strong>Modern Java Features:</strong> Demonstrations of Java 21 features</li>
 * </ul>
 * 
 * <h3>Cyclic Dependency Examples</h3>
 * <p>
 * The file contains several examples of cyclic dependencies:
 * </p>
 * <ul>
 *   <li><strong>PackageA → PackageB → PackageC → PackageA:</strong> Simple three-way cycle</li>
 *   <li><strong>InterfaceA ↔ InterfaceB:</strong> Interface-level circular dependency</li>
 *   <li><strong>ImplementationA ↔ ImplementationB:</strong> Implementation-level cycle</li>
 * </ul>
 * 
 * <h3>Good Dependency Examples</h3>
 * <p>
 * The file also contains examples of proper dependency patterns:
 * </p>
 * <ul>
 *   <li><strong>GoodPackageA → GoodPackageB:</strong> Simple linear dependency</li>
 *   <li><strong>ServiceLayer → DataLayer:</strong> Layered architecture pattern</li>
 *   <li><strong>Record Classes:</strong> Modern Java 21 immutable data objects</li>
 * </ul>
 * 
 * <h3>Modern Java 21 Features</h3>
 * <p>
 * This file demonstrates several modern Java 21 features:
 * </p>
 * <ul>
 *   <li><strong>Record Classes:</strong> Immutable data transfer objects</li>
 *   <li><strong>Pattern Matching:</strong> instanceof pattern matching</li>
 *   <li><strong>Switch Expressions:</strong> Enhanced switch with pattern matching</li>
 *   <li><strong>var Declarations:</strong> Local variable type inference</li>
 *   <li><strong>final Fields:</strong> Immutability best practices</li>
 * </ul>
 * 
 * <h3>Usage in Testing</h3>
 * <p>
 * These classes are used by the NoCyclicPackageDependencyRule to:
 * </p>
 * <ul>
 *   <li>Verify that cyclic dependencies are properly detected</li>
 *   <li>Ensure that good dependency patterns are not flagged</li>
 *   <li>Test the rule's ability to handle different types of cycles</li>
 *   <li>Validate the rule's reporting capabilities</li>
 * </ul>
 * 
 * @author Nelson Str
 * @version 1.0
 * @since 1.0
 * @see NoCyclicPackageDependencyRule
 */

// ============================================================================
// BAD EXAMPLES - Classes with Cyclic Dependencies
// ============================================================================

/**
 * Package A that depends on Package B, creating a cycle.
 * <p>
 * This class demonstrates a simple cyclic dependency where PackageA depends on
 * PackageB, which depends on PackageC, which in turn depends back on PackageA.
 * This creates a circular dependency that should be detected by the enforcer rule.
 * </p>
 * 
 * <h4>Dependency Chain:</h4>
 * <p>PackageA → PackageB → PackageC → PackageA</p>
 * 
 * <h4>Why This is Bad:</h4>
 * <ul>
 *   <li>Creates tight coupling between packages</li>
 *   <li>Makes code difficult to understand and maintain</li>
 *   <li>Complicates unit testing and mocking</li>
 *   <li>Prevents proper modularization</li>
 * </ul>
 */
class PackageA {
    private final PackageB b;
    
    public PackageA() {
        this.b = new PackageB();
    }
    
    public void methodA() {
        b.methodB();
    }
}

/**
 * Package B that depends on Package C, continuing the cycle.
 * <p>
 * This class is part of the cyclic dependency chain. It depends on PackageC,
 * which will eventually create a cycle back to PackageA.
 * </p>
 */
class PackageB {
    private final PackageC c;
    
    public PackageB() {
        this.c = new PackageC();
    }
    
    public void methodB() {
        c.methodC();
    }
}

/**
 * Package C that depends on Package A, completing the cycle.
 * <p>
 * This class completes the cyclic dependency by depending on PackageA,
 * creating a circular reference: PackageA → PackageB → PackageC → PackageA.
 * </p>
 */
class PackageC {
    private final PackageA a;
    
    public PackageC() {
        this.a = new PackageA();
    }
    
    public void methodC() {
        a.methodA();
    }
}

// ============================================================================
// GOOD EXAMPLES - Classes with Proper Dependencies
// ============================================================================

/**
 * Good example of Package A with no cyclic dependencies.
 * <p>
 * This class demonstrates a proper dependency pattern where GoodPackageA
 * depends on GoodPackageB, but GoodPackageB has no dependencies back to A.
 * This creates a linear, non-cyclic dependency chain.
 * </p>
 * 
 * <h4>Dependency Chain:</h4>
 * <p>GoodPackageA → GoodPackageB (linear, no cycle)</p>
 * 
 * <h4>Why This is Good:</h4>
 * <ul>
 *   <li>Clear, linear dependency flow</li>
 *   <li>Easy to understand and maintain</li>
 *   <li>Simple to test and mock</li>
 *   <li>Supports proper modularization</li>
 * </ul>
 */
class GoodPackageA {
    private final GoodPackageB b;
    
    public GoodPackageA() {
        this.b = new GoodPackageB();
    }
    
    public void methodA() {
        b.methodB();
    }
}

/**
 * Good example of Package B with no dependencies.
 * <p>
 * This class has no dependencies on other packages, making it a leaf node
 * in the dependency graph. This is an ideal situation for utility classes
 * or data objects.
 * </p>
 */
class GoodPackageB {
    public void methodB() {
        System.out.println("Good package B method");
    }
}

/**
 * Service layer demonstrating layered architecture pattern.
 * <p>
 * This class demonstrates the layered architecture pattern where the service
 * layer depends on the data layer, but not vice versa. This creates a clear
 * separation of concerns and proper dependency direction.
 * </p>
 * 
 * <h4>Architecture Pattern:</h4>
 * <p>ServiceLayer → DataLayer (layered architecture)</p>
 * 
 * <h4>Benefits:</h4>
 * <ul>
 *   <li>Clear separation of concerns</li>
 *   <li>Unidirectional dependencies</li>
 *   <li>Easy to test and maintain</li>
 *   <li>Supports dependency injection</li>
 * </ul>
 */
class ServiceLayer {
    private final DataLayer dataLayer;
    
    public ServiceLayer() {
        this.dataLayer = new DataLayer();
    }
    
    public void processData() {
        dataLayer.getData();
    }
}

/**
 * Data layer with no dependencies on service layer.
 * <p>
 * This class represents the data access layer and has no dependencies on
 * the service layer, maintaining proper layered architecture.
 * </p>
 */
class DataLayer {
    public void getData() {
        System.out.println("Getting data from database");
    }
}

// ============================================================================
// MODERN JAVA 21 FEATURES
// ============================================================================

/**
 * Record class demonstrating modern Java 21 features.
 * <p>
 * This record class demonstrates several modern Java 21 features including:
 * </p>
 * <ul>
 *   <li><strong>Record Classes:</strong> Immutable data transfer objects</li>
 *   <li><strong>Compact Constructor:</strong> Validation in constructor</li>
 *   <li><strong>String.isBlank():</strong> Modern null/empty checking</li>
 *   <li><strong>Automatic Methods:</strong> equals, hashCode, toString</li>
 * </ul>
 * 
 * <h4>Record Benefits:</h4>
 * <ul>
 *   <li>Immutable by design</li>
 *   <li>Automatic getter methods</li>
 *   <li>Automatic equals, hashCode, toString</li>
 *   <li>Compact syntax</li>
 *   <li>Built-in validation</li>
 * </ul>
 * 
 * @param name The name of the data transfer object
 * @param value The numeric value associated with the object
 */
record DataTransferObject(String name, int value) {
    /**
     * Compact constructor with validation.
     * <p>
     * This constructor validates the input parameters before creating the record.
     * It demonstrates modern Java validation patterns.
     * </p>
     * 
     * @throws IllegalArgumentException if name is null, blank, or value is negative
     */
    public DataTransferObject {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        if (value < 0) {
            throw new IllegalArgumentException("Value cannot be negative");
        }
    }
}

// ============================================================================
// INTERFACE-LEVEL CYCLIC DEPENDENCIES
// ============================================================================

/**
 * Interface A that is part of a cyclic dependency.
 * <p>
 * This interface demonstrates a cyclic dependency at the interface level,
 * where InterfaceA and InterfaceB depend on each other.
 * </p>
 */
interface InterfaceA {
    void methodA();
}

/**
 * Interface B that is part of a cyclic dependency.
 * <p>
 * This interface completes the cyclic dependency with InterfaceA.
 * </p>
 */
interface InterfaceB {
    void methodB();
}

/**
 * Implementation of InterfaceA that depends on InterfaceB.
 * <p>
 * This class implements InterfaceA and depends on InterfaceB, creating
 * a cyclic dependency at the implementation level.
 * </p>
 */
class ImplementationA implements InterfaceA {
    private final InterfaceB b;
    
    public ImplementationA(InterfaceB b) {
        this.b = b;
    }
    
    @Override
    public void methodA() {
        b.methodB();
    }
}

/**
 * Implementation of InterfaceB that depends on InterfaceA.
 * <p>
 * This class implements InterfaceB and depends on InterfaceA, completing
 * the cyclic dependency at the implementation level.
 * </p>
 */
class ImplementationB implements InterfaceB {
    private final InterfaceA a;
    
    public ImplementationB(InterfaceA a) {
        this.a = a;
    }
    
    @Override
    public void methodB() {
        a.methodA();
    }
}

// ============================================================================
// UTILITY CLASS FOR TESTING
// ============================================================================

/**
 * Utility class to create cyclic dependencies for testing.
 * <p>
 * This class provides static methods that can be used to create various
 * types of cyclic dependencies for testing the NoCyclicPackageDependencyRule.
 * It also demonstrates modern Java 21 features.
 * </p>
 * 
 * <h3>Testing Methods</h3>
 * <p>
 * The following methods are available for testing:
 * </p>
 * <ul>
 *   <li><strong>createCyclicDependency():</strong> Creates a simple cyclic dependency</li>
 *   <li><strong>createGoodDependency():</strong> Creates a good dependency pattern</li>
 *   <li><strong>createLayeredArchitecture():</strong> Creates layered architecture</li>
 *   <li><strong>createInterfaceCyclicDependency():</strong> Creates interface-level cycle</li>
 *   <li><strong>demonstrateModernFeatures():</strong> Shows Java 21 features</li>
 * </ul>
 * 
 * <h3>Usage in Tests</h3>
 * <p>
 * These methods can be called from test classes to create specific
 * dependency scenarios for testing the enforcer rule.
 * </p>
 */
public class CyclicDependencyTest {
    
    /**
     * Creates a cyclic dependency for testing.
     * <p>
     * This method creates an instance of PackageA, which will trigger the
     * creation of the entire cyclic dependency chain: PackageA → PackageB → PackageC → PackageA.
     * </p>
     * 
     * <h4>Dependency Chain Created:</h4>
     * <p>PackageA → PackageB → PackageC → PackageA</p>
     * 
     * <h4>Usage:</h4>
     * <pre>{@code
     * // This will create a cyclic dependency
     * CyclicDependencyTest.createCyclicDependency();
     * }</pre>
     */
    public static void createCyclicDependency() {
        // This will create a cyclic dependency
        var a = new PackageA();
        a.methodA();
    }
    
    /**
     * Creates a good dependency pattern for testing.
     * <p>
     * This method creates an instance of GoodPackageA, which demonstrates
     * a proper, non-cyclic dependency pattern.
     * </p>
     * 
     * <h4>Dependency Chain Created:</h4>
     * <p>GoodPackageA → GoodPackageB (linear, no cycle)</p>
     * 
     * <h4>Usage:</h4>
     * <pre>{@code
     * // This will create a good dependency chain
     * CyclicDependencyTest.createGoodDependency();
     * }</pre>
     */
    public static void createGoodDependency() {
        // This will create a good dependency chain
        var a = new GoodPackageA();
        a.methodA();
    }
    
    /**
     * Creates a layered architecture for testing.
     * <p>
     * This method creates an instance of ServiceLayer, which demonstrates
     * proper layered architecture with unidirectional dependencies.
     * </p>
     * 
     * <h4>Architecture Created:</h4>
     * <p>ServiceLayer → DataLayer (layered architecture)</p>
     * 
     * <h4>Usage:</h4>
     * <pre>{@code
     * // This will create a layered architecture
     * CyclicDependencyTest.createLayeredArchitecture();
     * }</pre>
     */
    public static void createLayeredArchitecture() {
        // This will create a good layered architecture
        var service = new ServiceLayer();
        service.processData();
    }
    
    /**
     * Creates an interface-level cyclic dependency for testing.
     * <p>
     * This method creates instances of ImplementationA and ImplementationB,
     * which demonstrate a cyclic dependency at the interface level.
     * </p>
     * 
     * <h4>Dependency Chain Created:</h4>
     * <p>ImplementationA ↔ ImplementationB (interface-level cycle)</p>
     * 
     * <h4>Usage:</h4>
     * <pre>{@code
     * // This will create an interface-level cyclic dependency
     * CyclicDependencyTest.createInterfaceCyclicDependency();
     * }</pre>
     */
    public static void createInterfaceCyclicDependency() {
        // This will create a cyclic dependency through interfaces
        var a = new ImplementationA(null);
        var b = new ImplementationB(a);
        // Note: This is just for demonstration, actual instantiation would be more complex
    }
    
    /**
     * Demonstrates modern Java 21 features.
     * <p>
     * This method showcases various modern Java 21 features including:
     * </p>
     * <ul>
     *   <li>Record classes with validation</li>
     *   <li>Pattern matching with instanceof</li>
     *   <li>Enhanced switch expressions</li>
     *   <li>var declarations for type inference</li>
     * </ul>
     * 
     * <h4>Features Demonstrated:</h4>
     * <ul>
     *   <li><strong>Record Classes:</strong> Immutable data objects with validation</li>
     *   <li><strong>Pattern Matching:</strong> instanceof with pattern variables</li>
     *   <li><strong>Switch Expressions:</strong> Expression-based switch with pattern matching</li>
     *   <li><strong>Type Inference:</strong> var declarations for cleaner code</li>
     * </ul>
     * 
     * <h4>Usage:</h4>
     * <pre>{@code
     * // This will demonstrate modern Java 21 features
     * CyclicDependencyTest.demonstrateModernFeatures();
     * }</pre>
     */
    public static void demonstrateModernFeatures() {
        // Demonstrate modern Java 21 features
        var dto = new DataTransferObject("test", 42);
        System.out.println(dto.name() + ": " + dto.value());
        
        // Pattern matching with instanceof
        Object obj = "test";
        if (obj instanceof String s && s.length() > 3) {
            System.out.println("String with length > 3: " + s);
        }
        
        // Enhanced switch expression
        String result = switch (dto.value()) {
            case 0 -> "zero";
            case 42 -> "answer";
            default -> dto.value() > 100 ? "large" : "other";
        };
        System.out.println("Switch result: " + result);
    }
} 