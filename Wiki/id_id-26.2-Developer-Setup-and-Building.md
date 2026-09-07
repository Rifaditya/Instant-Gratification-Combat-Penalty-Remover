# 26.2 Pengaturan Pengembang & Build

> 📌 **Penafian Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup commit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

---

## 1. Official Infobox Table

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.2` |
| **Fabric Loader Version** | `0.19.1` |
| **Fabric API Version** | `0.150.1+26.2` |
| **Java Runtime Required** | JDK 25 |
| **Gradle Wrapper** | Gradle 9.3+ |
| **DasikLibrary Version** | `1.8.5` |
| **Parchment Mapping Spec** | `26.1.2-2026.01.22` |

---

## 2. Build Environment Setup

1. Verify JDK 25 is installed and accessible in your shell:
```bash
java -version
```
Expected output:
```
openjdk version "25" ...
```

2. Confirm `gradle.properties` values for Minecraft 26.2:
```properties
minecraft_version=26.2
fabric_version=0.150.1+26.2
fabric_loader_version=0.19.1
dasik_library_version=1.8.5
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true
```

---

## 3. Compilation & Build Workflow

Compile the mod binary for Minecraft 26.2:
```bash
./gradlew build --no-daemon
```

Execute automated Loom test tasks and validation suites:
```bash
./gradlew check
```

Output Artifact:
`build/libs/combat-penalty-remover-1.1.0+26.2.jar`

---

## 4. Visual ASCII Toolchain Pipeline

```
[ Source Code (Java 25) ]
         |
         v
[ Gradle 9.3+ / Fabric Loom ]
         |
         |---> Reads parchment mappings (26.1.2-2026.01.22)
         |---> Injects DasikLibrary (1.8.5)
         |---> Injects Fabric API (0.150.1+26.2)
         |
         v
[ RemapJar Task ] ---> build/libs/combat-penalty-remover-1.1.0+26.2.jar
         |
         v
[ Auto-Archive Hook ] ---> Archive Jar of all versions/MC 26.2/
```

---

## 5. Dependency Declaration in `fabric.mod.json`

```json
{
  "schemaVersion": 1,
  "id": "combat-penalty-remover",
  "version": "${version}",
  "depends": {
    "fabricloader": ">=0.19.1",
    "minecraft": "*",
    "java": ">=25",
    "dasik-library": "*"
  }
}
```

---

## 🔗 Related Documentation Links
* [[Kembali ke Portal Minecraft 26.2|id_id-26.2-Home]]
* [[26.2 Arsitektur & Analisis Mixin|id_id-26.2-Architecture-and-Mixins]]
* [[Pengaturan Pengembang & Panduan Build Terpadu|id_id-Developer-Setup-and-Building]]
