package eapli.base.smm;

import java.util.ArrayList;
import java.util.List;

public class ListaMaquinas {

    private List<Integer> listaMaquinas = new ArrayList<>();

    public synchronized boolean adicionarMaquina(int maquina) {
        if (listaMaquinas.contains(maquina)) {
            return false;
        }
        listaMaquinas.add(maquina);
        return true;
    }

    public int getListaMaquinasSize() {
        return listaMaquinas.size();
    }

    public List<Integer> getListaMaquinas() {
        return listaMaquinas;
    }

}
