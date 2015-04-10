
package org.glassfish.jersey.examples.helloworld.webapp;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
 import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.PathSegment;
 
 
/**
 *
 * @author ltcn
 */

@Path("collect")
public class Urlcollector {
    
    
    private final String defpage = "<HTML>\n" +
"   <HEAD>\n" +
"      <TITLE>\n" +
"         Tell me your favorite websites\n" +
"      </TITLE>\n" +
"   </HEAD>\n" +
"<BODY>\n" +
"   <H1>Hi</H1>\n" +
"   <P>Please input your favorite website's url and category~.</P>\n" +
"\n" +
"   <P></P>\n" +
"\n" +
"   <form action=\"collect/query\" method=\"get\">\n" +
"    Url: <input type=\"text\" name=\"furl\" />\n" +
"    Category: \n" +
"    <select name=\"fcategory\">\n" +
"	<option value=\"news\">News</option>\n" +
"	<option value=\"forum\">Forum</option>\n" +
"	<option value=\"search\" selected=\"selected\">Search</option>\n" +
"	<option value=\"tech\">Tech</option>\n" +
"    </select>\n" +
"   <input type=\"submit\" value=\"Submit\" />\n" + 
"</form> \n" +
           "<button type=\"button\">\n" +
"<a href=\"/helloworld-webapp/collect/favolist\">favorite list</a>\n" +
"</button>" +
"</BODY>\n" +
"</HTML>";
    
    
    
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getCollect() {
        return defpage;
    }
    
    @GET
    @Path("/query")
    @Produces(MediaType.APPLICATION_JSON)
    public String QuerUrl(@QueryParam("furl") String furlString,
            @QueryParam("fcategory") String categoryString) throws IOException {
        
        String recordedString = recorder(furlString,categoryString);
        
        return recordedString + "\n" + "Please use browser's back button back to previous page.";
    }
    
    @GET
    @Path("/{fcategory}/{furl}")
    @Produces(MediaType.APPLICATION_JSON)
    public String PathUrl(@PathParam("furl") String furlString,
            @PathParam("fcategory") String categoryString) throws IOException {
        String recordedString = recorder(furlString,categoryString);
        
        return recordedString + "\n" + "Please use browser's back button back to previous page.";
    }
    
    private String recorder(String furlString,String categoryString) throws IOException{
        FileWriter fr = new FileWriter("C://collect/customers.txt",true);
        
        fr.write(furlString + " " + categoryString + "\n");
        fr.flush();
        fr.close();
        
        
        return "You like " + 
                furlString + "in Category: " + categoryString + "has been recorded";
    }
    
    public String createhtmlurlstring(String url, String category){
        String urlheader = "<a href=\"";
        String urltail1 = "\">";
        String urltail2 = "</a>";

        
        String urlstring;
        urlstring = "<p>" + category + ": " + urlheader + "http://"+ url + urltail1 + url + urltail2 + "\n";
        
        return urlstring;
    }
    
    
    @GET
    @Path("/favolist")
    @Produces(MediaType.TEXT_HTML)
    public  String loadlist() throws FileNotFoundException, IOException{

        FileReader fr = new FileReader("C://collect/customers.txt");
        BufferedReader br = new BufferedReader(fr);
        String ln;
        

        
        String htmlheader = "<!DOCTYPE html PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\n" +
"<HTML>\n" +
"   <HEAD>\n" +
"      <TITLE>\n" +
"         Your favorite websites\n" +
"      </TITLE>\n" +
"   </HEAD>\n" +
"<BODY>\n Your favorite websites are: \n";
        String htmltail = "</BODY>\n" +
"</HTML>";
        
        
        String urllist = "\n";
        
        while((ln = br.readLine()) != null){
            StringTokenizer st = new StringTokenizer(ln," ");
            
            urllist = urllist + createhtmlurlstring(st.nextToken(),st.nextToken()) + "\n";           
            
        }
        return htmlheader + urllist + htmltail;
    }
    
}
