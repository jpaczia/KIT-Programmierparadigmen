package DemoAkkaActor;

import akka.actor.*;

public class DemoAkkaActorTell {
    public static void main(String[] args) {
        ActorSystem actorSystem = ActorSystem.create("Demo");
        ActorRef zero = actorSystem.actorOf(Props.create(MyActor.class, 0));
        ActorRef one = actorSystem.actorOf(Props.create(MyActor.class, 1));

        zero.tell("Hello", one);
        one.tell("Hello", zero);
        actorSystem.terminate();
    }

    public static class MyActor extends AbstractActor {
        private int id;

        public MyActor(int id) {
            this.id = id;
        }

        @Override
        public Receive createReceive() {
            return receiveBuilder()
                .match(String.class, this::handleStringMessage)
                .matchAny(message -> unhandled(message))
                .build();
        }

        private void handleStringMessage(String message) {
            System.out.println("Id: " + this.id + " message: " + message);
            getSender().tell("Hi", ActorRef.noSender());
        }
    }
}

/* Ausgabe
Id: 1 message: Hello
Id: 0 message: Hello
Id: 0 message: Hi
Id: 1 message: Hi
*/