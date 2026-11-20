# 项目管理与自动化 / Project & Automation

本仓库使用一套中英双语的 Issue 模板、PR 模板、标签规则和 GitHub Projects 看板。本文档说明它们如何配合，以及维护者需要做的一次性设置。

This repository uses bilingual issue forms, a pull request template, label automation, and a GitHub Projects board. This document explains how they fit together and the one-time setup required from maintainers.

## 看板 / Project board

- **名称 / Name**: MuQoLS Development
- **地址 / URL**: <https://github.com/users/Mr-lhk/projects/6>
- **可见性 / Visibility**: Public
- **编号 / Number**: 6（工作流中的 `project-url` 使用此编号 / used in the workflow `project-url`）

### 字段 / Fields

| 字段 / Field | 类型 / Type | 取值 / Values | 用途 / Purpose |
| --- | --- | --- | --- |
| Status | Single select | Todo, In Progress, Done | 工作流状态 / Workflow state |
| Priority | Single select | P0, P1, P2 | P0 紧急，P1 高，P2 普通 / P0 critical, P1 high, P2 normal |
| Issue Type | Single select | Bug, Feature, Compatibility, Documentation | 工作类型 / Kind of work |
| Area | Single select | Core, Compat, Assets, CI | 影响范围 / Affected area |
| Target Date | Date | — | Roadmap 时间轴 / Roadmap timeline |

> GitHub 保留 `Type` 作为内置字段名，因此这里的自定义字段命名为 `Issue Type`。
> GitHub reserves `Type` for its built-in issue-type field, so the custom field is named `Issue Type`.

### 视图 / Views

| 视图 / View | 布局 / Layout | 说明 / Notes |
| --- | --- | --- |
| All items | Table | 显示 Status、Priority、Issue Type、Area、Target Date、Assignees、Labels、Repository |
| Board | Board | Group by Status（需一次性设置 / one-time setup）；展示 Priority、Issue Type、Area、Assignees、Target Date |
| Roadmap | Roadmap | 使用 Target Date 作为时间轴（需一次性确认 / confirm once） |

**一次性 UI 设置 / One-time UI setup**

GitHub 的公开 API 目前不能写入看板分组和 Roadmap 日期字段，因此创建后需要手动完成两步：

1. 打开 **Board** 视图，在视图菜单中设置 **Group by: Status**。
2. 打开 **Roadmap** 视图，在视图设置中选择 **Target Date** 作为日期字段（如果尚未自动选中）。

The public API cannot currently write board grouping or the Roadmap date field. After creation, do these two steps once:

1. Open the **Board** view and set **Group by: Status** in the view menu.
2. Open the **Roadmap** view and select **Target Date** as its date field, if it is not selected automatically.

设置完成后，本项目可以直接作为模板复制：

```bash
gh project copy 6 --source-owner Mr-lhk --target-owner Mr-lhk --title "New MuQoLS Project"
```

复制会保留字段、视图和已配置的内置工作流（自动添加工作流除外）；自动添加工作流、仓库关联、条目和项目成员需要重新配置。用户级 Project 不能使用 `gh project mark-template`（仅组织可用），所以用 `copy` 复用。

Copying preserves fields, views, and configured built-in workflows (except auto-add workflows); auto-add workflows, linked repositories, items, and collaborators are not copied. User-owned projects cannot be marked as templates with `gh project mark-template` (organization-only), so use `copy` instead.

## Issue 模板 / Issue forms

| 模板 / Form | 文件 / File | 自动标签 / Label |
| --- | --- | --- |
| 🐛 Bug 报告 / Bug Report | `.github/ISSUE_TEMPLATE/bug_report.yml` | `bug` |
| ✨ 功能请求 / Feature Request | `.github/ISSUE_TEMPLATE/feature_request.yml` | `enhancement` |
| 🧩 兼容性问题 / Compatibility Issue | `.github/ISSUE_TEMPLATE/compatibility.yml` | `compatibility` |
| ❓ 使用与安装问题 / Usage & Installation | `.github/ISSUE_TEMPLATE/question.yml` | `question` |
| 📝 文档与翻译 / Docs & Translation | `.github/ISSUE_TEMPLATE/documentation.yml` | `documentation` |

`config.yml` 关闭了空白 Issue，并引导用户先查看 README、Q&A Discussions 和 Ideas Discussions。

`config.yml` disables blank issues and points users to the README, Q&A Discussions, and Ideas Discussions first.

## 标签 / Labels

| 标签 / Label | 用途 / Purpose |
| --- | --- |
| `bug` | 可复现的缺陷 / Reproducible defect |
| `enhancement` | 新功能或改进 / New feature or improvement |
| `compatibility` | 与其他模组、整合包或加载器冲突 / Conflict with another mod, modpack, or loader |
| `question` | 使用、安装或配置问题 / Usage, installation, or configuration question |
| `documentation` | 文档或翻译问题 / Documentation or translation issue |
| `area: source` | Java 源码 / Java source |
| `area: assets` | 材质、模型、声音、语言文件 / Textures, models, sounds, language files |
| `area: data` | 数据包、配方、标签、战利品表 / Data packs, recipes, tags, loot tables |
| `area: build` | Gradle、构建脚本、模组元数据 / Gradle, build scripts, mod metadata |
| `area: ci` | GitHub Actions 与仓库自动化 / GitHub Actions and repository automation |
| `translation` | 翻译与语言文件 / Translations and language files |

### PR 路径标签 / Path-based PR labels

`.github/labeler.yml` 会根据改动文件自动添加 `area:*`、`documentation` 和 `translation` 标签。工作流 `.github/workflows/labeler.yml` 使用 `pull_request_target`，因此 fork PR 也能被标记；它不 checkout PR 代码。

`.github/labeler.yml` adds `area:*`, `documentation`, and `translation` labels based on changed files. `.github/workflows/labeler.yml` uses `pull_request_target` so fork PRs are labeled too; it never checks out PR code.

## 自动加入看板 / Auto-add to project

`.github/workflows/add-to-project.yml` 在 Issue 或 PR 打开/重新打开时，将其加入 [MuQoLS Development](https://github.com/users/Mr-lhk/projects/6)。没有标签过滤，所有条目都会加入。

`.github/workflows/add-to-project.yml` adds every issue or pull request to the [MuQoLS Development](https://github.com/users/Mr-lhk/projects/6) board when it is opened or reopened. There is no label filter.

### 为什么需要 `PROJECT_TOKEN` / Why `PROJECT_TOKEN` is required

仓库自带的 `GITHUB_TOKEN` 只能访问当前仓库，不能访问用户级 Projects v2。`actions/add-to-project` 需要一个有 `project` 权限的 PAT。该 PAT 存放在 Actions secret 中，绝不能提交到仓库。

The repository-scoped `GITHUB_TOKEN` cannot access user-owned Projects v2. `actions/add-to-project` needs a PAT with the `project` scope, stored as an Actions secret. Never commit the token.

### 配置步骤 / Setup

1. 打开 <https://github.com/settings/tokens>，生成一个 **classic** PAT。
2. 勾选 `repo` 和 `project` 权限；设置合理的过期时间。
3. 打开仓库的 **Settings → Secrets and variables → Actions → New repository secret**。
4. 名称填 `PROJECT_TOKEN`，值填刚生成的 PAT。
5. 新建一个测试 Issue 或 PR 验证；工作流由 `issues` / `pull_request_target` 事件触发，当前没有 `workflow_dispatch` 手动入口。

1. Open <https://github.com/settings/tokens> and create a **classic** PAT.
2. Grant `repo` and `project` scopes, and set a reasonable expiration.
3. Open the repository's **Settings → Secrets and variables → Actions → New repository secret**.
4. Name it `PROJECT_TOKEN` and paste the PAT.
5. Trigger the workflow with a test issue or PR to verify it.

> 细粒度 PAT 对用户级 Project 的支持可能不完整；如果遇到权限错误，请改用 classic PAT。
> Fine-grained PATs may not fully support user-owned projects. Use a classic PAT if you hit permission errors.

### 已有条目 / Existing items

自动添加只处理新打开或重新打开的条目。已有 Issue/PR 可以批量添加到看板：

Auto-add only handles newly opened or reopened items. Add existing items in bulk with:

```bash
gh project item-add 6 --owner Mr-lhk --url https://github.com/Mr-lhk/MoreUsefulQuality-of-LifeStuff/issues/5
```

也可以在 Project 页面使用 **Add items** 搜索并批量添加。

You can also use **Add items** on the project page to search and add in bulk.

## 项目内置工作流 / Built-in project workflows

项目已启用以下内置工作流（在 **Project → Workflows** 中查看）：

The following built-in workflows are enabled (see **Project → Workflows**):

- Item closed
- Pull request merged
- Pull request linked to issue
- Item added to project
- Auto-add sub-issues to project
- Auto-close issue

请在 UI 中确认 **Item closed** 和 **Pull request merged** 都把 Status 设为 **Done**。如果默认行为不同，手动设置一次即可。

Confirm in the UI that **Item closed** and **Pull request merged** set Status to **Done**. If they do not, set that mapping once.

## 维护约定 / Maintenance notes

- Issue 表单的 `labels` 必须对应仓库中已存在的标签；新增表单时先创建标签。Labels referenced by issue forms must already exist; create labels before adding a form.
- 修改 `.github/labeler.yml` 后，在下一个 PR 事件中生效。Changes to `.github/labeler.yml` take effect on the next PR event.
- 不要给 `pull_request_target` 工作流添加 checkout PR 代码的步骤，避免在拥有 secret 的上下文中执行不可信代码。Never add a step that checks out PR code to a `pull_request_target` workflow; that would run untrusted code with secrets.
- `PROJECT_TOKEN` 到期或轮换后，更新仓库 secret。Update the repository secret when `PROJECT_TOKEN` expires or is rotated.
