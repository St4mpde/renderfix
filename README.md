# RenderFix

Fixes rendering glitches at non-native resolution on Windows.

After events like Alt+Tab, Win+Shift+S (Snipping Tool), or volume OSD overlays, Minecraft's framebuffer can end up corrupted, causing a black screen or frozen image. This mod detects those events and resets the framebuffer automatically.

## Requirements

- Minecraft 1.21.11
- Fabric Loader ≥ 0.17.3
- Fabric API

## Installation

Drop the JAR into your `mods` folder.
