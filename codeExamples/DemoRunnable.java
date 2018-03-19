public class DemoRunnable {
    public static void main(String[] args) {
        Thread thread0 = new Thread(new MyRunnable(0));
        Thread thread1 = new Thread(new MyRunnable(1));

        thread0.start();
        thread1.start();
    }

    public static class MyRunnable implements Runnable {
        private int id;

        public MyRunnable(int id) {
            this.id = id;
        }

        public void run() {
            System.out.println("Running: " + this.id);
        }
    }
}

/* Ausgabe:
Running: 0
Running: 1
*/