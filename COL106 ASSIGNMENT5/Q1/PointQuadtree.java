import java.util.LinkedList;
import java.util.Queue;

public class PointQuadtree {

    enum Quad {
        NW,
        NE,
        SW,
        SE
    }

    public PointQuadtreeNode root;

    public PointQuadtree() {
        this.root = null;
    }

    private boolean insert_helper(CellTower a, PointQuadtreeNode node, PointQuadtreeNode parent, int pos){
        if (node == null){
            PointQuadtreeNode newnode = new PointQuadtreeNode(a);
            parent.quadrants[pos] = newnode;
            return true;
        }
        if (node.celltower.x == a.x && node.celltower.y == a.y){return false;}
        if (a.x < node.celltower.x && a.y >= node.celltower.y){
            return insert_helper(a, node.quadrants[0], node, 0);
        }
        else if (a.x >= node.celltower.x && a.y > node.celltower.y){
            return insert_helper(a, node.quadrants[1], node, 1);
        }
        else if (a.x > node.celltower.x && a.y <= node.celltower.y){
            return insert_helper(a, node.quadrants[3], node, 3);
        }
        else{
            return insert_helper(a, node.quadrants[2], node, 2);
        }
    }

    public boolean insert(CellTower a) {
        // TO be completed by students
        if (this.root == null){
            PointQuadtreeNode newnode = new PointQuadtreeNode(a);
            this.root = newnode;
            return true;
        }
        else{
            return insert_helper(a, this.root, null, -1);
        }
    }




    private boolean search_helper(int x, int y, PointQuadtreeNode node){
        if (node == null){return false;}
        if (node.celltower.x == x && node.celltower.y == y){return true;}
        if (x < node.celltower.x && y >= node.celltower.y){
            return search_helper(x, y, node.quadrants[0]);
        }
        else if (x >= node.celltower.x && y > node.celltower.y){
            return search_helper(x, y, node.quadrants[1]);
        }
        else if (x > node.celltower.x && y <= node.celltower.y){
            return search_helper(x, y, node.quadrants[3]);
        }
        else{
            return search_helper(x, y, node.quadrants[2]);
        }
    }

    public boolean cellTowerAt(int x, int y) {
        // TO be completed by students
        return search_helper(x, y, this.root);
    }



    private CellTower choose_helper(int x, int y, int r, PointQuadtreeNode node, CellTower min){
        if (node == null){return min;}
        CellTower min_cost = min;
        if (r > node.celltower.distance(x, y)){
            if (min == null || node.celltower.cost < min.cost){
                min_cost = node.celltower;
            }
            for (int i = 0; i < 4; i++){
                CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                if (temp != null && temp.cost < min_cost.cost){
                    min_cost = temp;
                }
            }
        }
        else if (r == node.celltower.distance(x, y)){
            if (min == null || node.celltower.cost < min.cost){
                min_cost = node.celltower;
            }
            if (node.celltower.x == x){
                if (y > node.celltower.y){
                    for (int i = 0; i < 2; i++){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && temp.cost < min_cost.cost){
                            min_cost = temp;
                        }
                    }
                }
                else if (y < node.celltower.y){
                    for (int i = 2; i < 4; i++){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && temp.cost < min_cost.cost){
                            min_cost = temp;
                        }
                    }
                }
            }
            else if (node.celltower.y == y){
                if (x > node.celltower.x){
                    for (int i = 1; i < 4; i+=2){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && temp.cost < min_cost.cost){
                            min_cost = temp;
                        }
                    }
                }
                else if (x < node.celltower.x){
                    for (int i = 0; i < 4; i+=2){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && temp.cost < min_cost.cost){
                            min_cost = temp;
                        }
                    }
                }
            }
            else{
                if (x < node.celltower.x && y > node.celltower.y){
                    for (int i = 0; i < 3; i++){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && temp.cost < min_cost.cost){
                            min_cost = temp;
                        }
                    }
                }
                else if (x > node.celltower.x && y > node.celltower.y){
                    for (int i = 0; i < 4; i++){
                        if (i == 2){continue;}
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && temp.cost < min_cost.cost){
                            min_cost = temp;
                        }
                    }
                }
                else if (x < node.celltower.x && y < node.celltower.y){
                    for (int i = 0; i < 4; i++){
                        if (i == 1){continue;}
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && temp.cost < min_cost.cost){
                            min_cost = temp;
                        }
                    }
                }
                else if (x > node.celltower.x && y < node.celltower.y){
                    for (int i = 1; i < 4; i++){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && temp.cost < min_cost.cost){
                            min_cost = temp;
                        }
                    }
                }
            }
        }
        else{
            if (node.celltower.x == x){
                if (y > node.celltower.y){
                    for (int i = 0; i < 2; i++){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                            min_cost = temp;
                        }
                    }
                }
                else if (y < node.celltower.y){
                    for (int i = 2; i < 4; i++){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                            min_cost = temp;
                        }
                    }
                }
            }
            else if (node.celltower.y == y){
                if (x > node.celltower.x){
                    for (int i = 1; i < 4; i+=2){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                            min_cost = temp;
                        }
                    }
                }
                else if (x < node.celltower.x){
                    for (int i = 0; i < 4; i+=2){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                            min_cost = temp;
                        }
                    }
                }
            }
            else if (r < node.celltower.x - x && r <= y - node.celltower.y){
                CellTower temp = choose_helper(x, y, r, node.quadrants[0], min_cost);
                if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                    min_cost = temp;
                }
            }
            else if (r <= x - node.celltower.x && r < y - node.celltower.y){
                CellTower temp = choose_helper(x, y, r, node.quadrants[1], min_cost);
                if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                    min_cost = temp;
                }
            }
            else if (r <= node.celltower.x - x && r < node.celltower.y - y){
                CellTower temp = choose_helper(x, y, r, node.quadrants[2], min_cost);
                if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                    min_cost = temp;
                }
            }
            else if (r < x - node.celltower.x && r <= node.celltower.y - y){
                CellTower temp = choose_helper(x, y, r, node.quadrants[3], min_cost);
                if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                    min_cost = temp;
                }
            }
            else if (r == node.celltower.x - x && r == y - node.celltower.y){
                for (int i = 0; i < 2; i++){
                    CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                    if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                        min_cost = temp;
                    }
                }
            }
            else if (r == x - node.celltower.x && r == y - node.celltower.y){
                for (int i = 1; i < 4; i += 2){
                    CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                    if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                        min_cost = temp;
                    }
                }
            }
            else if (r == node.celltower.x - x && r == node.celltower.y - y){
                for (int i = 0; i < 4; i += 2){
                    CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                    if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                        min_cost = temp;
                    }
                }
            }
            else if (r == x - node.celltower.x && r == node.celltower.y - y){
                for (int i = 2; i < 4; i++){
                    CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                    if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                        min_cost = temp;
                    }
                }
            }
            else if (r < node.celltower.x - x && r > y - node.celltower.y){
                for (int i = 0; i < 4; i += 2){
                    CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                    if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                        min_cost = temp;
                    }
                }
            }
            else if (r > node.celltower.x - x && r < y - node.celltower.y){
                for (int i = 0; i < 2; i++){
                    CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                    if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                        min_cost = temp;
                    }
                }
            }
            else if (r < x - node.celltower.x && r > y - node.celltower.y){
                for (int i = 1; i < 4; i += 2){
                    CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                    if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                        min_cost = temp;
                    }
                }
            }
            else if (r > node.celltower.x - x && r < node.celltower.y - y){
                for (int i = 2; i < 4; i ++){
                    CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                    if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                        min_cost = temp;
                    }
                }
            }
            else{
                if (x < node.celltower.x && y > node.celltower.y){
                    for (int i = 0; i < 3; i++){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                            min_cost = temp;
                        }
                    }
                }
                else if (x > node.celltower.x && y > node.celltower.y){
                    for (int i = 0; i < 4; i++){
                        if (i == 2){continue;}
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                            min_cost = temp;
                        }
                    }
                }
                else if (x < node.celltower.x && y < node.celltower.y){
                    for (int i = 0; i < 4; i++){
                        if (i == 1){continue;}
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                            min_cost = temp;
                        }
                    }
                }
                else if (x > node.celltower.x && y < node.celltower.y){
                    for (int i = 1; i < 4; i++){
                        CellTower temp = choose_helper(x, y, r, node.quadrants[i], min_cost);
                        if (temp != null && (min_cost == null || temp.cost < min_cost.cost)){
                            min_cost = temp;
                        }
                    }
                }   
            }
        }
        return min_cost;
    }

    public CellTower chooseCellTower(int x, int y, int r) {
        // TO be completed by students
        return choose_helper(x, y, r, this.root, null);
    }

}