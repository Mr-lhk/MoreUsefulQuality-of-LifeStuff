# More Useful Quality-of-Life Stuff (MuQoLS)

[![Build](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/actions/workflows/build.yml/badge.svg?branch=main)](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/actions/workflows/build.yml)
[![License: GPL-3.0](https://img.shields.io/badge/license-GPL--3.0-blue.svg)](LICENSE.txt)

**MuQoLS** is a utility-focused Minecraft mod for **NeoForge 1.21.1**. It is built around one idea: remove the small, repetitive frustrations that get in the way of playing, and replace them with practical quality-of-life improvements.

> **Project status:** early development. The project is a working NeoForge mod, but gameplay features are still being designed and implemented. Follow the [project board](https://github.com/users/Mr-lhk/projects/6) or [releases](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/releases) for progress.

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

If you are installing on a dedicated server, place the same jar in the server's `mods` folder.

## Design goals

- Reduce repetitive busywork in everyday gameplay.
- Provide sensible defaults, with configuration for players who want to tune behavior.
- Stay compatible-friendly: avoid hard dependencies where possible and document conflicts clearly.
- Prefer small, focused features over one large overhaul.

## Configuration

MuQoLS uses NeoForge's config system. In game, open `Options → Mods → MuQoLS → Config`. The config file is also available at `config/muqols-common.toml`.

## Building from source

You need JDK 21. Clone the repository and run the Gradle build:

```bash
git clone https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff.git
cd MoreUsefulQuality-of-LifeStuff
./gradlew build
```

On Windows, use `gradlew.bat build`.

The built mod jar is written to `build/libs/muqols-<version>.jar`.

For development, the usual run configurations are available:

```bash
./gradlew runClient
./gradlew runServer
```

## Contributing

- Bug reports and feature requests: use the [issue forms](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/issues/new/choose).
- Questions and general discussion: use [GitHub Discussions](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/discussions).
- Pull requests: describe the change, list the versions you tested with, and run `./gradlew build` before submitting.

## Links

- [Issue tracker](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/issues)
- [Discussions](https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/discussions)
- [Project board](https://github.com/users/Mr-lhk/projects/6)
- [NeoForge documentation](https://docs.neoforged.net/)

## License

MuQoLS is licensed under the GNU General Public License v3.0. See [LICENSE.txt](LICENSE.txt) for the full text.

---

Minecraft is a trademark of Mojang Synergies AB. This project is not affiliated with Mojang or NeoForged.
