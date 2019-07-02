package game;

import java.util.ArrayList;
import java.util.List;

public class Util {
    public static List<Integer> maxIndices(Integer[] arr) {
        ArrayList<Integer> out = new ArrayList<>();
        int max = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == max) {
                out.add(i);
            } else if (arr[i] > max) {
                max = arr[i];
                out = new ArrayList<>();
                out.add(i);
            }
        }
        return out;
    }

    public static List<Integer> minIndices(Integer[] arr) {
        ArrayList<Integer> out = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == min) {
                out.add(i);
            } else if (arr[i] < min) {
                min = arr[i];
                out = new ArrayList<>();
                out.add(i);
            }
        }
        return out;
    }

    public static List<Integer> maxIndicesDiff(Integer[] arrPos, Integer[] arrNeg) {
        ArrayList<Integer> out = new ArrayList<>();
        int max = -1;
        for (int i = 0; i < arrPos.length; i++) {
            if (arrPos[i] - arrNeg[i] == max) {
                out.add(i);
            } else if (arrPos[i] - arrNeg[i] > max) {
                max = arrPos[i] - arrNeg[i];
                out = new ArrayList<>();
                out.add(i);
            }
        }
        return out;
    }
}
