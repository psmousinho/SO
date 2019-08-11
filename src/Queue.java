/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pablo Suria
 */
public class Queue {

    protected Process[] dados;
    protected int inicio;
    protected int fim;
    protected int nElem;

    public Queue(int tam) {
        dados = new Process[tam];
        inicio = 0;
        fim = -1;
        nElem = 0;
    }

    public Queue() {
        this(100);
    }

    public Queue(Queue original) {
        this(original.maxSize());
        for (Process p : original.dados) {
            this.insert(p);
        }
        this.inicio = original.inicio;
        this.fim = original.fim;
        this.nElem = original.nElem;
    }

    public boolean empty() {
        return nElem == 0;
    }

    public boolean full() {
        return nElem == dados.length;
    }

    public int size() {
        return nElem;
    }

    public int maxSize() {
        return dados.length;
    }

    public boolean insert(Process value) {
        if (full()) {
            return false;
        }

        fim = (fim + 1) % dados.length;
        dados[fim] = value;
        nElem++;
        return true;
    }

    public Process remove() {
        if (empty()) {
            return null;
        }

        Process removido = dados[inicio];
        dados[inicio] = null;
        inicio = (inicio + 1) % dados.length;
        nElem--;
        return removido;
    }

    public Process consult() {
        if (empty()) {
            return null;
        }

        return dados[inicio];
    }

    //Adaptacoes:
    public Process[] getArray() {
        return dados;
    }

    public int getInicio() {
        return inicio;
    }

    public int getFinal() {
        return fim;
    }
}
