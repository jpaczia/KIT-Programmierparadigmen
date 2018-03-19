import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class DemoRecursiveAction {
    public static void main(String[] args) {
        MyRecursiveAction action = new MyRecursiveAction(4, "none");
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(action);
    }

    public static class MyRecursiveAction extends RecursiveAction {
        private static final long serialVersionUID = 1L;
        private int workload;
        private String side;

        public MyRecursiveAction(int workload, String side) {
            this.workload = workload;
            this.side = side;
        }

        @Override
        protected void compute() {
            if (this.workload > 1) {
                System.out.println("Workload splitted (" + this.side + "): " + this.workload);

                int half = (int) this.workload / 2;
                MyRecursiveAction left = new MyRecursiveAction(half, "left");
                MyRecursiveAction right = new MyRecursiveAction(half, "right");

                left.fork();
                right.compute();
                left.join();
            } else {
                System.out.println("Workload not splitted (" + this.side + "): " + this.workload);
            }
        }
    }
}

/* Ausgabe:
Workload splitted (none): 4
Workload splitted (right): 2
Workload not splitted (right): 1
Workload not splitted (left): 1
Workload splitted (left): 2
Workload not splitted (right): 1
Workload not splitted (left): 1
*/