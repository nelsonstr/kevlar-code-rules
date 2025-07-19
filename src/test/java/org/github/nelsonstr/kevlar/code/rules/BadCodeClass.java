package org.github.nelsonstr.kevlar.code.rules;

/**
 * Rainy test class - Bad code patterns that should fail PMD checks
 */
public class BadCodeClass {
    
    private String badField; // Bad: No access modifier
    public static String publicStaticField; // Bad: Public static field
    
    public BadCodeClass() {
        // Bad: Empty constructor
    }
    
    public void badMethod() {
        // Bad: Empty method
    }
    
    public void unusedMethod() {
        // Bad: Unused method
        String unused = "unused";
        int unusedInt = 42;
    }
    
    public void badExceptionHandling() {
        try {
            doSomethingBad();
        } catch (Exception e) {
            // Bad: Empty catch block
        }
    }
    
    private void doSomethingBad() throws Exception {
        // Bad: Throws generic Exception
        throw new Exception("Bad exception");
    }
    
    public void badLoop() {
        // Bad: Infinite loop potential
        for (int i = 0; i < 10; i++) {
            if (i == 5) {
                continue; // Bad: Unnecessary continue
            }
            System.out.println(i);
        }
    }
    
    public void badVariableNaming() {
        // Bad: Poor variable naming
        String a = "bad";
        int b = 1;
        String c = "worse";
    }
    
    public void badStringConcatenation() {
        // Bad: String concatenation in loop
        String result = "";
        for (int i = 0; i < 100; i++) {
            result += "bad"; // Bad: Should use StringBuilder
        }
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
        java.io.FileInputStream fis = null;
        try {
            fis = new java.io.FileInputStream("test.txt");
            // Use fis
        } catch (Exception e) {
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
    
    public void badBooleanLogic() {
        // Bad: Redundant boolean logic
        boolean flag = true;
        if (flag == true) { // Bad: Should be just 'if (flag)'
            System.out.println("Redundant");
        }
    }
    
    public void badMethodLength() {
        // Bad: Method too long
        System.out.println("Line 1");
        System.out.println("Line 2");
        System.out.println("Line 3");
        System.out.println("Line 4");
        System.out.println("Line 5");
        System.out.println("Line 6");
        System.out.println("Line 7");
        System.out.println("Line 8");
        System.out.println("Line 9");
        System.out.println("Line 10");
        System.out.println("Line 11");
        System.out.println("Line 12");
        System.out.println("Line 13");
        System.out.println("Line 14");
        System.out.println("Line 15");
        System.out.println("Line 16");
        System.out.println("Line 17");
        System.out.println("Line 18");
        System.out.println("Line 19");
        System.out.println("Line 20");
        System.out.println("Line 21");
        System.out.println("Line 22");
        System.out.println("Line 23");
        System.out.println("Line 24");
        System.out.println("Line 25");
        System.out.println("Line 26");
        System.out.println("Line 27");
        System.out.println("Line 28");
        System.out.println("Line 29");
        System.out.println("Line 30");
        System.out.println("Line 31");
        System.out.println("Line 32");
        System.out.println("Line 33");
        System.out.println("Line 34");
        System.out.println("Line 35");
        System.out.println("Line 36");
        System.out.println("Line 37");
        System.out.println("Line 38");
        System.out.println("Line 39");
        System.out.println("Line 40");
        System.out.println("Line 41");
        System.out.println("Line 42");
        System.out.println("Line 43");
        System.out.println("Line 44");
        System.out.println("Line 45");
        System.out.println("Line 46");
        System.out.println("Line 47");
        System.out.println("Line 48");
        System.out.println("Line 49");
        System.out.println("Line 50");
        System.out.println("Line 51");
        System.out.println("Line 52");
        System.out.println("Line 53");
        System.out.println("Line 54");
        System.out.println("Line 55");
        System.out.println("Line 56");
        System.out.println("Line 57");
        System.out.println("Line 58");
        System.out.println("Line 59");
        System.out.println("Line 60");
        System.out.println("Line 61");
        System.out.println("Line 62");
        System.out.println("Line 63");
        System.out.println("Line 64");
        System.out.println("Line 65");
        System.out.println("Line 66");
        System.out.println("Line 67");
        System.out.println("Line 68");
        System.out.println("Line 69");
        System.out.println("Line 70");
        System.out.println("Line 71");
        System.out.println("Line 72");
        System.out.println("Line 73");
        System.out.println("Line 74");
        System.out.println("Line 75");
        System.out.println("Line 76");
        System.out.println("Line 77");
        System.out.println("Line 78");
        System.out.println("Line 79");
        System.out.println("Line 80");
        System.out.println("Line 81");
        System.out.println("Line 82");
        System.out.println("Line 83");
        System.out.println("Line 84");
        System.out.println("Line 85");
        System.out.println("Line 86");
        System.out.println("Line 87");
        System.out.println("Line 88");
        System.out.println("Line 89");
        System.out.println("Line 90");
        System.out.println("Line 91");
        System.out.println("Line 92");
        System.out.println("Line 93");
        System.out.println("Line 94");
        System.out.println("Line 95");
        System.out.println("Line 96");
        System.out.println("Line 97");
        System.out.println("Line 98");
        System.out.println("Line 99");
        System.out.println("Line 100");
    }
} 