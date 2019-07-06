public interface ISubject
{
    public void Attach(BoardComponent observer);
    public void Detach(BoardComponent observer);
    public void Notify(BoardComponent square);
}