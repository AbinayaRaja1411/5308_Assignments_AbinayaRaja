public class ParagraphGenerator
{
    String[] paragraphs;
    ParagraphGenerator(String[] paragraphs)
    {
        this.paragraphs = paragraphs;
    }

    public String generateParagraphs()
    {
        StringBuilder paragraphBuilder = new StringBuilder();
        for (String paraText : paragraphs) 
        {
            paragraphBuilder.append("<P>");
		    paragraphBuilder.append(paraText);
		    paragraphBuilder.append("</P>\n");
        }
        return paragraphBuilder.toString();
    }
} 