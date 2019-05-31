// YOU ARE ALLOWED TO MODIFY THIS FILE
public interface IDatabase
{
    public int query(String drugname);

    public int claim(String drugname, int quantity);

    public boolean isDrugExist(String drugname);
}