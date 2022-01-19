
/**
 * Program for generating kanji component dependency order via topological sort.
 *
 * @author Ian Rodovsky, Acuna
 * @version 1
 */
import java.io.*;
import java.util.Scanner;
import java.util.LinkedList;

public class RodovskyMain {

    public static void main(String[] args) {
        BetterDiGraph graph = new BetterDiGraph();
        try {
            int currentKanjiInt;
            String currentKanjiString;
            Scanner kanjiScanner = new Scanner(new File("data-kanji.txt"), "UTF8");
            Scanner componentScanner = new Scanner(new File("data-components.txt"));
            while (kanjiScanner.hasNextLine()) {
                kanjiScanner.nextLine();
                while (!kanjiScanner.hasNextInt() && kanjiScanner.hasNextLine()) {
                    kanjiScanner.nextLine();
                }
                if (kanjiScanner.hasNextInt()) {
                    currentKanjiInt = kanjiScanner.nextInt();
                    currentKanjiString = kanjiScanner.next();
                    graph.addVertex(currentKanjiInt, currentKanjiString);
                }
            }
            int v;
            int w;
            while (componentScanner.hasNextLine()) {
                componentScanner.nextLine();
                while (!componentScanner.hasNextInt() && componentScanner.hasNextLine()) {
                    componentScanner.nextLine();
                }
                if (componentScanner.hasNextInt()) {
                    v = componentScanner.nextInt();
                    w = componentScanner.nextInt();
                    graph.addEdge(v, w);
                }
            }
            kanjiScanner.close();
            componentScanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        LinkedList kanjiList = (LinkedList) graph.vertices();
        IntuitiveTopological orderedGraph = new IntuitiveTopological(graph);
        LinkedList orderedKanjiIdxList = (LinkedList) orderedGraph.order();
        LinkedList orderedKanjiList = (LinkedList) graph.vertices();
        LinkedList orderedKanjiSymbol = new LinkedList<String>();
        for (int idx = 0; idx < orderedKanjiIdxList.size(); idx++) {
            orderedKanjiSymbol.add(orderedKanjiList.get((Integer) orderedKanjiIdxList.get(idx)));
        }
        System.out.println("Original:");
        for (int idx = 0; idx < kanjiList.size(); idx++) {
            System.out.print(kanjiList.get(idx) + " ");
        }
        System.out.println("\nSorted:");
        for (int idx = 0; idx < orderedKanjiSymbol.size(); idx++) {
            System.out.print(orderedKanjiSymbol.get(idx) + " ");
        }
        System.out.println();
    }
}
