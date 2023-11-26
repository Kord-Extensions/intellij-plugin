package dev.kordex.ide.intellij.markers

import com.intellij.codeInsight.daemon.LineMarkerInfo
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder
import com.intellij.icons.AllIcons
import com.intellij.psi.PsiVariable
import com.kotlindiscord.kord.extensions.tooling.TranslatableType

class LocaleLineMarkerProvider : TranslationLineMarkerProvider() {
	override val type: TranslatableType = TranslatableType.LOCALE

	override fun getMarkerInfo(reference: PsiVariable): LineMarkerInfo<*> =
		NavigationGutterIconBuilder.create(AllIcons.Actions.InlayGlobe)
			.setTooltipText("Locale!")
			.createLineMarkerInfo(reference)

	override fun getName(): String =
		"Translations locale"
}
