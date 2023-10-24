import java.util.*;

public class Bakery {
    static int solve(ArrayList<Integer> cakes){
        // TO be completed by students
        int answer = 0;
        final int POS_INF = Integer.MAX_VALUE;
        SkipList skplist = new SkipList();
        for (int i = 0; i < cakes.size(); i++){
            int number = skplist.upperBound(cakes.get(i));
            if (number == POS_INF){
                skplist.insert(cakes.get(i));
                answer++;
            }
            else{
                skplist.delete(number);
                skplist.insert(cakes.get(i));
            }
        }
        return answer;
    }
}
