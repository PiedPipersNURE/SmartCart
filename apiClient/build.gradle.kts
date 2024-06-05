import smartcart.Guava

plugins {
    id("java-library")
}

dependencies{
    implementation(Guava.guavaLib)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}