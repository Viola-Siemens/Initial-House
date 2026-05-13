# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

This repository is a Forge mod for Minecraft 1.20.1 that places an initial structure at the overworld spawn point. The mod ID is `initial_house`, the Java group is `com.hexagram2021.initial_house`, and the project targets Java 17.

The README is intentionally minimal: `README.md` only states that the mod adds an initial structure at world spawn for the modpack “The Winter Frontier”.

## Common Commands

Use the Gradle wrapper from the repository root.

- Build the mod jar: `./gradlew build`
- Clean build outputs: `./gradlew clean`
- Produce the development jar: `./gradlew jar`
- Produce the reobfuscated distributable jar: `./gradlew reobfJar`
- Publish to the local `mcmodsrepo` directory configured in `build.gradle`: `./gradlew publish`
- Run the client dev environment: `./gradlew runClient`
- Run the dedicated server dev environment: `./gradlew runServer`
- Run the Forge GameTest server environment: `./gradlew runGameTestServer`
- Generate data into `src/generated/resources`: `./gradlew runData`

## Tests

This repository does not currently contain standalone JUnit-style test sources or a dedicated `test` source tree.

For validation, the closest existing test-oriented workflow is:

- Run the GameTest environment: `./gradlew runGameTestServer`

There is no repository-defined command for running a single test case, because no individual test classes are present.

## Architecture

## Bootstrap and event wiring

`src/main/java/com/hexagram2021/initial_house/InitialHouse.java` is the mod entry point. It does three important things:

- Registers the server config via `IHServerConfig`
- Delegates Forge registry/bootstrap work to `IHContent`
- Hooks runtime behavior into Forge events for first join, respawn, overworld load, server start, and server stop

If behavior changes involve spawn handling or player teleport rules, start here.

## Registration layer

`src/main/java/com/hexagram2021/initial_house/server/IHContent.java` centralizes mod construction. It initializes the custom structure placement type, structure piece type, structure type, and structure set keys through the classes in `server/register`.

This is the boundary between Forge registration and the actual worldgen/runtime logic.

## Worldgen flow

The mod’s spawn structure generation is split across Java registrations and data-driven JSON resources:

- `src/main/java/com/hexagram2021/initial_house/server/register/IHStructureTypes.java` registers the custom structure type
- `src/main/java/com/hexagram2021/initial_house/server/register/IHStructurePlacementTypes.java` registers the custom placement type
- `src/main/java/com/hexagram2021/initial_house/server/world/structures/InitialHouseStructure.java` defines the structure and generation step
- `src/main/java/com/hexagram2021/initial_house/server/world/structures/InitialHouseStructurePieces.java` resolves the structure template and places the actual template piece
- `src/main/java/com/hexagram2021/initial_house/server/world/placements/SpawnPointOnlyPlacement.java` constrains placement to the cached spawn chunk, optionally offset by `xShift` and `zShift`
- `src/main/resources/data/initial_house/worldgen/structure/initial_house.json` binds the structure ID to the custom Java type
- `src/main/resources/data/initial_house/worldgen/structure_set/initial_houses.json` binds the structure set to the custom placement type
- `src/main/resources/data/initial_house/structures/initial_house.nbt` is the default structure template

The key architectural point is that generation only works when the Java-side registered codecs and the resource-side JSON IDs stay aligned.

## Spawn-point dependency

The structure placement logic depends on a cached overworld spawn chunk.

- `InitialHouse.onOverworldLoad(...)` calculates the spawn position from the world sampler and writes it into `SpawnPointOnlyPlacement`
- `SpawnPointOnlyPlacement.isPlacementChunk(...)` only returns true for that cached chunk plus configured offsets
- `InitialHouse.onServerClose(...)` clears the cache

If structure placement stops happening, verify this cache lifecycle before changing the structure JSON or template logic.

## Player spawn and persistence

This mod also alters player spawn behavior beyond world generation.

- `src/main/java/com/hexagram2021/initial_house/server/IHSavedData.java` stores which players have already been processed
- `InitialHouse.onEntityJoin(...)` teleports first-time joining players when the relevant config flag is enabled
- `InitialHouse.onPlayerRespawn(...)` can override vanilla random spawn shifting when no valid bed/anchor/custom respawn position exists

That means spawn behavior is a combination of worldgen, config, and saved data, not just structure placement.

## Configuration surface

`src/main/java/com/hexagram2021/initial_house/server/config/IHServerConfig.java` defines the operational tuning points:

- selectable structure template IDs via `INITIAL_HOUSE_STRUCTURES`
- pivot offsets used when aligning the template to the spawn point
- optional exact-spawn behavior via `DISABLE_SPAWN_POINT_RANDOM_SHIFTING`
- exact teleport offsets via `SPAWN_POINT_SHIFT_X/Y/Z`

When modifying generation position or supporting multiple templates, check config semantics before changing placement code.

## Mixins

The repository includes Sponge Mixin wiring:

- `src/main/resources/initial_house.mixins.json`
- `src/main/java/com/hexagram2021/initial_house/mixin/MinecraftServerMixin.java`

At the moment, the configured mixin exists but its injected method body is empty. Before adding behavior there, verify whether the same goal is already handled by Forge events in `InitialHouse.java`.

## Build Notes

`build.gradle` configures ForgeGradle plus Sponge Mixin, sets Java 17, and defines four run configurations: `client`, `server`, `gameTestServer`, and `data`.

`gradle.properties` sets `org.gradle.jvmargs=-Xmx3G` and disables the Gradle daemon. This project expects enough heap for Forge/Minecraft decompilation tasks.