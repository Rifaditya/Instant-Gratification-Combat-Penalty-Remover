# 26.3 Configuration Développeur et Compilation

> 📌 **Clause de Non-Responsabilité relative au Dépôt Source** : La documentation de ce Wiki reflète **l'état actuel du code source dans le dépôt**, qui peut inclure des commits récents non publiés ou des fonctionnalités en développement en avance sur les versions publiques de CurseForge et Modrinth.

---

## 1. Official Infobox Table

| Parameter | Technical Details |
| :--- | :--- |
| **Minecraft Target** | `26.3` |
| **Fabric Loader Version** | `0.19.3` |
| **Fabric API Version** | `0.156.1+26.3` |
| **Java Runtime Required** | JDK 25 |
| **Gradle Wrapper** | Gradle 9.3+ |
| **DasikLibrary Version** | `1.8.36` |
| **Parchment Mapping Spec** | `26.3-snapshot-6-2026.01.22` |

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

2. Confirm `gradle.properties` values for Minecraft 26.3:
```properties
minecraft_version=26.3
fabric_version=0.156.1+26.3
fabric_loader_version=0.19.3
dasik_library_version=1.8.36
fabric.loom.suppressJavaCompatibilityChecks=true
loom.suppressJavaCompatibilityChecks=true
```

---

## 3. Compilation & Build Workflow

Compile the mod binary for Minecraft 26.3:
```bash
./gradlew build --no-daemon
```

Execute automated Loom test tasks and validation suites:
```bash
./gradlew check
```

Output Artifact:
`build/libs/combat-penalty-remover-1.1.0+26.3.jar`

---

## 4. Visual ASCII Toolchain Pipeline

```
[ Source Code (Java 25) ]
         |
         v
[ Gradle 9.3+ / Fabric Loom ]
         |
         |---> Reads parchment mappings (26.3-snapshot-6-2026.01.22)
         |---> Injects DasikLibrary (1.8.36)
         |---> Injects Fabric API (0.156.1+26.3)
         |
         v
[ RemapJar Task ] ---> build/libs/combat-penalty-remover-1.1.0+26.3.jar
         |
         v
[ Auto-Archive Hook ] ---> Archive Jar of all versions/MC 26.3/
```

---

## 5. Dependency Declaration in `fabric.mod.json`

```json
{
  "schemaVersion": 1,
  "id": "combat-penalty-remover",
  "version": "${version}",
  "depends": {
    "fabricloader": ">=0.19.3",
    "minecraft": "*",
    "java": ">=25",
    "dasik-library": "*"
  }
}
```

---

## 🔗 Related Documentation Links
* [[Retour au Portail Minecraft 26.3|fr_fr-26.3-Home]]
* [[26.3 Architecture et Analyse Détaillée des Mixins|fr_fr-26.3-Architecture-and-Mixins]]
* [[Guide de Configuration Développeur et de Compilation|fr_fr-Developer-Setup-and-Building]]
