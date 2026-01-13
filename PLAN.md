# Noir JetBrains Plugin Specification

## Overview

A JetBrains IDE plugin providing syntax highlighting and LSP integration for the Noir zero-knowledge programming language. The plugin targets all JetBrains IDEs based on IntelliJ Platform 2023.2+.

**Plugin ID:** `com.ohaddahan.noir`
**Display Name:** Noir
**Initial Version:** 0.1.0
**License:** MIT

## Scope

### Included Features
- Syntax highlighting via TextMate grammar (ported from VSCode extension)
- LSP integration with `nargo lsp` for IDE intelligence
- Live Templates (code snippets) matching VSCode prefixes
- Macro expansion viewer (`nargo expand`)
- Stdlib source viewer (`noir-std://` URI scheme)
- Gutter icons for `#[test]` functions (visual indicator only)
- Settings for nargo path configuration

### Explicitly Excluded (Future Scope)
- Debugger (DAP) integration
- Test runner integration
- Run configurations for nargo commands
- Profiling/opcode visualization
- Nargo.toml schema validation

## Technical Stack

| Component | Choice |
|-----------|--------|
| Language | Kotlin |
| Build System | Gradle with Kotlin DSL |
| LSP API | JetBrains native LSP API (2023.2+) |
| Lexer | TextMate bundle (port of noir.tmLanguage.json) |
| Color Scheme | Standard TextMate mapping (no custom attributes) |
| LSP Transport | stdio only |

## Target Platforms

- **Minimum IDE Version:** 2023.2
- **Supported IDEs:** All JetBrains IDEs (IntelliJ IDEA, CLion, RustRover, WebStorm, PyCharm, GoLand, etc.)
- **Nargo Compatibility:** Best effort with any version (no minimum enforced)

## File Associations

| Extension | File Type |
|-----------|-----------|
| `.nr` | Noir Source File |

## Features Detail

### 1. Syntax Highlighting

Port the TextMate grammar from VSCode extension (`noir.tmLanguage.json`).

**Supported Constructs:**
- Keywords: `fn`, `impl`, `trait`, `type`, `mod`, `use`, `struct`, `if`, `else`, `for`, `loop`, `while`, `enum`, `match`, `break`, `continue`
- Modifiers: `global`, `comptime`, `quote`, `unsafe`, `unconstrained`, `pub`, `crate`, `mut`, `let`
- Built-in types: `u8`-`u128`, `i8`-`i128`, `str`, `bool`, `field`, `Field`
- Comments: `//` line comments, `/* */` block comments (with nesting)
- Strings: double-quoted, raw strings (`r#"..."#`), f-strings (`f"...{expr}..."`)
- Numeric literals: decimal, hexadecimal, boolean
- Attributes: `#[...]`
- Generics: `<Type>` handling

### 2. Language Configuration

**Auto-closing pairs:**
- `{` → `}`
- `[` → `]`
- `(` → `)`
- `"` → `"`

**Bracket matching:**
- `{}`, `[]`, `()`

**Comment toggling:**
- Line: `//`
- Block: `/* */`

### 3. LSP Integration

Connect to `nargo lsp` via stdio transport.

**Enabled Features:**
- Completions (autocomplete)
- Diagnostics (errors/warnings)
- Hover information (type info, documentation)
- Go to Definition
- Find References
- Signature Help
- Code Actions

**Settings (simplified):**
- Enable/Disable LSP (boolean)
- Lightweight Mode (disables completions, signature help, code actions)

### 4. Live Templates (Snippets)

Port all 12 snippets with identical prefixes:

| Prefix | Expansion |
|--------|-----------|
| `mod` | `mod ${name};` |
| `use` | `use ${name};` |
| `fn` | `fn ${name}() { $END$ }` |
| `fnmain` | `fn main() { $END$ }` |
| `struct` | `struct ${name} { $END$ }` |
| `let` | `let ${variable} = $END$;` |
| `letfor` | `let ${array} = for ${start}..${end} { $END$ };` |
| `letforin` | `let ${array} = for ${index} in ${start}..${end} { $END$ };` |
| `for` | `for ${start}..${end} { $END$ }` |
| `forin` | `for ${index} in ${start}..${end} { $END$ }` |
| `if` | `if ${condition} { $END$ }` |
| `elseif` | `else if ${condition} { $END$ }` |
| `else` | `else { $END$ }` |

### 5. Macro Expansion

**Action:** "Expand Macros" (available via Tools menu + Find Action)
**Behavior:** Runs `nargo expand` on current file/package and opens result in a scratch file
**Output:** Read-only scratch file with expanded Noir code

### 6. Stdlib Viewer

**Purpose:** Allow navigation to Noir standard library source code
**Implementation:** Virtual file system provider for `noir-std://` URIs
**Behavior:** When LSP returns stdlib location, display actual source (read-only)

### 7. Gutter Icons

**Scope:** Functions annotated with `#[test]`
**Icon:** Test tube or similar indicator
**Behavior:** Visual indicator only (no click action)

### 8. Settings

**Location:** Settings → Languages & Frameworks → Noir

**Application-level (global default):**
- Nargo executable path (auto-detect from PATH if empty)
- Enable LSP (default: true)
- Lightweight mode (default: false)

**Project-level (override):**
- Nargo executable path (overrides global)

### 9. Actions

**Accessible via:** Tools menu + Find Action (Ctrl/Cmd+Shift+A)

| Action | Description |
|--------|-------------|
| Restart Noir Language Server | Stops and restarts the LSP connection |
| Expand Macros | Runs nargo expand on current context |

### 10. Error Handling

**LSP Failure Behavior:**
- Silent degradation (syntax highlighting continues)
- LSP features quietly disabled
- Status shown in IDE tool window only (no popup notifications)
- Manual restart available via action

**Nargo Not Found:**
- Log warning
- Disable LSP features
- Continue with syntax highlighting only

## Project Structure

```
JetBrains-Noir-Syntax-Highlight-Plugin/
├── .github/
│   └── workflows/
│       ├── build.yml          # CI build and test
│       └── release.yml        # Marketplace publishing
├── src/
│   └── main/
│       ├── kotlin/
│       │   └── com/ohaddahan/noir/
│       │       ├── NoirLanguage.kt
│       │       ├── NoirFileType.kt
│       │       ├── NoirIcons.kt
│       │       ├── lsp/
│       │       │   ├── NoirLspServerSupportProvider.kt
│       │       │   └── NoirLspServerDescriptor.kt
│       │       ├── settings/
│       │       │   ├── NoirSettings.kt
│       │       │   ├── NoirSettingsConfigurable.kt
│       │       │   └── NoirProjectSettings.kt
│       │       ├── actions/
│       │       │   ├── RestartLspAction.kt
│       │       │   └── ExpandMacrosAction.kt
│       │       ├── annotator/
│       │       │   └── NoirTestAnnotator.kt
│       │       └── vfs/
│       │           └── NoirStdlibFileSystem.kt
│       └── resources/
│           ├── META-INF/
│           │   └── plugin.xml
│           ├── syntaxes/
│           │   └── noir.tmLanguage.json
│           ├── liveTemplates/
│           │   └── Noir.xml
│           └── icons/
│               ├── noir.svg
│               └── test.svg
├── src/
│   └── test/
│       └── kotlin/
│           └── com/ohaddahan/noir/
│               ├── NoirFileTypeTest.kt
│               └── NoirSettingsTest.kt
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── LICENSE
├── README.md
└── CHANGELOG.md
```

## Build & Release

### Development
```bash
./gradlew buildPlugin      # Build plugin zip
./gradlew runIde           # Run sandbox IDE with plugin
./gradlew test             # Run unit tests
```

### CI/CD Pipeline

**On Pull Request:**
1. Build plugin
2. Run unit tests
3. Verify compatibility with target IDE versions

**On Release Tag:**
1. Build plugin
2. Run tests
3. Sign plugin
4. Publish to JetBrains Marketplace

### Marketplace Requirements
- JetBrains Hub account
- Marketplace vendor registration
- Plugin signing certificate (generated via Marketplace)
- `PUBLISH_TOKEN` secret in GitHub repository

## Testing Strategy

**Unit Tests:**
- File type recognition (`.nr` files)
- Settings persistence (application and project level)
- Icon resolution

**Manual Testing Checklist:**
- [ ] Syntax highlighting for all Noir constructs
- [ ] LSP connection establishment
- [ ] Completions appear on typing
- [ ] Errors show inline diagnostics
- [ ] Go to definition works
- [ ] Hover shows type information
- [ ] Live templates expand correctly
- [ ] Expand Macros action works
- [ ] Stdlib navigation works
- [ ] Test gutter icons appear
- [ ] Settings UI functions
- [ ] Restart LSP action works

## Implementation Phases

### Phase 1: Foundation
- Project setup with Gradle Kotlin DSL
- Basic plugin.xml configuration
- File type and language registration
- TextMate grammar integration

### Phase 2: LSP Integration
- LSP server descriptor and support provider
- Settings for nargo path (app + project level)
- Silent failure handling

### Phase 3: Enhanced Features
- Live templates
- Expand macros action
- Stdlib virtual file system
- Test function gutter icons

### Phase 4: Polish & Release
- Unit tests
- CI/CD pipelines
- Documentation (README, CHANGELOG)
- Marketplace submission

## Dependencies

**Gradle Plugins:**
- `org.jetbrains.intellij` (IntelliJ Platform Gradle Plugin)
- `org.jetbrains.kotlin.jvm`

**Platform Dependencies:**
- IntelliJ Platform SDK 2023.2+
- TextMate Bundles support (bundled)
- LSP API (bundled in 2023.2+)

## References

- VSCode Noir Extension: `/Users/ohaddahan/RustroverProjects/zklsol/solana-privacy-hackathon/vscode-noir`
- [IntelliJ Platform SDK Docs](https://plugins.jetbrains.com/docs/intellij/)
- [JetBrains LSP API](https://plugins.jetbrains.com/docs/intellij/language-server-protocol.html)
- [TextMate Bundles in IntelliJ](https://plugins.jetbrains.com/docs/intellij/textmate.html)
- [Noir Language](https://noir-lang.org/)
