import java.lang.StringBuilder;

public class Question1
{
	StringBuilder builder;
	Question1()
	{
		builder = new StringBuilder();
	}

	public String buildBookQuoteHTML()
	{
		// Build header.
		buildHeader();

		// Add book quotes.
		buildParagraph("I know. I was there. I saw the great void in your soul, and you saw mine.");
		buildParagraph("She says nothing at all, but simply stares upward into the dark sky and watches, with sad eyes, the slow dance of the infinite stars.");
		buildParagraph("Clocks slay time… time is dead as long as it is being clicked off by little wheels; only when the clock stops does time come to life.");
		buildParagraph("None of those other things makes a difference. Love is the strongest thing in the world, you know. Nothing can touch it. Nothing comes close. If we love each other we’re safe from it all. Love is the biggest thing there is.");
		buildParagraph("Sometimes we get sad about things and we don’t like to tell other people that we are sad about them. We like to keep it a secret. Or sometimes, we are sad but we really don’t know why we are sad, so we say we aren’t sad but we really are.");
		buildParagraph("I know not all that may be coming, but be it what it will, I’ll go to it laughing.");
		buildParagraph("Maybe ever’body in the whole damn world is scared of each other.");
		buildParagraph("Life is to be lived, not controlled; and humanity is won by continuing to play in face of certain defeat.");
		buildParagraph("It's the possibility of having a dream come true that makes life interesting.");
		buildParagraph("I cannot fix on the hour, or the spot, or the look or the words, which laid the foundation. It is too long ago. I was in the middle before I knew that I had begun.");
		buildParagraph("There is no greater agony than bearing an untold story inside you.");
		buildParagraph("Still, there are times I am bewildered by each mile I have traveled, each meal I have eaten, each person I have known, each room in which I have slept. As ordinary as it all appears, there are times when it is beyond my imagination.");
		buildParagraph("And so we beat on, boats against the current, borne back ceaselessly into the past.");
		buildParagraph("Hello, babies. Welcome to Earth. It's hot in the summer and cold in the winter. It's round and wet and crowded. At the outside, babies, you've got about a hundred years here. There's only one rule that I know of, babies—God damn it, you've got to be kind.");
		buildParagraph("He stepped down, trying not to look long at her, as if she were the sun, yet he saw her, like the sun, even without looking.");
		buildParagraph("How wonderful it is that nobody need wait a single moment before starting to improve the world.");
			
		// Build footer.
		buildFooter();
		return builder.toString();
	}

	private void buildHeader()
	{
		builder.append("<H1>A page of famous book quotes!</H1>\n");
		builder.append("<H2>- Some book reviewer</H2>");
	}

	private void buildParagraph(String text)
	{
		builder.append("<P>");
		builder.append(text);
		builder.append("</P>\n");
	}

	private void buildFooter()
	{
		builder.append("<A HREF=\"page2.html\">Click here to go to the next page!</A>\n");
	}
}