# Developer Setup & Building Guide

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## 🛠️ Toolchain Prerequisites

To compile Combat Penalty Remover from source, verify that your development environment meets the following specifications:

* **Java Development Kit**: **JDK 25** (HotSpot, Eclipse Temurin, or Microsoft OpenJDK 25)
* **Gradle**: Version **9.3+** (or use the included `./gradlew` wrapper)
* **Fabric Loom**: Version **1.11+**
* **DasikLibrary**: Required dependency (`>=1.8.5` for 26.2, `>=1.8.36` for 26.3)

---

## 🚀 Cloning & Workspace Setup

1. Clone the repository recursively:
```bash
git clone https://github.com/Rifaditya/Instant-Gratification-Combat-Penalty-Remover.git
cd Instant-Gratification-Combat-Penalty-Remover
```

2. Point your `JAVA_HOME` to JDK 25 or verify `gradle.properties`:
```properties
org.gradle.parallel=true
org.gradle.java.home=E:/JDK25
```

3. Generate Loom ide project files:
```bash
./gradlew genSources
```

---

## 🔨 Building & Verification

### Standard Build
Compile the production remapped JAR:
```bash
./gradlew build --no-daemon
```
The compiled output is placed in:
`build/libs/combat-penalty-remover-<version>.jar`

### Verification & Testing
Run automated unit tests and code checks:
```bash
./gradlew check
```

---

## 📦 Automated Release Archiving Task
`build.gradle` features an automatic local archive hook:
```groovy
afterEvaluate {
    def targetJarTask = tasks.findByName('remapJar') ?: tasks.findByName('jar')
    if (targetJarTask != null) {
        def archiveTask = tasks.register('archiveReleaseJar') {
            group = 'build'
            description = 'Copies release JAR to local Archive and Central Release Hub'
            dependsOn targetJarTask
        }
        build.finalizedBy(archiveTask)
    }
}
```
Executing `./gradlew build` automatically packages and syncs the artifact into the project archive directories.
