package aademo.superawesome.tv.awesomeadsdemo.library.models;

import java.util.ArrayList;
import java.util.List;

public class App {

    private int id;
    private String name;
    private List<Placement> placements = new ArrayList<>();

    public App (){
        // do nothing
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Placement> getPlacements() {
        return placements;
    }
}
