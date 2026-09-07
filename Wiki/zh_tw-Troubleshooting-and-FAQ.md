# 疑難排解與常見問題 (FAQ)

> 📌 **程式碼倉庫來源免責聲明**：本維基文件反映了**程式碼倉庫中的當前源碼狀態**，可能包含領先於 CurseForge 與 Modrinth 平臺公開發布版本的最新未發布提交或開發中功能。

---

## ❓ 常見問題解答

### 1. Why did my Axe still take 1 durability when I attacked a zombie?
By default in vanilla Minecraft, axes take **2 durability damage** when used as weapons. Combat Penalty Remover offers two controlling GameRules:
1. `ig:treat_axes_as_weapons` (Default: `true`): Normalizes tool combat damage from 2 down to **1 durability**.
2. `ig:prevent_weapon_combat_damage` (Default: `true`): Completely eliminates durability loss (**0 durability**) for all primary weapons and axes (if `treat_axes_as_weapons` is active).

If your axe lost 1 durability, check if `ig:prevent_weapon_combat_damage` was toggled to `false` while `ig:treat_axes_as_weapons` remained `true`:
```mcfunction
/gamerule ig:prevent_weapon_combat_damage true
/gamerule ig:treat_axes_as_weapons true
```

---

### 2. Why did my Armor break when I fell into lava or fell from a cliff?
Combat Penalty Remover differentiates between **Combat Damage** and **Environmental Damage**:
* `ig:prevent_armor_combat_damage` (Default: `true`): Protects armor against melee attacks from mobs and players.
* `ig:prevent_armor_environmental_damage` (Default: `false`): Governs environmental damage such as fire, lava, cactus, kinetic impact, and falling.

To protect armor from environmental damage as well, enable the environmental rule:
```mcfunction
/gamerule ig:prevent_armor_environmental_damage true
```

---

### 3. What is "Spam Attack Durability Loss" and why is it prevented?
In modern Minecraft (post-1.9), attacking before your weapon's recharge meter reaches 100% deals severely reduced damage (down to 20%). However, vanilla Minecraft still deducts the full 1 or 2 points of item durability!
When players click rapidly in panic or intense combat, their precious diamond/netherite tools disintegrate without dealing meaningful damage.

`ig:prevent_spam_attack_durability_loss` (Default: `true`) checks if the attack charge is below 90%. If so, durability damage is completely cancelled:
$$\Delta D = 0 \quad \text{if } C_{\text{charge}} < 0.90$$

---

### 4. How do I configure PvP servers so gear still degrades when players fight each other?
By default, PvP protection rules are set to `false`, allowing normal PvP wear or custom balance:
- `ig:prevent_weapon_pvp_damage` (Default: `false`)
- `ig:prevent_armor_pvp_damage` (Default: `false`)

If you want PvP combat to also have unbreakable weapons and armor, simply set them to `true`:
```mcfunction
/gamerule ig:prevent_weapon_pvp_damage true
/gamerule ig:prevent_armor_pvp_damage true
```

---

### 5. How can I get instant 1.8-style spam combat?
Minecraft's attack recharge delay is controlled dynamically by:
```mcfunction
/gamerule ig:attack_speed_efficiency <percentage>
```
- `100`: Vanilla recharge speed (100%).
- `200`: Double speed (attack delay halved).
- `1000`: 10x speed.
- `10000`: Instantaneous recharge (0 delay ticks), restoring classic pre-1.9 spam clicking mechanics.

In accordance with the **Player Agency & Anti-Nanny Invariant**, this rule has **no artificial upper ceiling**. You can set it to any integer up to `2147483647` without mod interference.

---

### 6. Can players connect to a dedicated server without the mod installed?
**Yes!** Combat Penalty Remover is completely server-authoritative. The server intercepts durability drops and scales attack recharge delay in the server game loop. Vanilla clients require zero mods, resource packs, or special clients to play.

---

## 🔍 診斷指令與健康檢查

To verify all GameRules and their current active states in your world:
```mcfunction
/gamerule ig:prevent_weapon_combat_damage
/gamerule ig:prevent_armor_combat_damage
/gamerule ig:prevent_shield_block_damage
/gamerule ig:prevent_armor_environmental_damage
/gamerule ig:treat_axes_as_weapons
/gamerule ig:prevent_spam_attack_durability_loss
/gamerule ig:attack_speed_efficiency
/gamerule ig:prevent_weapon_pvp_damage
/gamerule ig:prevent_armor_pvp_damage
```

Check server logs upon startup to verify correct initialization:
```log
[Server thread/INFO] [combat-penalty-remover]: Combat Penalty Remover Initializing... (MC 26.1 Snapshot 11 API)
```
