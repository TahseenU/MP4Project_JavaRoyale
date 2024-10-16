public class Wall{
    private int x;
    private int y;
    private int width;
    private int height;

    public Wall (int x, int y, int w, int h){
        this.x = x;
        this.y = y;
        width = w;
        height = h;
    }

    public int getX (){
        return x;
    }

    public int getY (){
        return y;
    }

    public int getWidth (){
        return width;
    }

    public int getHeight (){
        return height;
    }
}