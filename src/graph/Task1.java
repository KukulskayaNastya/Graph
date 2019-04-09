package graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class Task1 {

    static Integer getDiameter(List<Vertex> vertices) {
        Integer result = 0;
        for (Vertex vertex : vertices) {
            Integer bfs = bfs(vertices, vertex.getNumber());
            result = bfs > result ? bfs : result;
        }
        return result;
    }

    static Integer bfs(List<Vertex> vertices, Integer number) {
        Integer[] distance = new Integer[vertices.size()];
        distance[number] = 0;
        LinkedList<Integer> vertexNumbers = new LinkedList<>();
        vertexNumbers.add(number);
        while (!vertexNumbers.isEmpty()) {
            Integer num = vertexNumbers.removeFirst();
            for (Integer i : GraphService.getVertex(vertices, num).getVertexNumbers()) {
                if (isNull(distance[i])) {
                    distance[i] = distance[num] + 1;
                    vertexNumbers.add(i);
                }
            }
        }
        return Arrays.stream(distance).filter(Objects::nonNull).mapToInt(Integer::intValue).max().orElse(0);
    }

    static Integer getComponentNumber(List<Vertex> vertices) {
        Integer result = 0;
        vertices.forEach(v -> v.setIsVisited(null));
        for (Vertex v : vertices) {
            if (isNull(v.getIsVisited())) {
                result++;
                dfs(vertices, v);
            }
        }
        return result;
    }

    static void dfs(List<Vertex> vertices, Vertex vertex) {
        vertex.setIsVisited(true);
        vertices.forEach(v -> {
            if (isNull(v.getIsVisited()) && vertex.getVertexNumbers().contains(v.getNumber())) dfs(vertices, v);
        });
    }

    static String getAdjacentVertexSet(List<Vertex> vertices, Integer number) {
        return GraphService.getVertex(vertices, number).getVertexNumbers().stream().sorted().map(Object::toString)
                .collect(Collectors.joining(" "));
    }

    static String isSubGraph(List<Vertex> vertices, List<Vertex> subGraph) {
        List<Integer> vertexNumbers = vertices.stream().map(Vertex::getNumber).collect(Collectors.toList());
        if (subGraph.stream().anyMatch(vertex -> !vertexNumbers.contains(vertex.getNumber()))) return "-1";
        if (subGraph.stream().anyMatch(vertex ->
                !GraphService.getVertex(vertices, vertex.getNumber()).getVertexNumbers().containsAll(vertex.getVertexNumbers())))
            return "-1";

        List<Integer> boundaryVertices = subGraph.stream().filter(vertex -> !vertex.getVertexNumbers().isEmpty() &&
                GraphService.getVertex(vertices, vertex.getNumber()).getVertexNumbers().size() > vertex.getVertexNumbers().size())
                .map(Vertex::getNumber).collect(Collectors.toList());
        return boundaryVertices.stream().sorted().map(Object::toString).collect(Collectors.joining(" "));
    }
}
