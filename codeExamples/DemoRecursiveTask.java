import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class DemoRecursiveTask {
    public static void main(String[] args) {
        MyRecursiveTask task = new MyRecursiveTask(4, "None");
        ForkJoinPool pool = new ForkJoinPool();
        Long result = pool.invoke(task);

        System.out.println("Final result: " + result);
    }

    public static class MyRecursiveTask extends RecursiveTask<Long> {
        private static final long serialVersionUID = 1L;
        private long workload;
        private String side;

        public MyRecursiveTask(long workload, String side) {
            this.workload = workload;
            this.side = side;
        }

        @Override
        protected Long compute() {
            if (this.workload > 1) {
                int half = (int) this.workload / 2;
                MyRecursiveTask left = new MyRecursiveTask(half, "Left");
                MyRecursiveTask right = new MyRecursiveTask(half, "Right");

                left.fork();
                Long rightResult = right.compute();
                Long leftResult = left.join();
                Long result = leftResult + rightResult;
                System.out.println(this.side + " result (splitted): " + result);
                return result;
            } else {
                System.out.println(this.side + " result (not splitted): " + this.workload);
                return this.workload;
            }
        }
    }
}

/* Ausgabe:
Right result (not splitted): 1
Left result (not splitted): 1
Right result (splitted): 2
Right result (not splitted): 1
Left result (not splitted): 1
Left result (splitted): 2
None result (splitted): 4
Final result: 4
*/