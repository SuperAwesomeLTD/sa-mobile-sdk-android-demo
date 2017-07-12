package aademo.superawesome.tv.awesomeadsdemo.library;

public interface Task <Input, Output, Result> {

    Result execute(Input input);
}
