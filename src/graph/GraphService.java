package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

public class GraphService {

    static List<Vertex> getVerticesByFile(String fileName) throws IOException {
        return Arrays.stream(new String(Files.readAllBytes(Paths.get("src/graph/resources/" + fileName))).split("\n"))
                .map(row -> {
                    String[] rowComponents = row.split(":");
                    Integer number = Integer.valueOf(rowComponents[0]);
                    List<Integer> vertexNumbers = (rowComponents.length > 1 && !rowComponents[1].trim().isEmpty()) ?
                            Arrays.stream(rowComponents[1].trim().split(" ")).map(Integer::valueOf).collect(Collectors.toList())
                            : new ArrayList<>();
                    return new Vertex(number, vertexNumbers);
                }).sorted(Comparator.comparing(Vertex::getNumber))
                .collect(Collectors.toList());
    }

    static boolean validate(List<Vertex> vertices) {
        return vertices.stream().allMatch(
                vertex -> vertex.getVertexNumbers().stream().allMatch(
                        vNum -> {
                            Vertex ver = getVertex(vertices, vNum);
                            return nonNull(ver) && ver.getVertexNumbers().contains(vertex.getNumber());
                        }));
    }

    static Vertex getVertex(List<Vertex> vertices, Integer number) {
        return vertices.stream().filter(vertex -> vertex.getNumber().equals(number)).findFirst().orElse(null);
    }
}
