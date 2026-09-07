# 版本兼容性与生命周期矩阵

> 📌 **代码仓库来源免责声明**：本维基文档反映了**代码仓库中的当前源码状态**，可能包含领先于 CurseForge 和 Modrinth 平台公开发布版本的最新未发布提交或开发中功能。

---

## 📊 Comprehensive Version Matrix

Combat Penalty Remover is maintained under the strict **1 Jar 1 Version Policy** for targeted major releases while supporting open forward compatibility through **DasikLibrary**.

| Minecraft Anchor | Mod Version | Fabric Loader | Fabric API | Java Runtime | Parchment Mappings | DasikLibrary Bound | Primary Status |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.2** | `1.1.0+26.2` | `>=0.19.1` | `0.150.1+26.2` | JDK 25 | `26.1.2-2026.01.22` | `>=1.8.5` | 稳定正式版 |
| **Minecraft 26.3** | `1.1.0+26.3` | `>=0.19.3` | `0.156.1+26.3` | JDK 25 | `26.3-snapshot-6-2026.01.22` | `>=1.8.36` | 快照测试分支 |

---

## 🧩 Architectural Compatibility Standards

### 1. The 1 Jar 1 Version Law
Each targeted Minecraft version anchor possesses its own compiled binary and source repository. This guarantees:
- Zero runtime bytecode shims or reflection overhead.
- Exact method descriptors matched to official Minecraft Mojang mappings.
- Clean dependency trees without stale classes or bloated universal fat-jars.

### 2. Client vs Server Compatibility
- **Dedicated Servers**: Fully supported. Install the mod JAR and `dasik-library` into the `mods/` directory. All GameRules will be registered and broadcast to connecting worlds.
- **Singleplayer / Integrated Server**: Fully supported. GameRules can be configured per world directly via in-game `/gamerule` or game creation menus.
- **Pure Vanilla Clients**: 100% compatible! Vanilla clients running no mods can connect seamlessly to servers hosting Combat Penalty Remover. All damage cancellation and attack speed calculations occur authoritatively on the server side.

### 3. Mod & Tool Ecosystem
- **ModMenu**: Supported on client installations. Features direct links to creator Ko-fi support.
- **Weapon Mods & Datapacks**: Fully compatible with any weapon that utilizes vanilla item tags (`#c:swords`, `#minecraft:swords`, `#minecraft:axes`, `#minecraft:spears`) or extends `ProjectileWeaponItem`.

---

## 🔗 Version Suites Quick Links
* [[👉 Minecraft 26.2 文档套件|zh_cn-26.2-Home]]
* [[👉 Minecraft 26.3 文档套件|zh_cn-26.3-Home]]
* [[🏠 返回中央门户|zh_cn-Home]]
