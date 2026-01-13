<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# Noir JetBrains Plugin Changelog

## [Unreleased]

## [0.1.0]
### Added
- Syntax highlighting for Noir (.nr) files via TextMate grammar
- LSP integration with `nargo lsp` for code intelligence
  - Code completion
  - Diagnostics (errors and warnings)
  - Go to definition
  - Hover information
  - Find references
  - Signature help
  - Code actions
- Live templates for common Noir constructs
  - `fn`, `fnmain` - Function declarations
  - `struct` - Struct declaration
  - `let` - Variable declaration
  - `for`, `forin` - For loops
  - `if`, `elseif`, `else` - Conditionals
  - `mod`, `use` - Module declarations
  - `letfor`, `letforin` - Array comprehensions
- Settings panel for configuring nargo path
  - Application-level settings (global)
  - Project-level settings (override)
  - Auto-detection from PATH
- Actions in Tools menu
  - Restart Language Server
  - Expand Macros (opens in scratch file)
- Comment toggling support (// and /* */)
- Bracket matching

### Notes
- Requires JetBrains IDE 2023.2 or later
- Requires nargo installed for LSP features
