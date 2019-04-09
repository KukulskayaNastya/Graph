package graph;

import java.util.List;

public class Vertex {
    private Integer number;
    private List<Integer> vertexNumbers;
    private Boolean isVisited;

    Vertex(Integer number, List<Integer> vertexNumbers) {
        this.number = number;
        this.vertexNumbers = vertexNumbers;
    }

    Integer getNumber() {
        return number;
    }

    List<Integer> getVertexNumbers() {
        return vertexNumbers;
    }

    Boolean getIsVisited() {
        return isVisited;
    }

    void setIsVisited(Boolean isVisited) {
        this.isVisited = isVisited;
    }
}