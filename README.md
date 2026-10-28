# Big Little Fixes

**Forge 1.20.1** · MrWizard94

## Universal half-slab flush

Anything that can sit on a **full block** can sit on a **bottom slab** — placement, hitbox, and mesh flush.

Anything that can hang under a **full ceiling** can hang under a **top slab** — placement, hitbox, and mesh flush.

### How

| Layer | Mechanism |
|--------|-----------|
| **Placement** | Bottom slabs report sturdy **UP**; top/double slabs report sturdy **DOWN** (`isFaceSturdy`) — vanilla + modded `canSurvive` / placement just work |
| **Hitbox** | `getShape` / `getCollisionShape` / `getVisualShape` moved ±½ block when flush applies |
| **Mesh** | `BlockRenderDispatcher.renderBatched` pose translate ±½ (Forge chunk path) |
| **Particles** | Torch / redstone torch flame dust adjusted when floor-flush |

### Examples

**Floor (on bottom slab):** torch, soul torch, redstone torch, standing lantern, pressure plates, carpet, rails, redstone dust, snow, flowers, buttons/levers on floor, most modded floor decorations, even full cubes above a bottom slab.

**Ceiling (under top slab):** hanging lanterns, ceiling grindstones/buttons/levers (`ATTACH_FACE=CEILING`), other hanging-style shapes (upper-cell collision).

**Skipped:** wall torches / wall-only mounts; the slab blocks themselves.

### Config

`config/biglittlefixes-common.toml` → `features.slabFriendlyLights` (default `true`).

### Build / run (PowerShell)

```powershell
cd "C:\WPAI\Gaming\Minecraft\Mods-1.20.1-Forge\Big Little Fixes"
.\gradlew.bat build
.\gradlew.bat runClient
```

Jar: `build/libs/biglittlefixes-1.0.0.jar`
