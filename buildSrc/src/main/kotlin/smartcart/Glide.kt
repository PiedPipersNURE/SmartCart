package smartcart

/**
 * Contains the Glide module dependencies for the SmartCart project.
 *
 * This object is used to avoid hard-coding module dependencies in the build scripts,
 * facilitating easier refactoring and maintenance of module dependencies.
 */
object Glide {

    /** Version of the Glide dependency. */
    private const val version = "4.12.0"

    /** Glide dependency. */
    const val glide = "com.github.bumptech.glide:glide:$version"

    /** Glide compiler dependency. */
    const val glideCompiler = "com.github.bumptech.glide:compiler:$version"
}