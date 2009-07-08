package graphics;
public class Asynchronous extends Player {
    private Move lastMove = null;
    // Creates new AsynchronousPlayer 
    public AsynchronousPlayer(String name, int number) 
    {
        super(name, number);
    }
    
    //asked what move it would like to make
    public synchronized  Move getMove(Board b)
    {
        try
        {
            wait();
        }
        
        //if we are interrupted, then it means the gam is over and return null
        catch(InterruptedException e)
        {
            return null;
        }
     
        return lastMove;
    }
  
//Make a move.

   public synchronized void makeMove(Move aMove)
    {
        lastMove = aMove;
        notifyAll();
    }
 
}