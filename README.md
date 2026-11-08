# ToolTip Fix — Modern Port for Minecraft 26.3 (Fabric)

![Mod loader: Fabric](https://img.shields.io/badge/modloader-Fabric-1976d2?style=for-the-badge)
![Minecraft: 26.3](https://img.shields.io/badge/Minecraft-26.3-green?style=for-the-badge)
![GitHub license](https://img.shields.io/github/license/Sekai0NI0itamio/ToolTipFix263?style=for-the-badge)

**Fixes tooltips from running off the screen.**

Tired of long item tooltips getting cut off at the edge of your screen?
ToolTip Fix wraps long tooltip lines to fit the space available next to
your cursor, and flips tooltips that would overflow to the other side —
so you can always read the full tooltip, no matter where your cursor is.

Now with a **modern port for Minecraft 26.3 (Fabric)**, rebuilt against
today's tooltip rendering pipeline (`GuiGraphics`, `ClientTooltipComponent`,
`ClientTextTooltip`) while keeping the original behaviour intact.

### What it does

- Wraps long tooltip lines so they fit on screen
- Flips tooltips that would run off the edge to the other side of the cursor
- Handles multi-line and styled tooltips correctly

### Modern port notes (26.3)

- Ported from Yarn to Mojang mappings for 26.3
- Tooltip flip logic reworked to compose with vanilla's built-in flip
  instead of double-flipping
- Java 25 · Fabric Loader 0.20.1+ · Fabric API 0.165.0+26.3
- Built and verified in CI via GitHub Actions

### Installation

1. Install Fabric Loader for Minecraft 26.3
2. Install Fabric API
3. Drop `tooltipfix263-*.jar` into your `mods` folder

### Original mod & credit

This is an unofficial modern port by **Itamio** of **ToolTipFix** by
**kyrptonaught** — the original (17M+ downloads on Modrinth, last updated
for 1.21.1) has not been updated to 26.3.

- Original mod: https://modrinth.com/mod/tooltipfix
- Original source: https://github.com/kyrptonaught/tooltipfix
- License: MIT (Copyright © 2019 kyrptonaught) — see `LICENSE`

All credit for the original concept, design, and logic goes to
kyrptonaught. Itamio does not take credit for the original work;
this port exists to bring it to Minecraft 26.3. See `NOTICE`.
