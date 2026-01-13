# Noir JetBrains Plugin - TODO

## Status: v0.1.0 Released

The core plugin is complete and ready for use.

## Completed Features

### Core
- [x] File type recognition for `.nr` files
- [x] Language registration (NoirLanguage)
- [x] Plugin icons (noir.svg, test.svg)
- [x] Comment toggling (`//` and `/* */`)
- [x] Bracket matching

### LSP Integration
- [x] LSP client connecting to `nargo lsp`
- [x] Code completion
- [x] Diagnostics (errors/warnings)
- [x] Go to definition
- [x] Hover information
- [x] Find references
- [x] Signature help
- [x] Code actions
- [x] Auto-detection of nargo from PATH
- [x] Silent failure handling

### Settings
- [x] Application-level settings (global nargo path, enable LSP, lightweight mode)
- [x] Project-level settings (nargo path override)
- [x] Settings UI at Settings → Languages & Frameworks → Noir

### Live Templates
- [x] `fn`, `fnmain` - Function declarations
- [x] `struct` - Struct declaration
- [x] `let` - Variable declaration
- [x] `for`, `forin` - For loops
- [x] `if`, `elseif`, `else` - Conditionals
- [x] `mod`, `use` - Module declarations
- [x] `letfor`, `letforin` - Array comprehensions

### Actions
- [x] Restart Language Server (Tools → Noir → Restart Language Server)
- [x] Expand Macros (Tools → Noir → Expand Macros)

### Documentation & CI/CD
- [x] README.md
- [x] CHANGELOG.md
- [x] LICENSE (MIT)
- [x] GitHub Actions workflows (build, release)

### Syntax Highlighting
- [x] Native lexer (NoirLexer.kt)
- [x] Token types and syntax highlighter
- [x] Keywords, types, strings, comments, numbers, attributes

## Future Enhancements

### Stdlib Viewer
- [ ] Virtual file system for `noir-std://` URIs
- [ ] Navigate to stdlib source code

### Gutter Icons
- [ ] Test indicator icons for `#[test]` functions
- [ ] Icon already created (test.svg)

### Testing
- [ ] Unit tests for file type recognition
- [ ] Unit tests for settings persistence

### Marketplace
- [ ] JetBrains Marketplace submission
- [ ] Plugin signing setup
- [ ] Screenshots for marketplace listing
