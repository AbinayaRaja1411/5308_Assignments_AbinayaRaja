public class HeaderGenerator
{
    public String generateHeaders(String heading1, String heading2)
    {
        StringBuilder headerBuilder = new StringBuilder();
        if(heading1 != null && !heading1.isEmpty())
        {
            headerBuilder.append("<H1>");
            headerBuilder.append(heading1);
            headerBuilder.append("</H1>\n");
        }
        if(heading1 != null && !heading2.isEmpty())
        {
            headerBuilder.append("<H2>");
            headerBuilder.append(heading2);
            headerBuilder.append("</H2>");
        }
        return headerBuilder.toString();
    }
}