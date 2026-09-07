# 개발자 환경 설정 및 통합 빌드 가이드

> 📌 **저장소 소스 코드 면책 조항**: 이 위키의 문서는 **저장소의 현재 소스 코드 상태**를 반영하며, CurseForge 및 Modrinth의 공개 릴리스 빌드에 앞서 최근 릴리스되지 않은 커밋이나 개발 중인 기능이 포함될 수 있습니다.

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
