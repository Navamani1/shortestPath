import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class ShortRoute {

    private static final String FILE_NAME = "C:\\Users\\T060156\\Desktop\\input.txt";

    public static void main(String[] args) throws Exception {
        // 0 - london
        // 1 - Dublin
        // 2 - Belfast
        // Sample data
//        int[][] distance = new int[size][size];
//        distance[0][0] = 0;
//        distance[0][1] = 464;
//        distance[0][2] = 518;
//
//        distance[1][0] = 464;
//        distance[1][1] = 0;
//        distance[1][2] = 141;
//
//        distance[2][0] = 518;
//        distance[2][1] = 141;
//        distance[2][2] = 0;

        BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME));
        List<String> list = new ArrayList<>();
        Map<String, Integer> mapDistance = new HashMap<>();
        String line = null;
        while ((line = reader.readLine()) != null) {
            String origin = line.substring(0, line.indexOf(" ")).trim();
            String dest = line.substring(line.indexOf("to") + 2, line.indexOf("=")).trim();
            if (!list.contains(origin)) {
                list.add(origin);
            }
            if (!list.contains(dest)) {
                list.add(dest);
            }
            mapDistance.put(line.substring(0, line.indexOf("=")).trim(), Integer.valueOf(line.substring(line.indexOf("=") + 1).trim()));
        }
        reader.close();

        int[][] distance = formMatrix(list, mapDistance);
        int currPos = 0;
        boolean[] visitCity = new boolean[list.size()];
        visitCity[currPos] = true;
        TreeMap<Integer, String> map = new TreeMap<>();
        findTotalDistance(distance, visitCity, currPos, list.size(), 1, 0, currPos + "", map);

        System.out.println(list);
        System.out.println(map.firstEntry());
    }


    static void findTotalDistance(int[][] distance, boolean[] visitCity, int currPos, int cities, int count, int cost, String route, TreeMap<Integer, String> map) {
        if (count == cities) {
            map.put(cost, route);
            return;
        }

        for (int i = 0; i < cities; i++) {
            if (!visitCity[i] && distance[currPos][i] > 0) {
                visitCity[i] = true;
                findTotalDistance(distance, visitCity, i, cities, count + 1, cost + distance[currPos][i], route + " -> " + i, map);
                visitCity[i] = false;
            }
        }
    }

    private static int[][] formMatrix(List<String> list, Map<String, Integer> mapDistance) {
        int size = list.size();
        int[][] distance = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (list.get(i).equals(list.get(j))) {
                    distance[i][j] = 0;
                } else {
                    int val = mapDistance.get(list.get(i) + " to " + list.get(j)) == null ? mapDistance.get(list.get(j) + " to " + list.get(i)) : mapDistance.get(list.get(i) + " to " + list.get(j));
                    distance[i][j] = val;
                }
            }
        }
        return distance;
    }
}
