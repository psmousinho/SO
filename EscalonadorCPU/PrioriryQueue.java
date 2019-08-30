/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pablo Suria
 */
public class PrioriryQueue extends Queue {

    @Override
    public boolean insert(Process value) {
        if (full()) {
            return false;
        }

        this.fim = (fim + 1) % dados.length;
        dados[fim] = value;
        nElem++;

        //encontrando a posicao;
        int i = fim;
        while (i - 1 >= inicio && dados[i].getRemainingDuration() < dados[i - 1].getRemainingDuration()) {
            Process aux = dados[i];
            dados[i] = dados[i - 1];
            dados[i - 1] = aux;
            i--;
        }

        return true;
    }

}
