package nastya.ru.badge.keeper.util.exception;

import java.util.UUID;

public class IdNotFoundException extends BusinessLogicException{
    public IdNotFoundException(String paramName, UUID id) {
        super("В базе данных нет %s с id=%s ".formatted(paramName, id));
    }
}