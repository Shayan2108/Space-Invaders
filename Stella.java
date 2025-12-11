public class Stella {
    public int x,y,velocita;
    public MyPanel m;

    public Stella(int x, int y, int velocita, MyPanel m) {
        this.x = x;
        this.y = y;
        this.velocita = velocita;
        this.m = m;
    }
    public void sposta()
    {
        y += velocita;
    }
    public boolean isFinita()
    {
        if(y > m.getHeight() + 10)
            return true;
        return false;
    }
}
