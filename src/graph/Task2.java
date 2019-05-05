package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Task2 {

    static List<Integer> clearVertex(List<Vertex> vertices, List<Integer> Z, List<Integer> A) {
        if (!A.containsAll(Z)) throw new RuntimeException("Некорректные данные");

        List<Integer> res = new ArrayList<>();
        for (Integer a : A) {
            if (Z.contains(a)) {
                res.add(a);
                continue;
            }
            List<Integer> aVertices = GraphService.getVertex(vertices, a).getVertexNumbers();
            if (A.containsAll(aVertices)) res.add(a);
            else cascadeDelete(vertices, a, res, A, Z);
        }

        Collections.sort(res);
        return res;
    }

    private static void cascadeDelete(List<Vertex> vertices, Integer a, List<Integer> res,
                                      List<Integer> A, List<Integer> Z) {
        List<Integer> aVertices = GraphService.getVertex(vertices, a).getVertexNumbers();
        for (Integer vertex : aVertices) {
            if (res.contains(vertex) && !Z.contains(vertex)) {
                res.remove(vertex);
                cascadeDelete(vertices, vertex, res, A, Z);
            }
        }
    }

    static String getSubGraph(List<Vertex> vertices, List<Integer> A) {
        List<String> subGraph = new ArrayList<>();
        for (Integer vertex : A) {
            List<Integer> vertexNumbers = GraphService.getVertex(vertices, vertex).getVertexNumbers()
                    .stream().filter(A::contains).collect(Collectors.toList());
            subGraph.add(vertex + ": " + vertexNumbers.toString().replaceAll("[\\[\\],]", ""));
        }
        return String.join("\n", subGraph);
    }

    public static List<Integer> getBorder(List<Vertex> vertices, List<Integer> A) {
        return A.stream().filter(num -> isVertexHasBlackEdge(vertices, A, num) && isVertexHasWhiteEdge(vertices, A, num))
                .sorted().collect(Collectors.toList());
    }

    private static boolean isVertexHasBlackEdge(List<Vertex> vertices, List<Integer> A, Integer vertexNum) {
        Vertex vertex = GraphService.getVertex(vertices, vertexNum);
        return vertex.getVertexNumbers().stream().anyMatch(num -> !A.contains(num));
    }

    private static boolean isVertexHasWhiteEdge(List<Vertex> vertices, List<Integer> A, Integer vertexNum) {
        Vertex vertex = GraphService.getVertex(vertices, vertexNum);
        return vertex.getVertexNumbers().stream().anyMatch(num -> A.contains(num));
    }

    public static String getEdges(List<Vertex> vertices, List<Integer> Z, List<Integer> A, List<Integer> border, Integer u) {
        if (!border.contains(u)) return getEdges(vertices, A);
        else getEdges(vertices, Z, A, u);
        return getEdges(vertices, A);
    }

    private static void getEdges(List<Vertex> vertices, List<Integer> Z, List<Integer> A, Integer u) {
        A.remove(u);
        List<Integer> uVertices = GraphService.getVertex(vertices, u).getVertexNumbers().stream()
                .filter(A::contains).collect(Collectors.toList());
        uVertices.forEach(num -> {
            if (!Z.contains(num)) getEdges(vertices, Z, A, num);
        });
    }

    private static String getEdges(List<Vertex> vertices, List<Integer> A) {
        Set<String> edges = new HashSet<>();
        A.forEach(num -> {
            List<Integer> numVertices = GraphService.getVertex(vertices, num).getVertexNumbers().stream()
                    .filter(A::contains).collect(Collectors.toList());
            numVertices.forEach(subNum -> edges.add(num < subNum ? num + "-" + subNum : subNum + "-" + num));
        });
        return String.join(" ", edges);
    }
}
