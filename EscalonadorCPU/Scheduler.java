/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pablo Suria
 */
public class Scheduler {

    public static void executeFifo(Queue processQueue) {
        int count = 0;
        int waitingSum = 0;
        int responseSum = 0;
        int returnSum = 0;

        int currentTime = 0;
        Process currentProcess = null;

        while (!processQueue.empty()) {
            currentProcess = processQueue.remove();

            if (currentTime < currentProcess.getInitialArrivalTime()) {
                currentTime = currentProcess.getInitialArrivalTime();
            }

            currentProcess.setResponseTime(currentTime - currentProcess.getInitialArrivalTime());

            currentProcess.setWaitingTime(currentProcess.getResponseTime());

            currentProcess.setReturnTime(currentProcess.getWaitingTime()
                    + currentProcess.getRemainingDuration());

            currentTime += currentProcess.getRemainingDuration();

            count++;
            waitingSum += currentProcess.getWaitingTime();
            responseSum += currentProcess.getResponseTime();
            returnSum += currentProcess.getReturnTime();

        }
        System.out.printf("FIFO %.1f %.1f %.1f\n", (float) returnSum / count, (float) responseSum / count, (float) waitingSum / count);
    }

    public static void executeSJF(Queue processQueue) {
        int count = 0;
        int waitingSum = 0;
        int responseSum = 0;
        int returnSum = 0;

        int currentTime = 0;
        Queue activeProceses = new PrioriryQueue();
        Process currentProcess = null;

        while (!processQueue.empty() || !activeProceses.empty()) {
            //Todos os processos que ja chegaram no sistema no tempo atual vão para a lista de ativos
            addActiveProcess(processQueue, activeProceses, currentTime);

            if (!activeProceses.empty()) {
                currentProcess = activeProceses.remove();

                //Tempo de resposta
                if (currentTime <= currentProcess.getArrivalTime()) {
                    currentTime = currentProcess.getArrivalTime();
                }
                currentProcess.setResponseTime(currentTime - currentProcess.getArrivalTime());

                currentProcess.setWaitingTime(currentProcess.getResponseTime());

                currentTime += currentProcess.getRemainingDuration();

                currentProcess.setReturnTime(currentTime - currentProcess.getArrivalTime());

                count++;
                waitingSum += currentProcess.getWaitingTime();
                responseSum += currentProcess.getResponseTime();
                returnSum += currentProcess.getReturnTime();

            } else {
                currentTime++;
            }

        }
        System.out.printf("SJF %.1f %.1f %.1f\n", (float) returnSum / count, (float) responseSum / count, (float) waitingSum / count);
    }

    public static void executeRR(Queue processQueue) {
        int count = 0;
        int waitingSum = 0;
        int responseSum = 0;
        int returnSum = 0;

        int currentTime = 0;
        Queue activeProceses = new Queue();
        Process currentProcess = null;

        while (!processQueue.empty() || !activeProceses.empty()) {
            //Todso os processos que ja chegaram no sistema no tempo atual vão para a lista de ativos
            addActiveProcess(processQueue, activeProceses, currentTime);

            //Roda o primeiro processo da lista de ativos
            if (!activeProceses.empty()) {
                currentProcess = activeProceses.remove();

                //Tempo de resposta
                if (!currentProcess.isExecuted()) {
                    currentProcess.setExecuted(true);
                    if (currentTime <= currentProcess.getArrivalTime()) {
                        currentTime = currentProcess.getArrivalTime();
                        currentProcess.setResponseTime(0);
                    } else {
                        currentProcess.setResponseTime(currentTime - currentProcess.getArrivalTime());
                    }
                }

                currentProcess.setWaitingTime(currentProcess.getWaitingTime() + currentTime - currentProcess.getArrivalTime());
                currentTime += 2;
                currentProcess.setRemainingDuration(currentProcess.getRemainingDuration() - 2);

                addActiveProcess(processQueue, activeProceses, currentTime);

                if (currentProcess.getRemainingDuration() <= 0) {
                    currentTime += currentProcess.getRemainingDuration();
                    currentProcess.setReturnTime(currentTime - currentProcess.getInitialArrivalTime());

                    count++;
                    waitingSum += currentProcess.getWaitingTime();
                    responseSum += currentProcess.getResponseTime();
                    returnSum += currentProcess.getReturnTime();

                } else {
                    currentProcess.setArrivalTime(currentTime);
                    activeProceses.insert(currentProcess);
                }
            } else {
                currentTime++;
            }

        }
        System.out.printf("RR %.1f %.1f %.1f\n", (float) returnSum / count, (float) responseSum / count, (float) waitingSum / count);
    }

    private static void addActiveProcess(Queue processQueue, Queue activeProceses, int currentTime) {
        while (!processQueue.empty() && processQueue.consult().getInitialArrivalTime() <= currentTime) {
            activeProceses.insert(processQueue.remove());
        }
    }

}
