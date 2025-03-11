package service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificacaoService {

    private final Map<EventoEnum, List<EventListener>> listeners = new HashMap<>(){{
        put(EventoEnum.LIMPAR_ESPACO, new ArrayList<>());
    }};

    public void subscribe(final EventoEnum tipoEvento, EventListener listener){
        var selectedListeners = listeners.get(tipoEvento);
        selectedListeners.add(listener);
    }
    public void notify(final EventoEnum tipoEvento){
        listeners.get(tipoEvento).forEach(l -> l.update(tipoEvento));
    }

}
