package service

import org.jsoup.nodes.Document
import org.jsoup.nodes.Element

class AnsScraperService {

    private final WebClientService webClient
    private final FileService fileService
    private final String BASE_URL = "https://www.gov.br/ans/pt-br"

    AnsScraperService() {
        this.webClient = new WebClientService()
        this.fileService = new FileService()
    }

    void executeTask1() {
        println("--- Iniciando Task 1: Busca do Componente de Comunicação ---")

        Document doc = webClient.getHtmlDocument(BASE_URL)

        String prestadorUrl = extractUrlByText(doc, "Espaço do Prestador de Serviços de Saúde")
        doc = webClient.getHtmlDocument(prestadorUrl)

        String tissUrl = extractUrlByText(doc, "TISS - Padrão para Troca de Informação de Saúde Suplementar")
        doc = webClient.getHtmlDocument(tissUrl)

        String latestVersionUrl = extractUrlByText(doc, "Clique aqui para acessar a versão")
        doc = webClient.getHtmlDocument(latestVersionUrl)

        Element communicationRow = doc.select("tr:contains(Componente de Comunicação)").first()

        if (communicationRow != null) {
            Element downloadLinkElement = communicationRow.select("a").first()
            String fileUrl = resolveAbsoluteUrl(downloadLinkElement.attr("href"))

            String destinationDir = "./Downloads/Arquivos_padrao_TISS"
            String fileName = "Componente_de_Comunicacao.zip"

            fileService.downloadFile(fileUrl, destinationDir, fileName)
        } else {
            println "Erro: Linha do Componente de Comunicação não encontrada na tabela."
        }
    }


    private String extractUrlByText(Document doc, String searchText) {
        Element linkElement = doc.select("a:contains(${searchText})").first()
        if (linkElement == null) {
            throw new RuntimeException("Link com o texto '${searchText}' não encontrado!")
        }
        return resolveAbsoluteUrl(linkElement.attr("href"))
    }


    private String resolveAbsoluteUrl(String url) {
        if (url.startsWith("/")) {
            return "https://www.gov.br" + url
        }
        return url
    }
}
