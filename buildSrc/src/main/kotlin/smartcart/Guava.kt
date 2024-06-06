package smartcart

/**
 * Guava is a library that includes several of Google's core libraries that we rely on in our Java code.
 */
object Guava {

    /**
     * The version of Guava that we use.
     */
    private var guavaVersion = "30.1-jre"

    /**
     * The Guava dependency.
     */
    val guavaLib = "com.google.guava:guava:$guavaVersion"
}