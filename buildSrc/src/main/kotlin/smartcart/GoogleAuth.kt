package smartcart

/**
 * Contains the GoogleAuth module dependencies for the SmartCart project.
 *
 * This object is used to avoid hard-coding module dependencies in the build scripts,
 * facilitating easier refactoring and maintenance of module dependencies.
 */
object GoogleAuth {

    /** Version of the Google Auth dependency. */
    private const val version = "21.2.0"

    /** Google Auth dependency. */
    const val auth = "com.google.android.gms:play-services-auth:$version"
}