package service

import groovyx.net.http.HttpBuilder
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

class WebClientService {

    Document getHtmlDocument(String url) {
        println("Acessando URL: ${url}")

        try {
            String htmlContent = HttpBuilder.configure {
                request.uri = url
            }.get()

            return Jsoup.parse(htmlContent, url)
        } catch (Exception e) {
            System.err.println("Erro ao acessar a URL ${url}: ${e.message}")
            throw e
        }
    }
}
