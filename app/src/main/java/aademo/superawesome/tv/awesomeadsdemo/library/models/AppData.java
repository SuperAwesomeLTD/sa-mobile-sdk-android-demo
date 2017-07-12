package aademo.superawesome.tv.awesomeadsdemo.library.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppData {

    int count;
    private App[] data;

    public AppData() {
        // do nothing
    }

    public List<App> getData() {
        if (data != null) {
            return Arrays.asList(data);
        } else {
            return new ArrayList<>();
        }
    }

}
