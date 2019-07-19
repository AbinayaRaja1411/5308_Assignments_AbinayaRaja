class FooterGenerator
{
    public String generateFooter(String footerText, String linkPageName)
    {
        StringBuilder footerBuilder = new StringBuilder();
        footerBuilder.append("<A HREF=\"" + linkPageName + ".html\">");
        footerBuilder.append(footerText);
        footerBuilder.append("</A>\n");
        return footerBuilder.toString();
    }
}