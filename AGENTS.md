# Project Rules for Codex

- For any GitHub-related task (creating/viewing PRs, listing/reading issues, assigning, labeling, comments, reviews):
  **always use GitHub CLI (`gh`)** from the terminal.
- Prefer these commands:
  - Create PR: `gh pr create --fill` (or with flags as needed)
  - View PR: `gh pr view --web` or `gh pr view <num> --json ...`
  - List Issues: `gh issue list --label ... --assignee ... --state open`
  - View Issue: `gh issue view <num> --json ...`
- Do not call GitHub REST/GraphQL directly; shell via `gh` is the source of truth.
- If auth is needed: `gh auth status` then `gh auth login`.
- Never merge a PR yourself.