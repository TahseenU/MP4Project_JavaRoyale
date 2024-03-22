public class Buff{
    private int x;
    private int y;
    
    public Buff (){
        x = (int) (Math.random () * 1321);
        y = (int) (Math.random () * 671);
    }

    public int getX (){
        return x;
    }

    public int getY (){
        return y;
    }
}