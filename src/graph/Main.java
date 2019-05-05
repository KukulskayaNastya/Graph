package graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        //Task1
        List<Vertex> vertices = GraphService.getVerticesByFile("g1.txt");
        if (!GraphService.validate(vertices)) System.out.println("G не неориентированный граф");
        else {
            System.out.println("#1.1\n" + Task1.getAdjacentVertexSet(vertices, 6));
            System.out.println("#1.2\n" + Task1.getComponentNumber(vertices));
            System.out.println("#1.3\n" + Task1.getDiameter(vertices));
            List<Vertex> subGraph = GraphService.getVerticesByFile("h1.txt");
            if (!GraphService.validate(subGraph)) System.out.println("#1.4\n-1");
            else System.out.println("#1.4\n" + Task1.isSubGraph(vertices, subGraph));
        }

        //Task2
        vertices = GraphService.getVerticesByFile("g2.txt");
        if (!GraphService.validate(vertices)) System.out.println("G не неориентированный граф");
        else {
            List<Integer> Z = new ArrayList<>(Arrays.asList(1, 3));
            List<Integer> A = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6));

            List<Integer> A_ = Task2.clearVertex(vertices, Z, A);
            System.out.println("#2.1\n" + A_.toString().replaceAll("[\\[\\],]", ""));

            System.out.println("#2.2\n" + Task2.getSubGraph(vertices, A_));

            Integer v = 7;
            Z.add(v);
            A_.add(v);
            List<Integer> border = Task2.getBorder(vertices, A_);
            System.out.println("#2.3\n" + border.toString().replaceAll("[\\[\\],]", ""));

            Integer u = 4;
            Z.remove(u);
            System.out.println("#2.4\n" + Task2.getEdges(vertices, Z, A_, border, u));
        }

        //Task3
        vertices = GraphService.getVerticesByFile("g3.txt");
        if (!GraphService.validate(vertices)) System.out.println("G не неориентированный граф");
        else {
            System.out.println("#3.1\n" + Task3.programs(vertices));
            String[] res = Task3.isWin(vertices);
            System.out.println("#3.2\n" + res[0]);
            System.out.println("#3.3\n" + res[1]);
        }
    }
}
