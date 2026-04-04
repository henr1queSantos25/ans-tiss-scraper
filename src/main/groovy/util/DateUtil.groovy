package util

import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class DateUtil {

    private static final YearMonth DATA_LIMITE = YearMonth.of(2016, 1)

    private static final DateTimeFormatter FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendText(ChronoField.MONTH_OF_YEAR, getMesesMap())
            .appendLiteral('/')
            .appendValue(ChronoField.YEAR, 4)
            .toFormatter()


    private static Map<Long, String> getMesesMap() {
        Map<Long, String> map = new HashMap<>()
        map.put(1L, "jan")
        map.put(2L, "fev")
        map.put(3L, "mar")
        map.put(4L, "abr")
        map.put(5L, "mai")
        map.put(6L, "jun")
        map.put(7L, "jul")
        map.put(8L, "ago")
        map.put(9L, "set")
        map.put(10L, "out")
        map.put(11L, "nov")
        map.put(12L, "dez")
        return map
    }


    static boolean isCompetenciaValida(String competenciaStr) {
        try {
            String cleanStr = competenciaStr.trim().toLowerCase()
            YearMonth competenciaDate = YearMonth.parse(cleanStr, FORMATTER)

            return !competenciaDate.isBefore(DATA_LIMITE)
        } catch (Exception e) {
            println("Aviso: Não foi possível parsear a data '${competenciaStr}'. Ignorando.")
            return false
        }
    }
}
