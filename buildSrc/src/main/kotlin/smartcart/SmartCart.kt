package smartcart

import org.gradle.api.JavaVersion

/**
 * Contains configuration constants for the SmartCart project.
 *
 * This class encapsulates general configuration settings such as the project version,
 * the required Java version for the project, and module dependencies. It is designed
 * to centralize the configuration to make the build scripts cleaner and more maintainable.
 */
@Suppress("unused", "ClassIndependentOfModule")
class SmartCart {
    companion object {
        /** Version of the project. */
        const val VERSION = "1.0"

        /** Java version required for the project. */
        val requiredJavaVersion = JavaVersion.VERSION_11
    }

    /**
     * Contains constants for the module names within the project.
     *
     * This object is used to avoid hard-coding module paths in the build scripts,
     * facilitating easier refactoring and maintenance of module references.
     */
    object Modules {
        const val buildSrc = ":buildSrc"      // The BuildSrc module for custom build logic
        const val app = ":app"                // The Android App module
        const val apiClient = ":apiClient"    // The API Client module
    }
}