package repository

import model.Interessado

class EmailRepository {
    private final String FILE_PATH = "interessados.csv"


    void salvar(Interessado interessado) {
        File file = new File(FILE_PATH)
        if (!file.exists()) {
            file.write("email\n")
        }

        List<String> existentes = listarTodos().collect { it.email }
        if (!existentes.contains(interessado.email)) {
            file.append("${interessado.email}\n")
            println("E-mail ${interessado.email} adicionado com sucesso.")
        }
    }


    List<Interessado> listarTodos() {
        File file = new File(FILE_PATH)
        if (!file.exists()) {
            return []
        }

        List<Interessado> lista = []
        file.eachLine(1) { line ->
            if (line.trim()) {
                lista.add(new Interessado(email: line.trim()))
            }
        }
        return lista
    }


    void remover(String email) {
        List<Interessado> atuais = listarTodos()
        List<Interessado> filtrados = atuais.findAll { it.email != email }

        File file = new File(FILE_PATH)
        file.write("email\n")
        filtrados.each { file.append("${it.email}\n") }
        println("E-mail ${email} removido se existia.")
    }
}
