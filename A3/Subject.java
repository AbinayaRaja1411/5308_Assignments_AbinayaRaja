import java.util.ArrayList;

public class Subject implements ISubject {
    ArrayList<BoardComponent> observers;
	private static ISubject asteroidSubject =null;
    @Override
    public void Attach(BoardComponent observer) {
        observers.add(observer);
    }

    @Override
    public void Detach(BoardComponent observer) {
        observers.remove(observer);
    }

    @Override
    public void Notify(BoardComponent square) {
        for (BoardComponent observer : observers) {
            if(observer.equals(square))
            {
                observer.updateObserver();
            }
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