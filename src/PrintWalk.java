import java.util.ArrayList;

public class PrintWalk implements IWalk<ArrayList<String>> {
    @Override
    public void doWalk(ArrayList<String> actualValue) {
        System.out.println(actualValue);
    }
}