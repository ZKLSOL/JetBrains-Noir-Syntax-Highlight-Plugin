package com.ohaddahan.noir.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.platform.lsp.api.LspServerManager

class RestartLspAction : AnAction("Restart Noir Language Server") {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return

        @Suppress("UnstableApiUsage")
        LspServerManager.getInstance(project).stopAndRestartIfNeeded(
            com.ohaddahan.noir.lsp.NoirLspServerSupportProvider::class.java
        )
    }

    override fun update(e: AnActionEvent) {
        e.presentation.isEnabledAndVisible = e.project != null
    }
}
