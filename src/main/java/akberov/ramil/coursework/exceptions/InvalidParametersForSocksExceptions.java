package akberov.ramil.coursework.exceptions;

public class InvalidParametersForSocksExceptions extends RuntimeException {

    @Override
    public String getMessage() {
        return "Введенные Вами данные недостаточны или они неверные. Проверьте все параметры носков";
    }
}
