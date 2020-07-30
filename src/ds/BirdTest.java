package ds;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Random;

/*
 * @author Pooja Vasudevan 2/7/20
 *
 * This servlet class initializes the BirdModel and contains a doGet method.
 * This takes in a user search word, populates the dropdown accordingly, and
 * displays a relevant image with photographer's name. This describes MVC pattern
 * with servlet.
 */

@WebServlet(name = "BirdTest",
        urlPatterns = {"/getBird"})
public class BirdTest extends javax.servlet.http.HttpServlet {

    BirdModel ipm = null;  // The "business model" for this app

    // Initiate this servlet by instantiating the model that it will use.
    @Override
    public void init() {
        ipm = new BirdModel();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String nextView="";
        String search = request.getParameter("searchWord");
        request.setAttribute("searchText", search);
        String ua = request.getHeader("User-Agent");
        if (ua != null && (ua.indexOf("Android") != -1) || (ua.indexOf("iPhone") != -1)) {
            request.setAttribute("doctype", "<!DOCTYPE html PUBLIC \"-//WAPFORUM//DTD XHTML Mobile 1.2//EN\" \"http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd\">");
        } else {

            request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
        }

        if(search!=null) {
            System.out.println(search);
            ArrayList<String> birdList = new ArrayList<String>();



            //read the birdnames from file

            try {
                InputStream in = getServletContext().getResourceAsStream("/birdnames.txt");
                BufferedReader b = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
               // sc = new Scanner(new File("/Users/pooja.vasudevan/IdeaProjects/Project1Task2/birdnames.txt")).useDelimiter("\n");
                String line;

                while((line=b.readLine())!=null){
                    birdList.add(line);

                }
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("File not found");
            }

            System.out.println(birdList.size());

            ArrayList<String> newBirdList = new ArrayList<String>();
            for (int i = 0; i < birdList.size(); i++) {
                if (birdList.get(i).toLowerCase().contains(search.toLowerCase())) {
                    newBirdList.add(birdList.get(i));
                }

            }

            ArrayList<String> subBirdList = new ArrayList<String>();
            ArrayList<Integer> intList = new ArrayList<Integer>();

            //randomly populate 10 bird names
            if(!newBirdList.isEmpty()) {
                if(newBirdList.size()>10){
                    Random r = new Random();
                    for (int i = 0; i < 10; i++) {
                        int x = r.nextInt(newBirdList.size());
                        while (intList.contains(x)) {
                            x = r.nextInt(newBirdList.size());
                        }
                        intList.add(x);
                        subBirdList.add(newBirdList.get(x));
                    }
                }
                else{
                    subBirdList = newBirdList;
                }
            }


            System.out.println("Found this many bird:" + newBirdList.size());
            request.setAttribute("birdList", subBirdList);

            nextView = "prompt.jsp";
            RequestDispatcher view = request.getRequestDispatcher(nextView);
            view.forward(request, response);
            //request.setAttribute("doctype", "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");

        } else {
            String birdVal = request.getParameter("birdbox");
            if(birdVal!=null) {
                request.setAttribute("birdName", birdVal);

                ArrayList<String> pInfo = ipm.searchBird(birdVal);
                request.setAttribute("pictureURL", pInfo.get(0));
                request.setAttribute("author", pInfo.get(1));

                String finalView = "result.jsp";
                RequestDispatcher v = request.getRequestDispatcher(finalView);
                v.forward(request, response);
            } else {
                nextView = "index.jsp";
            RequestDispatcher vw = request.getRequestDispatcher(nextView);
            vw.forward(request, response);
            System.out.println(request.getServletPath());
            }


            //}
        }



    }
}
