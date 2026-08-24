# Architecture Overview - Combat Penalty Remover

## Core Philosophy
The Combat Penalty Remover mod is designed to provide a "clean" way to remove durability penalties without breaking compatibility with other durability-modifying mods (like Durability Multiplier).

## Key Components

### 1. GameRule Registry
- **Path**: `net.instantgratification.combatpenaltyremover.registry.CombatPenaltyRules`
- **Purpose**: Registers and manages the 4 custom GameRules using the `ig:` namespace.
- **Design**: Uses static fields for GameRule keys to ensure performant O(1) lookups in Mixins via `serverLevel.getGameRules().getBoolean()`.

### 2. Mixin Layer
The mod uses a "High-Level Interception" strategy. Instead of hooking into the low-level `hurtAndBreak` method, it hooks into the methods that *decide* to call it.

- **`ItemStackMixin`**: Intercepts melee damage in `postHurtEnemy`.
- **`ProjectileWeaponItemMixin`**: Intercepts ranged damage in `shoot`.
- **`LivingEntityMixin`**: Intercepts armor damage in `doHurtEquipment`, providing the `DamageSource` necessary to distinguish between Combat and Environmental damage.
- **`BlocksAttacksMixin`**: Intercepts shield damage in the 26.1 `BlocksAttacks` component.
- **`ChangeItemDamageMixin`**: Intercepts enchantment-driven damage (Thorns, Soul Speed) via the `ChangeItemDamage` effect.

## Compatibility Strategy
By canceling or redirecting calls *before* they reach `ItemStack.hurtAndBreak`, this mod allows other mods that modify the *amount* of damage (like Durability Multiplier) to still function if the penalty is NOT being removed by this mod.

- **Conflict Prevention**: Uses the `cpr$` prefix for all Mixin members.
- **Standard Adherence**: Verified against Minecraft 26.1 Snapshot 11.
