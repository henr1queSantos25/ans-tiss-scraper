package view

import model.Interessado
import service.AnsScraperService
import repository.EmailRepository
import service.NotificationService

class Menu {
    private final Scanner scanner
    private final AnsScraperService scraper
    private final EmailRepository repository

    Menu() {
        this.scanner = new Scanner(System.in)
        this.scraper = new AnsScraperService()
        this.repository = new EmailRepository()
    }

    void iniciar() {
        boolean rodando = true

        while (rodando) {
            println("\n========================================")
            println("    ANS TISS Scraper - Menu Principal")
            println("========================================")
            println("1. Executar Crawler (Baixar dados da ANS)")
            println("2. Gerenciar E-mails Interessados")
            println("3. Enviar Relatórios por E-mail")
            println("0. Sair")
            print("Escolha uma opção: ")

            String opcao = scanner.nextLine().trim()

            switch (opcao) {
                case "1":
                    executarCrawler()
                    break
                case "2":
                    menuGerenciarEmails()
                    break
                case "3":
                    enviarRelatorios()
                    break
                case "0":
                    println("Encerrando a aplicação. Até logo!")
                    rodando = false
                    break
                default:
                    println("Opção inválida! Tente novamente.")
            }
        }
    }

    private void executarCrawler() {
        println("\nIniciando a extração de dados...")
        try {
            scraper.executeTask1()
            scraper.executeTask2()
            scraper.executeTask3()
            println("\nExtração concluída com sucesso! Arquivos salvos na pasta Downloads.")
        } catch (Exception e) {
            println("\nErro durante a execução do Crawler: ${e.message}")
        }
    }

    private void menuGerenciarEmails() {
        boolean noSubmenu = true
        while (noSubmenu) {
            println ("\n--- Gerenciar Interessados ---")
            println ("1. Listar E-mails")
            println ("2. Adicionar Novo E-mail")
            println ("3. Remover E-mail")
            println ("0. Voltar ao Menu Principal")
            print ("Escolha uma opção: ")

            String opcao = scanner.nextLine().trim()

            switch (opcao) {
                case "1":
                    List<Interessado> lista = repository.listarTodos()
                    if (lista.isEmpty()) {
                        println("Nenhum e-mail cadastrado.")
                    } else {
                        println("\nLista atual:")
                        lista.each { println "- ${it.email}" }
                    }
                    break
                case "2":
                    print("Digite o e-mail a ser adicionado: ")
                    String novoEmail = scanner.nextLine().trim()
                    if (novoEmail) {
                        repository.salvar(new Interessado(email: novoEmail))
                    } else {
                        println("E-mail não pode ser vazio.")
                    }
                    break
                case "3":
                    print("Digite o e-mail a ser removido: ")
                    String removerEmail = scanner.nextLine().trim()
                    if (removerEmail) {
                        repository.remover(removerEmail)
                    }
                    break
                case "0":
                    noSubmenu = false
                    break
                default:
                    println("Opção inválida!")
            }
        }
    }

    private void enviarRelatorios() {
        if (!scraper.todosArquivosExistem()) {
            println("\n[ERRO] Não é possível enviar o e-mail: Arquivos não encontrados.")
            println("Por favor, execute a opção '1' (Crawler) primeiro para baixar os dados da ANS.")
            return
        }

        List<Interessado> lista = repository.listarTodos()
        if (lista.isEmpty()) {
            println("\nAviso: Cadastre pelo menos um e-mail interessado antes de enviar.")
            return
        }

        println("\n--- Envio de Relatórios ---")
        print("Digite o seu e-mail remetente (ex: seuemail@gmail.com): ")
        String remetente = scanner.nextLine().trim()

        print("Digite a sua Senha de Aplicativo: ")
        String senha = scanner.nextLine().trim()

        if (!remetente || !senha) {
            println("Operação cancelada: E-mail e senha são obrigatórios.")
            return
        }

        String diretorioDownloads = "./Downloads/Arquivos_padrao_TISS"
        NotificationService notifier = new NotificationService(remetente, senha)

        notifier.enviarRelatorioComAnexos(lista, diretorioDownloads)
    }
}
