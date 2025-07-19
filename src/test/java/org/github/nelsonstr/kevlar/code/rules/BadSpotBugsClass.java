package org.github.nelsonstr.kevlar.code.rules;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rainy test class - Bad code patterns that should fail SpotBugs checks
 */
public class BadSpotBugsClass {
    
    private String name;
    private AtomicInteger counter;
    private boolean closed = false;
    private static final String[] ARRAY = {"a", "b", "c"}; // Bad: Mutable static field
    
    public BadSpotBugsClass(String name) {
        this.name = name;
        this.counter = new AtomicInteger(0);
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int getCounter() {
        return counter.get();
    }
    
    public void incrementCounter() {
        counter.incrementAndGet();
    }
    
    public void badNullCheck() {
        // Bad: Potential NPE
        String str = null;
        if (str.equals("test")) { // Bad: Will throw NPE
            System.out.println("This will never execute");
        }
    }
    
    public void badResourceManagement() {
        // Bad: Resource not properly managed
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("test.txt");
            // Use fis
        } catch (IOException e) {
            // Handle exception
        }
        // Bad: fis not closed in finally block
    }
    
    public void badSynchronization() {
        // Bad: Synchronization on non-final field
        Object lock = new Object();
        synchronized (lock) {
            // Bad: lock can be reassigned
            lock = new Object();
        }
    }
    
    public void badArrayAccess() {
        // Bad: Array access without bounds check
        int[] array = new int[5];
        int value = array[10]; // Bad: ArrayIndexOutOfBoundsException
    }
    
    public void badStringComparison() {
        // Bad: String comparison using == instead of equals
        String str1 = "hello";
        String str2 = "world";
        if (str1 == str2) { // Bad: Should use equals()
            System.out.println("Strings are equal");
        }
    }
    
    public void badExceptionHandling() {
        try {
            doSomethingRisky();
        } catch (Exception e) {
            // Bad: Empty catch block
        }
    }
    
    private void doSomethingRisky() throws Exception {
        if (Math.random() > 0.5) {
            throw new Exception("Random error");
        }
    }
    
    public void badThreadSafety() {
        // Bad: Non-thread-safe operation
        if (!closed) {
            counter.incrementAndGet(); // Bad: Not synchronized
        }
    }
    
    public void badMutableStaticField() {
        // Bad: Modifying static field
        ARRAY[0] = "modified"; // Bad: Modifying static field
    }
    
    public void badReturnOfMutableField() {
        // Bad: Returning reference to mutable field
        return; // This method would return counter if it had a return type
    }
    
    public void badEqualsImplementation() {
        // Bad: Equals implementation without proper null check
        BadSpotBugsClass other = new BadSpotBugsClass("test");
        if (name.equals(other.name)) { // Bad: name could be null
            System.out.println("Names are equal");
        }
    }
    
    public void badHashCodeImplementation() {
        // Bad: HashCode implementation that could be inconsistent
        int hashCode = name.hashCode(); // Bad: name could be null
        System.out.println("HashCode: " + hashCode);
    }
    
    public void badToStringImplementation() {
        // Bad: ToString implementation that could cause NPE
        String result = "BadSpotBugsClass{name='" + name + "'}"; // Bad: name could be null
        System.out.println(result);
    }
    
    public void badCloneImplementation() {
        // Bad: Clone implementation without proper exception handling
        try {
            BadSpotBugsClass clone = (BadSpotBugsClass) super.clone();
            // Bad: Not handling CloneNotSupportedException properly
        } catch (CloneNotSupportedException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badSerialization() {
        // Bad: Serialization without proper exception handling
        try {
            java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
            java.io.ObjectOutputStream oos = new java.io.ObjectOutputStream(baos);
            oos.writeObject(this);
            oos.close();
        } catch (IOException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badDeserialization() {
        // Bad: Deserialization without proper exception handling
        try {
            byte[] data = new byte[0];
            java.io.ByteArrayInputStream bais = new java.io.ByteArrayInputStream(data);
            java.io.ObjectInputStream ois = new java.io.ObjectInputStream(bais);
            Object obj = ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badFileOperation() {
        // Bad: File operation without proper exception handling
        try {
            java.io.File file = new java.io.File("test.txt");
            file.delete();
        } catch (SecurityException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badNetworkOperation() {
        // Bad: Network operation without proper exception handling
        try {
            java.net.URL url = new java.net.URL("http://example.com");
            java.net.URLConnection conn = url.openConnection();
            conn.connect();
        } catch (IOException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badReflection() {
        // Bad: Reflection without proper exception handling
        try {
            Class<?> clazz = Class.forName("java.lang.String");
            Object obj = clazz.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badThreadOperation() {
        // Bad: Thread operation without proper exception handling
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badCollectionOperation() {
        // Bad: Collection operation without proper exception handling
        try {
            java.util.List<String> list = new java.util.ArrayList<>();
            list.add(null); // Bad: Adding null to collection
            String item = list.get(0); // Bad: Could be null
            item.length(); // Bad: Potential NPE
        } catch (Exception e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badMathOperation() {
        // Bad: Math operation without proper exception handling
        try {
            int result = 10 / 0; // Bad: Division by zero
            System.out.println("Result: " + result);
        } catch (ArithmeticException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badDateOperation() {
        // Bad: Date operation without proper exception handling
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("invalid");
            java.util.Date date = sdf.parse("invalid date");
        } catch (java.text.ParseException e) {
            // Bad: Swallowing exception
        }
    }
    
    public void badRegexOperation() {
        // Bad: Regex operation without proper exception handling
        try {
            String pattern = "invalid[regex";
            "test".matches(pattern);
        } catch (java.util.regex.PatternSyntaxException e) {
            // Bad: Swallowing exception
        }
    }
} 