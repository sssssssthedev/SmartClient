package ro.sssssssthedev.AntiCheat.command.type.arguments;

import java.util.function.Function;

public final class Arguments {
    
    private String[] args;
    private int position = 0;
    
    public final int length;
    
    public Arguments(String[] args) {
        this.length = args.length;
        this.args = args;
    }
    
    public String get(int pos) {
        return args[pos];
    }
    
    public String next() {
        return args[position++];
    }
    
    public <A> A nextAs(Function<String, A> function) {
        return function.apply(next());
    }
    
    public boolean hasNext() {
        return position < length;
    }
    
    public void jumpTo(int position) {
        this.position = position;
    }
    
    public String joinAll() {
        jumpTo(0);
        return join();
    }
    
    public String join() {
        StringBuilder builder = new StringBuilder();
        
        while (hasNext())
            builder.append(next() + " ");
        
        return builder.toString().trim();
    }
}
