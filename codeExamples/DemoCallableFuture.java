import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class DemoCallableFuture {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        List<Future<String>> futures = new ArrayList<Future<String>>();

        for (int i = 0; i < 2; i++) {
            futures.add(executorService.submit(new MyCallable(i)));
        }

        for (Future<String> future : futures) {
            try {
                String result = future.get();
                System.out.println(result);
            } catch (InterruptedException e) {
                //TODO: handle exception
            } catch (ExecutionException e){
                //TODO: handle exception
            }
        }

        executorService.shutdown();
    }

    public static class MyCallable implements Callable<String> {
        private int id;

        public MyCallable(int id) {
            this.id = id;
        }

        public String call() {
            return "Running: " + this.id;
        }
    }
}

/* Ausgabe:
Running: 0
Running: 1
*/