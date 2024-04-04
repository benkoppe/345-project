package core;

public abstract class Algorithm {

    protected Node[] nodes;
    protected State[] states;

    private boolean isFinished = false;

    public Algorithm(Node[] nodes) {
        this.nodes = nodes;
        this.states = new State[nodes.length];
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void loopOnce() {
        assert !isFinished : "Cannot loop once after algorithm is finished";
        isFinished = loopOnceInternal();
    }

    public void finishImmediately() {
        do {
            loopOnce();
        } while (!isFinished);
    }

    abstract protected boolean loopOnceInternal();

    protected void changeState(int id, State state) {
        this.states[id] = state;
    }
}
