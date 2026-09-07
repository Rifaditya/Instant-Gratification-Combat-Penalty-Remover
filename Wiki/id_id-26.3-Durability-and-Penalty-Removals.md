# 26.3 Penghapusan Penalti & Kerusakan Durabilitas

> 📌 **Penafian Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup commit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

---

## 1. Official Infobox Table

| Parameter | Technical Details |
| :--- | :--- |
| **Feature Category** | Durability Consumption Interception & Normalization |
| **Target Minecraft Version** | `26.3-snapshot-6` |
| **Primary Mixins** | `ItemStackMixin`, `ProjectileWeaponItemMixin`, `BlocksAttacksMixin`, `LivingEntityMixin`, `ChangeItemDamageMixin` |
| **Controlling GameRules** | `ig:prevent_weapon_combat_damage`, `ig:treat_axes_as_weapons`, `ig:prevent_shield_block_damage`, `ig:prevent_armor_combat_damage`, `ig:prevent_armor_environmental_damage`, `ig:prevent_spam_attack_durability_loss` |
| **Item Tags Checked** | `#minecraft:swords`, `#minecraft:axes`, `#minecraft:pickaxes`, `#minecraft:shovels`, `#minecraft:hoes`, `#minecraft:spears` |
| **Side Execution** | Server-Side Authoritative (`ServerLevel`) |

---

## 2. Step-by-Step Player Workflow

```
[ Survival Combat Workflow ]
1. Player engages hostile mob or boss with weapon / tool / bow / shield.
2. Attack hits target:
   - If Weapon: Durability drop intercepted (0 loss).
   - If Tool (Axe): Durability drop normalized from 2 -> 1, or 0 if weapon protection active.
   - If Uncharged Click (<90% charge): Durability drop fully cancelled (0 loss).
3. Mob counter-attacks:
   - If Shield Raised: Block damage intercepted (0 shield loss).
   - If Armor Absorbs: Combat damage intercepted (0 armor loss).
   - If Fire / Lava / Fall: Depends on ig:prevent_armor_environmental_damage.
```

---

## 3. Mathematical Formulas & Interception Logic

### Weapon Durability Interception Formula
$$D_{\text{weapon}} = \begin{cases} 0 & \text{if } \text{PREVENT\_WEAPON\_COMBAT\_DAMAGE} = \text{true} \\ 1 & \text{if } \text{TREAT\_AXES\_AS\_WEAPONS} = \text{true} \land \text{isTool} \\ 2 & \text{vanilla axe default} \end{cases}$$

### Uncharged Spam Attack Safety
Vanilla attack strength scale $S_{\text{scale}} \in [0.0, 1.0]$ is captured during `Player.attack()`:
$$C_{\text{charge}} = \text{player.getAttackStrengthScale}(0.5\text{f})$$
The resulting durability deduction $\Delta D$ satisfies:
$$\Delta D = \begin{cases} 0 & \text{if } C_{\text{charge}} < 0.90 \land \text{PREVENT\_SPAM\_ATTACK\_DURABILITY\_LOSS} \\ D_{\text{weapon}} & \text{otherwise} \end{cases}$$

### DamageSource Entity Classification
Armor damage interception evaluates the provenance of incoming damage:
$$\text{Classification} = \begin{cases} \text{PvP} & \text{if } \text{source.getEntity()} \in \text{Player} \lor \text{source.getDirectEntity()} \in \text{Player} \\ \text{Combat} & \text{if } \text{source.getEntity()} \neq \text{null} \lor \text{source.getDirectEntity()} \neq \text{null} \\ \text{Environmental} & \text{otherwise} \end{cases}$$

---

## 4. Visual ASCII Flowchart

```
                 [ Incoming Combat Damage Event ]
                                |
        +-----------------------+-----------------------+
        |                                               |
  [ Item In Hand ]                             [ Entity Equipment ]
        |                                               |
  (Player Attacking)                            (Player Damaged)
        |                                               |
   Charge < 0.90?                               DamageSource Check:
    /          \                                 /        |        \
  (Yes)        (No)                           (PvP)    (Mob)   (Environment)
    |            |                              |        |           |
 [Cancel]   Primary Weapon?                     v        v           v
 (0 loss)     /        \                     PvP Rule Mob Rule   Env Rule
            (Yes)      (No: Tool)               |        |           |
              |          |                      +--------+-----------+
          [Cancel]   Treat Axes as Weapons?              |
          (0 loss)     /         \                  [Cancel]
                     (Yes)       (No)                (0 loss)
                       |           |
               Weapon Protected? [Vanilla 2]
                  /       \
                (Yes)     (No)
                  |         |
              [Cancel]  [Break 1]
              (0 loss)  (1 loss)
```

---

## 5. SNBT & Data Component Schemas

Modern Minecraft 26.x represents shield blocking and item damage via Data Components:

### `minecraft:blocks_attacks` Component Schema
```json
{
  "minecraft:blocks_attacks": {
    "block_delay": 0,
    "disable_cooldown": 100,
    "damage_blocked": 1.0
  }
}
```
*Mixin Interception*: `BlocksAttacksMixin` intercepts `hurtBlockingItem` before durability is modified on the `ItemStack`.

### `minecraft:damage` Component Schema
```json
{
  "minecraft:damage": 0,
  "minecraft:max_damage": 1561,
  "minecraft:unbreakable": {}
}
```

---

## 6. Exhaustive Reference Tables

| Target Equipment | Vanilla Durability Cost | Mod Default Cost | Controlling GameRule | Default Setting |
| :--- | :--- | :--- | :--- | :--- |
| **Swords** (`#swords`) | 1 per hit | **0** | `ig:prevent_weapon_combat_damage` | `true` |
| **Tridents / Maces / Spears** | 1 per hit | **0** | `ig:prevent_weapon_combat_damage` | `true` |
| **Axes (Combat)** (`#axes`) | 2 per hit | **0** (or **1** if weapon damage enabled) | `ig:treat_axes_as_weapons` | `true` |
| **Tools (Pick/Shovel/Hoe)** | 2 per hit | **0** (or **1**) | `ig:treat_axes_as_weapons` | `true` |
| **Bows / Crossbows** | 1 per shot | **0** | `ig:prevent_weapon_combat_damage` | `true` |
| **Shields** (`BlocksAttacks`) | Variable per blocked damage | **0** | `ig:prevent_shield_block_damage` | `true` |
| **Armor (Mob Attacks)** | 1-4 depending on hit magnitude | **0** | `ig:prevent_armor_combat_damage` | `true` |
| **Armor (PvP Attacks)** | 1-4 depending on hit magnitude | Vanilla (Default) | `ig:prevent_armor_pvp_damage` | `false` |
| **Armor (Fire / Lava / Fall)** | 1 per tick / event | Vanilla (Default) | `ig:prevent_armor_environmental_damage` | `false` |

---

## 7. Developer & Mixin Hooks

```java
// ItemStackMixin.java (postHurtEnemy Hook)
@Inject(method = "postHurtEnemy", at = @At("HEAD"), cancellable = true)
private void cpr$preventMeleeDurabilityLoss(LivingEntity mob, LivingEntity attacker, CallbackInfo ci) {
    if (attacker.level() instanceof ServerLevel serverLevel) {
        if (mob instanceof Player && serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_WEAPON_PVP_DAMAGE)) {
            ci.cancel();
            return;
        }
        if (serverLevel.getGameRules().get(CombatPenaltyRules.PREVENT_SPAM_ATTACK_DURABILITY_LOSS) 
            && CombatPenaltyRules.CAPTURED_ATTACK_STRENGTH.get() < 0.9F) {
            ci.cancel();
            return;
        }
    }
}
```

---

## 🔗 Related Documentation Links
* [[Kembali ke Portal Minecraft 26.3|id_id-26.3-Home]]
* [[26.3 Kecepatan Serangan & Kinematika Isi Ulang|id_id-26.3-Attack-Speed-and-Recharge-Kinematics]]
* [[26.3 Konfigurasi & Matriks GameRules|id_id-26.3-Configuration-and-GameRules]]
* [[26.3 Arsitektur & Analisis Mixin|id_id-26.3-Architecture-and-Mixins]]
