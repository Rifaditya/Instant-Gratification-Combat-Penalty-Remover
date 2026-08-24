<div align="center">

![Mod Banner](LINK_TO_BANNER_IMAGE)

</div>
<p align="center">
    <a href="https://modrinth.com/mod/fabric-api"><img src="https://img.shields.io/badge/Requires-Fabric_API-blue?style=for-the-badge&logo=fabric" alt="Requires Fabric API"></a>
    <img src="https://img.shields.io/badge/Language-Java-orange?style=for-the-badge&logo=java" alt="Java">
    <img src="https://img.shields.io/badge/License-GPL--3.0--or--later-green?style=for-the-badge" alt="License">
</p>

# 🌙 Combat Penalty Remover: The "Surgical" Update (Build 1)

**Active Version Policy:** I build **1 JAR for 1 Version**. I only update and maintain the latest active Minecraft version (e.g. when 26.3 is released, 26.2 is retired). No backports or legacy version maintenance. Please do not ask.

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


> [!IMPORTANT]
> **Config vs. In-Game GameRules:**
> The global configuration file only defines **default values for new worlds** at creation time.
> If you have **already created/opened a world**, changing the config file will have no effect. You must change the settings in-game using the **Edit Game Rules** UI screen or the /gamerule command.
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

## ☕ Support

if you enjoy **Combat Penalty Remover** and my modding philosophy, consider fueling the next update with a coffee!

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=for-the-badge&logo=ko-fi&logoColor=white)](https://ko-fi.com/dasikigaijin/tip)
[![SocioBuzz](https://img.shields.io/badge/SocioBuzz-Local_Support-7BB32E?style=for-the-badge)](https://sociabuzz.com/dasikigaijin/tribe)
[![Saweria](https://img.shields.io/badge/Saweria-Local_Support-FFA500?style=for-the-badge)](https://saweria.co/DasikIgaijinn)

> [!NOTE]
> **Indonesian Users:** SocioBuzz and Saweria support local payment methods (Gopay, OVO, Dana, etc.) if you want to support me without using PayPal/Ko-fi!

---

## 📜 Credits

| Role | Author |
| :--- | :--- |
| **Architect** | **Dasik (Rifaditya)** |
| **Collection** | Instant Gratification |
| **License** | GPL-3.0-or-later |

---

> [!IMPORTANT]
> This mod is part of my custom collection. You are free to use it in modpacks, videos, and servers.
>
> > [!IMPORTANT]
> > **📦 Modpack Permissions:** You are free to include this mod in any modpack on any platform. However, the mod itself must be downloaded from its official distribution pages on **Modrinth** or **CurseForge**. Re-uploading or redistributing the mod jar file to third-party sites is strictly prohibited unless explicitly permitted by the creator.


---

<div align="center">

**Made with ❤️ for the Minecraft community**

*Part of the Instant Gratification Collection*

</div>
