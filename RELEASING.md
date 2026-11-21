# Release process

## Branch model

- `main` — released code. Only updated by release pull requests and hotfixes.
- `develop` — integration branch. Every feature/fix/chore branch is merged here first.
- `feat/*`, `fix/*`, `chore/*` — short-lived branches created from `develop`.

## Regular change

1. Create a branch from `develop`.
2. Open a pull request into `develop`; CI runs `Build and Package`.
3. Squash merge the pull request and delete the branch.

## Release

1. Create a branch from `develop`, bump `mod_version` in `gradle.properties`, and open a pull request into `main`.
2. Squash merge it, then tag the merge commit on `main`:

   ```bash
   git checkout main
   git pull
   git tag -s v0.1.1 -m "MuQoLS v0.1.1"
   git push origin v0.1.1
   ```

   The tag must be `v<mod_version>`; CI verifies this and fails the run when the two disagree.

3. Pushing the tag builds the jar and creates the GitHub release. The release body starts empty, so fill it in
   afterwards (or write it before tagging and paste it in):

   ```bash
   gh release edit v0.1.1 --notes-file release-notes.md
   ```

4. Merge `main` back into `develop`, so squash-merging the release does not leave the branches diverged:

   ```bash
   git checkout develop
   git merge main
   git push
   ```

## Hotfix

Branch from `main`, fix the problem, open a pull request into `main`, tag a patch release, then merge `main` back into
`develop` as in step 4 above.

## Versioning

[Semantic versioning](https://semver.org/): `MAJOR.MINOR.PATCH`. Tags with `-alpha`, `-beta` or `-rc` suffixes are
published as prereleases by the release workflow.
