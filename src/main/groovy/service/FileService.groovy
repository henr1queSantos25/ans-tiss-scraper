package service

import model.VersaoTiss
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

class FileService {

    void downloadFile(String fileUrl, String directoryPath, String fileName) {
        println("Iniciando download do arquivo: ${fileName}")

        try {
            File directory = new File(directoryPath)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            def destinationPath = Paths.get(directoryPath, fileName)

            URLConnection connection = new URL(fileUrl).openConnection()
            connection.setRequestProperty("User-Agent", "Mozilla/5.0")

            InputStream inputStream = connection.getInputStream()
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING)
            inputStream.close()

            println("Download concluído com sucesso em: ${destinationPath.toString()}")
        } catch (Exception e) {
            System.err.println("Erro ao baixar o arquivo ${fileName}: ${e.message}")
            throw e
        }
    }


    void salvarDadosEmCsv(List<VersaoTiss> versoes, String directoryPath, String fileName) {
        try {
            File directory = new File(directoryPath)
            if (!directory.exists()) {
                directory.mkdirs()
            }

            File arquivoCsv = new File(directory, fileName)

            arquivoCsv.withWriter('UTF-8') { writer ->
                writer.writeLine("Competencia;Publicacao;Inicio_Vigencia")
                versoes.each { versao ->
                    writer.writeLine(versao.toString())
                }
            }

            println("Arquivo CSV salvo com sucesso (${versoes.size()} registros) em: ${arquivoCsv.absolutePath}")
        } catch (Exception e) {
            System.err.println("Erro ao salvar o arquivo ${fileName}: ${e.message}")
            throw e
        }
    }
}
