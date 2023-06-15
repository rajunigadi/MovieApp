import io.gitlab.arturbosch.detekt.Detekt

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application").version("8.0.1").apply(false)
    id("com.android.library").version ("8.0.1").apply(false)
    id("org.jetbrains.kotlin.android").version ("1.8.20").apply(false) // 1.7.20
    id("com.google.dagger.hilt.android").version("2.44").apply(false)
    id("io.gitlab.arturbosch.detekt").version("1.20.0")
    id("org.jlleitschuh.gradle.ktlint").version("10.2.0")
}

tasks.register("delete", Delete::class) {
    delete(rootProject.buildDir)
}

tasks.register<Detekt>("detektAll") {
    val configFile = file("${rootProject.rootDir}/configs/detekt/default_detekt.yml")
    val reportsPath = file("${rootProject.rootDir}/build/reports/detekt/")
    val kotlinFiles = "**/*.kt"
    val resourceFiles = "**/resources/**"
    val buildFiles = "**/build/**"

    description = "Custom DETEKT build for all modules"
    parallel = true
    ignoreFailures = false
    autoCorrect = false
    buildUponDefaultConfig = false
    setSource(projectDir)
    config.setFrom(configFile)
    include(kotlinFiles)
    exclude(resourceFiles, buildFiles)
    reports {
        html.required.set(true)
        xml.required.set(true)
        txt.required.set(false)
        reportsDir.set(file(reportsPath))
    }
}

tasks.register<Copy>("installGitHooks") {
    //from(file("${rootProject.rootDir}/configs/git-hooks/"))
    //into(file("${rootProject.rootDir}/.git/hooks"))
    fileMode = "0775".toInt(8)
}

tasks.getByPath(":app:preBuild").dependsOn(tasks.getByPath("installGitHooks"))
