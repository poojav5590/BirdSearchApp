package ds;

/*
 * @author Pooja Vasudevan 2/7/2020
 * 
 * This file is the Model component of the MVC, and it models the business
 * logic for the web application.  In this case, the business logic involves
 * making a request to zoo website then screen scraping the HTML that is
 * returned in order to fabricate an image URL.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Random;

public class BirdModel {

    /**
     * Arguments.
     *
     * @param searchTag the bird's name to be searched for.
     */
    public ArrayList<String> searchBird(String searchTag)
            throws UnsupportedEncodingException  {

        String[] arr = searchTag.split(" ");
        searchTag = URLEncoder.encode(searchTag, "UTF-8");
        String response = "";
        String searchURL="";
        if(arr.length>1) {
            if(arr.length==3){
                searchURL = "https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?pix=" + arr[0] + "+" + arr[1]+"+"+arr[2];
            }
            else {
                searchURL = "https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?pix=" + arr[0] + "+" + arr[1];
            }
        } else{
            searchURL = "https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/bird.cfm?pix=" + searchTag;
        }

        response = fetch(searchURL);

        int cutLeft = response.indexOf("https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/images/bigpic/");

        // If not found, then no such photo is available.
        if (cutLeft == -1) {
            System.out.println("pictureURL= null");
            return null;
        }

        int cutRight =0;
        ArrayList<String> pictureList = new ArrayList<String>();

        //get first 20 images
        for(int i=-0; i<20; i++){
            cutLeft  = response.indexOf("https://nationalzoo.si.edu/scbi/migratorybirds/featured_photo/images/bigpic/", cutRight);
            if(cutLeft == -1){
                break;
            }
            cutRight = response.indexOf(".jpg", cutLeft);
            cutRight += 4;
            String pictureURL = response.substring(cutLeft, cutRight);
            pictureList.add(pictureURL);
        }

        Random r = new Random();
        //choose random image from those 20
        String finalPic="";
        if(!pictureList.isEmpty()){
            int x = r.nextInt(pictureList.size());
            finalPic = pictureList.get(x);
        }

        int picLeft = response.indexOf(finalPic);
        int cutLeftAuthor = response.indexOf("View more pictures by this photographer", picLeft);
        cutLeftAuthor +="View more pictures by this photographer".length();


        // Now snip out the part from positions cutLeft to cutRight to retrieve author's name
        int cutRightAuthor = response.indexOf("</a>",cutLeftAuthor);
        //String pictureURL = response.substring(cutLeft, cutRight);
        String authorName = response.substring(cutLeftAuthor,cutRightAuthor-1);
        String[] authors = authorName.split(";");
        authorName = authors[1];
        //pictureURL = getPicSize(pictureURL, picSize);
        System.out.println("pictureURL= " + finalPic);
        System.out.println("Author name "+ authorName);
        ArrayList<String> pictureInfo = new ArrayList<String>();
        pictureInfo.add(finalPic);
        pictureInfo.add(authorName);
        return pictureInfo;

    }



    /*
     * Make an HTTP request to a given URL
     * 
     * @param urlString The URL of the request
     * @return A string of the response from the HTTP GET.  This is identical
     * to what would be returned from using curl on the command line.
     */
    private String fetch(String urlString) {
        String response = "";
        try {
            URL url = new URL(urlString);
            /*
             * Create an HttpURLConnection.  This is useful for setting headers
             * and for getting the path of the resource that is returned (which 
             * may be different than the URL above if redirected).
             * HttpsURLConnection (with an "s") can be used if required by the site.
             */
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            // Read all the text returned by the server
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String str;
            // Read each line of "in" until done, adding each to "response"
            while ((str = in.readLine()) != null) {
                // str is one line of text readLine() strips newline characters
                response += str;
            }
            in.close();
        } catch (IOException e) {
            System.out.println("Eeek, an exception");
        }
        return response;
    }
}
