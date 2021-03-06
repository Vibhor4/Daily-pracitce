package eventMultiThread;

import event.InputFinishedEvent;
import event.ShiftedInputEvent;
import event.SortedEvent;
import event.basic.EventRouter;
import event.basic.Input;
import event.basic.NoHandlerException;
import eventMultiThread.basic.MultiThreadInputHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by zzt on 1/31/16.
 * <p>
 * Usage:
 */
public class DoSort implements MultiThreadInputHandler {

    BlockingQueue<Input> queue = new LinkedBlockingQueue<>();
    private ArrayList<String> inputs = new ArrayList<>();


    public DoSort() {
        EventRouter.register(ShiftedInputEvent.class, this);
//        EventRouter.register(InputFinishedEvent.class, this);
        EventRouter.register(ShiftFinished.class, this);
    }

    @Override
    public void run() {
        while (true) {
            Input input;
            try {
                input = queue.poll(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
                continue;
            }
            if (input == null) {
                continue;
            }
            if (input.isFinished()) {
                Collections.sort(inputs);
                try {
                    EventRouter.throwEvent(new SortedEvent(new Input(inputs)));
                } catch (NoHandlerException e) {
                    e.printStackTrace();
                }
            } else {
                inputs.addAll(input.getInputs());
            }
        }
    }

    @Override
    public BlockingQueue<Input> getQueue() {
        return queue;
    }
}
