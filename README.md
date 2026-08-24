<div align="center">

# 🗡️ Combat Penalty Remover

**"Because your gear shouldn't break just because you're playing correctly."**

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Minecraft: 26.1+](https://img.shields.io/badge/Minecraft-26.1+-brightgreen.svg)](https://www.minecraft.net/en-us/article/minecraft-snapshot-26w11a)
[![Fabric: 0.16.10+](https://img.shields.io/badge/Fabric-0.16.10+-lightgrey.svg)](https://fabricmc.net/)

</div>

---

**Combat Penalty Remover** deletes the maintenance chore. It provides surgical control over durability loss triggers, allowing you to separate the "intent" of damage. Part of the **Instant Gratification Collection**.

## ✨ Features

- **⚔️ Weapon Protection**: No durability loss for melee or ranged combat hits.
- **🛡️ Shield Immortality**: Shields survive blocking creepers and heavy "super damage" hits.
- **👕 Context-Aware Armor**: Separate combat hits from environmental hazards (lava, falls, fire).
- **⚡ Spam Protection**: Prevents durability loss from low-charge attacks in Snapshot 11.
- **🔮 Enchantment Suppression**: Removes hidden durability costs for **Thorns** and **Soul Speed**.
- **🏃 Attack Speed Control**: Configurable recharge speed for a faster combat flow.

## ⚙️ Configuration

Configure via `/gamerule ig:` or the in-game GameRules screen.

| Rule | Default | Description |
| :--- | :---: | :--- |
| `ig:prevent_weapon_combat_damage` | true | Weapons won't lose durability when attacking enemies. |
| `ig:prevent_armor_combat_damage` | true | Armor won't lose durability when hit by entities. |
| `ig:prevent_shield_block_damage` | true | Shields won't lose durability when blocking attacks. |
| `ig:prevent_spam_attack_durability_loss` | true | Low-recharge attacks consume no durability. |
| `ig:attack_speed_efficiency` | 100 | Percentage multiplier for attack recharge speed. |
| `ig:prevent_weapon_pvp_damage` | false | Protect weapons specifically during PvP. |
| `ig:prevent_armor_pvp_damage` | false | Protect armor specifically during PvP. |

## 🧩 Compatibility

- **Minecraft**: 26.1 Snapshot 11+
- **Fabric Loader**: 0.16.10+
- **DasikLibrary**: Required for GameRule management.
- **Durability Multiplier**: Fully compatible. Use together to scale environmental damage while stopping combat damage entirely.

---

<div align="center">

**Made with ❤️ by Dasik (Rifaditya)**
*"I will NOT backport this mod. Don't ask. It's annoying."*

</div>
