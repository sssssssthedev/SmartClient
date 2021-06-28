package ro.sssssssthedev.AntiCheat.utils;

public final class Tuple<A, B>
{
    private A a;
    private B b;

    public Tuple(A aIn, B bIn)
    {
        this.a = aIn;
        this.b = bIn;
    }

    /**
     * Get the first Object in the Tuple
     */
    public A a() {
        return this.a;
    }

    /**
     * Get the second Object in the Tuple
     */
    public B b()
    {
        return this.b;
    }
}
