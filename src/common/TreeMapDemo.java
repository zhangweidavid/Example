package common;

/**
 * Created by wei.zw on 2017/6/8.
 */
public class TreeMapDemo {


    public static class Studen implements Comparable<Studen> {

        private String name;

        private int score;

        public Studen(String name, int s) {
            this.name = name;
            this.score = s;
        }


        @Override
        public int compareTo(Studen studen) {
            if (studen.score < this.score) {
                return 1;
            }
            if (studen.score > this.score) {
                return -1;
            }
            return 0;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public String toString() {
            return "Studen{" +
                    "name='" + name + '\'' +
                    ", score=" + score +
                    '}';
        }
    }

    public static void main(String[] args){
        //TreeMap
    }
}
