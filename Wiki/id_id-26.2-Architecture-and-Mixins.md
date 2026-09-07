# 26.2 Arsitektur & Analisis Mixin

> 📌 **Penafian Sumber Kode Repositori**: Dokumentasi di Wiki ini mencerminkan **kondisi kode sumber terkini dalam repositori**, yang mungkin mencakup commit terbaru yang belum dirilis atau fitur eksperimental sebelum rilis publik di CurseForge dan Modrinth.

---

## 1. Official Infobox Table

| Parameter | Technical Details |
| :--- | :--- |
| **Package Root** | `net.instantgratification.combatpenaltyremover` |
| **Target Minecraft Version** | `26.2` |
| **Entrypoint** | `CombatPenaltyRemover` (`ModInitializer`) |
| **Mixin Configuration** | `combat-penalty-remover.mixins.json` |
| **Total Mixins** | 6 Mixins |
| **Dependencies** | Fabric Loader, Fabric API, DasikLibrary (`1.8.5`) |
| **Thread Safety** | ThreadLocal-scoped charge state (`CAPTURED_ATTACK_STRENGTH`) |

---

## 2. Package Architecture Hierarchy

```
net.instantgratification.combatpenaltyremover
│
├── CombatPenaltyRemover.java        <- ModInitializer entrypoint
│
├── mixin
│   ├── BlocksAttacksMixin.java      <- Shield durability bypass
│   ├── ChangeItemDamageMixin.java   <- Enchantment damage bypass (Thorns, etc.)
│   ├── ItemStackMixin.java          <- Melee weapon & tool durability normalizer
│   ├── LivingEntityMixin.java       <- Armor & equipment damage source router
│   ├── PlayerMixin.java             <- Attack strength snapshot & speed delay modifier
│   └── ProjectileWeaponItemMixin.java <- Bow & crossbow shoot durability bypass
│
└── registry
    └── CombatPenaltyRules.java      <- Dynamic GameRules registration & ThreadLocal
```

---

## 3. Mixin Breakdown Matrix

| Mixin Class | Target Minecraft Class | Injection Point | Cancellable | Purpose |
| :--- | :--- | :--- | :--- | :--- |
| `BlocksAttacksMixin` | `net.minecraft.world.item.component.BlocksAttacks` | `@At("HEAD")` on `hurtBlockingItem` | `true` | Intercepts shield item damage when blocking attacks. |
| `ChangeItemDamageMixin` | `net.minecraft.world.item.enchantment.effects.ChangeItemDamage` | `@At("HEAD")` on `apply` | `true` | Prevents enchantment durability drain on weapons and armor. |
| `ItemStackMixin` | `net.minecraft.world.item.ItemStack` | `@At("HEAD")` on `postHurtEnemy` | `true` | Intercepts melee weapon & tool durability deduction. |
| `LivingEntityMixin` | `net.minecraft.world.entity.LivingEntity` | `@At("HEAD")` on `doHurtEquipment` | `true` | Contextual armor protection based on `DamageSource` classification. |
| `PlayerMixin` | `net.minecraft.world.entity.player.Player` | `@At("HEAD")` on `attack` | `false` | Snapshots `getAttackStrengthScale(0.5f)` into ThreadLocal state. |
| `PlayerMixin` | `net.minecraft.world.entity.player.Player` | `@At("RETURN")` on `getCurrentItemAttackStrengthDelay` | `true` | Modifies attack recharge delay by dividing by efficiency multiplier. |
| `ProjectileWeaponItemMixin` | `net.minecraft.world.item.ProjectileWeaponItem` | `@Redirect` on `hurtAndBreak` in `shoot` | N/A | Bypasses bow and crossbow durability consumption upon firing. |

---

## 4. Visual ASCII Injection Pipeline

```
[ Player Attacks Entity ]
         |
         |---> PlayerMixin.cpr$captureAttackStrength() [@At HEAD]
         |     CAPTURED_ATTACK_STRENGTH.set(player.getAttackStrengthScale(0.5F))
         |
         |---> Target Damaged -> ItemStackMixin.cpr$preventMeleeDurabilityLoss() [@At HEAD]
         |     Reads ThreadLocal scale
         |     Evaluates ig:prevent_spam_attack_durability_loss
         |     Evaluates ig:prevent_weapon_combat_damage & ig:treat_axes_as_weapons
         |     ci.cancel() (Zero Durability Loss)
         |
         +---> PlayerMixin.cpr$applyAttackSpeedEfficiency() [@At RETURN]
               cir.setReturnValue(delay / multiplier)
```

---

## 5. ThreadLocal Lifecycle & Zero-Allocation Performance

To ensure maximum TPS stability under intense combat (such as multi-player mob farms or PvP arenas):
1. **Zero GC Heap Allocation**: `CAPTURED_ATTACK_STRENGTH` uses a reusable `ThreadLocal<Float>` with an initial value of `1.0F`. No wrapper objects or heap allocations are instantiated during combat hits.
2. **Server-Side Thread Isolation**: In dedicated server environments with offloaded worker threads or tick loops, each worker thread retains its own isolated attack scale float, preventing cross-player race conditions.

---

## 6. Mixin JSON Descriptor (`combat-penalty-remover.mixins.json`)

```json
{
  "required": true,
  "minCompatibilityLevel": "JAVA_25",
  "package": "net.instantgratification.combatpenaltyremover.mixin",
  "compatibilityLevel": "JAVA_25",
  "mixins": [
    "ItemStackMixin",
    "ProjectileWeaponItemMixin",
    "LivingEntityMixin",
    "BlocksAttacksMixin",
    "ChangeItemDamageMixin",
    "PlayerMixin"
  ],
  "injectors": {
    "defaultRequire": 1
  }
}
```

---

## 7. Developer Guidelines for Addon Creation

Addons wishing to inspect or override Combat Penalty Remover state can hook into `CombatPenaltyRules`:
```java
// Check if an attack was flagged as a spam attack:
float charge = CombatPenaltyRules.CAPTURED_ATTACK_STRENGTH.get();
if (charge < 0.9F) {
    // Custom spam handling
}
```

---

## 🔗 Related Documentation Links
* [[Kembali ke Portal Minecraft 26.2|id_id-26.2-Home]]
* [[26.2 Penghapusan Penalti & Kerusakan Durabilitas|id_id-26.2-Durability-and-Penalty-Removals]]
* [[26.2 Kecepatan Serangan & Kinematika Isi Ulang|id_id-26.2-Attack-Speed-and-Recharge-Kinematics]]
* [[26.2 Konfigurasi & Matriks GameRules|id_id-26.2-Configuration-and-GameRules]]
