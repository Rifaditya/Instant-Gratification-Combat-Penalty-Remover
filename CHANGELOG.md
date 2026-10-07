<!-- Copyright (C) 2026 Dasik (Rifaditya) | GNU GPLv3 -->
# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [1.1.0+26.3] - 2026-09-26

### Added
- Native compatibility for official Minecraft 26.3 release.

### Changed
- Modernized build pipeline with Fabric Loader 0.19.5 and Fabric API 0.161.0+26.3.
- Aligned dependencies with DasikLibrary 1.9.2 runtime for centralized GameRule configuration.
- Standardized environment metadata in `fabric.mod.json` for universal client and dedicated server support.

### Unchanged
- Preserved GameRules: `ig:prevent_weapon_combat_damage`, `ig:prevent_armor_combat_damage`, `ig:prevent_shield_block_damage`, and `ig:prevent_armor_environmental_damage`.

## [1.1.0+26.2] - 2026-09-06

### Changed
- Clean SemVer rebuild targeting Minecraft 26.2.
- Updated license headers to GNU GPLv3 and aligned distribution guidelines.
- Toolchain modernization with Fabric Loom and Java 25 compatibility.

## [1.1.0+build.2] - 2026-08-24

### Changed
- Interim maintenance build for Minecraft 26.1/26.2 development snapshots (superseded by 1.1.0+26.2 and 1.1.0+26.3).

## [1.0.0+build.1] - 2026-03-07

### Added
- Initial release of Instant Gratification: Combat Penalty Remover.
- Added `ig:prevent_weapon_combat_damage` GameRule to prevent melee and ranged weapon durability loss during combat.
- Added `ig:prevent_armor_combat_damage` GameRule to prevent armor durability loss from combat attacks.
- Added `ig:prevent_shield_block_damage` GameRule to prevent shield durability loss when blocking attacks.
- Added `ig:prevent_armor_environmental_damage` GameRule to protect armor from environmental damage.
- Implemented melee protection via `ItemStackMixin`.
- Implemented ranged weapon protection via `ProjectileWeaponItemMixin`.
- Implemented armor protection via `LivingEntityMixin` (combat vs environmental damage separation).
- Implemented shield block protection via `BlocksAttacksMixin`.
- Implemented enchantment protection (Thorns and Soul Speed) via `ChangeItemDamageMixin`.
