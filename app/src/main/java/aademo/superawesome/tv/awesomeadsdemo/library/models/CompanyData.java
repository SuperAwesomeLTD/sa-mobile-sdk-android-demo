package aademo.superawesome.tv.awesomeadsdemo.library.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CompanyData {

    int count;
    private Company[] data;

    public CompanyData() {
        // do nothing
    }

    public List<Company> getData() {
        if (data != null) {
            return Arrays.asList(data);
        } else {
            return new ArrayList<>();
        }
    }
}
