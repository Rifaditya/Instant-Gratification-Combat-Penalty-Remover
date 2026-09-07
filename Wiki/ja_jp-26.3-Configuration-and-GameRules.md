# 26.3 設定とゲームルール (GameRules) マトリクス

> 📌 **リポジトリソースに関する免責事項**: 本 Wiki のドキュメントは**リポジトリ内の現在のソースコード状態**を反映しており、CurseForge および Modrinth での公開リリースビルドに先駆けた最新の未リリースコミットや開発中の機能が含まれている場合があります。

---

## 1. Official Infobox Table

| Parameter | Technical Details |
| :--- | :--- |
| **Configuration Architecture** | Dynamic Namespaced GameRules via DasikLibrary |
| **Target Minecraft Version** | `26.3` |
| **Category Identifier** | `combat-penalty-remover:combat_penalties` |
| **Category Title** | `Combat Penalties` |
| **Storage Location** | World Save `level.dat` (`GameRules` NBT compound) |
| **Command Interface** | Vanilla `/gamerule` with tab completion |
| **Total Registered Rules** | 9 (8 Booleans, 1 Integer) |

---

## 2. Step-by-Step Administration Workflow

```
[ GameRule Configuration Workflow ]
1. Open in-game chat as Operator (Permission Level 2) or Singleplayer with cheats enabled.
2. Type '/gamerule ig:' to view interactive tab-completion of all 9 combat rules.
3. Query active rule status:
   /gamerule ig:prevent_weapon_combat_damage
4. Modify rule value dynamically:
   /gamerule ig:attack_speed_efficiency 200
5. Rules update instantly in memory and serialize automatically to level.dat.
```

---

## 3. Exhaustive GameRules Matrix

| GameRule Key | Data Type | Default Value | Category | Gameplay Effect |
| :--- | :--- | :--- | :--- | :--- |
| `ig:prevent_weapon_combat_damage` | `Boolean` | `true` | Combat Penalties | Weapons (Swords, Tridents, Maces, Spears, Bows, Crossbows) do not lose durability when attacking enemies. |
| `ig:prevent_armor_combat_damage` | `Boolean` | `true` | Combat Penalties | Armor does not lose durability from combat damage caused by entities (mobs or other players). |
| `ig:prevent_shield_block_damage` | `Boolean` | `true` | Combat Penalties | Shields do not lose durability when blocking melee or projectile attacks. |
| `ig:prevent_armor_environmental_damage` | `Boolean` | `false` | Combat Penalties | Armor does not lose durability from environmental sources such as fire, lava, cactus, kinetic impact, or falling. |
| `ig:treat_axes_as_weapons` | `Boolean` | `true` | Combat Penalties | Axes and tools lose only 1 durability when striking an enemy instead of the vanilla penalty of 2 (or 0 if weapon protection is active). |
| `ig:prevent_spam_attack_durability_loss` | `Boolean` | `true` | Combat Penalties | Attacks executed below 90% charge do not consume weapon durability, protecting gear during rapid spam clicking. |
| `ig:attack_speed_efficiency` | `Integer` | `100` | Combat Penalties | Percentage multiplier for weapon attack recharge speed. 100 is vanilla, 200 is 2x speed, 1000 restores instant 1.8 spam combat. |
| `ig:prevent_weapon_pvp_damage` | `Boolean` | `false` | Combat Penalties | Weapons do not lose durability when attacking other players during PvP combat. |
| `ig:prevent_armor_pvp_damage` | `Boolean` | `false` | Combat Penalties | Armor does not lose durability when taking damage from other players during PvP combat. |

---

## 4. Player Agency & Anti-Nanny Invariant

In strict compliance with our core architectural standards:
* **Zero Artificial Upper Limits**: `ig:attack_speed_efficiency` can be set to any positive integer up to `Integer.MAX_VALUE` (`2,147,483,647`). Server owners and players have total sandbox freedom to make weapons recharge as fast as desired without arbitrary ceilings.
* **Crash-Prevention Lower Bound**: A mathematical guard ensures `efficiency > 0` before division, avoiding JVM arithmetic exceptions (`/ 0`).

---

## 5. Visual ASCII Decision Tree

```
                      [ Combat Attack Occurs ]
                                 |
           +---------------------+---------------------+
           |                                           |
    [ Attacker Weapon ]                         [ Defender Armor ]
           |                                           |
  pvp? -> ig:prevent_weapon_pvp_damage        pvp? -> ig:prevent_armor_pvp_damage
           |                                           |
  charge < 0.9? -> ig:prevent_spam_...        mob? -> ig:prevent_armor_combat_damage
           |                                           |
  weapon? -> ig:prevent_weapon_combat...      env? -> ig:prevent_armor_environmental...
           |
  axe/tool? -> ig:treat_axes_as_weapons
```

---

## 6. SNBT Storage Schema in `level.dat`

```snbt
{
  "Data": {
    "GameRules": {
      "ig:prevent_weapon_combat_damage": "true",
      "ig:prevent_armor_combat_damage": "true",
      "ig:prevent_shield_block_damage": "true",
      "ig:prevent_armor_environmental_damage": "false",
      "ig:treat_axes_as_weapons": "true",
      "ig:prevent_spam_attack_durability_loss": "true",
      "ig:attack_speed_efficiency": "100",
      "ig:prevent_weapon_pvp_damage": "false",
      "ig:prevent_armor_pvp_damage": "false"
    }
  }
}
```

---

## 7. Developer API Reference

```java
// Registration via DasikLibrary DynamicGameRuleManager
public static void register() {
    CATEGORY = DynamicGameRuleManager.registerCategory(
            Identifier.fromNamespaceAndPath("combat-penalty-remover", "combat_penalties")
    );

    PREVENT_WEAPON_COMBAT_DAMAGE = DynamicGameRuleManager.booleanRule("ig:prevent_weapon_combat_damage", CATEGORY, true)
            .name("Prevent Weapon Combat Damage")
            .description("If active, melee and ranged weapons won't lose durability during combat attacks.")
            .register();
}
```

---

## 🔗 Related Documentation Links
* [[Minecraft 26.3 ポータルに戻る|ja_jp-26.3-Home]]
* [[26.3 耐久値減少とペナルティ解除|ja_jp-26.3-Durability-and-Penalty-Removals]]
* [[26.3 攻撃速度とリチャージキネマティクス|ja_jp-26.3-Attack-Speed-and-Recharge-Kinematics]]
* [[26.3 アーキテクチャ設計と Mixin 解析|ja_jp-26.3-Architecture-and-Mixins]]
