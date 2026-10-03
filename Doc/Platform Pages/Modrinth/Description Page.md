<div align="center">

![Mod Banner](LINK_TO_BANNER_IMAGE)

</div>
<p align="center">
    <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <a href="https://discord.gg/EV99bgAFqb"><img src="https://img.shields.io/badge/Discord-Join_Community-5865F2?style=for-the-badge&logo=discord&logoColor=white" alt="Join Discord"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPL--3.0--or--later-green?style=for-the-badge" alt="License">
</p>

# 🌙 Combat Penalty Remover: The "Surgical" Update (Build 1)

**No Backports:** i will **NOT** backport this mod to 1.21 or anything else. don't ask. it's annoying to have multiple versions that are different and i don't like it.

**The Vanilla Problem:** in vanilla minecraft, every time you hit a mob with a sword or block with a shield, the game punishes you. it's a "durability tax" just for playing. you spend more time fixing your gear with mending than actually fighting. it's a maintenance loop that isn't fun at all.

**Combat Penalty Remover** changes this foundation. it gives you direct control over exactly what breaks. no more micromanaging repairs just to do basic combat.

---

## ✨ Features

### 🗡️ Melee & Ranged Protection

stop paying for every swing. whether it's a sword, axe, bow, or crossbow—combat interactions no longer drain your gear. 

> [!NOTE]
> **Technical Implementation**: hooks into `ItemStack.postHurtEnemy` and `ProjectileWeaponItem.shoot` using a surgical `@Redirect` to bypass `hurtAndBreak`.
> **Baseline Efficiency**: O(1) GameRule lookups ensure zero performance impact during high-speed combat.

### 🛡️ Shield Immortality

Vanilla 26.1 shields break way too fast when blocking heavy hits like creepers. we fixed this. shields are now immortal during successful blocks.

### 👕 Context-Aware Armor

we separate combat damage from environmental damage. you can stop mobs from breaking your armor while still letting fire and fall damage stay dangerous if you want.

> [!IMPORTANT]
> **Dynamic Context**: uses `DamageSource` tags to distinguish between entity attacks and world hazards like lava or falling.

### 🔮 Enchantment Suppression

hidden costs are the worst. this mod suppresses the extra durability drain from **Thorns** (armor) and **Soul Speed** (boots) if the relevant rules are enabled.

---

## ⚙️ Config

The mod works out of the box with zero setup. all settings are handled via standard minecraft gamerules.

* **In-Game**: Use `/gamerule ig:` for core settings.
  * `ig:prevent_weapon_combat_damage`: No loss for weapons (Default: `true`)
  * `ig:prevent_armor_combat_damage`: No loss from mob hits (Default: `true`)
  * `ig:prevent_shield_block_damage`: Invincible shields (Default: `true`)
  * `ig:prevent_armor_environmental_damage`: No loss from fire/fall (Default: `false`)

> [!IMPORTANT]
> **Recommended Mod**: Since this mod generates multiple GameRules, it is highly recommended to use **[Collapsible Game Rules](https://modrinth.com/mod/collapsible-gamerules)** for a cleaner UI.

---

## 🧩 Compatibility

| Feature | Fabric (26.1+) |
| :--- | :---: |
| Singleplayer | ✅ |
| Multiplayer (LAN/Server) | ✅ |
| **VO: Better Dogs** | ✅ |
| **Durability Multiplier** | ✅ |

---

### 💬 Join the Community & Get Support
Looking for help, want to test early beta builds, or vote on upcoming features? Join our official Discord community!
<p align="center">
  <a href="https://discord.gg/EV99bgAFqb">
    <img src="https://img.shields.io/badge/💬_Discord-Join_Community-5865F2?style=for-the-badge&logo=discord&logoColor=white" alt="Join Official Discord">
  </a>
</p>

---

## ☕ Support

If you enjoy the **Instant Gratification Collection**, consider fueling future updates!

<p align="center">
  <a href="https://ko-fi.com/dasikigaijin/tip"><img src="https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white" alt="Ko-fi"></a>
  <a href="https://sociabuzz.com/dasikigaijin/tribe"><img src="https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge" alt="SocioBuzz"></a>
  <a href="https://saweria.co/DasikIgaijinn"><img src="https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge" alt="Saweria"></a>
</p>

> [!NOTE]
> **🇮🇩 Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

---

## 📜 Credits & Modpack Permissions

| Role / Property | Author / Link |
| :--- | :--- |
| **Creator / Author** | **Dasik** (Rifaditya) |
| **Community** | [Official Discord](https://discord.gg/EV99bgAFqb) |
| **Collection** | Instant Gratification |
| **License** | [GNU General Public License v3.0 (GPLv3)](https://www.gnu.org/licenses/gpl-3.0.html) |
| **Source Code** | [GitHub - Rifaditya/Instant-Gratification-combat-penalty-remover](https://github.com/Rifaditya/Instant-Gratification-combat-penalty-remover) |
| **Issue Tracker** | [GitHub Issues](https://github.com/Rifaditya/Instant-Gratification-combat-penalty-remover/issues) |
| **Documentation / Wiki** | [GitHub Wiki](https://github.com/Rifaditya/Instant-Gratification-combat-penalty-remover/wiki) |

> [!IMPORTANT]
> **📦 Modpack Permissions & Distribution:**<br>
> You are fully welcome to include this mod in any modpack on any platform! However, the mod file must be downloaded directly through official distribution channels (**Modrinth** or **CurseForge**). Re-uploading, mirroring, or redistributing the original mod JAR to third-party mirror sites, scraper portals, or unauthorized launchers is strictly prohibited.
> <br><br>
> **⚖️ License & Fork Guidelines (No Zero-Change Re-uploads):**<br>
> This project is open-source under the **GNU GPLv3**. You are fully encouraged to inspect the code, learn from it, and fork the repository to create genuine modifications, substantial feature expansions, or community ports—provided your project remains open-source under GPLv3 with proper attribution.<br>
> **However, straight 1:1 re-uploads, clone forks with no meaningful functional changes, or re-publishing identical builds under different project names (e.g. to farm downloads or rewards) are strictly forbidden.**

---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
