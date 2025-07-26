package dev.forcetower.oversee.module.aeri

import dev.forcetower.oversee.extension.appendStart
import dev.forcetower.oversee.extension.asDocument
import dev.forcetower.oversee.model.NewsMessage
import dev.forcetower.oversee.module.Module
import dev.forcetower.oversee.networking.OverCalls
import dev.forcetower.oversee.networking.OverRequests
import org.jsoup.nodes.Document
import java.util.logging.Level
import java.util.logging.Logger

class AERIModule : Module<List<NewsMessage>> {
    override suspend fun execute(): List<NewsMessage> {
        val document = connect()
        return process(document)
    }

    private suspend fun connect(): Document {
        val call = OverCalls.aeri
        val result = call.executeSuspend()
        val response = result.body!!.string()
        return response.asDocument()
    }

    private fun process(document: Document): List<NewsMessage> {
        val articles = document.select("article[class=\"noticias\"]")
        return articles.mapNotNull { article ->
            try {
                val content = article.selectFirst("div[class=\"text\"]") ?: return@mapNotNull null
                val title = content.selectFirst("h2[class=\"titulo\"]")?.text() ?: return@mapNotNull null
                val link = content.selectFirst("a[class=\"bt-mais\"]")?.attr("href")?.appendStart(OverRequests.AERI_PAGE) ?: return@mapNotNull null
                val date = content.selectFirst("span[class=\"data\"]")?.text() ?: return@mapNotNull null
                val image = article.selectFirst("figure")?.selectFirst("img")?.attr("src")?.appendStart(OverRequests.AERI_PAGE)
                NewsMessage(title, link, image, date)
            } catch (error: Throwable) {
                Logger.getLogger("timber").log(Level.SEVERE, "something smells fishy", error)
                null
            }
        }
    }
}