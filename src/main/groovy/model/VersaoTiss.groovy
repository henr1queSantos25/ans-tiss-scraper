package model

class VersaoTiss {
    String competencia
    String publicacao
    String inicioVigencia

    @Override
    String toString() {
        return "${competencia};${publicacao};${inicioVigencia}"
    }
}
