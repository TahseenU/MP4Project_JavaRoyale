public class Pellet{
    private double x;
    private double y;
    private double vx;
    private double vy;
    private int size;
    private int target;

    public Pellet (double x, double y, double targetX, double targetY, int size, double speed, int target){
        this.target = target;
        this.x = x;
        this.y = y;
        double dx = targetX - x;
        double dy = targetY - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        this.vx = dx / distance * speed;
        this.vy = dy / distance * speed;
        this.size = size;
    }

    public void move (){
        x += vx;
        y += vy;
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