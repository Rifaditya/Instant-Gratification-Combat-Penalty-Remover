# Version History - Combat Penalty Remover

## 1.0.0+build.1 (2026-03-07)
### Initial Release
- **Feature**: Added `ig:prevent_weapon_combat_damage` GameRule.
- **Feature**: Added `ig:prevent_armor_combat_damage` GameRule.
- **Feature**: Added `ig:prevent_shield_block_damage` GameRule.
- **Feature**: Added `ig:prevent_armor_environmental_damage` GameRule.
- **Technical**: Implemented Melee protection via `ItemStackMixin`.
- **Technical**: Implemented Ranged protection via `ProjectileWeaponItemMixin`.
- **Technical**: Implemented Armor protection via `LivingEntityMixin` (Combat vs Environmental).
- **Technical**: Implemented Shield protection via `BlocksAttacksMixin`.
- **Technical**: Implemented Enchantment protection (Thorns/Soul Speed) via `ChangeItemDamageMixin`.
- **Refinement**: Verified against Minecraft 26.1 Snapshot 11.
