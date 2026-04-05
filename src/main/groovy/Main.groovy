import service.AnsScraperService

class Main {
    static void main(String[] args) {
        println ("========================================")
        println ("    Iniciando ANS TISS Scraper")
        println ("========================================\n")

        try {
            AnsScraperService scraper = new AnsScraperService()
            scraper.executeTask1()
            scraper.executeTask2()
            scraper.executeTask3()

        } catch (Exception e) {
            System.err.println("A execução falhou: ${e.message}")
            e.printStackTrace()
        }
    }
}
