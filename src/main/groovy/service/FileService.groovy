package service

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
}
