package DemoAkkaActor;

import akka.actor.*;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

import java.util.concurrent.TimeUnit;

public class DemoAkkaActorAsk{
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("Demo");
        ActorRef actor = actorSystem.actorOf(Props.create(MyActor.class));

        Integer[] values = new Integer[] {1,2};
        Timeout timeout = new Timeout(1, TimeUnit.SECONDS);
        Future<Object> future = Patterns.ask(actor,values,timeout);

        try {
            int result = (Integer) Await.result(future,timeout.duration());
            System.out.println("Result: " +result);
        } catch (Exception e) {
            e.printStackTrace();
        }

        actorSystem.terminate();
    }

    public static class MyActor extends AbstractActor {
        private int id;

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                .match(Integer[].class, this::handleIntegerMessage)
                .matchAny(message -> unhandled(message))
                .build();
        }

        private void handleIntegerMessage(Integer[] message) {
            int sum = 0;
            for (int val : message){
                sum+=val;
            }
            getSender().tell(sum,getSelf());
        }
    }
}

/* Ausgabe:
Result: 3
*/