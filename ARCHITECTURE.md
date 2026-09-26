# Architecture & Symbol Index: Combat Penalty Remover

## 1. Mod Metadata & Entrypoint
- **Mod ID**: `combat-penalty-remover`
- **Main Entrypoint**: `net.instantgratification.combatpenaltyremover.CombatPenaltyRemover` (`net.fabricmc.api.ModInitializer`)
- **Client Entrypoint**: `None`

## 2. Bytecode Mixin Target Registry
| Target Vanilla Class | Mixin Class | Purpose |
| :--- | :--- | :--- |
| `Vanilla Class` | `net.instantgratification.combatpenaltyremover.mixin.ItemStackMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.combatpenaltyremover.mixin.ProjectileWeaponItemMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.combatpenaltyremover.mixin.LivingEntityMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.combatpenaltyremover.mixin.BlocksAttacksMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.combatpenaltyremover.mixin.ChangeItemDamageMixin` | Core mixin hook |
| `Vanilla Class` | `net.instantgratification.combatpenaltyremover.mixin.PlayerMixin` | Core mixin hook |

## 3. Core Mechanics & Subsystems
- **Source Root**: `src/main/java/`
- **Resource Root**: `src/main/resources/`

## 4. Dynamic GameRules & Commands
- **GameRules / Commands**: Configured dynamically via namespaced keys (`combat-penalty-remover:*`).

## 5. Configuration & Sidedness Isolation
- **Sidedness**: Server-safe logic in main, client isolated in `src/client/java` or client entrypoint.
