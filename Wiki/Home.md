# Instant Gratification: Combat Penalty Remover Wiki

🌐 **Languages**: [[🇺🇸 English|Home]] | [[🇨🇳 简体中文|zh_cn-Home]] | [[🇭🇰 繁體中文|zh_tw-Home]] | [[🇷🇺 Русский|ru_ru-Home]] | [[🇪🇸 Español|es_es-Home]] | [[🇩🇪 Deutsch|de_de-Home]] | [[🇫🇷 Français|fr_fr-Home]] | [[🇧🇷 Português|pt_br-Home]] | [[🇯🇵 日本語|ja_jp-Home]] | [[🇮🇩 Bahasa Indonesia|id_id-Home]] | [[🇰🇷 한국어|ko_kr-Home]]

---

> 📌 **Repository Source Disclaimer**: The documentation in this Wiki reflects the **current source code state in the repository**, which may include recent unreleased commits or developmental features ahead of public release builds on CurseForge and Modrinth.

---

## ⚔️ Overview & Purpose

**Combat Penalty Remover** is a core gameplay refinement mod designed to eliminate arbitrary durability penalties, friction, and combat fatigue in modern Minecraft. In vanilla gameplay, weapons degrade with every strike, axes suffer a punishing 2x weapon penalty, uncharged spam swings consume full item durability despite dealing fractional damage, shields rapidly lose health when blocking large waves, and armor disintegrates under combat assault.

Combat Penalty Remover re-empowers player agency and weapon durability economics through a 100% server-side, dynamic GameRules engine. Players can fight relentlessly without fearing gear destruction, fine-tune weapon vs tool durability logic, protect armor against mobs or players independently, and adjust weapon attack recharge speed with zero artificial caps (Player Agency & Anti-Nanny Invariant).

### 🚀 Key Feature Pillars
1. **Melee & Ranged Durability Immunity**: Swords, tridents, maces, spears, bows, and crossbows bypass durability damage when striking enemies.
2. **Tool Weapon Normalization**: Axes and harvest tools used in combat can be normalized from the punishing vanilla 2x durability loss to 1x (sword standard), or 0x (complete immunity).
3. **Spam Attack Durability Protection**: Rapid uncharged clicks deal minimal damage in modern combat; this mod prevents them from chewing through your weapon's durability.
4. **Shield Block Immunity**: Blocking incoming projectile and melee assaults consumes zero shield durability.
5. **Granular DamageSource Armor Protection**: Armor can be protected from entity combat damage (mobs/players) while maintaining environmental hazards (fire, lava, falls), or protected from both.
6. **Dynamic Attack Recharge Kinematics**: Seamlessly accelerate weapon recharge speed using percentage multipliers (from vanilla 100% to 200%, 500%, or instant 1.8-style spam combat).
7. **100% Client-Server Parity**: Runs purely server-side! Vanilla clients can connect to servers running this mod without installing anything.

---

## 🧭 Multi-Version Documentation Portal

Select your targeted Minecraft version to access dedicated, isolated documentation suites:

| Minecraft Anchor | Release Type | Fabric Loader | Java Spec | DasikLibrary Bound | Dedicated Documentation Suite |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Minecraft 26.2** | Stable Anchor | `>=0.19.1` | JDK 25 | `>=1.8.5` | [[👉 Enter Minecraft 26.2 Wiki Suite|26.2-Home]] |
| **Minecraft 26.3** | Snapshot Anchor | `>=0.19.3` | JDK 25 | `>=1.8.36` | [[👉 Enter Minecraft 26.3 Wiki Suite|26.3-Home]] |

---

## 📖 Central Documentation Guides

* [[📋 Version Compatibility Matrix|Version-Compatibility]] — Full toolchain, Loom mappings, and platform compatibility.
* [[❓ Troubleshooting & FAQ|Troubleshooting-and-FAQ]] — Common questions on durability calculation, spam attacks, and PvP settings.
* [[🛠️ Developer Setup & Building|Developer-Setup-and-Building]] — Building from source with Gradle 9.3+ and JDK 25.

---

## ⚖️ License & Provenance

* **Author**: **Dasik (Rifaditya)**
* **License**: **GNU General Public License v3.0 (GPLv3)**
* **Source Code**: [GitHub Repository](https://github.com/Rifaditya/Instant-Gratification-Combat-Penalty-Remover)
* **Creator Support**: [Ko-fi](https://ko-fi.com/dasikigaijin)
