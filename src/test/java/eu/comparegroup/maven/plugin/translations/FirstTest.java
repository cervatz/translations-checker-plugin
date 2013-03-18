package eu.comparegroup.maven.plugin.translations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.Ignore;
import org.junit.Test;

public class FirstTest {
	
	@Test
	public void testThreee() {
		Pattern p = Pattern.compile("messages.([a-zA-Z_0-9]+)");
		//		String stringToProcess = "<meta name=\"description\" content=\"#{headText.buildText(category.pageMetaTagConfiguration.descriptionTemplate, messages.compare_category_meta_description)}\" />";		
		String stringToProcess = "<a class=\"more-details\" href=\"/shopinfo/#{shop.friendlyShopName}/review/?fullform=true\" title=\"#{messages.compare_product_list_post_a_review1}\">#{messages.compare_product_list_post_a_review2}</a>";
		Matcher m = p.matcher(stringToProcess);
		while (m.find()) {
			System.out.println(m.group());
		}
	}
	
}
