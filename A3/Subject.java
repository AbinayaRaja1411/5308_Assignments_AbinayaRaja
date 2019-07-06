import java.util.ArrayList;
import java.util.Iterator;

public class Subject implements ISubject {
    ArrayList<BoardComponent> observers;
    private static ISubject asteroidSubject =null;

    private Subject()
    {
        observers = new ArrayList<>();
    }
    @Override
    public void Attach(BoardComponent observer) {
        observers.add(observer);
    }

    @Override
    public void Detach(BoardComponent observer) {
        observers.remove(observer);
    }

    @Override
    public void Notify(BoardComponent square) 
    {
        Iterator<BoardComponent> observersItr = observers.iterator();
        while (observersItr.hasNext()) 
        {
            BoardComponent observer = observersItr.next();
            observer.updateObserver(square);
        }
    }
    
    public static ISubject getAsteroidSubject()
	{
		if(asteroidSubject == null)
		{
			asteroidSubject = new Subject();
		}
		return asteroidSubject;
	}
}