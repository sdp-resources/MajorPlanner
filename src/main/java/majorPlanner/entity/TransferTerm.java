package majorPlanner.entity;

public class TransferTerm implements Term {
    @Override
    public Period getPeriod() {
        throw new RuntimeException();
    }

    @Override
    public Year getYear() {
        throw new RuntimeException();
    }
}
