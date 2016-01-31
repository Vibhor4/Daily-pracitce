package event.basic;

import java.util.ArrayList;

/**
 * Created by zzt on 1/14/16.
 * <p>
 * Usage:
 */
public class Input {
    private ArrayList<String> inputs = new ArrayList<>();

    public Input(String input) {
        inputs.add(input);
    }

    public Input(ArrayList<String> strings) {
        // deep copy the object of message
        for (String string : strings) {
            inputs.add(string);
        }
//        inputs = strings;
    }

    public Input(Input input) {
        inputs.addAll(input.getInputs());
    }

    public void more(String string) {
        inputs.add(string);
    }

    public ArrayList<String> getInputs() {
        return inputs;
    }

    public void setInputs(ArrayList<String> inputs) {
        this.inputs = inputs;
    }
}
