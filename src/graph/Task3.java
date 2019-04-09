package graph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Task3 {

    static String programs(List<Vertex> vertices) throws IOException {
        Set<Integer> vertexNumber = vertices.stream().map(Vertex::getNumber).collect(Collectors.toSet());
        List<Set<Integer>> setList = getSetList();
        if (setList.get(0).size() != 0) return "-1";
        int maxSize = 0;
        for (int i = 0; i < setList.size() - 1; i++) {
            Set<Integer> prevSet = setList.get(i);
            Set<Integer> nextSet = setList.get(i + 1);
            List<String> errors = new ArrayList<>();

            if (!vertexNumber.containsAll(nextSet)) errors.add("-1");
            if (nextSet.containsAll(prevSet)) {
                if (nextSet.size() - prevSet.size() > 1) errors.add("-3");
            } else if (prevSet.containsAll(nextSet)) {
                continue;
            } else errors.add("-2");
            if (errors.size() > 0) return String.join(", ", errors);

            if (nextSet.size() > maxSize) maxSize = nextSet.size();
        }
        return String.valueOf(maxSize);
    }

    static String[] isWin(List<Vertex> vertices) throws IOException {
        Set<Integer> vertexNumber = vertices.stream().map(Vertex::getNumber).collect(Collectors.toSet());
        List<Set<Integer>> setList = getSetList();

        String mon = "1";
        Set<Integer> prevA = new HashSet<>();
        Set<Integer> nextA = new HashSet<>();
        for (int i = 0; i < setList.size() - 1; i++) {
            Set<Integer> prevZ = setList.get(i);
            Set<Integer> nextZ = setList.get(i + 1);

            if (nextZ.containsAll(prevZ)) {
                nextA.addAll(nextZ);
            } else if (prevZ.containsAll(nextZ)) {
                nextA = new HashSet<>(Task2.clearVertex(vertices, new ArrayList<>(nextZ), new ArrayList<>(nextA)));
            }

            if (prevA.size() >  nextA.size()) mon = "0";
            prevA = new HashSet<>(nextA);
        }

        if (nextA.containsAll(vertexNumber)) return new String[]{"Y", mon};
        else return new String[]{Task2.getSubGraph(vertices, new ArrayList<>(nextA)), mon};
    }

    private static List<Set<Integer>> getSetList() throws IOException {
        List<Set<Integer>> setList = new ArrayList<>();
        String[] lines = new String(Files.readAllBytes(Paths.get("src/graph/resources/z3.txt")), "UTF-8").split("\n");
        for (String line : lines) {
            Set<Integer> set = new HashSet<>();
            line = line.replaceAll("[\\r\\[\\] ]", "");
            if (line.length() != 0) {
                for (String i : line.split(",")) set.add(Integer.valueOf(i));
            }
            setList.add(set);
        }
        return setList;
    }
}
