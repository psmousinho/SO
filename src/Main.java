/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Pablo Suria
 */
public class main {

    public static void Main(String[] args) throws FileNotFoundException {
        Queue q = new Queue();
        File file = new File("TestData.txt");
        Scanner sc = new Scanner(file);
        while (sc.hasNextLine()) {
            String[] words = sc.nextLine().split(" ");
            q.insert(new Process(Integer.parseInt(words[0]), Integer.parseInt(words[1])));
        }

        Scheduler.executeFifo(new Queue(q));
        Scheduler.executeSJF(new Queue(q));
        Scheduler.executeRR(new Queue(q));

    }

}
