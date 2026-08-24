# User Guide - Combat Penalty Remover

## Installation
1. Ensure you are running **Minecraft 26.1 Snapshot 11** or higher.
2. Install the **Fabric Loader**.
3. Place the `combat-penalty-remover.jar` in your `mods` folder.

## Using the Mod
The mod is active by default with standard combat protection enabled. You can adjust the behavior using GameRules.

### Available Controls
- **Weapon Combat Damage**: `/gamerule ig:prevent_weapon_combat_damage <true|false>`
  - Stops swords, axes, and bows from breaking during combat.
- **Armor Combat Damage**: `/gamerule ig:prevent_armor_combat_damage <true|false>`
  - Stops armor from breaking when enemies hit you.
- **Shield Damage**: `/gamerule ig:prevent_shield_block_damage <true|false>`
  - Makes shields invincible when blocking.
- **Armor Environmental Damage**: `/gamerule ig:prevent_armor_environmental_damage <true|false>`
  - (Default: `false`) Stops armor from breaking from fire, fall, or drowning.

## Support for Enchantments
This mod automatically detects and suppresses durability costs from **Thorns** (on armor) and **Soul Speed** (on boots) if the corresponding rules are enabled. No additional configuration is needed.

## Compatibility
Combat Penalty Remover is designed to work alongside other durability mods. It cancels the *trigger* for durability loss, allowing other mods to handle the *math* of durability if loss is permitted.
