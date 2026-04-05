import view.Menu

class Main {
    static void main(String[] args) {
        try {
            Menu menu = new Menu()
            menu.iniciar()
        } catch (Exception e) {
            System.err.println("Erro crítico na aplicação: ${e.message}")
            e.printStackTrace()
        }
    }
}
