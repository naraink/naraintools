package narainthots.codePickerPlugin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

public class CodePickerHelper {

	public  String snippetProvider = new String("all");
	public  String snippetProviderTmp = new String("");
	public  ArrayList<String> codeList = new ArrayList<String>();
	public  ArrayList<String> titleList = new ArrayList<String>();
	public  ArrayList<String> linkList = new ArrayList<String>();
	public  String searchResult = null;

	public  String retrieveJsonResults(String urlString)
			throws IOException {

		URL url = new URL(urlString);

		StringBuffer response = new StringBuffer();
		BufferedReader in = null;
		if (snippetProviderTmp.equalsIgnoreCase("stackoverflow")) {
			in = new BufferedReader(new InputStreamReader(new GZIPInputStream(
					url.openStream())));
		} else if (snippetProviderTmp.equalsIgnoreCase("snipt")) {
			in = new BufferedReader(new InputStreamReader(url.openStream()));
		}
		String inputLine;

		while ((inputLine = in.readLine()) != null) {
			response = response.append(inputLine);
		}
		in.close();

		return response.toString();

	}

	public  String searchProvider(String searchString, String tag) {

		String searchBaseURL = new String();
		try {
			if (snippetProviderTmp.equalsIgnoreCase("stackoverflow")) {
				searchBaseURL = new String(
						"http://api.stackexchange.com/2.2/search/advanced?order=desc&sort=activity&accepted=True&title="
								+ URLEncoder.encode(searchString, "UTF-8")
										.replaceAll("\\+", "%20")
								+ "&tagged="
								+ URLEncoder.encode(tag, "UTF-8")
								+ "&site=stackoverflow");
			} else if (snippetProviderTmp.equalsIgnoreCase("snipt")) {
				searchBaseURL = new String(
						"https://snipt.net/api/public/snipt/?format=json&q="
								+ URLEncoder.encode(searchString + " " + tag,
										"UTF-8"));
			} else if("javaEg".equalsIgnoreCase(snippetProviderTmp)){
				searchBaseURL = new String("http://www.java-examples.com/search/node/" + searchString);
				return searchBaseURL;
			}else {
				return "";
			}		

		
		return retrieveJsonResults(searchBaseURL);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println(searchBaseURL);
			e.printStackTrace();
		}
		return null;
	}

	public  String searchAnswersStackOverFlow(String answerId)
			throws Exception {
		String searchBaseURL = new String(
				"https://api.stackexchange.com/2.2/answers/"
						+ answerId
						+ "?order=desc&sort=activity&site=stackoverflow&filter=withbody");
		//System.out.println(searchBaseURL);
		return retrieveJsonResults(searchBaseURL);
	}

	public  ArrayList getJSONTag(String jSONString) {

		JsonParserFactory factory = JsonParserFactory.getInstance();
		JSONParser parse = factory.newJsonParser();
		//System.out.println("jsondata: " + jSONString);

		/*
		 * Exception in thread "main" com.json.exceptions.JSONParsingException:
		 * 
		 * @Key-Heirarchy::root/idsNodes[0]/ @Key:: Description is expected but
		 * found empty...
		 * 
		 * @Position::256
		 */
		try {
			jSONString = jSONString.replaceAll("\"\"", "\" \"");
			//System.out.println("jsondata: " + jSONString);
			Map jsonData = parse.parseJson(jSONString);

			ArrayList items = new ArrayList();

			if ("stackoverflow".equals(snippetProviderTmp)) {
				items = (ArrayList) jsonData.get("items");
			} else if ("snipt".equals(snippetProviderTmp)) {
				items = (ArrayList) jsonData.get("objects");
			}
			// System.out.println("jsonData.get(items):" +
			// jsonData.get("items"));
			return items;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList();
		}

	}

	public  ArrayList getTitles() {
		return titleList;
	}

	public  ArrayList getLinks() {
		return linkList;
	}
	
	public ArrayList getCodeList() {
		return codeList;
	}
	
	public void searchSnipt(String searchString, String tag) throws Exception{
		if (snippetProvider.equalsIgnoreCase("snipt")
				|| snippetProvider.equalsIgnoreCase("all")) {
			String code = new String();
			String title = new String();
			String link = new String();

			snippetProviderTmp = new String("snipt");
			searchResult = searchProvider(searchString, tag);
			ArrayList<?> searchItems = getJSONTag(searchResult);
			
			// TODO for full results use searchItems.size()
			int srcSize = 0;
			srcSize = searchItems.size() > 5 ? 5 : searchItems.size();

			for (int i = 0; i < srcSize; i++) {
				title = (String) ((Map<?, ?>) searchItems.get(i)).get("title");
				title = snippetProviderTmp + ":  " + title;
				//System.out.println(title);
				titleList.add(title);

				link = (String) ((Map<?, ?>) searchItems.get(i))
						.get("full_absolute_url");
				//System.out.println(link);
				linkList.add(link);

				code = (String) ((Map<?, ?>) searchItems.get(i)).get("code");
				//System.out.println(code);
				try {
					code = StringEscapeUtils.unescapeJava(code);
				} catch (Exception e) {
					;
				}
				codeList.add(code);
			}
		}
	}
	
	public void searchStackOverFlow(String searchString, String tag) {
		if (snippetProvider.equalsIgnoreCase("stackoverflow")
				|| snippetProvider.equalsIgnoreCase("all")) {

			String answerId = new String();
			String title = new String();
			String link = new String();

			String answerResult = null;
			ArrayList<?> answerItems = new ArrayList<Object>();
			String body = null;
			
			snippetProviderTmp = new String("stackoverflow");
			searchResult = searchProvider(searchString, tag);
			ArrayList<?> searchItems = getJSONTag(searchResult);
			
			// TODO for full results use searchItems.size()
			int srcSize = 0;
			srcSize = searchItems.size() > 5 ? 5 : searchItems.size();

			for (int i = 0; i < srcSize; i++) {
				title = (String) ((Map<?, ?>) searchItems.get(i)).get("title");
				title = snippetProviderTmp + ":  " + title;
				//System.out.println(title);
				titleList.add(title);
				
				link = (String) ((Map<?, ?>) searchItems.get(i)).get("link");
				//System.out.println(link);
				linkList.add(link);

				answerId = (String) ((Map<?, ?>) searchItems.get(i))
						.get("accepted_answer_id");
				//System.out.println("Answer Id: " + answerId);
				try {
					answerResult = searchAnswersStackOverFlow(answerId);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				answerItems = getJSONTag(answerResult);
				String code = new String();
				for (int j = 0; j < answerItems.size(); j++) {
					body = (String) ((Map<?, ?>) answerItems.get(j))
							.get("body");
					// System.out.println("Body" + body);
					if (body.indexOf("<code>") != -1) {
						/*System.out.println("Code:"
								+ body.substring(body.indexOf("<code>") + 6,
										body.indexOf("</code>")));*/
						code = body.substring(body.indexOf("<code>") + 6,
								body.indexOf("</code>"));

						try {
							code = StringEscapeUtils.unescapeJava(code);
						} catch (Exception e) {
							;
						}
						codeList.add(code);
					}
				}

			}
		}
	}
	
	private void searchJavaEgs(String searchString, String tag) throws Exception {
		
	if (snippetProvider.equalsIgnoreCase("javaEg")
			|| snippetProvider.equalsIgnoreCase("all")) {
		String code = new String();
		String title = new String();
		String link = new String();

		snippetProviderTmp = new String("javaEg");
		searchResult = searchProvider(searchString, tag);
		Document srchResultDoc = Jsoup.connect(searchResult).get();
		
		Element content = srchResultDoc.getElementById("content-area");
		Elements links = content.getElementsByTag("a");

		int srcSize = 0;
		for (Element linkEle : links) {
			  title = linkEle.text();
			  title = snippetProviderTmp + ":  " + title;
			  //System.out.println(title);
			  titleList.add(title);
			  
			  link = linkEle.attr("href");
			  //System.out.println(link);			  
			  linkList.add(link);
			  
			  
		
		Document doc = Jsoup.connect(link).get();
		Element codeEle = doc.getElementById("content-area");
		
		for (Element codeLine : codeEle.getElementsByTag("li")){
			code = code + codeLine.text() + "\n"; 
		}
		
		//code = codeEle.text();
		//System.out.println("Code:" + code);
		srcSize ++;
		
		codeList.add(code);
		
		
		if (5==srcSize){
			break;
		}
		
		}
		
		
	}
	
	}

	@SuppressWarnings("rawtypes")
	public  ArrayList getSuggestions(String searchString) throws Exception {

		/*
		 * snipt - https://snipt.net/api/public/snipt/?format=json&q=<<>> or
		 * stackoverflow - Questions:
		 * http://api.stackexchange.com/2.2/search/advanced
		 * ?order=desc&sort=activity
		 * &accepted=True&title="+ URLEncoder.encode(searchString, "
		 * UTF-8").replaceAll("
		 * \\+", "%20") +"&tagged="+ URLEncoder.encode(tag,"UTF
		 * -8") +"&site=stackoverflow Specific Answers:
		 * https://api.stackexchange
		 * .com/2.2/answers/"+answerId+"?order=desc&sort
		 * =activity&site=stackoverflow&filter=withbody or googlecode - Not
		 * implemented
		 */

		

		
		
		snippetProvider = new String("all"); //snipt or stackoverflow or javaEg or all

		String tag = "java";
		
		codeList = new ArrayList<String>();
		titleList = new ArrayList<String>();
		linkList = new ArrayList<String>();

		//System.out.println("Search String Requested:" + searchString);
		//System.out.println("To be searched in site: " + snippetProvider);
		
		long before;
		long after;

		before = System.currentTimeMillis();
		searchJavaEgs(searchString, tag);
		after = System.currentTimeMillis();
		System.out.println("JavaEgs Took:" + (after - before));
		
		before = System.currentTimeMillis();
		searchSnipt(searchString, tag);
		after = System.currentTimeMillis();
		System.out.println("snipt Took:" + (after - before));
		
		before = System.currentTimeMillis();
		searchStackOverFlow(searchString, tag);
		after = System.currentTimeMillis();
		System.out.println("StackOverFlow Took:" + (after - before));
		
		return codeList;

	}


}
