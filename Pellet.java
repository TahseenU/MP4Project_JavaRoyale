public class Pellet{
    private double x;
    private double y;
    private double vX;
    private double vY;
    private int size;
    private int target;

    public Pellet (double x, double y, double targetX, double targetY, int size, double speed, int target){
        this.size = size;
        this.target = target;
        this.x = x;
        this.y = y;
        double dX = targetX - x;
        double dY = targetY - y;
        double distance = Math.sqrt(dX * dX + dY * dY);
        vX = dX / distance * speed;
        vY = dY / distance * speed;
    }

    public void move (){
        x += vX;
        y += vY;
    }

    public int getX (){
        return (int) x;
    }

    public int getY (){
        return (int) y;
    }

    public int getSize (){
        return size;
    }

    public int getTarget (){
        return target;
    }
}