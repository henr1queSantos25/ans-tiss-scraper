package service

import jakarta.mail.*
import jakarta.mail.internet.*
import model.Interessado

class NotificationService {

    private final String remetente
    private final String senha
    private final String host = "smtp.gmail.com"
    private final String porta = "587"

    NotificationService(String remetente, String senha) {
        this.remetente = remetente
        this.senha = senha
    }

    void enviarRelatorioComAnexos(List<Interessado> interessados, String diretorioArquivos) {
        if (interessados.isEmpty()) {
            println("Aviso: Nenhum e-mail cadastrado na lista de interessados. O envio foi cancelado.")
            return
        }

        println("\n--- Iniciando o envio de e-mails para ${interessados.size()} destinatário(s) ---")

        // Configurações do servidor SMTP
        Properties props = new Properties()
        props.put("mail.smtp.auth", "true")
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.host", host)
        props.put("mail.smtp.port", porta)

        // Autenticação
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(remetente, senha)
            }
        })

        try {
            Message message = new MimeMessage(session)
            message.setFrom(new InternetAddress(remetente))

            Address[] destinatarios = interessados.collect { new InternetAddress(it.email) } as Address[]
            message.setRecipients(Message.RecipientType.BCC, destinatarios)

            message.setSubject("Relatório Automático - Arquivos Padrão TISS (ANS)")

            BodyPart textoPart = new MimeBodyPart()
            textoPart.setText("""
                Olá!
                      
                Esta é uma mensagem automática.
                O Crawler finalizou a execução e extraiu os dados mais recentes do site da ANS.
                
                Segue em anexo:
                1. Componente de Comunicação (.zip)
                2. Histórico de Versões filtrado (.csv)
                3. Tabela de Erros de Envio (.xlsx)
                """.trim())

            Multipart multipart = new MimeMultipart()
            multipart.addBodyPart(textoPart)

            anexarArquivo(multipart, "${diretorioArquivos}/Componente_de_Comunicacao.zip")
            anexarArquivo(multipart, "${diretorioArquivos}/historico_versoes_tiss.csv")
            anexarArquivo(multipart, "${diretorioArquivos}/Tabela_de_Erros_ANS.xlsx")

            message.setContent(multipart)

            Transport.send(message)
            println("E-mails enviados com sucesso!")
        } catch (Exception e) {
            System.err.println("Erro crítico ao tentar enviar o e-mail: ${e.message}")
            e.printStackTrace()
        }
    }


    private void anexarArquivo(Multipart multipart, String caminhoArquivo) {
        File file = new File(caminhoArquivo)
        if (file.exists()) {
            MimeBodyPart attachPart = new MimeBodyPart()
            attachPart.attachFile(file)
            multipart.addBodyPart(attachPart)
            println("Anexado: ${file.name}")
        } else {
            println("AVISO: Arquivo não encontrado para anexo -> ${caminhoArquivo}")
        }
    }
}
