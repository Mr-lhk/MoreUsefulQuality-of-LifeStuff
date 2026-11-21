# More Useful Quality-of-Life Stuff (MuQoLS)

[![Build](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/actions/workflows/build.yml)
[![License: MIT](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE.txt)

MuQoLS is a small NeoForge 1.21.1 mod that removes everyday annoyances instead of adding new systems. It is meant to
feel like vanilla: a handful of practical conveniences, no new progression, no overpowered items.

## Features

The mod is in rapid development. Any features could be changed.

## Design philosophy

1. **Convenience, not power.** The mod should save clicks and busywork, not make the player stronger. Every item is
   balanced against its vanilla equivalent.
2. **Small and focused.** Several small, independent features are better than one large overhaul — each one can be
   understood and disabled on its own.
3. **Vanilla first.** No new systems to learn. If a feature changes how the game feels, it should feel like vanilla
   Minecraft doing it.
4. **Configurable.** Anything that changes gameplay can be switched off.
5. **Compatibility-friendly.** No hard dependencies. 

## Requirements

| Component | Version |
| --- | --- |
| Minecraft | 1.21.1 |
| NeoForge | 21.1.219 or newer |
| Java | 21 |

## Installation

1. Install NeoForge 1.21.1 (21.1.219 or newer).
2. Download the latest `muqols-*.jar` from [Releases](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/releases).
3. Place the jar in your Minecraft `mods` folder.
4. Launch the game with the NeoForge profile.

On a dedicated server, place the same jar in the server's `mods` folder.

## Configuration

The config file is `config/muqols-common.toml`; it is also reachable in game from `Options → Mods → MuQoLS → Config`.
Changing it requires a restart.

## Building from source

You need JDK 21:

```bash
git clone https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff.git
cd MoreUsefulQuality-of-LifeStuff
./gradlew build
```

On Windows use `gradlew.bat build`. The mod jar is written to `build/libs/`.

## License

MuQoLS is licensed under the **MIT License**. See [LICENSE.txt](LICENSE.txt) for the full text.

---

Minecraft is a trademark of Mojang Synergies AB. This project is not affiliated with Mojang or NeoForged.
